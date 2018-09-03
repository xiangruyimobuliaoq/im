package net.wrappy.im.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import net.wrappy.im.model.Register;
import net.wrappy.im.model.Result;
import net.wrappy.im.model.SaveUserFile;
import net.wrappy.im.ui.activity.PatternActivity;
import net.wrappy.im.ui.view.Layout;
import net.wrappy.im.util.AppFuncs;
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
    @BindView(R.id.edProfilePhone)
    EditText edPhone;
    @BindView(R.id.edProfileEmail)
    EditText edEmail;
    @BindView(R.id.edProfileGender)
    TextView edGender;
    @BindView(R.id.lnForSeft)
    LinearLayout lnForSeft;
    @BindView(R.id.lnForFriend)
    LinearLayout lnForFriend;
    @BindView(R.id.lnForStranger)
    LinearLayout lnForStranger;
    @BindView(R.id.btnPhotoCameraAvatar)
    ImageButton btnPhotoCameraAvatar;
    @BindView(R.id.btnProfileSubmit)
    Button btnProfileSubmit;
    @BindView(R.id.btnProfileCameraHeader)
    ImageButton btnProfileCameraHeader;
    @BindView(R.id.spnProfile)
    AppCompatSpinner spnProfile;
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

    private Auth mAuth;
    private String[] arrDetail;
    private boolean isRequestAvatar;

    public static final int AVATAR = 321;
    public static final int BANNER = AVATAR + 1;
    public static final int CROP_AVATAR = BANNER + 1;
    public static final int CROP_BANNER = CROP_AVATAR + 1;
    private Uri uriAvatar;
    private Uri uriHeader;

    @Override
    protected void init() {
        mAuth = (Auth) SpUtil.readObj(ConsUtils.PROFILE);
        arrDetail = getResources().getStringArray(R.array.profile_gender_display);
        try {
            edFullName.setText(mAuth.account.nickName);
            txtUsername.setText(mAuth.account.extendedInfo.username);
            edPhone.setText(mAuth.account.mobilePhone);
            edEmail.setText(mAuth.account.email);
        } catch (Exception e) {
        }
//        avatar, background_image
        OkUtil.privateGet((BaseActivity) getActivity(), Url.accounts_userFile + "/avatar", new OkUtil.Callback() {
            @Override
            public void success(Response<String> response) {
                Log.e("123", response.body());
                Result<String> json = new Gson().fromJson(response.body(), new TypeToken<Result<String>>() {
                }.getType());
                if (null != json.data)
                    imgPhotoAvatar.setImageBitmap(UIUtil.base64ToBitmap(json.data));
            }
        });
        OkUtil.privateGet((BaseActivity) getActivity(), Url.accounts_userFile + "/background_image", new OkUtil.Callback() {
            @Override
            public void success(Response<String> response) {
                Log.e("123", response.body());
                Result<String> json = new Gson().fromJson(response.body(), new TypeToken<Result<String>>() {
                }.getType());
                if (null != json.data)
                    imgProfileHeader.setImageBitmap(UIUtil.base64ToBitmap(json.data));
            }
        });
        btnProfileSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(ConsUtils.INTENT, ConsUtils.INTENT_CHECK);
                Register register = new Register();
                overlayForResult(PatternActivity.class, 100, bundle);
            }
        });
        lnProfileLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            String pattern = data.getStringExtra("pattern");
            Auth auth = new Auth();
            auth.account.nickName = edFullName.getText().toString().trim();
            auth.patternPassword = pattern;
            OkUtil.privatePut((BaseActivity) getActivity(), Url.accounts, new Gson().toJson(auth), new OkUtil.Callback() {
                @Override
                public void success(Response<String> response) {
                    Log.e("123", response.body());
                    AccountHelper.Response json = new Gson().fromJson(response.body(), AccountHelper.Response.class);
                    toast(json.message);
                    onDataEditChange(false);
                }

                @Override
                public void error(Response<String> response) {
                    Log.e("message", response.getRawResponse().message());
                    Log.e("body", response.getRawResponse().body().toString());
//                        AccountHelper.Response json = new Gson().fromJson(response.getRawResponse().body().toString(), AccountHelper.Response.class);
//                        toast(json.message);
                    onDataEditChange(false);
                }
            });
        }
    }

    public void onDataEditChange(boolean isEditting) {
        if (isEditting) {
//            edEmail.setEnabled(true);
            edFullName.setEnabled(true);
            edFullName.requestFocus();
            edFullName.setFocusableInTouchMode(true);
            edPhone.setTextColor(getResources().getColor(R.color.line));
            txtUsername.setTextColor(getResources().getColor(R.color.line));
            btnProfileSubmit.setVisibility(View.VISIBLE);
            spnProfile.setVisibility(View.VISIBLE);
//            edGender.setVisibility(View.INVISIBLE);
            btnPhotoCameraAvatar.setVisibility(View.VISIBLE);
            btnProfileCameraHeader.setVisibility(View.VISIBLE);
        } else {
            btnProfileSubmit.setVisibility(View.GONE);
//            edEmail.clearFocus();
//            edGender.clearFocus();
            edEmail.setEnabled(false);
            edFullName.clearFocus();
            edFullName.setEnabled(false);
            edPhone.setTextColor(Color.BLACK);
            txtUsername.setTextColor(Color.BLACK);
            btnProfileSubmit.setVisibility(View.GONE);
            spnProfile.setVisibility(View.GONE);
            edGender.setVisibility(View.VISIBLE);
            btnPhotoCameraAvatar.setVisibility(View.GONE);
            btnProfileCameraHeader.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.btnProfileSubmit, R.id.btnProfileCameraHeader, R.id.btnPhotoCameraAvatar})
    public void onClick(View view) {
        if (view.getId() == R.id.btnProfileSubmit) {
            int[] stringRes = new int[]{R.string.error_empty_nickname, R.string.error_invalid_email};
            EditText[] views = new EditText[]{edFullName, edEmail};
            if (!Utils.checkValidateAppEditTextViews(getActivity(), views, stringRes)) {
                return;
            }
            onDataEditChange(false);
            mAuth.account.email = edEmail.getText().toString();
            mAuth.account.gender = getGender();
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

    private String getGender() {
        String text = edGender.getText().toString().toUpperCase();
        if (!TextUtils.isEmpty(text) && text.equalsIgnoreCase(arrDetail[0]))
            return getResources().getStringArray(R.array.profile_gender)[0];
        else
            return getResources().getStringArray(R.array.profile_gender)[1];
    }

    @Override
    public void onResultPickerImage(boolean isAvatar, Intent data, boolean isSuccess) {
        if (isAvatar) {
            uriAvatar = UCrop.getOutput(data);
            if (uriAvatar != null) {
                try {
                    String base64 = UIUtil.bitmapToBase64(MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uriAvatar));
                    imgPhotoAvatar.setImageURI(uriAvatar);
//                    mAuth.account.extendedInfo.avatar = base64;
                    SaveUserFile file = new SaveUserFile();
                    file.file = base64;
                    file.type = "avatar";
                    OkUtil.privatePut((BaseActivity) getActivity(), Url.accounts_userFile, new Gson().toJson(file), new OkUtil.Callback() {
                        @Override
                        public void success(Response<String> response) {
                            AccountHelper.Response json = new Gson().fromJson(response.body(), AccountHelper.Response.class);
                            toast(json.message);
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
//                    mAuth.account.extendedInfo.backGroundImage = base64;
                    SaveUserFile file = new SaveUserFile();
                    file.file = base64;
                    file.type = "background_image";
                    OkUtil.privatePut((BaseActivity) getActivity(), Url.accounts_userFile, new Gson().toJson(file), new OkUtil.Callback() {
                        @Override
                        public void success(Response<String> response) {
                            AccountHelper.Response json = new Gson().fromJson(response.body(), AccountHelper.Response.class);
                            toast(json.message);
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
