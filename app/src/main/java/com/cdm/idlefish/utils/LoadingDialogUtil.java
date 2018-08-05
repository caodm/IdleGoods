package com.cdm.idlefish.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.cdm.idlefish.R;
import com.cdm.idlefish.view.LoadDialog;

public class LoadingDialogUtil {
    /**
     * 关闭dialog
     *
     * @param dialog
     */
    public static void dismissDialog(Dialog dialog) {
        //
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }

    /**
     * 打开dialog
     *
     * @param dialog
     */
    public static void showDialog(Dialog dialog, Activity ac) {
        if (dialog != null && !dialog.isShowing()) {
            //
            if (!ac.isFinishing())
                dialog.show();

        }
    }

    /**
     * 获取loadingDialog
     *
     * @param context
     * @return
     */
    public static LoadDialog getLoadDialog(Context context) {
        //
        LoadDialog loadDialog = new LoadDialog(context,
                R.style.MyDialogStyleLoading);
        //
        return loadDialog;
    }

    /**
     * 设置dialog 全屏
     *
     * @param dialog
     */
    public static void SetDialogFullScreen(Dialog dialog) {
        Window window = dialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        window.setGravity(Gravity.BOTTOM);
    }
}
