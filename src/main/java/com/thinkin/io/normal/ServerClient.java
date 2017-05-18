package com.thinkin.io.normal;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by xugangwen on 17/5/11.
 */
public  final  class ServerClient {
    private static int DEFAULT_PORT = 12312;
    private static ServerSocket server;
    public static void start() throws IOException {
        start(DEFAULT_PORT);
    }
    public synchronized static void start(int port) throws IOException{
        if(server != null) return;
        try{
            server = new ServerSocket(port);
            System.out.println("服务器已启动，端口号：" + port);
            Socket socket;
            for(;;){
                socket = server.accept();
                new Thread(new HandlerClient(socket)).start();//threadpool
            }
        }finally{
            if(server != null){
                System.out.println("服务器已关闭。");
                server.close();
                server = null;
            }
        }
    }
}
