package com.example.issac.marvel.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.issac.marvel.R;
import com.example.issac.marvel.pojo.superheroe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by issac on 26/01/18.
 */

public class superheroeArrayAdapter extends ArrayAdapter<superheroe> {
    private ArrayList<superheroe> arrayList;
    public superheroeArrayAdapter( Context context, int resource,  List<superheroe> objects) {
        super(context, resource, objects);
        arrayList = (ArrayList<superheroe>) objects;
    }

    @NonNull
    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        superheroe superheroe0 = arrayList.get(position);
        //itune itune1 = getItem(position);

        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.marvel_layout, parent, false);
        }

        TextView superheroeName = (TextView) convertView.findViewById(R.id.collection);
        //TextView superheroeType = (TextView) convertView.findViewById(R.id.trackName);

        superheroeName.setText(superheroe0.name);
       // superheroeType.setText(superheroe0.type);

        return convertView;
    }
}

