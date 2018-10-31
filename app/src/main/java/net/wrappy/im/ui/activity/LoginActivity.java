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
import net.wrappy.im.util.ManagementAllActivity;
import net.wrappy.im.util.OkUtil;
import net.wrappy.im.util.PopupUtils;
import net.wrappy.im.util.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import okhttp3.internal.Util;

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
    @BindView(R.id.txtLoginVersionName)
    TextView txtLoginVersionName;
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
        txtLoginVersionName.setText("V" + Utils.getAppVersionName(mContext));
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

                if (TextUtils.isEmpty(s)){
                    showOKDialog(getResources().getString(R.string.User_ID_cannot_be_empty));
                    return;
                }
                Pattern p= Pattern.compile(ConsUtils.WRAPPY_USERNAME_FROMAT);
                Matcher m=p.matcher(s);
                if(!m.matches()){
                    showOKDialog(getResources().getString(R.string.check_User_ID_format_letters_numbers_beginning_with_letters));
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
                String s1 = response.body().toString();
                UserNameStatus us = new Gson().fromJson(response.body().toString(), UserNameStatus.class);
                if (us.code == 1000){
                    Bundle bundle = new Bundle();
                    bundle.putString(ConsUtils.USERNAME, s);
                    bundle.putInt(ConsUtils.NUMBER_OF_LOCKS, us.data.count);
                    overlayForResult(InputPasswordLoginActivity.class, 100, bundle);
//                    overlay(InputPasswordLoginActivity.class, bundle);
                }else {
                    String status = us.data.status;
                    if (ConsUtils.NORMAL.equals(status)){
//                    showOKDialog(us.getMessage() + us.getData().getLockLeftSeconds());
                    }else if (ConsUtils.NOT_EXIST.equals(status)){
                     /** 帐号不存在*/
                    showOKDialog(us.message);
                    }else if (ConsUtils.LOCKED.equals(status)){
                        int count = us.data.count;
                        if (count > 6){
                            /** 帐号被锁，剩余多少秒后解锁*/
                            showOKDialog(us.message + ", please contact our customer service");
                        }else {
                            try {
                             /** 帐号被锁，剩余多少秒后解锁*/
//                            int seconds = Integer.parseInt(us.data.lockLeftSeconds);
//                                Log.e(TAG, "时间: " + seconds);
//                                Log.e(TAG, "时间: " + Utils.secToTime(seconds));
                            showOKDialog(us.message + ", the remaining \n" + Utils.formatTime(us.data.lockLeftSeconds * 1000));
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            @Override
            public void error(Response<String> response) {
                AppFuncs.dismissProgressWaiting();
                showErroe();
            }
        });


    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String pattern;
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            pattern = data.getStringExtra("pattern");
             showOKDialog(pattern);
        }
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
