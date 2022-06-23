package com.example.myapplication.ext;

import android.app.Activity;
import android.os.Build;

public class ConstExt {
    public static int POSITION = 0;
    public static void restartActivity(Activity activity) {
        if (Build.VERSION.SDK_INT >= 11) {
            activity.recreate();
        } else {
            activity.finish();
            activity.startActivity(activity.getIntent());
        }
    }
}
