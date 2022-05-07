package com.mobileteam.laundry.enums;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.mobileteam.laundry.R;

public enum ClothesColor {
    WHITE,
    GRAY,
    BLACK,
    RED,
    GREEN,
    BLUE,
    CYAN,
    YELLOW,
    MAGENTA;

    public int getColor(Context context) {
        int color = ContextCompat.getColor(context, R.color.white);

        switch (this) {
            case WHITE:
                color = ContextCompat.getColor(context, R.color.white);
                break;
            case GRAY:
                color = ContextCompat.getColor(context, R.color.grey);
                break;
            case BLACK:
                color = ContextCompat.getColor(context, R.color.black);
                break;
            case RED:
                color = ContextCompat.getColor(context, R.color.red);
                break;
            case GREEN:
                color = ContextCompat.getColor(context, R.color.green);
                break;
            case BLUE:
                color = ContextCompat.getColor(context, R.color.blue);
                break;
            case CYAN:
                color = ContextCompat.getColor(context, R.color.cyan);
                break;
            case YELLOW:
                color = ContextCompat.getColor(context, R.color.yellow);
                break;
            case MAGENTA:
                color = ContextCompat.getColor(context, R.color.magenta);
        }

        return color;
    }
}
