package com.vegaraju.anand.assignment_bigtrade;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * Created by Anand on 02-07-2017.
 */

public class BusinessListActivity extends AppCompatActivity {
    String webs;
    ListView list;
    String[] web = {
            "IBM",
            "Twitter",
            "TCS",
            "Microsoft",
            "Apple",
            "Wordpress",
            "Drupal"
    } ;
    Integer[] imageId = {
            R.drawable.common_google_signin_btn_icon_dark,
            R.drawable.common_google_signin_btn_icon_dark,
            R.drawable.common_google_signin_btn_icon_dark,
            R.drawable.common_google_signin_btn_icon_dark,
            R.drawable.common_google_signin_btn_icon_dark,
            R.drawable.common_google_signin_btn_icon_dark,
            R.drawable.common_google_signin_btn_icon_dark

    };
    String[] city = {
            "Bangalore",
            "Bangalore",
            "Bangalore",
            "Bangalore",
            "Bangalore",
            "Bangalore",
            "Bangalore",
    } ;

    String[] rating = {
            "5",
            "5",
            "4",
            "5",
            "4",
            "3",
            "4"
    } ;


    FirebaseDatabase database;
    private Button addbusiness;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.businesslist);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.custom_title_bar);
        DatabaseReference listref = database.getInstance().getReference("businessnames");
        listref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String tmp = dataSnapshot.getValue(String.class);
                        webs = tmp;
                        web = webs.split(" ");



                        CustomList adapter = new
                                CustomList(BusinessListActivity.this, web,city,rating, imageId);
                        list=(ListView)findViewById(R.id.listid);
                        list.setAdapter(adapter);
                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view,
                                                    int position, long id) {
                                Toast.makeText(getApplicationContext(), "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();

                            }
                        });


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );

        addbusiness = (Button)findViewById(R.id.addBusinessid);
        addbusiness.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent goto_addbusiness = new Intent(BusinessListActivity.this, AddBusiness.class);
                        startActivity(goto_addbusiness);
                    }
                }
        );







    }
}
