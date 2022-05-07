package com.mobileteam.laundry;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobileteam.laundry.adapter.ColorSearchAdapter;
import com.mobileteam.laundry.adapter.HowtoAdapter;
import com.mobileteam.laundry.adapter.TextureSearchAdapter;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    private ArrayList<String> textures = new ArrayList<String>();
    private ArrayList<String> colors = new ArrayList<String>();
    private String[] howto = new String[4];
    private TextureSearchAdapter textureAdapter;
    private ColorSearchAdapter colorAdapter;
    private HowtoAdapter howAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kind);

        View banner = findViewById(R.id.header2);
        banner.setBackgroundColor(getColor(AppData.getModeColor()));

        //db를 받아오기 전, 확인용 임시 설정(색상:빨강, 검정/재질: 가죽)
        colors.add("Black");
        colors.add("Red");
        textures.add("가죽");
        howto[0]="세탁기";
        howto[1]="약";
        howto[2]="상관없음";
        howto[3]="95ºC";

        //옷 색상을 표시해주는 리사이클러 뷰
        RecyclerView colorview = findViewById(R.id.colorlist);
        int colum1 = 9;
        colorview.setLayoutManager(new GridLayoutManager(this, colum1));
        colorAdapter = new ColorSearchAdapter(this, colors);
        colorview.setAdapter(colorAdapter);

        //옷 재질을 표시해주는 리사이클러 뷰
        RecyclerView textureview = findViewById(R.id.texturelist);
        int colum2 = 5;
        textureview.setLayoutManager(new GridLayoutManager(this, colum2));
        textureAdapter = new TextureSearchAdapter(this, textures);
        textureview.setAdapter(textureAdapter);

        //세탁 방법을 표시해주는 리사이클러 뷰
        RecyclerView howtoview = findViewById(R.id.howtolist);
        int colum3 = 4;
        howtoview.setLayoutManager(new GridLayoutManager(this, colum3));
        howAdapter = new HowtoAdapter(this, howto);
        howtoview.setAdapter(howAdapter);

    }


}
