package com.thinkin.io.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by bbking on 17-5-19.
 */
public class CommonUtil {

    /**
     *
     * @param bufferedReader
     * @param printWriter
     * @param socket
     */
    public static void finallyClose(BufferedReader bufferedReader, PrintWriter printWriter, Socket socket) {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (printWriter != null) {
                printWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //write
    public static void doWrite(SocketChannel channel, String request) throws IOException{
        byte[] bytes = request.getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        writeBuffer.put(bytes);
        writeBuffer.flip();
        channel.write(writeBuffer);
    }
}
