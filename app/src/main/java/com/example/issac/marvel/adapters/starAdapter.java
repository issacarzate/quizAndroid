package com.example.issac.marvel.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.issac.marvel.R;
import com.example.issac.marvel.VolleySingleton;
import com.example.issac.marvel.pojo.MarvelDude;
import com.example.issac.marvel.pojo.StarDude;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by issac on 02/02/18.
 */

public class starAdapter extends ArrayAdapter<StarDude>{
    private Context context;
    public starAdapter(@NonNull Context context, int resource, @NonNull List<StarDude> objects){
        super(context, resource, objects);
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        StarDude starDude = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.marvel_layout, parent, false);
        }
        TextView textView = (TextView)convertView.findViewById(R.id.collection);
        TextView birthdate = (TextView)convertView.findViewById(R.id.birth);
        NetworkImageView networkImageView = (NetworkImageView)convertView.findViewById(R.id.imageView2);
        textView.setText(starDude.name);
        birthdate.setText(starDude.birth);
        RequestQueue requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
        ImageLoader imageLoader = new ImageLoader(requestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap> cache =
                            new LruCache<String, Bitmap>(10);
                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url,bitmap);
                    }
                });
        networkImageView.setImageUrl(starDude.url, imageLoader);

        return convertView;
    }
}
