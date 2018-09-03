package net.wrappy.im.contants;

import android.content.Context;
import android.os.Build;

import net.wrappy.im.util.SpUtil;

import java.util.Locale;

/**
 * 创建者     彭龙
 * 创建时间   2018/6/21 上午10:05
 * 描述	      ${TODO}
 * <p>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class ConsUtils {
    public static final String INTENT_REGISTER = "register";
    public static final String INTENT_LOGIN = "login";
    public static final String INTENT = "intent";
    public static final String AUTHORIZATION_KEY = "Authorization";
    public static final String AUTHORIZATION_VALUE_DEFAULT = "Basic d3JhcHB5LWludGVybmFsOjZFMDdFRDEyRUFDODQ4QTk2REJCRjUwMjBEM0ZGNEY2";

    public static final String ADMIN_USERNAME = "admin";
    public static final String ADMIN_PASSWORD = "not4u2know";
    public static final String ACCESSTOKEN = "accesstoken";
    public static final String REFRESHTOKEN = "refreshtoken";
    public static final String USERINFO = "userinfo";
    public static final String TOKENTYPE = "token_type";
    public static final String PROFILE = "profile";
    public static final String REGISTRATION = "registration";
    public static final String VALIDATE_SMS_CODE = "validate_sms_code";
    public static final String VALIDATE_EMAIL = "validate_email";
    public static final String USERNAME = "username";
    public static final String FIND_USER_ACCOUNT = "find_user_account";
    public static final String INTENT_CHECK = "check";
    public static String VALIDATE_PHONE = "validate_phone";
    public static String VALIDATE_PASSWORD = "validate_password";
    public static String SECURITY_QUESTIONS = "security_questions";

    public static String getAccestoken() {
        return SpUtil.getString(USERINFO, ACCESSTOKEN, null);
    }

    public static void putAccestoken(String token) {
        SpUtil.putString(USERINFO, ACCESSTOKEN, token);
    }

    public static String getRefreshtoken() {
        return SpUtil.getString(USERINFO, REFRESHTOKEN, null);
    }

    public static void putRefreshtoken(String token) {
        SpUtil.putString(USERINFO, REFRESHTOKEN, token);
    }

    public static String getTokenType() {
        return SpUtil.getString(USERINFO, TOKENTYPE, null);
    }

    public static void putTokenType(String token_type) {
        SpUtil.putString(USERINFO, TOKENTYPE, token_type);
    }


    public static String getAdminAccestoken() {
        return SpUtil.getString(ADMIN_USERNAME, ACCESSTOKEN, null);
    }

    public static void putAdminAccestoken(String token) {
        SpUtil.putString(ADMIN_USERNAME, ACCESSTOKEN, token);
    }

    public static String getAdminRefreshtoken() {
        return SpUtil.getString(ADMIN_USERNAME, REFRESHTOKEN, null);
    }

    public static void putAdminRefreshtoken(String token) {
        SpUtil.putString(ADMIN_USERNAME, REFRESHTOKEN, token);
    }

    public static String getAdminTokenType() {
        return SpUtil.getString(ADMIN_USERNAME, TOKENTYPE, null);
    }

    public static void putAdminTokenType(String token_type) {
        SpUtil.putString(ADMIN_USERNAME, TOKENTYPE, token_type);
    }

    public static String getLanguage(Context context) {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = context.getResources().getConfiguration().getLocales().get(0);
        } else {
            locale = context.getResources().getConfiguration().locale;
        }
        return locale.getLanguage();
    }
}
