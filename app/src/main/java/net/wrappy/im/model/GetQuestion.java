package net.wrappy.im.model;

/**
 * 创建者     彭龙
 * 创建时间   2018/8/17 下午3:32
 * 描述	      ${TODO}
 * <p>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class GetQuestion {
    /**
     * type : security_questions
     * data : {"language":"en"}
     */
    public String type;
    public Data data = new Data();

    public static class Data {
        /**
         * language : en
         */
        public String language;
    }
}
