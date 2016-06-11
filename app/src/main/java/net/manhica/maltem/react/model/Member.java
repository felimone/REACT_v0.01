package net.manhica.maltem.react.model;

import android.content.ContentValues;

import net.manhica.maltem.react.database.Database;

/**
 * Created by afelimone on 6/4/2016.
 */
public class Member implements Table {
    private String memberUuid;
    private String permid;
    private String householdNumber;
    private String householdUuid;
    private int status;
    private String name;
    private String surname;
    private String visitDate;

    public String getMemberUuid() {

        return memberUuid;
    }

    public void setMemberUuid(String memberUuid) {
        this.memberUuid = memberUuid;
    }

    public String getPermid() {
        return permid;
    }

    public void setPermid(String permid) {
        this.permid = permid;
    }

    public String getHouseholdNumber() {
        return householdNumber;
    }

    public void setHouseholdNumber(String householdNumber) {
        this.householdNumber = householdNumber;
    }

    public String getHouseholdUuid() {
        return householdUuid;
    }

    public void setHouseholdUuid(String householdUuid) {
        this.householdUuid = householdUuid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    @Override
    public String getTableName() {
        return Database.Member.TABLE_NAME;
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(Database.Member.COLUMN_HOUSEHOLD_UUID, householdUuid);
        cv.put(Database.Member.COLUMN_MEMBER_UUID, memberUuid);
        cv.put(Database.Member.COLUMN_NAME, name);
        cv.put(Database.Member.COLUMN_SURNAME, surname);
        cv.put(Database.Member.COLUMN_STATUS, status);
        cv.put(Database.Member.COLUMN_PERMID, permid);
        cv.put(Database.Member.COLUMN_VISIT_DATE, visitDate);
        return cv;
    }

    @Override
    public String[] getColumnNames() {
        return Database.Member.ALL_COLUMNS;
    }
}
