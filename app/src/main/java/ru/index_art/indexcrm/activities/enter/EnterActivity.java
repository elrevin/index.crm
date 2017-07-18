package ru.index_art.indexcrm.activities.enter;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import ru.index_art.indexcrm.R;
import ru.index_art.indexcrm.activities.enter.fragments.CommonPasswordRequestFragment;
import ru.index_art.indexcrm.data.api.ServerApi;
import ru.index_art.indexcrm.domain.UsersRepository;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class EnterActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        toolbar = (Toolbar) findViewById(R.id.tlbEnterActivity);
        if (toolbar != null) {
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
            setSupportActionBar(toolbar);
        }

        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Проверяем общий пароль");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();


        Observable<String> req = UsersRepository.INSTANCE.isLogin();
        req.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> {
                    dialog.hide();
                    dialog.dismiss();
                    switch (res) {
                        case "commonLoginIncorrect":
                            // Common login or password are incorrect, let's ask them from user
                            setCommonPasswordRequestFragment();
                            break;
                        case "userNotFound":
                            // User not found, let's ask the user for his login and password

                            break;
                        case "networkError":
                            Toast.makeText(this, "Похоже отсутствует подключение к интернету. Или сервер не доступен.", Toast.LENGTH_LONG).show();
                            break;
                    }
                });
    }

    void setCommonPasswordRequestFragment() {
        hideToolbar();
        CommonPasswordRequestFragment commonPasswordRequestFragment = new CommonPasswordRequestFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.flEnterActivityFragmentsCont, commonPasswordRequestFragment);
        ft.commit();
    }

    void hideToolbar() {
        if (toolbar != null) {
            toolbar.setVisibility(View.GONE);
        }
    }

    void showToolbar() {
        if (toolbar != null) {
            toolbar.setVisibility(View.INVISIBLE);
        }
    }
}
