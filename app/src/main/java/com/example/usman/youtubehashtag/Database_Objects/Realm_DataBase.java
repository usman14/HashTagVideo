package com.example.usman.youtubehashtag.Database_Objects;

import com.example.usman.youtubehashtag.Database_Objects.Realm_Object_Favourite;
import com.example.usman.youtubehashtag.Database_Objects.Realm_Object_History;
import com.example.usman.youtubehashtag.Database_Objects.Realm_Object_Video_Keyword;
import com.example.usman.youtubehashtag.Database_Objects.Realm_Object_Video_Keywords_Common;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by usman on three/17/2017.
 */

public class Realm_DataBase {

    Realm realm;
    public boolean Add_Data(Realm realm, final String key) {
        realm = Realm.getDefaultInstance();
        Long count = realm.where(Realm_Object_Video_Keyword.class).equalTo("keyword", key, Case.INSENSITIVE).count();
        if (count == 0)
        {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Realm_Object_Video_Keyword video_keyword = realm.createObject(Realm_Object_Video_Keyword.class);
                    video_keyword.setKeyword(key);

                    Number currentIdNum = realm.where(Realm_Object_Video_Keyword.class).max(("key"));
                    int nextId;
                    if (currentIdNum == null) {
                        nextId = 1;
                    } else {
                        nextId = currentIdNum.intValue() + 1;
                    }
                    video_keyword.setKey(nextId);

                }
            });

            return true;
        } else {
            return false;

        }
    }

    public void Delete_Keyword(Realm realm,String Key){
        realm = Realm.getDefaultInstance();

        final Realm_Object_Video_Keywords_Common report = realm.where(Realm_Object_Video_Keywords_Common.class).equalTo("keyword", Key).findFirst();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                report.deleteFromRealm();
            }
        });

    }
    public void Add_History(Realm realm, final String title, final String videoid, final String url)
    {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Number currentIdNum = realm.where(Realm_Object_History.class).max(("id"));
                int nextId;
                if (currentIdNum == null) {
                    nextId = 1;
                } else {
                    nextId = currentIdNum.intValue() + 1;
                }
                Realm_Object_History realm_object_history=realm.createObject(Realm_Object_History.class);
                realm_object_history.setTitle(title);
                realm_object_history.setThumbnailurl(url);
                realm_object_history.setVideoid(videoid);
                realm_object_history.setId(nextId);
            }
        });

    }
    public boolean Add_Favourite(Realm realm,final String title, final String url, final String videoid)
    {
        Long count = realm.where(Realm_Object_Favourite.class).equalTo("Videoid", videoid).count();
if(count==0){
    realm.executeTransaction(new Realm.Transaction() {
        @Override
        public void execute(Realm realm) {
            final Realm_Object_Favourite realm_object_favourite=realm.createObject(Realm_Object_Favourite.class);

            Number currentIdNum = realm.where(Realm_Object_Favourite.class).max(("id"));
            int nextId;
            if (currentIdNum == null) {
                nextId = 1;
            } else {
                nextId = currentIdNum.intValue() + 1;
            }
            realm_object_favourite.setTitle(title);
            realm_object_favourite.setThumbnailurl(url);
            realm_object_favourite.setVideoid(videoid);
            realm_object_favourite.setId(nextId);
        }
    });
    return false;


}
     else
         return true;
}

    public void Delete_All_Favourite(Realm realm) {
        final RealmResults<Realm_Object_Favourite> result= realm.where(Realm_Object_Favourite.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                result.deleteAllFromRealm();
            }
        });

    }

    public void Delete_All_History(Realm realm) {
        final RealmResults<Realm_Object_History> result= realm.where(Realm_Object_History.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                result.deleteAllFromRealm();
            }
        });

    }
}
