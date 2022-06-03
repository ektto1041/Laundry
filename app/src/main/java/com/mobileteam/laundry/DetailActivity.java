package com.mobileteam.laundry;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mobileteam.laundry.adapter.TextureSearchAdapter;
import com.mobileteam.laundry.domain.Clothes;
import com.mobileteam.laundry.domain.SerializableClothes;
import com.mobileteam.laundry.domain.Texture;
import com.mobileteam.laundry.enums.Bleach;
import com.mobileteam.laundry.enums.ClothesColor;
import com.mobileteam.laundry.enums.Detergent;
import com.mobileteam.laundry.enums.Dry;
import com.mobileteam.laundry.enums.DryClean;
import com.mobileteam.laundry.enums.Iron;
import com.mobileteam.laundry.enums.Temperature;
import com.mobileteam.laundry.enums.WashingPower;
import com.mobileteam.laundry.enums.WashingType;
import com.mobileteam.laundry.enums.Weave;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.schedulers.Schedulers;

public class DetailActivity extends AppCompatActivity {

    private Clothes data;                                   // 현재 표시되고 있는 Clothes 데이터
    private ArrayList<String> textures = new ArrayList<>(); // 재질 리사이클러뷰에 표시될 데이터
    private TextureSearchAdapter textureAdapter;            // 재질 리사이클러뷰에 사용될 어댑터

    private ImageButton imageView;                          // 옷 이미지 뷰
    private ImageButton colorButton;                        // 색상 버튼
    private Dialog colorDialog;                             // 색상 선택 다이얼로그
    private Button addTextureButton;                        // 재질 추가 버튼
    private Dialog textureDialog;                           // 재질 추가 다이얼로그
    private ImageButton washingMethodButton;                // 세탁 방법 기호 버튼
    private Dialog washingMethodDialog;                     // 세탁 방법 기호 다이얼로그
    private ImageButton bleachButton;                       // 표백 방법 기호 버튼
    private Dialog bleachDialog;                            // 표백 방법 기호 다이얼로그
    private ImageButton ironButton;                         // 다림질 기호 버튼
    private Dialog ironDialog;                              // 다림질 기호 다이얼로그
    private ImageButton dryCleanButton;                     // 드라이클리닝 기호 버튼
    private Dialog dryCleanDialog;                          // 드라이클리닝 기호 다이얼로그
    private ImageButton weaveButton;                        // 짜는 방법 기호 버튼
    private Dialog weaveDialog;                             // 짜는 방법 기호 다이얼로그
    private ImageButton dryButton;                          // 건조 기호 버튼
    private Dialog dryDialog;                               // 건조 기호 다이얼로그

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
                                runOnUiThread(() -> {
                                    setClothes(clothes, textureList);
                                });
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

        imageView = (ImageButton) findViewById(R.id.image_view);
        colorButton = (ImageButton) findViewById(R.id.color_button);
        addTextureButton = (Button) findViewById(R.id.add_texture_button);
        washingMethodButton = (ImageButton) findViewById(R.id.washing_method_button);
        bleachButton = (ImageButton) findViewById(R.id.bleach_button);
        ironButton = (ImageButton) findViewById(R.id.iron_button);
        dryCleanButton = (ImageButton) findViewById(R.id.dry_clean_button);
        dryButton = (ImageButton) findViewById(R.id.dry_button);
        weaveButton = (ImageButton) findViewById(R.id.weave_button);

