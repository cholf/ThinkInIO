package com.thinkin.io.nio;

/**
 * Created by xugangwen on 17/5/13.
 */
public class UserClient {
    private static  final int server_port=12312;
    private static  final String server_ip="127.0.0.1";
    private static UserClientHandler userClientHandler;

    public static void start(){
        start(server_ip,server_port);
    }
    public static synchronized void start(String ip,int port){
        if(userClientHandler != null){
            userClientHandler.stop();
        }else {
            userClientHandler = new UserClientHandler(ip,port);
            new  Thread(userClientHandler,"User_Client").start();
        }

    }

    public static  boolean sendMsg(String msg){
        try {
            userClientHandler.sendMsg(msg);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
