package com.takeme.tm;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FindRide extends AppCompatActivity {
    int timehour, timemin;
    String time,s;
    private EditText source,desti;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    //Map<String,Object> value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_ride);
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
                Intent j = new Intent(FindRide.this, preferences.class);
                Bundle bundle=new Bundle();
                String src=source.getText().toString().toLowerCase().trim();
                final String dest=desti.getText().toString().toLowerCase().trim();
                bundle.putString("src",src);
                bundle.putString("dest",dest);
                bundle.putString("time",time);
                j.putExtras(bundle);
                startActivity(j);



            }
        });
    }
    /*private void SaveRideInformation()
    {
        private DatabaseReference databaseReference;
        private FirebaseAuth firebaseAuth;
        Map<String,Object> value;
        String src=source.getText().toString().trim();
        final String dest=desti.getText().toString().trim();
        // Intent maps = new Intent( FindRide.this, MapsActivity.class);
        FirebaseUser user=firebaseAuth.getCurrentUser();
        //RideInformation rideInformation=new RideInformation(src,dest,time);
        //databaseReference.child("Rides").child(user.getUid()).child("From").setValue(src);
        //databaseReference.child("Rides").child(user.getUid()).child("To").setValue(dest);
        //databaseReference.child("Rides").child(user.getUid()).child("StartAt").setValue(time);
        //Get text for search edit text box

        Query myquery= databaseReference.child("Users").orderByChild("To").equalTo(dest);
        // System.out.println(dataSnapshot.getKey() + "is" + value.get("socialNumber"));
        System.out.println(dest);

        /*myquery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChild) {
                System.out.println(dataSnapshot.getValue());
                Map<String,Object> value = (Map<String, Object>) dataSnapshot.getValue();

                String name1 = String.valueOf(value.get("fullName"));
                //System.out.println(dataSnapshot.getKey() + "is" + value.get("fullName").toString());
                if (name1.equals(dest)){
                    System.out.println("Name" + value.get("fullName"));
                }
                else{
                    System.out.println("its is null");
                }

            }};
        myquery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                System.out.println(dataSnapshot.getValue());
                value = (Map<String, Object>) dataSnapshot.getValue();

                String name1 = String.valueOf(value.get("To"));
                String a,b,c,d,e,f;
                //System.out.println(dataSnapshot.getKey() + "is" + value.get("fullName").toString());
                if (name1.equals(dest)){
                    System.out.println("From" + value.get("From"));
                    s=value.get("From").toString();
                    a=value.get("Name").toString();
                    b=value.get("Phone_Number").toString();
                    c=value.get("Car_Number").toString();
                    d=value.get("Car_Type").toString();
                    e=value.get("Ride_Points").toString();
                    f=value.get("Rating").toString();
                    Intent i = new Intent(FindRide.this, FindRideResult.class);

                    Bundle bundle=new Bundle();
                    bundle.putString("From",s);
                    bundle.putString("Name",a);
                    bundle.putString("Phone",b);
                    bundle.putString("CarNo",c);
                    bundle.putString("CarType",d);
                    bundle.putString("RidePoints",e);
                    bundle.putString("Rating",f);
                    i.putExtras(bundle);
                    startActivity(i);

                    System.out.println(s);
                    Toast.makeText(FindRide.this,"Name: "+a+"\nPhone No: "+b, Toast.LENGTH_SHORT).show();




                    //Toast.makeText(this,"Information saved..You will be notified when we find a person to travel with you",Toast.LENGTH_SHORT).show();

                }
                else{
                    System.out.println("its is null");
                }
                /*s=dataSnapshot.getKey();
                System.out.println(s);
                Toast.makeText(FindRide.this,s, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });}*/


        //startActivity(maps);
        //System.out.println(s);
        //Toast.makeText(this,"Information saved..You will be notified when we find a person to travel with you",Toast.LENGTH_SHORT).show();

    }


