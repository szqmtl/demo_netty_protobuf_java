package org.shu.zq.nettyprotoc;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Raymond on 2/9/2016.
 */
public class Client {
    static final String HOST = "127.0.0.1";
    static final int PORT = 8888;
    Channel ch;
    EventLoopGroup group;
    Logger logger = LoggerFactory.getLogger(Client.class);

    public void start(String host, int port) throws InterruptedException {
        group = new NioEventLoopGroup();

        Bootstrap b = new Bootstrap();
        b.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ClientInitializer());

        ch = b.connect(host, port).sync().channel();
    }

    public String send(int id, String msg) throws InterruptedException {

        ClientHandler handler;
        handler = ch.pipeline().get(ClientHandler.class);

        handler.send(id, msg);

        String ret = null;
        while(ret == null){
            Thread.sleep(200);
            ret = handler.getMessage();
        }
        return ret;
    }

    public void close(){
        ch.close();
    }
    public void shutdown(){
        if(group != null){
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        Client client = new Client();
        try{
            client.start(HOST, PORT);
            System.out.println("response: " + client.send(1, "abc"));
            client.close();
        }finally {
            client.shutdown();
        }
    }
}
