package com.cdm.idlefish.entity;


import android.os.Parcel;
import android.os.Parcelable;

public class HomeGoodEntity implements Parcelable {

    private int id;
    /** 当前物品所属用户id **/
    private int user_id;
    /** 当前物品所属用户头像 **/
    private String userIcon;
    /** 当前物品所属用户名称 **/
    private String userName;
    /** 物品标题 **/
    private String title;
    /**  **/
    private String topline;//这个暂时可为空
    private int dataFlag; //这个暂时可为空
    /** 物品图片地址，最多5张，"image1&image2&image3&image4&image5"**/
    private String image;
    /** 分类 **/
    private String type;
    /** 物品描述 **/
    private String content;
    private String operation;
    /** 物品重量 **/
    private String weight= "20.0";
    /** 物品售价 **/
    private float sellprice;
    /** 物品原价 **/
    private float originalprice;
    /** 联系方式 **/
    private String phone;

    /** 是否已收藏 0：收藏 1：未收藏 **/
    private String collect;

    private int memberId;

    private int start;
    private int totalCount;
    private int totalSize;
    private int page;
    private int limit;

    /**
     * {"id":2,"page":0,"limit":0,"start":0,"totalCount":-1,"totalSize":-1,"userIcon":null,
     "userName":null,"title":"测试","topline":null,"dataFlag":0,"image":null,"type":"ceshi",
     "content":"测试","operation":null,"weight":"30","sellprice":12.0,"originalprice":20.0,
     "phone":"15751155335","memberId":1}
     * @return
     */

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getCollect() {
        return collect;
    }

    public void setCollect(String collect) {
        this.collect = collect;
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

    public static Creator<HomeGoodEntity> getCREATOR() {
        return CREATOR;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getUser_name() {
        return userName;
    }

    public void setUser_name(String user_name) {
        this.userName = user_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public HomeGoodEntity(){

    }

    public HomeGoodEntity(int id, int user_id, String userIcon, String user_name,
                          String title, String topLine, int data_flag, String image,
                          String type, String content, String operation, String weight,
                          float sellPrice, float originalPrice, String phone,String collect) {
        this.id = id;
        this.user_id = user_id;
        this.userIcon = userIcon;
        this.userName = user_name;
        this.title = title;
        this.topline = topLine;
        this.dataFlag = data_flag;
        this.image = image;
        this.type = type;
        this.content = content;
        this.operation = operation;
        this.weight = weight;
        this.sellprice = sellPrice;
        this.originalprice = originalPrice;
        this.phone = phone;
        this.collect= collect;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_icon() {
        return userIcon;
    }

    public void setUser_icon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTopLine() {
        return topline;
    }

    public void setTopLine(String topLine) {
        this.topline = topLine;
    }

    public int getData_flag() {
        return dataFlag;
    }

    public void setData_flag(int data_flag) {
        this.dataFlag = data_flag;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public float getSellPrice() {
        return sellprice;
    }

    public void setSellPrice(float sellPrice) {
        this.sellprice = sellPrice;
    }

    public float getOriginalPrice() {
        return originalprice;
    }

    public void setOriginalPrice(float originalPrice) {
        this.originalprice = originalPrice;
    }

    @Override
    public String toString() {
        return "HomeGoodEntity{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", userIcon='" + userIcon + '\'' +
                ", userName='" + userName + '\'' +
                ", title='" + title + '\'' +
                ", topline='" + topline + '\'' +
                ", dataFlag=" + dataFlag +
                ", image='" + image + '\'' +
                ", type='" + type + '\'' +
                ", content='" + content + '\'' +
                ", operation='" + operation + '\'' +
                ", weight='" + weight + '\'' +
                ", sellprice=" + sellprice +
                ", originalprice=" + originalprice +
                ", phone='" + phone + '\'' +
                ", collect='" + collect + '\'' +
                ", memberId=" + memberId +
                ", start=" + start +
                ", totalCount=" + totalCount +
                ", totalSize=" + totalSize +
                ", page=" + page +
                ", limit=" + limit +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.user_id);
        dest.writeString(this.userIcon);
        dest.writeString(this.userName);
        dest.writeString(this.title);
        dest.writeString(this.topline);
        dest.writeInt(this.dataFlag);
        dest.writeString(this.image);
        dest.writeString(this.type);
        dest.writeString(this.content);
        dest.writeString(this.operation);
        dest.writeString(this.weight);
        dest.writeFloat(this.sellprice);
        dest.writeFloat(this.originalprice);
        dest.writeString(this.phone);
        dest.writeString(this.collect);

        dest.writeInt(this.start);
        dest.writeInt(this.totalCount);
        dest.writeInt(this.totalSize);
        dest.writeInt(this.page);
        dest.writeInt(this.limit);
    }

    protected HomeGoodEntity(Parcel in) {
        this.id = in.readInt();
        this.user_id = in.readInt();
        this.userIcon = in.readString();
        this.userName = in.readString();
        this.title = in.readString();
        this.topline = in.readString();
        this.dataFlag = in.readInt();
        this.image = in.readString();
        this.type = in.readString();
        this.content = in.readString();
        this.operation = in.readString();
        this.weight = in.readString();
        this.sellprice = in.readFloat();
        this.originalprice = in.readFloat();
        this.phone = in.readString();
        this.collect = in.readString();

        this.start = in.readInt();
        this.totalCount = in.readInt();
        this.totalSize = in.readInt();
        this.page = in.readInt();
        this.limit = in.readInt();
    }

    public static final Creator<HomeGoodEntity> CREATOR = new Creator<HomeGoodEntity>() {
        @Override
        public HomeGoodEntity createFromParcel(Parcel source) {
            return new HomeGoodEntity(source);
        }

        @Override
        public HomeGoodEntity[] newArray(int size) {
            return new HomeGoodEntity[size];
        }
    };
}
