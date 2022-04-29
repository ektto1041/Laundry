package com.mobileteam.laundry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.mobileteam.laundry.domain.Clothes;
import com.mobileteam.laundry.enums.Mode;

import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private LaundryDatabase db;

    private ConstraintLayout header;
    private ImageButton addButton;
    private ImageButton laundryButton;
    private ImageButton dryButton;
    private ImageButton ironButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initSetting();          // 초기 데이터 세팅
        viewInitialization();   // 멤버 변수 초기화
        buttonSetting();        // 버튼 온클릭
        layoutRefresh();        // 레이아웃 갱신
    }

    // 앱이 처음 실행되었을 때 데이터를 세팅해주는 메소드
    private void initSetting() {
        AppData.setMode(Mode.LAUNDRY);

        // DB 세팅
        db = Room.databaseBuilder(getApplicationContext(), LaundryDatabase.class, "laundry").build();
    }

    // 멤버 View 변수들을 초기화 하는 메소드
    private void viewInitialization() {
        addButton = (ImageButton) findViewById(R.id.add_button);
        header = (ConstraintLayout) findViewById(R.id.header);
        laundryButton = (ImageButton) findViewById(R.id.laundry_button);
        dryButton = (ImageButton) findViewById(R.id.dry_button);
        ironButton = (ImageButton) findViewById(R.id.iron_button);
    }

    // AppData 의 Mode 에 따라 레이아웃을 갱신하는 메소드
    private void layoutRefresh() {
        int color = AppData.getModeColor();

        // Selected Padding & padding
        int sp = (int)getResources().getDimension(R.dimen.bottom_nav_selected_button_padding);
        int p = (int)getResources().getDimension(R.dimen.bottom_nav_button_padding);

        switch(AppData.getMode()) {
            case LAUNDRY:
                laundryButton.setColorFilter(ContextCompat.getColor(this, color), android.graphics.PorterDuff.Mode.SRC_IN);
                laundryButton.setPadding(sp, sp, sp, sp);
                dryButton.setColorFilter(ContextCompat.getColor(this, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);
                dryButton.setPadding(p, p, p, p);
                ironButton.setColorFilter(ContextCompat.getColor(this, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);
                ironButton.setPadding(p, p, p, p);
                break;
            case DRY:
                laundryButton.setColorFilter(ContextCompat.getColor(this, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);
                laundryButton.setPadding(p, p, p, p);
                dryButton.setColorFilter(ContextCompat.getColor(this, color), android.graphics.PorterDuff.Mode.SRC_IN);
                dryButton.setPadding(sp, sp, sp, sp);
                ironButton.setColorFilter(ContextCompat.getColor(this, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);
                ironButton.setPadding(p, p, p, p);
                break;
            case IRON:
                laundryButton.setColorFilter(ContextCompat.getColor(this, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);
                laundryButton.setPadding(p, p, p, p);
                dryButton.setColorFilter(ContextCompat.getColor(this, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);
                dryButton.setPadding(p, p, p, p);
                ironButton.setColorFilter(ContextCompat.getColor(this, color), android.graphics.PorterDuff.Mode.SRC_IN);
                ironButton.setPadding(sp, sp, sp, sp);
        }

        header.setBackgroundColor(getColor(color));
        addButton.setColorFilter(ContextCompat.getColor(this, color), android.graphics.PorterDuff.Mode.SRC_IN);
    }

    // 버튼의 온클릭을 등록해주는 메소드
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
        laundryButton.setOnClickListener(this::onBottomButtonClicked);
        dryButton.setOnClickListener(this::onBottomButtonClicked);
        ironButton.setOnClickListener(this::onBottomButtonClicked);

        // TODO: Room DB 연결 확인을 위한 테스트 코드
        // 옷 추가 버튼 onClick
        addButton.setOnClickListener(v -> {
            db.clothesDao().insert(new Clothes("Strong"))
                    .doOnSuccess(id -> Log.d("#####", "" + id))
                    .doOnError(e -> Log.d("#####", e.toString()))
                    .subscribeOn(Schedulers.io()).subscribe();
        });
    }

    public void onSearchClicked() {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    private void onBottomButtonClicked(View v) {
        switch (v.getId()) {
            case R.id.laundry_button:
                AppData.setMode(Mode.LAUNDRY);
                break;
            case R.id.dry_button:
                AppData.setMode(Mode.DRY);
                break;
            case R.id.iron_button:
                AppData.setMode(Mode.IRON);
        }

        layoutRefresh();
    }
}