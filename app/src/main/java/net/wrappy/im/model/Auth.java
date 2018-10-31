package net.wrappy.im.model;

import java.io.Serializable;

/**
 * 创建者     彭龙
 * 创建时间   2018/7/9 下午4:10
 * 描述	      ${TODO}
 * <p>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class Auth implements Serializable {

    /**
     * access_token : eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJkeWwxMjM0NTY3Iiwic2NvcGUiOlsicmVhZCIsIndyaXRlIiwidHJ1c3QiXSwiZW5jcnlwdGlvbmtleSI6IkUxRjI0OUIyQkU5RUVCRDQ3NEE0MkIzOTVCN0VFMTNCMENBODM5Rjg2RTMiLCJleHAiOjE1MzExMjc4NDUsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiJjZDU1NDAxYi1lNjM3LTRjYmEtYTgxNS04NWViNzExMGM1ZjgiLCJhY2NvdW50Ijp7ImlkIjoiNWIzYzdiNjhjN2MxNzIyZjBlODFkMDExIiwiY3JlYXRlZERhdGUiOnsieWVhciI6MjAxOCwibW9udGhWYWx1ZSI6NywibW9udGgiOiJKVUxZIiwiZGF5T2ZNb250aCI6NCwiZGF5T2ZZZWFyIjoxODUsImRheU9mV2VlayI6IldFRE5FU0RBWSIsImhvdXIiOjcsIm1pbnV0ZSI6NDYsInNlY29uZCI6NDgsIm5hbm8iOjMzNjAwMDAwMCwiY2hyb25vbG9neSI6eyJjYWxlbmRhclR5cGUiOiJpc284NjAxIiwiaWQiOiJJU08ifX0sIm1vZGlmaWVkRGF0ZSI6eyJ5ZWFyIjoyMDE4LCJtb250aFZhbHVlIjo3LCJtb250aCI6IkpVTFkiLCJkYXlPZk1vbnRoIjo0LCJkYXlPZlllYXIiOjE4NSwiZGF5T2ZXZWVrIjoiV0VETkVTREFZIiwiaG91ciI6NywibWludXRlIjo0Niwic2Vjb25kIjo0OCwibmFubyI6MzM2MDAwMDAwLCJjaHJvbm9sb2d5Ijp7ImNhbGVuZGFyVHlwZSI6Imlzbzg2MDEiLCJpZCI6IklTTyJ9fSwiZmlyc3ROYW1lIjoiQWxleCBkeWwxMjM0NTY3IiwibWlkZGxlTmFtZSI6ImR5bDEyMzQ1NjciLCJsYXN0TmFtZSI6ImR5bDEyMzQ1NjciLCJuaWNrTmFtZSI6IkdvZ2xkZW4gZHlsMTIzNDU2NyIsImRhdGVPZkJpcnRoIjp7InllYXIiOjIwMTgsIm1vbnRoIjoiQVBSSUwiLCJtb250aFZhbHVlIjo0LCJkYXlPZk1vbnRoIjoyNywiZGF5T2ZZZWFyIjoxMTcsImRheU9mV2VlayI6IkZSSURBWSIsImNocm9ub2xvZ3kiOnsiY2FsZW5kYXJUeXBlIjoiaXNvODYwMSIsImlkIjoiSVNPIn0sImVyYSI6IkNFIiwibGVhcFllYXIiOmZhbHNlfSwiZW1haWwiOiJkeWwxMjM0NTY3LmFndWVsQGdtYWlsbC5jb20iLCJtb2JpbGVQaG9uZSI6bnVsbCwiaG9tZVBob25lIjoiKDAzMikgNDE0NDEzOCIsImFkZHJlc3MxIjoiMjc0MSBFbCBHdXN0byBEcml2ZSIsImFkZHJlc3MyIjoiUy4gQ2FiYWh1ZyBTVHJlZXQsIE1hYm9sbyIsImNpdHkiOiJDZWJ1Iiwic3RhdGUiOiJDZWJ1IiwiY291bnRyeSI6IlBISSIsInppcCI6IjYwMCIsImV4dGVuZGVkSW5mbyI6eyJpZCI6IjViM2M3YjY4YzdjMTcyMmYwZTgxZDAxMCIsImNyZWF0ZWREYXRlIjp7InllYXIiOjIwMTgsIm1vbnRoVmFsdWUiOjcsIm1vbnRoIjoiSlVMWSIsImRheU9mTW9udGgiOjQsImRheU9mWWVhciI6MTg1LCJkYXlPZldlZWsiOiJXRURORVNEQVkiLCJob3VyIjo3LCJtaW51dGUiOjQ2LCJzZWNvbmQiOjQ4LCJuYW5vIjozMzIwMDAwMDAsImNocm9ub2xvZ3kiOnsiY2FsZW5kYXJUeXBlIjoiaXNvODYwMSIsImlkIjoiSVNPIn19LCJtb2RpZmllZERhdGUiOnsieWVhciI6MjAxOCwibW9udGhWYWx1ZSI6NywibW9udGgiOiJKVUxZIiwiZGF5T2ZNb250aCI6NCwiZGF5T2ZZZWFyIjoxODUsImRheU9mV2VlayI6IldFRE5FU0RBWSIsImhvdXIiOjcsIm1pbnV0ZSI6NDYsInNlY29uZCI6NDgsIm5hbm8iOjMzMjAwMDAwMCwiY2hyb25vbG9neSI6eyJjYWxlbmRhclR5cGUiOiJpc284NjAxIiwiaWQiOiJJU08ifX0sInVzZXJuYW1lIjoiZHlsMTIzNDU2NyIsInBhc3N3b3JkIjoiJDJhJDEwJG1HMWJMUjZmT0cwTGU5VU9WakguamVVM3BiWEMxSVpCV0JIajBBQ1pyc0pFcTZ5NnJPdzMuIiwic3RhdHVzIjoxLCJhY2NvdW50VHlwZSI6IldSQVBQWSIsInJvbGUiOiJVU0VSIiwicmVmZXJyYWxDb2RlIjoiMDNhOGE5NmU1YWU4NGRhZTg1NjZiNTdkZjNmNzkzZDUiLCJsYW5ndWFnZSI6ImVuIiwic2VydmVyIjoiZ2xvYmFsLndyYXBweS5uZXQiLCJwYXNzd29yZEZhaWxlZEF0dGVtcHQiOjAsInBhc3N3b3JkQ2hhbmdlZERhdGUiOnsieWVhciI6MjAxOCwibW9udGhWYWx1ZSI6NywibW9udGgiOiJKVUxZIiwiZGF5T2ZNb250aCI6NCwiZGF5T2ZZZWFyIjoxODUsImRheU9mV2VlayI6IldFRE5FU0RBWSIsImhvdXIiOjcsIm1pbnV0ZSI6NDYsInNlY29uZCI6NDgsIm5hbm8iOjMzMjAwMDAwMCwiY2hyb25vbG9neSI6eyJjYWxlbmRhclR5cGUiOiJpc284NjAxIiwiaWQiOiJJU08ifX19fSwiY2xpZW50X2lkIjoid3JhcHB5LWludGVybmFsIn0.k1CzUKL_Tqx5n8im_CvAtt3CNoWuZLM9dnuAVVae1KWYIJ63UsmSvva6ygCpmHvnSC3NadLJoCzTjhbtuMTEdmQH0H4KFzn06fSWCV8G_cM-ZNseXM0OIftEFXucgv5BUmhMflHyyHAfCoKztmMGASLmsBiL18tlkv_B9WSB6gDSRzCdcVG5p9ZQfCAquzBa5X6YO1zsQZUtDsDNQG_wQLPR1_UkLvW7xapVgT_i6YGwGeAklaZNajwSl8E1gAMosQDlIWYyCvXFTjuhZENGqzUd2xcrHiIA7X3h8RAhFygYJ17wgxMRl-yw642VPHzoCBqKhXPPK0jupqDA6VM7vg
     * token_type : bearer
     * refresh_token : eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJkeWwxMjM0NTY3Iiwic2NvcGUiOlsicmVhZCIsIndyaXRlIiwidHJ1c3QiXSwiYXRpIjoiY2Q1NTQwMWItZTYzNy00Y2JhLWE4MTUtODVlYjcxMTBjNWY4IiwiZW5jcnlwdGlvbmtleSI6IkUxRjI0OUIyQkU5RUVCRDQ3NEE0MkIzOTVCN0VFMTNCMENBODM5Rjg2RTMiLCJleHAiOjE1MzExMzE0NDUsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiJhNTIwYTUzZS02MzI4LTQ2N2YtOGFlOS1lOWI2MThmZWE4NWIiLCJhY2NvdW50Ijp7ImlkIjoiNWIzYzdiNjhjN2MxNzIyZjBlODFkMDExIiwiY3JlYXRlZERhdGUiOnsieWVhciI6MjAxOCwibW9udGhWYWx1ZSI6NywibW9udGgiOiJKVUxZIiwiZGF5T2ZNb250aCI6NCwiZGF5T2ZZZWFyIjoxODUsImRheU9mV2VlayI6IldFRE5FU0RBWSIsImhvdXIiOjcsIm1pbnV0ZSI6NDYsInNlY29uZCI6NDgsIm5hbm8iOjMzNjAwMDAwMCwiY2hyb25vbG9neSI6eyJjYWxlbmRhclR5cGUiOiJpc284NjAxIiwiaWQiOiJJU08ifX0sIm1vZGlmaWVkRGF0ZSI6eyJ5ZWFyIjoyMDE4LCJtb250aFZhbHVlIjo3LCJtb250aCI6IkpVTFkiLCJkYXlPZk1vbnRoIjo0LCJkYXlPZlllYXIiOjE4NSwiZGF5T2ZXZWVrIjoiV0VETkVTREFZIiwiaG91ciI6NywibWludXRlIjo0Niwic2Vjb25kIjo0OCwibmFubyI6MzM2MDAwMDAwLCJjaHJvbm9sb2d5Ijp7ImNhbGVuZGFyVHlwZSI6Imlzbzg2MDEiLCJpZCI6IklTTyJ9fSwiZmlyc3ROYW1lIjoiQWxleCBkeWwxMjM0NTY3IiwibWlkZGxlTmFtZSI6ImR5bDEyMzQ1NjciLCJsYXN0TmFtZSI6ImR5bDEyMzQ1NjciLCJuaWNrTmFtZSI6IkdvZ2xkZW4gZHlsMTIzNDU2NyIsImRhdGVPZkJpcnRoIjp7InllYXIiOjIwMTgsIm1vbnRoIjoiQVBSSUwiLCJtb250aFZhbHVlIjo0LCJkYXlPZk1vbnRoIjoyNywiZGF5T2ZZZWFyIjoxMTcsImRheU9mV2VlayI6IkZSSURBWSIsImNocm9ub2xvZ3kiOnsiY2FsZW5kYXJUeXBlIjoiaXNvODYwMSIsImlkIjoiSVNPIn0sImVyYSI6IkNFIiwibGVhcFllYXIiOmZhbHNlfSwiZW1haWwiOiJkeWwxMjM0NTY3LmFndWVsQGdtYWlsbC5jb20iLCJtb2JpbGVQaG9uZSI6bnVsbCwiaG9tZVBob25lIjoiKDAzMikgNDE0NDEzOCIsImFkZHJlc3MxIjoiMjc0MSBFbCBHdXN0byBEcml2ZSIsImFkZHJlc3MyIjoiUy4gQ2FiYWh1ZyBTVHJlZXQsIE1hYm9sbyIsImNpdHkiOiJDZWJ1Iiwic3RhdGUiOiJDZWJ1IiwiY291bnRyeSI6IlBISSIsInppcCI6IjYwMCIsImV4dGVuZGVkSW5mbyI6eyJpZCI6IjViM2M3YjY4YzdjMTcyMmYwZTgxZDAxMCIsImNyZWF0ZWREYXRlIjp7InllYXIiOjIwMTgsIm1vbnRoVmFsdWUiOjcsIm1vbnRoIjoiSlVMWSIsImRheU9mTW9udGgiOjQsImRheU9mWWVhciI6MTg1LCJkYXlPZldlZWsiOiJXRURORVNEQVkiLCJob3VyIjo3LCJtaW51dGUiOjQ2LCJzZWNvbmQiOjQ4LCJuYW5vIjozMzIwMDAwMDAsImNocm9ub2xvZ3kiOnsiY2FsZW5kYXJUeXBlIjoiaXNvODYwMSIsImlkIjoiSVNPIn19LCJtb2RpZmllZERhdGUiOnsieWVhciI6MjAxOCwibW9udGhWYWx1ZSI6NywibW9udGgiOiJKVUxZIiwiZGF5T2ZNb250aCI6NCwiZGF5T2ZZZWFyIjoxODUsImRheU9mV2VlayI6IldFRE5FU0RBWSIsImhvdXIiOjcsIm1pbnV0ZSI6NDYsInNlY29uZCI6NDgsIm5hbm8iOjMzMjAwMDAwMCwiY2hyb25vbG9neSI6eyJjYWxlbmRhclR5cGUiOiJpc284NjAxIiwiaWQiOiJJU08ifX0sInVzZXJuYW1lIjoiZHlsMTIzNDU2NyIsInBhc3N3b3JkIjoiJDJhJDEwJG1HMWJMUjZmT0cwTGU5VU9WakguamVVM3BiWEMxSVpCV0JIajBBQ1pyc0pFcTZ5NnJPdzMuIiwic3RhdHVzIjoxLCJhY2NvdW50VHlwZSI6IldSQVBQWSIsInJvbGUiOiJVU0VSIiwicmVmZXJyYWxDb2RlIjoiMDNhOGE5NmU1YWU4NGRhZTg1NjZiNTdkZjNmNzkzZDUiLCJsYW5ndWFnZSI6ImVuIiwic2VydmVyIjoiZ2xvYmFsLndyYXBweS5uZXQiLCJwYXNzd29yZEZhaWxlZEF0dGVtcHQiOjAsInBhc3N3b3JkQ2hhbmdlZERhdGUiOnsieWVhciI6MjAxOCwibW9udGhWYWx1ZSI6NywibW9udGgiOiJKVUxZIiwiZGF5T2ZNb250aCI6NCwiZGF5T2ZZZWFyIjoxODUsImRheU9mV2VlayI6IldFRE5FU0RBWSIsImhvdXIiOjcsIm1pbnV0ZSI6NDYsInNlY29uZCI6NDgsIm5hbm8iOjMzMjAwMDAwMCwiY2hyb25vbG9neSI6eyJjYWxlbmRhclR5cGUiOiJpc284NjAxIiwiaWQiOiJJU08ifX19fSwiY2xpZW50X2lkIjoid3JhcHB5LWludGVybmFsIn0.OOe3ZL4_gdJr9XngouMtsDy4fRW5jKzN32jN9hDpYOwO0OXdMXF-YZKRMbmn4klWkwr9YHT46fkxOGoO8qTpm87-QAKN-Y4FZtEifVAsIWnvxqU0t9srS0ZUHR6XmExds2Swd_CwgaUTPhKJ8BYAfcbKtQR-YbGe3fLiC7gDGOJIFJtsC9j5tBY2kpRYRzSMeq4VDt4nbGM6X9WilPIvgxbfcNqyPnYw_29QEl3fNBvIf5qY3GX_S4KZlreWcrQdnu8QH497FQj2N2yI6taiuERarmdSN8A7RgAcPZGt4yT937Kw3WXquSuEPwR99GixCyxYLkbsydm5VdkBxvAv2Q
     * expires_in : 3599
     * scope : read write trust
     * encryptionkey : E1F249B2BE9EEBD474A42B395B7EE13B0CA839F86E3
     * account : {"id":"5b3c7b68c7c1722f0e81d011","createdDate":"2018-07-04T07:46:48.336","modifiedDate":"2018-07-04T07:46:48.336","firstName":"Alex dyl1234567","middleName":"dyl1234567","lastName":"dyl1234567","nickName":"Goglden dyl1234567","dateOfBirth":"2018-04-27","email":"dyl1234567.aguel@gmaill.com","mobilePhone":null,"homePhone":"(032) 4144138","address1":"2741 El Gusto Drive","address2":"S. Cabahug STreet, Mabolo","city":"Cebu","state":"Cebu","country":"PHI","zip":"600","extendedInfo":{"id":"5b3c7b68c7c1722f0e81d010","createdDate":"2018-07-04T07:46:48.332","modifiedDate":"2018-07-04T07:46:48.332","username":"dyl1234567","password":"$2a$10$mG1bLR6fOG0Le9UOVjH.jeU3pbXC1IZBWBHj0ACZrsJEq6y6rOw3.","status":1,"accountType":"WRAPPY","role":"USER","referralCode":"03a8a96e5ae84dae8566b57df3f793d5","language":"en","server":"global.wrappy.net","passwordFailedAttempt":0,"passwordChangedDate":"2018-07-04T07:46:48.332"}}
     * jti : cd55401b-e637-4cba-a815-85eb7110c5f8
     */

    public String access_token;
    public String token_type;
    public String refresh_token;
    public int expires_in;
    public String scope;
    public String encryptionkey;
    public Account account = new Account();
    public String jti;
    public String patternPassword;
    public String error_description;
