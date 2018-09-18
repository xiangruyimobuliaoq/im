package net.wrappy.im.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者     彭龙
 * 创建时间   2018/9/13 17:46
 * 描述	      ${TODO}
 * <p>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class ManagementAllActivity {

   private static List<Activity> mList = new ArrayList<>();
    public static void addActivity(Activity activity){
        mList.add(activity);
    }

    public static void removeActivity(Activity activity){

        for (int i = 0; i <mList.size() ; i++) {
            if (activity.equals(mList.get(i))){
                mList.remove(i);
            }
        }
    }


    public static void finishAllActivity(){

        try {
            for (Activity activity : mList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
