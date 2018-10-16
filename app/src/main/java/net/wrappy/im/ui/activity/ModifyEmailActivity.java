package net.wrappy.im.ui.activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.goodiebag.pinview.Pinview;
import com.google.gson.Gson;
import com.lzy.okgo.model.Response;
import net.wrappy.im.BaseActivity;
import net.wrappy.im.R;
import net.wrappy.im.contants.ConsUtils;
import net.wrappy.im.contants.Url;
import net.wrappy.im.model.AccountHelper;
import net.wrappy.im.ui.view.Layout;
import net.wrappy.im.util.AppFuncs;
import net.wrappy.im.util.OkUtil;
import net.wrappy.im.util.PopupUtils;
import net.wrappy.im.util.Utils;

import butterknife.BindView;

/**
 * 创建者     彭龙
 * 创建时间   2018/7/28 下午2:28
 * 描述	      ${TODO}
 * <p>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */

@Layout(layoutId = R.layout.activity_modify_email)
public class ModifyEmailActivity extends BaseActivity {

    private static final String TAG = "ModifyEmailActivity";

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.txt_pin_entry)
    Pinview txtPin;
    @BindView(R.id.lnVerifyContainer)
    LinearLayout lnVerifyContainer;
    @BindView(R.id.edVerifyPhone)
    EditText email;
    @BindView(R.id.btnVerifyCheck)
    Button btnVerifyCheck;
    @BindView(R.id.sendCode)
    TextView sendCode;
    private String mEmail;

    @Override
    protected void init() {
        AppFuncs.dismissKeyboard(this);
        title.setText(getResources().getString(R.string.please_input_your_new_email));
        //发送邮箱验证码
//        btnVerifyCheck.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String value = txtPin.getValue();
//                mEmail = email.getText().toString().trim();
//
//                if (TextUtils.isEmpty(mEmail)) {
//                    PopupUtils.showOKDialog(ModifyEmailActivity.this, "", "Please input the email number");
//                    return;
//                }
//
//                if (TextUtils.isEmpty(value)) {
//                    showOKDialog(getResources().getString(R.string.please_input_the_sns_code));
//                    return;
//                }
//
//                if (value.length() != 5){
//                    showOKDialog(getResources().getString(R.string.please_enter_5_bit_verification_code));
//                    return;
//                }
////                validateCode(value, mPhone);
//            }
//        });
        //发送邮箱
        sendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEmail = email.getText().toString().trim();
                if (TextUtils.isEmpty(mEmail)) {
                    showOKDialog(getResources().getString(R.string.email_address_cannot_be_empty));
                    return;
                }
                if (!Utils.isEmail(mEmail)){
                    showOKDialog(getResources().getString(R.string.email_address_format_not_correct));
                    return;
                }

                validatePhone(mEmail);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void validateCode(String value, String phone) {
        AppFuncs.showProgressWaiting(this);
        AccountHelper.VALIDATE_SMS_CODE helper = new AccountHelper.VALIDATE_SMS_CODE();
        helper.data.countryCode = "";
        helper.data.type = ConsUtils.REGISTRATION;
        helper.data.phone = phone;
        helper.data.code = value;
        OkUtil.publicPost(Url.accounts_helper, new Gson().toJson(helper), new OkUtil.Callback() {
            @Override
            public void success(Response<String> response) {
                AppFuncs.dismissProgressWaiting();
                AccountHelper.Response res = new Gson().fromJson(response.body(), AccountHelper.Response.class);
                if (res.code == 1000) {

//                    Bundle bundle = new Bundle();
//                    bundle.putString(ConsUtils.INTENT, ConsUtils.INTENT_REGISTER);
//                    Register register = new Register();
//                    register.mobilePhone = picker.getSelectedCountryCodeWithPlus() + mPhone;
//                    bundle.putSerializable(ConsUtils.REGISTRATION, register);
//                    overlay(PatternActivity.class, bundle);
//                    AppFuncs.dismissKeyboard(ModifyMobilePhoneActivity.this);
                }else {
                    showOKDialog(res.message);
                }
            }

            @Override
            public void error(Response<String> response) {
                super.error(response);
                showErroe();
            }
        });
    }

    private void validatePhone(String email) {
        AppFuncs.showProgressWaiting(this);
        AccountHelper.MODIFY_EMAIL helper = new AccountHelper.MODIFY_EMAIL();
        helper.email = email;
        OkUtil.publicPost(Url.accounts + "/sendRecoveryEmail", new Gson().toJson(helper), new OkUtil.Callback() {
            @Override
            public void success(Response<String> response) {
                AppFuncs.dismissProgressWaiting();
                Log.e(TAG, "success: " + response.body().toString());
                AccountHelper.Response res = new Gson().fromJson(response.body(), AccountHelper.Response.class);
                if (res.code == 1000){
                showOKDialog("Send Success");
                }else {
                showOKDialog(res.message);
                }
//                toast(res.message);
            }

            @Override
            public void error(Response<String> response) {
                super.error(response);
                showErroe();
            }
        });
    }
}
