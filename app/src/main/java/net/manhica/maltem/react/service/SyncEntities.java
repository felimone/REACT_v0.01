package net.manhica.maltem.react.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import net.manhica.maltem.react.R;
import net.manhica.maltem.react.database.Database;
import net.manhica.maltem.react.model.Case;
import net.manhica.maltem.react.model.Household;
import net.manhica.maltem.react.model.Member;
import net.manhica.maltem.react.model.Table;
import net.manhica.maltem.react.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by afelimone on 6/4/2016.
 */
public class SyncEntities extends AsyncTask<Void, Integer, String> {
    private String response = null;
    private URL url;
    private final String BASE_URL = "http://192.168.1.100/REACT/API/api.php?op=";
    private InputStream inputStream;
    private BufferedReader bufferedReader;
    private ArrayList<Member> alMember;
    private Database database;
    private Context context;
    public ServiceResponse serviceResponse;
    private int task;
    private HttpURLConnection connection = null;
    private ProgressDialog progressDialog;
    private State state;
    private Entity entity;
    private final List<Table> values = new ArrayList<Table>();

    private enum State {DOWNLOADING, SAVING}
    private enum Entity {HOUSEHOLD, MEMBER, CASES}

    public SyncEntities(Context context, int task, ServiceResponse serviceResponse, ProgressDialog progressDialog) {
        this.context = context;
        this.serviceResponse = serviceResponse;
        database = new Database(context);
        this.task = task;
        this.progressDialog = progressDialog;
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        StringBuilder builder = new StringBuilder();
        switch (state) {
            case DOWNLOADING:
                builder.append(context.getString(R.string.label_downloading));
                break;
            case SAVING:
                builder.append(context.getString(R.string.label_saving));
                break;
        }
        switch (entity){
            case MEMBER:
                builder.append(" "+context.getString(R.string.label_members));
                break;
            case HOUSEHOLD:
                builder.append(" "+context.getString(R.string.label_households));
                break;
        }
        if (values.length > 0) {
            builder.append(" " + values[0] + "%");
        }
        progressDialog.setMessage(builder.toString());
    }

    /*
    public String logon(String jsonCredentials, String urlStr){

        JSONArray JSONResponse = null;
        try {
            progressDialog.setMessage(context.getString(R.string.message_authenticating));
            url = new URL(urlStr);
            connection = (HttpURLConnection)url.openConnection();
            connection.setDoOutput(true);
            connection.setConnectTimeout(60000);
            connection.setReadTimeout(12000);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            Log.d("Server", ""+connection.getResponseMessage());
            if(connection.getResponseCode()==connection.HTTP_OK){
                User user = new User();
                inputStream = connection.getInputStream();
                String response = connection.getResponseMessage();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line="";
                StringBuffer stringBuffer = new  StringBuffer();
                while ((line= bufferedReader.readLine())!=null){
                    stringBuffer.append(line);
                }
                Log.d("RESPONSE:", stringBuffer.toString());
                JSONResponse = new JSONArray(stringBuffer.toString());
                JSONObject jsonObject = JSONResponse.getJSONObject(0);
                user.setPassword(jsonObject.getString(Database.User.COLUMN_NAME_PASSWORD));
                user.setUsername(jsonObject.getString(Database.User.COLUMN_NAME_USERNAME));
                database.open();
                long insertedId = database.insert(user);
                database.close();
                if(insertedId>0){
                    Log.d("ADD USER","success");
                    return "success";
                }
                else{
                    return "error";
                }
            }
        }
        catch (IOException ex){
            ex.printStackTrace();
            return "-1";
        }
        catch (JSONException ex){
            ex.printStackTrace();
            return "-2";
            // Invalid user
        }
        return  "-3";
    }
*/
    //get allMember from database remote database and save locally
    public void syncDatabase(){
        String households = getHouseholds(BASE_URL+2);
        saveHouseholds(households);
        String members = getMembers(BASE_URL+1);
        saveMembers(members);
    }

