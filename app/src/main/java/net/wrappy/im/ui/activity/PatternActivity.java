package net.wrappy.im.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;

import net.wrappy.im.R;
import net.wrappy.im.contants.ConsUtils;
import net.wrappy.im.contants.Url;
import net.wrappy.im.model.AccountHelper;
import net.wrappy.im.model.Register;
import net.wrappy.im.util.AppFuncs;
import net.wrappy.im.util.OkUtil;
import net.wrappy.im.util.PopupUtils;

import java.io.Serializable;
import java.util.ArrayList;
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

//    List<Integer> list = new ArrayList();
    private static final String TAG = "PatternActivity";
    private String mIntentType;
    private Register mRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mIntentType = getIntent().getStringExtra(ConsUtils.INTENT);
        mRegister = (Register) getIntent().getSerializableExtra(ConsUtils.REGISTRATION);
        if (!TextUtils.equals(mIntentType, ConsUtils.INTENT_REGISTER)){
            setTypePattern(TYPE_NOCONFIRM);
        }
        if (ConsUtils.INTENT_CHECK.equals(mIntentType)){
            title.setText(getResources().getString(me.tornado.android.patternlock.R.string.update_user_info));
        }else {
            title.setText(getResources().getString(me.tornado.android.patternlock.R.string.registration));
        }
    }

    public static Intent getStartIntent(Activity context) {
        return new Intent(context, PatternActivity.class);
    }

    @Override
    protected void onSetPattern(List<PatternView.Cell> pattern) {
        Intent intent;
//        String ss = "";
//        list.clear();
//        String str = PatternUtils.patternToString(pattern);
//        for (int i = 0; i <str.length() ; i++) {
//            list.add(Integer.parseInt(str.substring(i,i+1)) + 1);
//        }
//        for (int i = 0; i <list.size() ; i++) {
//            ss = ss + list.get(i);
//        }
        Log.e(TAG, "九宫格数据: " + PatternUtils.patternToString(pattern));
                switch (mIntentType) {
            case ConsUtils.INTENT_CHECK:
                intent = new Intent();
                intent.putExtra("pattern", PatternUtils.patternToString(pattern));
//                intent.putExtra("pattern", ss);
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
