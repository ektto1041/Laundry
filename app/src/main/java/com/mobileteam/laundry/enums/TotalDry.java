package com.mobileteam.laundry.enums;

import com.mobileteam.laundry.R;

public enum TotalDry {

    HOOK,
    HOOK_UNDER,
    LIE,
    LIE_UNDER,
    MC,
    MC_NOT;


    public int getResource() {
        int result = -1;

        switch (this) {
            case HOOK:
                result = R.drawable.ic_dry_hook;
                break;
            case HOOK_UNDER:
                result = R.drawable.ic_dry_hook_under;
                break;
            case LIE:
                result = R.drawable.ic_dry_lie;
                break;
            case LIE_UNDER:
                result = R.drawable.ic_dry_lie_under;
                break;
            case MC:
                result = R.drawable.ic_wv_mc;
                break;
            case MC_NOT:
                result = R.drawable.ic_wv_mc_not;
                break;


        }

        return result;
    }
}
