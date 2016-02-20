package org.shu.zq.nettyprotoc;

import org.junit.Test;

import java.security.*;
import java.util.Random;
import java.math.BigInteger;

import static org.junit.Assert.assertEquals;

/**
 * Created by Raymond on 2/10/2016.
 */
public class ProtocolTest {

    @Test
    public void testStart() throws Exception {
        int port = 47878;
        String address = "localhost";
        TestServer svr = new TestServer(port);
        Client cli = new Client();

        svr.start();
        Thread.sleep(2000);
        cli.start(address, port);

        Random gen = new Random();
        int id = gen.nextInt(10) + 1;
        String msg = new BigInteger(130, new SecureRandom()).toString(32);
        assertEquals(cli.send(id, msg), "" + id + "-" + msg);
        cli.close();
        svr.shutdown();

    }

    class TestServer extends Thread {
        int port = 0;
        Server svr = null;

        public TestServer(int port) {
            this.port = port;
            svr = new Server();
        }

        public void run() {
            try {
                svr.start(port);
            }catch(Exception e){
                e.printStackTrace();
            } finally {
                svr.shutdown();
            }
        }

        public void shutdown() {
            svr.shutdown();
        }
    }
}