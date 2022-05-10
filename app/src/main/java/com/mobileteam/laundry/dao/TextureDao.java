package com.mobileteam.laundry.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.mobileteam.laundry.domain.Texture;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

@Dao
public interface TextureDao {
    @Insert
    public Single<Long> insert(Texture texture);

    @Query("SELECT * FROM TEXTURES WHERE clothes_id = :clothesId")
    public Single<List<Texture>> findAll(long clothesId);

    @Query("DELETE FROM TEXTURES WHERE clothes_id = :clothesId")
    public Single<Integer> delete(long clothesId);

    @Query("DELETE FROM TEXTURES WHERE name = :name AND clothes_id = :clothesId")
    public Single<Integer> deleteByName(String name, long clothesId);
}
