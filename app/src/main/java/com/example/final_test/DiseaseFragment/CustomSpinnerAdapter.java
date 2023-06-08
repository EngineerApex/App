package com.example.final_test.DiseaseFragment;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomSpinnerAdapter extends ArrayAdapter<String> {

    private Context context;
    private List<String> items;
    private int textViewResourceId;
    private int textSize; // Add this variable to hold the desired text size

    public CustomSpinnerAdapter(Context context, int textViewResourceId, List<String> items, int textSize) {
        super(context, textViewResourceId, items);
        this.context = context;
        this.items = items;
        this.textViewResourceId = textViewResourceId;
        this.textSize = textSize;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getView(position, convertView, parent);
        view.setTextSize(textSize); // Set the text size
        view.setTextColor(context.getResources().getColor(android.R.color.holo_green_light)); // Set the text color to white
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getDropDownView(position, convertView, parent);
        view.setTextSize(textSize); // Set the text size
        view.setTextColor(context.getResources().getColor(android.R.color.holo_green_light)); // Set the text color to white
        return view;
    }
}


