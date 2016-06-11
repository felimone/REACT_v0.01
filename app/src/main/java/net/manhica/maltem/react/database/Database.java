package net.manhica.maltem.react.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;
import android.widget.Toast;

import net.manhica.maltem.react.R;
import net.manhica.maltem.react.model.Table;

import java.util.Collection;

/**
 * Created by afelimone on 6/4/2016.
 */
public class Database {

    public static final String DATABASE_NAME = "react";
    public static final int DATABASE_VERSION = 9;

    public static class Case implements BaseColumns{
        public static final String COLUMN_PERMID = "PERMID";
        public static final String COLUMN_INDEX_UUID = "INDEX_UUID";
        public static final String COLUMN_MEMBER_UUID = "MEMBER_UUID";
        public static final String COLUMN_HOUSEHOLD_UUID = "HOUSEHOLD_UUID";
        public static final String COLUMN_STATUS = "STATUS";
        public static final String COLUMN_NAME = "NAME";
        public static final String COLUMN_SURNAME = "SURNNAME";
        public static final String COLUMN_LAST_VISIT_DATE = "LAST_VISIT_DATE";
        public static final String COLUMN_DOB = "DOB";
        public static final String COLUMN_MOTHER_NAME = "MOTHER_NAME";
        public static final String COLUMN_MOTHER_SURNAME = "MOTHER_SURNAME";
        public static final String COLUMN_STREET = "STREET";
        public static final String COLUMN_ADMINISTRATIVE_POST = "ADMINISTRATIVE_POST";
        public static final String COLUMN_VILLAGE = "VILLAGE";
        public static final String COLUMN_LOCALITY = "LOCALITY";
        public static final String COLUMN_NEIGHBOR_PHONE = "NEIGHBOR_PHONE";
        public static final String COLUMN_PHONE = "PHONE";
        public static final String COLUMN_HEAD_PHONE = "HEAD_PHONE";
        public static final String COLUMN_HOUSE_NEXT_TO = "HOUSE_NEXT_TO";

        public static final String TABLE_NAME="CASES";

        public static final String[] ALL_COLUMNS = {COLUMN_PERMID, COLUMN_INDEX_UUID, COLUMN_MEMBER_UUID, COLUMN_HOUSEHOLD_UUID, COLUMN_STATUS,
                                                    COLUMN_NAME, COLUMN_SURNAME, COLUMN_HEAD_PHONE, COLUMN_NEIGHBOR_PHONE, COLUMN_PHONE,
                                                    COLUMN_HOUSE_NEXT_TO, COLUMN_LOCALITY, COLUMN_STREET, COLUMN_ADMINISTRATIVE_POST, COLUMN_VILLAGE,
                                                    COLUMN_MOTHER_NAME, COLUMN_MOTHER_SURNAME};
    }

    public static class Member implements BaseColumns{
        public static final String COLUMN_NAME = "NAME";
        public static final String COLUMN_SURNAME = "SURNAME";
        public static final String COLUMN_MEMBER_UUID = "MEMBER_UUID";
        public static final String COLUMN_PERMID = "PERMID";
        public static final String COLUMN_HOUSEHOLD_UUID = "HOUSEHOLD_UUID";
        public static final String COLUMN_VISIT_DATE = "VISIT_DATE";
        public static final String COLUMN_STATUS = "STATUS";


        public static final String TABLE_NAME = "MEMBER";

        public static final String[] ALL_COLUMNS = {COLUMN_NAME, COLUMN_SURNAME, COLUMN_MEMBER_UUID, COLUMN_PERMID, COLUMN_HOUSEHOLD_UUID,
                                                        COLUMN_VISIT_DATE, COLUMN_STATUS};

    }

    public static class Household implements BaseColumns{
        public static final String COLUMN_HOUSEHOLD_NUMBER = "HOUSEHOLD_NUMBER";
        public static final String COLUMN_HOUSEHOLD_UUID = "HOUSEHOLD_UUID";
        public static final String COLUMN_HOUSEHOLD_HEAD = "HOUSEHOLD_HEAD";
        public static final String COLUMN_LAST_VISIT_DATE = "VISIT_DATE";
        public static final String COLUMN_STATUS = "STATUS";

