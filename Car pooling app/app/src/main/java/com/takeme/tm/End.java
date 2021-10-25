package com.takeme.tm;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

public class End extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        Bundle ss=getIntent().getExtras();
        TextView t=(TextView)findViewById(R.id.textView23);
        float f=ss.getFloat("distance");
        float x=f;
        t.setText(x+" points have been deducted from your account\n ");

    }
}
