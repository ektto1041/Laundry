package com.mobileteam.laundry.domain;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.mobileteam.laundry.R;

import java.io.Serializable;

@Entity(tableName = "CLOTHES")
public class Clothes implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "washing_type")
    private String washingType;

    @ColumnInfo(name = "washing_power")
    private String washingPower;

    @ColumnInfo(name = "detergent")
    private String detergent;

    @ColumnInfo(name = "temperature")
    private String temperature;

    @ColumnInfo(name = "image")
    private Bitmap image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWashingType() {
        return washingType;
    }

    public void setWashingType(String washingType) {
        this.washingType = washingType;
    }

    public String getWashingPower() {
        return washingPower;
    }

    public void setWashingPower(String washingPower) {
        this.washingPower = washingPower;
    }

    public String getDetergent() {
        return detergent;
    }

    public void setDetergent(String detergent) {
        this.detergent = detergent;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Clothes(String washingType, String washingPower, String detergent, String temperature, Bitmap image) {
        this.washingType = washingType;
        this.washingPower = washingPower;
        this.detergent = detergent;
        this.temperature = temperature;
        this.image = image;
    }
}
