package ru.index_art.indexcrm.data.requests;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import retrofit2.http.QueryName;
import rx.Observable;

public interface RGetTokenByLoginAndPasword {
    @GET("api.php?module=users&method=createTokenByLogin")
    Observable<SAGetTokenByLoginAndPasword> request(@Header("Authorization") String authorization, @Query("login") String login, @Query("password") String password);
}
