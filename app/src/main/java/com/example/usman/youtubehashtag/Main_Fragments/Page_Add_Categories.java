package com.example.usman.youtubehashtag.Main_Fragments;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usman.youtubehashtag.Activities.MainActivity;
import com.example.usman.youtubehashtag.R;
import com.example.usman.youtubehashtag.Database_Objects.Realm_DataBase;
import com.example.usman.youtubehashtag.Database_Objects.Realm_Object_Video_Keyword;
import com.example.usman.youtubehashtag.Database_Objects.Realm_Object_Video_Keywords_Common;
import com.riontech.staggeredtextgridview.StaggeredTextGridView;
import com.riontech.staggeredtextgridview.utils.ColorGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import at.markushi.ui.CircleButton;
import io.realm.Realm;
import io.realm.RealmResults;

import static android.support.v7.content.res.AppCompatResources.getDrawable;

/**
 * Created by usman on 3/16/2017.
 */

public class Page_Add_Categories extends Fragment {
    ArrayList<Realm_Object_Video_Keyword> list;
    EditText editText;
    CircleButton button_add;
            FloatingActionButton button_search;
    StaggeredTextGridView gridView;
    GridView gridView_common;
    private Realm realm;
    Realm_DataBase dataBase;
    private Handler handler;

    ArrayAdapter<Realm_Object_Video_Keywords_Common> stringArrayAdapter;
    ArrayAdapter<Realm_Object_Video_Keyword> adapter;
    WordsAdapter1 wordsAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.page_add_categories, container, false);
        list=new ArrayList<>();
        Get_Widgets(v);
        ( getActivity()).setTitle("HASHTAGS");
        realm = Realm.getDefaultInstance();
        handler = new Handler();
        dataBase=new Realm_DataBase();
        Click_listeners(v);
        Long count=realm.where(Realm_Object_Video_Keyword.class).count();
        if(count==0)
        {
        Add_First_Keyword();
        }
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        editText.setMinHeight(height/15);
        editText.setMinimumWidth(width/2);
        return v;
    }

    private void Click_listeners(final View v) {
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText.getText().toString().equals("")) {
                    if (!dataBase.Add_Data(realm, editText.getText().toString())) {
                        Toast.makeText(getContext(), "Keywords Already Exists", Toast.LENGTH_SHORT).show();
                        wordsAdapter.notifyDataSetChanged();
                    }
                    else
                        {
                        wordsAdapter.notifyDataSetChanged();
                        Fragment currentFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.frame);
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.detach(currentFragment).attach(currentFragment).commit();
                         }
                }
                else
                    {
                    Toast.makeText(getContext(), "Empty Value", Toast.LENGTH_SHORT).show();
                    }
            }
        });
        Update_Videos_Found();
        Add_Common_Keywords();
        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Long count = realm.where(Realm_Object_Video_Keyword.class).count();
                if (count == 0) {
                    Toast.makeText(getContext(), "Please Select At least one Keyword", Toast.LENGTH_SHORT).show();

                } else {
                    final RealmResults<Realm_Object_Video_Keyword> Allreports = realm.where(Realm_Object_Video_Keyword.class).findAll();
                    String[] reports = new String[Allreports.size()];
                    for (int j = 0; j < Allreports.size(); j++) {
                        reports[j] = Allreports.get(j).getKeyword();
                    }
                    Collections.shuffle(Arrays.asList(reports));
                    StringBuilder strBuilder = new StringBuilder();
                    for (int i = 0; i < reports.length; i++) {
                        strBuilder.append(reports[i]).append(" ");
                    }
                    String newString = strBuilder.toString();
                    Bundle bundle = new Bundle();
                    bundle.putString("keyword", newString);
                    Videos_List Activity_Tab = new Videos_List();
                    Activity_Tab.setArguments(bundle);
                    if (Is_Network_Connected()) {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                        getFragmentManager().beginTransaction().replace(R.id.frame, Activity_Tab).commit();

                    } else {
                        Toast.makeText(getContext(), "No Internet Connection Found", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private boolean Is_Network_Connected() {

        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;

    }

    public void Get_Widgets(View v) {
        editText=(EditText)v.findViewById(R.id.edt_page_add_categories);
        button_add=(CircleButton) v.findViewById(R.id.img_btn_add_hashtag_page_add_categories) ;
        gridView=(StaggeredTextGridView) v.findViewById(R.id.staggeredTextView);
        gridView_common=(GridView)v.findViewById(R.id.grd_view_common_page_add_categories);
        button_search=(FloatingActionButton) v.findViewById(R.id.fab) ;
    }
    private void Update_Videos_Found() {

        final RealmResults<Realm_Object_Video_Keyword> Allreports = realm.where(Realm_Object_Video_Keyword.class).findAll();
        wordsAdapter=new WordsAdapter1(getContext(),Allreports) ;
        gridView.setAdapter(wordsAdapter);

    }
    public void Add_Common_Keywords()
    {
        final RealmResults<Realm_Object_Video_Keywords_Common> Allreports=realm.where(Realm_Object_Video_Keywords_Common.class).findAll();
        Long count  =realm.where(Realm_Object_Video_Keywords_Common.class).count();
        if(count==0)
        {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Realm_Object_Video_Keywords_Common one=realm.createObject(Realm_Object_Video_Keywords_Common.class);
                    Realm_Object_Video_Keywords_Common two=realm.createObject(Realm_Object_Video_Keywords_Common.class);
                    Realm_Object_Video_Keywords_Common three=realm.createObject(Realm_Object_Video_Keywords_Common.class);
                    Realm_Object_Video_Keywords_Common four=realm.createObject(Realm_Object_Video_Keywords_Common.class);
                    Realm_Object_Video_Keywords_Common five=realm.createObject(Realm_Object_Video_Keywords_Common.class);

                    Realm_Object_Video_Keywords_Common six=realm.createObject(Realm_Object_Video_Keywords_Common.class);
                    Realm_Object_Video_Keywords_Common seven=realm.createObject(Realm_Object_Video_Keywords_Common.class);
                    Realm_Object_Video_Keywords_Common eight=realm.createObject(Realm_Object_Video_Keywords_Common.class);
                    Realm_Object_Video_Keywords_Common nine=realm.createObject(Realm_Object_Video_Keywords_Common.class);
                    Realm_Object_Video_Keywords_Common ten=realm.createObject(Realm_Object_Video_Keywords_Common.class);
                    one.setKeyword("Football");

                    two.setKeyword("Cricket");
                    three.setKeyword("Funny");
                    four.setKeyword("Cooking");
                    five.setKeyword("Politics");
                    six.setKeyword("Hollywood");
                    seven.setKeyword("Bollywood");
                    eight.setKeyword("Sports");
                    nine.setKeyword("Bikes");
                    ten.setKeyword("Makeup");


                }
            });
        }


        stringArrayAdapter = new ArrayAdapter<Realm_Object_Video_Keywords_Common>(getContext(), R.layout.item_grid_view_popular_hashtags, Allreports)
        {
            @Override
            public View getView(final int position, View convertView, final ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater(null).inflate(R.layout.item_grid_view_popular_hashtags, parent, false);
                }

                TextView title = (TextView) convertView.findViewById(R.id.page_add_categories_grid_view_common_tv_hashtag);

                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!dataBase.Add_Data(realm,Allreports.get(position).getKeyword()))
                        {
                            Toast.makeText(getContext(),"Keywords Already Exists",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            dataBase.Delete_Keyword(realm,Allreports.get(position).getKeyword());
                            //adapter.notifyDataSetChanged();
                            stringArrayAdapter.notifyDataSetChanged();
                            Fragment currentFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.frame);
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.detach(currentFragment).attach(currentFragment).commit();
                        }

                    }
                });
                title.setText(Allreports.get(position).getKeyword());
                return convertView;
            }


        };
        gridView_common.setAdapter(stringArrayAdapter);
    }
    public class WordsAdapter1 extends BaseAdapter {

        private Context context;
        private RealmResults<Realm_Object_Video_Keyword> jArray;
        Realm realm;
        public WordsAdapter1(Context context,RealmResults<Realm_Object_Video_Keyword> jArray) {
            this.context = context;
            this.jArray = jArray;

        }

        @Override
        public int getCount() {
            return jArray.size();
        }

        @Override
        public String getItem(int position) {
            try {
                return jArray.get(position).getKeyword();
            } catch (Exception e) {
                return "Sample";
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            TextView view = (TextView) LayoutInflater.from(context).inflate(R.layout.item_keyword,  parent, false);
            if(position==0)
            {
                view.setHeight(0);
                view.setWidth(0);

            }
            Drawable drawable =getDrawable(context, R.drawable.selector_item);
            drawable.setColorFilter(ColorGenerator.MATERIAL.getRandomColor(), PorterDuff.Mode.SRC_ATOP);
            view.setBackground(drawable);

            try {
                view.setText(jArray.get(position).getKeyword());
                view.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        realm= Realm.getDefaultInstance();
                        final Realm_Object_Video_Keyword report = realm.where(Realm_Object_Video_Keyword.class).equalTo("key", jArray.get(position).getKey()).findFirst();
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                report.deleteFromRealm();

                            }
                        });

                        Fragment currentFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.frame);
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.detach(currentFragment).attach(currentFragment).commit();
                        notifyDataSetChanged();
                        updateAdapter(jArray);
                    }


                });

            } catch (Exception e){
                e.printStackTrace();
            }

            return view;
        }
        public void updateAdapter(RealmResults<Realm_Object_Video_Keyword> arrylst) {
            this.jArray= arrylst;
            notifyDataSetChanged();
        }

    }
    public void Add_First_Keyword()
    {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                Realm_Object_Video_Keyword video_keyword = realm.createObject(Realm_Object_Video_Keyword.class);
                video_keyword.setKeyword("");

    }
    });
        }
}