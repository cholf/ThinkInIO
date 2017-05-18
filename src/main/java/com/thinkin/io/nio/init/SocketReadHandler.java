package com.thinkin.io.nio.init;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * Created by xugangwen on 17/5/19.
 */
public class SocketReadHandler implements Runnable {
    final SocketChannel socketChannel;
    final SelectionKey selectionKey;
    static final int READING = 0, SENDING = 1;
    int state = READING;

    public SocketReadHandler(Selector selector, SocketChannel c)
            throws IOException {
        socketChannel = c;
        socketChannel.configureBlocking(false);
        selectionKey = socketChannel.register(selector, 0);
        selectionKey.attach(this);
        selectionKey.interestOps(SelectionKey.OP_READ);
        selector.wakeup();
    }

    public void run() {
        try {
            readRequest();
        } catch (Exception ex) {
            //
        }
    }


    private void readRequest() throws Exception {
        ByteBuffer input = ByteBuffer.allocate(1024);
        input.clear();
        try {
            int bytesRead = socketChannel.read(input);
            {
                //dosomting
                //requestHandle
            }
        } catch (Exception e) {
        }
    }
}
