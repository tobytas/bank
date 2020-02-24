package com.gmail.wondergab12.bank.repository.database;

import java.util.Optional;

public interface DatabaseDao {

    long createRecord(String city, String currency, String response);

    Optional<DatabaseResponse> readRecord(String city, String currency);

}
