package com.gmail.wondergab12.bank.vm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gmail.wondergab12.bank.consts.Consts;
import com.gmail.wondergab12.bank.model.Atm;
import com.gmail.wondergab12.bank.model.JsonParse;
import com.gmail.wondergab12.bank.repository.RepoImpl;
import com.gmail.wondergab12.bank.repository.database.DatabaseResponse;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HomeViewModel extends ViewModel {

    // Refresh cache every 21 day
    private static long REFRESH_INTERVAL = TimeUnit.DAYS.toMillis(21);

    private MutableLiveData<List<Atm>> atms;
    private MutableLiveData<Throwable> errs;

    private Disposable disposable;
    private RepoImpl repository;

    public HomeViewModel(RepoImpl repository) {
        this.repository = repository;
    }

    public LiveData<List<Atm>> getAtms() {
        if (atms == null) {
            atms = new MutableLiveData<>();
        }

        return atms;
    }

    public LiveData<Throwable> getErrs() {
        if (errs == null) {
            errs = new MutableLiveData<>();
        }

        return errs;
    }

    public void getAtmList(String city, String currency) {
        /*
        Observable<Response> observable = Observable.create(emitter ->
                emitter.onNext(client.newCall(request).execute()));
        observable.map(Response::body)
                .map(ResponseBody::string)
                .map(responseString -> repository.insertResponse(city, currency, responseString))
                .map(longValue -> repository.readResponse(city, currency))
                .map(JsonParser::getAtms)
                .subscribeOn(Schedulers.io())
                .subscribe(list -> atms.postValue(list), err -> errs.postValue(err));
         */
        /*  First check the cache
         *  if it is empty: make request , write to cache
         *  and take from it
         *  otherwise: simply take from cache
         */
        Observable<Optional<DatabaseResponse>> observable = Observable.create(emitter ->
                emitter.onNext(repository.readResponse(city, currency)));
        disposable = observable.flatMap((Function<Optional<DatabaseResponse>,
                ObservableSource<Optional<DatabaseResponse>>>) optionalDatabaseResponse -> {
            if (!optionalDatabaseResponse.isPresent() || Calendar.getInstance()
                    .getTimeInMillis() - optionalDatabaseResponse.get().getDate() > REFRESH_INTERVAL) {
                return Observable.create((ObservableOnSubscribe<Response>) emitter ->
                        emitter.onNext(getNetworkResponse(city, currency)))
                        .map(Response::body)
                        .map(ResponseBody::string)
                        .map(responseString -> repository.insertResponse(city, currency, responseString))
                        .map(longValue -> repository.readResponse(city, currency));
            } else {
                return Observable.create(emitter -> emitter.onNext(optionalDatabaseResponse));
            }
        }).map(Optional::get)
                .map(DatabaseResponse::getStringResponse)
                .map(JsonParse.Companion::getAtms)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> atms.setValue(list), err -> errs.setValue(err));
    }

    private Response getNetworkResponse(String city, String currency) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String url = String.format(Consts.ATMS_URL, city, currency);
        Request request = new Request.Builder().url(url).build();

        return client.newCall(request).execute();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }

}
