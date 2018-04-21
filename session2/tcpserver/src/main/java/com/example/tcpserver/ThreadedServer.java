package com.example.tcpserver;

import com.example.tcpserver.codec.CodecPipelineFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ThreadedServer extends TcpServer {
    private static final Logger LOG = LoggerFactory.getLogger(TcpServer.class);

    private static final int BUFFER_SIZE = 1024;
    private ServerSocket serverSocket;
    private Thread serverThread;

    // A map containing useful information for each connection (e.g. the socket)
    private Map<Connection, ConnectionData> connections = new HashMap<>();

    public ThreadedServer(int port, CodecPipelineFactory codecPipelineFactory, MessageHandler messageHandler) {
        super(port, codecPipelineFactory, messageHandler);
    }

    @Override
    public void start() throws IOException {
        if (this.isRunning()) {
            this.stop();
        }

        /*
         * You should start the server socket and create the server thread, starting it with the listenLoop()
         */

        serverSocket = new ServerSocket(port);
        serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                listenLoop();
            }
        });
        serverThread.start();


        System.out.printf("Listening on %s\n", serverSocket.toString());
    }

    private void listenLoop() {
        setRunning(true);
        while (isRunning()) {

            // accept new connections
            Socket client = null;
            try {
                client = serverSocket.accept();
            } catch (IOException e) {
                return;
            }

            // after the connection was accepted, initialize a Connection object
            Connection connection = startNewConnection();

            Thread clientThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    clientLoop(connection);
                }
            });
            connections.put(connection, new ConnectionData(client, clientThread));
            clientThread.start();
        }

    }

    private void clientLoop(Connection connection) {
        // remember to handle closed sockets

        // get the socket associated with this client. use the map.
        Socket socket = connections.get(connection).getSocket();

        // create a buffer and read from the socket as long as it is open
        byte[] buff = new byte[1024];

        while (true) {
            int len = 0;
            try {
                len = socket.getInputStream().read(buff);
            } catch (IOException e) {
                // does this mean that socket was closed?
                return;
            }
            if (len < 0) {
                break;
            }

            ByteBuffer bbuff = ByteBuffer.wrap(Arrays.copyOfRange(buff, 0, len));
            buff = new byte[1024];
            connection.received(bbuff);
        }

    }

    @Override
    public void stop() {
        this.setRunning(false);
        // close all the connection sockets

        for (Map.Entry<Connection, ConnectionData> entry : connections.entrySet())
            closeConnection(entry.getKey());

        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void send(Connection connection, ByteBuffer data) {
        try {
            connections.get(connection).getSocket().getOutputStream().write(data.array());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeConnection(Connection connection) {
        // close the socket of the connection and cleanly close the client thread
        try {
            connections.get(connection).getSocket().close();
            connections.remove(connection);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ConnectionData {
        private Socket socket;
        private Thread thread;

        public ConnectionData(Socket socket, Thread thread) {
            this.socket = socket;
            this.thread = thread;
        }

        public Socket getSocket() {
            return socket;
        }

        public Thread getThread() {
            return thread;
        }
    }
}
