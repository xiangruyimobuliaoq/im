package net.wrappy.im.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.BottomSheetDialog;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.wrappy.im.R;
import net.wrappy.im.model.BottomSheetCell;
import net.wrappy.im.model.BottomSheetListener;

import java.util.ArrayList;

/**
 * Created by hp on 12/18/2017.
 */

public class PopupUtils {
    private static final String TAG = "PopupUtils";
    private static AlertDialog dialog;

    public static void getSelectionDialog(Context context, String title, ArrayAdapter<String> languagesAdapter, DialogInterface.OnClickListener listener) {
        getSelectionDialog(context, title, -1, languagesAdapter, listener);
    }

    public static void getSelectionDialog(Context context, String title, int resIcon, ArrayAdapter<String> languagesAdapter, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (resIcon > 0)
            builder.setIcon(resIcon);
        builder.setTitle(title);
        builder.setAdapter(languagesAdapter, listener);
        builder.show();
    }

    public static void getSelectionDialog(Context context, String title, CharSequence[] options, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        if (!TextUtils.isEmpty(title))
            builder.setTitle(title);

        builder.setItems(options, listener);

        builder.show();
    }

    public static void showOKDialog(Context context, String title, String message) {
        showCustomDialog(context, title, message, R.string.ok, -1, null, null, true);
    }

    public static void showOKDialog(Context context, String title, String message, View.OnClickListener onOkListener) {
        showCustomDialog(context, title, message, R.string.ok, -1, onOkListener, null, true);
    }

    public static void showOKCancelDialog(Context context, String title, String message, View.OnClickListener onOkListener, View.OnClickListener onCancelListener) {
        showCustomDialog(context, title, message, R.string.ok, R.string.cancel, onOkListener, onCancelListener, true);
    }

    public static void showCustomDialog(Context context, String title, String message, int resOK, View.OnClickListener onOkListener) {
        showCustomDialog(context, title, message, resOK, -1, onOkListener, null, true);
    }

    public static void showCustomDialog(Context context, String title, String message, int resOK, View.OnClickListener onOkListener, boolean isCancelable) {
        showCustomDialog(context, title, message, resOK, -1, onOkListener, null, isCancelable);
    }

    public static void showCustomDialog(Context context, String title, String message, int resOK, int resCancel, View.OnClickListener onOkListener, View.OnClickListener onCancelListener) {
        showCustomDialog(context, title, message, resOK, resCancel, onOkListener, onCancelListener, true);
    }

    public static void showCustomDialog(Context context, String title, String message, int resOK, int resCancel, View.OnClickListener onOkListener, View.OnClickListener onCancelListener, boolean isCancelable) {
        dismissDialog();
        try {
            View dialogView = getView(context, R.layout.custom_alert_dialog);
            AlertDialog.Builder builder = getBuilderDialog(context, dialogView, isCancelable);
            dialog = builder.show();
            handleButtons(dialog, dialogView, resOK, resCancel, onOkListener, onCancelListener);
            handleTexts(dialogView, title, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void dismissDialog() {
        try {
            if (dialog != null && dialog.isShowing())
                dialog.dismiss();
        } catch (Exception e) {
        }
    }

    private static void handleTexts(View dialogView, String title, String message) {
        TextView tvTitle = (TextView) dialogView.findViewById(R.id.txtTitle);
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
            tvTitle.setVisibility(View.VISIBLE);
        } else tvTitle.setVisibility(View.GONE);

        TextView tvMessage = (TextView) dialogView.findViewById(R.id.txtMessage);
        if (!TextUtils.isEmpty(message)) {
            tvMessage.setText(message);
            tvMessage.setVisibility(View.VISIBLE);
        } else tvMessage.setVisibility(View.GONE);
    }

    private static void handleButtons(Dialog dialog, View dialogView, int resOK, int resCancel, View.OnClickListener onOkListener, View.OnClickListener onCancelListener) {
        handleButtons(dialog, dialogView, resOK, resCancel, onOkListener, onCancelListener, null);
    }

    private static void handleButtons(final Dialog dialog, View dialogView, int resOK, int resCancel, final View.OnClickListener onOkListener, final View.OnClickListener onCancelListener, final EditText editText) {
        TextView btnOk = (TextView) dialogView.findViewById(R.id.btnOk);
        TextView btnCancel = (TextView) dialogView.findViewById(R.id.btnCancel);
        if (resOK > 0) {
            btnOk.setText(resOK);
            btnOk.setVisibility(View.VISIBLE);
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editText != null)
                        v.setTag(editText.getText().toString());
                    if (onOkListener != null)
                        onOkListener.onClick(v);
                    dialog.dismiss();
                }
            });
        } else {
            btnOk.setVisibility(View.GONE);
        }
        if (resCancel > 0) {
            btnCancel.setText(resCancel);
            btnCancel.setVisibility(View.VISIBLE);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onCancelListener != null)
                        onCancelListener.onClick(v);
                    dialog.dismiss();
                }
            });
        } else {
            btnCancel.setVisibility(View.GONE);
        }
    }

    public static void showCustomInputPasswordDialog(Context context, String message, int resOK, int resCancel, View.OnClickListener onOkListener, View.OnClickListener onCancelListener) {
        showCustomEditDialog(context, message, "", resOK, resCancel, onOkListener, onCancelListener, true);
    }

    public static void showCustomEditDialog(Context context, String message, String data, int resOK, int resCancel, View.OnClickListener onOkListener, View.OnClickListener onCancelListener) {
        showCustomEditDialog(context, message, data, resOK, resCancel, onOkListener, onCancelListener, false);
    }

    public static void showCustomEditDialog(Context context, String message, String data, int resOK, int resCancel, View.OnClickListener onOkListener, View.OnClickListener onCancelListener, boolean isPassword) {
        showCustomEditDialog(context, "", message, data, resOK, resCancel, onOkListener, onCancelListener, isPassword);
    }

    public static void showCustomEditDialog(Context context, String title, String message, String data, int resOK, int resCancel, View.OnClickListener onOkListener, View.OnClickListener onCancelListener, boolean isPassword) {
        View dialogView = getView(context, R.layout.dialog_with_edittext);
        AlertDialog.Builder builder = getBuilderDialog(context, dialogView);

        handleTexts(dialogView, title, message);
        EditText edt = (EditText) dialogView.findViewById(R.id.etinputpass);
        if (isPassword) {
            edt.setHint(Html.fromHtml(context.getString(R.string.hint_password)));
        } else {
            edt.setInputType(InputType.TYPE_CLASS_TEXT);
            edt.setText(data);
        }

        Dialog dialog = builder.show();
        handleButtons(dialog, dialogView, resOK, resCancel, onOkListener, onCancelListener, edt);
    }

