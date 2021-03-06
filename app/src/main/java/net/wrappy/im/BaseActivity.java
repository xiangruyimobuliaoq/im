package net.wrappy.im;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.yalantis.ucrop.UCrop;

import net.wrappy.im.ui.view.Layout;
import net.wrappy.im.util.AppFuncs;
import net.wrappy.im.util.ToastHelper;

import butterknife.ButterKnife;

/**
 * 创建者     彭龙
 * 创建时间   2018/6/14 下午3:36
 * 描述	      ${TODO}
 * <p>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public abstract class BaseActivity extends AppCompatActivity {
    public static final int REQUEST_PERMISSION_CAMERA_AVATAR = 501;
    public static final int REQUEST_PERMISSION_CAMERA_BANNER = 502;
    public static final int RESULT_AVATAR = 503;
    public static final int RESULT_BANNER = 504;
    public static final int AVATAR = 505;
    public static final int BANNER = 506;
    public static final int REQUEST_PERMISSION_PICKER_AVATAR = 507;
    public static final int REQUEST_PERMISSION_PICKER_BANNER = 508;
    private Toast mToast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        Layout layout = getClass().getAnnotation(Layout.class);
        if (layout.layoutId() > 0) {
            setContentView(layout.layoutId());
        }
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        init();
    }

    protected abstract void init();

    public void toast(String msg) {
        ToastHelper.showToast(msg, this);
    }

    protected void toast(int msg_id) {
        ToastHelper.showToast(getString(msg_id), this);
    }

    /**
     * @param content
     */
    protected void showSnackBar(String content) {
        ViewGroup view = (ViewGroup) getWindow().getDecorView();
        Snackbar.make(view.getChildAt(0), content, Snackbar.LENGTH_LONG)
                .setAction("关闭", null).show();
    }

    /**
     * 转跳 没有finish 没有切换效果
     *
     * @param classObj
     */
    public void toActivity(Class<?> classObj) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setClass(this, classObj);
        startActivity(intent);
    }

    /**
     * 转跳 没有finish
     *
     * @param classObj
     */
    public void overlay(Class<?> classObj) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setClass(this, classObj);
        startActivity(intent);
    }

    /**
     * 转跳 没有finish
     *
     * @param classObj
     * @param params
     */
    public void overlay(Class<?> classObj, Bundle params) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setClass(this, classObj);
        intent.putExtras(params);
        startActivity(intent);
    }

    /**
     * 转跳 没有finish
     *
     * @param classObj
     * @param requestCode
     * @param params
     */
    public void overlayLeftForResult(Class<?> classObj, int requestCode, Bundle params) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setClass(this, classObj);
        intent.putExtras(params);
        startActivityForResult(intent, requestCode);
    }

    /**
     * 转跳 没有finish
     *
     * @param classObj
     * @param requestCode
     */
    public void overlayForResult(Class<?> classObj, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setClass(this, classObj);
        startActivityForResult(intent, requestCode);
    }
    // //////////////////////////////////////////////////////////////////////////////////////////////
    // logic method

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

    /**
     * 转跳 有finish
     *
     * @param classObj
     */
    public void forward(Class<?> classObj) {
        Intent intent = new Intent();
        intent.setClass(this, classObj);
        startActivity(intent);
        finish();
    }

    public void startAndClearAll(Class<?> classObj) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setClass(this, classObj);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case UCrop.RESULT_ERROR:
                    final Throwable cropError = UCrop.getError(data);
                    AppFuncs.log(cropError.getLocalizedMessage());
                    onResultPickerImage(false, data, false);
                    break;
                case RESULT_AVATAR:
                    AppFuncs.cropImage(this, data, true, AVATAR);
                    break;
                case RESULT_BANNER:
                    AppFuncs.cropImage(this, data, false, BANNER);
                    break;
                case AVATAR:
                    onResultPickerImage(true, data, true);
                    break;
                case BANNER:
                    onResultPickerImage(false, data, true);
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_CAMERA_AVATAR:
                AppFuncs.openCamera(this, true);
                break;
            case REQUEST_PERMISSION_CAMERA_BANNER:
                AppFuncs.openCamera(this, false);
                break;
            case REQUEST_PERMISSION_PICKER_AVATAR:
                AppFuncs.openGallery(this, true);
                break;
            case REQUEST_PERMISSION_PICKER_BANNER:
                AppFuncs.openGallery(this, false);
                break;
        }
    }

    public void onResultPickerImage(boolean isAvatar, Intent data, boolean isSuccess) {
    }
    public void showHidePassword(final EditText editText, final boolean isLogin) {
        editText.setOnTouchListener(new View.OnTouchListener() {
            boolean isVisible = false;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {

                    if (motionEvent.getRawX() >= (editText.getWidth() - editText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width() - editText.getPaddingRight())) {

                        if (!isVisible) {
                            editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            editText.setSelection(editText.length());

                            if (isLogin) {
                                editText.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_lock_1, 0, R.mipmap.ic_show, 0);
                            } else {
                                editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_show, 0);
                            }

                            isVisible = true;
                        } else {
                            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            editText.setSelection(editText.length());

                            if (isLogin) {
                                editText.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_lock_1, 0, R.mipmap.ic_hidden, 0);
                            } else {
                                editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_hidden, 0);
                            }


                            isVisible = false;
                        }
                    }
                }
                return false;
            }
        });
    }

}
