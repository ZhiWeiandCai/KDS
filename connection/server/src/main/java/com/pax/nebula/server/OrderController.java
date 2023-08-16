package com.pax.nebula.server;

import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.pax.nebula.common.HttpConstants;
import com.pax.nebula.common.JsonUtils;
import com.pax.nebula.common.entity.GetAllItemResponse;
import com.pax.nebula.common.entity.KDSDish;
import com.pax.nebula.common.entity.OrderItem;
import com.yanzhenjie.andserver.annotation.GetMapping;
import com.yanzhenjie.andserver.annotation.PathVariable;
import com.yanzhenjie.andserver.annotation.PostMapping;
import com.yanzhenjie.andserver.annotation.RequestMapping;
import com.yanzhenjie.andserver.annotation.RequestParam;
import com.yanzhenjie.andserver.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * File description
 *
 * @author yehongbo
 * @date 2023/1/10
 */
@RestController
@RequestMapping(path = HttpConstants.Order.path)
public class OrderController {

    @PostMapping(path = HttpConstants.Order.UseTable.path)
    public String useTable(
            @RequestParam(HttpConstants.Order.UseTable.param_deviceId) String deviceId,
            @RequestParam(HttpConstants.Order.UseTable.param_tableId) String tableId
    ) {
        boolean isSuccess;
        IServer server = ServerManager.getInstance().getServer();
        if (server == null) {
            isSuccess = false;
        } else {
            isSuccess = server.useTable(deviceId, tableId);
        }
        return Boolean.toString(isSuccess);
    }

    @PostMapping(path = HttpConstants.Order.CancelTable.path)
    public String cancelTable(
            @RequestParam(HttpConstants.Order.CancelTable.param_deviceId) String deviceId,
            @RequestParam(HttpConstants.Order.CancelTable.param_tableId) String tableId
    ) {
        boolean isSuccess;
        IServer server = ServerManager.getInstance().getServer();
        if (server == null) {
            isSuccess = false;
        } else {
            isSuccess = server.cancelTable(deviceId, tableId);
        }
        return Boolean.toString(isSuccess);
    }

    @GetMapping(path = HttpConstants.Order.GetAllTableItems.path)
    public String getAllTableItems() {
        IServer server = ServerManager.getInstance().getServer();
        List<GetAllItemResponse> responseList = null;
        if (server != null) {
            responseList = server.getAllTableItems();
        }
        if (responseList == null) {
            return JsonUtils.toJson(new ArrayList<GetAllItemResponse>());
        } else {
            return JsonUtils.toJson(responseList);
        }
    }

    @GetMapping(path = HttpConstants.Order.GetAllItem.path)
    public String getAllItem(
            @PathVariable(HttpConstants.Order.GetAllItem.path_tableId) String tableId
    ) {
        IServer server = ServerManager.getInstance().getServer();
        GetAllItemResponse response = null;
        if (server != null) {
            response = server.getAllItem(tableId);
        }
        if (response == null) {
            return JsonUtils.toJson(new GetAllItemResponse());
        } else {
            return JsonUtils.toJson(response);
        }
    }

    @PostMapping(path = HttpConstants.Order.PushAllItem.path)
    public String pushAllItem(
            @RequestParam(HttpConstants.Order.PushAllItem.param_deviceId) String deviceId,
            @RequestParam(HttpConstants.Order.PushAllItem.param_tableId) String tableId,
            @RequestParam(HttpConstants.Order.PushAllItem.param_personNum) String personNum,
            @RequestParam(HttpConstants.Order.PushAllItem.param_time) String time,
            @RequestParam(HttpConstants.Order.PushAllItem.param_items) String items
    ) {
        Log.d("Order", "items json: " + items);
        boolean isSuccess;
        IServer server = ServerManager.getInstance().getServer();
        if (server == null) {
            isSuccess = false;
        } else {
            List<OrderItem> itemList = JsonUtils.fromJson(items, new TypeToken<List<OrderItem>>(){}.getType());
            isSuccess = server.pushAllItem(deviceId, tableId, personNum, time, itemList);
        }
        return Boolean.toString(isSuccess);
    }

    @PostMapping(path = HttpConstants.Order.GetIPConnectState.path)
    public String getIpConnectState(
            @RequestParam(HttpConstants.Order.GetIPConnectState.param_deviceId) String deviceId,
            @RequestParam(HttpConstants.Order.GetIPConnectState.param_ip) String ip
    ) {
        boolean isSuccess;
        IServer server = ServerManager.getInstance().getServer();
        if (server == null) {
            isSuccess = false;
        } else {
            isSuccess = server.getIpConnectState(deviceId, ip);
        }
        return Boolean.toString(isSuccess);
    }

    @PostMapping(path = HttpConstants.Order.PushKDSDish.path)
    public String pushKDSDish(
            @RequestParam(HttpConstants.Order.PushKDSDish.param_deviceId) String deviceId,
            @RequestParam(HttpConstants.Order.PushKDSDish.param_tId) String tId,
            @RequestParam(HttpConstants.Order.PushKDSDish.param_tableId) String tableId,
            @RequestParam(HttpConstants.Order.PushKDSDish.param_items) String items
    ) {
        Log.d("KDSDish", "items json: " + items);
        boolean isSuccess;
        IServer server = ServerManager.getInstance().getServer();
        if (server == null) {
            isSuccess = false;
        } else {
            List<KDSDish> itemList = JsonUtils.fromJson(items, new TypeToken<List<KDSDish>>(){}.getType());
            isSuccess = server.pushKDSDish(deviceId, tId, tableId, itemList);
        }
        return Boolean.toString(isSuccess);
    }
}
