package net.wrappy.im.util;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.Response;

import net.wrappy.im.App;
import net.wrappy.im.BaseActivity;
import net.wrappy.im.contants.ConsUtils;
import net.wrappy.im.contants.Url;
import net.wrappy.im.model.Auth;
import net.wrappy.im.ui.activity.LoginActivity;

/**
 * 创建者     彭龙
 * 创建时间   2018/7/28 上午10:19
 * 描述	      ${TODO}
 * <p>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class OkUtil {

    private static final String TAG = "OkUtil";



    public static Gson gson = new Gson();

    public static void init(App app) {
        HttpHeaders headers = new HttpHeaders();
        headers.put(ConsUtils.AUTHORIZATION_KEY, ConsUtils.AUTHORIZATION_VALUE_DEFAULT);
        OkGo.getInstance().init(app).addCommonHeaders(headers);
    }


    public static void refresh(String token, String type) {
        HttpHeaders headers = new HttpHeaders();
        headers.put(ConsUtils.AUTHORIZATION_KEY, type + " " + token);
        OkGo.getInstance().addCommonHeaders(headers);
    }

    public static void refreshAdmin() {
        HttpHeaders headers = new HttpHeaders();
        headers.put(ConsUtils.AUTHORIZATION_KEY, ConsUtils.AUTHORIZATION_VALUE_DEFAULT);
        OkGo.getInstance().addCommonHeaders(headers);
    }

    public static void Login(String username, String password, final Callback callback) {
        HttpHeaders headers = new HttpHeaders();
        headers.put(ConsUtils.AUTHORIZATION_KEY, ConsUtils.AUTHORIZATION_VALUE_DEFAULT);
        OkGo.getInstance().addCommonHeaders(headers);
        OkGo.<String>post(Url.OauthToken)
                .params("grant_type", "password")
                .params("username", username)
                .params("password", password)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e(TAG, "111onSuccess: " + response.body() );
                        Auth auth = gson.fromJson(response.body(), Auth.class);
                        ConsUtils.putAccestoken(auth.access_token);
                        ConsUtils.putRefreshtoken(auth.refresh_token);
                        ConsUtils.putTokenType(auth.token_type);
                        SpUtil.saveObj(ConsUtils.PROFILE, auth);
                        refresh(auth.access_token, auth.token_type);
                        callback.success(response);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callback.error(response);
                    }
                });
    }

    /**
     * 没有登录之前调用此方法
     * @param url
     * @param json
     * @param callback
     */
    public static void publicPost(final String url, final String json, final Callback callback) {
        if (TextUtils.isEmpty(ConsUtils.getAdminAccestoken()) || TextUtils.isEmpty(ConsUtils.getAdminRefreshtoken()) || TextUtils.isEmpty(ConsUtils.getAdminTokenType())) {
            HttpHeaders headers = new HttpHeaders();
            headers.put(ConsUtils.AUTHORIZATION_KEY, ConsUtils.AUTHORIZATION_VALUE_DEFAULT);
            OkGo.getInstance().addCommonHeaders(headers);
            OkGo.<String>post(Url.OauthToken)
                    .params("grant_type", "password")
                    .params("username", ConsUtils.ADMIN_USERNAME)
                    .params("password", ConsUtils.ADMIN_PASSWORD)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            Auth auth = gson.fromJson(response.body(), Auth.class);
                            ConsUtils.putAdminAccestoken(auth.access_token);
                            ConsUtils.putAdminRefreshtoken(auth.refresh_token);
                            ConsUtils.putAdminTokenType(auth.token_type);
                            refresh(auth.access_token, auth.token_type);
                            publicPost(url, json, callback);
                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                        }
                    });
        } else {
            OkGo.<String>post(url).upJson(json).execute(new StringCallback() {
                @Override
                public void onSuccess(Response<String> response) {
                    if (response.body().contains("{\"error\":\"unauthorized\",")) {
                        ConsUtils.putAdminAccestoken(null);
                        publicPost(url, json, callback);
                    } else {
                        callback.success(response);
                    }
                }

                @Override
                public void onError(Response<String> response) {
                    super.onError(response);
                    callback.error(response);
                }
            });
        }
    }

    public static void publicGet(final String url, final Callback callback) {
        if (TextUtils.isEmpty(ConsUtils.getAdminAccestoken()) || TextUtils.isEmpty(ConsUtils.getAdminRefreshtoken()) || TextUtils.isEmpty(ConsUtils.getAdminTokenType())) {
            HttpHeaders headers = new HttpHeaders();
            headers.put(ConsUtils.AUTHORIZATION_KEY, ConsUtils.AUTHORIZATION_VALUE_DEFAULT);
            OkGo.getInstance().addCommonHeaders(headers);
            OkGo.<String>post(Url.OauthToken)
                    .params("grant_type", "password")
                    .params("username", ConsUtils.ADMIN_USERNAME)
                    .params("password", ConsUtils.ADMIN_PASSWORD)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            Auth auth = gson.fromJson(response.body(), Auth.class);
                            ConsUtils.putAdminAccestoken(auth.access_token);
                            ConsUtils.putAdminRefreshtoken(auth.refresh_token);
                            ConsUtils.putAdminTokenType(auth.token_type);
                            refresh(auth.access_token, auth.token_type);
                            publicGet(url, callback);
                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                        }
                    });
        } else {
            OkGo.<String>get(url).execute(new StringCallback() {
                @Override
                public void onSuccess(Response<String> response) {
                    if (response.body().contains("{\"error\":\"unauthorized\",")) {
                        ConsUtils.putAdminAccestoken(null);
                        publicGet(url, callback);
                    } else {
                        callback.success(response);
                    }
                }

                @Override
                public void onError(Response<String> response) {
                    super.onError(response);
                    callback.error(response);
                }
            });
        }
    }

    public static void privatePost(final BaseActivity activity, final String url, final String json, final Callback callback) {
        if (TextUtils.isEmpty(ConsUtils.getAccestoken()) || TextUtils.isEmpty(ConsUtils.getRefreshtoken()) || TextUtils.isEmpty(ConsUtils.getTokenType())) {
            activity.overlay(LoginActivity.class);
        } else {
            OkGo.<String>post(url).upJson(json).execute(new StringCallback() {
                @Override
                public void onSuccess(Response<String> response) {
                    if (response.body().contains("{\"error\":\"unauthorized\",") || response.body().contains("{\"error\":\"invalid_token\",")) {
                        refreshAdmin();
                        OkGo.<String>post(Url.OauthToken)
                                .params("grant_type", "refresh_token")
                                .params("refresh_token", ConsUtils.getRefreshtoken())
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(Response<String> response) {
                                        if (response.body().contains("{\"error\":\"unauthorized\",") || response.body().contains("{\"error\":\"invalid_token\",")) {
                                            activity.overlay(LoginActivity.class);
                                        } else {
                                            Log.e(TAG, "222onSuccess: " + response.body() );
                                            Auth auth = gson.fromJson(response.body(), Auth.class);
                                            ConsUtils.putAccestoken(auth.access_token);
                                            ConsUtils.putRefreshtoken(auth.refresh_token);
                                            ConsUtils.putTokenType(auth.token_type);
                                            SpUtil.saveObj(ConsUtils.PROFILE, auth);
                                            refresh(auth.access_token, auth.token_type);
                                            privatePost(activity, url, json, callback);
                                        }
                                    }

                                    @Override
                                    public void onError(Response<String> response) {
                                        super.onError(response);
                                        callback.error(response);
                                    }
                                });
                    } else {
                        callback.success(response);
                    }
                }

                @Override
                public void onError(Response<String> response) {
                    super.onError(response);
                    callback.error(response);
                }
            });
        }
    }

    public static void privatePut(final BaseActivity activity, final String url, final String json, final Callback callback) {
        if (TextUtils.isEmpty(ConsUtils.getAccestoken()) || TextUtils.isEmpty(ConsUtils.getRefreshtoken()) || TextUtils.isEmpty(ConsUtils.getTokenType())) {
            activity.overlay(LoginActivity.class);
        } else {
            OkGo.<String>put(url).upJson(json).execute(new StringCallback() {
                @Override
                public void onSuccess(Response<String> response) {
                    if (response.body().contains("{\"error\":\"unauthorized\",") || response.body().contains("{\"error\":\"invalid_token\",")) {
                        refreshAdmin();
                        OkGo.<String>post(Url.OauthToken)
                                .params("grant_type", "refresh_token")
                                .params("refresh_token", ConsUtils.getRefreshtoken())
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(Response<String> response) {
                                        if (response.body().contains("{\"error\":\"unauthorized\",") || response.body().contains("{\"error\":\"invalid_token\",")) {
                                            activity.overlay(LoginActivity.class);
                                        } else {
                                            Log.e(TAG, "333onSuccess: " + response.body());
                                            Auth auth = gson.fromJson(response.body(), Auth.class);
                                            ConsUtils.putAccestoken(auth.access_token);
                                            ConsUtils.putRefreshtoken(auth.refresh_token);
                                            ConsUtils.putTokenType(auth.token_type);
                                            SpUtil.saveObj(ConsUtils.PROFILE, auth);
                                            refresh(auth.access_token, auth.token_type);
                                            privatePut(activity, url, json, callback);
                                        }
                                    }

                                    @Override
                                    public void onError(Response<String> response) {
                                        super.onError(response);
                                        callback.error(response);
                                    }
                                });
                    } else {
                        callback.success(response);
                    }
                }

                @Override
                public void onError(Response<String> response) {
                    super.onError(response);
                    callback.error(response);
                }
            });
        }
    }

    public static void privateGet(final BaseActivity activity, final String url, final Callback callback) {
        if (TextUtils.isEmpty(ConsUtils.getAccestoken()) || TextUtils.isEmpty(ConsUtils.getRefreshtoken()) || TextUtils.isEmpty(ConsUtils.getTokenType())) {
            activity.overlay(LoginActivity.class);
        } else {
            OkGo.<String>get(url).execute(new StringCallback() {
                @Override
                public void onSuccess(Response<String> response) {
                    if (response.body().contains("{\"error\":\"unauthorized\",") || response.body().contains("{\"error\":\"invalid_token\",")) {
                        refreshAdmin();
                        OkGo.<String>post(Url.OauthToken)
                                .params("grant_type", "refresh_token")
                                .params("refresh_token", ConsUtils.getRefreshtoken())
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(Response<String> response) {
                                        if (response.body().contains("{\"error\":\"unauthorized\",") || response.body().contains("{\"error\":\"invalid_token\",")) {
                                            activity.overlay(LoginActivity.class);
                                        } else {
                                            Log.e(TAG, "444onSuccess: " + response.body() );
                                            Auth auth = gson.fromJson(response.body(), Auth.class);
                                            ConsUtils.putAccestoken(auth.access_token);
                                            ConsUtils.putRefreshtoken(auth.refresh_token);
                                            ConsUtils.putTokenType(auth.token_type);
                                            SpUtil.saveObj(ConsUtils.PROFILE, auth);
                                            refresh(auth.access_token, auth.token_type);
                                            privateGet(activity, url, callback);
                                        }
                                    }

                                    @Override
                                    public void onError(Response<String> response) {
                                        super.onError(response);
                                        callback.error(response);
                                    }
                                });
                    } else {
                        callback.success(response);
                    }
                }

                @Override
                public void onError(Response<String> response) {
                    super.onError(response);
                    callback.error(response);
                }
            });
        }
    }

    public abstract static class Callback {
        public abstract void success(Response<String> response);

        public void error(Response<String> response) {
        }
    }
}
