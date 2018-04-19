package com.example;

import com.example.tcpserver.MessageHandler;
import com.example.tcpserver.NioServer;
import com.example.tcpserver.TcpServer;
import com.example.tcpserver.ThreadedServer;
import com.example.tcpserver.codec.CodecPipeline;
import com.example.tcpserver.codec.CodecPipelineFactory;
import com.example.tcpserver.codec.LengthFieldCodec;
import com.example.tcpserver.codec.StringCodec;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) throws IOException {


        TcpServer server = new NioServer(8080, new CodecPipelineFactory() {
            @Override
            public CodecPipeline newCodecPipeline() {
                CodecPipeline pipeline = new CodecPipeline();
                //pipeline.addCodec(new LengthFieldCodec());
                pipeline.addCodec(new StringCodec());
                return  pipeline;
            }
        }, new MessageHandler<String, String>() {

            @Override
            public List<String> handle(String message) {
                return Collections.singletonList(message);
            }
        });

        server.start();

        Scanner stdin = new Scanner(System.in);
        System.out.println("Server started, press enter to exit");
        String line = stdin.nextLine();
        System.out.println("Stopping server...");
        server.stop();
    }
}
