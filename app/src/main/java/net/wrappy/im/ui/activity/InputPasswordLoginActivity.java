package net.wrappy.im.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.model.Response;

import net.wrappy.im.BaseActivity;
import net.wrappy.im.R;
import net.wrappy.im.contants.ConsUtils;
import net.wrappy.im.model.Auth;
import net.wrappy.im.ui.view.Layout;
import net.wrappy.im.util.AppFuncs;
import net.wrappy.im.util.OkUtil;

import butterknife.BindView;

/**
 * 创建者     彭龙
 * 创建时间   2018/6/28 上午9:12
 * 描述	      ${TODO}
 * <p>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
@Layout(layoutId = R.layout.activity_inputpasswordlogin)
public class InputPasswordLoginActivity extends BaseActivity {
    @BindView(R.id.btnlogin)
    protected Button btnLogin;
    @BindView(R.id.btnforgetpass)
    protected TextView mBtnForgetPass;
    @BindView(R.id.edtpassword)
    protected EditText edtpassword;
    private String mPwd;
    private String mUsername;

    @Override
    protected void init() {
        mUsername = getIntent().getStringExtra(ConsUtils.USERNAME);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();

            }
        });
        mBtnForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("username", mUsername);
                overlay(ForgetPasswordActivity.class, bundle);
            }
        });
    }

    private void login() {
        AppFuncs.showProgressWaiting(this);
        OkUtil.Login(mUsername, mPwd, new OkUtil.Callback() {
            @Override
            public void success(Response<String> response) {
                AppFuncs.dismissProgressWaiting();
                Auth auth = new Gson().fromJson(response.body(), Auth.class);
                if (null != auth.account) {
                    startAndClearAll(MainActivity.class);
                } else {
                    toast("login failed");
                }
            }
        });
    }

    private void check() {
        mPwd = edtpassword.getText().toString().trim();
        if (TextUtils.isEmpty(mPwd)) {
            toast("password is empty");
            return;
        }
        login();
    }
}
