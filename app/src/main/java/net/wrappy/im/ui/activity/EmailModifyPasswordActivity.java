package net.wrappy.im.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import net.wrappy.im.contants.ConsUtils;
import net.wrappy.im.contants.Url;
import net.wrappy.im.model.AccountHelper;
import net.wrappy.im.ui.view.Layout;
import net.wrappy.im.util.AppFuncs;
import net.wrappy.im.util.OkUtil;
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
@Layout(layoutId = R.layout.activity_email_modify_password)
public class EmailModifyPasswordActivity extends BaseActivity {

    private static final String TAG = "EmailModifyPasswordActi";
    @BindView(R.id.back)
    protected ImageView back;
    @BindView(R.id.title)
    protected TextView title;

    @BindView(R.id.edProfileEmail)
    protected EditText edProfileEmail;

    @BindView(R.id.btnProfileComplete)
    protected Button btnProfileComplete;
    private String mEmail;

    public static String emailSecretKey;
    @Override
    protected void init() {
        emailSecretKey = TAG;
        Intent intent = getIntent();
        String action = intent.getAction();
        if (Intent.ACTION_VIEW.equals(action)) {
            Uri uri = intent.getData();
            if (uri != null) {
                String secretKey = uri.getQueryParameter("secretKey");
                Log.e(TAG, "init: " + secretKey);
                Bundle bundle = new Bundle();
                bundle.putString(ConsUtils.INTENT, ConsUtils.INTENT_REGISTER);
                bundle.putString(ConsUtils.WRAPPY_MODIFY_PASSWORD,ConsUtils.WRAPPY_MODIFY_PASSWORD);
                bundle.putString(ConsUtils.WRAPPY_SECRETKEY,secretKey);
                overlayForResult(PatternActivity.class, 100, bundle);
                finish();
            }
        }
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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String action = intent.getAction();
        if (Intent.ACTION_VIEW.equals(action)) {
            Uri uri = intent.getData();
            if (uri != null) {
                String secretKey = uri.getQueryParameter("secretKey");
                Log.e(TAG, "init: " + secretKey);
                Bundle bundle = new Bundle();
                bundle.putString(ConsUtils.INTENT, ConsUtils.INTENT_REGISTER);
                bundle.putString(ConsUtils.WRAPPY_MODIFY_PASSWORD,ConsUtils.WRAPPY_MODIFY_PASSWORD);
                bundle.putString(ConsUtils.WRAPPY_SECRETKEY,secretKey);
                overlayForResult(PatternActivity.class, 100, bundle);
            }
          }
        }

    private void check() {
        mEmail = edProfileEmail.getText().toString().trim();
        if (TextUtils.isEmpty(mEmail)) {
            showOKDialog(getResources().getString(R.string.email_address_cannot_be_empty));
            return;
        }
        if (!Utils.isEmail(mEmail)){
            showOKDialog(getResources().getString(R.string.email_address_format_not_correct));
            return;
        }
        request();
    }



    private void request() {
        AppFuncs.showProgressWaiting(this);
        AccountHelper.sendRecoveryEmail account = new AccountHelper.sendRecoveryEmail();
        account.email = mEmail;
        Log.e(TAG, "request: " + new Gson().toJson(account));
        OkUtil.publicPost(Url.accounts + "/sendRecoveryEmail", new Gson().toJson(account), new OkUtil.Callback() {
            @Override
            public void success(Response<String> response) {
                AppFuncs.dismissProgressWaiting();
                Log.e(TAG, "success: " + response.body());
                AccountHelper.FIND_USER_ACCOUNT.Response json = new Gson().fromJson(response.body(), AccountHelper.FIND_USER_ACCOUNT.Response.class);
                if (json.code == 1000){
                   showOKDialog("Send success！");
                }else {
                showOKDialog(json.message);
                }
            }

            @Override
           public void error(Response<String> response) {
                AppFuncs.dismissProgressWaiting();
                Log.e(TAG, "error: " + response.body());
            }
        });
    }

}