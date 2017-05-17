package com.thinkin.io.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.UnsupportedEncodingException;

/**
 * server_handler
 * Created by xugangwen on 17/5/14.
 */
public class ServerClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws UnsupportedEncodingException {
        ByteBuf in = (ByteBuf) msg;
        byte[] req = new byte[in.readableBytes()];
        in.readBytes(req);
        String body = new String(req,"utf-8");
        System.out.println("client_msg:"+body);
        String calrResult = null;
        try{
            calrResult = "after_deal_by_server:"+body+"*****";
        }catch(Exception e){
            calrResult = "server_exception:" + e.getMessage();
        }
        ctx.write(Unpooled.copiedBuffer(calrResult.getBytes()));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
