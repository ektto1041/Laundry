
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
        import com.mobileteam.laundry.enums.Temperature;
        import com.mobileteam.laundry.enums.TotalDry;
        import com.mobileteam.laundry.enums.WashingPower;
        import com.mobileteam.laundry.enums.WashingType;
        import com.mobileteam.laundry.enums.WeaveDry;


        import java.io.Serializable;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.stream.Collectors;

        import io.reactivex.rxjava3.schedulers.Schedulers;

public class DryActivity extends AppCompatActivity implements View.OnClickListener {
    ArrayList<String> textures = new ArrayList<>();
    ArrayList<ClothesColor> colors = new ArrayList<>();
    private TotalDry totalDry = TotalDry.HOOK;
    private WeaveDry weave = WeaveDry.HD;
    TextureSearchAdapter textureAdapter;
    ColorSearchAdapter colorAdapter;
    private int machineDry = 0;
    private int handDry = 0;
    private int hangDry = 0;
    private int sunDry = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        View header = findViewById(R.id.header);
        header.setBackgroundColor(getColor(AppData.getModeColor()));

        //??? ????????? ??????????????? ??????????????? ???
        RecyclerView textureview = findViewById(R.id.texture_recycler_view);
        int colums1 = 4;
        textureview.setLayoutManager(new GridLayoutManager(this, colums1));
        textureAdapter = new TextureSearchAdapter(this, textures, -1);
        textureview.setAdapter(textureAdapter);

        //??? ????????? ??????????????? ??????????????? ???
        RecyclerView colorview = findViewById(R.id.color_recycler_view);
        int colums2 = 9;
        colorview.setLayoutManager(new GridLayoutManager(this, colums2));
        colorAdapter = new ColorSearchAdapter(this, colors);
        colorview.setAdapter(colorAdapter);

        TextView subtitle = (TextView) findViewById(R.id.condition_text_view);
        subtitle.setText("?????? ??????");


