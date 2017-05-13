package com.example.usman.youtubehashtag.Database_Objects;

import io.realm.RealmObject;

/**
 * Created by usman on 3/17/2017.
 */

public class Realm_Object_History extends RealmObject {
    String title;
    String thumbnailurl;
    String Videoid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    int id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnailurl() {
        return thumbnailurl;
    }

    public void setThumbnailurl(String thumbnailurl) {
        this.thumbnailurl = thumbnailurl;
    }

    public String getVideoid() {
        return Videoid;
    }

    public void setVideoid(String videoid) {
        Videoid = videoid;
    }


}
