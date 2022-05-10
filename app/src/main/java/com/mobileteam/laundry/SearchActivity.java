package com.mobileteam.laundry;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobileteam.laundry.adapter.ColorSearchAdapter;
import com.mobileteam.laundry.adapter.TextureSearchAdapter;
import com.mobileteam.laundry.domain.Clothes;
import com.mobileteam.laundry.enums.ClothesColor;
import com.mobileteam.laundry.enums.Detergent;
import com.mobileteam.laundry.enums.Temperature;
import com.mobileteam.laundry.enums.WashingPower;
import com.mobileteam.laundry.enums.WashingType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener{
    ArrayList<String> textures = new ArrayList<>();
    ArrayList<ClothesColor> colors = new ArrayList<>();
    private WashingType washingType = WashingType.WASHER;
    private WashingPower washingPower = WashingPower.STRONG;
    private Temperature temperature = Temperature._95;
    private Detergent detergent = Detergent.ANY;
    TextureSearchAdapter textureAdapter;
    ColorSearchAdapter colorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        View header = findViewById(R.id.header);
        header.setBackgroundColor(getColor(AppData.getModeColor()));

        //옷 재질을 표시해주는 리사이클러 뷰
        RecyclerView textureview = findViewById(R.id.texture_recycler_view);
        int colums1 = 4;
        textureview.setLayoutManager(new GridLayoutManager(this, colums1));
        textureAdapter = new TextureSearchAdapter(this, textures, -1);
        textureview.setAdapter(textureAdapter);

        //옷 색상을 표시해주는 리사이클러 뷰
        RecyclerView colorview = findViewById(R.id.color_recycler_view);
        int colums2 = 9;
        colorview.setLayoutManager(new GridLayoutManager(this, colums2));
        colorAdapter = new ColorSearchAdapter(this, colors);
        colorview.setAdapter(colorAdapter);


        //세탁 방법의 드롭 다운
        Spinner how = (Spinner) findViewById(R.id.washing_type_spinner);
        ArrayAdapter<CharSequence> how_adp = ArrayAdapter.createFromResource(
                this, R.array.how_list, R.layout.spinner_item);
        how_adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        how.setAdapter(how_adp);
        how.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                switch(pos) {
                    case 0:
                        washingType = WashingType.WASHER;
                        break;
                    case 1:
                        washingType = WashingType.WATER;
                        break;
                    case 2:
                        washingType = WashingType.NO_WATER;
                }
            }
            public void onNothingSelected(AdapterView<?> arg0) {}
        });

        //세탁 강도의 드롭 다운
        Spinner strength = (Spinner) findViewById(R.id.washing_power_spinner);
        ArrayAdapter<CharSequence> strength_adp = ArrayAdapter.createFromResource(
                this, R.array.strength_list, R.layout.spinner_item);
        strength_adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        strength.setAdapter(strength_adp);
        strength.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                switch (pos) {
                    case 0:
                        washingPower = WashingPower.STRONG;
                        break;
                    case 1:
                        washingPower = WashingPower.WEAK;
                }
            }
            public void onNothingSelected(AdapterView<?> arg0) {}
        });

        //세제 종류의 드롭 다운
        Spinner detergentSpinner = (Spinner) findViewById(R.id.detergent_spinner);
        ArrayAdapter<CharSequence> detergent_adp = ArrayAdapter.createFromResource(
                this, R.array.detergent_list, R.layout.spinner_item);
        detergent_adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        detergentSpinner.setAdapter(detergent_adp);
        detergentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                switch (pos) {
                    case 0:
                        detergent = Detergent.ANY;
                        break;
                    case 1:
                        detergent = Detergent.NEUTRAL;
                }
            }
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        //물 온도의 드롭 다운
        Spinner temperatureSpinner = (Spinner) findViewById(R.id.temperature_spinner);
        ArrayAdapter<CharSequence> temperature_adp = ArrayAdapter.createFromResource(
                this, R.array.temperature_list, R.layout.spinner_item);
        temperature_adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        temperatureSpinner.setAdapter(temperature_adp);
        temperatureSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                switch (pos) {
                    case 0:
                        temperature = Temperature._95;
                        break;
                    case 1:
                        temperature = Temperature._60;
                        break;
                    case 2:
                        temperature = Temperature._40;
                        break;
                    case 3:
                        temperature = Temperature._30;
                }
            }
            public void onNothingSelected(AdapterView<?> arg0) {}
        });


        Button btn_search = findViewById(R.id.search_button);
        btn_search.setBackgroundColor(getColor(AppData.getModeColor()));

        // header back button onClick
        ImageButton backButton = (ImageButton) findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> {
            finish();
        });
    }

    //옷 색상추가 대화상자 열기
    public void open(View view) {
        CharSequence[] items = getResources().getStringArray(R.array.color_list);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ArrayList<ClothesColor> choice = new ArrayList<>();

        builder.setTitle("옷 색상을 선택하시오");
        builder.setMultiChoiceItems(R.array.color_list, null, new DialogInterface.OnMultiChoiceClickListener() {
            public void onClick(DialogInterface dialog, int item, boolean isChecked) {
                ClothesColor selected = ClothesColor.valueOf(items[item].toString().toUpperCase());

                if(isChecked) {
                    choice.add(selected);
                } else {
                    choice.remove(selected);
                }
            }
        });
        builder.setNeutralButton("완료", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which) {
//                colors = choice;
//                colorAdapter.update(colors.toArray(new String[0]));
                colors.clear();
                colors.addAll(choice);
                colorAdapter.notifyDataSetChanged();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //재질 검색 대화상자 열기
    public void onClick(View view) {
        Dialog searchtexture = new Dialog(this);
        searchtexture.setContentView(R.layout.custom_texture);
        searchtexture.setTitle("재질 검색");

        final EditText input = (EditText) searchtexture.findViewById(R.id.edit_texture);

        searchtexture.findViewById(R.id.btn_texture).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                textures.add(input.getText().toString().trim());
                textureAdapter.notifyDataSetChanged();
                searchtexture.dismiss();
            }
        });

        searchtexture.show();
    }

    //검색 버튼 명령어
    public void search(View v) {
        int colorsCount = colors.size();
        int textureCount = textures.size();

        Log.d("#####", "colors: " + colorsCount);
        Log.d("#####", "texture: " + textureCount);

        if(colorsCount == 0 && textureCount == 0) {
            // 재질, 색상 조건이 설정되어 있지 않으면
            AppData.getDb().clothesDao().findAll(
                    washingType,
                    washingPower,
                    detergent,
                    temperature
            )
                    .doOnSuccess(this::afterSearchQuery)
                    .doOnError(e -> Log.e("#####", e.toString()))
                    .subscribeOn(Schedulers.io()).subscribe();
        } else if(colorsCount == 0) {
            // 재질 조건만 설정되어 있으면
            AppData.getDb().clothesDao().findAllWithTexture(
                    washingType,
                    washingPower,
                    detergent,
                    temperature,
                    textures
            )
                    .doOnSuccess(this::afterSearchQuery)
                    .doOnError(e -> Log.e("#####", e.toString()))
                    .subscribeOn(Schedulers.io()).subscribe();
        } else if(textureCount == 0) {
            // 색상 조건만 설정되어 있으면
            AppData.getDb().clothesDao().findAllWithColors(
                    washingType,
                    washingPower,
                    detergent,
                    temperature,
                    colors
            )
                    .doOnSuccess(this::afterSearchQuery)
                    .doOnError(e -> Log.e("#####", e.toString()))
                    .subscribeOn(Schedulers.io()).subscribe();
        } else {
            // 재질, 색상 조건 모두 설정되어 있으면
            AppData.getDb().clothesDao().findAllWithColorsTexture(
                    washingType,
                    washingPower,
                    detergent,
                    temperature,
                    colors,
                    textures
            )
                    .doOnSuccess(this::afterSearchQuery)
                    .doOnError(e -> Log.e("#####", e.toString()))
                    .subscribeOn(Schedulers.io()).subscribe();
        }
    }

    private void afterSearchQuery(List<Clothes> list) {
        Intent intent = new Intent(this, ClosetActivity.class);

        Log.d("#####", "" + list.size());
        intent.putExtra("searched",(Serializable) list);
        setResult(RESULT_OK, intent);
        startActivity(intent);
    }

}