        //????????? ?????? (????????? ??????, ????????? ??????)
        TextView hangTxt = (TextView) findViewById(R.id.washing_power_text_view);
        hangTxt.setText("????????? ??????:");
        Spinner strength = (Spinner) findViewById(R.id.washing_power_spinner);
        ArrayAdapter<CharSequence> strength_adp = ArrayAdapter.createFromResource(
                this, R.array.hang_list, R.layout.spinner_item);
        strength_adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        strength.setAdapter(strength_adp);
        strength.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                switch (pos) {
                    case 0:
                        hangDry = 0;
                        break;
                    case 1:
                        hangDry = 1;
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        //?????? ?????? (?????? ??????, ?????? ??????)
        TextView sunTxt = (TextView) findViewById(R.id.detergent_text_view);
        sunTxt.setText("?????? ??????:");
        Spinner detergentSpinner = (Spinner) findViewById(R.id.detergent_spinner);
        ArrayAdapter<CharSequence> detergent_adp = ArrayAdapter.createFromResource(
                this, R.array.sun_list, R.layout.spinner_item);
        detergent_adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        detergentSpinner.setAdapter(detergent_adp);
        detergentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                switch (pos) {
                    case 0:
                        sunDry = 0;
                        break;
                    case 1:
                        sunDry = 1;
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        //??? ??????
        TextView handTxt = (TextView) findViewById(R.id.temperature_text_view);
        handTxt.setText("?????????:");
        Spinner temperatureSpinner = (Spinner) findViewById(R.id.temperature_spinner);
        ArrayAdapter<CharSequence> temperature_adp = ArrayAdapter.createFromResource(
                this, R.array.hand_list, R.layout.spinner_item);
        temperature_adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        temperatureSpinner.setAdapter(temperature_adp);
        temperatureSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                switch (pos) {
                    case 0:
                        weave = WeaveDry.HD;
                        break;
                    case 1:
                        weave = WeaveDry.HD_NOT;
                        break;
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        //?????? ?????? ?????? ?????????
        TextView machine = (TextView) findViewById(R.id.washing_type_text_view);
        machine.setText("???????????? ??????:");
        Spinner how = (Spinner) findViewById(R.id.washing_type_spinner);
        ArrayAdapter<CharSequence> how_adp = ArrayAdapter.createFromResource(
                this, R.array.machine_list, R.layout.spinner_item);
        how_adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        how.setAdapter(how_adp);
        how.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                switch (pos) {
                    case 0:
                        machineDry = 0;
                        break;
                    case 1:
                        machineDry = 1;
                        break;
                    case 2:
                        machineDry = 2;
                        break;
                }

                if(machineDry != 2) {
                    strength.setEnabled(false);
                    detergentSpinner.setEnabled(false);
                    temperatureSpinner.setEnabled(false);

                } else {
                    strength.setEnabled(true);
                    detergentSpinner.setEnabled(true);
                    temperatureSpinner.setEnabled(true);
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        Button btn_search = findViewById(R.id.search_button);
        btn_search.setBackgroundColor(getColor(AppData.getModeColor()));

        // header back button onClick
        ImageButton backButton = (ImageButton) findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> {
            finish();
        });
    }

    //??? ???????????? ???????????? ??????
    public void open(View view) {
        CharSequence[] items = getResources().getStringArray(R.array.color_list);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ArrayList<ClothesColor> choice = new ArrayList<>();

        builder.setTitle("??? ????????? ???????????????");
        builder.setMultiChoiceItems(R.array.color_list, null, new DialogInterface.OnMultiChoiceClickListener() {
            public void onClick(DialogInterface dialog, int item, boolean isChecked) {
                ClothesColor selected = ClothesColor.valueOf(items[item].toString().toUpperCase());

                if (isChecked) {
                    choice.add(selected);
                } else {
                    choice.remove(selected);
                }
            }
        });
        builder.setNeutralButton("??????", new DialogInterface.OnClickListener() {
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

    //?????? ?????? ???????????? ??????
    public void onClick(View view) {
        Dialog searchtexture = new Dialog(this);
        searchtexture.setContentView(R.layout.custom_texture);
        searchtexture.setTitle("?????? ??????");

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

    public void determine() {

        if(machineDry == 2) {
            if (sunDry == 0) {
                if (hangDry == 0)
                    totalDry = totalDry.HOOK;
                else
                    totalDry = totalDry.HOOK_UNDER;
            }
            if (sunDry == 1) {
                if (hangDry == 0)
                    totalDry = totalDry.LIE;
                else
                    totalDry = totalDry.LIE_UNDER;
            }
        if(machineDry == 0) {
            totalDry = TotalDry.MC;

        }
        else
            totalDry = TotalDry.MC_NOT;


        }
    }








    //?????? ?????? ?????????
    public void search(View v) {
        int colorsCount = colors.size();
        int textureCount = textures.size();


        Log.d("#####", "machineDry: " + machineDry);
        Log.d("#####", "hangDry: " + hangDry);
        Log.d("#####", "sunDry: " + sunDry);
        Log.d("#####", "handDry: " + handDry);
        Log.d("#####", "colors: " + colorsCount);
        Log.d("#####", "texture: " + textureCount);

        if (colorsCount == 0 && textureCount == 0) {
            // ??????, ?????? ????????? ???????????? ?????? ?????????
            AppData.getDb().clothesDao().findDry(
                    totalDry,
                    weave
            )
                    .doOnSuccess(this::afterSearchQuery)
                    .doOnError(e -> Log.e("#####", e.toString()))
                    .subscribeOn(Schedulers.io()).subscribe();
        } else if (colorsCount == 0) {
            // ?????? ????????? ???????????? ?????????
            AppData.getDb().clothesDao().findDryWithTexture(
                    totalDry,
                    weave,
                    textures
            )
                    .doOnSuccess(this::afterSearchQuery)
                    .doOnError(e -> Log.e("#####", e.toString()))
                    .subscribeOn(Schedulers.io()).subscribe();
        } else if (textureCount == 0) {
            // ?????? ????????? ???????????? ?????????
            AppData.getDb().clothesDao().findDryWithColors(
                    totalDry,
                    weave,
                    colors
            )
                    .doOnSuccess(this::afterSearchQuery)
                    .doOnError(e -> Log.e("#####", e.toString()))
                    .subscribeOn(Schedulers.io()).subscribe();
        } else {
            // ??????, ?????? ?????? ?????? ???????????? ?????????
            AppData.getDb().clothesDao().findDryWithColorsTexture(
                    totalDry,
                    weave,
                    colors,
                    textures
            )
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
            intent.putExtra("searched", (Serializable) serializableClothesList);
            setResult(RESULT_OK, intent);
            startActivity(intent);
        });
    }

}