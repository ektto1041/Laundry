package com.mobileteam.laundry.enums;

import com.mobileteam.laundry.R;

public enum Weave {
    MC,
    MC_NOT,
    HD,
    HD_NOT;

    public int getResource() {
        int result = -1;

        switch (this) {
            case MC:
                result = R.drawable.ic_wv_mc;
                break;
            case MC_NOT:
                result = R.drawable.ic_wv_mc_not;
                break;
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
