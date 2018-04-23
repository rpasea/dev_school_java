package com.example.tcpserver;

import com.example.tcpserver.codec.CodecPipeline;
import com.example.tcpserver.codec.CodecPipelineFactory;
import com.example.tcpserver.codec.StringCodec;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class TCPServerIT {
    private static final Logger LOG = LoggerFactory.getLogger(TCPServerIT.class);
    private static final int PORT = 9090;
    private static final String MESSAGE = "MOCK";

    private TcpServer nioServer;

    @Before
    public void setup() throws IOException {

        nioServer = new ThreadedServer(PORT, new CodecPipelineFactory() {
            @Override
            public CodecPipeline newCodecPipeline() {
                CodecPipeline pipeline = new CodecPipeline();
                pipeline.addCodec(new StringCodec());

                return pipeline;
            }
        }, new MessageHandler<String, String>() {

            @Override
            public List<String> handle(String message) {
                return Collections.singletonList(message);
            }
        });

        nioServer.start();
    }

    @After
    public void teardown() throws InterruptedException {
        nioServer.stop();
    }

    @Test
    public void shouldReceiveEchoResponse() throws IOException {
        try (Socket client = new Socket("localhost", PORT)) {
            client.setSoTimeout(5000);
            client.getOutputStream().write(MESSAGE.getBytes());

            byte[] buffer = new byte[1024];
            int len = client.getInputStream().read(buffer);

            byte[] response = Arrays.copyOfRange(buffer, 0, len);
            assertEquals(MESSAGE, new String(response));
        }
    }
}
