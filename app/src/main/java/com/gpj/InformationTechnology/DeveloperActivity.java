package com.gpj.InformationTechnology;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.vikasojha.quizbee.R;

public class DeveloperActivity extends AppCompatActivity {
    Button btnRestart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);

        ImageView btnRestart = (ImageView) findViewById(R.id.back);


        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in2 = new Intent(getApplicationContext(), MainActivity2.class);
                startActivity(in2);
            }
        });
    }
}
