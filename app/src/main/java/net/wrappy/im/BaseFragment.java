package net.wrappy.im;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;


import com.yalantis.ucrop.UCrop;

import net.wrappy.im.util.AppFuncs;
import net.wrappy.im.util.PopupUtils;
import net.wrappy.im.util.ToastHelper;


public abstract class BaseFragment extends BaseLazyFragment {
    public static final int REQUEST_PERMISSION_CAMERA_AVATAR = 501;
    public static final int REQUEST_PERMISSION_CAMERA_BANNER = 502;
    public static final int RESULT_AVATAR = 503;
    public static final int RESULT_BANNER = 504;
    public static final int AVATAR = 505;
    public static final int BANNER = 506;
    public static final int REQUEST_PERMISSION_PICKER_AVATAR = 507;
    public static final int REQUEST_PERMISSION_PICKER_BANNER = 508;
    /**
     * 转跳 没有finish 没有切换效果
     *
     * @param classObj
     */
    public void toActivity(Class<?> classObj) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setClass(mActivity, classObj);
        startActivity(intent);
    }
    public abstract void reloadFragment();

    /**
     * 转跳 没有finish
     *
     * @param classObj
     */
    public void overlay(Class<?> classObj) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setClass(mActivity, classObj);
        startActivity(intent);
//        mActivity.overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.slide_out_to_top);
    }

    /**
     * 转跳 没有finish
     *
     * @param classObj
     * @param params
     */
    public void overlay(Class<?> classObj, Bundle params) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setClass(mActivity, classObj);
        intent.putExtras(params);
        startActivity(intent);
        mActivity.overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
    }

    /**
     * 转跳 没有finish
     *
     * @param classObj
     * @param requestCode
     */
    public void overlayForResult(Class<?> classObj, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setClass(mActivity, classObj);
        startActivityForResult(intent, requestCode);
        mActivity.overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
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
        intent.setClass(mActivity, classObj);
        intent.putExtras(params);
        startActivityForResult(intent, requestCode);
        mActivity.overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
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
        intent.setClass(mActivity, classObj);
        intent.putExtras(params);
        startActivityForResult(intent, requestCode);
        mActivity.overridePendingTransition(R.anim.push_left_in, R.anim.push_right_out);
    }

    /**
     * 转跳 有finish
     *
     * @param classObj
     */
    public void forward(Class<?> classObj) {
        Intent intent = new Intent();
        intent.setClass(mActivity, classObj);
        this.startActivity(intent);
        mActivity.overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
        mActivity.finish();
    }
    // //////////////////////////////////////////////////////////////////////////////////////////////
    // debug method

    /**
     * 转跳 有finish
     *
     * @param classObj
     * @param params
     */
    public void forward(Class<?> classObj, Bundle params) {
        Intent intent = new Intent();
        intent.setClass(mActivity, classObj);
        intent.putExtras(params);
        this.startActivity(intent);
        mActivity.overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
        mActivity.finish();
    }

    public final String TAG = this.getClass().getSimpleName();

    public void showOKDialog(String msg){
        PopupUtils.showCustomDialog(mContext,"",msg,R.string.ok,-1,null,null);
    }

    public void toast(String msg) {
        ToastHelper.showToast(msg, getActivity());
    }

    protected void toast(int msg_id) {
        ToastHelper.showToast(getString(msg_id), getActivity());
    }

    /**
     * 查找view
     */
    protected <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case UCrop.RESULT_ERROR:
                    final Throwable cropError = UCrop.getError(data);
                    AppFuncs.log(cropError.getLocalizedMessage());
                    onResultPickerImage(false, data, false);
                    break;
                case RESULT_AVATAR:
                    AppFuncs.cropImage(getActivity(), data, true, AVATAR);
                    break;
                case RESULT_BANNER:
                    AppFuncs.cropImage(getActivity(), data, false, BANNER);
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
                AppFuncs.openCamera(getActivity(), true);
                break;
            case REQUEST_PERMISSION_CAMERA_BANNER:
                AppFuncs.openCamera(getActivity(), false);
                break;
            case REQUEST_PERMISSION_PICKER_AVATAR:
                AppFuncs.openGallery(getActivity(), true);
                break;
            case REQUEST_PERMISSION_PICKER_BANNER:
                AppFuncs.openGallery(getActivity(), false);
                break;
        }
    }

    public void onResultPickerImage(boolean isAvatar, Intent data, boolean isSuccess) {
    }
}
