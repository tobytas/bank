package com.gmail.wondergab12.bank.model;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class CustomCallback implements Callback {

    private Consumer<IOException> onException;
    private Consumer<List<Atm>> onResponse;

    public CustomCallback(Consumer<IOException> onException, Consumer<List<Atm>> onResponse) {
        this.onException = onException;
        this.onResponse = onResponse;
    }

    @Override
    public void onFailure(@NotNull Call call, @NotNull IOException e) {
        onException.accept(e);
    }

    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        ResponseBody responseBody = response.body();
        if (responseBody != null) {
            String responseString = responseBody.string();
            List<Atm> list = JsonParser.getAtms(responseString);
            onResponse.accept(list);
        }
    }

}
