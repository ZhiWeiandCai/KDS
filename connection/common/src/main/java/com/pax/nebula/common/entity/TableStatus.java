package com.pax.nebula.common.entity;

/**
 * 桌子的状态
 */
public enum TableStatus {
    /**
     * 已结账
     */
    ORDERED(true),

    /**
     * 正在下单
     */
    ORDERING(true),

    /**
     * 空闲
     */
    IDLE(false);

    /**
     * 是否正在使用
     */
    public final boolean isUsing;

    TableStatus(boolean isUsing) {
        this.isUsing = isUsing;
    }
}
