package com.takeme.tm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class login extends AppCompatActivity implements View.OnClickListener {
    private Button register;
    private EditText email;
    private EditText password;
    private TextView signup;
    private ProgressDialog dialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth=FirebaseAuth.getInstance();
        dialog=new ProgressDialog(this);
        register=(Button)findViewById(R.id.regbutton);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        signup=(TextView)findViewById(R.id.textview);
        register.setOnClickListener(this);
        signup.setOnClickListener(this);

    }
    private void loginuser()
    {
        String emailid=email.getText().toString().trim();
        String pword=password.getText().toString().trim();
        dialog.setMessage("Logging In. Please Wait.");

        databaseReference= FirebaseDatabase.getInstance().getReference();

        firebaseAuth.signInWithEmailAndPassword(emailid,pword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                dialog.dismiss();
                if(task.isSuccessful())
                {
                    finish();
                    Toast.makeText(login.this, "Login Successful! Wait for a few seconds.", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(getApplicationContext(),FindOrOfferRide.class));

                }
                else
                {
                    //Toast.makeText(this,"Wrong Credentials. Please register before signing in.",Toast.LENGTH_SHORT).show();
                      //Toast.makeText(this,"Wrong Credentials. Please register before signing in.",Toast.LENGTH_SHORT).show();
                    Toast.makeText(login.this, "Wrong Credentials. Please register before signing in.", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public void onClick(View v) {
        if(v==register)
        {
           // Toast.makeText(login.this,"Registered Successfully",Toast.LENGTH_SHORT);

            loginuser();

        }
        if(v==signup)
        {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }

    }
}