        public static final String TABLE_NAME = "HOUSEHOLD";

        public static final String[] ALL_COLUMNS = {COLUMN_HOUSEHOLD_NUMBER, COLUMN_HOUSEHOLD_UUID, COLUMN_HOUSEHOLD_HEAD, COLUMN_LAST_VISIT_DATE, COLUMN_STATUS};
    }

    public static class User implements BaseColumns{
        public static final String COLUMN_USERNAME = "USERNMAE";
        public static final String COLUMN_PASSWORD = "PASSWORD";
        public static final String COLUMN_NAME = "NAME";
        public static final String COLUMN_SURNAME = "SURNAME";
        public static final String COLUMN_URI = "URI";
        public static final String COLUMN_TYPE = "TYPE";

        public static final String TABLE_NAME = "USER";

        public static final String[] ALL_COLUMNS = {COLUMN_USERNAME, COLUMN_PASSWORD, COLUMN_NAME, COLUMN_SURNAME, COLUMN_URI, COLUMN_TYPE};

    }

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqlDatabase;

    public Database(Context context){
        try{
            databaseHelper = new DatabaseHelper(context);
        }
        catch (Exception ex){
            Toast.makeText(context, context.getString(R.string.message_error_opening_db), Toast.LENGTH_LONG).show();
        }
    }

    public void open(){
        try{
            sqlDatabase = databaseHelper.getWritableDatabase();
        }
        catch(Exception ex)
        {
            Log.d("Error:", ex.getMessage());
        }

    }

    public void close(){
        databaseHelper.close();
    }

    public long insert(Collection<? extends Table> entities){
        long insertedId = -1;
        for(Table entity: entities){
            insertedId = sqlDatabase.insert(entity.getTableName(), null, entity.getContentValues());
        }
        return insertedId;
    }

    public long insert(Table table){
        long insertedId = -1;
        insertedId = sqlDatabase.insert(table.getTableName(), null, table.getContentValues());
        return insertedId;
    }

    public int update(Class<? extends Table> table, ContentValues contentValues, String whereClause, String[] whereArgs){
        Table entity = newInstance(table);
        int updatedRows = 0;
        updatedRows = sqlDatabase.update(entity.getTableName(), contentValues, whereClause, whereArgs);
        return updatedRows;
    }

    // Custom delete method
    public void deleteAll(Class<? extends Table> table){
        Table entity = newInstance(table);
        sqlDatabase.execSQL("DELETE FROM "+entity.getTableName());

    }

    public int delete(Class<? extends Table> table, ContentValues contentValues, String whereClause, String[] whereArgs){
        Table entity = newInstance(table);
        int deletedRows = 0;
        deletedRows = sqlDatabase.update(entity.getTableName(), contentValues,whereClause, whereArgs);
        return deletedRows;
    }

    public Cursor select(Class<? extends Table> table, String[]columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy )
    {
        Table entity = newInstance(table);
        Cursor cursor = sqlDatabase.query(entity.getTableName(), entity.getColumnNames(), selection, selectionArgs, groupBy, having, orderBy);
        return cursor;
    }

    public Cursor select(Class<? extends Table> table, String selection, String[] selectionArgs, String groupBy, String having, String orderBy){
        Table entity = newInstance(table);

        Cursor cursor = sqlDatabase.query(entity.getTableName(), entity.getColumnNames(), selection, selectionArgs, groupBy, having, orderBy);

        return cursor;
    }

    public int updateStatus(String uri, String table, ContentValues cv){
        int updatedRows=sqlDatabase.update(table, cv,"URI=?",new String[]{uri});
        return updatedRows;
    }

    private Table newInstance(java.lang.Class<? extends Table> entity){
        try {
            Table obj =  entity.newInstance();
            return obj;
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

}
