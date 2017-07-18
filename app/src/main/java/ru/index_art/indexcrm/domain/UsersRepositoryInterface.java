package ru.index_art.indexcrm.domain;

import ru.index_art.indexcrm.data.models.LoginAndPassword;
import rx.Observable;

public interface UsersRepositoryInterface {
    Observable<Boolean> checkCommonLoginAndPassword(String login, String password);
    Observable<LoginAndPassword> getCommonLoginAndPasswordFromPreferences();
}
