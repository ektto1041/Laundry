package com.mobileteam.laundry.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.mobileteam.laundry.domain.Texture;

@Dao
public interface TextureDao {
    @Insert
    public long insert(Texture texture);
}
