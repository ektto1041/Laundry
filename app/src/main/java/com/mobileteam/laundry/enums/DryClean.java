package com.mobileteam.laundry.enums;

import com.mobileteam.laundry.R;

public enum DryClean {
    ANY,
    OIL,
    PRO,
    NOT;

    public int getResource() {
        int result = -1;

        switch (this) {
            case ANY:
                result = R.drawable.ic_dc;
                break;
            case OIL:
                result = R.drawable.ic_dc_oil;
                break;
            case PRO:
                result = R.drawable.ic_dc_pro;
                break;
            case NOT:
                result = R.drawable.ic_dc_not;
        }

        return result;
    }
}
