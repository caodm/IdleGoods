package com.cdm.idlefish.entity;

/**
 * Created by user on 2016/10/20.
 */
public class User {

    private String name;//用户名
    private int id; //用户id
    private String password; //密码
    private String userIcon; //用户头像
    private Object nickName;//昵称
    private Object loginName;//用户名
    private int usersex;//性别: 0 : 男，1：女
    private String token; //token

    private Object roles; //可以为空
    private Object qquid; //可以为空
    private Object wechatuid;//可以为空
    private Object microbloguid;//可以为空
    private Object salt; //可以为空
    private Object registerDate;//可以为空
    private Object openid;//可以为空
    private Object bindWechat;//可以为空

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }


    public Object getNickName() {
        return nickName;
    }

    public void setNickName(Object nickName) {
        this.nickName = nickName;
    }




    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Object getRoles() {
        return roles;
    }

    public void setRoles(Object roles) {
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Object getQquid() {
        return qquid;
    }

    public void setQquid(Object qquid) {
        this.qquid = qquid;
    }

    public Object getWechatuid() {
        return wechatuid;
    }

    public void setWechatuid(Object wechatuid) {
        this.wechatuid = wechatuid;
    }

    public Object getMicrobloguid() {
        return microbloguid;
    }

    public void setMicrobloguid(Object microbloguid) {
        this.microbloguid = microbloguid;
    }


    public Object getLoginName() {
        return loginName;
    }

    public void setLoginName(Object loginName) {
        this.loginName = loginName;
    }

    public Object getSalt() {
        return salt;
    }

    public void setSalt(Object salt) {
        this.salt = salt;
    }

    public Object getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Object registerDate) {
        this.registerDate = registerDate;
    }

    public Object getOpenid() {
        return openid;
    }

    public void setOpenid(Object openid) {
        this.openid = openid;
    }

    public Object getBindWechat() {
        return bindWechat;
    }

    public void setBindWechat(Object bindWechat) {
        this.bindWechat = bindWechat;
    }


    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                ", token='" + token + '\'' +
                ", qquid=" + qquid +
                ", wechatuid=" + wechatuid +
                ", microbloguid=" + microbloguid +
                ", loginName=" + loginName +
                ", salt=" + salt +
                ", registerDate=" + registerDate +
                ", openid=" + openid +
                ", bindWechat=" + bindWechat +
                '}';
    }
}
