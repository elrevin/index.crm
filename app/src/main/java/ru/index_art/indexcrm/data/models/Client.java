package ru.index_art.indexcrm.data.models;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class Client implements RealmModel {
    @PrimaryKey
    public int id;
    public String name;
    public String groupName;
    public String phone;
}
