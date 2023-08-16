package com.pax.nebula.client;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.pax.nebula.common.IPConstants;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;

/**
 * 接收 IP 广播
 */
public class IPReceiver {
    private IPReceiver() {
        // do nothing
    }

    public static void receiveBlocking(OnReceiveListener listener) {
        try (DatagramSocket socket = new DatagramSocket(IPConstants.port)) {
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            while (true) {
                socket.receive(packet);
                String data = new String(packet.getData(), 0, packet.getLength(), StandardCharsets.UTF_8);
                if (IPConstants.data.equals(data)) {
                    String address = packet.getAddress().getHostAddress();
                    if (address == null) {
                        listener.onError("Empty address", null);
                    } else {
                        boolean isContinue = listener.onReceive(address);
                        if (!isContinue) {
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            listener.onError("Receive error", e);
        }
    }

    public interface OnReceiveListener {
        boolean onReceive(@NonNull String address);
        void onError(@NonNull String message, @Nullable Throwable throwable);
    }
}
