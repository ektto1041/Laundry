package com.mobileteam.laundry.dao;

import android.graphics.Bitmap;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.mobileteam.laundry.domain.Clothes;
import com.mobileteam.laundry.enums.Bleach;
import com.mobileteam.laundry.enums.ClothesColor;
import com.mobileteam.laundry.enums.Detergent;
import com.mobileteam.laundry.enums.Dry;
import com.mobileteam.laundry.enums.DryClean;
import com.mobileteam.laundry.enums.Iron;
import com.mobileteam.laundry.enums.Temperature;
import com.mobileteam.laundry.enums.TotalDry;
import com.mobileteam.laundry.enums.WashingPower;
import com.mobileteam.laundry.enums.WashingType;
import com.mobileteam.laundry.enums.Weave;
import com.mobileteam.laundry.enums.WeaveDry;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

@Dao
public interface ClothesDao {
    @Insert
    public Single<Long> insert(Clothes clothes);

    @Query("SELECT * FROM CLOTHES")
    public Single<List<Clothes>> getAll();

    @Query("SELECT DISTINCT CLOTHES.id, washing_type, washing_power, detergent, temperature, colors, bleach, iron, dry_clean, weave, dry, image " +
            "FROM CLOTHES " +
            "INNER JOIN TEXTURES ON CLOTHES.id = TEXTURES.clothes_id " +
            "WHERE washing_type = :washingType AND washing_power = :washingPower" +
            " AND detergent = :detergent AND temperature = :temperature" +
            " AND colors IN (:clothesColorList)" +
            " AND TEXTURES.name IN (:textureList)")
    public Single<List<Clothes>> findAllWithColorsTexture(
            WashingType washingType,
            WashingPower washingPower,
            Detergent detergent,
            Temperature temperature,
            List<ClothesColor> clothesColorList,
            List<String> textureList
    );

    @Query("SELECT DISTINCT CLOTHES.id, washing_type, washing_power, detergent, temperature, colors, bleach, iron, dry_clean, weave, dry, image " +
            "FROM CLOTHES " +
            "WHERE washing_type = :washingType AND washing_power = :washingPower" +
            " AND detergent = :detergent AND temperature = :temperature" +
            " AND colors IN (:clothesColorList)")
    public Single<List<Clothes>> findAllWithColors(
            WashingType washingType,
            WashingPower washingPower,
            Detergent detergent,
            Temperature temperature,
            List<ClothesColor> clothesColorList
    );

    @Query("SELECT DISTINCT CLOTHES.id, washing_type, washing_power, detergent, temperature, colors, bleach, iron, dry_clean, weave, dry, image " +
            "FROM CLOTHES, TEXTURES " +
            "WHERE CLOTHES.id = TEXTURES.clothes_id" +
            " AND washing_type = :washingType AND washing_power = :washingPower" +
            " AND detergent = :detergent AND temperature = :temperature" +
            " AND TEXTURES.name IN (:textureList)")
    public Single<List<Clothes>> findAllWithTexture(
            WashingType washingType,
            WashingPower washingPower,
            Detergent detergent,
            Temperature temperature,
            List<String> textureList
    );

    @Query("SELECT DISTINCT CLOTHES.id, washing_type, washing_power, detergent, temperature, colors, bleach, iron, dry_clean, weave, dry, image " +
            "FROM CLOTHES " +
            "WHERE washing_type = :washingType AND washing_power = :washingPower" +
            " AND detergent = :detergent AND temperature = :temperature")
    public Single<List<Clothes>> findAll(
            WashingType washingType,
            WashingPower washingPower,
            Detergent detergent,
            Temperature temperature
    );

    //iron 관련(임시)
    @Query("SELECT DISTINCT CLOTHES.id, washing_type, washing_power, detergent, temperature, colors, bleach, iron, dry_clean, weave, dry, image " +
            "FROM CLOTHES " +
            "INNER JOIN TEXTURES ON CLOTHES.id = TEXTURES.clothes_id " +
            "WHERE iron = :iron" +
            " AND colors IN (:clothesColorList)" +
            " AND TEXTURES.name IN (:textureList)")
    public Single<List<Clothes>> findIronWithColorsTexture(
            Iron iron,
            List<ClothesColor> clothesColorList,
            List<String> textureList
    );

    @Query("SELECT DISTINCT CLOTHES.id, washing_type, washing_power, detergent, temperature, colors, bleach, iron, dry_clean, weave, dry, image " +
            "FROM CLOTHES " +
            "WHERE iron = :iron" +
            " AND colors IN (:clothesColorList)")
    public Single<List<Clothes>> findIronWithColors(
            Iron iron,
            List<ClothesColor> clothesColorList
    );

