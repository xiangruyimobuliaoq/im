package net.wrappy.im.ui.activity;

import android.content.Intent;
import android.os.Bundle;
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
import net.wrappy.im.contants.Url;
import net.wrappy.im.model.AccountHelper;
import net.wrappy.im.model.AccountHelper.VALIDATE_PASSWORD;
import net.wrappy.im.model.Register;
import net.wrappy.im.ui.view.Layout;
import net.wrappy.im.util.AppFuncs;
import net.wrappy.im.util.ManagementAllActivity;
import net.wrappy.im.util.OkUtil;
import net.wrappy.im.util.PopupUtils;

import butterknife.BindView;

/**
 * 创建者     彭龙
 * 创建时间   2018/6/21 上午9:49
 * 描述	      ${TODO}
 * <p>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
@Layout(layoutId = R.layout.activity_modify_password)
public class ModifyPasswordActivity extends BaseActivity {
    private static final String TAG = "ModifyPasswordActivity";
    @BindView(R.id.btcreatepassword)
    Button mBtnLogin;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.edtpassword)
    EditText edtpassword;
    @BindView(R.id.edtconfirmpassword)
    EditText edtconfirmpassword;
    private String mPwd;
    private String mPwd2;
    private String secretKey,gesturePassword;



    @Override
    protected void init() {
        secretKey = getIntent().getStringExtra(ConsUtils.WRAPPY_SECRETKEY);
        gesturePassword = getIntent().getStringExtra(ConsUtils.WRAPPY_GESTURE_PASSWORD);
        title.setText(getResources().getString(R.string.reset_password));
        showHidePassword(edtpassword, false);
        showHidePassword(edtconfirmpassword, false);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPwd = edtpassword.getText().toString().trim();
                mPwd2 = edtconfirmpassword.getText().toString().trim();
                check();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void checkFromServer() {


        AccountHelper.sendModifyPasswordData spd = new AccountHelper.sendModifyPasswordData();
        spd.secretKey = secretKey;
        spd.newPassword = mPwd;
        spd.newPatternPassword = gesturePassword;
        AppFuncs.showProgressWaiting(this);
        OkUtil.privatePut( this,Url.accounts + "/resetPassword", new Gson().toJson(spd),new OkUtil.Callback() {
            @Override
            public void success(Response<String> response) {
                Log.e(TAG, "success: " + response.body());
                AppFuncs.dismissProgressWaiting();
                AccountHelper.Response json = new Gson().fromJson(response.body().toString(), AccountHelper.Response.class);
                if (json.code == 1000){
                    PopupUtils.showCustomDialog(mContext, "", json.message, R.string.ok, -1, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            Intent intent = new Intent(ModifyPasswordActivity.this,InputPasswordLoginActivity.class);
//                            startActivity(intent);
                            ManagementAllActivity.finishAllActivity();
                            overlay(LoginActivity.class);
                        }
                    }, null);
                }else {
                    PopupUtils.showCustomDialog(mContext,"",json.message, R.string.ok,-1,null,null);
                }
            }

            @Override
            public void error(Response<String> response) {
                super.error(response);
                AppFuncs.dismissProgressWaiting();
                Log.e(TAG, "error: " + response.body());
                showErroe();
            }
        });


    }

    private void check() {

        if (TextUtils.isEmpty(mPwd)) {
            showOKDialog("Password can't be empty.");
            return;
        }
        if (mPwd.length() < 8){
            showOKDialog("The password is at least 8 bits.");
            return;
        }
        if (!mPwd.equals(mPwd2)){
            showOKDialog("The password entered two times is inconsistent.");
            return;
        }
        checkFromServer();
    }
}
