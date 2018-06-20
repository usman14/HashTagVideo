package com.example.usman.youtubehashtag.Main_Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.usman.youtubehashtag.Activities.MainActivity;
import com.example.usman.youtubehashtag.R;
import com.example.usman.youtubehashtag.Database_Objects.Realm_DataBase;
import com.example.usman.youtubehashtag.Database_Objects.Realm_Object_Video_Keyword;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by usman on three/20/2017.
 */

public class Page_Playlist extends android.support.v4.app.Fragment {
    ArrayList<Realm_Object_Video_Keyword> list;
    EditText editText;
    GridView gridView;
    GridView gridView_common;
    private Realm realm;
    Realm_DataBase dataBase;
    private Handler handler;
    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.page_add_categories, container, false);
        list=new ArrayList<>();
        getwidgets(v);
        ( getActivity()).setTitle("HASHTAGS");

        realm = Realm.getDefaultInstance();
        handler = new Handler();
        dataBase=new Realm_DataBase();
        Long count=realm.where(Realm_Object_Video_Keyword.class).count();
        if(count==0)
        {
            Toast.makeText(getContext(),"Please Select At least one Keyword",Toast.LENGTH_SHORT).show();

        }
        else
        {
            final RealmResults<Realm_Object_Video_Keyword> Allreports = realm.where(Realm_Object_Video_Keyword.class).findAll();
            String [] animals=new String[Allreports.size()];
            for(int j=0;j<Allreports.size();j++){
                animals[j]=Allreports.get(j).getKeyword();
            }
            Collections.shuffle(Arrays.asList(animals));
            StringBuilder strBuilder = new StringBuilder();
            for (int i = 0; i < animals.length; i++) {
                strBuilder.append(animals[i]).append(" ");
            }
            String newString = strBuilder.toString();
            Bundle bundle = new Bundle();
            bundle.putString("keyword", newString);
            Videos_List Activity_Tab = new Videos_List();
            Activity_Tab.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.frame, Activity_Tab).commit();
        }

        return v;
    }

    public void getwidgets(View v) {
        editText=(EditText)v.findViewById(R.id.edt_page_add_categories);
        gridView_common=(GridView)v.findViewById(R.id.grd_view_common_page_add_categories);
    }

}