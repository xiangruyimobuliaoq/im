package net.wrappy.im.ui.activity;

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.model.Response;

import net.wrappy.im.BaseActivity;
import net.wrappy.im.R;
import net.wrappy.im.contants.ConsUtils;
import net.wrappy.im.model.Auth;
import net.wrappy.im.ui.fragment.ForgetPasswordQuestionFragment;
import net.wrappy.im.ui.view.Layout;
import net.wrappy.im.util.AppFuncs;
import net.wrappy.im.util.OkUtil;
import net.wrappy.im.util.SpUtil;

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
    private static final String TAG = "InputPasswordLoginActiv";
    @BindView(R.id.back)
    protected ImageView back;
    @BindView(R.id.iv_type)
    protected ImageView iv_type;
    @BindView(R.id.title)
    protected TextView title;
    @BindView(R.id.btnlogin)
    protected Button btnLogin;
    @BindView(R.id.btnforgetpass)
    protected TextView mBtnForgetPass;
    @BindView(R.id.edtpassword)
    protected EditText edtpassword;
    private String mPwd;
    private String mUsername;

    private boolean isTrue = true;
    @Override
    protected void init() {
        title.setText(getResources().getString(R.string.login));
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
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        iv_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isTrue){
                   edtpassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                   iv_type.setImageResource(R.mipmap.ic_show);
                    isTrue = false;
                }else {
                   edtpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                   iv_type.setImageResource(R.mipmap.ic_hidden);
                    isTrue = true;

                }

            }
        });
    }

    private void login() {
        AppFuncs.showProgressWaiting(this);
        OkUtil.Login(mUsername, mPwd, new OkUtil.Callback() {
            @Override
            public void success(Response<String> response) {
                AppFuncs.dismissProgressWaiting();
                Log.e(TAG, "success: " + response.body().toString());
                Auth auth = new Gson().fromJson(response.body().toString(), Auth.class);
                if (null != auth.account) {
//                    if ("invalid_grant".equals(auth.error)){
                    if (auth.error != null){
                        showOKDialog(auth.error_description);
                    }else {
                        SpUtil.putString("liderong",mPwd,"11");
//                        SpUtil.putString(mUsername,mPwd,"10");
                    startAndClearAll(MainActivity.class);
                    }
                } else {
                    showOKDialog("login failed");
//                    toast("login failed");
                }
            }

            @Override
            public void error(Response<String> response) {
                super.error(response);
                AppFuncs.dismissProgressWaiting();
                showErroe();
            }
        });
    }

    private void check() {
        mPwd = edtpassword.getText().toString().trim();
        if (TextUtils.isEmpty(mPwd)) {
//            toast("password is empty");
            showOKDialog("password cannot be empty");
            return;
        }
        login();
    }
}
