package com.pax.nebula.common.entity;

/**
 * 订单项
 */
public class OrderItem {
    private String id;
    private int num;

    public OrderItem() {
    }

    public OrderItem(String id, int num) {
        this.id = id;
        this.num = num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
