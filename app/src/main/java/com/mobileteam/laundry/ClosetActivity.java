package com.mobileteam.laundry;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobileteam.laundry.adapter.ClosetAdapter;

import java.util.ArrayList;

public class ClosetActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_closet);

        // 임시 옷 데이터
        ArrayList<Cloth> data = new ArrayList<>();
        data.add(new Cloth(R.drawable.add_circle_icon));
        data.add(new Cloth(R.drawable.search_icon));
        data.add(new Cloth(R.drawable.menu_icon));
        data.add(new Cloth(R.drawable.search_icon));
        data.add(new Cloth(R.drawable.search_icon));

        RecyclerView closetRecyclerView = (RecyclerView) findViewById(R.id.closet_recycler_view);
        int closetColumn = 3;
        closetRecyclerView.setLayoutManager(new GridLayoutManager(this, closetColumn));
        ClosetAdapter closetAdapter = new ClosetAdapter(data);
        closetRecyclerView.setAdapter(closetAdapter);
    }
}