//    public Aterror error_description = new Aterror();
    public String error;

    public static class Account implements Serializable {
        /**
         * id : 5b3c7b68c7c1722f0e81d011
         * createdDate : 2018-07-04T07:46:48.336
         * modifiedDate : 2018-07-04T07:46:48.336
         * firstName : Alex dyl1234567
         * middleName : dyl1234567
         * lastName : dyl1234567
         * nickName : Goglden dyl1234567
         * dateOfBirth : 2018-04-27
         * email : dyl1234567.aguel@gmaill.com
         * mobilePhone : null
         * homePhone : (032) 4144138
         * address1 : 2741 El Gusto Drive
         * address2 : S. Cabahug STreet, Mabolo
         * city : Cebu
         * state : Cebu
         * country : PHI
         * zip : 600
         * extendedInfo : {"id":"5b3c7b68c7c1722f0e81d010","createdDate":"2018-07-04T07:46:48.332","modifiedDate":"2018-07-04T07:46:48.332","username":"dyl1234567","password":"$2a$10$mG1bLR6fOG0Le9UOVjH.jeU3pbXC1IZBWBHj0ACZrsJEq6y6rOw3.","status":1,"accountType":"WRAPPY","role":"USER","referralCode":"03a8a96e5ae84dae8566b57df3f793d5","language":"en","server":"global.wrappy.net","passwordFailedAttempt":0,"passwordChangedDate":"2018-07-04T07:46:48.332"}
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
        public ExtendedInfo extendedInfo = new ExtendedInfo();
        public String gender;

        public static class ExtendedInfo implements Serializable {
            /**
             * id : 5b3c7b68c7c1722f0e81d010
             * createdDate : 2018-07-04T07:46:48.332
             * modifiedDate : 2018-07-04T07:46:48.332
             * username : dyl1234567
             * password : $2a$10$mG1bLR6fOG0Le9UOVjH.jeU3pbXC1IZBWBHj0ACZrsJEq6y6rOw3.
             * status : 1
             * accountType : WRAPPY
             * role : USER
             * referralCode : 03a8a96e5ae84dae8566b57df3f793d5
             * language : en
             * server : global.wrappy.net
             * passwordFailedAttempt : 0
             * passwordChangedDate : 2018-07-04T07:46:48.332
             */

            public String id;
            public String createdDate;
            public String modifiedDate;
            public String username;
            public String password;
            public int status;
            public String accountType;
            public String role;
            public String patternPassword;
            public String language;
            public String server;
            public String avatar;
//            public String backGroundImage;
            public String backgroundImage;
            public String referralCode;
            public int passwordFailedAttempt;
            public String passwordChangedDate;
        }
    }
}
