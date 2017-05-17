package com.thinkin.io.nio;

import java.util.Scanner;

/**
 * Created by xugangwen on 17/5/13.
 */
public class NioTest {
    public static void main(String[] args) throws Exception {
        ServerClient.start();
        Thread.sleep(100);
        UserClient.start();
        while(UserClient.sendMsg(new Scanner(System.in).nextLine()));
    }
}
