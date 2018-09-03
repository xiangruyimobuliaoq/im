package net.wrappy.im.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.lzy.okgo.model.Response;

import net.wrappy.im.BaseActivity;
import net.wrappy.im.R;
import net.wrappy.im.contants.Url;
import net.wrappy.im.model.AccountHelper;
import net.wrappy.im.ui.view.Layout;
import net.wrappy.im.util.AppFuncs;
import net.wrappy.im.util.OkUtil;
import net.wrappy.im.util.PopupUtils;

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
        btnProfileComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();

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
                AccountHelper.FIND_USER_ACCOUNT.Response json = new Gson().fromJson(response.body(), AccountHelper.FIND_USER_ACCOUNT.Response.class);
                PopupUtils.showOKDialog(ForgetIDActivity.this, "tips", json.message);
            }
        });
    }

    private void check() {
        mEmail = edProfileEmail.getText().toString().trim();
        mPhone = edProfilePhone.getText().toString().trim();

        if (TextUtils.isEmpty(mEmail)) {
            toast("email is empty");
            return;
        }
        if (TextUtils.isEmpty(mPhone)) {
            toast("phone is empty");
            return;
        }
        request();
    }
}