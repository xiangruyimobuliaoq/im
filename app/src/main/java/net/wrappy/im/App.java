package net.wrappy.im;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;

import net.wrappy.im.contants.ConsUtils;
import net.wrappy.im.util.OkUtil;
import net.wrappy.im.util.UIUtil;

/**
 * 创建者     彭龙
 * 创建时间   2018/6/14 下午3:35
 * 描述	      ${TODO}
 * <p>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class App extends Application {

    public static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        UIUtil.init(this);
        OkUtil.init(this);
        app = this;
    }

    public void initOkgo() {
        HttpHeaders headers = new HttpHeaders();
        headers.put(ConsUtils.AUTHORIZATION_KEY, ConsUtils.AUTHORIZATION_VALUE_DEFAULT);
        OkGo.getInstance().init(this).addCommonHeaders(headers);
    }
}
