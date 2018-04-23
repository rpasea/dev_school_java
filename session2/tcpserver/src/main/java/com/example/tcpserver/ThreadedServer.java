package com.example.tcpserver;

import com.example.tcpserver.codec.CodecPipelineFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class ThreadedServer extends TcpServer {
    private static final Logger LOG = LoggerFactory.getLogger(TcpServer.class);

    private static final int BUFFER_SIZE = 1024;
    private int port;
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
            try {
                this.stop();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        serverSocket = new ServerSocket(port);

        this.setRunning(true);
        serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    listenLoop();
                } catch (IOException e) {
                    try {
                        stop();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        serverThread.start();

        /*
         * You should start the server socket and create the server thread, starting it with the listenLoop()
         */
    }

    private void listenLoop() throws IOException {
        while (isRunning()) {

            Socket client = serverSocket.accept();

            // accept new connections


            // after the connection was accepted, initialize a Connection object
            Connection connection = startNewConnection();


            Thread clientThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        clientLoop(connection);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            ConnectionData connData = new ConnectionData(client, serverThread);

            connections.put(connection, connData);

            clientThread.start();

            /*
             * start a new thread which will handle this new connection (use the clientLoop() method)
             * create the ConnectionData associated with this connection and store it in the connections map
             */
        }
    }

    private void clientLoop(Connection connection) throws IOException {
        // get the socket associated with this client. use the map.
        Socket socket = connections.get(connection).getSocket();
        // create a buffer and read from the socket as long as it is open
        ByteBuffer bbuffer = ByteBuffer.allocate(1024);
        byte[] buffer = new byte[1024];

        try {
            while (true) {
                int len = socket.getInputStream().read(bbuffer.array());
                if (len < 0) {
                    break;
                }
                connection.received(bbuffer);
                bbuffer.clear();
            }
        }catch (IOException e){
        } finally{
            closeConnection(connection);
        }
        // remember to handle closed sockets

        closeConnection(connection);
    }

    @Override
    public void stop() throws InterruptedException {
        this.setRunning(false);

        try{
            if (serverSocket != null) serverSocket.close();
        } catch (IOException ignored){
        }

        try{
            serverThread.join();
            }catch (InterruptedException e) {
            e.printStackTrace();
        }
        // close all the connection sockets

        // close the server socket

        // try to cleanly wait for serverThread to finish (use the join() method)

        // try to avoid calling serverThread.kill() or only call it as a last resort.
    }

    @Override
    public void send(Connection connection, ByteBuffer data) throws IOException {
        // retrieve the socket associated with the connection and perform a write

        Socket socket = connections.get(connection).getSocket();
        try (PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
        }
    }

    @Override
    public void closeConnection(Connection connection) throws IOException {
        // close the socket of the connection
        Socket socket = connections.get(connection).getSocket();
        socket.close();

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