//    public static void showCustomViewDialog(Context context, View view, int resOK, View.OnClickListener onOkListener) {
//        showCustomViewDialog(context, view, resOK, -1, onOkListener, null);
//    }

    public static void showCustomViewDialog(Context context, View view, int resOK, int resCancel, View.OnClickListener onOkListener, View.OnClickListener onCancelListener) {
        showCustomViewDialog(context, view, "", "", resOK, resCancel, onOkListener, onCancelListener);
    }

    public static void showCustomViewDialog(Context context, View view, String title, String message, int resOK, int resCancel, View.OnClickListener onOkListener, View.OnClickListener onCancelListener) {
        View dialogView = getView(context, R.layout.custom_alert_dialog);
        AlertDialog.Builder builder = getBuilderDialog(context, dialogView);

        Dialog dialog = builder.show();
        handleButtons(dialog, dialogView, resOK, resCancel, onOkListener, onCancelListener);
        ViewGroup group = (ViewGroup) dialogView.findViewById(R.id.lnContent);
        group.addView(view);

        TextView tvTitle = (TextView) dialogView.findViewById(R.id.txtTitle);
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        } else group.removeView(tvTitle);

        TextView tvMessage = (TextView) dialogView.findViewById(R.id.txtMessage);
        if (!TextUtils.isEmpty(message)) {
            tvMessage.setText(message);
        } else group.removeView(tvMessage);
    }

    private static AlertDialog.Builder getBuilderDialog(Context context, View dialogView) {
        return getBuilderDialog(context, dialogView, true);
    }

    private static AlertDialog.Builder getBuilderDialog(Context context, View dialogView, boolean isCancelable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);
        builder.setCancelable(isCancelable);
        return builder;
    }

    private static View getView(Context context, int resId) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(resId, null);
    }

    public static BottomSheetDialog createBottomSheet(Activity activity, ArrayList<BottomSheetCell> sheetCells, final BottomSheetListener sheetListener) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(activity);
        final View view = LayoutInflater.from(activity).inflate(R.layout.bottom_sheet_dialog, null);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.bottomSheetContainer);
        for (int i = 0; i < sheetCells.size(); i++) {
            final int index = sheetCells.get(i).getIndex();
            linearLayout.addView(createBottomSheetCell(activity, sheetCells.get(i).getResId(), sheetCells.get(i).getTitle(), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sheetListener.onSelectBottomSheetCell(index);
                    bottomSheetDialog.cancel();
                }
            }));
        }
        Button button = (Button) view.findViewById(R.id.bottomSheetCancel);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.cancel();
            }
        });
        bottomSheetDialog.setContentView(view);
        return bottomSheetDialog;
    }

    private static View createBottomSheetCell(Activity activity, int resId, String tite, View.OnClickListener listener) {
        View view = LayoutInflater.from(activity).inflate(R.layout.bottom_sheet_cell, null);
        if (resId != 0) {
            ImageView imageView = (ImageView) view.findViewById(R.id.bottomSheetIcon);
            imageView.setImageResource(resId);
        }
        TextView appTextView = (TextView) view.findViewById(R.id.bottomSheetTitle);
        appTextView.setText(tite);
        appTextView.setOnClickListener(listener);
        return view;
    }

}
