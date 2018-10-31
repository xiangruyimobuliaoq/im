package net.wrappy.im.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.model.Response;
import com.yalantis.ucrop.UCrop;

import net.wrappy.im.BaseActivity;
import net.wrappy.im.BaseFragment;
import net.wrappy.im.R;
import net.wrappy.im.contants.ConsUtils;
import net.wrappy.im.contants.Url;
import net.wrappy.im.model.AccountHelper;
import net.wrappy.im.model.Auth;
import net.wrappy.im.model.BottomSheetCell;
import net.wrappy.im.model.BottomSheetListener;
import net.wrappy.im.model.ModificationInfo;
import net.wrappy.im.model.Result;
import net.wrappy.im.model.SaveUserFile;
import net.wrappy.im.ui.activity.LoginActivity;
import net.wrappy.im.ui.activity.MainActivity;
import net.wrappy.im.ui.activity.ModifierSecurityQuestionActivity;
import net.wrappy.im.ui.activity.ModifyEmailActivity;
import net.wrappy.im.ui.activity.ModifyPhoneActivity;
import net.wrappy.im.ui.activity.PatternActivity;
import net.wrappy.im.ui.view.Layout;
import net.wrappy.im.util.AppFuncs;
import net.wrappy.im.util.ManagementAllActivity;
import net.wrappy.im.util.NotificationCenter;
import net.wrappy.im.util.OkUtil;
import net.wrappy.im.util.PopupUtils;
import net.wrappy.im.util.SpUtil;
import net.wrappy.im.util.UIUtil;
import net.wrappy.im.util.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建者     彭龙
 * 创建时间   2018/7/9 下午2:22
 * 描述	      ${TODO}
 * <p>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
@Layout(layoutId = R.layout.fragment_profile)
public class ProfileFragment extends BaseFragment {
    @BindView(R.id.imgProfileHeader)
    ImageView imgProfileHeader;
    @BindView(R.id.imgPhotoAvatar)
    ImageView imgPhotoAvatar;
    @BindView(R.id.txtProfileUsername)
    TextView txtUsername;
    @BindView(R.id.txtProfileNickname)
    TextView txtProfileNickname;
    @BindView(R.id.edProfileFullName)
    EditText edFullName;
    @BindView(R.id.tvProfilePhone)
    TextView tvPhone;
    @BindView(R.id.tvProfileEmail)
    TextView tvEmail;
//    @BindView(R.id.edProfileGender)
//    TextView edGender;
    @BindView(R.id.lnForSeft)
    LinearLayout lnForSeft;
    @BindView(R.id.lnForFriend)
    LinearLayout lnForFriend;
    @BindView(R.id.lnForStranger)
    LinearLayout lnForStranger;
    @BindView(R.id.ll_update_or_cancel_update)
    LinearLayout ll_update_or_cancel_update;
    @BindView(R.id.btnPhotoCameraAvatar)
    ImageButton btnPhotoCameraAvatar;
    @BindView(R.id.btnProfileSubmit)
    Button btnProfileSubmit;
    @BindView(R.id.cancelupdate)
    Button cancelupdate;
    @BindView(R.id.btnProfileCameraHeader)
    ImageButton btnProfileCameraHeader;
    @BindView(R.id.spnProfile)
    Spinner spnProfile;
    @BindView(R.id.lnProfilePhone)
    LinearLayout lnProfilePhone;
    @BindView(R.id.lnProfileEmail)
    LinearLayout lnProfileEmail;
    @BindView(R.id.lnProfileGender)
    LinearLayout lnProfileGender;
    @BindView(R.id.lnProfileUsername)
    LinearLayout lnProfileUsername;
    @BindView(R.id.lnProfileLogout)
    LinearLayout lnProfileLogout;
    @BindView(R.id.txtClientName)
    TextView txtClientName;
    @BindView(R.id.lnProfileChangeQuestion)
    LinearLayout lnProfileChangeQuestion;

    private Auth mAuth;
    private String[] arrDetail;
    private boolean isRequestAvatar;

    public static final int AVATAR = 321;
    public static final int BANNER = AVATAR + 1;
    public static final int CROP_AVATAR = BANNER + 1;
    public static final int CROP_BANNER = CROP_AVATAR + 1;
    private Uri uriAvatar;
    private Uri uriHeader;
    //                头像，    背景
    private String mBitmapAvatar,mBitmapHeader;
    String firstName;
    ArrayAdapter<CharSequence> adapterGender;

