package net.wrappy.im.ui.activity;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import net.wrappy.im.LauncherActivity;
import net.wrappy.im.contants.ConsUtils;
import net.wrappy.im.ui.view.Layout;
import net.wrappy.im.R;
import net.wrappy.im.util.SpUtil;

//@Layout(layoutId = R.layout.activity_router)//LauncherActivity
//public class RouterActivity extends LauncherActivity {
public class RouterActivity extends Activity {
    private static final String TAG = "RouterActivity";
//    @Override
//    protected void init() {
//        //如果用户没有点击退出
//        if (SpUtil.getSave(ConsUtils.WRAPPY_EXIT)){
//            startAndClearAll(MainActivity.class);
//            finish();
//        }else {
//            forward(WelcomActivity.class);
//          }
//    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String action = intent.getAction();
        if (Intent.ACTION_VIEW.equals(action)) {
            Uri uri = intent.getData();
            if (uri != null) {
                String secretKey = uri.getQueryParameter("secretKey");
                Log.e(TAG, "init: " + secretKey);
                Bundle bundle = new Bundle();
                bundle.putString(ConsUtils.INTENT, ConsUtils.INTENT_REGISTER);
                bundle.putString(ConsUtils.WRAPPY_MODIFY_PASSWORD,ConsUtils.WRAPPY_MODIFY_PASSWORD);
                bundle.putString(ConsUtils.WRAPPY_SECRETKEY,secretKey);
                overlayForResult(PatternActivity.class, 100, bundle);
                finish();
            }
        }else {
        if (SpUtil.getSave(ConsUtils.WRAPPY_EXIT)) {
            startActivity(new Intent(RouterActivity.this, MainActivity.class));
            finish();
        }else {
           //如果用户没有点击退出
            startActivity(new Intent(RouterActivity.this, WelcomActivity.class));
            finish();
        }
        }
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
        intent.setClass(this, classObj);
        intent.putExtras(params);
        startActivityForResult(intent, requestCode);
    }
}
