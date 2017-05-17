package com.thinkin.io.nio;

/**
 * server_clent
 * Created by xugangwen on 17/5/13.
 */
public class ServerClient {
    private static int server_port = 12312;
    private static ServerClientHandler serverHandle;

    public static void start() {
        start(server_port);
    }

    private static synchronized void start(int port) {
        if (serverHandle != null) {
            serverHandle.stop();
        } else {
            serverHandle = new ServerClientHandler(port);
            new Thread(serverHandle, "Server_client").start();
        }
    }
}
