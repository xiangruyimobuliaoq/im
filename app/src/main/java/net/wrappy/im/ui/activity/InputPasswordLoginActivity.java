package net.wrappy.im.ui.activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
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
import net.wrappy.im.model.Auth;
import net.wrappy.im.ui.view.Layout;
import net.wrappy.im.util.AppFuncs;
import net.wrappy.im.util.OkUtil;
import net.wrappy.im.util.SpUtil;
import butterknife.BindView;
import me.tornado.android.patternlock.PatternUtils;

/**
 * 创建者     彭龙
 * 创建时间   2018/6/28 上午9:12
 * 描述	      ${TODO}
 * <p>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
@Layout(layoutId = R.layout.activity_inputpasswordlogin)
public class InputPasswordLoginActivity extends BaseActivity {
    private static final String TAG = "InputPasswordLoginActiv";
    @BindView(R.id.back)
    protected ImageView back;
    @BindView(R.id.iv_type)
    protected ImageView iv_type;
    @BindView(R.id.title)
    protected TextView title;
    @BindView(R.id.btnlogin)
    protected Button btnLogin;
    @BindView(R.id.btnforgetpass)
    protected TextView mBtnForgetPass;
    @BindView(R.id.edtpassword)
    protected EditText edtpassword;
    private String mPwd;
    private String mUsername;
    private int numberLocks;
    private boolean isTrue = true;
    @Override
    protected void init() {
        title.setText(getResources().getString(R.string.login));
        mUsername = getIntent().getStringExtra(ConsUtils.USERNAME);
        numberLocks = getIntent().getIntExtra(ConsUtils.NUMBER_OF_LOCKS, 0);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }
        });
        mBtnForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("username", mUsername);
                overlay(ForgetPasswordActivity.class, bundle);

//                createSelectDialog();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        iv_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isTrue){
                    edtpassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    iv_type.setImageResource(R.mipmap.ic_show);
                    isTrue = false;
                    edtpassword.requestFocus();
                }else {
                    edtpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    iv_type.setImageResource(R.mipmap.ic_hidden);
                    isTrue = true;
                    edtpassword.requestFocus();

                }

            }
        });
//        ManagementAllActivity.addActivityTwo(this);
    }

    Dialog mDialog;
    AlertDialog.Builder dialog;
    private void createSelectDialog() {

        dialog = new AlertDialog.Builder(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_select_item, null);
        dialog.setView(view);
        view.findViewById(R.id.Question).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                Bundle bundle = new Bundle();
                bundle.putString("username", mUsername);
                overlay(ForgetPasswordActivity.class, bundle);
            }
        });
        view.findViewById(R.id.email_reset_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                Bundle bundle = new Bundle();
                bundle.putString("username", mUsername);
                overlay(EmailModifyPasswordActivity.class, bundle);
            }
        });
        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog = dialog.show();


    }


    //access
    private void login() {
        AppFuncs.showProgressWaiting(this);
        OkUtil.Login(mUsername, mPwd, new OkUtil.Callback() {
            @Override
            public void success(Response<String> response) {
                AppFuncs.dismissProgressWaiting();
                Log.e(TAG, "success: " + response.body());
                Auth auth = new Gson().fromJson(response.body(), Auth.class);
                if (null != auth.account) {
                    if (auth.error != null){
                        if (numberLocks > 0){
                            Intent intent = new Intent();
                            intent.putExtra("pattern", "User account is locked");
                            setResult(RESULT_OK, intent);
                            finish();
                        }else {
                        if ("User account is locked".equals(auth.error_description)){
                            Intent intent = new Intent();
                            intent.putExtra("pattern", auth.error_description);
                            setResult(RESULT_OK, intent);
                            finish();
                        }else {
                             showOKDialog(auth.error_description);
                        }
                        }

                    }else {
                        SpUtil.spSaveUsernameOrPassword(ConsUtils.WRAPPY_LOGIN_USERNAME,mUsername);
                        SpUtil.spSaveUsernameOrPassword(ConsUtils.WRAPPY_LOGIN_PASSWORD,mPwd);
                        startAndClearAll(MainActivity.class);
                    }

                } else {
                    showOKDialog("login failed");
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

    private void check() {
        mPwd = edtpassword.getText().toString().trim();
        if (TextUtils.isEmpty(mPwd)) {
//            toast("password is empty");
            showOKDialog("password cannot be empty");
            return;
        }
        login();
    }
}
