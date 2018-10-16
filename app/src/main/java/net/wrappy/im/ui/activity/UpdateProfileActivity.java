package net.wrappy.im.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.model.Response;
import com.yalantis.ucrop.UCrop;

import net.wrappy.im.BaseActivity;
import net.wrappy.im.BuildConfig;
import net.wrappy.im.R;
import net.wrappy.im.contants.ConsUtils;
import net.wrappy.im.contants.Url;
import net.wrappy.im.model.AccountHelper;
import net.wrappy.im.model.BottomSheetCell;
import net.wrappy.im.model.BottomSheetListener;
import net.wrappy.im.model.ModificationInfo;
import net.wrappy.im.model.Register;
import net.wrappy.im.ui.view.Layout;
import net.wrappy.im.util.AppFuncs;
import net.wrappy.im.util.ManagementAllActivity;
import net.wrappy.im.util.OkUtil;
import net.wrappy.im.util.PopupUtils;
import net.wrappy.im.util.UIUtil;
import net.wrappy.im.util.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 创建者     彭龙
 * 创建时间   2018/6/21 下午3:21
 * 描述	      ${TODO}
 * <p>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
@Layout(layoutId = R.layout.activity_updateprofile)
public class UpdateProfileActivity extends BaseActivity {

    private static final String TAG = "UpdateProfileActivity";
    @BindView(R.id.back)
    protected ImageView back;
    @BindView(R.id.title)
    protected TextView title;
    @BindView(R.id.btnProfileComplete)
    protected Button btnProfileComplete;
    @BindView(R.id.edProfileNickname)
    protected EditText edProfileNickname;
    @BindView(R.id.edProfilePhone)
    protected TextView edProfilePhone;
    @BindView(R.id.edProfileEmail)
    protected EditText edProfileEmail;
    @BindView(R.id.spinnerProfileGender)
    protected Spinner spinnerProfileGender;

    @BindView(R.id.imgPhotoAvatar)
    CircleImageView imgAvatar;//头像
    @BindView(R.id.imgProfileHeader)
    ImageView imgHeader;
    @BindView(R.id.btnProfileCameraHeader)
    ImageButton btnCameraHeader;
    @BindView(R.id.btnPhotoCameraAvatar)
    ImageButton btnCameraAvatar;

    private Register mRegister;
    private ArrayAdapter<CharSequence> adapterGender;
    private String mNickname;
    private String email;
    Uri uriHeader, uriAvatar;

    @Override
    protected void init() {
        title.setText(getResources().getString(R.string.Profile));
        btnCameraAvatar.setVisibility(View.VISIBLE);
        btnCameraHeader.setVisibility(View.VISIBLE);
        mRegister = (Register) getIntent().getSerializableExtra(ConsUtils.REGISTRATION);
        edProfilePhone.setText(mRegister.mobilePhone);
        adapterGender = ArrayAdapter.createFromResource(this,
                R.array.profile_gender, R.layout.update_profile_textview);
        spinnerProfileGender.setAdapter(adapterGender);
        btnProfileComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }
        });
        btnCameraHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<BottomSheetCell> sheetCells = new ArrayList<>();
                BottomSheetCell sheetCell = new BottomSheetCell(1, R.mipmap.ic_choose_camera, getString(R.string.popup_take_photo));
                sheetCells.add(sheetCell);
                sheetCell = new BottomSheetCell(2, R.mipmap.ic_choose_gallery, getString(R.string.popup_choose_gallery));
                sheetCells.add(sheetCell);
                if (uriHeader != null) {
                    sheetCell = new BottomSheetCell(3, R.mipmap.setting_delete, getString(R.string.popup_delete_photo));
                    sheetCells.add(sheetCell);
                }
                PopupUtils.createBottomSheet(UpdateProfileActivity.this, sheetCells, new BottomSheetListener() {
                    @Override
                    public void onSelectBottomSheetCell(int index) {
                        switch (index) {
                            case 1:
                                AppFuncs.openCamera(UpdateProfileActivity.this, false);
                                break;
                            case 2:
                                AppFuncs.openGallery(UpdateProfileActivity.this, false);
                                break;
                            case 3:
                                uriHeader = null;
                                imgHeader.setImageBitmap(null);
                                break;
                            default:
                        }
                    }
                }).show();
            }
        });
        btnCameraAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<BottomSheetCell> sheetCells = new ArrayList<>();
                BottomSheetCell sheetCell = new BottomSheetCell(1, R.mipmap.ic_choose_camera, getString(R.string.popup_take_photo));
                sheetCells.add(sheetCell);
                sheetCell = new BottomSheetCell(2, R.mipmap.ic_choose_gallery, getString(R.string.popup_choose_gallery));
                sheetCells.add(sheetCell);
