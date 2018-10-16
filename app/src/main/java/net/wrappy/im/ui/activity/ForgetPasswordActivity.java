package net.wrappy.im.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import net.wrappy.im.BaseActivity;
import net.wrappy.im.R;
import net.wrappy.im.ui.fragment.ForgetPasswordQuestionFragment;
import net.wrappy.im.ui.view.Layout;
import net.wrappy.im.util.AppDelegate;
import net.wrappy.im.util.ManagementAllActivity;

import butterknife.ButterKnife;

/**
 * Created by ben on 07/12/2017.
 */
@Layout(layoutId = R.layout.forget_password_question_activity)
public class ForgetPasswordActivity extends BaseActivity implements AppDelegate{

    public static final String FORGET_PASSWORD = "forgetpassword";

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, ForgetPasswordActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ManagementAllActivity.removeActivityTwo(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password_question_activity);
        ButterKnife.bind(this);
//        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_ab_arrow_back);
//        getSupportActionBar().setTitle(R.string.forget_password);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        goToQuestionFragment();
        ManagementAllActivity.addActivityTwo(this);
    }

    @Override
    protected void init() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            LoginActivity.start(ForgetPasswordActivity.this);
            finish();
        }
        return true;
    }

    private void goToQuestionFragment() {
        getFragmentManager().beginTransaction().replace(R.id.forgetPasswordContainer, ForgetPasswordQuestionFragment.newInstance(0)).commit();
    }



    @Override
    public void onChangeInApp(int id, String data) {
        switch (id) {
            case ACTION_FROM_QUESTION:
                if (data.isEmpty()) {
                } else {
                    data = data.replaceAll("\"", "");
                    if (data.length() > 10) {
                        Intent intent = PatternActivity.getStartIntent(this);
                        Bundle arg = new Bundle();
                        arg.putInt("type", LoginActivity.REQUEST_CODE_INPUT_NEW_PASSWORD);
                        arg.putString("username", "");
                        arg.putString(FORGET_PASSWORD, data);
                        intent.putExtras(arg);
                        startActivity(intent);
                        finish();
                    }
                }

                break;
            case ACTION_FROM_CHECK_EMAIL:
                break;
            default:
        }
    }
}
