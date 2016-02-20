package org.shu.zq.nettyprotoc;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.shu.zq.nettyprotoc.exchange.Protocol;

/**
 * Created by Raymond on 2/9/2016.
 */
public class ClientHandler extends SimpleChannelInboundHandler<Protocol.Response> {
    private volatile Channel channel = null;
    private Protocol.Response svrResponse = null;

    public void send(int id, String msg){
        Protocol.Request.Builder builder = Protocol.Request.newBuilder();
        builder.setId(id).setMsg(msg);
        channel.writeAndFlush(builder.build());
    }

    public String getMessage(){
        if(svrResponse != null){
            return svrResponse.getMsg();
        }
        return null;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx){
        channel = ctx.channel();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Protocol.Response response) throws Exception {
        svrResponse = response;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }
}
