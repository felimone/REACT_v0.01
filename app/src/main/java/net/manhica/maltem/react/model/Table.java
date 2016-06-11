package net.manhica.maltem.react.model;

import android.content.ContentValues;

/**
 * Created by afelimone on 6/4/2016.
 */
public interface Table {
    public String getTableName();
    public ContentValues getContentValues();
    public String[] getColumnNames();
}
