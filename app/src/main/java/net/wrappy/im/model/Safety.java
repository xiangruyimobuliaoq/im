package net.wrappy.im.model;

import java.util.List;

/**
 * 创建者     彭龙
 * 创建时间   2018/9/12 16:19
 * 描述	      ${TODO}
 * <p>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class Safety {


    /**
     * code : 1000
     * message : SUCCESS
     * request_timestamp : 2018-09-12T17:10:36.337
     * response_timestamp : 2018-09-12T17:10:36.377
     * data : [{"id":"5b971a019a54e60b5e0bfafd","createdDate":null,"modifiedDate":null,"accountId":"5b971a019a54e60b5e0bfafc","code":"Q001EN","question":"What is your mother\u2019s maiden name?","answer":null},{"id":"5b971a019a54e60b5e0bfafe","createdDate":null,"modifiedDate":null,"accountId":"5b971a019a54e60b5e0bfafc","code":"Q002EN","question":"What is your favorite food?","answer":null}]
     */

    private int code;
    private String message;
    private String request_timestamp;
    private String response_timestamp;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 5b971a019a54e60b5e0bfafd
         * createdDate : null
         * modifiedDate : null
         * accountId : 5b971a019a54e60b5e0bfafc
         * code : Q001EN
         * question : What is your mother’s maiden name?
         * answer : null
         */

        private String id;
        private Object createdDate;
        private Object modifiedDate;
        private String accountId;
        private String code;
        private String question;
        private Object answer;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Object getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(Object createdDate) {
            this.createdDate = createdDate;
        }

        public Object getModifiedDate() {
            return modifiedDate;
        }

        public void setModifiedDate(Object modifiedDate) {
            this.modifiedDate = modifiedDate;
        }

        public String getAccountId() {
            return accountId;
        }

        public void setAccountId(String accountId) {
            this.accountId = accountId;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public Object getAnswer() {
            return answer;
        }

        public void setAnswer(Object answer) {
            this.answer = answer;
        }
    }
}
