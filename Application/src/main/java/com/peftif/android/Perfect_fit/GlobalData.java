package com.peftif.android.Perfect_fit;

import android.app.Application;

public class GlobalData extends Application {
    private int checkFragment = 1;
    public static boolean isWidgetDistroyed = true;

    public int getCheckFragment() {
        return checkFragment;
    }

    public void setCheckFragment(int checkFragment) {
        this.checkFragment = checkFragment;
    }
}
