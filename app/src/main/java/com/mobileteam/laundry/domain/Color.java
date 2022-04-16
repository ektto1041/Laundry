package com.mobileteam.laundry.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "COLORS")
public class Color {
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "clothes_id")
    private int clothesId;

    @ColumnInfo(name = "color_name")
    private int colorName;
}
