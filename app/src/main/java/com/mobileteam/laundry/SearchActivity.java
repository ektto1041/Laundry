package com.mobileteam.laundry;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener{
    ArrayList<String> textures = new ArrayList<String>();
    ArrayList<String> colors = new ArrayList<String>();
    TextureSearchAdapter textureAdapter;
    ColorSearchAdapter colorAdapter;
    boolean txtfound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //옷 재질을 표시해주는 리사이클러 뷰
        RecyclerView textureview = findViewById(R.id.textureview);
        int colums1 = 4;
        textureview.setLayoutManager(new GridLayoutManager(this, colums1));
        textureAdapter = new TextureSearchAdapter(this, textures);
        textureview.setAdapter(textureAdapter);

        //옷 색상을 표시해주는 리사이클러 뷰
        RecyclerView colorview = findViewById(R.id.colorview);
        int colums2 = 9;
        colorview.setLayoutManager(new GridLayoutManager(this, colums2));
        colorAdapter = new ColorSearchAdapter(this, colors);
        colorview.setAdapter(colorAdapter);


        //세탁 방법의 드롭 다운
        Spinner how = (Spinner) findViewById(R.id.lst_how);
        ArrayAdapter<CharSequence> how_adp = ArrayAdapter.createFromResource(
                this, R.array.how_list, R.layout.spinner_item);
        how_adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        how.setAdapter(how_adp);
        how.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            }
            public void onNothingSelected(AdapterView<?> arg0) {}
        });

        //세탁 강도의 드롭 다운
        Spinner strength = (Spinner) findViewById(R.id.lst_strength);
        ArrayAdapter<CharSequence> strength_adp = ArrayAdapter.createFromResource(
                this, R.array.strength_list, R.layout.spinner_item);
        strength_adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        strength.setAdapter(strength_adp);
        strength.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            }
            public void onNothingSelected(AdapterView<?> arg0) {}
        });

        //세제 종류의 드롭 다운
        Spinner detergent = (Spinner) findViewById(R.id.lst_detergent);
        ArrayAdapter<CharSequence> detergent_adp = ArrayAdapter.createFromResource(
                this, R.array.detergent_list, R.layout.spinner_item);
        detergent_adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        detergent.setAdapter(detergent_adp);
        detergent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            }
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        //물 온도의 드롭 다운
        Spinner temperature = (Spinner) findViewById(R.id.lst_temperature);
        ArrayAdapter<CharSequence> temperature_adp = ArrayAdapter.createFromResource(
                this, R.array.temperature_list, R.layout.spinner_item);
        temperature_adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        temperature.setAdapter(temperature_adp);
        temperature.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            }
            public void onNothingSelected(AdapterView<?> arg0) {}
        });
    }

    //옷 색상추가 대화상자 열기
    public void open(View view) {
        CharSequence[] items = getResources().getStringArray(R.array.color_list);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ArrayList<String> choice = new ArrayList<String>();

        builder.setTitle("옷 색상을 선택하시오");
        builder.setMultiChoiceItems(R.array.color_list, null, new DialogInterface.OnMultiChoiceClickListener() {
            public void onClick(DialogInterface dialog, int item, boolean isChecked) {
                if(isChecked) {
                    choice.add(items[item].toString());
                } else {
                    choice.remove(items[item].toString());
                }
            }
        });
        builder.setNeutralButton("완료", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which) {
                colors = choice;
                colorAdapter.update(colors.toArray(new String[0]));
                colorAdapter.notifyDataSetChanged();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void onClick(View view) {
        Dialog searchtexture = new Dialog(this);
        searchtexture.setContentView(R.layout.custom_texture);
        searchtexture.setTitle("재질 검색");
        CharSequence[] texturelist = getResources().getStringArray(R.array.texture_list);
        Toast.makeText(getApplicationContext(), texturelist[0], Toast.LENGTH_LONG);
        Button search = searchtexture.findViewById(R.id.btn_texture);
        ArrayList<String> choice = new ArrayList<String>();
        final EditText input = (EditText) searchtexture.findViewById(R.id.edit_texture);

        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                for(int i = 0; i < texturelist.length; i++) {
                    if(texturelist[i].toString().equals(input.getText().toString().trim())){
                        textures.add(texturelist[i].toString());
                        txtfound = true;
                        break;
                    }
                }
                if(!txtfound) {
                    Toast.makeText(getApplicationContext(), "지원하지 않는 재질입니다.", Toast.LENGTH_LONG).show();
                    searchtexture.dismiss();
                }
                txtfound = false;
                textureAdapter.update(textures);
                textureAdapter.notifyDataSetChanged();
                searchtexture.dismiss();
            }
        });
        searchtexture.show();
    }
}