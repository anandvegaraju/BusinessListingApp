package com.vegaraju.anand.assignment_bigtrade;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by Anand on 02-07-2017.
 */

public class AddBusiness extends AppCompatActivity {

    private EditText businessname, businessdesc, businessaddress;
    private Spinner spinner;
    private RatingBar ratingBar;
    private StorageReference mStorageRef;
    private Button upload,choose, submit;
    private String name, desc, addr, rating, city;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_business);
        getSupportActionBar().setTitle("Add new Business");
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("businesses");
        businessname = (EditText)findViewById(R.id.bnameid);
        businessdesc = (EditText)findViewById(R.id.bdescid);
        businessaddress = (EditText)findViewById(R.id.baddid);
        spinner = (Spinner)findViewById(R.id.spinner);
        String[] items = new String[]{"Bangalore", "Hyderabad", "Chennai", "Mumbai", "New Delhi", "Kolkata", "Vizag"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        mStorageRef = FirebaseStorage.getInstance().getReference();

        choose = (Button)findViewById(R.id.chooseid);
        upload = (Button)findViewById(R.id.uploadid);




        submit = (Button)findViewById(R.id.submitbutton);
        submit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        name = businessname.getText().toString();
                        desc = businessdesc.getText().toString();
                        city = spinner.getSelectedItem().toString();
                        Float ratingf =  ratingBar.getRating();
                        rating = String.valueOf(ratingf);
                        addr = businessaddress.getText().toString();

                        //upload to db

                        DatabaseReference busref = database.getReference(name);
                        busref.child("Name").setValue(name);
                        busref.child("City").setValue(city);
                        busref.child("Description").setValue(desc);
                        busref.child("Rating").setValue(rating);
                        busref.child("Address").setValue(addr);

                        Intent gotohome = new Intent(AddBusiness.this, BusinessListActivity.class);
                        startActivity(gotohome);

                    }
                }
        );




    }
}
