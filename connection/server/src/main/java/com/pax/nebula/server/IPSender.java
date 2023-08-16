package com.pax.nebula.server;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.LinkAddress;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.pax.nebula.common.IPConstants;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

/**
 * 广播 IP
 */
public class IPSender {
    private IPSender() {
        // do nothing
    }

    public static void sendOnce(Context context, OnSendListener listener) {
        NetworkRequest request = new NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .build();
        ConnectivityManager manager = context.getApplicationContext().getSystemService(ConnectivityManager.class);
        manager.registerNetworkCallback(request, new ConnectivityManager.NetworkCallback() {
            @Override
            public void onLinkPropertiesChanged(@NonNull Network network,
                    @NonNull LinkProperties linkProperties) {
                super.onLinkPropertiesChanged(network, linkProperties);
                for (LinkAddress address : linkProperties.getLinkAddresses()) {
                    String deviceAddress = address.getAddress().getHostAddress();
                    if (deviceAddress != null && !deviceAddress.contains(":")) {
                        int index = deviceAddress.lastIndexOf(".");
                        if (index > 0) {
                            String subAddress = deviceAddress.substring(0, index);
                            String serverAddress = subAddress + ".255";
                            try (DatagramSocket socket = new DatagramSocket()){
                                InetAddress server = InetAddress.getByName(serverAddress);
                                DatagramPacket packet = new DatagramPacket(
                                        IPConstants.data.getBytes(StandardCharsets.UTF_8),
                                        IPConstants.data.length(),
                                        server,
                                        IPConstants.port);
                                socket.send(packet);
                                listener.onSend(deviceAddress);
                            } catch (Exception e) {
                                listener.onError("Send error", e);
                            }
                        } else {
                            listener.onError("IP address format error: " + deviceAddress, null);
                        }
                    }
                }
                manager.unregisterNetworkCallback(this);
            }
        });
    }

    public interface OnSendListener {
        void onSend(@NonNull String address);
        void onError(@NonNull String message, @Nullable Throwable throwable);
    }
}
