package com.free.blog.library.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.free.blog.R;


@SuppressLint("InflateParams")
public class ToastUtil {

    private static Toast mToast;

    /**
     * Toast显示消息(中间位置)
     */

    public static void show(final Context context, final String message) {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }

        mToast = Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT);
        View view = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.toast_bg, null);
        TextView messageView = (TextView) view.findViewById(R.id.tv_message);
        mToast.setView(view);

        messageView.setText(message);
        mToast.show();
    }

    /**
     * Toast显示消息(底部)
     */

    public static void showCenter(final Context context, final String message) {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }

        mToast = Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT);
        View view = LayoutInflater.from(context).inflate(R.layout.toast_bg, null);
        TextView messageView = (TextView) view.findViewById(R.id.tv_message);
        mToast.setView(view);

        messageView.setText(message);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }

}
