package ru.index_art.indexcrm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import ru.index_art.indexcrm.server_api.Api;
import rx.Observable;

public class EnterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);

        Observable<Boolean> req = Api.INSTANCE.checkLoginAndPassword("fff", "rrr");
        req.subscribe(res -> {
            Log.d("ML", res.toString());
        });
    }
}
