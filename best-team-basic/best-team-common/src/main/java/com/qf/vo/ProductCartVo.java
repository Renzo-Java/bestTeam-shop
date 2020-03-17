package com.qf.vo;

import com.qf.entity.TProduct;

import java.io.Serializable;
import java.util.Date;

public class ProductCartVo implements Serializable {
    private TProduct product;

    public TProduct getProduct() {
        return product;
    }

    public void setProduct(TProduct product) {
        this.product = product;
    }

    private int count;

    private Date updateTime;

    public ProductCartVo() {
    }

    public ProductCartVo(TProduct product, int count, Date updateTime) {
        this.product = product;
        this.count = count;
        this.updateTime = updateTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "ProductCartVo{" +
                "product=" + product +
                ", count=" + count +
                ", updateTime=" + updateTime +
                '}';
    }
}
