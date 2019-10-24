package com.example.s326197mappe2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class AddOrderActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);

    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    public void onBackPressed(){
        finish();
    }
}
