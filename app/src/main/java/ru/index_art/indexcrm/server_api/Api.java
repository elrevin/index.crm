package ru.index_art.indexcrm.server_api;

import android.util.Base64;

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
                .map(res -> res.getStatus());
        return toReturn;
    }
}
