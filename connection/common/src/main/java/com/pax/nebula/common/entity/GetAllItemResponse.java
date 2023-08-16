package com.pax.nebula.common.entity;

import java.util.List;

/**
 * 获取桌子的数据
 */
public class GetAllItemResponse {
    private String tableId;
    private String personNum;
    private String time;
    private List<OrderItem> itemList;
    private TableStatus status;

    public GetAllItemResponse() {
    }

    public GetAllItemResponse(String tableId, List<OrderItem> itemList, TableStatus status) {
        this.tableId = tableId;
        this.itemList = itemList;
        this.status = status;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getPersonNum() {
        return personNum;
    }

    public void setPersonNum(String personNum) {
        this.personNum = personNum;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<OrderItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<OrderItem> itemList) {
        this.itemList = itemList;
    }

    public TableStatus getStatus() {
        return status;
    }

    public void setStatus(TableStatus status) {
        this.status = status;
    }
}
