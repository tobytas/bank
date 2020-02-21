package com.gmail.wondergab12.bank.repository.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class BankOpenHelper extends SQLiteOpenHelper implements DatabaseHelper {

    private static final String DATABASE_NAME = "bank.db";
    private static final int DATABASE_VERSION = 1;

    private static BankOpenHelper instance;

    public static DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new BankOpenHelper(context);
        }

        return instance;
    }

    private AtmsDao atmsDao;

    private BankOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        atmsDao = new AtmsDao(getWritableDatabase());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AtmsDao.SQL_CREATE_ATMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public DatabaseDao databaseHelper() {
        return atmsDao;
    }

}
