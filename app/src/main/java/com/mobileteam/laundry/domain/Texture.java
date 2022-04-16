package com.mobileteam.laundry.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "TEXTURES")
public class Texture {
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "clothes_id")
    private int clothesId;

    @ColumnInfo(name = "name")
    private String name;
}
