package ru.index_art.indexcrm.data.requests;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import rx.Observable;

public interface RCheckToken {
    @GET("api.php?module=users&method=auth")
    Observable<SACheckToken> request(@Header("Authorization") String authorization, @Query("token") String token, @Query("user_id") String usetId);
}
