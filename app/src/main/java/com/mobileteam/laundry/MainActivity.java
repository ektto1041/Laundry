package com.mobileteam.laundry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.mobileteam.laundry.enums.Mode;

public class MainActivity extends AppCompatActivity {
    private Mode mode;

    private ImageButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = (ImageButton) findViewById(R.id.add_button);

        buttonSetting();
    }

    public void onSearchClicked() {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    private void onBottomButtonClicked(View v) {
        switch (v.getId()) {
            case R.id.laundry_button:
                addButton.setColorFilter(ContextCompat.getColor(this, R.color.red), android.graphics.PorterDuff.Mode.SRC_IN);
                break;
            case R.id.dry_button:
                addButton.setColorFilter(ContextCompat.getColor(this, R.color.green), android.graphics.PorterDuff.Mode.SRC_IN);
                break;
            case R.id.iron_button:
                addButton.setColorFilter(ContextCompat.getColor(this, R.color.blue), android.graphics.PorterDuff.Mode.SRC_IN);
        }


    }

    private void buttonSetting() {
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

        // 하단 세탁 버튼 onClick
        ImageButton laundryButton = (ImageButton) findViewById(R.id.laundry_button);
        ImageButton dryButton = (ImageButton) findViewById(R.id.dry_button);
        ImageButton ironButton = (ImageButton) findViewById(R.id.iron_button);
        laundryButton.setOnClickListener(this::onBottomButtonClicked);
        dryButton.setOnClickListener(this::onBottomButtonClicked);
        ironButton.setOnClickListener(this::onBottomButtonClicked);

    }
}