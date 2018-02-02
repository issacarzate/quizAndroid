package com.example.issac.marvel.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.issac.marvel.R;
import com.example.issac.marvel.pojo.itune;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by issac on 26/01/18.
 */

public class ituneArrayAdapter extends ArrayAdapter<itune> {
    private ArrayList<itune> arrayList;
    public ituneArrayAdapter( Context context, int resource,  List<itune> objects) {
        super(context, resource, objects);
        arrayList = (ArrayList<itune>) objects;
    }

    @NonNull
    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        itune itune0 = arrayList.get(position);
        //itune itune1 = getItem(position);

        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.marvel_layout, parent, false);
        }

        TextView collectionName = (TextView) convertView.findViewById(R.id.collection);
        //TextView trackName = (TextView) convertView.findViewById(R.id.trackName);
        //TextView trackPrice = (TextView) convertView.findViewById(R.id.trackPrice);

        collectionName.setText(itune0.collectionName);
       // trackName.setText(itune0.trackName);
        //trackPrice.setText(itune0.trackPrice+"");

        return convertView;
    }
}

