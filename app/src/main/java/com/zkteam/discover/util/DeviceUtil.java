package com.zkteam.discover.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.KeyguardManager;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.text.TextUtils;
import android.view.WindowManager;

import com.zkteam.discover.app.BaseApp;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.List;

import static android.content.Context.KEYGUARD_SERVICE;

/**
 * 设备信息获取工具类
 */
public class DeviceUtil {

    private static int mScreenWidth;
    private static int mScreenHeight;

    /**
     * 获取设备屏幕宽度
     *
     * @return
     */
    public static int getScreenWidth() {

        if (mScreenWidth == 0)
            mScreenWidth = BaseApp.getContext().getResources().getDisplayMetrics().widthPixels;

        return mScreenWidth;
    }

    /**
     * 获取设备屏幕高度
     *
     * @return
     */
    public static int getScreenHeight() {

        if (mScreenHeight == 0)
            mScreenHeight = BaseApp.getContext().getResources().getDisplayMetrics().heightPixels;

        return mScreenHeight;
    }

    /**
     * 获取当前activity 状态栏高度
     *
     * @param activity
     * @return
     */
    public static int getStatusBarHeightByFrame(Activity activity) {

        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        return frame.top;
    }

    /**
     * 设置状态栏颜色
     *
     * @param activity
     * @param color
     */
    public static void setStatusBarColor(Activity activity, int color) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            activity.getWindow().setStatusBarColor(color);
    }

    /**
     * 设置状态栏颜色资源
     *
     * @param activity
     * @param colorResId
     */
    public static void setStatusBarColorResource(Activity activity, int colorResId) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            activity.getWindow().setStatusBarColor(activity.getResources().getColor(colorResId));
    }

    /**
     * 设置状态栏透明
     *
     * @param activity
     * @param translucent
     * @return
     */
    public static boolean setStatusBarTranslucent(Activity activity, boolean translucent, boolean kitkatEnable) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
            return false;

        try {

            //umeng日志中，在某些机型上，addFlags函数会报
            // java.lang.SecurityException: No permission to prevent power key:
            // Neither user 10069 nor current process has android.permission.PREVENT_POWER_KEY
            //所以这里做了简单的try catch 处理
            if (kitkatEnable || Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                if (translucent) {

                    activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                } else {

                    activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                }

                return true;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    /**
     * 获取设备型号
     *
     * @return
     */
    public static String getDeviceType() {

        return TextUtil.filterNull(Build.MODEL);
    }

    /**
     * 获取系统版本信息
     *
     * @return
     */
    public static String getOsVersion() {

        return TextUtil.filterNull(Build.VERSION.RELEASE);
    }

    /**
     * 获取系统能给当前能给app分配的最大内存
     *
     * @return
     */
    public static long getRuntimeMaxMemory() {

        return Runtime.getRuntime().maxMemory();
    }

    /**
     * 获取本机ip地址
     *
     * @return
     */
    public static String getLocalIpAddress() {

        try {

            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {

                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {

                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Throwable t) {

        }

        return TextUtil.TEXT_EMPTY;
    }

    /**
     * 获取设备品牌
     *
     * @return
     */
    public static String getBrand() {

        return TextUtil.filterNull(Build.BRAND);
    }

    public static String getBrandAndModel() {

        return Build.BRAND + "_" + Build.MODEL;
    }

    public static boolean isOppoBrand() {

        return isBrand("oppo");
    }

    public static boolean isVivoBrand() {

        return isBrand("vivo");
    }

    public static boolean isXiaoMiBrand() {

        return isBrand("xiaomi");
    }

    public static boolean isHuaWei() {

        return isBrand("huawei");
    }

    public static boolean isMeizu() {

        return isBrand("meizu");
    }

    private static boolean isBrand(String brand) {

        return TextUtil.filterNull(Build.BRAND).toLowerCase().contains(brand);
    }


    /**
     * @param context
     * @return false：表示未锁屏
     */
    public static boolean isLockScreen(Context context) {

        try {

            KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
            return keyguardManager.inKeyguardRestrictedInputMode();

        } catch (Throwable t) {

            return false;
        }
    }


    /**
     * 判断应用是否是在后台
     */
    public static boolean isAppInBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(KEYGUARD_SERVICE);

        List<RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        for (RunningAppProcessInfo appProcess : appProcesses) {
            if (TextUtils.equals(appProcess.processName, context.getPackageName())) {
                boolean isBackground = (appProcess.importance != RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.importance != RunningAppProcessInfo.IMPORTANCE_VISIBLE);
                boolean isLockedState = keyguardManager.inKeyguardRestrictedInputMode();
                return isBackground || isLockedState;
            }
        }
        return false;
    }

}
