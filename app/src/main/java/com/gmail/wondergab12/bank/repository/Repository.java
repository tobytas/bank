package com.gmail.wondergab12.bank.repository;

public interface Repository {

    long insertResponse(String city, String currency, String response);

    String readResponse(String city, String currency);

}
