package ru.index_art.indexcrm.activities.enter;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import retrofit2.HttpException;
import retrofit2.Retrofit;
import ru.index_art.indexcrm.R;
import ru.index_art.indexcrm.activities.enter.fragments.CommonPasswordRequestFragment;
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


        Observable<Boolean> req = Api.INSTANCE.checkLoginAndPassword("index", "Troglodit");
        req.subscribe(res -> {
                dialog.hide();
                dialog.dismiss();
                if (res != null && !res) {
                    // Не верный логин или пароль, надо запросить
                    setCommonPasswordRequestFragment();
                } else if (res == null) {
                    Toast.makeText(this, "Похоже отсутствует подключение к интернету. Или сервер не доступен.", Toast.LENGTH_LONG);
                } else {

                }
            });
    }

    void setCommonPasswordRequestFragment() {
        CommonPasswordRequestFragment commonPasswordRequestFragment = new CommonPasswordRequestFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.flEnterActivityFragmentsCont, commonPasswordRequestFragment);
        ft.commit();
    }
}
