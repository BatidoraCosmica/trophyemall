package com.example.trophyemall.model;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.example.trophyemall.R;

public class CreateActivity extends AppCompatActivity {

    Button btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        btnCancel = findViewById(R.id.btnCreateCancel);
        btnCancel.setOnClickListener(l -> {
            finish();
        });
    }
}