package net.wrappy.im.ui.activity;

import android.app.Activity;
import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import net.wrappy.im.BaseActivity;
import net.wrappy.im.R;
import net.wrappy.im.contants.ConsUtils;
import net.wrappy.im.ui.view.Layout;

import butterknife.BindView;

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
                if (TextUtils.isEmpty(s)) {
                    toast("username is empty");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString(ConsUtils.USERNAME, s);
                overlay(InputPasswordLoginActivity.class, bundle);
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

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, LauncherActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

}
