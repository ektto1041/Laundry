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
                result = R.drawable.ic_dry_hook;
                break;
            case MC_NOT:
                result = R.drawable.ic_dry_hook_under;
                break;
            case HD:
                result = R.drawable.ic_dry_lie;
                break;
            case HD_NOT:
                result = R.drawable.ic_dry_lie_under;
                break;
        }

        return result;
    }
}
