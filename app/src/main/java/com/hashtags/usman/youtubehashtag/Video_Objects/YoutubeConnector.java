package com.hashtags.usman.youtubehashtag.Video_Objects;

import android.content.Context;
import android.util.Log;

import com.hashtags.usman.youtubehashtag.BuildConfig;
import com.hashtags.usman.youtubehashtag.R;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrei on 16.05.2015.
 */
public class YoutubeConnector {
    private YouTube youtube;
    private YouTube.Search.List query;

    public static final String KEY = "AIzaSyA1dMYB6aYSTsJOv_K9VNSkN72uLIpZ9X8";

    public YoutubeConnector(Context context) {
        youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest httpRequest) throws IOException {
            }
        }).setApplicationName(context.getString(R.string.app_name)).build();

        try {
            query = youtube.search().list("id,snippet");
            query.setKey(BuildConfig.API_KEY);
            query.setType("video");
            query.setMaxResults((long)50);
            query.setFields("items(id/videoId,snippet/title,snippet/description,snippet/thumbnails/default/url)");
        } catch (IOException e) {
            Log.d("YC", "Could not initialize: " + e.getMessage());
        }
    }


    public ArrayList<VideoItem> search(String keywords) {
        query.setQ(keywords);
        try {
            SearchListResponse response = query.execute();
            List<SearchResult> results = response.getItems();
            ArrayList<VideoItem> items = new ArrayList<>();
            int a=0;
                for (SearchResult result : results)
                {
                    if(a==0)
                    {
                        VideoItem item = new VideoItem();
                        item.setTitle(result.getSnippet().getTitle());
                        item.setDescription(result.getSnippet().getDescription());
                        item.setThumbnailURL(result.getSnippet().getThumbnails().getDefault().getUrl());
                        item.setId(result.getId().getVideoId());
                        item.setFavourite(false);
                        item.setViewvalue(1);
                        items.add(item);
                        a=1;

                    }
                    else if(a==1)
                    {
                        VideoItem item = new VideoItem();
                        item.setTitle(result.getSnippet().getTitle());
                        item.setDescription(result.getSnippet().getDescription());
                        item.setThumbnailURL(result.getSnippet().getThumbnails().getDefault().getUrl());
                        item.setId(result.getId().getVideoId());
                        item.setFavourite(false);
                        item.setViewvalue(2);
                        items.add(item);

                        a=2;
                    }
                    else if(a==2)
                    {
                        VideoItem item = new VideoItem();
                        item.setTitle(result.getSnippet().getTitle());
                        item.setDescription(result.getSnippet().getDescription());
                        item.setThumbnailURL(result.getSnippet().getThumbnails().getDefault().getUrl());
                        item.setId(result.getId().getVideoId());
                        item.setFavourite(false);
                        item.setViewvalue(3);
                        items.add(item);

                        a=0;

                    }
            }




            return items;
        } catch (IOException e) {
            Log.d("YC", "Could not search: " + e);
            return null;
        }
    }
}
