package com.example.final_test.PlantsFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.final_test.PlantsFragment.Plant;
import com.example.final_test.R;

import java.util.ArrayList;

public class PlantsAdapter extends ArrayAdapter<Plant> {

    private Context mContext;
    private int mResource;
    private ArrayList<Plant> marrayList;
                                                                //We use ArrayList
    public PlantsAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Plant> objects) {
        super(context, resource, objects);

        this.mContext = context;
        this.mResource = resource;
        this.marrayList = objects;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        convertView = layoutInflater.inflate(mResource, parent, false);

        ImageView imageView = convertView.findViewById(R.id.image);
        TextView textView = convertView.findViewById(R.id.text);
        TextView subtextView = convertView.findViewById(R.id.subtext);

        imageView.setImageResource(getItem(position).getImage());
        textView.setText(getItem(position).getName());
        subtextView.setText(getItem(position).getDes());

        return convertView;
    }
}
