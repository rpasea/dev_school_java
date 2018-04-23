package com.example.tcpserver;

import com.example.tcpserver.codec.CodecPipelineFactory;

import java.io.IOException;
import java.nio.ByteBuffer;

public abstract class TcpServer {
    protected CodecPipelineFactory codecPipelineFactory;
    protected MessageHandler messageHandler;
    protected int port;
    protected boolean running;

    public TcpServer(int port, CodecPipelineFactory codecPipelineFactory, MessageHandler messageHandler) {
        this.codecPipelineFactory = codecPipelineFactory;
        this.messageHandler = messageHandler;
        this.port = port;
    }

    public abstract void start() throws IOException;
    public abstract void stop() throws InterruptedException;
    public abstract void send(Connection connection, ByteBuffer data) throws IOException;
    public abstract void closeConnection(Connection connection) throws IOException;


    protected Connection startNewConnection() {
        return new Connection(this, codecPipelineFactory.newCodecPipeline(), messageHandler);
    }

    public boolean isRunning() {
        return running;
    }

    protected void setRunning(boolean running) {
        this.running = running;
    }
    protected int getPort() {
        return port;
    }
}
