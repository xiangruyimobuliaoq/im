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

    private int code;
    private String message;
    private String request_timestamp;
    private String response_timestamp;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequest_timestamp() {
        return request_timestamp;
    }

    public void setRequest_timestamp(String request_timestamp) {
        this.request_timestamp = request_timestamp;
    }

    public String getResponse_timestamp() {
        return response_timestamp;
    }

    public void setResponse_timestamp(String response_timestamp) {
        this.response_timestamp = response_timestamp;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

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

        private String id;
        private String createdDate;
        private String modifiedDate;
        private String username;
        private String status;
        private int count;
        private int lockLeftSeconds;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getModifiedDate() {
            return modifiedDate;
        }

        public void setModifiedDate(String modifiedDate) {
            this.modifiedDate = modifiedDate;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getLockLeftSeconds() {
            return lockLeftSeconds;
        }

        public void setLockLeftSeconds(int lockLeftSeconds) {
            this.lockLeftSeconds = lockLeftSeconds;
        }
    }
}
