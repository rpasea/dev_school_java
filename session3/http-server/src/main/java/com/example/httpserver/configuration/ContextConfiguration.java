package com.example.httpserver.configuration;

import com.example.httpserver.model.HttpRequestCodec;
import com.example.httpserver.model.HttpResponseCodec;
import com.example.tcpserver.MessageHandler;
import com.example.tcpserver.NioServer;
import com.example.tcpserver.TcpServer;
import com.example.tcpserver.codec.CodecPipeline;
import com.example.tcpserver.codec.CodecPipelineFactory;
import com.example.tcpserver.codec.StringCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.Collections;
import java.util.List;

@Configuration
@ComponentScan(basePackages = "com.example.httpserver")
@PropertySource("application.properties")
public class ContextConfiguration {
    @Autowired
    private Environment environment;

    @Bean
    public CodecPipelineFactory codecPipelineFactory() {

        return new CodecPipelineFactory() {
            @Override
            public CodecPipeline newCodecPipeline() {
                CodecPipeline pipeline = new CodecPipeline();
                pipeline.addCodec(new StringCodec());
                pipeline.addCodec(new HttpRequestCodec());
                pipeline.addCodec(new HttpResponseCodec());

                return pipeline;
            }
        };
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public TcpServer tcpServer(@Autowired CodecPipelineFactory codecPipelineFactory,
                               @Autowired @Qualifier("HelloWorldHandler") MessageHandler messageHandler) {
        int port = Integer.parseInt(environment.getProperty("server.port"));
        return new NioServer(port, codecPipelineFactory, messageHandler);
    }
}
