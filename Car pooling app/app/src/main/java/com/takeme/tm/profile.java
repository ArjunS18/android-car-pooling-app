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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class profile extends AppCompatActivity implements View.OnClickListener {
 private Button save;
    private EditText editname,editph,editadd,editcity,editpin,editcarno,edittype;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        firebaseAuth=FirebaseAuth.getInstance();
        save=(Button)findViewById(R.id.button2);
        editname=(EditText)findViewById(R.id.editText);
        editph=(EditText)findViewById(R.id.editText2);
        editadd=(EditText)findViewById(R.id.editText3);
        editcity=(EditText)findViewById(R.id.editText4);
        editpin=(EditText)findViewById(R.id.editText5);
        editcarno=(EditText)findViewById(R.id.editText6);
        edittype=(EditText)findViewById(R.id.editText7);
        save.setOnClickListener(this);

        databaseReference= FirebaseDatabase.getInstance().getReference();
    }
private void saveUserInformation() {
    String name = editname.getText().toString().trim();
    String ph = editph.getText().toString().trim();
    String city = editcity.getText().toString().trim();
    String pin = editpin.getText().toString().trim();
    String add = editadd.getText().toString().trim();
    String carno = editcarno.getText().toString().trim();
    String type = edittype.getText().toString().trim();
    if (name.isEmpty() || ph.isEmpty() || city.isEmpty() || pin.isEmpty() || carno.isEmpty() || type.isEmpty()) {
        Toast.makeText(profile.this, "Please fill all the details", Toast.LENGTH_LONG).show();
    } else {
        UserInformation UserInformation = new UserInformation(name, ph, add, city, pin, carno, type);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.child("Users").child(user.getUid()).setValue(UserInformation);
        Toast.makeText(this, "Information saved...", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(),FindOrOfferRide.class));


    }
}
    @Override
    public void onClick(View v) {
        if(v==save)

        {
            saveUserInformation();
        }

    }
}
