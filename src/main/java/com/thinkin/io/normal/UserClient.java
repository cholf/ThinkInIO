package com.thinkin.io.normal;

import com.thinkin.io.utils.CommonUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by xugangwen on 17/5/11.
 */
public class UserClient {
    private static  final int server_port=12312;
    private static  final String server_ip="127.0.0.1";

    public static void sendMsg(String inputMsg) {
        sendMsg(server_port, inputMsg);
    }

    public static void sendMsg(int port, String msg) {
        Socket socket =null ;
        BufferedReader bufferedReader=null;
        PrintWriter printWriter=null;
        try {
            socket = new Socket(server_ip,port);
            //
            printWriter = new PrintWriter(socket.getOutputStream(),true);
            printWriter.println(msg);
            //
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.printf("result:"+bufferedReader.readLine());

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            CommonUtil.finallyClose(bufferedReader, printWriter,socket);
        }

    }
}
