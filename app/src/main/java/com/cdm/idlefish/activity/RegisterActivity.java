package com.cdm.idlefish.activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ViewFlipper;

import com.cdm.activity.BaseActivity;
import com.cdm.idlefish.R;
import com.cdm.idlefish.config.Constants;
import com.cdm.idlefish.dao.LoginDao;
import com.cdm.idlefish.entity.User;
import com.cdm.idlefish.session.Session;
import com.cdm.idlefish.utils.StringUtil;
import com.cdm.idlefish.view.ClearEditText;
import com.cdm.network.interf.HttpAuthCallBack;
import com.cdm.network.model.ResultModel;
import com.cdm.utils.ToastUtils;
import com.orhanobut.hawk.Hawk;


/**
 * Created by user on 2016/10/20.
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = "RegisterActivity";

    private Button mBtnNext;
    private View mRegisterPhone , mRegisterInfo;
    private ViewFlipper mViewFlipper;

    private ClearEditText mEditTxtName , mEditTxtPW;
    private String userNameStr, userPwdStr;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_register;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        setTitle("注册");
        mViewFlipper = $(R.id.register_flipper);
        mRegisterPhone = $(R.id.register_phone);
        mRegisterInfo = $(R.id.register_info);
        mBtnNext = $(R.id.tt_register_btn_next);
        mEditTxtName = $(R.id.register_et_phone);
        mEditTxtPW = $(R.id.register_et_password);
    }

    @Override
    protected void initListener() {
        mBtnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tt_register_btn_next:
                if(validate()){
                    register();
                }

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
        else if (userPwdStr.length() < 6) {
            mEditTxtPW.requestFocus();
            mEditTxtPW.setError("请填写6位以上的密码");
            return false;
        } else if (!StringUtil.isValidPwd(userPwdStr)) {
            mEditTxtPW.requestFocus();
            mEditTxtPW.setError("请填写6-16位的数字和字母组合");
            return false;
        }

        return true;
    }

    private void register(){
        LoginDao.getInstanse().doRegister(this,userNameStr,userPwdStr,new HttpAuthCallBack<User>() {
            @Override
            public void onSucceeded(User successObj) {
                Log.i(TAG, "successObj -- "+successObj);
                if(successObj==null){
                    return ;
                }
                Session.getInstance().clear();
                Session.getInstance().setUser(successObj.getToken(), successObj);
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
