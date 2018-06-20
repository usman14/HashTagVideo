package com.example.usman.youtubehashtag.Database_Objects;

import io.realm.RealmModel;
import io.realm.RealmObject;

/**
 * Created by usman on three/16/2017.
 */

public class Realm_Object_Video_Keywords_Common extends RealmObject{

    String keyword;


    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