        //옷 재질을 표시해주는 리사이클러 뷰
        RecyclerView textureRecyclerView = findViewById(R.id.texture_recycler_view);
        textureRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        textureAdapter = new TextureSearchAdapter(this, textures, -1);
        textureRecyclerView.setAdapter(textureAdapter);
    }

    private void buttonSetting() {
        // 뒤로가기 버튼
        ImageButton backButton = (ImageButton) findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());

        // 동시 세탁 버튼
        ImageButton laundryWithButton = (ImageButton) findViewById(R.id.laundry_with_button);
        laundryWithButton.setOnClickListener(v -> {
            AppData.getDb().clothesDao().findAll(
                    data.getWashingType(),
                    data.getWashingPower(),
                    data.getDetergent(),
                    data.getTemperature()
            )
                    .doOnSuccess(this::afterSearchQuery)
                    .doOnError(e -> Log.e("#####", e.toString()))
                    .subscribeOn(Schedulers.io()).subscribe();
        });

        // 삭제 버튼
        ImageButton deleteButton = (ImageButton) findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(v -> {
            AppData.getDb().textureDao().delete(data.getId())
                    .doOnSuccess(id -> {
                        AppData.getDb().clothesDao().delete(data.getId())
                                .doOnSuccess(id_ -> {
                                    runOnUiThread(this::finish);
                                })
                                .doOnError(e -> Log.e("#####", e.toString()))
                                .subscribeOn(Schedulers.io()).subscribe();
                    })
                    .doOnError(e -> Log.e("#####", e.toString()))
                    .subscribeOn(Schedulers.io()).subscribe();
        });

        // 옷 이미지 변경
        imageView.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("image/*");
            intent.putExtra("crop", true);
            intent.putExtra("scale", true);
            startActivityForResult(intent, 99);
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

        textureDialog.findViewById(R.id.btn_texture).setOnClickListener(this::textureDialogButtonOnClickListener);

        addTextureButton.setOnClickListener(v -> textureDialog.show());

        // Washing Method 버튼
        washingMethodDialog = new Dialog(this);
        washingMethodDialog.setContentView(R.layout.dialog_washing_method);
        washingMethodDialog.setTitle("세탁 기호 선택");

        washingMethodDialog.findViewById(R.id._95_button).setOnClickListener(this::washingMethodDialogButtonOnClickListener);
        washingMethodDialog.findViewById(R.id._60_button).setOnClickListener(this::washingMethodDialogButtonOnClickListener);
        washingMethodDialog.findViewById(R.id._40_button).setOnClickListener(this::washingMethodDialogButtonOnClickListener);
        washingMethodDialog.findViewById(R.id.weak_40_button).setOnClickListener(this::washingMethodDialogButtonOnClickListener);
        washingMethodDialog.findViewById(R.id._30_button).setOnClickListener(this::washingMethodDialogButtonOnClickListener);
        washingMethodDialog.findViewById(R.id.hand_30_button).setOnClickListener(this::washingMethodDialogButtonOnClickListener);
        washingMethodDialog.findViewById(R.id.no_water_button).setOnClickListener(this::washingMethodDialogButtonOnClickListener);

        washingMethodButton.setOnClickListener(v -> washingMethodDialog.show());

        // Bleach 버튼
        bleachDialog = new Dialog(this);
        bleachDialog.setContentView(R.layout.dialog_bleach);
        bleachDialog.setTitle("세탁 기호 선택");

        bleachDialog.findViewById(R.id.chlorine_button).setOnClickListener(this::bleachDialogButtonOnClickListener);
        bleachDialog.findViewById(R.id.not_chlorine_button).setOnClickListener(this::bleachDialogButtonOnClickListener);
        bleachDialog.findViewById(R.id.oxygen_button).setOnClickListener(this::bleachDialogButtonOnClickListener);
        bleachDialog.findViewById(R.id.not_oxygen_button).setOnClickListener(this::bleachDialogButtonOnClickListener);
        bleachDialog.findViewById(R.id.all_button).setOnClickListener(this::bleachDialogButtonOnClickListener);
        bleachDialog.findViewById(R.id.not_all_button).setOnClickListener(this::bleachDialogButtonOnClickListener);

        bleachButton.setOnClickListener(v -> bleachDialog.show());

        // Iron 버튼
        ironDialog = new Dialog(this);
        ironDialog.setContentView(R.layout.dialog_iron);
        ironDialog.setTitle("세탁 기호 선택");

        ironDialog.findViewById(R.id.ht_button).setOnClickListener(this::ironDialogButtonOnClickListener);
        ironDialog.findViewById(R.id.ht_with_button).setOnClickListener(this::ironDialogButtonOnClickListener);
        ironDialog.findViewById(R.id.mt_button).setOnClickListener(this::ironDialogButtonOnClickListener);
        ironDialog.findViewById(R.id.mt_with_button).setOnClickListener(this::ironDialogButtonOnClickListener);
        ironDialog.findViewById(R.id.lt_button).setOnClickListener(this::ironDialogButtonOnClickListener);
        ironDialog.findViewById(R.id.lt_with_button).setOnClickListener(this::ironDialogButtonOnClickListener);
        ironDialog.findViewById(R.id.not_button).setOnClickListener(this::ironDialogButtonOnClickListener);

        ironButton.setOnClickListener(v -> ironDialog.show());

        // DryClean 버튼
        dryCleanDialog = new Dialog(this);
        dryCleanDialog.setContentView(R.layout.dialog_dry_clean);
        dryCleanDialog.setTitle("세탁 기호 선택");

        dryCleanDialog.findViewById(R.id.dc_any_button).setOnClickListener(this::dryCleanDialogButtonOnClickListener);
        dryCleanDialog.findViewById(R.id.dc_oil_button).setOnClickListener(this::dryCleanDialogButtonOnClickListener);
        dryCleanDialog.findViewById(R.id.dc_pro_button).setOnClickListener(this::dryCleanDialogButtonOnClickListener);
        dryCleanDialog.findViewById(R.id.dc_not_button).setOnClickListener(this::dryCleanDialogButtonOnClickListener);

        dryCleanButton.setOnClickListener(v -> dryCleanDialog.show());

        // Weave 버튼
        weaveDialog = new Dialog(this);
        weaveDialog.setContentView(R.layout.dialog_weave);
        weaveDialog.setTitle("세탁 기호 선택");

        weaveDialog.findViewById(R.id.wv_mc_button).setOnClickListener(this::weaveDialogButtonOnClickListener);
        weaveDialog.findViewById(R.id.wv_mc_not_button).setOnClickListener(this::weaveDialogButtonOnClickListener);
        weaveDialog.findViewById(R.id.wv_hd_button).setOnClickListener(this::weaveDialogButtonOnClickListener);
        weaveDialog.findViewById(R.id.wv_hd_not_button).setOnClickListener(this::weaveDialogButtonOnClickListener);

        weaveButton.setOnClickListener(v -> weaveDialog.show());

        // Dry 버튼
        dryDialog = new Dialog(this);
        dryDialog.setContentView(R.layout.dialog_dry);
        dryDialog.setTitle("세탁 기호 선택");

        dryDialog.findViewById(R.id.dry_hook_button).setOnClickListener(this::dryDialogButtonOnClickListener);
        dryDialog.findViewById(R.id.dry_hook_under_button).setOnClickListener(this::dryDialogButtonOnClickListener);
        dryDialog.findViewById(R.id.dry_lie_button).setOnClickListener(this::dryDialogButtonOnClickListener);
        dryDialog.findViewById(R.id.dry_lie_under_button).setOnClickListener(this::dryDialogButtonOnClickListener);

        dryButton.setOnClickListener(v -> dryDialog.show());
    }

    private void setClothes(Clothes clothes, List<Texture> textureList) {
        // 옷 이미지
        Bitmap image = clothes.getImage();
        if(image != null) {
            imageView.setImageBitmap(image);
        }

        // 색상
        ClothesColor clothesColor = clothes.getClothesColor();
        if(clothesColor != null) {
            int color = clothes.getClothesColor().getColor(getApplicationContext());
            colorButton.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }

        // 재질
        refreshTextures(textureList);
        textureAdapter.setClothesId(clothes.getId());

        // 세탁 기호 - Washing Method
        Temperature temperature = clothes.getTemperature();
        WashingType washingType = clothes.getWashingType();
        if(temperature == null) {
            if(washingType == null) washingMethodButton.setImageResource(R.drawable.ic_add_circle);
            else washingMethodButton.setImageResource(R.drawable.ic_no_water);
        } else {
            switch (temperature) {
                case _95:
                    washingMethodButton.setImageResource(R.drawable.ic_tp_95);
                    break;
                case _60:
                    washingMethodButton.setImageResource(R.drawable.ic_tp_60);
                    break;
                case _40:
                    WashingPower washingPower = clothes.getWashingPower();
                    if(washingPower == WashingPower.WEAK) washingMethodButton.setImageResource(R.drawable.ic_tp_40_weak);
                    else washingMethodButton.setImageResource(R.drawable.ic_tp_40);
                    break;
                case _30:
                    if(washingType == WashingType.WASHER) washingMethodButton.setImageResource(R.drawable.ic_tp_30_weak_neutral);
                    else washingMethodButton.setImageResource(R.drawable.ic_hand_30_weak_neutral);
            }
        }

        // 세탁 기호 - Bleach
        Bleach bleach = clothes.getBleach();
        if(bleach == null) bleachButton.setImageResource(R.drawable.ic_add_circle);
        else bleachButton.setImageResource(bleach.getResource());

        // 세탁 기호 - Iron
        Iron iron = clothes.getIron();
        if(iron == null) ironButton.setImageResource(R.drawable.ic_add_circle);
        else ironButton.setImageResource(iron.getResource());

        // 세탁 기호 - DryClean
        DryClean dryClean = clothes.getDryClean();
        if(dryClean == null) dryCleanButton.setImageResource(R.drawable.ic_add_circle);
        else dryCleanButton.setImageResource(dryClean.getResource());

        // 세탁 기호 - Weave
        Weave weave = clothes.getWeave();
        if(weave == null) weaveButton.setImageResource(R.drawable.ic_add_circle);
        else weaveButton.setImageResource(weave.getResource());

        // 세탁 기호 - Dry
        Dry dry = clothes.getDry();
        if(dry == null) dryButton.setImageResource(R.drawable.ic_add_circle);
        else dryButton.setImageResource(dry.getResource());
    }

    private void refreshTextures(List<Texture> textureList) {
        List<String> textureStrList = textureList.stream().map(Texture::getName).collect(Collectors.toList());
        textures.clear();
        textures.addAll(textureStrList);
        textureAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent dataIntent) {
        super.onActivityResult(requestCode, resultCode, dataIntent);

        if(requestCode == 99 && resultCode == RESULT_OK) {
            try {
                Uri uri = dataIntent.getData();

                Glide.with(getApplicationContext()).load(uri).into(imageView);

                Bitmap bitmap = null;
                try {
                    bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(getContentResolver(), uri));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(bitmap == null) return;
                AppData.getDb().clothesDao().updateImage(data.getId(), bitmap)
                        .doOnSuccess(id -> {})
                        .doOnError(e -> Log.e("#####", e.toString()))
                        .subscribeOn(Schedulers.io()).subscribe();

            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  다이얼로그 내 버튼 클릭 시 호출되는 메소드들
     */
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
        AppData.getDb().clothesDao().updateColor(data.getId(), color)
                .doOnSuccess(clothesId -> {
                    runOnUiThread(colorDialog::dismiss);
                })
                .doOnError(e -> Log.e("#####", e.toString()))
                .subscribeOn(Schedulers.io()).subscribe();
    }

    private void textureDialogButtonOnClickListener(View v) {
        EditText editText = (EditText) textureDialog.findViewById(R.id.edit_texture);
        String texture = editText.getText().toString().trim();

        textures.add(texture);
        textureAdapter.notifyDataSetChanged();

        AppData.getDb().textureDao().insert(new Texture(data.getId(), texture))
                .doOnSuccess(id -> {
                    runOnUiThread(textureDialog::dismiss);
                })
                .doOnError(e -> Log.e("#####", e.toString()))
                .subscribeOn(Schedulers.io()).subscribe();
    }

    private void washingMethodDialogButtonOnClickListener(View v) {
        int imgResource = -1;

        WashingType washingType = null;
        WashingPower washingPower = null;
        Temperature temperature = null;
        Detergent detergent = null;

        switch (v.getId()) {
            case R.id._95_button:
                imgResource = R.drawable.ic_tp_95;
                washingType = WashingType.WASHER;
                washingPower = WashingPower.STRONG;
                temperature = Temperature._95;
                detergent = Detergent.ANY;
                break;
            case R.id._60_button:
                imgResource = R.drawable.ic_tp_60;
                washingType = WashingType.WASHER;
                washingPower = WashingPower.STRONG;
                temperature = Temperature._60;
                detergent = Detergent.ANY;
                break;
            case R.id._40_button:
                imgResource = R.drawable.ic_tp_40;
                washingType = WashingType.WASHER;
                washingPower = WashingPower.STRONG;
                temperature = Temperature._40;
                detergent = Detergent.ANY;
                break;
            case R.id.weak_40_button:
                imgResource = R.drawable.ic_tp_40_weak;
                washingType = WashingType.WASHER;
                washingPower = WashingPower.WEAK;
                temperature = Temperature._40;
                detergent = Detergent.ANY;
                break;
            case R.id._30_button:
                imgResource = R.drawable.ic_tp_30_weak_neutral;
                washingType = WashingType.WASHER;
                washingPower = WashingPower.WEAK;
                temperature = Temperature._30;
                detergent = Detergent.NEUTRAL;
                break;
            case R.id.hand_30_button:
                imgResource = R.drawable.ic_hand_30_weak_neutral;
                washingType = WashingType.WATER;
                washingPower = WashingPower.WEAK;
                temperature = Temperature._30;
                detergent = Detergent.NEUTRAL;
                break;
            case R.id.no_water_button:
                imgResource = R.drawable.ic_no_water;
                washingType = WashingType.NO_WATER;
        }

        // 레이아웃 수정
        washingMethodButton.setImageResource(imgResource);
        washingMethodDialog.dismiss();

        Log.d("#####", washingType.name());

        // DB 작업
        AppData.getDb().clothesDao().updateWashingMethod(data.getId(), washingType, washingPower, temperature, detergent)
                .doOnSuccess(id -> { Log.d("#####", "update finish"); })
                .doOnError(e -> Log.e("#####", e.toString()))
                .subscribeOn(Schedulers.io()).subscribe();
    }

    private void bleachDialogButtonOnClickListener(View v) {
        int imgResource = -1;

        Bleach bleach = null;

        switch (v.getId()) {
            case R.id.chlorine_button:
                imgResource = R.drawable.ic_bleach_chlorine;
                bleach = Bleach.CHLORINE;
                break;
            case R.id.not_chlorine_button:
                imgResource = R.drawable.ic_bleach_not_chlorine;
                bleach = Bleach.NOT_CHLORINE;
                break;
            case R.id.oxygen_button:
                imgResource = R.drawable.ic_bleach_oxygen;
                bleach = Bleach.OXYGEN;
                break;
            case R.id.not_oxygen_button:
                imgResource = R.drawable.ic_bleach_not_oxygen;
                bleach = Bleach.NOT_OXYGEN;
                break;
            case R.id.all_button:
                imgResource = R.drawable.ic_bleach_all;
                bleach = Bleach.ALL;
                break;
            case R.id.not_all_button:
                imgResource = R.drawable.ic_bleach_not_all;
                bleach = Bleach.NOT_ALL;
        }

        // 레이아웃 수정
        bleachButton.setImageResource(imgResource);
        bleachDialog.dismiss();

        // DB 작업
        AppData.getDb().clothesDao().updateBleach(data.getId(), bleach)
                .doOnSuccess(id -> { Log.d("#####", "update finish"); })
                .doOnError(e -> Log.e("#####", e.toString()))
                .subscribeOn(Schedulers.io()).subscribe();
    }

    private void ironDialogButtonOnClickListener(View v) {
        int imgResource = -1;

        Iron iron = null;

        switch (v.getId()) {
            case R.id.ht_button:
                imgResource = R.drawable.ic_iron_ht;
                iron = Iron.HT;
                break;
            case R.id.ht_with_button:
                imgResource = R.drawable.ic_iron_ht_with;
                iron = Iron.HT_WITH;
                break;
            case R.id.mt_button:
                imgResource = R.drawable.ic_iron_mt;
                iron = Iron.MT;
                break;
            case R.id.mt_with_button:
                imgResource = R.drawable.ic_iron_mt_with;
                iron = Iron.MT_WITH;
                break;
            case R.id.lt_button:
                imgResource = R.drawable.ic_iron_lt;
                iron = Iron.LT;
                break;
            case R.id.lt_with_button:
                imgResource = R.drawable.ic_iron_lt_with;
                iron = Iron.LT_WITH;
                break;
            case R.id.not_button:
                imgResource = R.drawable.ic_iron_not;
                iron = Iron.NOT;
        }

        // 레이아웃 수정
        ironButton.setImageResource(imgResource);
        ironDialog.dismiss();

        // DB 작업
        AppData.getDb().clothesDao().updateIron(data.getId(), iron)
                .doOnSuccess(id -> { Log.d("#####", "update finish"); })
                .doOnError(e -> Log.e("#####", e.toString()))
                .subscribeOn(Schedulers.io()).subscribe();
    }

    private void dryCleanDialogButtonOnClickListener(View v) {
        int imgResource = -1;

        DryClean dryClean = null;

        switch (v.getId()) {
            case R.id.dc_any_button:
                imgResource = R.drawable.ic_dc;
                dryClean = DryClean.ANY;
                break;
            case R.id.dc_oil_button:
                imgResource = R.drawable.ic_dc_oil;
                dryClean = DryClean.OIL;
                break;
            case R.id.dc_pro_button:
                imgResource = R.drawable.ic_dc_pro;
                dryClean = DryClean.PRO;
                break;
            case R.id.dc_not_button:
                imgResource = R.drawable.ic_dc_not;
                dryClean = DryClean.NOT;
        }

        // 레이아웃 수정
        dryCleanButton.setImageResource(imgResource);
        dryCleanDialog.dismiss();

        // DB 작업
        AppData.getDb().clothesDao().updateDryClean(data.getId(), dryClean)
                .doOnSuccess(id -> { Log.d("#####", "update finish"); })
                .doOnError(e -> Log.e("#####", e.toString()))
                .subscribeOn(Schedulers.io()).subscribe();
    }

    private void weaveDialogButtonOnClickListener(View v) {
        int imgResource = -1;

        Weave weave = null;

        switch (v.getId()) {
            case R.id.wv_mc_button:
                imgResource = R.drawable.ic_wv_mc;
                weave = Weave.MC;
                break;
            case R.id.wv_mc_not_button:
                imgResource = R.drawable.ic_wv_mc_not;
                weave = Weave.MC_NOT;
                break;
            case R.id.wv_hd_button:
                imgResource = R.drawable.ic_wv_hd;
                weave = Weave.HD;
                break;
            case R.id.wv_hd_not_button:
                imgResource = R.drawable.ic_wv_hd_not;
                weave = Weave.HD_NOT;
        }

        // 레이아웃 수정
        weaveButton.setImageResource(imgResource);
        weaveDialog.dismiss();

        // DB 작업
        AppData.getDb().clothesDao().updateWeave(data.getId(), weave)
                .doOnSuccess(id -> { Log.d("#####", "update finish"); })
                .doOnError(e -> Log.e("#####", e.toString()))
                .subscribeOn(Schedulers.io()).subscribe();
    }

    private void dryDialogButtonOnClickListener(View v) {
        int imgResource = -1;

        Dry dry = null;

        switch (v.getId()) {
            case R.id.dry_hook_button:
                imgResource = R.drawable.ic_dry_hook;
                dry = Dry.HOOK;
                break;
            case R.id.dry_hook_under_button:
                imgResource = R.drawable.ic_dry_hook_under;
                dry = Dry.HOOK_UNDER;
                break;
            case R.id.dry_lie_button:
                imgResource = R.drawable.ic_dry_lie;
                dry = Dry.LIE;
                break;
            case R.id.dry_lie_under_button:
                imgResource = R.drawable.ic_dry_lie_under;
                dry = Dry.LIE_UNDER;
        }

        // 레이아웃 수정
        dryButton.setImageResource(imgResource);
        dryDialog.dismiss();

        // DB 작업
        AppData.getDb().clothesDao().updateDry(data.getId(), dry)
                .doOnSuccess(id -> { Log.d("#####", "update finish"); })
                .doOnError(e -> Log.e("#####", e.toString()))
                .subscribeOn(Schedulers.io()).subscribe();
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
