package com.pax.nebula.common.entity;

/**
 * Created by caizhiwei on 2023/8/7
 */
public class KDSDish {
    private long id;
    private String name;
    private int num;

    public KDSDish(long id, String name, int num) {
        this.id = id;
        this.name = name;
        this.num = num;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
