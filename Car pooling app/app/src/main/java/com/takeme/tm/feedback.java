package com.takeme.tm;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

import java.util.Map;

public class feedback extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Bundle bu=getIntent().getExtras();
        firebaseAuth=FirebaseAuth.getInstance();
        final float dis=bu.getFloat("distance");
        final String name=bu.getString("Name");
        databaseReference= FirebaseDatabase.getInstance().getReference();

        Button find = (Button) findViewById(R.id.button8);
        final EditText driving = (EditText) findViewById(R.id.editText8);
        final EditText language = (EditText) findViewById(R.id.editText13);
        final EditText behaviour = (EditText) findViewById(R.id.editText10);
       final EditText car = (EditText) findViewById(R.id.editText11);
        final Intent s = new Intent(feedback.this, End.class);
        final Bundle ss=new Bundle();
        ss.putFloat("distance",dis);
        s.putExtras(ss);


        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final int drive = Integer.parseInt(driving.getText().toString().trim());
                final int lan = Integer.parseInt(language.getText().toString().trim());
                final int bev = Integer.parseInt(behaviour.getText().toString().trim());
                final int c = Integer.parseInt(car.getText().toString().trim());
                if (drive > 5 || lan > 5 || bev > 5 || c > 5) {
                    Toast.makeText(feedback.this, "Please rate out of 5", Toast.LENGTH_SHORT);
                } else {
                    final Map<String, Object> val;
                    Query myq = databaseReference.child("Users").orderByChild("Name").equalTo(name);
                    myq.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            int m, n, p, q, k;
                            final float e;
                            float f;
                            final float z;

                            final Map<String, Object> val = (Map<String, Object>) dataSnapshot.getValue();
                            e = Float.parseFloat(val.get("Ride_Points").toString());
                            f = Float.parseFloat(val.get("Rating").toString());
                            m = Integer.parseInt(val.get("Driving").toString());
                            n = Integer.parseInt(val.get("Language").toString());
                            p = Integer.parseInt(val.get("Behaviour").toString());
                            q = Integer.parseInt(val.get("Rides").toString());

                            q = q + 1;
                            k = ((drive * 3) + (bev * 2) + (lan) + c) / 7;
                            f = (f + k) / (q);
                            m = (m + drive) / (q);
                            n = (n + lan) / (q);
                            p = (p + bev) / (q);
                            z = (e + (dis));
                            String key = dataSnapshot.getKey();


                            databaseReference.child("Users").child(key).child("Driving").setValue(m);

                            databaseReference.child("Users").child(key).child("Language").setValue(n);
                            databaseReference.child("Users").child(key).child("Behaviour").setValue(p);
                            databaseReference.child("Users").child(key).child("Rating").setValue(f);
                            databaseReference.child("Users").child(key).child("Rides").setValue(q);
                            databaseReference.child("Users").child(key).child("Ride_Points").setValue(z);
                            System.out.print(m + " " + n + " " + p);


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
                    final FirebaseUser user = firebaseAuth.getCurrentUser();
                    Query my = databaseReference.child("Users").orderByChild(user.getUid());
                    my.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                            final Map<String, Object> v = (Map<String, Object>) dataSnapshot.getValue();
                            float w = Float.parseFloat(v.get("Ride_Points").toString());
                            w = w - ((dis));
                            System.out.println("Points" + w);


                            databaseReference.child("Users").child(user.getUid()).child("Ride_Points").setValue(w);


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


                    startActivity(s);


                }
            }
    });
    }
}

