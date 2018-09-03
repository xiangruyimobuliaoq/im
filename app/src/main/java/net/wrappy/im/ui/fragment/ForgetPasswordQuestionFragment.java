package net.wrappy.im.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.LauncherActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import net.wrappy.im.model.Result;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import net.wrappy.im.BaseActivity;
import net.wrappy.im.R;
import net.wrappy.im.contants.ConsUtils;
import net.wrappy.im.contants.Url;
import net.wrappy.im.model.Result;
import net.wrappy.im.util.AppDelegate;
import net.wrappy.im.util.AppFuncs;
import net.wrappy.im.util.OkUtil;
import net.wrappy.im.util.PopupUtils;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by ben on 07/12/2017.
 */

public class ForgetPasswordQuestionFragment extends Fragment {

    private static String TYPE = "typeofsecu";
    /**
     * result : success
     * message : account has been blocked sucessfully
     */

    public String result;
    public String message;

    View mainView;
    @BindView(R.id.txtSecurityForgetQuestion01)
    TextView txtSecurityForgetQuestion01;
    @BindView(R.id.txtSecurityForgetQuestion02)
    TextView txtSecurityForgetQuestion02;
    @BindView(R.id.edSecurityForgetQuestion01)
    EditText edSecurityForgetQuestion01;
    @BindView(R.id.edSecurityForgetQuestion02)
    EditText edSecurityForgetQuestion02;
    @BindView(R.id.txtSecurityQuestionTitle)
    TextView txtSecurityQuestionTitle;

    AppDelegate appDelegate;
//    ArrayList<WpKMemberSecurityQuestionDto> stringQuestions = new ArrayList<>();

    int type = 0;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        appDelegate = (AppDelegate) activity;
    }


    public static ForgetPasswordQuestionFragment newInstance(int type) {
        ForgetPasswordQuestionFragment fragment = new ForgetPasswordQuestionFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.forget_password_question_fragment, null);
        ButterKnife.bind(this, mainView);
        type = getArguments().getInt(TYPE, 0);
        if (type == 1) {
            txtSecurityQuestionTitle.setText(getString(R.string.security_change_old_question));
        }
        getListQuestion();
        return mainView;
    }

    private void getListQuestion() {
        AppFuncs.showProgressWaiting(getActivity());
        String username = getActivity().getIntent().getStringExtra("username");
        OkUtil.publicGet(Url.accounts_securityQuestions + "/" + username + "/" + "2", new OkUtil.Callback() {
            @Override
            public void success(Response<String> response) {
                Log.e("123", response.body());
                AppFuncs.dismissProgressWaiting();
            }

            @Override
            public void error(Response<String> response) {
                try {
                    Log.e("123", response.getRawResponse().body().string());
                    Result<String> o = new Gson().fromJson(response.getRawResponse().body().string(), new TypeToken<Result<String>>() {
                    }.getType());
                    PopupUtils.showOKDialog(getActivity(), "tips", o.message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                AppFuncs.dismissProgressWaiting();
            }
        });
    }

    @OnClick(R.id.btnForgetPasswordQuestion)
    public void onClick(View v) {
        String answer01 = edSecurityForgetQuestion01.getText().toString().trim();
        String answer02 = edSecurityForgetQuestion02.getText().toString().trim();
        String error = "";
        if (answer01.isEmpty()) {
            error = String.format(getString(R.string.error_empty_answer), 1);
        } else if (answer02.isEmpty()) {
            error = String.format(getString(R.string.error_empty_answer), 2);
        } else {
//            if (stringQuestions != null && stringQuestions.size() > 1) {
//                stringQuestions.get(0).setAnswer(answer01);
//                stringQuestions.get(1).setAnswer(answer02);
//                postResultToServer();
//            }
        }
        if (!error.isEmpty()) {
//            AppFuncs.alert(getActivity(), error, true);
        }
    }
    private void TAG(String s) {
        Log.i("LTH", s);
    }
}
