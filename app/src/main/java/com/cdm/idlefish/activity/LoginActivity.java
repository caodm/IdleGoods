package com.cdm.idlefish.activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.cdm.activity.BaseActivity;
import com.cdm.idlefish.R;
import com.cdm.idlefish.config.Constants;
import com.cdm.idlefish.dao.LoginDao;
import com.cdm.idlefish.entity.User;
import com.cdm.idlefish.session.Session;
import com.cdm.idlefish.view.ClearEditText;
import com.cdm.network.interf.HttpAuthCallBack;
import com.cdm.network.model.ResultModel;
import com.cdm.utils.ToastUtils;
import com.orhanobut.hawk.Hawk;


/**
 * Created by user on 2016/10/18.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = "LoginActivity";

    private Button mBtnLogin , mBtnRegister;
    private ClearEditText mEditTxtName , mEditTxtPW;

    private String userNameStr, userPwdStr;

    private View mViewThirdWeixin,mViewThirdQQ,mViewThirdWeibo;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_login_2;
    }


    @Override
    protected void initView() {
        setTitle("登录");
        mBtnLogin = $(R.id.login_btn_login);
        mBtnRegister = $(R.id.login_btn_register);
        mEditTxtName = $(R.id.login_et_username);
        mEditTxtPW = $(R.id.login_et_password);

        mViewThirdWeixin = $(R.id.tt_third_login_weixin);
        mViewThirdQQ = $(R.id.tt_third_login_qq);
        mViewThirdWeibo = $(R.id.tt_third_login_weibo);

        ToastUtils.init(this);
    }

    @Override
    protected void initData() {
        if (Hawk.get(Constants.HAWK_USERNAME) != null) {
            mEditTxtName.setText(Hawk.get(Constants.HAWK_USERNAME).toString());
            mEditTxtName.setSelection(mEditTxtName.getText().length());
        }

        if (Hawk.get(Constants.HAWK_PASSWORD) != null) {
            mEditTxtPW.setText(Hawk.get(Constants.HAWK_PASSWORD).toString());
            mEditTxtPW.setSelection(mEditTxtPW.getText().length());
        }
    }

    @Override
    protected void initListener() {
        mBtnLogin.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);
        mViewThirdWeixin.setOnClickListener(this);
        mViewThirdQQ.setOnClickListener(this);
        mViewThirdWeibo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn_login:
                if(validate()){
                    hideSoftKeyboard();
                   // login();
                    startActivityWithoutExtras(MainActivity2.class);
                }
                break;
            case R.id.login_btn_register:
                startActivityWithoutExtras(RegisterActivity.class);
                break;
            case R.id.tt_third_login_weixin:
                ToastUtils.getInstance().showToast("需要在第三方平台注册帐号，暂不支持!");
                break;
            case R.id.tt_third_login_qq:
                ToastUtils.getInstance().showToast("需要在第三方平台注册帐号，暂不支持!");
                break;
            case R.id.tt_third_login_weibo:
                ToastUtils.getInstance().showToast("需要在第三方平台注册帐号，暂不支持!");
                break;
        }
    }
    private boolean validate() {
        userNameStr = mEditTxtName.getText().toString().trim();
        userPwdStr = mEditTxtPW.getText().toString().trim();

        if (TextUtils.isEmpty(userNameStr)) {
            mEditTxtName.requestFocus();
            mEditTxtName.setError("请填写用户名");
            return false;
        }

        if (TextUtils.isEmpty(userPwdStr)) {
            mEditTxtPW.requestFocus();
            mEditTxtPW.setError("请填写密码");
            return false;
        }
//        else if (userPwdStr.length() < 6) {
//            mEditTxtPW.requestFocus();
//            mEditTxtPW.setError("请填写6位以上的密码");
//            return false;
//        } else if (!StringUtil.isValidPwd(userPwdStr)) {
//            mEditTxtPW.requestFocus();
//            mEditTxtPW.setError("请填写6位以上的数字字母组合密码");
//            return false;
//        }

        return true;
    }

    private void login(){
        LoginDao.getInstanse().doLogin(this,userNameStr,userPwdStr,new HttpAuthCallBack<User>() {
            @Override
            public void onSucceeded(User successObj) {
                Log.i(TAG, "successObj -- "+successObj.toString());
                if (successObj != null) {
                    Session.getInstance().clear();
                    Session.getInstance().setUser(successObj.getToken(), successObj);
                }
                Hawk.put(Constants.HAWK_USERNAME, successObj.getName());
                Hawk.put(Constants.HAWK_PASSWORD, successObj.getPassword());
                Hawk.put(Constants.HAWK_ISLOGIN, 1); //1为已登录
                startActivityWithoutExtras(MainActivity2.class);
            }

            @Override
            public void onFailed(final ResultModel failObj) {
                runOnUiThread(new Thread(){
                    @Override
                    public void run() {
                        ToastUtils.getInstance().showToast(failObj.getMessage());
                    }
                });
            }
        });
    }
}
