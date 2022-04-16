package com.mobileteam.laundry.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "CLOTHES")
public class Clothes {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "washing_type")
    private String washingType;

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

    public Clothes(String washingType) {
        this.washingType = washingType;
    }
}
