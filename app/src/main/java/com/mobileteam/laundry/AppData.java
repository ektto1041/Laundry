package com.mobileteam.laundry;

import com.mobileteam.laundry.enums.Mode;

public class AppData {
    private static Mode mode;

    public static Mode getMode() {
        return mode;
    }

    public static void setMode(Mode mode) {
        AppData.mode = mode;
    }
}
