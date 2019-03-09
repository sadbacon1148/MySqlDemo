package com.example.sadbacon.mysqldemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import static com.example.sadbacon.mysqldemo.BarcodeCaptureActivity.BarcodeObject;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        String url = getIntent().getStringExtra(BarcodeObject);

        Toast.makeText(this, url, Toast.LENGTH_LONG).show();


    }
}
