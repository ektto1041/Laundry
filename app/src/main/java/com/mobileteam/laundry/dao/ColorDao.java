package com.mobileteam.laundry.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.mobileteam.laundry.domain.Color;

import io.reactivex.rxjava3.core.Single;

@Dao
public interface ColorDao {
    @Insert
    public Single<Long> insert(Color color);
}
