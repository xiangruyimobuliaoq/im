package net.wrappy.im.ui.activity;

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
import net.wrappy.im.util.OkUtil;
import net.wrappy.im.util.PopupUtils;

import java.util.List;

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
@Layout(layoutId = R.layout.activity_inputpasswordregister)
public class InputPasswordRegisterActivity extends BaseActivity {
    private static final String TAG = "InputPasswordRegisterAc";
    @BindView(R.id.btcreatepassword)
    Button mBtnLogin;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.edtpassword)
    EditText edtpassword;
    @BindView(R.id.edtusername)
    EditText edtusername;
    @BindView(R.id.edtconfirmpassword)
    EditText edtconfirmpassword;
    private Register mRegister;
    private String mPwd;
    private String mPwd2;
    private String mun;

    @Override
    protected void init() {
        mRegister = (Register) getIntent().getSerializableExtra(ConsUtils.REGISTRATION);
        title.setText(getResources().getString(R.string.registration));
        showHidePassword(edtpassword, false);
        showHidePassword(edtconfirmpassword, false);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPwd = edtpassword.getText().toString().trim();
                mPwd2 = edtconfirmpassword.getText().toString().trim();
                mun = edtusername.getText().toString().trim();
                check();
//                Bundle bundle = new Bundle();
//                bundle.putSerializable(ConsUtils.REGISTRATION, mRegister);
//                overlay(RegistrationSecurityQuestionActivity.class, bundle);
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
//        AppFuncs.showProgressWaiting(this);
        VALIDATE_PASSWORD helper = new VALIDATE_PASSWORD();
        helper.data.username = mun;
        helper.data.password = mPwd;
        helper.language = "en";
        OkUtil.publicPost(Url.accounts_helper, new Gson().toJson(helper), new OkUtil.Callback() {
            @Override
            public void success(Response<String> response) {
                Log.e(TAG, "success: " + response.body());
                AppFuncs.dismissProgressWaiting();
                VALIDATE_PASSWORD.Response json = new Gson().fromJson(response.body(), VALIDATE_PASSWORD.Response.class);
                if (json.code == 1000) {
                    mRegister.extendedInfo.username = mun;
                    mRegister.extendedInfo.password = mPwd;
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(ConsUtils.REGISTRATION, mRegister);
                    overlay(RegistrationSecurityQuestionActivity.class, bundle);
                } else if (json.code == -1000) {
                    if (json.data == null){
                        showOKDialog(json.message);
                    }else {
                        List<String> data = json.data;
                        String message = "";
                        for (int i = 0; i <data.size() ; i++) {
                              message = message + data.get(i) + ",";
                        }
                        showOKDialog(message);
                    }
                } else {
                    PopupUtils.showOKDialog(InputPasswordRegisterActivity.this, "", json.message);
                }
            }
            @Override
            public void error(Response<String> response) {
                Log.e(TAG, "error: " + response.body());
                AppFuncs.dismissProgressWaiting();
                showErroe();

            }
        });
    }

    private void check() {
        if (TextUtils.isEmpty(mun)) {
            showOKDialog(getResources().getString(R.string.User_ID_cannot_be_empty));
            return;
        }
        if (!mun.matches("^[a-zA-Z0-9]{6,48}$")){
            showOKDialog(getResources().getString(R.string.check_User_ID_format_letters_numbers_beginning_with_letters));
            return;
        }
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

        verifyTheAccount();
    }

    private void verifyTheAccount() {


        AppFuncs.showProgressWaiting(this);
        AccountHelper.VALIDATE_PASSWORD_TWO helper = new AccountHelper.VALIDATE_PASSWORD_TWO();
        helper.data.username = mun;
        helper.data.password = mPwd;
        OkUtil.publicPost(Url.accounts_helper, new Gson().toJson(helper), new OkUtil.Callback() {
            @Override
            public void success(Response<String> response) {
                Log.e(TAG, "验证帐号: " + response.body());
                VALIDATE_PASSWORD.Verification json = new Gson().fromJson(response.body(), VALIDATE_PASSWORD.Verification.class);
                if (json.code == 1000){
                    checkFromServer();
                }else {
                    AppFuncs.dismissProgressWaiting();
                    showOKDialog(json.message);
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
}
