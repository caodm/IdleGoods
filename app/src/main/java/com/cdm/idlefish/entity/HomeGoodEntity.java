package com.cdm.idlefish.entity;


import android.os.Parcel;
import android.os.Parcelable;

public class HomeGoodEntity implements Parcelable {

    private int id;
    /** 用户id **/
    private int user_id;
    /** 用户头像 **/
    private String user_icon;
    /** 物品标题 **/
    private String title;
    /**  **/
    private String topLine;
    private int data_flag;
    private String image;
    /** 物品描述 **/
    private String content;
    private String operation;

    /** 物品售价 **/
    private float sellPrice;
    /** 物品原价 **/
    private float originalPrice;
    /** 联系方式 **/
    private String phone;

    public HomeGoodEntity(){

    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    protected HomeGoodEntity(Parcel in) {
        id = in.readInt();
        user_id = in.readInt();
        user_icon = in.readString();
        title = in.readString();
        topLine = in.readString();
        data_flag = in.readInt();
        image = in.readString();
        content = in.readString();
        operation = in.readString();
    }

    public static final Creator<HomeGoodEntity> CREATOR = new Creator<HomeGoodEntity>() {
        @Override
        public HomeGoodEntity createFromParcel(Parcel in) {
            return new HomeGoodEntity(in);
        }

        @Override
        public HomeGoodEntity[] newArray(int size) {
            return new HomeGoodEntity[size];
        }
    };

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
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(user_id);
        parcel.writeString(user_icon);
        parcel.writeString(title);
        parcel.writeString(topLine);
        parcel.writeInt(data_flag);
        parcel.writeString(image);
        parcel.writeString(content);
        parcel.writeString(operation);
    }
}
