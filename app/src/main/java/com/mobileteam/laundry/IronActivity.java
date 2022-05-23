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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobileteam.laundry.adapter.ColorSearchAdapter;
import com.mobileteam.laundry.adapter.TextureSearchAdapter;
import com.mobileteam.laundry.domain.Clothes;
import com.mobileteam.laundry.domain.SerializableClothes;
import com.mobileteam.laundry.enums.ClothesColor;
import com.mobileteam.laundry.enums.Detergent;
import com.mobileteam.laundry.enums.Iron;
import com.mobileteam.laundry.enums.Temperature;
import com.mobileteam.laundry.enums.WashingPower;
import com.mobileteam.laundry.enums.WashingType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.schedulers.Schedulers;

public class IronActivity extends AppCompatActivity implements View.OnClickListener{
    ArrayList<String> textures = new ArrayList<>();
    ArrayList<ClothesColor> colors = new ArrayList<>();
    private Iron iron = Iron.HT;
    TextureSearchAdapter textureAdapter;
    ColorSearchAdapter colorAdapter;
    private int tmp_chk = 0;
    private int cover_chk = 0;

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

        TextView subtitle = (TextView) findViewById(R.id.condition_text_view);
        subtitle.setText("다림질 조건");

        //다림질 방법과 관련된 드롭 다운은 기존의 것을 수정해 사용함
        //사용하지 않는 드롭 다운
        TextView nonTxt = (TextView) findViewById(R.id.washing_power_text_view);
        nonTxt.setVisibility(View.INVISIBLE);
        Spinner nonSpin = (Spinner) findViewById(R.id.washing_power_spinner);
        nonSpin.setVisibility(View.INVISIBLE);

        //다림질 온도의 드롭 다운
        TextView tmpTxt = (TextView) findViewById(R.id.detergent_text_view);
        tmpTxt.setText("다림질 온도:");
        Spinner temperatureSpinner = (Spinner) findViewById(R.id.detergent_spinner);
        ArrayAdapter<CharSequence> temperature_adp = ArrayAdapter.createFromResource(
                this, R.array.irontmp_list, R.layout.spinner_item);
        temperature_adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        temperatureSpinner.setAdapter(temperature_adp);
        temperatureSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                switch (pos) {
                    case 0:
                        tmp_chk = 0;
                        break;
                    case 1:
                        tmp_chk = 1;
                        break;
                    case 2:
                        tmp_chk = 2;
                }
            }
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        //천 덮개 여부의 드롭 다운
        TextView covTxt = (TextView) findViewById(R.id.temperature_text_view);
        covTxt.setText("천 덮개:");
        Spinner coverSpinner = (Spinner) findViewById(R.id.temperature_spinner);
        ArrayAdapter<CharSequence> cover_adp = ArrayAdapter.createFromResource(
                this, R.array.cover_list, R.layout.spinner_item);
        cover_adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        coverSpinner.setAdapter(cover_adp);
        coverSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                switch (pos) {
                    case 0:
                        cover_chk = 0;
                        break;
                    case 1:
                        cover_chk = 1;
                }
            }
            public void onNothingSelected(AdapterView<?> arg0) {}
        });

        //다리미 가능여부의 드롭 다운
        TextView ableTxt = (TextView) findViewById(R.id.washing_type_text_view);
        ableTxt.setText("가능 여부:");
        Spinner ableSpinner = (Spinner) findViewById(R.id.washing_type_spinner);
        ArrayAdapter<CharSequence> able_adp = ArrayAdapter.createFromResource(
                this, R.array.ironable_list, R.layout.spinner_item);
        able_adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ableSpinner.setAdapter(able_adp);
        ableSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                switch(pos) {
                    case 0:
                        temperatureSpinner.setEnabled(true);
                        coverSpinner.setEnabled(true);
                        break;
                    case 1:
                        iron = Iron.NOT;
                        //다른 스피너 사용 불가
                        temperatureSpinner.setEnabled(false);
                        coverSpinner.setEnabled(false);
                        break;
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

    public void determine(int tmp, int cover) {
        if(tmp == 0) {
            if(cover == 0) {
                iron = Iron.HT;
            } else {
                iron = Iron.HT_WITH;
            }
        } else if(tmp == 1) {
            if (cover ==0) {
                iron = Iron.MT;
            } else {
                iron = Iron.MT_WITH;
            }
        } else {
            if (cover == 0) {
                iron = Iron.LT;
            } else {
                iron = Iron.LT_WITH;
            }
        }
    }

    //검색 버튼 명령어
    public void search(View v) {
        int colorsCount = colors.size();
        int textureCount = textures.size();
        determine(tmp_chk, cover_chk);

        Log.d("#####", "iron: " + iron);
        Log.d("#####", "colors: " + colorsCount);
        Log.d("#####", "texture: " + textureCount);

        if(colorsCount == 0 && textureCount == 0) {
            // 재질, 색상 조건이 설정되어 있지 않으면
            AppData.getDb().clothesDao().findIron(iron)
                    .doOnSuccess(this::afterSearchQuery)
                    .doOnError(e -> Log.e("#####", e.toString()))
                    .subscribeOn(Schedulers.io()).subscribe();
        } else if(colorsCount == 0) {
            // 재질 조건만 설정되어 있으면
            AppData.getDb().clothesDao().findIronWithTexture(iron, textures)
                    .doOnSuccess(this::afterSearchQuery)
                    .doOnError(e -> Log.e("#####", e.toString()))
                    .subscribeOn(Schedulers.io()).subscribe();
        } else if(textureCount == 0) {
            // 색상 조건만 설정되어 있으면
            AppData.getDb().clothesDao().findIronWithColors(iron, colors)
                    .doOnSuccess(this::afterSearchQuery)
                    .doOnError(e -> Log.e("#####", e.toString()))
                    .subscribeOn(Schedulers.io()).subscribe();
        } else {
            // 재질, 색상 조건 모두 설정되어 있으면
            AppData.getDb().clothesDao().findIronWithColorsTexture(iron, colors, textures)
                    .doOnSuccess(this::afterSearchQuery)
                    .doOnError(e -> Log.e("#####", e.toString()))
                    .subscribeOn(Schedulers.io()).subscribe();
        }
    }

    private void afterSearchQuery(List<Clothes> list) {
        final List<SerializableClothes> serializableClothesList = list.stream().map(SerializableClothes::new).collect(Collectors.toList());

        runOnUiThread(() -> {
            Intent intent = new Intent(this, ClosetActivity.class);

            Log.d("#####", "" + serializableClothesList.size());
            intent.putExtra("searched",(Serializable) serializableClothesList);
            setResult(RESULT_OK, intent);
            startActivity(intent);
        });
    }



}