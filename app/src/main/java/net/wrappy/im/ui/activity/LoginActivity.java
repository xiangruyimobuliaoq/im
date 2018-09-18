package net.wrappy.im.ui.activity;

import android.app.Activity;
import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.model.Response;

import net.wrappy.im.BaseActivity;
import net.wrappy.im.R;
import net.wrappy.im.contants.ConsUtils;
import net.wrappy.im.contants.Url;
import net.wrappy.im.model.AccountHelper;
import net.wrappy.im.model.UserNameStatus;
import net.wrappy.im.ui.view.Layout;
import net.wrappy.im.util.AppFuncs;
import net.wrappy.im.util.OkUtil;
import net.wrappy.im.util.PopupUtils;

import butterknife.BindView;

/**
 * 创建者     彭龙
 * 创建时间   2018/6/15 上午11:26
 * 描述	      ${TODO}
 * <p>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
@Layout(layoutId = R.layout.activity_login)
public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";
    @BindView(R.id.forgetID)
    TextView forgetID;
    @BindView(R.id.btnShowLogin)
    Button btnShowLogin;
    @BindView(R.id.btnShowRegister)
    Button btnShowRegister;
    @BindView(R.id.edtUserMame)
    EditText edtUserMame;
    public static final int REQUEST_CODE_INPUT_NEW_PASSWORD = 1113;
    @Override
    protected void init() {
//        forgetID.setText(getResources().getString(R.string.Forget_username).toLowerCase());
        forgetID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overlay(ForgetIDActivity.class);
            }
        });
        btnShowLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = edtUserMame.getText().toString().trim();
                if (TextUtils.isEmpty(s)) {
//                    toast("username is empty");
                    showOKDialog("username is empty");
                    return;
                }
                sendData(s);
            }
        });
        btnShowRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Bundle bundle = new Bundle();
////                bundle.putString(ConsUtils.INTENT, ConsUtils.INTENT_REGISTER);
////                overlay(PatternActivity.class,bundle);
                overlay(ValidatePhoneActivity.class);
            }
        });
    }

    private void sendData(final String s) {

        AppFuncs.showProgressWaiting(LoginActivity.this);
        final usernameCertification uc = new usernameCertification();
        uc.data.username = s;
        OkUtil.publicPost(Url.accounts_helper, new Gson().toJson(uc), new OkUtil.Callback() {
            @Override
            public void success(Response<String> response) {
                AppFuncs.dismissProgressWaiting();
                Log.e(TAG, "success: " + response.body().toString());
                UserNameStatus us = new Gson().fromJson(response.body().toString(), UserNameStatus.class);
                if (us.getCode() == 1000){
                    Bundle bundle = new Bundle();
                    bundle.putString(ConsUtils.USERNAME, s);
                    overlay(InputPasswordLoginActivity.class, bundle);
                }else {
                    String status = us.getData().getStatus();
                    if (ConsUtils.NORMAL.equals(status)){

//                    showOKDialog(us.getMessage() + us.getData().getLockLeftSeconds());
                    }else if (ConsUtils.NOT_EXIST.equals(status)){
                     /** 帐号不存在*/
                    showOKDialog(us.getMessage());
                    }else if (ConsUtils.LOCKED.equals(status)){
                        /** 帐号被锁，剩余多少秒后解锁*/
                    showOKDialog(us.getMessage() + "The remaining "  + us.getMessage() + us.getData().getLockLeftSeconds() +  " seconds");
                    }
                }
            }

            @Override
            public void error(Response<String> response) {
                AppFuncs.dismissProgressWaiting();
            }
        });


    }

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, LauncherActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }


    class usernameCertification{
        public String type = ConsUtils.ACCOUNT_STATUS;
        usernameData data = new usernameData();
        class usernameData{
            public String username;
        }
    }

}
