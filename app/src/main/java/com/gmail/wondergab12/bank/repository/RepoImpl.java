package com.gmail.wondergab12.bank.repository;

import com.gmail.wondergab12.bank.repository.database.DatabaseDao;
import com.gmail.wondergab12.bank.repository.database.DatabaseResponse;

import java.util.Optional;

public class RepoImpl implements Repository {

    private DatabaseDao databaseDao;

    public RepoImpl(DatabaseDao databaseDao) {
        this.databaseDao = databaseDao;
    }

    @Override
    public long insertResponse(String city, String currency, String response) {
        return databaseDao.createRecord(city, currency, response);
    }

    @Override
    public Optional<DatabaseResponse> readResponse(String city, String currency) {
        return databaseDao.readRecord(city, currency);
    }

}
