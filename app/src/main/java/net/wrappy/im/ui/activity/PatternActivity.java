package net.wrappy.im.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import net.wrappy.im.contants.ConsUtils;
import net.wrappy.im.model.Register;

import java.io.Serializable;
import java.util.List;

import me.tornado.android.patternlock.PatternUtils;
import me.tornado.android.patternlock.PatternView;
import me.tornado.android.patternlock.SetPatternActivity;

/**
 * 创建者     彭龙
 * 创建时间   2018/6/20 上午11:06
 * 描述	      ${TODO}
 * <p>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class PatternActivity extends SetPatternActivity {

    private String mIntentType;
    private Register mRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIntentType = getIntent().getStringExtra(ConsUtils.INTENT);
        mRegister = (Register) getIntent().getSerializableExtra(ConsUtils.REGISTRATION);
        if (!TextUtils.equals(mIntentType, ConsUtils.INTENT_REGISTER))
            setTypePattern(TYPE_NOCONFIRM);
    }

    public static Intent getStartIntent(Activity context) {
        return new Intent(context, PatternActivity.class);
    }

    @Override
    protected void onSetPattern(List<PatternView.Cell> pattern) {
        Intent intent;
        switch (mIntentType) {
            case ConsUtils.INTENT_CHECK:
                intent = new Intent();
                intent.putExtra("pattern", PatternUtils.patternToString(pattern));
                setResult(RESULT_OK, intent);
                finish();
                break;
            case ConsUtils.INTENT_LOGIN:
                intent = new Intent(this, InputPasswordLoginActivity.class);
                startActivity(intent);
                break;
            case ConsUtils.INTENT_REGISTER:
                String s = PatternUtils.patternToString(pattern);
                mRegister.extendedInfo.patternPassword = s;
                intent = new Intent(this, InputPasswordRegisterActivity.class);
                intent.putExtra(ConsUtils.REGISTRATION, mRegister);
                startActivity(intent);
                break;
        }
    }
}
