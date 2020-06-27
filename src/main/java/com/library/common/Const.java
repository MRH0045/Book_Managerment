package com.library.common;

public class Const {


    /**
         * @Description 图书归还状态
         * @Date 18:49 2020/6/27
         **/

    public static final Integer  UNRETURN = 1;

    public static final Integer  RETURNED = 0;

    /**
         * @Description  用户角色
         * @Date 17:59 2020/6/23
         **/
    public static final String  SYSMANAGER = "sysManager";

    public static final String LIBMANAGER = "LibManage";

    public static final String USER = "user";

    public static final String PASSWORD = "It's a secret!";


    /**
         * @Description  分页
         * @Date 18:01 2020/6/23
         **/

    public static final Integer DEFAULT_PAGE = 1;

    public static final Integer DEFAULT_PAGE_SIZE = 10;


    /**
     * @Description Token
     * @Date 18:01 2020/6/23
     **/
    public static final String TOKEN = "Authentication";

    public static final String TOKEN_CACHE_PREFIX = "excellent.cache.token";


    /**
         * @Author MRH0045
         * @Description  用户状态
         * @Date 18:01 2020/6/23
         **/

    public enum  STATUS{

        /**
         * 用户状态
         */
        NORMAL(0,"正常"),
        PROHIBITION(1,"已封禁");

        private Integer status;
        private String desc;

        STATUS(int status, String desc) {
            this.status = status;
            this.desc = desc;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getStatus() {
            return status;
        }

        public String getDesc() {
            return desc;
        }


    }
}
