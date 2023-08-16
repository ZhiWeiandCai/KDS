package com.pax.nebula.common;

/**
 * File description
 *
 * @author yehongbo
 * @date 2023/1/10
 */
public final class HttpConstants {
    public static final String version = "/v1";
    public static final int port = 27890;

    private HttpConstants() {
        // do nothing
    }

    public static final class Order {
        public static final String path = version + "/order";

        private Order() {
            // do nothing
        }

        public static final class UseTable {
            public static final String path = "/use_table";
            public static final String param_deviceId = "deviceId";
            public static final String param_tableId = "tableId";

            private UseTable() {
                // do nothing
            }

            public static String url(String ip) {
                return "http://" + ip + ":" + HttpConstants.port + Order.path + UseTable.path;
            }
        }

        public static final class CancelTable {
            public static final String path = "/cancel_table";
            public static final String param_deviceId = "deviceId";
            public static final String param_tableId = "tableId";

            private CancelTable() {
                // do nothing
            }

            public static String url(String ip) {
                return "http://" + ip + ":" + HttpConstants.port + Order.path + CancelTable.path;
            }
        }

        public static final class GetAllTableItems {
            public static final String path = "/get_all_table_items";

            private GetAllTableItems() {
                // do nothing
            }

            public static String url(String ip) {
                return "http://" + ip + ":" + HttpConstants.port + Order.path + GetAllTableItems.path;
            }
        }

        public static final class GetAllItem {
            public static final String path = "/get_all_item/{tableId}";
            public static final String path_tableId = "tableId";

            private GetAllItem() {
                // do nothing
            }

            public static String url(String ip, String tableId) {
                return "http://" + ip + ":" + HttpConstants.port + Order.path + "/get_all_item/" + tableId;
            }
        }

        public static final class PushAllItem {
            public static final String path = "/push_all_item";
            public static final String param_deviceId = "deviceId";
            public static final String param_tableId = "tableId";
            public static final String param_personNum = "personNum";
            public static final String param_time = "time";
            public static final String param_items = "items";

            private PushAllItem() {
                // do nothing
            }

            public static String url(String ip) {
                return "http://" + ip + ":" + HttpConstants.port + Order.path + PushAllItem.path;
            }
        }

        public static final class GetIPConnectState {
            public static final String path = "/get_ip_connect_state";
            public static final String param_deviceId = "deviceId";
            public static final String param_ip = "ip";

            private GetIPConnectState() {
                // do nothing
            }

            public static String url(String ip) {
                return "http://" + ip + ":" + HttpConstants.port + Order.path + GetIPConnectState.path;
            }
        }

        public static final class PushKDSDish {
            public static final String path = "/push_kds_dish";
            public static final String param_deviceId = "deviceId";
            public static final String param_tId = "tId";
            public static final String param_tableId = "tableId";
            public static final String param_items = "items";

            private PushKDSDish() {
                // do nothing
            }

            public static String url(String ip) {
                return "http://" + ip + ":" + HttpConstants.port + Order.path + PushKDSDish.path;
            }
        }
    }
}
