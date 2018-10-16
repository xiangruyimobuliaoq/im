package net.wrappy.im.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import net.wrappy.im.ui.view.Layout;
import net.wrappy.im.util.AppFuncs;
import net.wrappy.im.util.OkUtil;
import net.wrappy.im.util.PopupUtils;
import net.wrappy.im.util.Utils;

import butterknife.BindView;
import me.tornado.android.patternlock.PatternUtils;

/**
 * 创建者     彭龙
 * 创建时间   2018/7/28 下午2:28
 * 描述	      ${TODO}
 * <p>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */

@Layout(layoutId = R.layout.activity_modify_phone)
public class ModifyPhoneActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.txt_pin_entry)
    Pinview txtPin;
//    @BindView(R.id.lnVerifyContainer)
//    LinearLayout lnVerifyContainer;
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
        title.setText(getResources().getString(R.string.modify_your_phone_number));
        picker.changeDefaultLanguage(CountryCodePicker.Language.ENGLISH);
        picker.setAutoDetectedCountry(true);
        //发送手机验证码
        btnVerifyCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = txtPin.getValue();
                //把电话号码格式化一次
                String formatHponeNumber = Utils.getFormatHponeNumber(edVerifyPhone.getText().toString().trim(), picker.getSelectedCountryNameCode());
                edVerifyPhone.setText(formatHponeNumber);
                mPhone = edVerifyPhone.getText().toString().trim();

                if (TextUtils.isEmpty(mPhone)) {
                    showOKDialog(getResources().getString(R.string.please_input_the_phone_number));
                    return;
                }
                if (!Utils.isPhoneNumberValid(picker.getSelectedCountryCodeWithPlus() + mPhone,mPhone)){
                    showOKDialog(getResources().getString(R.string.please_input_the_correct_cell_phone_number));
                    return;
                }

                if (TextUtils.isEmpty(value)) {
                    PopupUtils.showOKDialog(ModifyPhoneActivity.this, "", getResources().getString(R.string.please_input_the_sns_code));
                    return;
                }

                if (value.length() != 5){
                    showOKDialog(getResources().getString(R.string.please_enter_5_bit_verification_code));
                    return;
                }
                validateCode(value, mPhone);
            }
        });
        //发送手机号码
        sendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhone = edVerifyPhone.getText().toString().trim();
                if (TextUtils.isEmpty(mPhone)) {
                    showOKDialog(getResources().getString(R.string.please_input_the_phone_number));
                    return;
                }
                mCountryCode = picker.getSelectedCountryCodeWithPlus();
                if (!Utils.isPhoneNumberValid(mCountryCode + mPhone,mPhone)){
                    showOKDialog(getResources().getString(R.string.please_input_the_correct_cell_phone_number));
                    return;
                }
                validatePhone(mCountryCode, mPhone);
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
        helper.data.countryCode = picker.getSelectedCountryCodeWithPlus();
        helper.data.type = ConsUtils.CHANGE_MOBILE;
        helper.data.phone = phone;
        helper.data.code = value;
        OkUtil.publicPost(Url.accounts_helper, new Gson().toJson(helper), new OkUtil.Callback() {
            @Override
            public void success(Response<String> response) {
                AppFuncs.dismissProgressWaiting();
                AccountHelper.Response res = new Gson().fromJson(response.body(), AccountHelper.Response.class);
                if (res.code == 1000) {
                    showOKDialog(res.message);

                    PopupUtils.showCustomDialog(mContext, "", res.message, R.string.ok, -1, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.putExtra("pattern",picker.getSelectedCountryCodeWithPlus() + edVerifyPhone.getText().toString().trim());
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }, null);

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

    private void validatePhone(String selectedCountryCodeWithPlus, String phone) {
        AppFuncs.showProgressWaiting(this);
        AccountHelper.VALIDATE_PHONE helper = new AccountHelper.VALIDATE_PHONE();
        helper.data.checkRegistered = true;
        helper.data.sendValidationCode = true;
        helper.data.countryCode = selectedCountryCodeWithPlus;
        helper.data.codeType = ConsUtils.CHANGE_MOBILE;
        helper.data.phone = phone;
        OkUtil.publicPost(Url.accounts_helper, new Gson().toJson(helper), new OkUtil.Callback() {
            @Override
            public void success(Response<String> response) {
                AppFuncs.dismissProgressWaiting();
                AccountHelper.Response res = new Gson().fromJson(response.body(), AccountHelper.Response.class);
                if (res.code == 1000){
                showOKDialog("Send Success");
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
}
