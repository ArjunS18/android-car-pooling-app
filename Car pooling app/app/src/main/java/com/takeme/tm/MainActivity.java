package com.takeme.tm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button register;
    private EditText email;
    private EditText password;
    private TextView signin;
    private ProgressDialog dialog;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth=FirebaseAuth.getInstance();
        dialog=new ProgressDialog(this);
        register=(Button)findViewById(R.id.regbutton);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        signin=(TextView)findViewById(R.id.textview);
        register.setOnClickListener(this);
        signin.setOnClickListener(this);
    }
    private void registeruser()
    {
        String emailid=email.getText().toString().trim();
        String pword=password.getText().toString().trim();
        if(TextUtils.isEmpty(emailid))
        {
            Toast.makeText(this,"Please enter mailId", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pword))
        {
            Toast.makeText(this,"Please enter Password", Toast.LENGTH_SHORT).show();
            return;
        }
        //dialog.setMessage("Registering User");
        //dialog.show();
        firebaseAuth.createUserWithEmailAndPassword(emailid,pword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                finish();
                startActivity(new Intent(getApplicationContext(),profile.class));


            }
        });

    }

    @Override
    public void onClick(View v) {
        if(v==register)
        {
            Toast.makeText(MainActivity.this,"Registered Successfully", Toast.LENGTH_SHORT);

            registeruser();


        }
        if(v==signin)
        {
            startActivity(new Intent(getApplicationContext(),login.class));
        }

    }
}
