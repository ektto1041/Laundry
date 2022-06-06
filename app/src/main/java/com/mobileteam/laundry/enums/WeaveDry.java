package com.mobileteam.laundry.enums;

import com.mobileteam.laundry.R;

public enum WeaveDry {

    HD,
    HD_NOT;


    public int getResource() {
        int result = -1;

        switch (this) {


    case HD:
    result = R.drawable.ic_wv_hd;
                break;
            case HD_NOT:
    result = R.drawable.ic_wv_hd_not;
                break;

    }


        return result;
    }
}
