package ru.index_art.indexcrm.server_api;

import android.util.Base64;
import android.util.Log;

import java.io.IOException;

import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.index_art.indexcrm.models.SACheckLoginAndPassword;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Api {

    public static final Api INSTANCE = new Api();

    private String getBasicAuthString(String login, String password) {
        return "Basic " + Base64.encodeToString(String.format("%s:%s", login, password).getBytes(), Base64.NO_WRAP);
    }

    public Observable<Boolean> checkLoginAndPassword(String login, String password) {

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://self.index-crm.ru/tree/api/")
                .build();

        RCheckLoginAndPassword request = retrofit.create(RCheckLoginAndPassword.class);
        Observable<SACheckLoginAndPassword> result = request.request(getBasicAuthString(login, password));
        Observable<Boolean> toReturn = result.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(res -> res.getStatus())
                .onErrorReturn(error -> {
                    if (error instanceof HttpException) {
                        int code = ((HttpException) error).code();

                        Log.e("index.art", "Http error: " + Integer.toString(code));
                        Log.e("index.art", "Http error: " + ((HttpException) error).message());

                        if (code == 401) {
                            return Boolean.valueOf(false);
                        }
                    }

                    if (error instanceof IOException) {
                        Log.e("index.art", "IO error: " + ((IOException) error).getMessage());
                    }
                    return null;
                });
        return toReturn;
    }
}
