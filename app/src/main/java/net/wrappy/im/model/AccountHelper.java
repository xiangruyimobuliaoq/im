package net.wrappy.im.model;

import net.wrappy.im.contants.ConsUtils;

import java.util.Calendar;
import java.util.List;

import static net.wrappy.im.contants.ConsUtils.VALIDATE_PASSWORD;
import static net.wrappy.im.contants.ConsUtils.VALIDATE_PHONE;
import static net.wrappy.im.contants.ConsUtils.VALIDATE_SMS_CODE;

/**
 * 创建者     彭龙
 * 创建时间   2018/7/28 下午4:42
 * 描述	      ${TODO}
 * <p>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class AccountHelper {

    public static class VALIDATE_PASSWORD_TWO {
        /**
         * type : validate_phone
         * data : {"countryCode":"86","phone":"13689728762","sendValidationCode":true,"checkRegistered":true,"codeType":"registration"}
         */
        public String type = ConsUtils.VALIDATE_USERNAME;
        public Data data = new Data();

        public static class Data {
            /**
             * username : 86
             * password : 13689728762
             */
            public String username;
            public String password;
        }
    }

        public static class VALIDATE_PHONE {
        /**
         * type : validate_phone
         * data : {"countryCode":"86","phone":"13689728762","sendValidationCode":true,"checkRegistered":true,"codeType":"registration"}
         */
        public String type = VALIDATE_PHONE;
        public Data data = new Data();

        public static class Data {
            /**
             * countryCode : 86
             * phone : 13689728762
             * sendValidationCode : true
             * checkRegistered : true
             * codeType : registration
             */
            public String countryCode;
            public String phone;
            public boolean sendValidationCode;
            public boolean checkRegistered;
            public String codeType;
            public String code;
        }
    }

    public static class VALIDATE_SMS_CODE {
        /**
         * type : validate_phone
         * data : {"countryCode":"86","phone":"13689728762","sendValidationCode":true,"checkRegistered":true,"codeType":"registration"}
         */
        public String type = VALIDATE_SMS_CODE;
        public Data data = new Data();

        public static class Data {
            /**
             * countryCode : 86
             * phone : 13689728762
             * sendValidationCode : true
             * checkRegistered : true
             * codeType : registration
             */
            public String countryCode;
            public String phone;
            public String type;
            public String code;
        }
    }

    public static class VALIDATE_PASSWORD {
        /**
         * type : validate_phone
         * data : {"countryCode":"86","phone":"13689728762","sendValidationCode":true,"checkRegistered":true,"codeType":"registration"}
         */
        public String type = VALIDATE_PASSWORD;
        public Data data = new Data();
        public String language;

        public static class Data {
            /**
             * countryCode : 86
             * phone : 13689728762
             * sendValidationCode : true
             * checkRegistered : true
             * codeType : registration
             */
            public String username;
            public String password;
        }

        public static class Response {
            /**
             * code : -1000
             * message : FAIL
             * request_timestamp : 2018-07-30T09:37:49.525
             * response_timestamp : 2018-07-30T09:37:49.538
             * data : ["Template for INSUFFICIENT_UPPERCASE not find","Template for INSUFFICIENT_SPECIAL not find"]
             */
            public int code;
            public String message;
            public String request_timestamp;
            public String response_timestamp;
            public List<String> data;
        }
        public static class Verification {

            /**
             * code : -1000
             * message : The User ID has already existed
             * request_timestamp : 2018-10-31T11:18:03.266
             * response_timestamp : 2018-10-31T11:18:03.305
             * data : ron009
             */

            public int code;
            public String message;
            public String request_timestamp;
            public String response_timestamp;
            public String data;
        }
    }

    public static class SECURITY_QUESTIONS {
        /**
         * type : validate_phone
         * data : {"countryCode":"86","phone":"13689728762","sendValidationCode":true,"checkRegistered":true,"codeType":"registration"}
         */

        public String type = ConsUtils.SECURITY_QUESTIONS;
        public Data data = new Data();
        public String language;

        public static class Data {
            /**
             * countryCode : 86
             * phone : 13689728762
             * sendValidationCode : true
             * checkRegistered : true
             * codeType : registration
             */
        }

        public static class Response {
            public int code;
            public String message;
            public String request_timestamp;
            public String response_timestamp;
            public List<Data> data;
            public String language;

            public static class Data {
                /**
                 * id : 5b5a74c3f32bafbb7fdbdf40
                 * language : en
                 * code : Q001EN
                 * question : What is your mother’s maiden name?
                 */

                public String id;
                public String code;
                public String question;
            }
        }
    }

    public static class MODIFY_EMAIL {

        public String type = ConsUtils.WRAPPY_SEND_EMAIL_CODE;
        public Data data = new Data();

        public static class Data {
        public String email;
        public String type = ConsUtils.WRAPPY_CHANGE_EMAIL;
        }



    }
    public static class VALIDATE_EMAIL {
        /**
         * type : validate_phone
         * data : {"countryCode":"86","phone":"13689728762","sendValidationCode":true,"checkRegistered":true,"codeType":"registration"}
         */

        public String type = ConsUtils.VALIDATE_EMAIL;
        public Data data = new Data();

        public static class Data {
            public String emailAddress;
        }

        public static class Response {
            /**
             * code : 1000
             * message : SUCCESS
             * request_timestamp : 2018-07-30T15:40:13.158
             * response_timestamp : 2018-07-30T15:40:13.163
             * data : 446971874@126.com
             */
            public int status;
            public int code;
            public String message;
            public String request_timestamp;
            public String response_timestamp;
            public Object data;
        }
    }

    public static class FIND_USER_ACCOUNT {
        /**
         * type : validate_phone
         * data : {"countryCode":"86","phone":"13689728762","sendValidationCode":true,"checkRegistered":true,"codeType":"registration"}
         */

        public String type = ConsUtils.FIND_USER_ACCOUNT;
        public Data data = new Data();

        public static class Data {


            /**
             * email : bill@nst.com
             * phone : +8618673290987
             * send_email : true
             * send_sms : false
             */

            public String email;
            public String phone;
            public boolean send_email = true;
            public boolean send_sms = true;
        }

        public static class Response {
            /**
             * code : 1000
             * message : SUCCESS
             * request_timestamp : 2018-07-30T15:40:13.158
             * response_timestamp : 2018-07-30T15:40:13.163
             * data : 446971874@126.com
             */
            public int code;
            public String message;
            public String request_timestamp;
            public String response_timestamp;
            public String data;
        }
    }

    public class Response {


        /**
         * code : 1000
         * message : SUCCESS
         * request_timestamp : 2018-07-28T17:02:43.375
         * response_timestamp : 2018-07-28T17:02:43.543
         * data : {"phone":"+852 6723 7387","region":"HK","code":852,"type":"MOBILE"}
         */

        public int code;
        public String message;
        public String request_timestamp;
        public String response_timestamp;
        public Data data;

        public class Data {
            /**
             * phone : +852 6723 7387
             * region : HK
             * code : 852
             * type : MOBILE
             */

            public String phone;
            public String region;
            public int code;
            public String type;

        }
    }


    public class ResponseTwo {


        /**
         * code : 1000
         * message : SUCCESS
         * request_timestamp : 2018-07-28T17:02:43.375
         * response_timestamp : 2018-07-28T17:02:43.543
         * data : {"phone":"+852 6723 7387","region":"HK","code":852,"type":"MOBILE"}
         */

        public int code;
        public String message;
        public String request_timestamp;
        public String response_timestamp;
        public Data data;

        public class Data {
            /**
             *        "id": "5bc6db83e1670218348d7b7f",
             *         "createdDate": "2018-10-17T14:49:39.168",
             *         "modifiedDate": "2018-10-17T14:49:39.168",
             *         "email": "215640361@qq.com",
             *         "code": null,
             *         "type": "change_email",
             *         "used": 0
             */

            public String id;
            public String createdDate;
            public String modifiedDate;
            public String email;
            public String code;
            public String type;
            public String used;
        }
    }

   /**
   *  修改邮箱验证码
    */
    public static class sendModifiedMailboxVerificationCode{
       /**
        * "type":"validate_email_code",
        */
        public String type = ConsUtils.VALIDATE_EMAIL_CODE;
        public Data data = new Data();

       public class Data {

           /**
            *  "email":"215640361@qq.com",
            *  "type":"change_email",
            *   "code":"93608"
            */

           public String email;
           public String type = ConsUtils.WRAPPY_CHANGE_EMAIL;
           public String code;
       }

   }


    /**
     *  重新设置密码传的参数
     */
    public static class sendModifyPasswordData {

        public String newPassword;//新密码
        public String newPatternPassword;//新手势密码
        public String secretKey;
    }
    /**
     *
     */
    public static class errorData {

        public String error;//
        public String error_description;
    }



    public static class sendRecoveryEmail{
        /**
         * "email": "pxbiao@qq.com",
         * "type": "reset_pattern"
         */
        public String email;
        public String type = ConsUtils.VALIDATE_RESET_PATTERN;

    }


    /**
     *  更新用户信息成功返回正确数的数据
     */
    public static class updateUserInformation{


        /**
         * code : 1000
         * message : SUCCESS
         * request_timestamp : 2018-09-11T12:52:19.403
         * response_timestamp : 2018-09-11T12:52:19.615
         * data : {"id":"5b961c79e83789fbb063b6ef","createdDate":"2018-09-10T15:25:45.573","modifiedDate":"2018-09-10T15:25:45.573","firstName":"Bill091001","middleName":"Crab091001","lastName":"Lee091001","nickName":"ClimbCrabtets","dateOfBirth":"2018-06-25","email":"pxbiawwww1@qq.com","mobilePhone":"+86168000000444401","homePhone":"0769-78423421","address1":"DongGuan shatian","address2":"Changsha jingxiuhuayuan","city":"DG","state":"GD","country":"CN","zip":"3243413","gender":"FEMALE","extendedInfo":{"id":"5b961c79e83789fbb063b6ee","createdDate":"2018-09-10T15:25:40.789","modifiedDate":"2018-09-10T15:25:40.789","username":"bill091001","password":"{bcrypt}$2a$10$QQX7OYpZpvLUsKGn9cLkOOIxx.7OB9asGECBH3vBssxn7pXtrPQU.","patternPassword":null,"patternPasswordFlag":"OFF","clearPassword":null,"status":1,"accountType":"WRAPPY","role":"USER","referralCode":"fffa6c3f86e0439b99b3d16ca9cab38e","language":"en","server":"ejabberd-test.newsupplytech.com","passwordFailedAttempt":0,"passwordChangedDate":"2018-09-10T15:25:40.789","avatar":null,"backgroundImage":null},"securityQuestions":null}
         */

        public int code;
        public String message;
        public String request_timestamp;
        public String response_timestamp;
        public DataBean data = new DataBean();

        public static class DataBean {
            /**
             * id : 5b961c79e83789fbb063b6ef
             * createdDate : 2018-09-10T15:25:45.573
             * modifiedDate : 2018-09-10T15:25:45.573
             * firstName : Bill091001
             * middleName : Crab091001
             * lastName : Lee091001
             * nickName : ClimbCrabtets
             * dateOfBirth : 2018-06-25
             * email : pxbiawwww1@qq.com
             * mobilePhone : +86168000000444401
             * homePhone : 0769-78423421
             * address1 : DongGuan shatian
             * address2 : Changsha jingxiuhuayuan
             * city : DG
             * state : GD
             * country : CN
             * zip : 3243413
             * gender : FEMALE
             * extendedInfo : {"id":"5b961c79e83789fbb063b6ee","createdDate":"2018-09-10T15:25:40.789","modifiedDate":"2018-09-10T15:25:40.789","username":"bill091001","password":"{bcrypt}$2a$10$QQX7OYpZpvLUsKGn9cLkOOIxx.7OB9asGECBH3vBssxn7pXtrPQU.","patternPassword":null,"patternPasswordFlag":"OFF","clearPassword":null,"status":1,"accountType":"WRAPPY","role":"USER","referralCode":"fffa6c3f86e0439b99b3d16ca9cab38e","language":"en","server":"ejabberd-test.newsupplytech.com","passwordFailedAttempt":0,"passwordChangedDate":"2018-09-10T15:25:40.789","avatar":null,"backgroundImage":null}
             * securityQuestions : null
             */

            public String id;
            public String createdDate;
            public String modifiedDate;
            public String firstName;
            public String middleName;
            public String lastName;
            public String nickName;
            public String dateOfBirth;
            public String email;
            public String mobilePhone;
            public String homePhone;
            public String address1;
            public String address2;
            public String city;
            public String state;
            public String country;
            public String zip;
            public String gender;
            public DataBean.ExtendedInfoBean extendedInfo = new ExtendedInfoBean();
            public String securityQuestions;

            public static class ExtendedInfoBean {
                /**
                 * id : 5b961c79e83789fbb063b6ee
                 * createdDate : 2018-09-10T15:25:40.789
                 * modifiedDate : 2018-09-10T15:25:40.789
                 * username : bill091001
                 * password : {bcrypt}$2a$10$QQX7OYpZpvLUsKGn9cLkOOIxx.7OB9asGECBH3vBssxn7pXtrPQU.
                 * patternPassword : null
                 * patternPasswordFlag : OFF
                 * clearPassword : null
                 * status : 1
                 * accountType : WRAPPY
                 * role : USER
                 * referralCode : fffa6c3f86e0439b99b3d16ca9cab38e
                 * language : en
                 * server : ejabberd-test.newsupplytech.com
                 * passwordFailedAttempt : 0
                 * passwordChangedDate : 2018-09-10T15:25:40.789
                 * avatar : null
                 * backgroundImage : null
                 */

                public String id;
                public String createdDate;
                public String modifiedDate;
                public String username;
                public String password;
                public Object patternPassword;
                public String patternPasswordFlag;
                public String clearPassword;
                public int status;
                public String accountType;
                public String role;
                public String referralCode;
                public String language;
                public String server;
                public String passwordFailedAttempt;
                public String passwordChangedDate;
                public String avatar;
                public String backgroundImage;
            }
        }


    }



}
