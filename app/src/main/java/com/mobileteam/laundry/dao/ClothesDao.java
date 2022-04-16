package com.mobileteam.laundry.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.mobileteam.laundry.domain.Clothes;

@Dao
public interface ClothesDao {
    @Insert
    public long insert(Clothes clothes);
}
