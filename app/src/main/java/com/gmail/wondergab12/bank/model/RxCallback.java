package com.gmail.wondergab12.bank.model;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class RxCallback extends Observable<List<Atm>> implements Callback {

    private Observer<? super List<Atm>> observer;

    @Override
    protected void subscribeActual(@NonNull Observer<? super List<Atm>> observer) {
        this.observer = observer;
    }

    @Override
    public void onFailure(@NotNull Call call, @NotNull IOException e) {
        observer.onError(e);
    }

    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        ResponseBody responseBody = response.body();
        if (responseBody != null) {
            String responseString = responseBody.string();
            List<Atm> list = JsonParser.getAtms(responseString);
            observer.onNext(list);
        }
        observer.onComplete();
    }

}
