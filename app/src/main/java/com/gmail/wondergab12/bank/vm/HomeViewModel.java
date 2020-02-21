package com.gmail.wondergab12.bank.vm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gmail.wondergab12.bank.consts.Consts;
import com.gmail.wondergab12.bank.model.Atm;
import com.gmail.wondergab12.bank.model.JsonParser;
import com.gmail.wondergab12.bank.repository.Repository;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<Atm>> atms;
    private MutableLiveData<Throwable> errs;

    private Repository repository;

    public HomeViewModel(Repository repository) {
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

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void getAtmList(String city, String currency) {
        OkHttpClient client = new OkHttpClient();
        String url = String.format(Consts.ATMS_URL, city, currency);
        Request request = new Request.Builder().url(url).build();

        /*
        CustomCallback callback = new CustomCallback(exception -> errs.postValue(exception),
                atmsList -> atms.postValue(atmsList));
        client.newCall(request).enqueue(callback);
         */
        Observable<Response> observable = Observable.create(emitter ->
                emitter.onNext(client.newCall(request).execute()));
        observable.map(Response::body)
                .map(ResponseBody::string)
                .map(responseString -> repository.insertResponse(city, currency, responseString))
                .map(longValue -> repository.readResponse(city, currency))
                .map(JsonParser::getAtms)
                .subscribeOn(Schedulers.io())
                .subscribe(list -> atms.postValue(list), err -> errs.postValue(err));
    }

}
