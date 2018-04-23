package com.example.tcpserver;

import com.example.tcpserver.codec.CodecPipelineFactory;
import com.sun.security.ntlm.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
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
            this.stop();
        }

        /*
         * You should start the server socket and create the server thread, starting it with the listenLoop()
         */
        serverSocket = new ServerSocket(this.port);

        this.setRunning(true);
        serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    listenLoop();
                } catch (IOException e) {
                    stop();
                }
            }
        });
        serverThread.start();


    }

    private void listenLoop() throws IOException {
        while (isRunning()) {
            Socket client;

            // accept new connections
            client = serverSocket.accept();

            // after the connection was accepted, initialize a Connection object
            //conexiune proprie
            Connection connection = startNewConnection();


            Thread clientThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    clientLoop(connection);
                }
            });

           // connections.put(connection, "");

            /*
             * start a new thread which will handle this new connection (use the clientLoop() method)
             * create the ConnectionData associated with this connection and store it in the connections map
             */
            //asociere cu conexiune niste ingo de care avem nevoie (asociem cele 2 de la conn)
        }
    }

    private void clientLoop(Connection connection) {
        // get the socket associated with this client. use the map.
        //avem un map unde putem adauga chestii
        Socket socket = connections.get(connection).getSocket();
        //bytebuffer sau array si apel de read pe socket continuu
        // create a buffer and read from the socket as long as it is open
        ByteBuffer bbuffer = ByteBuffer.allocate(BUFFER_SIZE);
        byte[] buffer = new byte[BUFFER_SIZE];
        //ce se intampla cand se inchide socketul
        // remember to handle closed sockets
        try {
            while(true) {
                int len = socket.getInputStream().read(bbuffer.array());
                if (len < 0) {
                    break;
                }
                bbuffer.limit(len);
                connection.received(bbuffer);
                bbuffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        //connection.receiver(data);
        closeConnection(connection);
    }

    @Override
    public void stop() {
        this.setRunning(false);
        // close all the connection sockets
        //oprire thread (join?)
        // close the server socket

        // try to cleanly wait for serverThread to finish (use the join() method)

        // try to avoid calling serverThread.kill() or only call it as a last resort.
    }

    @Override
    public void send(Connection connection, ByteBuffer data) {
        // retrieve the socket associated with the connectiond and perform a write
        //trimite. trimite trimite trimite
    }

    @Override
    public void closeConnection(Connection connection) {

        // close the socket of the connection - iesire din loop (clientloop and some magic)

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

//java NIO

//spring TODO next time