    public String getMembers(String address) {
        Double progress;
        StringBuilder stringBuilder= new StringBuilder();
        try {
            entity = Entity.MEMBER;
            url = new URL(address);
            progressDialog.setMessage(context.getString(R.string.label_connecting));
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "aplication/json");
            connection.connect();
            inputStream = connection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            int contentLenght = Integer.parseInt(connection.getHeaderField("Content-length"));
            if(connection.getResponseCode()==HttpURLConnection.HTTP_OK && contentLenght>0){
                inputStream = connection.getInputStream();
                byte data[] = new byte[contentLenght];
                int bytesRead = 0;
                int totalRead = 0;
                state = State.DOWNLOADING;
                while((bytesRead = inputStream.read(data))>0){
                    stringBuilder.append(new String(data,0,bytesRead,"UTF-8"));
                    totalRead+=bytesRead;
                    if(totalRead%100==0){ // only update fora each 100 chars read
                        progress = (totalRead*100/(double)contentLenght);
                        publishProgress(progress.intValue());
                    }
                }
            }
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                    return stringBuilder.toString();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public int saveMembers(String memberJSON){

        JSONArray memberArrayJSON;
        alMember = new ArrayList<>();
        Log.d("response",memberJSON);
        if(memberJSON!=null){
            try{
                memberArrayJSON = new JSONArray(memberJSON);
                Member member = new Member();
                JSONObject jsonObject;
                database.open();
                state = State.SAVING;
                entity = Entity.MEMBER;
                Double progress;
                for (int i = 0; i < memberArrayJSON.length(); i++) {
                    jsonObject = memberArrayJSON.getJSONObject(i);
                    member.setName(jsonObject.getString("name"));
                    member.setSurname(jsonObject.getString("surname"));
                    member.setHouseholdNumber(jsonObject.getString("household_number"));
                    member.setHouseholdUuid(jsonObject.getString("household_uuid"));
                    member.setMemberUuid(jsonObject.getString("member_uuid"));
                    member.setPermid(jsonObject.getString("permid"));
                    member.setVisitDate(jsonObject.getString("last_visit_date"));
                    if(i%100==0){
                        progress = i*100/(double)memberArrayJSON.length();
                        publishProgress(progress.intValue());
                    }
                    long insertId = database.insert(member);
                    Log.d("insertedId",insertId+"");
                    if(insertId<0){
                        return -1;
                    }
                }
                database.close();
                return 1;
            }
            catch (JSONException ex){
                ex.printStackTrace();
            }
        }
        return 0;
    }

    public String getHouseholds(String address) {
        Double progress;
        StringBuilder stringBuilder= new StringBuilder();
        try {
            entity = Entity.HOUSEHOLD;
            url = new URL(address);
            progressDialog.setMessage(context.getString(R.string.label_connecting));
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "aplication/json");
            connection.connect();
            inputStream = connection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            int contentLenght = Integer.parseInt(connection.getHeaderField("Content-length"));
            if(connection.getResponseCode()==HttpURLConnection.HTTP_OK && contentLenght>0){
                inputStream = connection.getInputStream();
                byte data[] = new byte[contentLenght];
                int bytesRead = 0;
                int totalRead = 0;
                state = State.DOWNLOADING;
                while((bytesRead = inputStream.read(data))>0){
                    stringBuilder.append(new String(data,0,bytesRead,"UTF-8"));
                    totalRead+=bytesRead;
                    if(totalRead%100==0){ // only update fora each 100 chars read
                        progress = (totalRead*100/(double)contentLenght);
                        publishProgress(progress.intValue());
                    }
                }
            }
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                    return stringBuilder.toString();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public int saveHouseholds(String householdJSON){
        JSONArray householdArrayJSON;
        alMember = new ArrayList<>();
        Log.d("response",householdJSON);
        if(householdJSON!=null){
            try{
                householdArrayJSON = new JSONArray(householdJSON);
                Household household = new Household();
                JSONObject jsonObject;
                database.open();
                state = State.SAVING;
                entity = Entity.HOUSEHOLD;
                Double progress;
                for (int i = 0; i < householdArrayJSON.length(); i++) {
                    jsonObject = householdArrayJSON.getJSONObject(i);
                    household.setHouseholdUuid(jsonObject.getString("household_uuid"));
                    household.setHouseholdNumber(jsonObject.getString("household_number"));
                    household.setHouseholdHead(jsonObject.getString("household_head"));
                    household.setLastVisitDate(jsonObject.getString("last_visit_date"));
                    if(i%100==0){
                        progress = i*100/(double)householdArrayJSON.length();
                        publishProgress(progress.intValue());
                    }
                    long insertId = database.insert(household);
                    Log.d("insertedId",insertId+"");
                    if(insertId<0){
                        return -1;
                    }
                }
                database.close();
                return 1;
            }
            catch (JSONException ex){
                ex.printStackTrace();
            }
        }
        return 0;
    }

    public String getCasess(String address) {
        Double progress;
        StringBuilder stringBuilder= new StringBuilder();
        try {
            entity = Entity.CASES;
            url = new URL(address);
            progressDialog.setMessage(context.getString(R.string.label_connecting));
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "aplication/json");
            connection.connect();
            inputStream = connection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            int contentLenght = Integer.parseInt(connection.getHeaderField("Content-length"));
            if(connection.getResponseCode()==HttpURLConnection.HTTP_OK && contentLenght>0){
                inputStream = connection.getInputStream();
                byte data[] = new byte[contentLenght];
                int bytesRead = 0;
                int totalRead = 0;
                state = State.DOWNLOADING;
                while((bytesRead = inputStream.read(data))>0){
                    stringBuilder.append(new String(data,0,bytesRead,"UTF-8"));
                    totalRead+=bytesRead;
                    progress = (totalRead*100/(double)contentLenght);
                    publishProgress(progress.intValue());
                }
            }
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                    return stringBuilder.toString();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public int saveCases(String casesJSON){
        JSONArray caseArrayJSON;
        alMember = new ArrayList<>();
        Log.d("response",casesJSON);
        if(casesJSON!=null){
            try{
                caseArrayJSON = new JSONArray(casesJSON);
                Case cases = new Case();
                JSONObject jsonObject;
                database.open();
                state = State.SAVING;
                entity = Entity.CASES;
                Double progress;
                for (int i = 0; i < caseArrayJSON.length(); i++) {
                    jsonObject = caseArrayJSON.getJSONObject(i);
                    cases.setHouseholdUuid(jsonObject.getString("household_uuid"));
                    cases.setDob(jsonObject.getString("dob"));
                    cases.setHouseholdNumber(jsonObject.getString("household_number"));
                    cases.setGender(jsonObject.getString("gender"));
                    cases.setAdministrativePost(jsonObject.getString("administrative_post"));
                    cases.setHeadPhone(jsonObject.getString("head_phone"));
                    cases.setIndex(jsonObject.getString("index_uuid"));
                    cases.setHouseNextTo(jsonObject.getString("house_next_to"));
                    cases.setLocality(jsonObject.getString("locality"));
                    cases.setVillage(jsonObject.getString("vllage"));
                    cases.setHeadPhone(jsonObject.getString("head_phone"));
                    cases.setMemberUuid(jsonObject.getString("member_uuid"));
                    cases.setMotherName(jsonObject.getString("mother_name"));
                    cases.setMotherSurname(jsonObject.getString("mother_surname"));
                    cases.setName(jsonObject.getString("name"));
                    cases.setSurname(jsonObject.getString("surname"));
                    cases.setOtherLocationInfo(jsonObject.getString("other_location_info"));
                    cases.setPermid(jsonObject.getString("permid"));
                    cases.setPhone(jsonObject.getString("phone"));
                    cases.setStreet(jsonObject.getString("street"));
                    progress = i*100/(double)caseArrayJSON.length();
                    publishProgress(progress.intValue());
                    long insertId = database.insert(cases);
                    Log.d("insertedId",insertId+"");
                    if(insertId<0){
                        return -1;
                    }
                }
                database.close();
                return 1;
            }
            catch (JSONException ex){
                ex.printStackTrace();
            }
        }
        return 0;
    }
    //login remotely and save user data locally
    public HttpURLConnection connect(String urlStr) {
        try {
            url = new URL(urlStr);
            return (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            return null;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected String doInBackground(Void... params) {

        String result = "error";
        switch (task) {
            case 1:
                syncDatabase();
                break;
            case 2:
                //
                break;
        }
        return result;
    }

    protected void onPostExecute(String result) {
        progressDialog.dismiss();
        serviceResponse.serviceFinish(result);
    }
}

