package com.thinkin.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * NIO-user-handler
 * Created by xugangwen on 17/5/13.
 */
public class UserClientHandler implements Runnable {

    private String host_ip;
    private int port;
    private Selector selector;
    private SocketChannel socketChannel;
    private volatile boolean started;

    public UserClientHandler(String ip,int port){

        this.host_ip = ip;
        this.port = port;

        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            started = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void run() {

        //connect
        try {
            doConnect();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        //selector
        while (started){
            try {
                selector.select(1000);
                Set<SelectionKey> keset = selector.selectedKeys();
                Iterator<SelectionKey> selectionKeyIterator = keset.iterator();
                SelectionKey key;
                while (selectionKeyIterator.hasNext()){
                    key=selectionKeyIterator.next();
                    selectionKeyIterator.remove();
                    try {
                        handleInput(key);
                    } catch (Exception e) {
                        if(key != null){
                            key.cancel();
                            if(key.channel() != null){
                                key.channel().close();
                            }
                        }
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        if(selector != null){
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop(){
        started = false;
    }
    private void doConnect() throws IOException{
        if(socketChannel.connect(new InetSocketAddress(host_ip,port))){

        }else {
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
        }
    }

    public void sendMsg(String msg) throws Exception{
        socketChannel.register(selector, SelectionKey.OP_READ);
        doWrite(socketChannel, msg);
    }

    private void handleInput(SelectionKey key) throws IOException{
        if(key.isValid()){
            SocketChannel socketChannel = (SocketChannel) key.channel();
            if(key.isConnectable()){
                if (socketChannel.finishConnect()){

                }else {
                    System.exit(1);
                }
            }
            //read msg
            if(key.isReadable()){
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                int readBytes = socketChannel.read(byteBuffer);
                if(readBytes > 0){
                    byteBuffer.flip();
                    byte[] bytes = new byte[byteBuffer.remaining()];
                    byteBuffer.get(bytes);
                    String result = new String(bytes,"UTF-8");
                    System.out.println("receive msg :"+result);
                }else if(readBytes < 0){
                    key.cancel();
                    socketChannel.close();
                }
            }
        }
    }

    //write
    private void doWrite(SocketChannel channel,String request) throws IOException{
        byte[] bytes = request.getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        writeBuffer.put(bytes);
        writeBuffer.flip();
        channel.write(writeBuffer);
    }
}
