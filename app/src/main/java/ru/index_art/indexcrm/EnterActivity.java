package ru.index_art.indexcrm;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import ru.index_art.indexcrm.server_api.Api;
import rx.Observable;

public class EnterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);

        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Проверяем общий пароль");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();


        Observable<Boolean> req = Api.INSTANCE.checkLoginAndPassword("index", "Troglodit26");
        req.doOnError(error -> {
        })
            .subscribe(res -> {
                dialog.hide();
                dialog.dismiss();
                if (res.booleanValue()) {

                } else {

                }
            });
    }
}
