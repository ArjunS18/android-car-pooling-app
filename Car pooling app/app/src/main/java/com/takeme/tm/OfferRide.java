package com.takeme.tm;

import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OfferRide extends AppCompatActivity {
    int timehour, timemin;
    String time;
    private EditText source,desti;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_ride);
        firebaseAuth=FirebaseAuth.getInstance();
        Button btnfind = (Button)findViewById(R.id.submit);
        source = (EditText) findViewById(R.id.src);

        desti = (EditText) findViewById(R.id.dest);

        final TimePicker timepick = (TimePicker)findViewById(R.id.timePicker);
        final EditText etxtstarttime = (EditText) findViewById(R.id.tt);
        Button btnstarttime = (Button) findViewById(R.id.btn);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        btnstarttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Calendar calender;
                calender = Calendar.getInstance();
                calender.getTimeInMillis();*/
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    timehour = timepick.getHour();
                    timemin = timepick.getMinute();
                }
                else
                {
                    timehour = timepick.getCurrentHour();
                    timemin = timepick.getCurrentMinute();
                }

                time = Integer.toString(timehour) + " : " + Integer.toString(timemin);
                etxtstarttime.setText(time);
            }
        });
        btnfind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveRideInformation();



            }
        });
    }
    private void SaveRideInformation()
    {
        String src=source.getText().toString().toLowerCase().trim();
        String dest=desti.getText().toString().toLowerCase().trim();
        // Intent maps = new Intent( FindRide.this, MapsActivity.class);
        FirebaseUser user=firebaseAuth.getCurrentUser();
        RideInformation rideInformation=new RideInformation(src,dest,time);
        databaseReference.child("Users").child(user.getUid()).child("From").setValue(src);
        databaseReference.child("Users").child(user.getUid()).child("To").setValue(dest);
        databaseReference.child("Users").child(user.getUid()).child("StartAt").setValue(time);
        //startActivity(maps);
        Toast.makeText(this,"Information saved..You will be notified when we find a person to travel with you", Toast.LENGTH_SHORT).show();

    }
}
