package com.mobileteam.laundry;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobileteam.laundry.adapter.ClosetAdapter;
import com.mobileteam.laundry.domain.Clothes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.schedulers.Schedulers;

public class ClosetActivity extends AppCompatActivity {
    private List<Clothes> data = new ArrayList<>();

    private ClosetAdapter closetAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closet);

        // 리사이클러뷰
        RecyclerView closetRecyclerView = (RecyclerView) findViewById(R.id.closet_recycler_view);
        int closetColumn = 3;
        closetRecyclerView.setLayoutManager(new GridLayoutManager(this, closetColumn));
        closetAdapter = new ClosetAdapter(this, data);
        closetRecyclerView.setAdapter(closetAdapter);

        // header 색 변경
        View header = findViewById(R.id.header);
        header.setBackgroundColor(getColor(AppData.getModeColor()));

        // header back button onClick
        ImageButton backButton = (ImageButton) findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> {
            finish();
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        // intent 에서 데이터 가져오는 과정
        List<Clothes> clothesList = getClothesList();
        if(clothesList == null) {
            // 전달된 Intent 가 없을 때 (바로 이 액티비티로 넘어왔을 때)
            Log.d("#####", "No List");

            // DB에서 모든 옷 아이템을 가져옴
            AppData.getDb().clothesDao().getAll()
                    .doOnSuccess(list -> {
                        data.clear();
                        data.addAll(list);

                        runOnUiThread(closetAdapter::notifyDataSetChanged);
                    })
                    .doOnError(e -> Log.e("#####", e.toString()))
                    .subscribeOn(Schedulers.io()).subscribe();

        } else {
            Log.d("#####", "Has List");

            data.clear();
            data.addAll(clothesList);

            closetAdapter.notifyDataSetChanged();
        }
    }

    private List<Clothes> getClothesList() {
        return (List<Clothes>) getIntent().getSerializableExtra("searched");
    }
}
