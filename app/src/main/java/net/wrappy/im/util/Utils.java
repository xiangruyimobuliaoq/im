package net.wrappy.im.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import net.wrappy.im.App;
import net.wrappy.im.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.michaelrocks.libphonenumber.android.NumberParseException;
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import io.michaelrocks.libphonenumber.android.Phonenumber;

/**
 * Created by hp on 12/21/2017.
 */

public class Utils {

    private static final String TAG = "Utils";

    public static int getContrastColor(int colorIn) {
        double y = (299 * Color.red(colorIn) + 587 * Color.green(colorIn) + 114 * Color.blue(colorIn)) / 1000;
        return y >= 128 ? Color.BLACK : Color.WHITE;
    }

    public static String formatDurationMedia(long duration) {
        return String.valueOf(Math.round(duration * 2 / 2000.0) + "secs");// round 3/4
    }

    public static int getWithScreenDP(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);

        int screenWidth = Math.round(displayMetrics.widthPixels / displayMetrics.density);

        return screenWidth;
    }

    public static int convertSpToPixels(float sp, Context context) {
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
        return px;
    }

    public static String isValidEmail(Context context, String email) {
        String error = null;
        if (!TextUtils.isEmpty(email) && !AppFuncs.isEmailValid(email)) {
            error = context.getString(R.string.error_invalid_email);
        } else if (TextUtils.isEmpty(email)) {
            error = context.getString(R.string.error_empty_email);
        }
        return error;
    }

    public static String uppercaseFirstChar(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
    }

    public static boolean setTextForView(TextView view, String text) {
        if (view!=null && !TextUtils.isEmpty(text)) {
            view.setText(text);
            return true;
        }
        return false;
    }

    public static boolean checkValidateAppEditTextViews(Activity activity, EditText[] views, int[] errorRes) {
        String error = "";
        for (int i = 0; i < views.length; i++) {
            EditText editTextView = views[i];
            String text = editTextView.getText().toString().trim();
            String email = activity.getString(R.string.error_invalid_email);
            error = activity.getString(errorRes[i]);
            if (error.equalsIgnoreCase(email)) {
                if (!TextUtils.isEmpty(text) && !AppFuncs.isEmailValid(text)) {
                    error = activity.getString(R.string.error_invalid_email);
                    break;
                } else if (TextUtils.isEmpty(text)) {
                    error = activity.getString(R.string.error_empty_email);
                    break;
                } else {
                    error = "";
                }
            } else {
                if (!TextUtils.isEmpty(text)) {
                    error = "";
                } else
                    break;
            }
        }
        if (error.equalsIgnoreCase("")) {
            return true;
        } else {
            PopupUtils.showCustomDialog(activity, activity.getString(R.string.error), error, R.string.cancel, null);
            return false;
        }
    }



    /**
     * 判断邮箱是否合法
     * @param email
     * @return
     */
    public static boolean isEmail(String email){
        if (null==email || "".equals(email)) {
            return false;
        }
        String strPattern = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 根据区号判断是否是正确的电话号码
     * @param phoneNumber :带国家码的电话号码
     * @param countryCode :电话号码
     * return ：true 合法  false：不合法
     */
    public static boolean isPhoneNumberValid(String phoneNumber, String countryCode) {
        Log.e(TAG, "isPhoneNumberValid: " + phoneNumber+"/"+countryCode);
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.createInstance(App.app);
//        try {
//            Phonenumber.PhoneNumber swissNumberProto = phoneUtil.parse(countryCode, US);
//            //返回格式化过的电话号码  就是把电话号码第一位是0的去掉
//           String format = phoneUtil.format(swissNumberProto, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
//        } catch (NumberParseException e) {
//            e.printStackTrace();
//        }
        try{
            Phonenumber.PhoneNumber numberProto = phoneUtil.parse(phoneNumber, countryCode);
            return phoneUtil.isValidNumber(numberProto);
        }catch (NumberParseException e){
            e.printStackTrace();
            System.err.println("isPhoneNumberValid NumberParseException was thrown: " + e.toString());
        } return false;
    }


    /***
     *
     * @param phone   电话号码
     * @param US     国家代码  英文的
     * @return  格式过的电话号码
     */
    public static String getFormatHponeNumber(String phone,String US){
        String phonenumber = "";
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.createInstance(App.app);
        try {
            Phonenumber.PhoneNumber swissNumberProto = phoneUtil.parse(phone, US);
            long nationalNumber = swissNumberProto.getNationalNumber();
            phonenumber = "" + nationalNumber;
        } catch (NumberParseException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "getFormatHponeNumber: " + phonenumber  + US);
        return phonenumber.trim();
    }


    public static String majorizationPhone(String phone){
        String mPhone = "";
        if (phone.substring(0,1).equals("0")){
            mPhone = phone.substring(1,phone.length());
        }else {
            mPhone = phone;
        }

        Log.e(TAG, "majorizationPhone: " + mPhone);
        return mPhone;
    }




    /**
     * 毫秒转化 天、时、分、秒、毫秒
     */
    public static String formatTime(Long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        StringBuffer sb = new StringBuffer();
        if(day > 0) {
            sb.append(day+"天");
        }
        if(hour > 0) {
            sb.append(hour+"小时");
        }
        if(minute > 0) {
            sb.append(minute+"分");
        }
        if(second > 0) {
            sb.append(second+"秒");
        }
        if(milliSecond > 0) {
            sb.append(milliSecond+"毫秒");
        }
        return sb.toString();
    }

}
