package com.cdm.idlefish.base;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;

import com.cdm.idlefish.R;
import com.cdm.idlefish.utils.PermissionReq;
import com.cdm.idlefish.utils.binding.ViewBinder;

public class BassExpressActivity extends AppCompatActivity{

    protected Handler handler;
    private ProgressDialog progressDialog;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handler = new Handler(Looper.getMainLooper());

//        if (shouldSetStatusBarColor() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            SystemBarTintManager tintManager = new SystemBarTintManager(this);
//            tintManager.setStatusBarTintEnabled(true);
//            TypedValue typedValue = new TypedValue();
//            getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
//            tintManager.setStatusBarTintResource(typedValue.resourceId);
//        }
//
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }
    }

    protected boolean shouldSetStatusBarColor() {
        return true;
    }


    @Override
    public void setContentView(int layoutResID) {
        View view = getLayoutInflater().inflate(layoutResID, null);
        setContentView(view);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        ViewBinder.bind(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionReq.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void showProgress() {
        showProgress(getString(R.string.loading));
    }

    public void showProgress(String message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
        }
        progressDialog.setMessage(message);
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    public void cancelProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }
}
