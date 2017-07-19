package ru.index_art.indexcrm.data.api;

import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;

import java.io.IOException;

import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.index_art.indexcrm.data.models.GetToken;
import ru.index_art.indexcrm.data.models.Model;
import ru.index_art.indexcrm.data.requests.RCheckLoginAndPassword;
import ru.index_art.indexcrm.data.requests.RCheckToken;
import ru.index_art.indexcrm.data.requests.RGetTokenByLoginAndPasword;
import ru.index_art.indexcrm.data.requests.SACheckLoginAndPassword;
import ru.index_art.indexcrm.data.requests.SACheckToken;
import ru.index_art.indexcrm.data.requests.SAGetTokenByLoginAndPasword;
import rx.Observable;

public class ServerApi {

    public static final ServerApi INSTANCE = new ServerApi();

    private String getBasicAuthString(String login, String password) {
        return "Basic " + Base64.encodeToString(String.format("%s:%s", login, password).getBytes(), Base64.NO_WRAP);
    }

    private String getBasicAuthString() {
        String login = PreferencesApi.INSTANCE.getLogin();
        String password = PreferencesApi.INSTANCE.getPassword();
        return getBasicAuthString(login, password);
    }

    public Observable<String> checkCommonLoginAndPassword(String login, String password) {

        Retrofit retrofit = getRetrofit();

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

    public Observable<GetToken> getTokenByLoginAndPassword(String login, String password) {
        Retrofit retrofit = getRetrofit();

        RGetTokenByLoginAndPasword request = retrofit.create(RGetTokenByLoginAndPasword.class);
        Observable<SAGetTokenByLoginAndPasword> result = request.request(getBasicAuthString(), login, password);
        Observable<GetToken> toReturn = result
                .map(res -> {
                    GetToken retVal = new GetToken();
                    if (res.getStatus()) {
                        retVal.status = true;
                        retVal.error = "";
                        retVal.token = res.getToken();
                        retVal.userId = res.getId();
                        return retVal;
                    }
                    retVal.status = false;
                    retVal.error = res.getError();
                    return retVal;
                })
                .onErrorReturn(error -> {
                    GetToken retVal = new GetToken();
                    retVal.status = false;
                    if (error instanceof HttpException) {
                        int code = ((HttpException) error).code();

                        Log.e("index.art", "Http error: " + Integer.toString(code));
                        Log.e("index.art", "Http error: " + ((HttpException) error).message());

                        if (code == 401) {
                            retVal.error = "commonLoginIncorrect";
                        }
                    }

                    if (error instanceof IOException) {
                        Log.e("index.art", "IO error: " + ((IOException) error).getMessage());
                        retVal.error = "networkError";
                    } else {
                        Log.e("index.art", "HZ error: " + error.getMessage());
                        retVal.error = "networkError";
                    }
                    return retVal;
                });
        return toReturn;
    }

    @NonNull
    private Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://self.index-crm.ru/tree/api/")
                .build();
    }

    public Observable<Model> checkToken(String token, String userId) {
        Retrofit retrofit = getRetrofit();

        RCheckToken request = retrofit.create(RCheckToken.class);
        Observable<SACheckToken> result = request.request(getBasicAuthString(), token, userId);
        return result
                .map(res -> {
                    Model retVal = new Model();
                    if (res.getStatus()) {
                        retVal.status = true;
                        retVal.error = "";
                        return retVal;
                    }
                    retVal.status = false;
                    retVal.error = res.getError();
                    return retVal;
                })
                .onErrorReturn(error -> {
                    Model retVal = new Model();
                    retVal.status = false;
                    if (error instanceof HttpException) {
                        int code = ((HttpException) error).code();

                        Log.e("index.art", "Http error: " + Integer.toString(code));
                        Log.e("index.art", "Http error: " + ((HttpException) error).message());

                        if (code == 401) {
                            retVal.error = "commonLoginIncorrect";
                        }
                    }

                    if (error instanceof IOException) {
                        Log.e("index.art", "IO error: " + ((IOException) error).getMessage());
                        retVal.error = "networkError";
                    } else {
                        Log.e("index.art", "HZ error: " + error.getMessage());
                        retVal.error = "networkError";
                    }
                    return retVal;
                });
    }
}
