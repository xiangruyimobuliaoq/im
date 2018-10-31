package net.wrappy.im.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import net.wrappy.im.model.AccountHelper;
import net.wrappy.im.model.Register;
import net.wrappy.im.model.Result;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.model.Response;

import net.wrappy.im.R;
import net.wrappy.im.contants.ConsUtils;
import net.wrappy.im.contants.Url;
import net.wrappy.im.model.Safety;
import net.wrappy.im.model.ValidationProblem;
import net.wrappy.im.ui.activity.ModifierSecurityQuestionActivity;
import net.wrappy.im.ui.activity.ModifyPasswordActivity;
import net.wrappy.im.ui.activity.PatternActivity;
import net.wrappy.im.util.AppDelegate;
import net.wrappy.im.util.AppFuncs;
import net.wrappy.im.util.ManagementAllActivity;
import net.wrappy.im.util.OkUtil;
import net.wrappy.im.util.PopupUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by ben on 07/12/2017.
 */

public class ForgetPasswordQuestionFragment extends Fragment {

    private static String TYPE = "typeofsecu";
    private static final String TAG = "ForgetPasswordQuestionF";
    /**
     * result : success
     * message : account has been blocked sucessfully
     */

    public String result;
    public String message;

    View mainView;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title01)
    TextView title01;
    @BindView(R.id.title02)
    TextView title02;
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
    String secretKey;
    List<Safety.DataBean> sd;
    AppDelegate appDelegate;
    Safety sa;
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


    @Override
    public void onDestroy() {
        super.onDestroy();
        ManagementAllActivity.removeActivity(getActivity());
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.forget_password_question_fragment, null);
        ManagementAllActivity.addActivity(getActivity());
        ButterKnife.bind(this, mainView);
        type = getArguments().getInt(TYPE, 0);
        if (type == 1) {
            txtSecurityQuestionTitle.setText(getString(R.string.security_change_old_question));
        }
        getListQuestion();
        title.setText(getResources().getString(R.string.forgot_password));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        return mainView;
    }

    private void getListQuestion() {
        AppFuncs.showProgressWaiting(getActivity());
        String username = getActivity().getIntent().getStringExtra("username");
        OkUtil.publicGet(Url.accounts_securityQuestions + username + "?count=2", new OkUtil.Callback() {
            @Override
            public void success(Response<String> response) {
                Log.e("123", response.body());
                AppFuncs.dismissProgressWaiting();
                sa = new Gson().fromJson(response.body().toString(),Safety.class);
                if (1000 == sa.getCode()){
//                    showOKDialog(sa.getMessage());
                    sd = sa.getData();
                    for (int i = 0; i <sd.size() ; i++) {
                        if (i == 0){
                            title01.setText(sd.get(i).getQuestion());
                        }else {
                            title02.setText(sd.get(i).getQuestion());
                        }
                    }

                }else {
                    showOKDialog(sa.getMessage());
                }


            }

            @Override
            public void error(Response<String> response) {
                try {
                    Log.e("123", response.getRawResponse().body().string());
                    Result<String> o = new Gson().fromJson(response.getRawResponse().body().string(), new TypeToken<Result<String>>() {
                    }.getType());
                    if (o != null){
                    PopupUtils.showOKDialog(getActivity(), "tips", o.message);
                    }
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
        }
        if (TextUtils.isEmpty(error)) {
//            AppFuncs.alert(getActivity(), error, true);
             postSendDate(answer01,answer02);
        }else {
            showOKDialog(error);
        }
    }

         List<sendData> list = new ArrayList<>();
    private void postSendDate(String answer01, String answer02) {
         list.clear();
         list.add(new sendData(sd.get(0).getCode(),answer01));
         list.add(new sendData(sd.get(1).getCode(),answer02));
        String username = getActivity().getIntent().getStringExtra("username");
        AppFuncs.showProgressWaiting(getActivity());
         OkUtil.publicPost(Url.accounts_securityQuestions + username + "/validate",new Gson().toJson(list), new OkUtil.Callback() {
            @Override
            public void success(Response<String> response) {
                AppFuncs.dismissProgressWaiting();
                String s = response.body().toString();
                if (!TextUtils.isEmpty(s)){
                  ValidationProblem vp = new Gson().fromJson(s, ValidationProblem.class);
                  if (vp.getCode() == 1000){
                      secretKey = vp.getData();
                      Bundle bundle = new Bundle();
                      bundle.putString(ConsUtils.INTENT, ConsUtils.INTENT_REGISTER);
                      bundle.putString(ConsUtils.WRAPPY_MODIFY_PASSWORD,ConsUtils.WRAPPY_MODIFY_PASSWORD);
                      bundle.putString(ConsUtils.WRAPPY_SECRETKEY,secretKey);
                      overlayForResult(PatternActivity.class, 100, bundle);
                  }else {
                      showOKDialog(vp.getMessage());
                  }

                }
            }

             @Override
             public void error(Response<String> response) {
                 try {
                     Log.e("123", response.getRawResponse().body().string());
                     Result<String> o = new Gson().fromJson(response.getRawResponse().body().string(), new TypeToken<Result<String>>() {
                     }.getType());
                     if (o != null){
//                         showOKDialog(o.message);
                         PopupUtils.showOKDialog(getActivity(), "tips", o.message);
                     }
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
                 AppFuncs.dismissProgressWaiting();
             }


        });

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            String pattern = data.getStringExtra("pattern");
            Intent intent = new Intent(getActivity(), ModifyPasswordActivity.class);
            intent.putExtra(ConsUtils.WRAPPY_SECRETKEY,secretKey);
            intent.putExtra(ConsUtils.WRAPPY_GESTURE_PASSWORD,pattern);
            startActivity(intent);
        }
    }

    private void TAG(String s) {
        Log.i("LTH", s);
    }


    public void showOKDialog(String msg){
        PopupUtils.showCustomDialog(getActivity(),"",msg,R.string.ok,-1,null,null);
    }

    class sendData{
        public String code;
        public String answer;
        public sendData(String code,String answer){
            this.code = code;
            this.answer = answer;
        }
    }

    //请求失败，请重新请求
    public void showErroe(){
        PopupUtils.showCustomDialog(getActivity(),"",getActivity().getResources().getString(R.string.request_failed),R.string.ok,-1,null,null);
    }
    /**
     * 转跳 没有finish
     *
     * @param classObj
     * @param requestCode
     * @param params
     */
    public void overlayForResult(Class<?> classObj, int requestCode, Bundle params) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setClass(getActivity(), classObj);
        intent.putExtras(params);
        startActivityForResult(intent, requestCode);
        getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
    }


}
