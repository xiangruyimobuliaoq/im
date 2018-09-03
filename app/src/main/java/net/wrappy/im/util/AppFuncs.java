package net.wrappy.im.util;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.yalantis.ucrop.UCrop;

import net.wrappy.im.BaseActivity;
import net.wrappy.im.BuildConfig;
import net.wrappy.im.R;
import net.wrappy.im.ui.fragment.ProfileFragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by ben on 14/11/2017.
 */

public class AppFuncs {
    private static ProgressDialog dialog;
    private static String mCurrentPhotoPath;

    public static void log(String string) {
        if (BuildConfig.DEBUG) {
            Log.d("wrappy_log", string);
        }
    }

    public static void showProgressWaiting(Activity activity) {
        dialog = new ProgressDialog(activity);
        dialog.setCancelable(false);
        dialog.setMessage(activity.getString(R.string.waiting_dialog));
        if (!activity.isFinishing()) {
            dialog.show();
        }
    }

    public static void dismissProgressWaiting() {
        if (null != dialog && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public static String bitmapToBase64String(Bitmap bitmap) {
        String base64String = "";
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            base64String = Base64.encodeToString(byteArray, Base64.DEFAULT);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return base64String;
    }


    public static void dismissKeyboard(Activity activity) {
        try {
            View view = activity.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm.isAcceptingText())
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void shareApp(Activity activity, String content) {
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, activity.getString(R.string.app_name));
            i.putExtra(Intent.EXTRA_TEXT, content);
            activity.startActivity(Intent.createChooser(i, activity.getString(R.string.share_title)));
        } catch (Exception e) {
            //e.toString();
        }
    }

    public static void openCamera(Activity activity, boolean isAvatar) {
        if ((ContextCompat.checkSelfPermission(activity,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) && ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile(activity);
                } catch (IOException ex) {

                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(activity,
                            activity.getPackageName() + ".provider",
                            photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    if (isAvatar) {
                        activity.startActivityForResult(takePictureIntent, BaseActivity.RESULT_AVATAR);
                    } else {
                        activity.startActivityForResult(takePictureIntent, BaseActivity.RESULT_BANNER);
                    }

                }
            }
        } else {
            if (isAvatar) {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE},
                        BaseActivity.REQUEST_PERMISSION_CAMERA_AVATAR);
            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE},
                        BaseActivity.REQUEST_PERMISSION_CAMERA_BANNER);
            }

        }
    }
    public static void openCameraFrag(ProfileFragment activity, boolean isAvatar) {
        if ((ContextCompat.checkSelfPermission(activity.getActivity(),
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) && ContextCompat.checkSelfPermission(activity.getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(activity.getActivity().getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile(activity);
                } catch (IOException ex) {

                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(activity.getActivity(),
                            activity.getActivity().getPackageName() + ".provider",
                            photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    if (isAvatar) {
                        activity.startActivityForResult(takePictureIntent, BaseActivity.RESULT_AVATAR);
                    } else {
                        activity.startActivityForResult(takePictureIntent, BaseActivity.RESULT_BANNER);
                    }

                }
            }
        } else {
            if (isAvatar) {
                ActivityCompat.requestPermissions(activity.getActivity(),
                        new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE},
                        BaseActivity.REQUEST_PERMISSION_CAMERA_AVATAR);
            } else {
                ActivityCompat.requestPermissions(activity.getActivity(),
                        new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE},
                        BaseActivity.REQUEST_PERMISSION_CAMERA_BANNER);
            }

        }
    }

    private static File createImageFile(Activity activity) throws IOException {
        // Create an image file name
        String imageFileName = "JPEG_" + UUID.randomUUID().toString();
        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
    private static File createImageFile(ProfileFragment activity) throws IOException {
        // Create an image file name
        String imageFileName = "JPEG_" + UUID.randomUUID().toString();
        File storageDir = activity.getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public static void openGallery(Activity activity, boolean isAvatar) {
        if ((ContextCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            Intent intent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            intent.setType("image/*");
            if (isAvatar) {
                activity.startActivityForResult(intent, BaseActivity.RESULT_AVATAR);
            } else {
                activity.startActivityForResult(intent, BaseActivity.RESULT_BANNER);
            }
        } else {
            if (isAvatar) {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        BaseActivity.REQUEST_PERMISSION_PICKER_AVATAR);
            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        BaseActivity.REQUEST_PERMISSION_PICKER_BANNER);
            }

        }
    }
    public static void openGalleryFrag(ProfileFragment activity, boolean isAvatar) {
        if ((ContextCompat.checkSelfPermission(activity.getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            Intent intent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            intent.setType("image/*");
            if (isAvatar) {
                activity.startActivityForResult(intent, BaseActivity.RESULT_AVATAR);
            } else {
                activity.startActivityForResult(intent, BaseActivity.RESULT_BANNER);
            }
        } else {
            if (isAvatar) {
                ActivityCompat.requestPermissions(activity.getActivity(),
                        new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        BaseActivity.REQUEST_PERMISSION_PICKER_AVATAR);
            } else {
                ActivityCompat.requestPermissions(activity.getActivity(),
                        new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        BaseActivity.REQUEST_PERMISSION_PICKER_BANNER);
            }

        }
    }

    public static void cropImage(Activity activity, Intent intent, boolean isAvarta, int requestCode) {
        Uri source = null;
        if (intent == null) {
            File path = new File(mCurrentPhotoPath);
            source = Uri.fromFile(path);
        } else {
            if (intent.getData() != null) {
                source = intent.getData();
            } else {
                File path = new File(mCurrentPhotoPath);
                source = Uri.fromFile(path);
            }
        }
        if (source == null) {
            return;
        }
        Uri destination = Uri.fromFile(new File(activity.getCacheDir(), UUID.randomUUID().toString()));

        if (isAvarta) {
            UCrop.of(source, destination)
                    .withAspectRatio(1, 1)
                    .withMaxResultSize(100, 100)
                    .start(activity, requestCode);
        } else {
            UCrop.of(source, destination)
                    .withAspectRatio(16, 9)
                    .withMaxResultSize(320, 180)
                    .start(activity, requestCode);
        }

    }

    public static boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
