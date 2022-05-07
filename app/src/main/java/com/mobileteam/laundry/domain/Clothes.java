package com.mobileteam.laundry.domain;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.mobileteam.laundry.enums.ClothesColor;
import com.mobileteam.laundry.enums.Detergent;
import com.mobileteam.laundry.enums.Temperature;
import com.mobileteam.laundry.enums.WashingPower;
import com.mobileteam.laundry.enums.WashingType;

import java.io.Serializable;

@Entity(tableName = "CLOTHES")
public class Clothes implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "washing_type")
    private WashingType washingType;

    @ColumnInfo(name = "washing_power")
    private WashingPower washingPower;

    @ColumnInfo(name = "detergent")
    private Detergent detergent;

    @ColumnInfo(name = "temperature")
    private Temperature temperature;

    @ColumnInfo(name = "colors")
    private ClothesColor clothesColor;

    @ColumnInfo(name = "image")
    private Bitmap image;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public WashingType getWashingType() {
        return washingType;
    }

    public void setWashingType(WashingType washingType) {
        this.washingType = washingType;
    }

    public WashingPower getWashingPower() {
        return washingPower;
    }

    public void setWashingPower(WashingPower washingPower) {
        this.washingPower = washingPower;
    }

    public Detergent getDetergent() {
        return detergent;
    }

    public void setDetergent(Detergent detergent) {
        this.detergent = detergent;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public ClothesColor getClothesColor() {
        return clothesColor;
    }

    public void setClothesColor(ClothesColor clothesColor) {
        this.clothesColor = clothesColor;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Clothes(WashingType washingType, WashingPower washingPower, Detergent detergent, Temperature temperature, ClothesColor clothesColor, Bitmap image) {
        this.washingType = washingType;
        this.washingPower = washingPower;
        this.detergent = detergent;
        this.temperature = temperature;
        this.clothesColor = clothesColor;
        this.image = image;
    }
}
