package net.manhica.maltem.react.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by afelimone on 6/4/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String CREATE_TABLE_CASE ="CREATE TABLE " +
            Database.Case.TABLE_NAME+" ("+
            Database.Case.COLUMN_NAME+" text," +
            Database.Case.COLUMN_SURNAME+" text," +
            Database.Case.COLUMN_STATUS+" integer," +
            Database.Case.COLUMN_INDEX_UUID+" text," +
            Database.Case.COLUMN_PERMID+" text," +
            Database.Case.COLUMN_HOUSEHOLD_UUID+" text," +
            Database.Case.COLUMN_MEMBER_UUID+" text," +
            Database.Case.COLUMN_LAST_VISIT_DATE+" text," +
            Database.Case.COLUMN_MOTHER_NAME+" text," +
            Database.Case.COLUMN_MOTHER_SURNAME+" text," +
            Database.Case.COLUMN_STREET+" text," +
            Database.Case.COLUMN_VILLAGE+" text," +
            Database.Case.COLUMN_LOCALITY+" text," +
            Database.Case.COLUMN_ADMINISTRATIVE_POST+" text," +
            Database.Case.COLUMN_PHONE+" text," +
            Database.Case.COLUMN_NEIGHBOR_PHONE+" text," +
            Database.Case.COLUMN_HEAD_PHONE+" text," +
            Database.Case.COLUMN_HOUSE_NEXT_TO+" text)";

    public static final String CREATE_TABLE_MEMBER = "CREATE TABLE " +
            Database.Member.TABLE_NAME+" (" +
            Database.Member.COLUMN_NAME+" text," +
            Database.Member.COLUMN_SURNAME+" text," +
            Database.Member.COLUMN_MEMBER_UUID+" text," +
            Database.Member.COLUMN_STATUS+" integer," +
            Database.Member.COLUMN_HOUSEHOLD_UUID+" text," +
            Database.Member.COLUMN_PERMID+" text," +
            Database.Member.COLUMN_VISIT_DATE+" text)";

    public static final String CREATE_TABLE_HOUSEHOLD = "CREATE TABLE " +
            Database.Household.TABLE_NAME+" (" +
            Database.Household.COLUMN_HOUSEHOLD_UUID+" text," +
            Database.Household.COLUMN_HOUSEHOLD_NUMBER+" text," +
            Database.Household.COLUMN_HOUSEHOLD_HEAD+" text," +
            Database.Household.COLUMN_LAST_VISIT_DATE+" text," +
            Database.Household.COLUMN_STATUS+" integer)";

    public static final String DROP_TABLE_MEMBER = "DROP TABLE IF EXISTS "+Database.Member.TABLE_NAME;
    public static final String DROP_TABLE_CASE = "DROP TABLE IF EXISTS "+Database.Case.TABLE_NAME;
    public static final String DROP_TABLE_HOUSEHOLD = "DROP TABLE IF EXISTS "+Database.Household.TABLE_NAME;

    public DatabaseHelper(Context context){
        super(context,Database.DATABASE_NAME, null, Database.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL(CREATE_TABLE_CASE);
            db.execSQL(CREATE_TABLE_HOUSEHOLD);
            db.execSQL(CREATE_TABLE_MEMBER);
        }
        catch (SQLiteException ex){
            ex.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try{
            Log.d("upgrade", "called");
            db.execSQL(DROP_TABLE_CASE);
            db.execSQL(DROP_TABLE_HOUSEHOLD);
            db.execSQL(DROP_TABLE_MEMBER);
            db.execSQL(CREATE_TABLE_CASE);
            db.execSQL(CREATE_TABLE_HOUSEHOLD);
            db.execSQL(CREATE_TABLE_MEMBER);
        }
        catch (SQLiteException ex){
            ex.printStackTrace();
        }
    }
}
