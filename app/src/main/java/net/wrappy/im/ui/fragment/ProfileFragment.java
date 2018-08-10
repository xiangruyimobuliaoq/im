package net.wrappy.im.ui.fragment;

import android.text.TextUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import net.wrappy.im.BaseFragment;
import net.wrappy.im.R;
import net.wrappy.im.contants.ConsUtils;
import net.wrappy.im.model.Auth;
import net.wrappy.im.ui.view.Layout;
import net.wrappy.im.util.SpUtil;
import net.wrappy.im.util.UIUtil;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

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
    @BindView(R.id.edProfileFullName)
    protected TextView edProfileFullName;
    @BindView(R.id.txtProfileUsername)
    protected TextView txtProfileUsername;
    @BindView(R.id.edProfilePhone)
    protected TextView edProfilePhone;
    @BindView(R.id.edProfileEmail)
    protected TextView edProfileEmail;

    @BindView(R.id.imgPhotoAvatar)
    CircleImageView imgAvatar;
    @BindView(R.id.imgProfileHeader)
    ImageView imgHeader;

    @BindView(R.id.btnProfileCameraHeader)
    ImageButton btnCameraHeader;
    @BindView(R.id.btnPhotoCameraAvatar)
    ImageButton btnCameraAvatar;

    private Auth mAuth;

    @Override
    protected void init() {
        mAuth = (Auth) SpUtil.readObj(ConsUtils.PROFILE);
        try {
            edProfileFullName.setText(mAuth.account.nickName);
            txtProfileUsername.setText(mAuth.account.extendedInfo.username);
            edProfilePhone.setText(mAuth.account.mobilePhone);
            edProfileEmail.setText(mAuth.account.email);
            if (!TextUtils.isEmpty(mAuth.account.extendedInfo.avatar))
                imgAvatar.setImageBitmap(UIUtil.base64ToBitmap(mAuth.account.extendedInfo.avatar));
            if (!TextUtils.isEmpty(mAuth.account.extendedInfo.backGroundImage))
                imgHeader.setImageBitmap(UIUtil.base64ToBitmap(mAuth.account.extendedInfo.backGroundImage));
        } catch (Exception e) {
        }
    }
}
