package com.hashtags.usman.youtubehashtag.Video_Objects;

/**
 * Created by andrei on 16.05.2015.
 */
public class VideoItem {

    private String title;
    private String description;
    private String thumbnailURL;
    private String id;
    private Boolean favourite;
    private int viewvalue;

    public int getViewvalue() {
        return viewvalue;
    }

    public void setViewvalue(int viewvalue) {
        this.viewvalue = viewvalue;
    }

    public Boolean getFavourite() {
        return favourite;
    }

    public void setFavourite(Boolean favourite) {
        this.favourite = favourite;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
