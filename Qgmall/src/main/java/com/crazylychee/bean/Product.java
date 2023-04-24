package com.crazylychee.bean;

import java.util.Date;
import java.util.List;

/**
 * @author yc
 * @date 2023/4/23 20:49
 */
public class Product {
    private String name;
    private String subTitle;
    private float originalPrice;
    private float promotePrice;
    private int stock;
    private Date createDate;
    private int id;
    private ProductImage FristImages;
    private List<ProductImage> productImages;
    private List<ProductImage> ProductDetailImages;
    private List<ProductImage> ProductSingleImages;
    private int reviewCount;
    private int saleCount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public float getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(float originalPrice) {
        this.originalPrice = originalPrice;
    }

    public float getPromotePrice() {
        return promotePrice;
    }

    public void setPromotePrice(float promotePrice) {
        this.promotePrice = promotePrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProductImage getFristImages() {
        return FristImages;
    }

    public void setFristImages(ProductImage fristImages) {
        FristImages = fristImages;
    }

    public List<ProductImage> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImage> productImages) {
        this.productImages = productImages;
    }

    public List<ProductImage> getProductDetailImages() {
        return ProductDetailImages;
    }

    public void setProductDetailImages(List<ProductImage> productDetailImages) {
        ProductDetailImages = productDetailImages;
    }

    public List<ProductImage> getProductSingleImages() {
        return ProductSingleImages;
    }

    public void setProductSingleImages(List<ProductImage> productSingleImages) {
        ProductSingleImages = productSingleImages;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public int getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(int saleCount) {
        this.saleCount = saleCount;
    }
    @Override
    public String toString() {
        return name;
    }
}
