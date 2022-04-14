package com.mobileteam.laundry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 옷장 버튼 onClick
        ImageButton closetButton = (ImageButton) findViewById(R.id.menu_button);
        closetButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ClosetActivity.class);
            startActivity(intent);
        });

        // 검색 버튼 onClick
        ImageButton searchButton = (ImageButton) findViewById(R.id.search_button);
        searchButton.setOnClickListener(v -> {
            onSearchClicked();
        });
    }

    public void onSearchClicked() {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }
}