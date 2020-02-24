package com.gmail.wondergab12.bank.repository.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class BankOpenHelper extends SQLiteOpenHelper implements DatabaseHelper {

    private static final String DATABASE_NAME = "bank.db";
    private static final int DATABASE_VERSION = 2;

    private static DatabaseHelper instance;

    public static DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new BankOpenHelper(context);
        }

        return instance;
    }

    private DatabaseDao databaseDao;

    private BankOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        databaseDao = new AtmsDao(getWritableDatabase());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AtmsDao.SQL_CREATE_ATMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(AtmsDao.SQL_DROP_TABLE);
        onCreate(db);
    }

    @Override
    public DatabaseDao databaseHelper() {
        return databaseDao;
    }

}
