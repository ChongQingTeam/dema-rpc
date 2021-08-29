package com.dema.core.executor;

import com.dema.core.annotation.ServiceDema;
import com.dema.core.common.RpcDemaProperties;
import com.dema.metadata.report.ServiceMeta;
import com.dema.registry.enums.RegistryType;
import com.dema.registry.factory.RegistryFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class RpcDemaSpringExecutor implements ApplicationContextAware, InitializingBean, BeanPostProcessor {

    private String serverAddress;

    private int port;

    private  RpcDemaProperties rpcDemaProperties;;

    public RpcDemaSpringExecutor(RpcDemaProperties rpcDemaProperties) {
        this.rpcDemaProperties = rpcDemaProperties;
        this.port = port;
    }

    private static ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet(){
        new Thread(() -> {
            try {
                //初始化netty,监听端口
                this.initRpc(applicationContext);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void initRpc(ApplicationContext applicationContext) throws UnknownHostException {
        if(applicationContext != null){
            serverAddress = InetAddress.getLocalHost().getHostAddress();
            EventLoopGroup master = new NioEventLoopGroup();
            EventLoopGroup slave = new NioEventLoopGroup();

            try {
                ServerBootstrap server = new ServerBootstrap();
                server.group(master, slave)
                        .channel(NioServerSocketChannel.class)
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel socketChannel) throws Exception {
                                socketChannel.pipeline()
                                        .addLast("codec", new HttpServerCodec())
                                        .addLast("compressor", new HttpContentCompressor())   // HttpContent 压缩
                                        .addLast("aggregator", new HttpObjectAggregator(65536)); // HTTP 消息聚合
//                                        .addLast("handler", new RpcRequestHandler());      // 自定义业务逻辑处理器
                            }
                        }).childOption(ChannelOption.SO_KEEPALIVE, true);
                ChannelFuture channelFuture = server.bind(serverAddress, port);
                channelFuture.channel().closeFuture().sync();


            }catch (Exception e){
                e.printStackTrace();
            }finally {
                master.shutdownGracefully();
                slave.shutdownGracefully();
            }
        }
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(applicationContext != null){
            ServiceDema demaService = bean.getClass().getAnnotation(ServiceDema.class);
            if(demaService != null){
                ServiceMeta serviceMeta = new ServiceMeta();
                serviceMeta.setServiceAddr(serverAddress);
                serviceMeta.setServicePort(port);
                serviceMeta.setName(demaService.name());
                serviceMeta.setValue(demaService.value());
                serviceMeta.setVersion(demaService.version());
                //注册到注册中心
                RegistryFactory.getRegistryServiceInstance(rpcDemaProperties.getRegistryAddress(), RegistryType.valueOf(rpcDemaProperties.getRegistryType()));
                //本地缓存
            }
        }
        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        RpcDemaSpringExecutor.applicationContext = applicationContext;
    }
}
