package com.example.tutorialapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {
    ArrayList<Subject> arrayList;
    Context context;

    public CustomAdapter(Context context, ArrayList<Subject> arrayList) {
        super(context, 0, arrayList);
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String data = arrayList.get(position).getName();

        convertView = LayoutInflater.from(context).inflate(R.layout.list_row, parent, false);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        // display row by using position plus one
        title.setText((data));
        return  convertView;

    }


}
