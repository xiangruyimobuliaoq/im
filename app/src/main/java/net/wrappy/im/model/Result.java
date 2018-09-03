package net.wrappy.im.model;

/**
 * 创建者     彭龙
 * 创建时间   2018/8/17 下午2:44
 * 描述	      ${TODO}
 * <p>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class Result<T> {
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
    public T data;
    public String timestamp;
    public int status;
    public String error;
    public String path;
}
