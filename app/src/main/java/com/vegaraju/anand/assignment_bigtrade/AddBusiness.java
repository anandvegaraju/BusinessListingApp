package com.vegaraju.anand.assignment_bigtrade;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


/**
 * Created by Anand on 02-07-2017.
 */

public class AddBusiness extends AppCompatActivity implements View.OnClickListener {

    private static final int PICK_IMAGE_REQUEST = 234;
    private EditText businessname, businessdesc, businessaddress;
    private Spinner spinner;
    private RatingBar ratingBar;
    private StorageReference mStorageRef;
    private Button upload,choose, submit;
    private String name, desc, addr, rating, city;
    private Uri filePath;
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

        choose.setOnClickListener(this);
        upload.setOnClickListener(this);



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

                        DatabaseReference busref = myRef.child(name);
                        final DatabaseReference busnamesref = database.getReference("businessnames");
                        final DatabaseReference citynamesref = database.getReference("cities");
                        final DatabaseReference ratingsref = database.getReference("ratings");


                        busnamesref.addListenerForSingleValueEvent(
                                new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String blist = dataSnapshot.getValue(String.class);
                                        blist = blist + " " + name;
                                        busnamesref.setValue(blist);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                }
                        );

                        citynamesref.addListenerForSingleValueEvent(
                                new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String clist = dataSnapshot.getValue(String.class);
                                        clist = clist + " " + city;
                                        citynamesref.setValue(clist);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                }
                        );

                        ratingsref.addListenerForSingleValueEvent(
                                new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String rlist = dataSnapshot.getValue(String.class);
                                        rlist = rlist + " " + rating;
                                        ratingsref.setValue(rlist);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                }
                        );
                        busref.child("Name").setValue(name);
                        busref.child("City").setValue(city);
                        busref.child("Description").setValue(desc);
                        busref.child("Rating").setValue(rating);
                        busref.child("Address").setValue(addr);
                        Toast.makeText(getApplicationContext(),"Business added",Toast.LENGTH_SHORT).show();
                        Intent gotohome = new Intent(AddBusiness.this, BusinessListActivity.class);
                        startActivity(gotohome);

                    }
                }
        );




    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

        }
    }

    @Override
    public void onClick(View view) {
        //if the clicked button is choose
        if (view == choose) {
            showFileChooser();
        }
        else if (view == upload) {
            Toast.makeText(getApplicationContext(),"Uploading..",Toast.LENGTH_LONG).show();
            uploadFile();
        }

    }


    private void uploadFile() {
        if (filePath != null) {

            name = businessname.getText().toString();
            StorageReference riversRef = mStorageRef.child("images/" + name + ".jpg");
            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }


}
