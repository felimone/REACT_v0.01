package net.manhica.maltem.react.model;

import android.content.ContentValues;
import android.provider.ContactsContract;

import net.manhica.maltem.react.database.Database;

/**
 * Created by afelimone on 6/4/2016.
 */
public class Case implements Table{

    private String index;
    private String memberUuid;
    private String householdUuid;
    private String permid;
    private int status;
    private String householdNumber;
    private String name;
    private String surname;
    private String dob;
    private String gender;
    private String motherName;
    private String motherSurname;
    private String street;
    private String administrativePost;
    private String locality;
    private String village;
    private String otherLocationInfo;
    private String houseNextTo;
    private String phone;
    private String headPhone;
    private String neighborPhone;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getMemberUuid() {
        return memberUuid;
    }

    public void setMemberUuid(String memberUuid) {
        this.memberUuid = memberUuid;
    }

    public String getHouseholdUuid() {
        return householdUuid;
    }

    public void setHouseholdUuid(String householdUuid) {
        this.householdUuid = householdUuid;
    }

    public String getPermid() {
        return permid;
    }

    public void setPermid(String permid) {
        this.permid = permid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getHouseholdNumber() {
        return householdNumber;
    }

    public void setHouseholdNumber(String householdNumber) {
        this.householdNumber = householdNumber;
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


    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getMotherSurname() {
        return motherSurname;
    }

    public void setMotherSurname(String motherSurname) {
        this.motherSurname = motherSurname;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAdministrativePost() {
        return administrativePost;
    }

    public void setAdministrativePost(String administrativePost) {
        this.administrativePost = administrativePost;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getOtherLocationInfo() {
        return otherLocationInfo;
    }

    public void setOtherLocationInfo(String otherLocationInfo) {
        this.otherLocationInfo = otherLocationInfo;
    }

    public String getHouseNextTo() {
        return houseNextTo;
    }

    public void setHouseNextTo(String houseNextTo) {
        this.houseNextTo = houseNextTo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHeadPhone() {
        return headPhone;
    }

    public void setHeadPhone(String headPhone) {
        this.headPhone = headPhone;
    }

    public String getNeighborPhone() {
        return neighborPhone;
    }

    public void setNeighborPhone(String neighborPhone) {
        this.neighborPhone = neighborPhone;
    }

    @Override
    public String getTableName() {
        return Database.Case.TABLE_NAME;
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(Database.Case.COLUMN_INDEX_UUID, index);
        cv.put(Database.Case.COLUMN_HOUSEHOLD_UUID, householdUuid);
        cv.put(Database.Case.COLUMN_MEMBER_UUID, memberUuid);
        cv.put(Database.Case.COLUMN_NAME, name);
        cv.put(Database.Case.COLUMN_PERMID, permid);
        cv.put(Database.Case.COLUMN_STATUS, status);
        cv.put(Database.Case.COLUMN_SURNAME, surname);
        cv.put(Database.Case.COLUMN_PHONE, phone);
        cv.put(Database.Case.COLUMN_NEIGHBOR_PHONE, neighborPhone);
        cv.put(Database.Case.COLUMN_LOCALITY, locality);
        cv.put(Database.Case.COLUMN_ADMINISTRATIVE_POST, administrativePost);
        cv.put(Database.Case.COLUMN_VILLAGE, village);
        cv.put(Database.Case.COLUMN_MOTHER_NAME, motherName);
        cv.put(Database.Case.COLUMN_MOTHER_SURNAME, motherSurname);
        cv.put(Database.Case.COLUMN_STREET, street);
        cv.put(Database.Case.COLUMN_HOUSE_NEXT_TO, houseNextTo);
        cv.put(Database.Case.COLUMN_DOB, dob);
        cv.put(Database.Case.COLUMN_HEAD_PHONE, headPhone);

        return null;
    }


    @Override

    public String[] getColumnNames() {
        return Database.Case.ALL_COLUMNS;
    }
}
