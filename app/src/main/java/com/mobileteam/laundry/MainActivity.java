package com.mobileteam.laundry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // haha
    }

    public void onSearchClicked(View v) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }
}