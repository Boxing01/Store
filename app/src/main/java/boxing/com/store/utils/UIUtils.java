package boxing.com.store.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import boxing.com.store.base.BaseApplication;


/**
 * Created by liuyuli on 2017/5/27 0027.
 */

public class UIUtils {
    private static Toast toast;


    /**
     * 全局 Context
     *
     * @return Context
     */
    public static Context getContext() {
        return BaseApplication.getApplication();
    }

    /**
     * 获取资源
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 获取文字
     */
    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    /**
     * 获取文字数组
     */
    public static String[] getStringArray(int resId) {
        return getResources().getStringArray(resId);
    }

    /**
     * 获取dimen
     */
    public static int getDimens(int resId) {
        return getResources().getDimensionPixelSize(resId);
    }

    /**
     * 获取drawable
     */
    public static Drawable getDrawable(int resId) {
        return getResources().getDrawable(resId);
    }

    /**
     * 获取drawableId
     */
    public static int getDrawableId(String resName) {
        int indentify = getResources().getIdentifier(getContext().getPackageName() + ":drawable/" +
                resName, null, null);
        return indentify;
    }

    /**
     * 获取颜色
     */
    public static int getColor(int resId) {
        return getResources().getColor(resId);
    }

    /**
     * 获取颜色选择器
     */
    public static ColorStateList getColorStateList(int resId) {
        return getResources().getColorStateList(resId);
    }

    /**
     * dip转换px
     */
    public static int dip2px(int dip) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * px转换dip
     */
    public static int px2dip(int px) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * 获取屏幕的宽 单位px
     *
     * @return int
     */
    public static int getScreenWidth() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕的高 单位px
     *
     * @return int
     */
    public static int getScreenHeight() {
        return getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * @param activity
     * @return 状态栏高度  > 0 success; <= 0 fail 单位 px
     */
    public static int getStatusHeight(Activity activity) {
        int statusHeight = 0;
        Rect localRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject)
                        .toString());
                statusHeight = activity.getResources().getDimensionPixelSize(i5);
            } catch (Exception e) {
                L.e(e);
            }
        }
        return statusHeight;
    }

    /**
     * api大于19才可设置
     *
     * @param activity
     * @param textView 根布局的第一个view, 用来填充status
     * @param colorRes 颜色
     */
    public static void setStatusColor(Activity activity, TextView textView, @ColorRes int
            colorRes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            textView.setHeight(UIUtils.getStatusHeight(activity));
            textView.setBackgroundResource(colorRes);
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
        }
    }

    /**
     * 吐司
     *
     * @param text 内容
     */
    public static void showToast(CharSequence text) {
        if (toast == null) {
            toast = Toast.makeText(BaseApplication.getApplication(), text, Toast.LENGTH_LONG);
        } else {
            toast.setText(text);
        }
        toast.show();
    }

    /**
     * 吐司
     *
     * @param resId 资源文件 string
     */
    public static void showToast(int resId) {
        if (toast == null) {
            toast = Toast.makeText(BaseApplication.getApplication(), resId, Toast.LENGTH_LONG);
        } else {
            toast.setText(resId);
        }
        toast.show();
    }

    /**
     * 有父布局时使用
     *
     * @param resoure
     * @param root
     * @param attchToRoot
     * @return 填充的view
     */
    public static View inflate(@LayoutRes int resoure, ViewGroup root, boolean attchToRoot) {
        return LayoutInflater.from(BaseApplication.getApplication()).inflate(resoure, root,
                attchToRoot);
    }

    /**
     * 没有父布局时使用
     *
     * @param resoure
     * @return 填充的 view
     */
    public static View inflate(@LayoutRes int resoure) {
        return LayoutInflater.from(BaseApplication.getApplication()).inflate(resoure, null);
    }

    public static void closeSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) UIUtils.getContext().getSystemService
                (Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 2);
    }

    public static void openSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) UIUtils.getContext().
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, 0);
    }
}