    @Query("SELECT DISTINCT CLOTHES.id, washing_type, washing_power, detergent, temperature, colors, bleach, iron, dry_clean, weave, dry, image " +
            "FROM CLOTHES, TEXTURES " +
            "WHERE CLOTHES.id = TEXTURES.clothes_id" +
            " AND iron = :iron" +
            " AND TEXTURES.name IN (:textureList)")
    public Single<List<Clothes>> findIronWithTexture(
            Iron iron,
            List<String> textureList
    );

    @Query("SELECT DISTINCT CLOTHES.id, washing_type, washing_power, detergent, temperature, colors, bleach, iron, dry_clean, weave, dry, image " +
            "FROM CLOTHES " +
            "WHERE iron = :iron")
    public Single<List<Clothes>> findIron(
            Iron iron
    );

    //dry 관련(임시)
    @Query("SELECT DISTINCT CLOTHES.id, washing_type, washing_power, detergent, temperature, colors, bleach, iron, dry_clean, weave, dry, image " +
            "FROM CLOTHES " +
            "INNER JOIN TEXTURES ON CLOTHES.id = TEXTURES.clothes_id " +
            "WHERE dry = :totalDry" +
            " AND weave =:weaveDry" +
            " AND colors IN (:clothesColorList)" +
            " AND TEXTURES.name IN (:textureList)")
    public Single<List<Clothes>> findDryWithColorsTexture(
            TotalDry totalDry,
            WeaveDry weaveDry,
            List<ClothesColor> clothesColorList,
            List<String> textureList
    );

    @Query("SELECT DISTINCT CLOTHES.id, washing_type, washing_power, detergent, temperature, colors, bleach, iron, dry_clean, weave, dry, image " +
            "FROM CLOTHES " +
            "WHERE dry = :totalDry" +
            " And weave = :weaveDry" +
            " AND colors IN (:clothesColorList)")
    public Single<List<Clothes>> findDryWithColors(
            TotalDry totalDry,
            WeaveDry weaveDry,
            List<ClothesColor> clothesColorList
    );

    @Query("SELECT DISTINCT CLOTHES.id, washing_type, washing_power, detergent, temperature, colors, bleach, iron, dry_clean, weave, dry, image " +
            "FROM CLOTHES, TEXTURES " +
            "WHERE CLOTHES.id = TEXTURES.clothes_id" +
            " AND dry = :totalDry" +
            " AND weave = :weaveDry" +
            " AND TEXTURES.name IN (:textureList)")
    public Single<List<Clothes>> findDryWithTexture(
            TotalDry totalDry,
            WeaveDry weaveDry,
            List<String> textureList
    );

    @Query("SELECT DISTINCT CLOTHES.id, washing_type, washing_power, detergent, temperature, colors, bleach, iron, dry_clean, weave, dry, image " +
            "FROM CLOTHES " +
            "WHERE dry = :totalDry" +
            " AND weave = :weaveDry")
    public Single<List<Clothes>> findDry(
            TotalDry totalDry,
            WeaveDry weaveDry
    );

    @Query("SELECT * FROM CLOTHES WHERE id = :clothesId")
    public Single<Clothes> fineOne(long clothesId);

    @Query("UPDATE CLOTHES SET image = :image WHERE id = :clothesId")
    public Single<Integer> updateImage(long clothesId, Bitmap image);

    @Query("UPDATE CLOTHES SET colors = :clothesColor WHERE id = :clothesId")
    public Single<Integer> updateColor(long clothesId, ClothesColor clothesColor);

    @Query("UPDATE CLOTHES " +
            "SET washing_type = :washingType, washing_power = :washingPower, temperature = :temperature, detergent = :detergent " +
            "WHERE id = :clothesId")
    public Single<Integer> updateWashingMethod(long clothesId, WashingType washingType, WashingPower washingPower, Temperature temperature, Detergent detergent);

    @Query("UPDATE CLOTHES " +
            "SET bleach = :bleach " +
            "WHERE id = :clothesId")
    public Single<Integer> updateBleach(long clothesId, Bleach bleach);

    @Query("UPDATE CLOTHES " +
            "SET iron = :iron " +
            "WHERE id = :clothesId")
    public Single<Integer> updateIron(long clothesId, Iron iron);

    @Query("UPDATE CLOTHES " +
            "SET dry_clean = :dryClean " +
            "WHERE id = :clothesId")
    public Single<Integer> updateDryClean(long clothesId, DryClean dryClean);

    @Query("UPDATE CLOTHES " +
            "SET dry = :dry " +
            "WHERE id = :clothesId")
    public Single<Integer> updateDry(long clothesId, Dry dry);

    @Query("UPDATE CLOTHES " +
            "SET weave = :weave " +
            "WHERE id = :clothesId")
    public Single<Integer> updateWeave(long clothesId, Weave weave);

    @Query("DELETE FROM CLOTHES WHERE id = :clothesId")
    public Single<Integer> delete(long clothesId);
}

