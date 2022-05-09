package com.mobileteam.laundry;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Debug;
import android.util.Log;

import androidx.room.TypeConverter;

import com.mobileteam.laundry.enums.Bleach;
import com.mobileteam.laundry.enums.ClothesColor;
import com.mobileteam.laundry.enums.Detergent;
import com.mobileteam.laundry.enums.Dry;
import com.mobileteam.laundry.enums.DryClean;
import com.mobileteam.laundry.enums.Iron;
import com.mobileteam.laundry.enums.Temperature;
import com.mobileteam.laundry.enums.WashingPower;
import com.mobileteam.laundry.enums.WashingType;
import com.mobileteam.laundry.enums.Weave;

import java.io.ByteArrayOutputStream;

public class Converters {
    // Bitmap -> ByteArray
    @TypeConverter
    public byte[] toByteArray(Bitmap bitmap) {
        if(bitmap == null) return null;

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
        return os.toByteArray();
    }

    // ByteArray -> Bitmap
    @TypeConverter
    public Bitmap toBitmap(byte[] byteArray) {
        if(byteArray == null) return null;
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

    // WashingType -> String
    @TypeConverter
    public String fromWashingType(WashingType washingType) {
        return washingType == null ? null : washingType.name();
    }

    // String -> WashingType
    @TypeConverter
    public WashingType toWashingType(String str) {
        return str == null ? null : WashingType.valueOf(str);
    }

    // WashingPower -> String
    @TypeConverter
    public String fromWashingPower(WashingPower washingPower) {
        return washingPower == null ? null : washingPower.name();
    }

    // String -> WashingPower
    @TypeConverter
    public WashingPower toWashingPower(String str) {
        return str == null ? null : WashingPower.valueOf(str);
    }

    // Temperature -> String
    @TypeConverter
    public String fromTemperature(Temperature temperature) {
        return temperature == null ? null : temperature.name();
    }

    // String -> Temperature
    @TypeConverter
    public Temperature toTemperature(String str) {
        return str == null ? null : Temperature.valueOf(str);
    }

    // Detergent -> String
    @TypeConverter
    public String fromDetergent(Detergent detergent) {
        return detergent == null ? null : detergent.name();
    }

    // String -> Detergent
    @TypeConverter
    public Detergent toDetergent(String str) {
        return str == null ? null : Detergent.valueOf(str);
    }

    // Color -> String
    @TypeConverter
    public String fromColor(ClothesColor clothesColor) {
        return clothesColor.name();
    }

    // String -> Color
    @TypeConverter
    public ClothesColor toColor(String str) {
        return ClothesColor.valueOf(str);
    }

    // Bleach -> String
    @TypeConverter
    public String fromBleach(Bleach bleach) {
        return bleach == null ? null : bleach.name();
    }

    // String -> Bleach
    @TypeConverter
    public Bleach toBleach(String str) {
        return str == null ? null : Bleach.valueOf(str);
    }

    // Iron -> String
    @TypeConverter
    public String fromIron(Iron iron) {
        return iron == null ? null : iron.name();
    }

    // String -> Iron
    @TypeConverter
    public Iron toIron(String str) {
        return str == null ? null : Iron.valueOf(str);
    }

    // DryClean -> String
    @TypeConverter
    public String fromDryClean(DryClean dryClean) {
        return dryClean == null ? null : dryClean.name();
    }

    // String -> DryClean
    @TypeConverter
    public DryClean toDryClean(String str) {
        return str == null ? null : DryClean.valueOf(str);
    }

    // Dry -> String
    @TypeConverter
    public String fromDry(Dry dry) {
        return dry == null ? null : dry.name();
    }

    // String -> Dry
    @TypeConverter
    public Dry toDry(String str) {
        return str == null ? null : Dry.valueOf(str);
    }

    // Weave -> String
    @TypeConverter
    public String fromWeave(Weave weave) {
        return weave == null ? null : weave.name();
    }

    // String -> Weave
    @TypeConverter
    public Weave toWeave(String str) {
        return str == null ? null : Weave.valueOf(str);
    }
}
