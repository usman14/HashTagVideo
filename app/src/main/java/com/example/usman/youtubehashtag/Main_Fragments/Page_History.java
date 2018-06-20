package com.example.usman.youtubehashtag.Main_Fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.usman.youtubehashtag.Activities.MainActivity;
import com.example.usman.youtubehashtag.Activities.PlayerActivity;
import com.example.usman.youtubehashtag.R;
import com.example.usman.youtubehashtag.Database_Objects.Realm_DataBase;
import com.example.usman.youtubehashtag.Database_Objects.Realm_Object_History;
import com.squareup.picasso.Picasso;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by usman on three/17/2017.
 */

public class Page_History extends Fragment {
    Realm realm;
    GridView gridView;
    Realm_DataBase dataBase;
    ArrayAdapter<Realm_Object_History> adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.page_history,container,false);
        realm=Realm.getDefaultInstance();
        dataBase=new Realm_DataBase();
        gridView=(GridView)v.findViewById(R.id.page_history_grd_view) ;
        Update_Videos_Found();
        setHasOptionsMenu(true);
        ( getActivity()).setTitle("HISTORY");

        return v;
    }

    private void Update_Videos_Found() {

        final RealmResults<Realm_Object_History> Allreports = realm.where(Realm_Object_History.class).findAll().sort("id", Sort.DESCENDING);
        adapter = new ArrayAdapter<Realm_Object_History>(getActivity(), R.layout.item_page_favrt_grid_view, Allreports)
        {

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {


                    if (convertView == null) {
                        convertView = getLayoutInflater(null).inflate(R.layout.item_page_favrt_grid_view, parent, false);
                    }

                    ImageView thumbnail = (ImageView) convertView.findViewById(R.id.img_view_page_favrt);
                    TextView title = (TextView) convertView.findViewById(R.id.tv_page_favrt);
                      thumbnail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), PlayerActivity.class);
                        intent.putExtra("VIDEO_ID", Allreports.get(position).getVideoid());
                        intent.putExtra("Title", Allreports.get(position).getTitle());
                        intent.putExtra("ThumbnailUrl",Allreports.get(position).getThumbnailurl());
                        startActivity(intent);
                    }
                });

                Picasso.with(getContext()).load("http://i1.ytimg.com/vi/"+Allreports.get(position).getVideoid()+"/0.jpg").fit().into(thumbnail);
                title.setText(Allreports.get(position).getTitle());
                return convertView;
            }
        };
        gridView.setAdapter(adapter);


}
    private void Delete_History() {
        final android.app.AlertDialog.Builder logoutDialogBuilder = new android.app.AlertDialog.Builder(getContext());
        logoutDialogBuilder.setTitle(R.string.delete_history_title).setMessage(R.string.delete_history).setCancelable(true).setPositiveButton(R.string.delete,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dataBase.Delete_All_History(realm);
                        adapter.notifyDataSetChanged();
                    }
                }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        logoutDialogBuilder.show();

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_favourites, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_favourite:
                Delete_History();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
