package com.zkteam.discover.app;

import android.app.Application;
import android.content.Context;

import com.zkteam.discover.fresco.FrescoInitUtil;

/**
 * ===========================================================
 * 作    者：大印（高印） Github地址：https://github.com/GaoYin2016
 * 邮    箱：18810474975@163.com
 * 版    本：
 * 创建日期：2018/4/24 上午11:49
 * 描    述：
 * 修订历史：
 * ===========================================================
 */
public class BaseApp extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {

        super.onCreate();
        mContext = getApplicationContext();
        initFrescoConfig();
    }

    private void initFrescoConfig() {

        FrescoInitUtil.initFrescoConfig();
    }

    public static Context getContext() {

        return mContext;
    }
}
