package com.example.issac.marvel.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.issac.marvel.R;
import com.example.issac.marvel.pojo.StarDude;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by issac on 16/02/18.
 */

public class starArrayAdapter extends ArrayAdapter<StarDude> {
    private ArrayList<StarDude> arrayList;
    public starArrayAdapter(Context context, int resource, List<StarDude> objects) {
        super(context, resource, objects);
        arrayList = (ArrayList<StarDude>) objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StarDude StarDude0 = arrayList.get(position);

        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.marvel_layout, parent, false);
        }

        TextView StarDudeName = (TextView) convertView.findViewById(R.id.collection);

        StarDudeName.setText(StarDude0.name);

        return convertView;
    }
}