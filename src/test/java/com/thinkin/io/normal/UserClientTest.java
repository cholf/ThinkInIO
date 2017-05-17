package com.thinkin.io.normal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Created by xugangwen on 17/5/13.
 */
public class UserClientTest {

    private CountDownLatch latch = new CountDownLatch(3);

    @Before
    public void setUp() throws Exception {
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

    }

    @Test
    public  void sendMsg() throws Exception {
        new Thread(new Runnable() {
            public void run() {
                while(true){
                    Calendar calendar = Calendar.getInstance();
                    int seconds = calendar.get(Calendar.SECOND);
                    UserClient.sendMsg("hello_io_user"+"__"+seconds);
                    try {
                        Thread.currentThread().sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        latch.await(5, TimeUnit.MINUTES);
    }



}