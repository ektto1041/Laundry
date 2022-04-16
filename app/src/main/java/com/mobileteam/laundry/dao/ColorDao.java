package com.mobileteam.laundry.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.mobileteam.laundry.domain.Color;

@Dao
public interface ColorDao {
    @Insert
    public long insert(Color color);
}
