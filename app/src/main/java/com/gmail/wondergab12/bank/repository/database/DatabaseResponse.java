package com.gmail.wondergab12.bank.repository.database;

public class DatabaseResponse {

    private long date;
    private String stringResponse;

    public DatabaseResponse(long date, String stringResponse) {
        this.date = date;
        this.stringResponse = stringResponse;
    }

    public long getDate() {
        return date;
    }

    public String getStringResponse() {
        return stringResponse;
    }

}