    @Override
    protected void init() {
        btnPhotoCameraAvatar.setVisibility(View.VISIBLE);
        btnProfileCameraHeader.setVisibility(View.VISIBLE);
        mAuth = (Auth) SpUtil.readObj(ConsUtils.PROFILE);
        arrDetail = getResources().getStringArray(R.array.profile_gender_display);
        try {
            edFullName.setText(mAuth.account.firstName);
            txtUsername.setText(mAuth.account.extendedInfo.username);
            tvPhone.setText(mAuth.account.mobilePhone);
            tvEmail.setText(mAuth.account.email);
            tvEmail.setEnabled(false);
        } catch (Exception e) {
        }
        adapterGender = ArrayAdapter.createFromResource(mContext,
                R.array.profile_gender, R.layout.update_profile_textview);
        spnProfile.setAdapter(adapterGender);
        String gender = mAuth.account.gender;
        if (TextUtils.isEmpty(gender) || "FEMALE".equals(gender)){
            spnProfile.setSelection(0);
        }else {
            spnProfile.setSelection(1);
        }

//        avatar, background_image
        OkUtil.privateGet((BaseActivity) getActivity(), Url.accounts_userFile + "/avatar", new OkUtil.Callback() {
            @Override
            public void success(Response<String> response) {
                Log.e("123", response.body());
                Result<String> json = new Gson().fromJson(response.body(), new TypeToken<Result<String>>() {
                }.getType());
                if (null != json.data){
                    imgPhotoAvatar.setImageBitmap(UIUtil.base64ToBitmap(json.data));
                    mBitmapAvatar = json.data;
                }
            }
        });
        OkUtil.privateGet((BaseActivity) getActivity(), Url.accounts_userFile + "/background_image", new OkUtil.Callback() {
            @Override
            public void success(Response<String> response) {
                Log.e("123", response.body());
                Result<String> json = new Gson().fromJson(response.body(), new TypeToken<Result<String>>() {
                }.getType());
                if (null != json.data){
                    imgProfileHeader.setImageBitmap(UIUtil.base64ToBitmap(json.data));
                    mBitmapHeader = json.data;
                }
            }
        });
        //更新用户信息
        btnProfileSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firstName = edFullName.getText().toString().trim();
                if (TextUtils.isEmpty(firstName)){
                    showOKDialog("cannot be empty");
                    return;
                }
                if (mBitmapAvatar == null){
                    showOKDialog("Please upload your avatar");
                    return;
                }

                Bundle bundle = new Bundle();
                bundle.putString(ConsUtils.INTENT, ConsUtils.INTENT_CHECK);
//                Register register = new Register();
                overlayForResult(PatternActivity.class, 100, bundle);
            }
        });
        //取消更新按钮
        cancelupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edFullName.setText(mAuth.account.firstName);
                txtUsername.setText(mAuth.account.extendedInfo.username);
                tvPhone.setText(mAuth.account.mobilePhone);
                tvEmail.setText(mAuth.account.email);
                tvEmail.setEnabled(false);
                MainActivity.btnHeaderEdit.setVisibility(View.VISIBLE);
                onDataEditChange(false);
            }
        });
        //修改密码提示问题
        lnProfileChangeQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Bundle bundle = new Bundle();
                bundle.putString(ConsUtils.INTENT, ConsUtils.INTENT_CHECK);
