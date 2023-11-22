package com.itsi.almuntaqimorevn.utils;

import android.content.Context;
import android.content.res.Resources;

public class MyJavaUtils {

    int getSystemNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }
}
