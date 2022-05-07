package com.mobileteam.laundry;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.mobileteam.laundry.dao.ClothesDao;
import com.mobileteam.laundry.dao.TextureDao;
import com.mobileteam.laundry.domain.Clothes;
import com.mobileteam.laundry.domain.Texture;

@Database(entities = {Clothes.class, Texture.class}, version = 1)
@TypeConverters(Converters.class)
public abstract class LaundryDatabase extends RoomDatabase {
    public abstract ClothesDao clothesDao();
    public abstract TextureDao textureDao();
}
