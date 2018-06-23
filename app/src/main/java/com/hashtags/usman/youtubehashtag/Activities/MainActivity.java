package com.hashtags.usman.youtubehashtag.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.hashtags.usman.youtubehashtag.Database_Objects.Realm_Object_Video_Keyword;
import com.hashtags.usman.youtubehashtag.Database_Objects.Realm_Object_Video_Keywords_Common;
import com.hashtags.usman.youtubehashtag.Main_Fragments.Page_Add_Categories;
import com.hashtags.usman.youtubehashtag.Main_Fragments.Page_Favourite;
import com.hashtags.usman.youtubehashtag.Main_Fragments.Page_History;
import com.hashtags.usman.youtubehashtag.Main_Fragments.Page_Playlist;
import com.hashtags.usman.youtubehashtag.R;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Realm realm;
    FloatingActionButton floatingActionButton;

    int a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

        setSupportActionBar(toolbar);
        Realm.init(this);
        try {
            realm = Realm.getDefaultInstance();

        } catch (Exception e) {

            RealmConfiguration config = new RealmConfiguration.Builder()
                    .deleteRealmIfMigrationNeeded()
                    .build();
            realm = Realm.getInstance(config);

        }
        Long count = realm.where(Realm_Object_Video_Keywords_Common.class).count();
        if (count == 0) {
            Add_Common_Keywords();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Long count_1 = realm.where(Realm_Object_Video_Keyword.class).count();
        if(count_1==0)
        {
            Add_First_Keyword();
        }
        Long count_2 = realm.where(Realm_Object_Video_Keyword.class).count();
        if (count_2 == 1) {
            //Add_Common_Keywords();
            getSupportFragmentManager().beginTransaction().add(R.id.frame, new Page_Add_Categories()).commit();

        } else {
            getSupportFragmentManager().beginTransaction().add(R.id.frame, new Page_Playlist()).commit();

        }


    }

    private void Add_Common_Keywords() {


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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if (getSupportFragmentManager().getBackStackEntryCount() >= 1) {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        else
            {
            super.onBackPressed();

        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.HashTags)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Page_Add_Categories()).commit();
            Log.d("count", String.valueOf(getSupportFragmentManager().getBackStackEntryCount()));

        }
        else if (id == R.id.Playlist)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Page_Playlist()).commit();
        }
        else if (id == R.id.Favourites)
        {

            getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Page_Favourite()).commit();
        }
        else if (id == R.id.History)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Page_History()).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
