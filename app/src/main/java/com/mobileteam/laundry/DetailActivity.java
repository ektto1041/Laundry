package com.mobileteam.laundry;

import android.app.Dialog;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobileteam.laundry.adapter.ColorSearchAdapter;
import com.mobileteam.laundry.adapter.HowtoAdapter;
import com.mobileteam.laundry.adapter.TextureSearchAdapter;
import com.mobileteam.laundry.domain.Clothes;
import com.mobileteam.laundry.domain.Texture;
import com.mobileteam.laundry.enums.ClothesColor;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.schedulers.Schedulers;

public class DetailActivity extends AppCompatActivity {

    private Clothes data;                                   // 현재 표시되고 있는 Clothes 데이터
    private ArrayList<String> textures = new ArrayList<>(); // 재질 리사이클러뷰에 표시될 데이터
    private TextureSearchAdapter textureAdapter;            // 재질 리사이클러뷰에 사용될 어댑터

    private ImageButton colorButton;                        // 색상 버튼
    private Dialog colorDialog;                             // 색상 선택 다이얼로그
    private Button addTextureButton;
    private Dialog textureDialog;                           // 재질 추가 다이얼로그

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kind);

        viewInitialization();   // 레이아웃 변수
        buttonSetting();        // Button OnClick

        // DB에서 clothes id 를 이용해 데이터 가져옴
        long clothesId = getIntent().getLongExtra("clothesId", -1);
        // TODO: clothes id == -1
        if(clothesId == -1) return;

        AppData.getDb().clothesDao().fineOne(clothesId)
                .doOnSuccess(clothes -> {
                    data = clothes;

                    AppData.getDb().textureDao().findAll(clothesId)
                            .doOnSuccess(textureList -> {
                                setClothes(clothes, textureList);
                            })
                            .doOnError(e -> Log.d("#####", e.toString()))
                            .subscribeOn(Schedulers.io()).subscribe();
                })
                .doOnError(e -> Log.d("#####", e.toString()))
                .subscribeOn(Schedulers.io()).subscribe();
    }

    private void viewInitialization() {
        View header = findViewById(R.id.header);
        header.setBackgroundColor(getColor(AppData.getModeColor()));

        colorButton = (ImageButton) findViewById(R.id.color_button);
        addTextureButton = (Button) findViewById(R.id.add_texture_button);

        //옷 재질을 표시해주는 리사이클러 뷰
        RecyclerView textureRecyclerView = findViewById(R.id.texture_recycler_view);
        textureRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        textureAdapter = new TextureSearchAdapter(this, textures);
        textureRecyclerView.setAdapter(textureAdapter);
    }

    private void buttonSetting() {
        // 뒤로가기 버튼
        ImageButton backButton = (ImageButton) findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());

        // 삭제 버튼
        ImageButton deleteButton = (ImageButton) findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(v -> {
            AppData.getDb().textureDao().delete(data.getId())
                    .doOnSuccess(id -> {
                        AppData.getDb().clothesDao().delete(data.getId())
                                .doOnSuccess(id_ -> {
                                    finish();
                                })
                                .doOnError(e -> Log.e("#####", e.toString()))
                                .subscribeOn(Schedulers.io()).subscribe();
                    })
                    .doOnError(e -> Log.e("#####", e.toString()))
                    .subscribeOn(Schedulers.io()).subscribe();
        });

        // 색상 버튼
        colorDialog = new Dialog(this);
        colorDialog.setContentView(R.layout.dialog_color_detail);
        colorDialog.setTitle("색상 선택");

        colorDialog.findViewById(R.id.white_button).setOnClickListener(this::colorDialogButtonOnClickListener);
        colorDialog.findViewById(R.id.grey_button).setOnClickListener(this::colorDialogButtonOnClickListener);
        colorDialog.findViewById(R.id.black_button).setOnClickListener(this::colorDialogButtonOnClickListener);
        colorDialog.findViewById(R.id.red_button).setOnClickListener(this::colorDialogButtonOnClickListener);
        colorDialog.findViewById(R.id.green_button).setOnClickListener(this::colorDialogButtonOnClickListener);
        colorDialog.findViewById(R.id.blue_button).setOnClickListener(this::colorDialogButtonOnClickListener);
        colorDialog.findViewById(R.id.cyan_button).setOnClickListener(this::colorDialogButtonOnClickListener);
        colorDialog.findViewById(R.id.yellow_button).setOnClickListener(this::colorDialogButtonOnClickListener);
        colorDialog.findViewById(R.id.magenta_button).setOnClickListener(this::colorDialogButtonOnClickListener);

        colorButton.setOnClickListener(v -> colorDialog.show());

        // 재질 추가 버튼
        textureDialog = new Dialog(this);
        textureDialog.setContentView(R.layout.custom_texture);
        textureDialog.setTitle("재질 추가");

        textureDialog.findViewById(R.id.btn_texture).setOnClickListener(v -> {
            EditText editText = (EditText) textureDialog.findViewById(R.id.edit_texture);
            String texture = editText.getText().toString().trim();

            textures.add(texture);
            textureAdapter.notifyDataSetChanged();

            AppData.getDb().textureDao().insert(new Texture(data.getId(), texture))
                    .doOnSuccess(id -> {
                        textureDialog.dismiss();
                    })
                    .doOnError(e -> Log.e("#####", e.toString()))
                    .subscribeOn(Schedulers.io()).subscribe();
        });

        addTextureButton.setOnClickListener(v -> textureDialog.show());
    }

    private void colorDialogButtonOnClickListener(View v) {
        int id = v.getId();

        ClothesColor color = null;

        switch (id) {
            case R.id.white_button:
                color = ClothesColor.WHITE;
                break;
            case R.id.grey_button:
                color = ClothesColor.GRAY;
                break;
            case R.id.black_button:
                color = ClothesColor.BLACK;
                break;
            case R.id.red_button:
                color = ClothesColor.RED;
                break;
            case R.id.green_button:
                color = ClothesColor.GREEN;
                break;
            case R.id.blue_button:
                color = ClothesColor.BLUE;
                break;
            case R.id.cyan_button:
                color = ClothesColor.CYAN;
                break;
            case R.id.yellow_button:
                color = ClothesColor.YELLOW;
                break;
            case R.id.magenta_button:
                color = ClothesColor.MAGENTA;
        }

        if(color == null) return;

        colorButton.setColorFilter(color.getColor(this), PorterDuff.Mode.SRC_IN);
        AppData.getDb().clothesDao().update(data.getId(), color)
                .doOnSuccess(clothesId -> {
                    colorDialog.dismiss();
                })
                .doOnError(e -> Log.e("#####", e.toString()))
                .subscribeOn(Schedulers.io()).subscribe();
    }

    private void setClothes(Clothes clothes, List<Texture> textureList) {
        // 색상
        ClothesColor clothesColor = clothes.getClothesColor();
        if(clothesColor != null) {
            int color = clothes.getClothesColor().getColor(getApplicationContext());
            colorButton.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }

        // 재질
        refreshTextures(textureList);
    }

    private void refreshTextures(List<Texture> textureList) {
        List<String> textureStrList = textureList.stream().map(Texture::getName).collect(Collectors.toList());
        textures.clear();
        textures.addAll(textureStrList);
        textureAdapter.notifyDataSetChanged();
    }
}
