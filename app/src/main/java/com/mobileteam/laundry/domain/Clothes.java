package com.mobileteam.laundry.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "CLOTHES")
public class Clothes {
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "washing_type")
    private String washingType;
}