//                Register register = new Register();
                overlayForResult(PatternActivity.class, 99, bundle);

            }
        });
        //退出APP
        lnProfileLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupUtils.showCustomDialog(mContext, "", "Logout without saved data ?", R.string.ok, R.string.cancel, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (v.getId() == R.id.btnOk){
                            //把之前保存的数据清除掉
//                            ConsUtils.putAdminAccestoken("");
//                            ConsUtils.putAdminRefreshtoken("");
//                            ConsUtils.putAdminTokenType("");
//                            OkUtil.refresh("", "");
                            ConsUtils.putAdminAccestoken(null);
                            SpUtil.spSave(ConsUtils.WRAPPY_EXIT,false);
                            ManagementAllActivity.finishAllActivity();
                            toActivity(LoginActivity.class);

                        }

                    }
                }, null);
            }
        });
        //修改电话号码
        tvPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString(ConsUtils.INTENT, ConsUtils.INTENT_CHECK);
                overlayForResult(ModifyPhoneActivity.class, 98, bundle);

            }
        });
        //修改邮箱
        tvEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString(ConsUtils.INTENT, ConsUtils.INTENT_CHECK);
                overlayForResult(ModifyEmailActivity.class, 97, bundle);

            }
        });

        onDataEditChange(false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            String pattern;
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
              pattern = data.getStringExtra("pattern");
            verifyPattenrnPassword(pattern,"fff");
        }else if (requestCode == 99 && resultCode == Activity.RESULT_OK){
            pattern = data.getStringExtra("pattern");
            verifyPattenrnPassword(pattern,"");
        }else if (requestCode == 98 && resultCode == Activity.RESULT_OK){
            pattern = data.getStringExtra("pattern");
            tvPhone.setText(pattern);
        }else if (requestCode == 97 && resultCode == Activity.RESULT_OK){
            pattern = data.getStringExtra("pattern");
            tvEmail.setText(pattern);
        }
    }

    private void verifyPattenrnPassword(final String pattern, final String str) {

        AppFuncs.showProgressWaiting(getActivity());
        OkUtil.publicPost( Url.accounts + "/validatePatternPassword/"+ pattern, "",new OkUtil.Callback() {
            @Override
            public void success(Response<String> response) {
                AppFuncs.dismissProgressWaiting();
                Log.e(TAG, "success: " + response.body().toString());
                AccountHelper.Response json = new Gson().fromJson(response.body().toString(), AccountHelper.Response.class);
                if (json.code == 1000){
                    if (TextUtils.isEmpty(str)){
                     AppFuncs.dismissProgressWaiting();
                    Intent intent = new Intent(mContext, ModifierSecurityQuestionActivity.class);
                    intent.putExtra(ConsUtils.USERNAME,mAuth.account.extendedInfo.username);
                    startActivity(intent);
                    }else {
                       updata();
                    }
                }else {
                    PopupUtils.showCustomDialog(mContext,"",json.message, R.string.ok,-1,null,null);
                }

            }

            @Override
            public void error(Response<String> response) {
                super.error(response);
                AppFuncs.dismissProgressWaiting();
                Log.e(TAG, "error: " + response.body());
                showErroe();
            }
        });

    }

    private void updata() {

        ModificationInfo info = new ModificationInfo();
        info.firstName = firstName;
        info.gender = adapterGender.getItem(spnProfile.getSelectedItemPosition()).toString().toUpperCase();
        info.mobilePhone = tvPhone.getText().toString().trim();
        info.email = tvEmail.getText().toString().trim();
        info.extendedInfo.avatar = mBitmapAvatar;
        info.extendedInfo.backgroundImage = mBitmapHeader;

//            mAuth.patternPassword = pattern;//现在不传手势密码
        mAuth.account.firstName = firstName;
        mAuth.account.gender = adapterGender.getItem(spnProfile.getSelectedItemPosition()).toString().toUpperCase();
        mAuth.account.mobilePhone = tvPhone.getText().toString().trim();
        mAuth.account.email = tvEmail.getText().toString().trim();
        mAuth.account.extendedInfo.avatar = mBitmapAvatar;
        mAuth.account.extendedInfo.backgroundImage = mBitmapHeader;
        AppFuncs.showProgressWaiting(getActivity());
        Log.e(TAG, "onActivityResult: " + new Gson().toJson(info));
        OkUtil.privatePut((BaseActivity) getActivity(), Url.accounts, new Gson().toJson(info), new OkUtil.Callback() {
            @Override
            public void success(Response<String> response) {
                AppFuncs.dismissProgressWaiting();
                Log.e("123", response.body());
                AccountHelper.updateUserInformation json = new Gson().fromJson(response.body(), AccountHelper.updateUserInformation.class);
                //修改成功
                if (json.code == 1000){
                    ModifySuccees();
                    mAuth.account.email = json.data.email;
                    mAuth.account.mobilePhone = json.data.mobilePhone;
                    mAuth.account.firstName = json.data.firstName;
                    mAuth.account.gender = json.data.gender;
                    mAuth.account.extendedInfo.avatar = json.data.extendedInfo.avatar;
                    mAuth.account.extendedInfo.backgroundImage = json.data.extendedInfo.backgroundImage;
                    SpUtil.saveObj(ConsUtils.PROFILE, mAuth);
                    //修改失败
                }else {
                    onDataEditChange(true);
                }
//                    toast(json.message);
                showOKDialog(json.message);
            }

            @Override
            public void error(Response<String> response) {
                Log.e("message", response.getRawResponse().message());
                Log.e("body", response.getRawResponse().body().toString());
                onDataEditChange(false);
                AppFuncs.dismissProgressWaiting();
                showErroe();
            }
        });

    }

    /**修改成功*/
    private void ModifySuccees() {
        MainActivity.btnHeaderEdit.setVisibility(View.VISIBLE);
        onDataEditChange(false);
    }

    public void onDataEditChange(boolean isEditting) {
        if (isEditting) {
            tvEmail.setEnabled(true);
            tvPhone.setEnabled(true);
            spnProfile.setEnabled(true);
            edFullName.setEnabled(true);
            edFullName.requestFocus();
            edFullName.setFocusableInTouchMode(true);
            txtUsername.setTextColor(getResources().getColor(R.color.line));
            tvPhone.setTextColor(getResources().getColor(R.color.text_black_color));
            tvEmail.setTextColor(getResources().getColor(R.color.text_black_color));
            ll_update_or_cancel_update.setVisibility(View.VISIBLE);
        } else {
            tvPhone.setEnabled(false);
            tvEmail.setEnabled(false);
            spnProfile.setEnabled(false);
            txtUsername.setEnabled(false);
            tvPhone.setEnabled(false);
            edFullName.setEnabled(false);
            edFullName.setFocusableInTouchMode(false);
            txtUsername.setTextColor(getResources().getColor(R.color.line));
            tvPhone.setTextColor(getResources().getColor(R.color.text_black_color));
            tvEmail.setTextColor(getResources().getColor(R.color.text_black_color));
            ll_update_or_cancel_update.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.btnProfileSubmit, R.id.btnProfileCameraHeader, R.id.btnPhotoCameraAvatar})
    public void onClick(View view) {
        if (view.getId() == R.id.btnProfileSubmit) {
            int[] stringRes = new int[]{R.string.error_empty_nickname, R.string.error_invalid_email};
            EditText v1 = new EditText(mContext);
            v1.setText(tvEmail.getText().toString().trim());
            EditText[] views = new EditText[]{edFullName, v1};

            if (!Utils.checkValidateAppEditTextViews(getActivity(), views, stringRes)) {
                return;
            }
            onDataEditChange(false);
            mAuth.account.email = tvEmail.getText().toString().trim();
            mAuth.account.gender = adapterGender.getItem(spnProfile.getSelectedItemPosition()).toString().toUpperCase();
            mAuth.account.nickName = edFullName.getText().toString();
//            updateData();
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.showEditIconOnMainActivity, "");
        } else if (view.getId() == R.id.btnPhotoCameraAvatar || view.getId() == R.id.btnProfileCameraHeader) {
            ArrayList<BottomSheetCell> sheetCells = new ArrayList<>();
            BottomSheetCell sheetCell = new BottomSheetCell(1, R.mipmap.ic_choose_camera, getString(R.string.popup_take_photo));
            sheetCells.add(sheetCell);
            sheetCell = new BottomSheetCell(2, R.mipmap.ic_choose_gallery, getString(R.string.popup_choose_gallery));
            sheetCells.add(sheetCell);
            PopupUtils.createBottomSheet(getActivity(), sheetCells, new BottomSheetListener() {
                @Override
                public void onSelectBottomSheetCell(int index) {
                    switch (index) {
                        case 1:
                            AppFuncs.openCameraFrag(ProfileFragment.this, isRequestAvatar);
                            break;
                        case 2:
                            AppFuncs.openGalleryFrag(ProfileFragment.this, isRequestAvatar);
                            break;
                    }
                }
            }).show();
            if (view.getId() == R.id.btnPhotoCameraAvatar) {
                isRequestAvatar = true;
            } else {
                isRequestAvatar = false;
            }
        }
    }


    @Override
    public void onResultPickerImage(boolean isAvatar, Intent data, boolean isSuccess) {
        if (isAvatar) {
            uriAvatar = UCrop.getOutput(data);
            if (uriAvatar != null) {
                try {
                    String base64 = UIUtil.bitmapToBase64(MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uriAvatar));
                    imgPhotoAvatar.setImageURI(uriAvatar);
                    mBitmapAvatar = base64;
//                    mAuth.account.extendedInfo.avatar = base64;
                    SaveUserFile file = new SaveUserFile();
                    file.file = base64;
                    file.type = "avatar";
                    OkUtil.privatePut((BaseActivity) getActivity(), Url.accounts_userFile, new Gson().toJson(file), new OkUtil.Callback() {
                        @Override
                        public void success(Response<String> response) {
                            AccountHelper.Response json = new Gson().fromJson(response.body(), AccountHelper.Response.class);
//                            toast(json.message);
                            showOKDialog(json.message);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            uriHeader = UCrop.getOutput(data);
            if (uriHeader != null) {
                try {
                    String base64 = UIUtil.bitmapToBase64(MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uriHeader));
                    imgProfileHeader.setImageURI(uriHeader);
                    mBitmapHeader = base64;
//                    mAuth.account.extendedInfo.backGroundImage = base64;
                    SaveUserFile file = new SaveUserFile();
                    file.file = base64;
                    file.type = "background_image";
                    OkUtil.privatePut((BaseActivity) getActivity(), Url.accounts_userFile, new Gson().toJson(file), new OkUtil.Callback() {
                        @Override
                        public void success(Response<String> response) {
                            AccountHelper.Response json = new Gson().fromJson(response.body(), AccountHelper.Response.class);
//                            toast(json.message);
                            showOKDialog(json.message);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void reloadFragment() {

    }
}
