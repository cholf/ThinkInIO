package com.thinkin.io.normal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by xugangwen on 17/5/11.
 */
public class HandlerClient implements Runnable {

    private Socket socket;
    public HandlerClient(Socket socket){
        this.socket = socket;
    }

    public void run() {
        BufferedReader bufferedReader = null;
        PrintWriter printWriter = null;
        try{
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(socket.getOutputStream(),true);
            String handlMsg;
            String result;
            for(;;){
                if((handlMsg = bufferedReader.readLine())==null) break;
                try{
                    result = "after_handle:"+handlMsg+"************";
                }catch(Exception e){
                    result = "异常信息：" + e.getMessage();
                }
                //
                printWriter.println(result);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(bufferedReader != null){
                    bufferedReader.close();
                }
                if(printWriter != null){
                    printWriter.close();
                }
                if(socket != null){
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
