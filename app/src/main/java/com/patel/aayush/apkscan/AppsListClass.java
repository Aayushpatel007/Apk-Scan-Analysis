package com.patel.aayush.apkscan;

import android.graphics.drawable.Drawable;

/**
 * Created by aayush on 02-01-2018.
 */

public class AppsListClass {
    Drawable image;
    String appname;
    String packagename;
    int dangerous;
    int normal;
    int others;

    public AppsListClass(Drawable image,String appname, String packagename, int dangerous, int normal, int others) {
        this.image=image;
        this.appname = appname;
        this.packagename = packagename;
        this.dangerous = dangerous;
        this.normal = normal;
        this.others = others;
    }
}
