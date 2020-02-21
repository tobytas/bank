package com.gmail.wondergab12.bank.repository;

import android.content.Context;

import com.gmail.wondergab12.bank.repository.database.BankOpenHelper;
import com.gmail.wondergab12.bank.repository.database.DatabaseDao;

public class RepoImpl implements Repository {

    private DatabaseDao databaseDao;

    public RepoImpl(Context context) {
        databaseDao = BankOpenHelper.getInstance(context).databaseHelper();
    }

    @Override
    public long insertResponse(String city, String currency, String response) {
        return databaseDao.createRecord(city, currency, response);
    }

    @Override
    public String readResponse(String city, String currency) {
        return databaseDao.readRecord(city, currency);
    }

}
