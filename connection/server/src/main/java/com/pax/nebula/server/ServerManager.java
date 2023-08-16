package com.pax.nebula.server;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 服务器管理类
 */
public class ServerManager {
    @Nullable
    private HttpServer server;

    private ServerManager() {
        // do nothing
    }

    private static class Holder {
        private static final ServerManager INSTANCE = new ServerManager();
    }

    public static ServerManager getInstance() {
        return Holder.INSTANCE;
    }

    @Nullable
    IServer getServer() {
        if (server == null) {
            return null;
        } else {
            return server.getServer();
        }
    }

    /**
     * 初始化
     *
     * @param context Context
     * @param server 服务器逻辑
     */
    public void init(@NonNull Context context, @NonNull IServer server) {
        this.server = new HttpServer(context, server);
    }

    /**
     * 启动服务器
     */
    public void start() {
        if (server != null) {
            server.start();
        }
    }

    /**
     * 检查服务器是否在运行
     * @return {@code true} 表示正在运行
     */
    public boolean isRunning() {
        if (server == null) {
            return false;
        } else {
            return server.isRunning();
        }
    }

    /**
     * 关闭服务器
     */
    public void stop() {
        if (server != null) {
            server.stop();
        }
    }
}
