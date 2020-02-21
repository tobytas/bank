package com.gmail.wondergab12.bank.repository.database;

public interface DatabaseDao {

    long createRecord(String city, String currency, String response);

    String readRecord(String city, String currency);

}
