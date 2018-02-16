package com.example.sunrainy.minerseeker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Option extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        setupOPtionBackbtn();
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context,Option.class);
    }
    private void setupOPtionBackbtn() {
        Button btn=(Button) findViewById(R.id.optionbackbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Option.this,"you Cicked back button",Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}
