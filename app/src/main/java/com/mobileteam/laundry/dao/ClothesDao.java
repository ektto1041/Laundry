package com.mobileteam.laundry.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.mobileteam.laundry.domain.Clothes;
import com.mobileteam.laundry.enums.Detergent;
import com.mobileteam.laundry.enums.Temperature;
import com.mobileteam.laundry.enums.WashingPower;
import com.mobileteam.laundry.enums.WashingType;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface ClothesDao {
    @Insert
    public Single<Long> insert(Clothes clothes);

    @Query("SELECT * FROM CLOTHES")
    public Single<List<Clothes>> getAll();

    @Query("SELECT * FROM CLOTHES " +
            "WHERE washing_type = :washingType AND washing_power = :washingPower" +
            " AND detergent = :detergent AND temperature = :temperature")
    public Single<List<Clothes>> findAll(WashingType washingType, WashingPower washingPower, Detergent detergent, Temperature temperature);
}
