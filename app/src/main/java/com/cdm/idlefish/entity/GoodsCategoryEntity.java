package com.cdm.idlefish.entity;

/**
 * Created by Administrator on 2016/10/22 0022.
 */
public class GoodsCategoryEntity {
    private String categoryTitle;
    private String categoryContent;
    private int categoryIcon;

    public GoodsCategoryEntity() {
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public String getCategoryContent() {
        return categoryContent;
    }

    public void setCategoryContent(String categoryContent) {
        this.categoryContent = categoryContent;
    }

    public int getCategoryIcon() {
        return categoryIcon;
    }

    public void setCategoryIcon(int categoryIcon) {
        this.categoryIcon = categoryIcon;
    }

    @Override
    public String toString() {
        return "GoodsCategoryEntity{" +
                "categoryTitle='" + categoryTitle + '\'' +
                ", categoryContent='" + categoryContent + '\'' +
                ", categoryIcon=" + categoryIcon +
                '}';
    }
}
