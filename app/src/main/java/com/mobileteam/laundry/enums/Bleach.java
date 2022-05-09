package com.mobileteam.laundry.enums;

import com.mobileteam.laundry.R;

public enum Bleach {
    CHLORINE,
    NOT_CHLORINE,
    OXYGEN,
    NOT_OXYGEN,
    ALL,
    NOT_ALL;

    public int getResource() {
        int result = -1;

        switch(this) {
            case CHLORINE:
                result = R.drawable.ic_bleach_chlorine;
                break;
            case NOT_CHLORINE:
                result = R.drawable.ic_bleach_not_chlorine;
                break;
            case OXYGEN:
                result = R.drawable.ic_bleach_oxygen;
                break;
            case NOT_OXYGEN:
                result = R.drawable.ic_bleach_not_oxygen;
                break;
            case ALL:
                result = R.drawable.ic_bleach_all;
                break;
            case NOT_ALL:
                result = R.drawable.ic_bleach_not_all;
                break;
        }

        return result;
    }
}
