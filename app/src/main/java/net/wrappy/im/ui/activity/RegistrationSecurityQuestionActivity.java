package net.wrappy.im.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.model.Response;

import net.wrappy.im.BaseActivity;
import net.wrappy.im.R;
import net.wrappy.im.contants.ConsUtils;
import net.wrappy.im.contants.Url;
import net.wrappy.im.model.AccountHelper;
import net.wrappy.im.model.Register;
import net.wrappy.im.ui.view.Layout;
import net.wrappy.im.util.OkUtil;
import net.wrappy.im.util.PopupUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ben on 13/11/2017.
 */
@Layout(layoutId = R.layout.activity_security_question)
public class RegistrationSecurityQuestionActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.btnQuestionComplete)
    Button btnQuestionComplete;
    @BindView(R.id.txtSecurityTitle)
    TextView txtSecurityTitle;

    @BindView(R.id.securityQuestionLayout)
    LinearLayout securityQuestionLayout;

    ArrayList<Spinner> spinnersQuestion = new ArrayList<>();
    ArrayList<EditText> appEditTextViewsAnswers = new ArrayList<>();
    private ArrayAdapter<String> questionsAdapter;
    private List<AccountHelper.SECURITY_QUESTIONS.Response.Data> mData;
    private Register mRegister;

    @Override
    protected void init() {
        title.setText(getResources().getString(R.string.registration));
        mRegister = (Register) getIntent().getSerializableExtra(ConsUtils.REGISTRATION);
        getListQuestion();
        btnQuestionComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (spinnersQuestion.size() > 0) {
                        String errorString = "";
                        ArrayList<String> strings = new ArrayList<>();
                        ArrayList<String> stringQuestions = new ArrayList<>();
                        ArrayList<Register.SecurityQuestions> securityQuestions = new ArrayList<>();
                        for (int i = 0; i < spinnersQuestion.size(); i++) {
                            Spinner spinner = spinnersQuestion.get(i);
                            String question = spinner.getSelectedItem().toString();
                            EditText editTextView = appEditTextViewsAnswers.get(i);
                            String answer = editTextView.getText().toString().trim();
                            if (stringQuestions.size() > 0) {
                                if (stringQuestions.contains(question)) {
                                    //您不能选择相同的安全问题
                                    errorString = getString(R.string.error_question_duplicate);
                                    break;
                                }
                            }
                            // validate answer text
                            if (answer.isEmpty()) {
                                errorString = String.format(getString(R.string.error_empty_answer), (i + 1));
                                break;
                            }

//                            if (answer.length() > 8) {
//                                errorString = String.format(getString(R.string.error_min_length_answer), (i + 1));
//                                break;
//                            }

                            if (answer.length() < 3) {
                                errorString = String.format(getString(R.string.error_min_length_answer), (i + 1));
                                break;
                            }

                            if (strings.size() > 0) {
                                if (strings.contains(answer)) {//你的答案是重复的
                                    errorString = getString(R.string.error_duplicate_answer);
                                    break;
                                }
                            }

                            strings.add(answer);
                            stringQuestions.add(question);
                            String code = null;
                            for (AccountHelper.SECURITY_QUESTIONS.Response.Data dd : mData
                                    ) {
                                if (dd.question.equals(question)) {
                                    code = dd.code;
                                }
                            }
                            Register.SecurityQuestions questions = new Register.SecurityQuestions(code, question, answer);
                            securityQuestions.add(questions);
                        }
                        if (!errorString.isEmpty()) {
                            PopupUtils.showOKDialog(RegistrationSecurityQuestionActivity.this, "", errorString);
                            return;
                        }
                        mRegister.securityQuestions = securityQuestions;
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(ConsUtils.REGISTRATION, mRegister);
                        overlay(UpdateProfileActivity.class, bundle);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getListQuestion() {
        AccountHelper.SECURITY_QUESTIONS questions = new AccountHelper.SECURITY_QUESTIONS();
        questions.language = "en";
        OkUtil.publicPost(Url.accounts_helper, new Gson().toJson(questions), new OkUtil.Callback() {
            @Override
            public void success(Response<String> response) {
                AccountHelper.SECURITY_QUESTIONS.Response json = new Gson().fromJson(response.body(), AccountHelper.SECURITY_QUESTIONS.Response.class);
                mData = json.data;
                ArrayList<String> stringQuestions = new ArrayList<>();
                for (AccountHelper.SECURITY_QUESTIONS.Response.Data aa : mData
                        ) {
                    stringQuestions.add(aa.question);
                }

                questionsAdapter = new ArrayAdapter<String>(RegistrationSecurityQuestionActivity.this, R.layout.registration_activity_security_question_item_textview, stringQuestions);
                securityQuestionLayout.removeAllViews();
                for (int i = 0; i < 3; i++) {
                    View questionLayoutView = LayoutInflater.from(RegistrationSecurityQuestionActivity.this).inflate(R.layout.registration_activity_security_question_item, null);
                    securityQuestionLayout.addView(questionLayoutView);
                    TextView txtQuestionTitle = (TextView) questionLayoutView.findViewById(R.id.txtQuestionTitle);
                    txtQuestionTitle.setText(String.format(getString(R.string.question), (i + 1)));
                    Spinner questionSpinner = (Spinner) questionLayoutView.findViewById(R.id.spinnerQuestion);
                    spinnersQuestion.add(questionSpinner);
                    questionSpinner.setAdapter(questionsAdapter);
                    questionSpinner.setSelection(i);
                    EditText editTextView = (EditText) questionLayoutView.findViewById(R.id.edQuestionAnswer);
                    editTextView.setImeOptions(EditorInfo.IME_ACTION_DONE);
                    editTextView.setSingleLine();
                    appEditTextViewsAnswers.add(editTextView);
                }
                btnQuestionComplete.setEnabled(true);
            }
        });
    }


}
