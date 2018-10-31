package net.wrappy.im.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建者     彭龙
 * 创建时间   2018/7/28 下午6:08
 * 描述	      ${TODO}
 * <p>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class Register implements Serializable {


    /**
     * firstName : Teste01
     * middleName : Crab01
     * lastName : Lee01
     * nickName : 战士01
     * dateOfBirth : 2018-07-10
     * email : libiaolee01@gmail.com
     * mobilePhone : +8618122012501
     * homePhone : 0769-78423401
     * address1 : DongGuan shatian
     * address2 : Changsha jingxiuhuayuan
     * city : DG
     * state : GD
     * country : CN
     * zip : 3201413
     * extendedInfo : {"username":"Test01","password":"NST@100%test","language":"en","server":"ejabberd-test.newsupplytech.com"}
     * securityQuestions : [{"question":"where are you from","code":"Q002CN","answer":"China"},{"question":"what's you favorite food","code":"Q001CN","answer":"Apple"},{"question":"what's your first school name","code":"Q005CN","answer":"Xujia school"}]
     */

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
    public List<SecurityQuestions> securityQuestions = new ArrayList<>();
    public String gender;

    public static class ExtendedInfo implements Serializable {
        /**
         * username : Test01
         * password : NST@100%test
         * language : en
         * server : ejabberd-test.newsupplytech.com
         */

        public String username;
        public String password;
        public String patternPassword;
        public String language;
        public String server;
        public String avatar;  //头像不能够为空
        public String backgroundImage;//背景可以为空
    }

    public static class SecurityQuestions implements Serializable {
        /**
         * question : where are you from
         * code : Q002CN
         * answer : China
         */

        public String question;
        public String code;
        public String answer;

        public SecurityQuestions(String code, String question, String answer) {
            this.code = code;
            this.question = question;
            this.answer = answer;
        }
    }
}
