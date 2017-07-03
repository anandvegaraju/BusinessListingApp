package com.vegaraju.anand.assignment_bigtrade;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;



/**
 * Created by Anand on 03-07-2017.
 */

public class BusinessDetails extends AppCompatActivity {

    private Button backbutton;
    private TextView nametext, citytext, desctext, addtext;
    private RatingBar ratingBar;
    FirebaseDatabase database;
    private StorageReference mStorageRef;
    private ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.businessdetails);
        Intent intent = getIntent();
        String bizname = intent.getStringExtra("name");
        database = FirebaseDatabase.getInstance();
        DatabaseReference bizref = database.getReference("businesses").child(bizname);
        nametext = (TextView)findViewById(R.id.nameview);
        desctext = (TextView)findViewById(R.id.descview);
        imageView = (ImageView)findViewById(R.id.imageview);
        addtext = (TextView)findViewById(R.id.addview);
        citytext = (TextView)findViewById(R.id.cityview);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar2);
        nametext.setText(bizname);
        mStorageRef = FirebaseStorage.getInstance().getReference("images").child(bizname+".jpg");
        backbutton = (Button)findViewById(R.id.button2);
        backbutton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent goback = new Intent(BusinessDetails.this, BusinessListActivity.class);
                        mStorageRef.delete();
                        startActivity(goback);
                    }
                }
        );

        Glide.with(this /* context */)
                .using(new FirebaseImageLoader())
                .load(mStorageRef)
                .into(imageView);

        bizref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String des = dataSnapshot.child("Description").getValue(String.class);
                        String add = dataSnapshot.child("Address").getValue(String.class);
                        String city = dataSnapshot.child("City").getValue(String.class);
                        String rating = dataSnapshot.child("Rating").getValue(String.class);
                        desctext.setText(des);
                        addtext.setText(add);
                        citytext.setText(city);
                        float f = Float.valueOf(rating);
                        ratingBar.setRating(f);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );
    }
}
