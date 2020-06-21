package com.library.common;

public class Const {


    public static final String Manager = "manage";

    public static final Integer DEFAULT_PAGE = 1;

    public static final Integer DEFAULT_PAGE_SIZE = 5;

    public static final String USER = "user";
    public enum  STATUS{

        /**
         * 帖子状态
         */
        NEED_EXAMINE_POST(1,"需要审核帖子"),
        NORMAL_POST(0,"正常帖子"),
        FAIL_PASS_POST(2,"未通过审核的帖子"),

        /**
         * 用户状态
         */
        NORMAL(0,"正常"),
        PROHIBITION(1,"已封禁"),
        NEED_INFO(2,"未完善信息");



        private int status;
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