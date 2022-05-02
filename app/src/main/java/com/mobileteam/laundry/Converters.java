package com.mobileteam.laundry;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.room.TypeConverter;

import com.mobileteam.laundry.enums.Detergent;
import com.mobileteam.laundry.enums.Temperature;
import com.mobileteam.laundry.enums.WashingPower;
import com.mobileteam.laundry.enums.WashingType;

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
        return washingType.name();
    }

    // String -> WashingType
    @TypeConverter
    public WashingType toWashingType(String str) {
        return WashingType.valueOf(str);
    }

    // WashingPower -> String
    @TypeConverter
    public String fromWashingPower(WashingPower washingPower) {
        return washingPower.name();
    }

    // String -> WashingPower
    @TypeConverter
    public WashingPower toWashingPower(String str) {
        return WashingPower.valueOf(str);
    }

    // Temperature -> String
    @TypeConverter
    public String fromTemperature(Temperature temperature) {
        return temperature.name();
    }

    // String -> Temperature
    @TypeConverter
    public Temperature toTemperature(String str) {
        return Temperature.valueOf(str);
    }

    // Detergent -> String
    @TypeConverter
    public String fromDetergent(Detergent detergent) {
        return detergent.name();
    }

    // String -> Detergent
    @TypeConverter
    public Detergent toDetergent(String str) {
        return Detergent.valueOf(str);
    }
}
