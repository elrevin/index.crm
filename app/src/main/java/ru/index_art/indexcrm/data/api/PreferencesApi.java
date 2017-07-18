package ru.index_art.indexcrm.data.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import ru.index_art.indexcrm.App;

public class PreferencesApi {
    public static final PreferencesApi INSTANCE = new PreferencesApi();

    private SharedPreferences pref;

    private Context appContext;

    private String login, password, token, userId;

    public PreferencesApi() {
        appContext = App.getContext();
        pref = PreferenceManager.getDefaultSharedPreferences(appContext);

        login = pref.getString("login", null);
        password = pref.getString("password", null);
        token = pref.getString("token", null);
        userId = pref.getString("userId", null);
    }

    public String getLogin() {
        return login;
    }

    public String getPassword () {
        return password;
    }

    public String getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }

    public void setLogin(String _login) {
        login = _login;
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("login", login);
        editor.apply();
        editor.commit();
    }

    public void setPassword(String _password) {
        password = _password;
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("password", _password);
        editor.apply();
        editor.commit();
    }

    public void setToken(String _token) {
        token = _token;
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("token", _token);
        editor.apply();
        editor.commit();
    }

    public void setUserId(String _id) {
        userId = _id;
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("userId", _id);
        editor.apply();
        editor.commit();
    }
}
