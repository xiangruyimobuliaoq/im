package net.wrappy.im.model;

/**
 * 创建者     彭龙
 * 创建时间   2018/9/13 18:41
 * 描述	      ${TODO}
 * <p>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class UserNameStatus {


    /**
     * code : 1000
     * message : SUCCESS
     * request_timestamp : 2018-08-28T11:10:39.986
     * response_timestamp : 2018-08-28T11:10:40.581
     * data : {"id":"5b7e124fa7409dce9c0c94dc","createdDate":"2018-08-23T09:47:59.81","modifiedDate":"2018-08-27T17:40:07.505","username":"bill008","status":"NORMAL","count":3,"lockLeftSeconds":0}
     */

    public int code;
    public String message;
    public String request_timestamp;
    public String response_timestamp;
    public DataBean data = new DataBean();

    public static class DataBean {
        /**
         * id : 5b7e124fa7409dce9c0c94dc
         * createdDate : 2018-08-23T09:47:59.81
         * modifiedDate : 2018-08-27T17:40:07.505
         * username : bill008
         * status : NORMAL
         * count : 3
         * lockLeftSeconds : 0
         */

        public String id;
        public String createdDate;
        public String modifiedDate;
        public String username;
        public String status;
        public int count;
        public long lockLeftSeconds;
    }

}
