package ru.index_art.indexcrm.server_api;

import retrofit2.http.GET;
import retrofit2.http.Header;
import ru.index_art.indexcrm.models.SACheckLoginAndPassword;
import rx.Observable;

public interface RCheckLoginAndPassword {
    @GET("api.php?module=testlogin")
    Observable<SACheckLoginAndPassword> request(@Header("Authorization") String authorization);
}
