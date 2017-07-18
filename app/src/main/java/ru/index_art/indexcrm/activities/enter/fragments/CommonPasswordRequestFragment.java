package ru.index_art.indexcrm.activities.enter.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.logging.Logger;

import ru.index_art.indexcrm.R;
import ru.index_art.indexcrm.activities.enter.EnterActivity;
import ru.index_art.indexcrm.domain.UsersRepository;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommonPasswordRequestFragment extends Fragment {

    public EnterActivity enterActivity;

    public CommonPasswordRequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_common_password_request, container, false);

        ((Button) view.findViewById(R.id.btnSaveCommonLoginAndPassword)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSaveCommonLoginAndPasswordClick();
            }
        });

        enterActivity = (EnterActivity) (this.getActivity());

        return view;
    }


    public void btnSaveCommonLoginAndPasswordClick() {
        String login = ((EditText) getView().findViewById(R.id.edtCommonLogin)).getText().toString();
        String password = ((EditText) getView().findViewById(R.id.edtCommonPassword)).getText().toString();

        Observable<String> res = UsersRepository.INSTANCE.setLoginAndPassword(login, password);

        res.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ansv -> {
                    if (ansv.equals("commonLoginOk")) {
                        Toast.makeText(enterActivity, "All OK", Toast.LENGTH_LONG).show();
                    } else if (ansv.equals("networkError")) {
                        Toast.makeText(enterActivity, "Похоже отсутствует подключение к интернету. Или сервер не доступен.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(enterActivity, "Логин или пароль введены не правильно", Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        enterActivity.onCommonPasswordRequestFragmentResume();
    }
}
