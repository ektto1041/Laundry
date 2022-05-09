package com.mobileteam.laundry.enums;

import com.mobileteam.laundry.R;

public enum Iron {
    HT,
    HT_WITH,
    MT,
    MT_WITH,
    LT,
    LT_WITH,
    NOT;

    public int getResource() {
        int result = -1;

        switch (this) {
            case HT:
                result = R.drawable.ic_iron_ht;
                break;
            case HT_WITH:
                result = R.drawable.ic_iron_ht_with;
                break;
            case MT:
                result = R.drawable.ic_iron_mt;
                break;
            case MT_WITH:
                result = R.drawable.ic_iron_mt_with;
                break;
            case LT:
                result = R.drawable.ic_iron_lt;
                break;
            case LT_WITH:
                result = R.drawable.ic_iron_lt_with;
                break;
            case NOT:
                result = R.drawable.ic_iron_not;
        }

        return result;
    }
}
