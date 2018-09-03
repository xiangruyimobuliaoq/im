package net.wrappy.im.model;

import net.wrappy.im.contants.ConsUtils;

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
}
