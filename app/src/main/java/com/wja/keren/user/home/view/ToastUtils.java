package com.wja.keren.user.home.view;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wja.keren.R;


public class ToastUtils {
    private Toast toast;
    private LinearLayout toastView;

    /**
     * 完全自定义布局Toast
     */
    public ToastUtils(Context context, View view, int duration) {
        toast = new Toast(context);
        toast.setView(view);
        toast.setDuration(duration);
    }
    /**
     * 将Toast封装在一个方法中，在其它地方使用时直接输入要弹出的内容即可
     */
    public static void ToastMessage( Activity activity,  String messages) {
        //LayoutInflater的作用：对于一个没有被载入或者想要动态载入的界面，都需要LayoutInflater.inflate()来载入，LayoutInflater是用来找res/layout/下的xml布局文件，并且实例化
        LayoutInflater inflater = activity.getLayoutInflater();//调用Activity的getLayoutInflater()
        View view = inflater.inflate(R.layout.toast_style, null); //加載layout下的布局
        ImageView iv = view.findViewById(R.id.tvImageToast);
        iv.setImageResource(R.mipmap.toast_fail);//显示的图片
        TextView text = view.findViewById(R.id.tvTextToast);
        text.setText(messages); //toast内容
        Toast toast = new Toast(activity);
        toast.setGravity(Gravity.CENTER_HORIZONTAL, 12, 20);//setGravity用来设置Toast显示的位置，相当于xml中的android:gravity或android:layout_gravity
        toast.setDuration(Toast.LENGTH_LONG);//setDuration方法：设置持续时间，以毫秒为单位。该方法是设置补间动画时间长度的主要方法
        toast.setView(view);
        toast.show();

    }
    /**
     * 向Toast中添加自定义View
     */
    public ToastUtils addView(View view, int position) {
        toastView = (LinearLayout) toast.getView();
        toastView.addView(view, position);
        return this;
    }
    public static void ToastMessage(){


    }
    /**
     * 设置Toast字体及背景
     */
    public ToastUtils setToastBackground(int messageColor, int background) {
        View view = toast.getView();
        if (view != null) {
//            TextView message = (TextView) view.findViewById(R.id.message);
//            message.setBackgroundResource(background);
//            message.setTextColor(messageColor);
        }

        return this;
    }

    /**
     * 短时间显示Toast
     */
    public ToastUtils Short(Context context, CharSequence message) {
        if (toast == null
                || (toastView != null && toastView.getChildCount() > 1)) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toastView = null;
        } else {
            toast.setText(message);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        return this;
    }

    /**
     * 长时间显示toast
     */
    public ToastUtils Long(Context context, CharSequence message) {
        if (toast == null
                || (toastView != null && toastView.getChildCount() > 1)) {
            toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            toastView = null;
        } else {
            toast.setText(message);
            toast.setDuration(Toast.LENGTH_LONG);
        }
        return this;
    }

    /**
     * 自定义显示Toast的时长
     */
    public ToastUtils Indefinite(Context context, CharSequence message,
                                 int duration) {
        if (toast == null
                || (toastView != null && toastView.getChildCount() > 1)) {
            toast = Toast.makeText(context, message, duration);
            toastView = null;
        } else {
            toast.setText(message);
            toast.setDuration(duration);
        }

        return this;
    }

    /**
     * 显示Toast
     */
    public ToastUtils show() {
        toast.show();
        return this;
    }

    /**
     * 获取Toast
     */
    public Toast getToast() {
        return toast;
    }
}
