package com.example.usman.youtubehashtag.Database_Objects;

import io.realm.RealmObject;

/**
 * Created by usman on three/16/2017.
 */

public class Realm_Object_Video_Keyword extends RealmObject{
    String keyword;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    int    key;
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }


}