//                if (uriAvatar != null) {
//                    sheetCell = new BottomSheetCell(3, R.mipmap.setting_delete, getString(R.string.popup_delete_photo));
//                    sheetCells.add(sheetCell);
//                }
                PopupUtils.createBottomSheet(UpdateProfileActivity.this, sheetCells, new BottomSheetListener() {
                    @Override
                    public void onSelectBottomSheetCell(int index) {
                        switch (index) {
                            case 1:
                                AppFuncs.openCamera(UpdateProfileActivity.this, true);
                                break;
                            case 2:
                                AppFuncs.openGallery(UpdateProfileActivity.this, true);
                                break;
                            case 3:
                                uriAvatar = null;
                                imgAvatar.setImageResource(R.mipmap.avatar);
                                break;
                            default:
                        }
                    }
                }).show();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void check() {
        mNickname = edProfileNickname.getText().toString().trim();
        email = edProfileEmail.getText().toString().trim();
        if (TextUtils.isEmpty(mNickname)) {
            showOKDialog("Nickname  can not be empty");
//            toast("Nickname is empty");
            return;
        }
        if (TextUtils.isEmpty(email)) {
            showOKDialog("Email  can not be empty");
//            toast("email is empty");
            return;
        }
        if (!Utils.isEmail(email)){
            showOKDialog("Email format is not correct");
            return;
        }
        if (mRegister.extendedInfo.avatar == null){
            showOKDialog("Please upload your profile photo");
            return;
        }
        validateEmail();
    }

    private void validateEmail() {
        AppFuncs.showProgressWaiting(this);
        AccountHelper.VALIDATE_EMAIL email = new AccountHelper.VALIDATE_EMAIL();
        email.data.emailAddress = this.email;
        OkUtil.publicPost(Url.accounts_helper, new Gson().toJson(email), new OkUtil.Callback() {
            @Override
            public void success(Response<String> response) {
                Log.e(TAG, "success: " + response.body());
                AccountHelper.VALIDATE_EMAIL.Response json = new Gson().fromJson(response.body(), AccountHelper.VALIDATE_EMAIL.Response.class);
                if (json.code == 1000) {
                    regist();
                } else {
                   AppFuncs.dismissProgressWaiting();
                    showOKDialog(json.message);
                }
            }
            @Override
            public void error(Response<String> response) {
                Log.e(TAG, "success: " + response.body());
                AppFuncs.dismissProgressWaiting();
                showErroe();
            }

        });
    }

    private void regist() {
        mRegister.email = email;
        mRegister.firstName = mNickname;//firstName
        mRegister.nickName = mNickname;//firstName
        mRegister.gender = adapterGender.getItem(spinnerProfileGender.getSelectedItemPosition()).toString().toUpperCase();
        mRegister.extendedInfo.server = BuildConfig.DOMAIN;
        Log.e(TAG, " 发送的数据" +  new Gson().toJson(mRegister));
        OkUtil.publicPost(Url.accounts, new Gson().toJson(mRegister), new OkUtil.Callback() {
            @Override
            public void success(Response<String> response) {
                AppFuncs.dismissProgressWaiting();
                AccountHelper.VALIDATE_EMAIL.Response json = new Gson().fromJson(response.body(), AccountHelper.VALIDATE_EMAIL.Response.class);
//                toast(json.message);
                Log.e(TAG, "success: " + response.body().toString());
                if (json.code == 1000) {
                    PopupUtils.showCustomDialog(mContext, "", "SUCCESS", R.string.ok, -1, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (v.getId() == R.id.btnOk){
                                ManagementAllActivity.finishAllActivity();
                                startAndClearAll(LoginActivity.class);
                            }
                        }
                    }, null);
                } else {
                    showOKDialog(response.body().toString());
                }

            }
            @Override
            public void error(Response<String> response) {
                AppFuncs.dismissProgressWaiting();
                showErroe();
            }
        });
    }

    @Override
    public void onResultPickerImage(boolean isAvatar, Intent data, boolean isSuccess) {
        super.onResultPickerImage(isAvatar, data, isSuccess);
        if (isAvatar) {
            uriAvatar = UCrop.getOutput(data);
            if (uriAvatar != null) {//如果选择了头像
                try {
                    String base64 = UIUtil.bitmapToBase64(MediaStore.Images.Media.getBitmap(getContentResolver(), uriAvatar));
                    imgAvatar.setImageURI(uriAvatar);
                    mRegister.extendedInfo.avatar = base64; //头像
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            uriHeader = UCrop.getOutput(data);
            if (uriHeader != null) {
                try {
                    String base64 = UIUtil.bitmapToBase64(MediaStore.Images.Media.getBitmap(getContentResolver(), uriHeader));
                    imgHeader.setImageURI(uriHeader);
                    mRegister.extendedInfo.backGroundImage = base64;//背景
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
