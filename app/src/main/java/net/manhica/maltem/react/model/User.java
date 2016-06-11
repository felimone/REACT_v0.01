package net.manhica.maltem.react.model;

import android.content.ContentValues;

/**
 * Created by afelimone on 6/4/2016.
 */
public class User implements Table{
    private String username;
    private String password;
    private String name;
    private String type;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getTableName() {
        return null;
    }

    @Override
    public ContentValues getContentValues() {
        return null;
    }

    @Override
    public String[] getColumnNames() {
        return new String[0];
    }
}
