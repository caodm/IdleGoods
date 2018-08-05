package com.cdm.idlefish.entity;

/**
 * Created by user on 2016/10/20.
 */
public class User {


    /**
     * name : zhangshihui
     * id : 1
     * password : 123
     * roles : null
     * token : c6b902535a14426ca29eecb5c750ab9a
     * qquid : null
     * wechatuid : null
     * microbloguid : null
     * memberInfo : {"address":null,"name":null,"id":1,"status":1,"sysuserId":null,"levelId":null,"nickName":null,"no":null,"tel":null,"balance":null,"avatarPath":null,"lev":null,"totalConsume":null,"totalPoint":null,"birth":null,"pwd":null,"email":null,"sex":null,"identification":null,"hobby":null,"postCode":null,"mac":null}
     * loginName : null
     * salt : null
     * registerDate : null
     * openid : null
     * bindWechat : null
     */

    private String name;
    private int id;
    private String password;
    private Object roles;
    private String token;
    private Object qquid;
    private Object wechatuid;
    private Object microbloguid;
    /**
     * address : null
     * name : null
     * id : 1
     * status : 1
     * sysuserId : null
     * levelId : null
     * nickName : null
     * no : null
     * tel : null
     * balance : null
     * avatarPath : null
     * lev : null
     * totalConsume : null
     * totalPoint : null
     * birth : null
     * pwd : null
     * email : null
     * sex : null
     * identification : null
     * hobby : null
     * postCode : null
     * mac : null
     */

    private MemberInfo memberInfo;
    private Object loginName;
    private Object salt;
    private Object registerDate;
    private Object openid;
    private Object bindWechat;


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

    public MemberInfo getMemberInfo() {
        return memberInfo;
    }

    public void setMemberInfo(MemberInfo memberInfo) {
        this.memberInfo = memberInfo;
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
                ", memberInfo=" + memberInfo +
                ", loginName=" + loginName +
                ", salt=" + salt +
                ", registerDate=" + registerDate +
                ", openid=" + openid +
                ", bindWechat=" + bindWechat +
                '}';
    }
}
