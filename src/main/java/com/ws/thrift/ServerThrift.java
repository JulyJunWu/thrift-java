package com.ws.thrift;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;

/**
 * 测试Thrift 服务端
 */
public class ServerThrift {

    public static void main(String[] args) throws Exception {
        //  创建函数处理器
        TProcessor tprocessor = new SharedService.Processor<SharedService.Iface>(new ShareServiceImpl());
        //  监听端口
//        TServerSocket serverSocket = new TServerSocket(9090);
        TNonblockingServerSocket serverSocket = new TNonblockingServerSocket(9090);
        TBinaryProtocol.Factory factory = new TBinaryProtocol.Factory();
        //  单线程版
        //  singleServer(serverSocket,factory,tprocessor);
        //  多线程版
        threadPollServer(serverSocket, factory, tprocessor);
        System.out.println("服务关闭");
    }

    /**
     * 单线程版
     *
     * @param serverSocket
     * @param factory
     * @param tprocessor
     */
    public static void singleServer(TNonblockingServerTransport serverSocket, TBinaryProtocol.Factory factory, TProcessor tprocessor) {
        //  参数设置
        TServer.Args tArgs = new TServer.Args(serverSocket);
        //  设置处理器
        tArgs.processor(tprocessor);
        //  协议
        tArgs.protocolFactory(factory);
        //  单线程版
        TSimpleServer tSimpleServer = new TSimpleServer(tArgs);
        System.out.println("启动成功");
        //  启动服务
        tSimpleServer.serve();
    }

    /**
     * IO多路复用
     *
     * @param serverSocket
     * @param factory
     * @param tprocessor
     */
    public static void threadPollServer(TNonblockingServerTransport serverSocket, TBinaryProtocol.Factory factory, TProcessor tprocessor) {
        //  创建服务
        TThreadedSelectorServer.Args argsT = new TThreadedSelectorServer.Args(serverSocket);
        argsT.transportFactory(new TFramedTransport.Factory());
        argsT.protocolFactory(factory);
        argsT.processor(tprocessor);
        // io线程
        argsT.selectorThreads(Runtime.getRuntime().availableProcessors() * 2);
        // 业务线程
        argsT.workerThreads(Runtime.getRuntime().availableProcessors() * 5);
        TServer server = new TThreadedSelectorServer(argsT);
        server.serve();
    }

}
