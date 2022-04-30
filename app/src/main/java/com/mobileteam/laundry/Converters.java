package com.mobileteam.laundry;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.room.TypeConverter;

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
}
