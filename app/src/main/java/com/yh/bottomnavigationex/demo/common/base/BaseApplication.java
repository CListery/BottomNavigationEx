package com.yh.bottomnavigationex.demo.common.base;

import android.app.Application;
import android.content.Context;

import com.yh.bottomnavigationex.Utils;

/**
 * Created by yu on 2016/11/24.
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }

    /**
     * dp to px
     *
     * @param context
     * @param dpValue dp
     * @return px
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
