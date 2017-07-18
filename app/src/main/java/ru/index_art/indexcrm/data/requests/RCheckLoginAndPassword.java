package ru.index_art.indexcrm.data.requests;

import retrofit2.http.GET;
import retrofit2.http.Header;
import ru.index_art.indexcrm.data.requests.SACheckLoginAndPassword;
import rx.Observable;

public interface RCheckLoginAndPassword {
    @GET("api.php?module=testlogin")
    Observable<SACheckLoginAndPassword> request(@Header("Authorization") String authorization);
}
