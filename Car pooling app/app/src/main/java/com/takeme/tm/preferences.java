package com.takeme.tm;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import java.util.Map;

public class preferences extends AppCompatActivity {
    private Button find;
    private EditText driving, lang, behav;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
final int lan,bev,drive;
        databaseReference= FirebaseDatabase.getInstance().getReference();
        find = (Button) findViewById(R.id.button11);
        driving = (EditText) findViewById(R.id.editText);
        lang = (EditText) findViewById(R.id.editText2);
        behav = (EditText) findViewById(R.id.editText6);
        Bundle bundle = getIntent().getExtras();
        final String src = bundle.getString("src");
        final String dest = bundle.getString("dest");
        final String time = bundle.getString("time");


        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int drive= Integer.parseInt(driving.getText().toString().trim());
                final int lan= Integer.parseInt(lang.getText().toString().trim());
                final int bev= Integer.parseInt(behav.getText().toString().trim());

                final Map<String, Object> value;

if(drive>5||lan>5||bev>5)
{
    Toast.makeText(preferences.this, "Please rate out of 5", Toast.LENGTH_SHORT);
}
       else {// FirebaseUser user = firebaseAuth.getCurrentUser();


    Query myquery = databaseReference.child("Users").orderByChild("To").equalTo(dest);
    //
    // orderByChild("Driving").startAt(drive).orderByChild("Language").startAt(lan).orderByChild("Behaviour").startAt(bev);


    myquery.addChildEventListener(new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            for (DataSnapshot chatSnapshot : dataSnapshot.getChildren()) {
                final Map<String, Object> value = (Map<String, Object>) dataSnapshot.getValue();

                String to = String.valueOf(value.get("To"));
                String a, b, c, d;
                float e, f, m, n, p;
                int flag = 0;

                if (to.equals(dest)) {
                    System.out.println("From" + value.get("From"));
                    s = value.get("From").toString();
                    a = value.get("Name").toString();
                    b = value.get("Phone_Number").toString();
                    c = value.get("Car_Number").toString();
                    d = value.get("Car_Type").toString();
                    e = Float.parseFloat(value.get("Ride_Points").toString());
                    f = Float.parseFloat(value.get("Rating").toString());
                    m = Integer.parseInt(value.get("Driving").toString());
                    n = Integer.parseInt(value.get("Language").toString());
                    p = Integer.parseInt(value.get("Behaviour").toString());


                    if (f >= 2 && m >= drive && n >= lan && p >= bev ) {
                        Intent i = new Intent(preferences.this, FindRideResult.class);


                        Bundle bundle = new Bundle();
                        bundle.putString("From", s);
                        bundle.putString("Name", a);
                        bundle.putString("Phone", b);
                        bundle.putString("CarNo", c);
                        bundle.putString("CarType", d);
                        bundle.putFloat("RidePoints", e);
                        bundle.putFloat("Rating", f);
                        bundle.putString("To", to);
                        i.putExtras(bundle);
                        startActivity(i);

                        // FragmentManager fm = getFragmentManager();
                        // BlankFragment f2 = (BlankFragment) fm.findFragmentById(R.id.);
                        System.out.println(s);
                        //Toast.makeText(preferences.this, "Name: " + a + "\nPhone No: " + b, Toast.LENGTH_SHORT).show();


                        //Toast.makeText(this,"Information saved..You will be notified when we find a person to travel with you",Toast.LENGTH_SHORT).show();

                    }
                               /* else
                                {
                                    Toast.makeText(preferences.this,"Sorry! We could not find any person with the given preferences.\n Decreasing the preference settings might help you find a ride.",Toast.LENGTH_LONG).show();

                                }*/

                }
                            /*else
                            {
                                Toast.makeText(preferences.this,"Sorry! We could not find any ride to this destination.\nTry again later.",Toast.LENGTH_LONG).show();

                            }*/



                       /* System.out.println(dataSnapshot.getValue());
                        final Map<String, Object> value = (Map<String, Object>) dataSnapshot.getValue();

                        String name1 = String.valueOf(value.get("To"));
                        String a, b, c, d, e, f;
                        if (name1.equals(dest)) {
                            System.out.println("From" + value.get("From"));
                            s = value.get("From").toString();
                            a = value.get("Name").toString();
                            b = value.get("Phone_Number").toString();
                            c = value.get("Car_Number").toString();
                            d = value.get("Car_Type").toString();
                            e = value.get("Ride_Points").toString();
                            f = value.get("Rating").toString();
                            if (f >= 50) {
                                Intent i = new Intent(preferences.this, FindRideResult.class);


                                Bundle bundle = new Bundle();
                                bundle.putString("From", s);
                                bundle.putString("Name", a);
                                bundle.putString("Phone", b);
                                bundle.putString("CarNo", c);
                                bundle.putString("CarType", d);
                                bundle.putString("RidePoints", e);
                                bundle.putString("Rating", f);
                                i.putExtras(bundle);
                                startActivity(i);

                                System.out.println(s);
                                Toast.makeText(preferences.this, "Name: " + a + "\nPhone No: " + b, Toast.LENGTH_SHORT).show();


                                //Toast.makeText(this,"Information saved..You will be notified when we find a person to travel with you",Toast.LENGTH_SHORT).show();

                            } else {
                                System.out.println("its is null");
                            }
                        }*/
            }
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
    });

}
            }

        });

    }
    }




