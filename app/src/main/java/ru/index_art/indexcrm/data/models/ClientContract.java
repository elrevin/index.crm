package ru.index_art.indexcrm.data.models;

import java.util.Date;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class ClientContract implements RealmModel {
    @PrimaryKey
    public int id;
    public String number;
    public Date date;
}
