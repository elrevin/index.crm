package ru.index_art.indexcrm.activities.enter.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ru.index_art.indexcrm.R;
import ru.index_art.indexcrm.activities.enter.EnterActivity;
import ru.index_art.indexcrm.data.models.GetToken;
import ru.index_art.indexcrm.domain.UsersRepository;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalLoginAndPasswordRequestFragment extends Fragment {

    private EnterActivity enterActivity;

    public PersonalLoginAndPasswordRequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal_login_and_password_request, container, false);

        enterActivity = (EnterActivity) getActivity();

        ((Button) view.findViewById(R.id.btnSavePersonalLoginAndPassword)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSavePersonalLoginAndPasswordClick();
            }
        });

        return view;
    }

    private void btnSavePersonalLoginAndPasswordClick() {
        String login = ((EditText) getView().findViewById(R.id.edtPersonalLogin)).getText().toString();
        String password = ((EditText) getView().findViewById(R.id.edtPersonalPassword)).getText().toString();

        Observable<GetToken> res = UsersRepository.INSTANCE.getTokenByLoginAndPassword(login, password);
        res.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ansv -> {
                    if (ansv.status) {
                        Toast.makeText(enterActivity, "All OK!!!!", Toast.LENGTH_LONG).show();
                    } else if (ansv.error.equals("networkError")) {
                        Toast.makeText(enterActivity, "Похоже отсутствует подключение к интернету. Или сервер не доступен.", Toast.LENGTH_LONG).show();
                    } else if (ansv.error.equals("commonLoginIncorrect")) {
                        Toast.makeText(enterActivity, "Общие логин и пароль не верны, нужно ввести их заново.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(enterActivity, ansv.error, Toast.LENGTH_LONG).show();
                    }
                });
    }
}
