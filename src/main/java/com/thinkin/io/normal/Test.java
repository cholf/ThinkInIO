package com.thinkin.io.normal;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.Random;

/**
 * Created by xugangwen on 17/5/12.
 */
public class Test {
    public static void main(String[] args) throws  InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ServerClient.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Thread.sleep(1000);
        Random random = new Random(System.currentTimeMillis());
        new Thread(new Runnable() {
            public void run() {
                while(true){
                    UserClient.sendMsg("hello_io_user");
                    try {
                        Thread.currentThread().sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


}
