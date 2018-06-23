package com.hashtags.usman.youtubehashtag.Video_Objects;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hashtags.usman.youtubehashtag.Database_Objects.Realm_Object_Video_Keyword;
import com.hashtags.usman.youtubehashtag.R;
import com.riontech.staggeredtextgridview.StaggeredTextGridView;
import com.riontech.staggeredtextgridview.utils.ColorGenerator;

import io.realm.Realm;
import io.realm.RealmResults;

import static android.support.v7.content.res.AppCompatResources.getDrawable;

/**
 * Created by usman on 4/13/2017.
 */

public class WordsAdapter extends BaseAdapter {

    private Context context;
    StaggeredTextGridView gridView;

    private RealmResults<Realm_Object_Video_Keyword> jArray;
    Realm realm;
    public WordsAdapter(Context context,RealmResults<Realm_Object_Video_Keyword> jArray) {
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
        TextView view = (TextView) LayoutInflater.from(context).inflate(R.layout.item_keyword, null);
        Drawable drawable =getDrawable(context, R.drawable.selector_item);
        drawable.setColorFilter(ColorGenerator.MATERIAL.getRandomColor(), PorterDuff.Mode.SRC_ATOP);
        view.setBackground(drawable);

        //Drawable drawable = ContextCompat.getDrawable(context, R.drawable.s);
       // drawable.setColorFilter(com.riontech.staggeredtextgridview.utils.ColorGenerator.MATERIAL.getRandomColor(), PorterDuff.Mode.SRC_ATOP);
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

        //and call notifyDataSetChanged
        notifyDataSetChanged();
    }

}
