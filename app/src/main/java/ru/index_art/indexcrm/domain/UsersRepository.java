package ru.index_art.indexcrm.domain;

import ru.index_art.indexcrm.data.api.PreferencesApi;
import ru.index_art.indexcrm.data.api.ServerApi;
import rx.Observable;

public class UsersRepository {
    private String login;
    private String password;

    public static final UsersRepository INSTANCE = new UsersRepository();

    public Observable<String> isLogin() {
        login = PreferencesApi.INSTANCE.getLogin();
        password = PreferencesApi.INSTANCE.getPassword();
        Observable<String> ret = ServerApi.INSTANCE.checkCommonLoginAndPassword(login, password);
        ret.map(ansv -> {
            if (ansv.equals("commonLoginOk")) {
                return "commonLoginOk";
            } else {
                return ansv;
            }
        });
        return ret;
    }

    public Observable<String> setLoginAndPassword(String _login, String _password) {
        login = _login;
        PreferencesApi.INSTANCE.setLogin(_login);
        password = _password;
        PreferencesApi.INSTANCE.setPassword(_password);

        return ServerApi.INSTANCE.checkCommonLoginAndPassword(_login, _password);
    }

}
