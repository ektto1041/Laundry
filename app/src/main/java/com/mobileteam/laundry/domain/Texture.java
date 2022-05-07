package com.mobileteam.laundry.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "TEXTURES")
public class Texture {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "clothes_id")
    private long clothesId;

    @ColumnInfo(name = "name")
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getClothesId() {
        return clothesId;
    }

    public void setClothesId(long clothesId) {
        this.clothesId = clothesId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Texture(long clothesId, String name) {
        this.clothesId = clothesId;
        this.name = name;
    }
}
