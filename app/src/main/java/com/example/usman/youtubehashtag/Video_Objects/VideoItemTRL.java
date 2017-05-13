package com.example.usman.youtubehashtag.Video_Objects;

import java.io.Serializable;

/**
 * Created by usman on 3/21/2017.
 */

 public class VideoItemTRL implements Serializable {
    private VideoItem top;
    private VideoItem right;
    private VideoItem left;

    public VideoItem getTop() {
        return top;
    }

    public void setTop(VideoItem top) {
        this.top = top;
    }

    public VideoItem getRight() {
        return right;
    }

    public void setRight(VideoItem right) {
        this.right = right;
    }

    public VideoItem getLeft() {
        return left;
    }

    public void setLeft(VideoItem left) {
        this.left = left;
    }
}

