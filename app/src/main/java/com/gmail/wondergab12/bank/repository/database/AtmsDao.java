package com.gmail.wondergab12.bank.repository.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AtmsDao implements DatabaseDao {

    private static final String TABLE_NAME = "atms_table";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_CITY = "city";
    private static final String COLUMN_CURRENCY = "currency";
    private static final String COLUMN_RESPONSE = "response";

    static final String SQL_CREATE_ATMS_TABLE = String.format(
            "CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "UNIQUE (%s, %s) ON CONFLICT REPLACE)",
            TABLE_NAME,
            COLUMN_ID,
            COLUMN_CITY,
            COLUMN_CURRENCY,
            COLUMN_RESPONSE,
            COLUMN_CITY, COLUMN_CURRENCY);

    private SQLiteDatabase db;

    AtmsDao(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public long createRecord(String city, String currency, String response) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CITY, city);
        contentValues.put(COLUMN_CURRENCY, currency);
        contentValues.put(COLUMN_RESPONSE, response);

        return db.insert(TABLE_NAME, null, contentValues);
    }

    @Override
    public String readRecord(String city, String currency) {
        String response = null;
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_RESPONSE},
                String.format("%s = ? AND %s = ?", COLUMN_CITY, COLUMN_CURRENCY),
                new String[]{city, currency}, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int indexResponse = cursor.getColumnIndex(COLUMN_RESPONSE);
                response = cursor.getString(indexResponse);
            }
            cursor.close();
        }

        return response;
    }

}
