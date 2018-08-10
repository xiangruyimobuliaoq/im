package net.wrappy.im.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodiebag.pinview.Pinview;
import com.google.gson.Gson;
import com.hbb20.CountryCodePicker;
import com.lzy.okgo.model.Response;

import net.wrappy.im.BaseActivity;
import net.wrappy.im.R;
import net.wrappy.im.contants.ConsUtils;
import net.wrappy.im.contants.Url;
import net.wrappy.im.model.AccountHelper;
import net.wrappy.im.model.Register;
import net.wrappy.im.ui.view.Layout;
import net.wrappy.im.util.AppFuncs;
import net.wrappy.im.util.OkUtil;

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

@Layout(layoutId = R.layout.activity_validatephone)
public class ValidatePhoneActivity extends BaseActivity {
    @BindView(R.id.txt_pin_entry)
    Pinview txtPin;
    @BindView(R.id.lnVerifyContainer)
    LinearLayout lnVerifyContainer;
    @BindView(R.id.picker)
    CountryCodePicker picker;
    @BindView(R.id.edVerifyPhone)
    EditText edVerifyPhone;
    @BindView(R.id.btnVerifyCheck)
    Button btnVerifyCheck;
    @BindView(R.id.sendCode)
    TextView sendCode;
    private String mPhone;
    private String mCountryCode;

    @Override
    protected void init() {
        AppFuncs.dismissKeyboard(this);
        picker.changeDefaultLanguage(CountryCodePicker.Language.ENGLISH);
        picker.setAutoDetectedCountry(true);
        btnVerifyCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = txtPin.getValue();
                mPhone = edVerifyPhone.getText().toString().trim();
                if (TextUtils.isEmpty(value)) {
                    toast("Please input the SNS code");
                    return;
                }
                if (TextUtils.isEmpty(mPhone)) {
                    toast("Please input the phone number");
                    return;
                }
                validateCode(value, mPhone);
            }
        });
        sendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhone = edVerifyPhone.getText().toString().trim();
                if (TextUtils.isEmpty(mPhone)) {
                    toast("Please input the phone number");
                    return;
                }
                mCountryCode = picker.getSelectedCountryCodeWithPlus();
                validatePhone(mCountryCode, mPhone);
            }
        });
    }

    private void validateCode(String value, String phone) {
        AppFuncs.showProgressWaiting(this);
        AccountHelper.VALIDATE_SMS_CODE helper = new AccountHelper.VALIDATE_SMS_CODE();
        helper.data.countryCode = mCountryCode;
        helper.data.type = ConsUtils.REGISTRATION;
        helper.data.phone = phone;
        helper.data.code = value;
        OkUtil.publicRequest(Url.accounts_helper, new Gson().toJson(helper), new OkUtil.Callback() {
            @Override
            public void success(Response<String> response) {
                AppFuncs.dismissProgressWaiting();
                AccountHelper.Response res = new Gson().fromJson(response.body(), AccountHelper.Response.class);
                toast(res.message);
                if (res.code == 1000) {
                    Bundle bundle = new Bundle();
                    bundle.putString(ConsUtils.INTENT, ConsUtils.INTENT_REGISTER);
                    Register register = new Register();
                    register.mobilePhone = mCountryCode + mPhone;
                    bundle.putSerializable(ConsUtils.REGISTRATION, register);
                    overlay(PatternActivity.class, bundle);
                    AppFuncs.dismissKeyboard(ValidatePhoneActivity.this);
                }
            }
        });
    }

    private void validatePhone(String selectedCountryCodeWithPlus, String phone) {
        AppFuncs.showProgressWaiting(this);
        AccountHelper.VALIDATE_PHONE helper = new AccountHelper.VALIDATE_PHONE();
        helper.data.checkRegistered = true;
        helper.data.sendValidationCode = true;
        helper.data.countryCode = selectedCountryCodeWithPlus;
        helper.data.codeType = ConsUtils.REGISTRATION;
        helper.data.phone = phone;
        OkUtil.publicRequest(Url.accounts_helper, new Gson().toJson(helper), new OkUtil.Callback() {
            @Override
            public void success(Response<String> response) {
                AppFuncs.dismissProgressWaiting();
                AccountHelper.Response res = new Gson().fromJson(response.body(), AccountHelper.Response.class);
                toast(res.message);
            }
        });
    }
}
