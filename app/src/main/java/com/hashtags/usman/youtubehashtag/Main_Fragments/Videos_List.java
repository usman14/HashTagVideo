package com.hashtags.usman.youtubehashtag.Main_Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hashtags.usman.youtubehashtag.Activities.PlayerActivity;
import com.hashtags.usman.youtubehashtag.R;
import com.hashtags.usman.youtubehashtag.Database_Objects.Realm_DataBase;
import com.hashtags.usman.youtubehashtag.Video_Objects.VideoItem;
import com.hashtags.usman.youtubehashtag.Video_Objects.VideoItemTRL;
import com.hashtags.usman.youtubehashtag.Video_Objects.YoutubeConnector;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import at.markushi.ui.CircleButton;
import io.realm.Realm;

/**
 * Created by usman on three/16/2017.
 */

public class Videos_List extends Fragment {
    private ArrayList<VideoItem> searchResults;
    Handler handler;
    Realm realm;
    GridView gridview;
    Realm_DataBase dataBase;
    long mCurCheckPosition;
    long values;
    ProgressDialog progressDialog;
    boolean spinners;
    int mLastSpinnerPosition;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.page_videos_list,container,false);
        handler=new Handler();
        gridview=(GridView)v.findViewById(R.id.page_videos_list_grd_view);
        progressDialog = ProgressDialog.show(getContext(), "",
                "Loading Videos", true);
        //progressDialog.setMessage("Downloading Videos");
        progressDialog.show();
        mLastSpinnerPosition = 0;
        dataBase=new Realm_DataBase();
        realm=Realm.getDefaultInstance();
        Bundle bundle = this.getArguments();
        String filename = bundle.getString("keyword");
        searchOnYoutube(filename);
        Bundle mySavedInstanceState = getArguments();
        values = mySavedInstanceState.getLong("adapter_position");
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                progressDialog.dismiss();
            }
        };

        return v;}



    private void searchOnYoutube(final String keywords) {

        new Thread(){
            @Override
            public void run() {
                YoutubeConnector yc = new YoutubeConnector(getContext());
                searchResults = yc.search(keywords);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        updateVideosFound();
                        handler.sendEmptyMessage(0);
                    }

                });
            }
        }.start();
    }


    public ArrayList<VideoItemTRL> Make_TRL_List(ArrayList<VideoItem> searchResults)
    {
        ArrayList<VideoItem> list1=new ArrayList<>(searchResults.size());
        ArrayList<VideoItem> list2=new ArrayList<>(searchResults.size());
        ArrayList<VideoItem> list3=new ArrayList<>(searchResults.size());
        ArrayList<VideoItemTRL>list=new ArrayList<>(searchResults.size());

        for(VideoItem item : searchResults)
        {

            if(item.getViewvalue()==1)
            {
                list1.add(item);
            }
            if(item.getViewvalue()==2)
            {
                list2.add(item);
            }
            if(item.getViewvalue()==3)
            {
                list3.add(item);
            }
        }
        for(int a=0;a<list1.size();a++)
        {   VideoItemTRL videoItemTRL=new VideoItemTRL();
            videoItemTRL.setTop(list1.get(a));
            list.add(videoItemTRL);
        }
        for(int a=0;a<list2.size();a++)
        {
            list.get(a).setLeft(list2.get(a));
        }
        for(int a=0;a<list3.size();a++)
        {
            list.get(a).setRight(list3.get(a));
        }
        list.remove(list.size()-1);

        return list; }

    private void updateVideosFound() {
        final ArrayList<VideoItemTRL> ListTRL = Make_TRL_List(searchResults);
        if (getActivity() != null) {


            ArrayAdapter<VideoItemTRL> adapter = new ArrayAdapter<VideoItemTRL>(getContext(), R.layout.item_page_videos_list, ListTRL) {
                @Override
                public View getView(final int position, View convertView, ViewGroup parent) {


                    {
                        if (convertView == null) {
                            convertView = getLayoutInflater(null).inflate(R.layout.item_page_videos_list, parent, false);
                        }
                        if(ListTRL!=null)
                        {
                            final ImageView thumbnail = (ImageView) convertView.findViewById(R.id.img_view_top_page_videos_list);
                            ImageView thumbnail1 = (ImageView) convertView.findViewById(R.id.img_view_left_page_videos);
                            ImageView thumbnail2 = (ImageView) convertView.findViewById(R.id.img_view_right_page_videos);
                            CircleButton imageButton1 = (CircleButton) convertView.findViewById(R.id.img_btn_left_page_videos);
                            CircleButton imageButton2 = (CircleButton) convertView.findViewById(R.id.img_btn_top_page_videos);
                            CircleButton imageButton3 = (CircleButton) convertView.findViewById(R.id.img_btn_right_page_videos);
                            imageButton1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Drop_Down_List(ListTRL.get(position).getTop().getTitle(),
                                            ListTRL.get(position).getTop().getThumbnailURL(), ListTRL.get(position).getTop().getId());
                                }
                            });
                            imageButton2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Drop_Down_List(ListTRL.get(position).getLeft().getTitle(),
                                            ListTRL.get(position).getLeft().getThumbnailURL(), ListTRL.get(position).getLeft().getId());
                                }
                            });
                            imageButton3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Drop_Down_List(ListTRL.get(position).getRight().getTitle(),
                                            ListTRL.get(position).getRight().getThumbnailURL(), ListTRL.get(position).getRight().getId());
                                }
                            });
                            TextView title = (TextView) convertView.findViewById(R.id.tv_top_page_videos_list);
                            TextView title1 = (TextView) convertView.findViewById(R.id.tv_left_page_videos);
                            TextView title2 = (TextView) convertView.findViewById(R.id.tv_right_page_videos);

                            VideoItemTRL result = ListTRL.get(position);
                            if (result.getTop() != null) {
                                Picasso.with(getContext()).load("http://i1.ytimg.com/vi/" + ListTRL.get(position).getTop().getId() + "/0.jpg").fit().into(thumbnail);

                            }
                            if (result.getRight() != null) {
                                Picasso.with(getContext()).load("http://i1.ytimg.com/vi/" + ListTRL.get(position).getLeft().getId() + "/0.jpg").fit().into(thumbnail1);

                            }
                            if (result.getLeft() != null) {
                                if(result.getLeft().getId()!=null)
                                {
                                    Picasso.with(getContext()).load("http://i1.ytimg.com/vi/" + ListTRL.get(position).getRight().getId() + "/0.jpg").fit().into(thumbnail2);

                                }

                            }


                            title.setText(ListTRL.get(position).getTop().getTitle());
                            title1.setText(ListTRL.get(position).getLeft().getTitle());
                            title2.setText(ListTRL.get(position).getRight().getTitle());

                            thumbnail.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mCurCheckPosition = position;
                                    Intent intent = new Intent(getContext(), PlayerActivity.class);
                                    intent.putExtra("VIDEO_ID", ListTRL.get(position).getTop().getId());
                                    intent.putExtra("Title", ListTRL.get(position).getTop().getTitle());
                                    intent.putExtra("ThumbnailUrl", ListTRL.get(position).getTop().getThumbnailURL());
                                    startActivity(intent);
                                }
                            });
                            thumbnail1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mCurCheckPosition = position;
                                    Intent intent = new Intent(getContext(), PlayerActivity.class);
                                    intent.putExtra("VIDEO_ID", ListTRL.get(position).getLeft().getId());
                                    intent.putExtra("Title", ListTRL.get(position).getLeft().getTitle());
                                    intent.putExtra("ThumbnailUrl", ListTRL.get(position).getLeft().getThumbnailURL());
                                    startActivity(intent);
                                }
                            });
                            thumbnail2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mCurCheckPosition = position;
                                    Intent intent = new Intent(getContext(), PlayerActivity.class);
                                    intent.putExtra("VIDEO_ID", ListTRL.get(position).getRight().getId());
                                    intent.putExtra("Title", ListTRL.get(position).getRight().getTitle());
                                    intent.putExtra("ThumbnailUrl", ListTRL.get(position).getRight().getThumbnailURL());
                                    startActivity(intent);
                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(getContext(),"No Results found,Please try again",Toast.LENGTH_LONG).show();
                        }




                    }


                    return convertView;
                }
            };
            gridview.setAdapter(adapter);
        }
    }
    public void Drop_Down_List(final String title, final String url, final String id)
    {
        final CharSequence[] items = {"Add Video to Favorite List", "Share Video"};

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Options");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if(item==0)
                {
                    if(dataBase.Add_Favourite(realm,title,url,id)){
                        Toast.makeText(getContext(),"Video Already Exists in Favourites",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getContext(),"Video Added to Favourites",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT,
                            "youtube.com/watch?v="+id);
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                }



            }
        });
        AlertDialog alert = builder.create();
        alert.show();
        //window.setLayout(240, 350);


    }
    @Override
    public void onPause() {
        super.onPause();

        getArguments().putLong("adapter_position", mCurCheckPosition);
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        if ( progressDialog!=null && progressDialog.isShowing() ){
            progressDialog.cancel();
        }
    }
}

