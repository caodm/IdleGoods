package com.cdm.idlefish.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.cdm.idlefish.R;

/*******************************************************************
 * @文件名称: DialogUtil
 * @作者 :  caodm
 * @文件描述:
 * @创建时间: 2018/7/15
 * ******************************************************************
 */
public class DialogUtil {

    private static DialogUtil instence;

    private DialogUtil() {

    }

    public static DialogUtil getInstence() {
        if (instence == null) {
            instence = new DialogUtil();
        }
        return instence;
    }


    /**
     * 打开dialog
     *
     * @param dialog
     */
    public static void showDialog(Dialog dialog, Activity ac) {
        if (dialog != null && !dialog.isShowing()) {
            if (!ac.isFinishing())
                dialog.show();

        }
    }

    public static void dismissDialog(Dialog dialog) {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }

    public interface TakeImageDialogClickListener {

        public void takephotoBtnClicked();

        public void imageboxClicked();

    }

    /**
     * 显示拍照相册对话框
     *
     * @param context
     */
    public static Dialog GetTakeImageDialog(Context context,
                                            final TakeImageDialogClickListener onClickListener) {

        final Dialog mImageBoxDialog = new Dialog(context,
                R.style.Dialog_TakePhotoTheme);

        mImageBoxDialog.setCanceledOnTouchOutside(true);
        View v = LayoutInflater.from(context).inflate(
                R.layout.view_takephoto, null);
        v.findViewById(R.id.btn_takephoto).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        mImageBoxDialog.dismiss();
                        onClickListener.takephotoBtnClicked();
                    }
                });
        v.findViewById(R.id.btn_imagebox).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        mImageBoxDialog.dismiss();
                        onClickListener.imageboxClicked();
                    }
                });
        mImageBoxDialog.setContentView(v);
        v.findViewById(R.id.btn_cansle).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        mImageBoxDialog.dismiss();
                    }
                });

        DialogUtil.SetDialogFullScreen(mImageBoxDialog);
        return mImageBoxDialog;
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
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        window.setGravity(Gravity.BOTTOM);
    }

    /**
     * 设置dialog 全屏
     *
     * @param dialog
     */
    public static void SetDialogFullScreenCenter(Dialog dialog) {
        Window window = dialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        window.setGravity(Gravity.CENTER);
    }



}
