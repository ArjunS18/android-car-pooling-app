package com.takeme.tm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class FindOrOfferRide extends AppCompatActivity implements View.OnClickListener{
    private Button profile;
    private Button find;
    private Button offer;
    private static final int PROGRESS = 0x1;

    private ProgressDialog progressDialog;
    private int mProgressStatus = 0;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_or_offer_ride);
        progressDialog=new ProgressDialog(this);
        profile=(Button)findViewById(R.id.button3);
        offer=(Button)findViewById(R.id.button4);
        find=(Button)findViewById(R.id.button5);
        profile.setOnClickListener(this);
        find.setOnClickListener(this);
        offer.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v==profile)
        {
            startActivity(new Intent(getApplicationContext(),ViewProfile.class));
        }
        if(v==offer)
        {
           //progressDialog.setMessage("Wait for a moment...");
            //progressDialog.show();
            //Toast.makeText(this,"Wait for 14 seconds..",Toast.LENGTH_SHORT).show();
           startActivity(new Intent(getApplicationContext(),OfferRide.class));
            //progressDialog.dismiss();

        }
        if(v==find)
        {           // Toast.makeText(this,"Wait for 14 seconds..",Toast.LENGTH_SHORT).show();

            startActivity(new Intent(getApplicationContext(),FindRide.class));
        }
    }
}
