package com.vegaraju.anand.assignment_bigtrade;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomList extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] web;
    private final String[] city;
    private final String[] rating;
    private final Integer[] imageId;
    public CustomList(Activity context,
                      String[] web,String[] city,String[] rating, Integer[] imageId) {
        super(context, R.layout.list_row, web);
        this.context = context;
        this.web = web;
        this.city = city;
        this.rating = rating;
        this.imageId = imageId;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_row, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.name);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.list_image);
        txtTitle.setText(web[position]);

        TextView citytext = (TextView)rowView.findViewById(R.id.city);
        citytext.setText(city[position]);
        TextView ratingtext = (TextView)rowView.findViewById(R.id.rating);
        ratingtext.setText(rating[position]);


        imageView.setImageResource(imageId[position]);
        return rowView;
    }
}