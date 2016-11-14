package com.hxp.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/7/11.
 */
    public class User implements Serializable {

    /**用户ID*/
        private Integer id;
    /**用户名称*/
        private String username;
    /**用户登陆名*/
        private  String loginName;
    /**用户密码*/
        private String password;
    /** 性别,1:男,2:女,3:未知 */
        private Integer gender;
    /** 用户身份证号码*/
        private String idCardNo;
    /**用户最后登陆时间*/
        private String lastLoginTime;
    /**用户登陆时间*/
        private String loginTime;
    /**用户最后登陆IP*/
        private String lastLoginIp;
    /**用户IP*/
        private String ip;
    /**用户邮件*/
        private String email;
    /**用户手机号*/
        private String mobilePhone;
    /**用户状态*/
        private Integer status;
    /**用户创建时间*/
        private String createTime;
    /** 头像 */
        private String headUrl;
    /** 用户类型,1:老板,2:经理,3：职员*/
        private Integer type;


        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }




        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getLastLoginTime() {
            return lastLoginTime;
        }

        public void setLastLoginTime(String lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
        }

        public String getLoginTime() {
            return loginTime;
        }

        public void setLoginTime(String loginTime) {
            this.loginTime = loginTime;
        }

        public String getLastLoginIp() {
            return lastLoginIp;
        }

        public void setLastLoginIp(String lastLoginIp) {
            this.lastLoginIp = lastLoginIp;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobilePhone() {
            return mobilePhone;
        }

        public void setMobilePhone(String mobilePhone) {
            this.mobilePhone = mobilePhone;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
