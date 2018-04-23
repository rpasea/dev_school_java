package com.example.tcpserver;

import com.example.tcpserver.codec.Codec;
import com.example.tcpserver.codec.CodecPipeline;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// Not the runner
@RunWith(MockitoJUnitRunner.class)
public class ConnectionTest {
    private static final String REQUEST = "mock_request";
    private static final ByteBuffer SERIALIZED_REQUEST = ByteBuffer.wrap(new byte[]{-12, -34, 0, 34, 126});

    // You can control the behavior of Mock objects and inspect their interactions
    @Mock
    private TcpServer server;
    @Mock
    private CodecPipeline codecPipeline;

    //the handler should echo back the request
    @Mock
    private MessageHandler handler;
    @Mock
    private Codec codec;

    // The Object under test
    @InjectMocks
    private Connection connection;

    @Before
    public void setup() {
        when(codecPipeline.getReadOrder()).thenReturn(Collections.singletonList(codec).iterator());

        when(codec.decode(SERIALIZED_REQUEST)).thenReturn(Arrays.asList(REQUEST));
        when(handler.handle(REQUEST)).thenReturn(Collections.singletonList(REQUEST));

        when(codecPipeline.getWriteOrder()).thenReturn(Collections.singleton(codec).iterator());

        when(codec.encode(REQUEST)).thenReturn(SERIALIZED_REQUEST);


        //when(server.send());
        /*
         * You should use Mockito to correctly wire the mocks so you can implement your unit test
         */
    }

    @Test
    public void whenReceivingMessageShouldDecodeWithCodecsCallHandlerAndSendResponse() {
        connection.received(SERIALIZED_REQUEST);

        verify(server).send(connection, SERIALIZED_REQUEST);
    }

}
