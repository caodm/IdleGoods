package com.cdm.idlefish.entity;

/**
 * Created by user on 2016/10/20.
 */
public class User {

    private String name;//用户名
    private int id; //用户id
    private String password; //密码
    private String usericon; //用户头像
    private String nickName;//昵称
    private String loginName;//用户名
    private int usersex;//性别: 0 : 男，1：女
    private String token; //token

    private int page;
    private int limit;
    private int start;

    public int getUsersex() {
        return usersex;
    }

    public void setUsersex(int usersex) {
        this.usersex = usersex;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    private int totalCount;
    private int totalSize;

    private Object roles; //可以为空
    private Object qquid; //可以为空
    private Object wechatuid;//可以为空
    private Object microbloguid;//可以为空
    private Object salt; //可以为空
    private Object registerDate;//可以为空
    private Object openid;//可以为空
    private Object bindWechat;//可以为空

    /**
     *
     * {"id":0,"page":0,"limit":0,"start":0,"totalCount":-1,"totalSize":-1,"name":null,
     "password":"123qwe","usericon":null,"nickName":"daming","loginName":"daming","sex":1,
     "token":null,"roles":null,"qquid":0,"wechatuid":0,"microbloguid":0,"salt":null,
     "registerdate":null,"openid":0,"bindwechat":null}
     *
     */



    public String getUserIcon() {
        return usericon;
    }

    public void setUserIcon(String userIcon) {
        this.usericon = userIcon;
    }


    public Object getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
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

    public void setLoginName(String loginName) {
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
