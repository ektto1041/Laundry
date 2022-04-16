package com.mobileteam.laundry.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.mobileteam.laundry.domain.Clothes;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface ClothesDao {
    @Insert
    public Single<Long> insert(Clothes clothes);

    @Query("SELECT * FROM CLOTHES")
    public Single<List<Clothes>> getAll();
}
