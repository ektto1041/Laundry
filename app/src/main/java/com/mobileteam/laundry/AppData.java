package com.mobileteam.laundry;

import android.content.Context;

import androidx.room.Room;

import com.mobileteam.laundry.enums.Mode;

public class AppData {
    private static LaundryDatabase db = null;
    private static Mode mode;

    public static void setDb(Context context) {
        if(db == null) db = Room.databaseBuilder(context, LaundryDatabase.class, "laundry").build();
    }
    public static LaundryDatabase getDb() {
        return db;
    }

    public static Mode getMode() {
        return mode;
    }

    public static void setMode(Mode mode) {
        AppData.mode = mode;

        switch (mode) {
            case LAUNDRY:
                modeColor = R.color.aespa_red;
                break;
            case DRY:
                modeColor = R.color.aespa_blue;
                break;
            case IRON:
                modeColor = R.color.aespa_yellow;
        }
    }

    private static int modeColor;

    public static int getModeColor() {
        return modeColor;
    }
}
