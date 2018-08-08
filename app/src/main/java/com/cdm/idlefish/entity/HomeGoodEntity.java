package com.cdm.idlefish.entity;


import android.os.Parcel;
import android.os.Parcelable;

public class HomeGoodEntity implements Parcelable {

    private int id;
    /** 用户id **/
    private int user_id;
    /** 用户头像 **/
    private String user_icon;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    /** 用户名称 **/
    private String user_name;
    /** 物品标题 **/
    private String title;
    /**  **/
    private String topLine;
    private int data_flag;
    private String image;
    private int type;
    /** 物品描述 **/
    private String content;
    private String operation;

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    private String weight;
    /** 物品售价 **/
    private float sellPrice;
    /** 物品原价 **/
    private float originalPrice;
    /** 联系方式 **/
    private String phone;

    public HomeGoodEntity(){

    }

    public HomeGoodEntity(int id, int user_id, String user_icon, String user_name,
                          String title, String topLine, int data_flag, String image,
                          int type, String content, String operation, String weight,
                          float sellPrice, float originalPrice, String phone) {
        this.id = id;
        this.user_id = user_id;
        this.user_icon = user_icon;
        this.user_name = user_name;
        this.title = title;
        this.topLine = topLine;
        this.data_flag = data_flag;
        this.image = image;
        this.type = type;
        this.content = content;
        this.operation = operation;
        this.weight = weight;
        this.sellPrice = sellPrice;
        this.originalPrice = originalPrice;
        this.phone = phone;
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
        return user_icon;
    }

    public void setUser_icon(String user_icon) {
        this.user_icon = user_icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTopLine() {
        return topLine;
    }

    public void setTopLine(String topLine) {
        this.topLine = topLine;
    }

    public int getData_flag() {
        return data_flag;
    }

    public void setData_flag(int data_flag) {
        this.data_flag = data_flag;
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
        return sellPrice;
    }

    public void setSellPrice(float sellPrice) {
        this.sellPrice = sellPrice;
    }

    public float getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(float originalPrice) {
        this.originalPrice = originalPrice;
    }

    @Override
    public String toString() {
        return "HomeGoodEntity{" +
                "userIcon='" + user_icon + '\'' +
                ", title='" + title + '\'' +
                ", topLine='" + topLine + '\'' +
                ", flag='" + data_flag + '\'' +
                ", image='" + image + '\'' +
                ", content='" + content + '\'' +
                ", operation='" + operation + '\'' +
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
        dest.writeString(this.user_icon);
        dest.writeString(this.user_name);
        dest.writeString(this.title);
        dest.writeString(this.topLine);
        dest.writeInt(this.data_flag);
        dest.writeString(this.image);
        dest.writeInt(this.type);
        dest.writeString(this.content);
        dest.writeString(this.operation);
        dest.writeString(this.weight);
        dest.writeFloat(this.sellPrice);
        dest.writeFloat(this.originalPrice);
        dest.writeString(this.phone);
    }

    protected HomeGoodEntity(Parcel in) {
        this.id = in.readInt();
        this.user_id = in.readInt();
        this.user_icon = in.readString();
        this.user_name = in.readString();
        this.title = in.readString();
        this.topLine = in.readString();
        this.data_flag = in.readInt();
        this.image = in.readString();
        this.type = in.readInt();
        this.content = in.readString();
        this.operation = in.readString();
        this.weight = in.readString();
        this.sellPrice = in.readFloat();
        this.originalPrice = in.readFloat();
        this.phone = in.readString();
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
