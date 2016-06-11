package net.manhica.maltem.react.model;

import android.content.ContentValues;

import net.manhica.maltem.react.database.Database;

/**
 * Created by afelimone on 6/4/2016.
 */
public class Household implements Table {
    private String householdUuid;
    private String householdHead;
    private String householdNumber;
    private int status;
    private String lastVisitDate;

    public String getHouseholdUuid() {
        return householdUuid;
    }

    public void setHouseholdUuid(String householdUuid) {
        this.householdUuid = householdUuid;
    }

    public String getHouseholdHead() {
        return householdHead;
    }

    public void setHouseholdHead(String householdHead) {
        this.householdHead = householdHead;
    }

    public String getHouseholdNumber() {
        return householdNumber;
    }

    public void setHouseholdNumber(String householdNumber) {
        this.householdNumber = householdNumber;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLastVisitDate() {
        return lastVisitDate;
    }

    public void setLastVisitDate(String lastVisitDate) {
        this.lastVisitDate = lastVisitDate;
    }

    @Override
    public String getTableName() {
        return Database.Household.TABLE_NAME;
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(Database.Household.COLUMN_HOUSEHOLD_UUID, householdUuid);
        cv.put(Database.Household.COLUMN_HOUSEHOLD_HEAD, householdHead);
        cv.put(Database.Household.COLUMN_HOUSEHOLD_NUMBER, householdNumber);
        cv.put(Database.Household.COLUMN_LAST_VISIT_DATE, lastVisitDate);
        cv.put(Database.Household.COLUMN_STATUS, status);
        return cv;
    }

    @Override
    public String[] getColumnNames() {
        return Database.Household.ALL_COLUMNS;
    }
}
