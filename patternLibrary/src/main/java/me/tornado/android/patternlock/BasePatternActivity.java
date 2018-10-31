/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.tornado.android.patternlock;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BasePatternActivity extends AppCompatActivity {

    private static final int CLEAR_PATTERN_DELAY_MILLI = 2000;
    public Context mContext;
    protected TextView mMessageText;
    protected TextView title;
    protected ImageView back;
    protected PatternView mPatternView;
    protected LinearLayout mButtonContainer;
    protected Button mLeftButton;
    protected Button mRightButton;
    protected TextView bottomText;
    protected View view;
    protected TextView mMessageTextError;
//    public TextView mCountdownText;

    private final Runnable clearPatternRunnable = new Runnable() {
        public void run() {
            // clearPattern() resets display mode to DisplayMode.Correct.
            mPatternView.clearPattern();
            mMessageTextError.setText("");
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pl_base_pattern_activity);
        mContext = this;
        back = (ImageView) findViewById(R.id.back);
        title = (TextView) findViewById(R.id.title);
        mMessageText = (TextView) findViewById(R.id.pl_message_text);
//        mMessageTextError = (TextView) findViewById(R.id.pl_message_text_error);
        mPatternView = (PatternView) findViewById(R.id.pl_pattern);
        mButtonContainer = (LinearLayout) findViewById(R.id.pl_button_container);
        mLeftButton = (Button) findViewById(R.id.pl_left_button);
        mRightButton = (Button) findViewById(R.id.pl_right_button);
        bottomText = (TextView) findViewById(R.id.bottomText);
        view = (View) findViewById(R.id.viewspan);
        mMessageTextError = (TextView) findViewById(R.id.mMessageTextError);
//        mMessageTextError = (TextView) findViewById(R.id.pl_message_text_error);
    }

    protected void removeClearPatternRunnable() {
        mPatternView.removeCallbacks(clearPatternRunnable);
    }

    protected void postClearPatternRunnable() {
        removeClearPatternRunnable();
        mPatternView.postDelayed(clearPatternRunnable, CLEAR_PATTERN_DELAY_MILLI);
    }
}
