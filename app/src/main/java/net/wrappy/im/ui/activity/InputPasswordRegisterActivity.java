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
import net.wrappy.im.model.AccountHelper.VALIDATE_PASSWORD;
import net.wrappy.im.model.Register;
import net.wrappy.im.ui.view.Layout;
import net.wrappy.im.util.AppFuncs;
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
        AppFuncs.showProgressWaiting(this);
        VALIDATE_PASSWORD helper = new VALIDATE_PASSWORD();
        helper.data.username = mun;
        helper.data.password = mPwd;
        helper.language = "en";
        OkUtil.publicPost(Url.accounts_helper, new Gson().toJson(helper), new OkUtil.Callback() {
            @Override
            public void success(Response<String> response) {
                Log.e(TAG, "success: " + response.body().toString());
                AppFuncs.dismissProgressWaiting();
                VALIDATE_PASSWORD.Response json = new Gson().fromJson(response.body(), VALIDATE_PASSWORD.Response.class);
                if (json.code == 1000) {
                    mRegister.extendedInfo.username = mun;
                    mRegister.extendedInfo.password = mPwd;
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(ConsUtils.REGISTRATION, mRegister);
                    overlay(RegistrationSecurityQuestionActivity.class, bundle);
                } else if (json.code == -1020) {
                    PopupUtils.showOKDialog(InputPasswordRegisterActivity.this, "", json.message);
                } else {
                    String s = "";
                    for (String ss : json.data
                            ) {
                        s += ss + ",";
                    }
                    PopupUtils.showOKDialog(InputPasswordRegisterActivity.this, "", s);
                }
            }
            @Override
            public void error(Response<String> response) {
                String s = response.body().toString();

            }

        });
    }

    private void check() {
        if (TextUtils.isEmpty(mun) || !mun.matches("^[a-zA-Z0-9]{6,48}$")) {
            if (mun.length() < 6 || mun.length() > 30){
                showOKDialog("check user name format, 6-30 letters, numbers, beginning with letters.");
                return;
            }
            return;
        }
        if (TextUtils.isEmpty(mPwd)) {
            showOKDialog("Password is empty");
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
