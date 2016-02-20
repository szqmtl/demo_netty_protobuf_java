package org.shu.zq.nettyprotoc;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.shu.zq.nettyprotoc.exchange.Protocol;

/**
 * Created by Raymond on 2/9/2016.
 */
public class ServerHandler extends SimpleChannelInboundHandler<Protocol.Request> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Protocol.Request request) throws Exception {
        int id = request.getId();
        Protocol.Response.Builder respBuilder = Protocol.Response.newBuilder();
        respBuilder.setMsg("" + id + "-" + request.getMsg());
        channelHandlerContext.write(respBuilder.build());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx){
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }
}
