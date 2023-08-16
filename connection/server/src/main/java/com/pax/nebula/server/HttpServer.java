package com.pax.nebula.server;

import android.content.Context;
import androidx.annotation.NonNull;
import com.pax.nebula.common.HttpConstants;
import com.yanzhenjie.andserver.AndServer;
import com.yanzhenjie.andserver.Server;
import java.util.concurrent.TimeUnit;

/**
 * Http 服务器
 */
class HttpServer {

    @NonNull
    private final IServer server;
    @NonNull
    private final Server webServer;

    HttpServer(@NonNull Context context, @NonNull IServer server) {
        this.server = server;
        this.webServer = AndServer.webServer(context)
                .port(HttpConstants.port)
                .timeout(10, TimeUnit.SECONDS)
                .listener(new Server.ServerListener() {
                    @Override
                    public void onStarted() {
                        server.onStarted();
                    }

                    @Override
                    public void onStopped() {
                        server.onStopped();
                    }

                    @Override
                    public void onException(Exception e) {
                        server.onException("Server throw exception", e);
                    }
                })
                .build();
    }

    void start() {
        try {
            webServer.startup();
        } catch (Exception e) {
            server.onException("Server start failed", e);
        }
    }

    boolean isRunning() {
        try {
            return webServer.isRunning();
        } catch (Exception e) {
            server.onException("Get server running status failed", e);
            return false;
        }
    }

    void stop() {
        try {
            webServer.shutdown();
        } catch (Exception e) {
            server.onException("Server stop failed", e);
        }
    }

    @NonNull
    IServer getServer() {
        return server;
    }
}
