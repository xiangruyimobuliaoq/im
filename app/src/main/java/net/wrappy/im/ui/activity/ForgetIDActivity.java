package net.wrappy.im.ui.activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hbb20.CountryCodePicker;
import com.lzy.okgo.model.Response;

import net.wrappy.im.BaseActivity;
import net.wrappy.im.R;
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
 * 创建时间   2018/6/20 上午9:38
 * 描述	      ${TODO}
 * <p>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
@Layout(layoutId = R.layout.activity_forgetid)
public class ForgetIDActivity extends BaseActivity {

    private static final String TAG = "ForgetIDActivity";
    @BindView(R.id.back)
    protected ImageView back;
    @BindView(R.id.title)
    protected TextView title;
    @BindView(R.id.picker)
    CountryCodePicker picker;
    @BindView(R.id.edProfileEmail)
    protected EditText edProfileEmail;
    @BindView(R.id.edProfilePhone)
    protected EditText edProfilePhone;
    @BindView(R.id.btnProfileComplete)
    protected Button btnProfileComplete;
    private String mEmail;
    private String mPhone;

    @Override
    protected void init() {
        picker.changeDefaultLanguage(CountryCodePicker.Language.ENGLISH);
        picker.setAutoDetectedCountry(true);
        btnProfileComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();

            }
        });
        title.setText(getResources().getString(R.string.forgot_username));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void request() {
        AppFuncs.showProgressWaiting(this);
        AccountHelper.FIND_USER_ACCOUNT account = new AccountHelper.FIND_USER_ACCOUNT();
        account.data.email = mEmail;
        account.data.phone = mPhone;
        OkUtil.publicPost(Url.accounts_helper, new Gson().toJson(account), new OkUtil.Callback() {
            @Override
            public void success(Response<String> response) {
                AppFuncs.dismissProgressWaiting();
                Log.e(TAG, "success: " + response.body().toString());
                AccountHelper.FIND_USER_ACCOUNT.Response json = new Gson().fromJson(response.body(), AccountHelper.FIND_USER_ACCOUNT.Response.class);
                PopupUtils.showOKDialog(ForgetIDActivity.this, "tips", json.message);
            }

            @Override
           public void error(Response<String> response) {
                AppFuncs.dismissProgressWaiting();
            }
        });
    }

    private void check() {
        mEmail = edProfileEmail.getText().toString().trim();
        mPhone = edProfilePhone.getText().toString().trim();

        if (TextUtils.isEmpty(mEmail)) {
//            toast("email is empty");
            showOKDialog("email is empty");
            return;
        }
        if (!Utils.isEmail(mEmail)){
            showOKDialog(getResources().getString(R.string.illegal_email));
            return;
        }

        if (TextUtils.isEmpty(mPhone)) {
//            toast("phone is empty");
            showOKDialog("phone is empty");
            return;
        }
        mPhone = picker.getSelectedCountryCodeWithPlus() + mPhone;

        if (!Utils.isPhoneNumberValid(mPhone, picker.getSelectedCountryCodeWithPlus())){
            showOKDialog(getResources().getString(R.string.please_input_the_correct_cell_phone_number));
            return;
        }
        request();
    }






}