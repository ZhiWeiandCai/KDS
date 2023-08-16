package com.pax.nebula.server;

import com.pax.nebula.common.entity.GetAllItemResponse;
import com.pax.nebula.common.entity.KDSDish;
import com.pax.nebula.common.entity.OrderItem;

import java.util.List;

/**
 * 订单接口
 */
public interface IOrder {
    boolean useTable(
        String deviceId,
        String tableId);

    default boolean cancelTable(
            String deviceId,
            String tableId) {
        return false;
    }

    List<GetAllItemResponse> getAllTableItems();

    GetAllItemResponse getAllItem(String tableId);

    boolean pushAllItem(
            String deviceId,
            String tableId,
            String personNum,
            String time,
            List<OrderItem> items);

    boolean getIpConnectState(
            String deviceId,
            String ip);

    boolean pushKDSDish(
            String deviceId,
            String tId,
            String tableId,
            List<KDSDish> items);
}
