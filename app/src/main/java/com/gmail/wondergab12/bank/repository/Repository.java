package com.gmail.wondergab12.bank.repository;

import com.gmail.wondergab12.bank.repository.database.DatabaseResponse;

import java.util.Optional;

public interface Repository {

    long insertResponse(String city, String currency, String response);

    Optional<DatabaseResponse> readResponse(String city, String currency);

}
