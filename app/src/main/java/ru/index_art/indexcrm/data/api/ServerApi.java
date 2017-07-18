package ru.index_art.indexcrm.data.api;

import android.util.Base64;
import android.util.Log;

import java.io.IOException;

import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.index_art.indexcrm.data.requests.RCheckLoginAndPassword;
import ru.index_art.indexcrm.data.requests.SACheckLoginAndPassword;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ServerApi {

    public static final ServerApi INSTANCE = new ServerApi();

    private String getBasicAuthString(String login, String password) {
        return "Basic " + Base64.encodeToString(String.format("%s:%s", login, password).getBytes(), Base64.NO_WRAP);
    }

    public Observable<String> checkCommonLoginAndPassword(String login, String password) {

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://self.index-crm.ru/tree/api/")
                .build();

        RCheckLoginAndPassword request = retrofit.create(RCheckLoginAndPassword.class);
        Observable<SACheckLoginAndPassword> result = request.request(getBasicAuthString(login, password));
        Observable<String> toReturn = result.map(res -> {
                    if (res.getStatus()) {
                        return "commonLoginOk";
                    }
                    return "commonLoginIncorrect";
                })
                .onErrorReturn(error -> {
                    if (error instanceof HttpException) {
                        int code = ((HttpException) error).code();

                        Log.e("index.art", "Http error: " + Integer.toString(code));
                        Log.e("index.art", "Http error: " + ((HttpException) error).message());

                        if (code == 401) {
                            return "commonLoginIncorrect";
                        }
                    }

                    if (error instanceof IOException) {
                        Log.e("index.art", "IO error: " + ((IOException) error).getMessage());
                    }
                    Log.e("index.art", "HZ error: " + error.getMessage());
                    return "networkError";
                });
        return toReturn;
    }
}
