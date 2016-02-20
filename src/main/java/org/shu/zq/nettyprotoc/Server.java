package org.shu.zq.nettyprotoc;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Created by Raymond on 2/9/2016.
 */
public class Server {
    final static int PORT = 8888;
    EventLoopGroup bossGroup;
    EventLoopGroup workerGroup;

    public void start(int port) throws InterruptedException {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();

        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ServerInitializer());

        b.bind(port).sync().channel().closeFuture().sync();
    }

    public void shutdown(){
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    public static void main(String[] args) throws Exception {
        Server svr = new Server();
        try{
            svr.start(PORT);
        }finally {
            svr.shutdown();
        }
    }
}
