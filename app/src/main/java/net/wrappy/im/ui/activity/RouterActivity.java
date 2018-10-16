package net.wrappy.im.ui.activity;


import android.content.Intent;
import android.net.Uri;

import net.wrappy.im.BaseActivity;
import net.wrappy.im.LauncherActivity;
import net.wrappy.im.contants.ConsUtils;
import net.wrappy.im.ui.view.Layout;
import net.wrappy.im.R;
import net.wrappy.im.util.ManagementAllActivity;
import net.wrappy.im.util.SpUtil;

@Layout(layoutId = R.layout.activity_router)
public class RouterActivity extends LauncherActivity {
    @Override
    protected void init() {
        Intent intent = getIntent();
        String action = intent.getAction();
        if (Intent.ACTION_VIEW.equals(action)) {
            Uri uri = intent.getData();
            if (uri != null) {

            }
        }
        //如果用户没有点击退出
        if (SpUtil.getSave(ConsUtils.WRAPPY_EXIT)){
            startAndClearAll(MainActivity.class);
        }else {
        forward(WelcomActivity.class);
        }
    }
}
