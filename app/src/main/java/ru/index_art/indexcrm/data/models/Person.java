package ru.index_art.indexcrm.data.models;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class Person implements RealmModel {
    @PrimaryKey
    public int id;
    public String name;
    public String position;
    public String phone;
}
