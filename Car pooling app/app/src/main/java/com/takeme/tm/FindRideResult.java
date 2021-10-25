package com.takeme.tm;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FindRideResult extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_find_ride_result);
        Bundle bundle = getIntent().getExtras();
        firebaseAuth= FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();

        final String a=bundle.getString("Name");
        String b=bundle.getString("Phone");
        String c=bundle.getString("CarNo");
        String d=bundle.getString("CarType");
        String e=bundle.getString("From");
        float f=bundle.getFloat("Rating");
        float g=bundle.getFloat("RidePoints");
        String x=bundle.getString("To");
        final Intent y = new Intent(FindRideResult.this, MapsActivity.class);
        final Intent q=new Intent(FindRideResult.this,FindOrOfferRide.class);
        Bundle bund=new Bundle();
        bund.putString("From",e);
        bund.putString("To",x);
        bund.putString("Name",a);
        y.putExtras(bund);
        Button accept=(Button)findViewById(R.id.button7);
        Button cancel=(Button)findViewById(R.id.button9);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(q);
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* final FirebaseUser user=firebaseAuth.getCurrentUser();
                Query mq=databaseReference.orderByChild(user.getUid());
                mq.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                            int m, n, p, g;
                            float e, f;
                            String q, x, y,key;
                            final Map<String, Object> val = (Map<String, Object>) dataSnapshot.getValue();
                        key=dataSnapshot.getKey();

                            q = val.get("Name").toString();
                            e = Float.parseFloat(val.get("Ride_Points").toString());
                            f = Float.parseFloat(val.get("Rating").toString());
                            x = val.get("To").toString();
                            y = val.get("Phone_Number").toString();

                            m = Integer.parseInt(val.get("Driving").toString());
                            n = Integer.parseInt(val.get("Language").toString());
                            p = Integer.parseInt(val.get("Behaviour").toString());
                            g = Integer.parseInt(val.get("Rides").toString());
                            String addr = q+"has requested to share your ride";
                             sendSMS("9791029909","XOLOBER: Hello"+a+" !,"+addr);
                            Toast.makeText(FindRideResult.this, q + " has been informed. You will be notified as soon as he accepts your ride", Toast.LENGTH_SHORT).show();




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
                });*/
                startActivity(y);

            }
        });





        final TextView h= (TextView) findViewById(R.id.textView5);
        final TextView i=(TextView)findViewById(R.id.textView6);
        final TextView j=(TextView)findViewById(R.id.textView12);
        final TextView k=(TextView)findViewById(R.id.textView11);
        final TextView l=(TextView)findViewById(R.id.textView10);
        final TextView m=(TextView)findViewById(R.id.textView9);
        final TextView n=(TextView)findViewById(R.id.textView8);
        //final TextView o=(TextView)findViewById(R.id.textView13);
        h.setText("Name: "+a);
        i.setText("Phone No: "+b);
        j.setText("Car Number: "+c);
        k.setText("Car Type: "+d);
        l.setText("Coming From: "+e);
        n.setText("Rating: "+f+"/5");
        m.setText("Points Accumulated: "+g);



    }
    public void sendSMS(String phonenumber, String message)
    {
        //Log.d(TAG, "inside message");
        SmsManager sms = SmsManager.getDefault();
        //Log.d(TAG, "sent message");
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.SEND_SMS)
                    == PackageManager.PERMISSION_GRANTED) {
                sms.sendTextMessage(phonenumber, null, message, null, null);
            }
        }
    }
}
