package ru.index_art.indexcrm.activities.enter;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.zip.Inflater;

import ru.index_art.indexcrm.R;
import ru.index_art.indexcrm.activities.enter.fragments.CommonPasswordRequestFragment;
import ru.index_art.indexcrm.activities.enter.fragments.PersonalLoginAndPasswordRequestFragment;
import ru.index_art.indexcrm.activities.main.MainActivity;
import ru.index_art.indexcrm.data.models.Model;
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
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Проверяем общий пароль");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();


        Observable<String> req = UsersRepository.INSTANCE.checkCommonLoginAndPassword();
        req.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> {
                    dialog.hide();
                    dialog.dismiss();
                    switch (res) {
                        case "commonLoginIncorrect":
                            // Common login or password are incorrect, let's ask them from user
                            setCommonLoginAndPasswordRequestFragment();
                            break;
                        case "commonLoginOk":
                            // User not found, let's ask the user for his login and password
                            checkTocken();
                            break;
                        case "networkError":
                            Toast.makeText(this, "Похоже отсутствует подключение к интернету. Или сервер не доступен.", Toast.LENGTH_LONG).show();
                            break;
                    }
                });
    }

    private void checkTocken() {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Проверяем авторизацию");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        UsersRepository.INSTANCE.checkToken()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ansv -> {
                    dialog.hide();
                    dialog.dismiss();
                    if (ansv.status) {
                        startMainActivity();
                    } else if (ansv.error.equals("commonLoginIncorrect")) {
                        setCommonLoginAndPasswordRequestFragment();
                    } else if (ansv.error.equals("networkError")) {
                        Toast.makeText(this, "Похоже отсутствует подключение к интернету. Или сервер не доступен.", Toast.LENGTH_LONG).show();
                    } else {
                        setPersonalLoginAndPasswordRequestFragment();
                    }
                });
    }

    void setCommonLoginAndPasswordRequestFragment() {
        CommonPasswordRequestFragment commonPasswordRequestFragment = new CommonPasswordRequestFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.flEnterActivityFragmentsCont, commonPasswordRequestFragment);
        ft.commit();
    }

    void setPersonalLoginAndPasswordRequestFragment() {
        PersonalLoginAndPasswordRequestFragment personalLoginAndPasswordRequestFragment = new PersonalLoginAndPasswordRequestFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.flEnterActivityFragmentsCont, personalLoginAndPasswordRequestFragment);
        ft.commit();
    }

    public void onCommonLoginAndPasswordSet() {
        setPersonalLoginAndPasswordRequestFragment();
    }

    public void onTokenSet() {
        startMainActivity();
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}
