package com.numbeo.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a price entry from the Numbeo API city_prices endpoint.
 */
public class Price {
    @SerializedName("item_id")
    private int itemId;

    @SerializedName("item_name")
    private String itemName;

    @SerializedName("category_id")
    private int categoryId;

    @SerializedName("category_name")
    private String categoryName;

    @SerializedName(value = "lowest_price", alternate = {"min_value", "minValue", "min"})
    private Double minValue;

    @SerializedName(value = "highest_price", alternate = {"max_value", "maxValue", "max"})
    private Double maxValue;

    @SerializedName(value = "average_price", alternate = {"average_value", "averageValue", "avg"})
    private Double averageValue;

    @SerializedName(value = "data_points", alternate = {"dataPoints", "count"})
    private Integer dataPoints;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Double getMinValue() {
        return minValue;
    }

    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }

    public Double getAverageValue() {
        return averageValue;
    }

    public void setAverageValue(Double averageValue) {
        this.averageValue = averageValue;
    }

    public Integer getDataPoints() {
        return dataPoints;
    }

    public void setDataPoints(Integer dataPoints) {
        this.dataPoints = dataPoints;
    }

    @Override
    public String toString() {
        return "Price{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", minValue=" + minValue +
                ", maxValue=" + maxValue +
                ", averageValue=" + averageValue +
                ", dataPoints=" + dataPoints +
                '}';
    }
}
