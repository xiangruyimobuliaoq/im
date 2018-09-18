package net.wrappy.im.model;

/**
 * 创建者     彭龙
 * 创建时间   2018/9/12 17:55
 * 描述	      ${TODO}
 * <p>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class ValidationProblem {


    /**
     * code : 1000
     * message : SUCCESS
     * request_timestamp : 2018-08-02T17:29:11.734
     * response_timestamp : 2018-08-02T17:29:11.893
     * data : 5b5edb40fcdfb088f420c78f
     */

    private int code;
    private String message;
    private String request_timestamp;
    private String response_timestamp;
    private String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
