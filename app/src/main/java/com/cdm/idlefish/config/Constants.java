package com.cdm.idlefish.config;

/*******************************************************************
 * @文件名称:
 * @作者 : caodm
 * @文件描述: 放置 常量
 * @创建时间: 2016/8/3
 * ******************************************************************
 */
public class Constants {
    /* 用户名*/
    public static final String HAWK_USERNAME = "userName";
    /* 密码*/
    public static final String HAWK_PASSWORD = "password";
    /*明文密码*/
    public static final String HAWK_PWD = "pwd";
    /*是否登录*/
    public static final String HAWK_ISLOGIN = "isLogin";

    /*图文地址*/
    public static String ImageUrl = Server.getInstance().getHttpServer() + "/article/";

    /* 首页轮播图*/
    public static final String HAWK_BANNER_HOME = "banner";

    /* 物品列表 */
    public static final String HAWK_GOODS_LIST = "goods_list";

    /**************************物品相关常量*********************************/
    public static final String SPLIT_TAG = "@";
    public static final String GOODS_TYPE = "type";
    public static final String GOODS_TITLE = "title";
    public static final String GOODS_ID = "id";

    /****/
    public static final String[] GOODSCATEGORY=new String[]{
            "潮流女装","品牌男装","品牌女士",
            "家用电器","手机数码","电脑办公","个护化妆","母婴频道","食物生鲜",
            "酒水饮料","家居家纺","整车车品","鞋靴箱包","运动户外","图书",
            "玩具乐器","钟表","居家生活","珠宝饰品","音像制品","家具建材",
            "营养保健","奢侈礼品","生活服务","旅游出行"};

}
