package net.wrappy.im.model;

/**
 * 创建者     彭龙
 * 创建时间   2018/9/21 16:43
 * 描述	      ${TODO}
 * <p>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class ModificationInfo {


    /**
     * patternPassword : 14789
     * account : {"firstName":"ClimbCrabtets","email":"pxbiawwww1@qq.com","mobilePhone":"+86168000000444401","extendedInfo":{"avatar":"BASE-64_STRING","backgroundImage":"BASE-64_STRING"}}
     */

    public String patternPassword;
    public AccountBean account = new AccountBean();

    public static class AccountBean {
        /**
         * firstName : ClimbCrabtets
         * email : pxbiawwww1@qq.com
         * mobilePhone : +86168000000444401
         * extendedInfo : {"avatar":"BASE-64_STRING","backgroundImage":"BASE-64_STRING"}
         */

        public String firstName;
        public String email;
        public String mobilePhone;
        public ExtendedInfoBean extendedInfo = new ExtendedInfoBean();


        public static class ExtendedInfoBean {
            /**
             * avatar : BASE-64_STRING
             * backgroundImage : BASE-64_STRING
             */

            public String avatar;
            public String backgroundImage;

        }
    }
}