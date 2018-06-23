package com.hashtags.usman.youtubehashtag.Database_Objects;

import io.realm.RealmObject;

/**
 * Created by usman on three/17/2017.
 */

public class Realm_Object_Favourite extends RealmObject {
    String title;
    String thumbnailurl;
    String Videoid;

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    boolean favourite;
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
