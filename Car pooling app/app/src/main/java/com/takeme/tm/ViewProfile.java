package com.takeme.tm;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

import java.util.Map;

public class ViewProfile extends AppCompatActivity implements View.OnClickListener{
    private ListView listView;
    DatabaseReference dref;
    private FirebaseAuth firebaseAuth;

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        button=(Button)findViewById(R.id.button6);
        firebaseAuth=FirebaseAuth.getInstance();

        final TextView h= (TextView) findViewById(R.id.textView33);
        final TextView i=(TextView)findViewById(R.id.textView34);
        final TextView j=(TextView)findViewById(R.id.textView35);
        final TextView k=(TextView)findViewById(R.id.textView36);
        final TextView l=(TextView)findViewById(R.id.textView37);
        final TextView m=(TextView)findViewById(R.id.textView38);
        final TextView n=(TextView)findViewById(R.id.textView39);
        final TextView o=(TextView)findViewById(R.id.textView40);
        final TextView p=(TextView)findViewById(R.id.textView32);



        button.setOnClickListener(this);

        dref= FirebaseDatabase.getInstance().getReference();
        FirebaseUser user=firebaseAuth.getCurrentUser();
        Query ma=dref.child("Users").orderByChild(user.getUid());

        ma.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String a, b, c, d,x,y,z;
                float e,f;
                final Map<String, Object> valu = (Map<String, Object>) dataSnapshot.getValue();
                a = valu.get("Name").toString();
                b = valu.get("Phone_Number").toString();
                c = valu.get("Address").toString();
                d = valu.get("City").toString();
                x = valu.get("Car_Number").toString();
                y = valu.get("Car_Type").toString();
                z=valu.get("PIN_Code").toString();
                e = Float.parseFloat(valu.get("Ride_Points").toString());
                f = Float.parseFloat(valu.get("Rating").toString());
                h.setText("Name: "+a);
                i.setText("Phone No: "+b);
                j.setText("Address: "+c);
                k.setText("City: "+d);
                l.setText("Car Number: "+x);
                m.setText("Car Type: "+y);
                n.setText("PIN_Code: "+z);
                o.setText("Points Accumulated: "+e);
                p.setText("Rating: "+f);






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

    @Override
    public void onClick(View v) {
        if(v==button)
        {
            startActivity(new Intent(getApplicationContext(),profile.class));
        }

    }
}
