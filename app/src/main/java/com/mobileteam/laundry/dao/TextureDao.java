package com.mobileteam.laundry.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.mobileteam.laundry.domain.Texture;

import io.reactivex.rxjava3.core.Single;

@Dao
public interface TextureDao {
    @Insert
    public Single<Long> insert(Texture texture);
}
