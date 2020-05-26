package com.ws.thrift;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;

/**
 * 测试Thrift 服务端
 */
public class ServerThrift {

    public static void main(String[] args) throws Exception {
        //  创建函数处理器
        TProcessor tprocessor = new SharedService.Processor<SharedService.Iface>(new ShareServiceImpl());
        //  监听端口
        TServerSocket serverSocket = new TServerSocket(9090);
        //  参数设置
        TServer.Args tArgs = new TServer.Args(serverSocket);
        //  设置处理器
        tArgs.processor(tprocessor);
        //  协议
        tArgs.protocolFactory(new TBinaryProtocol.Factory());
        //  创建服务
        TSimpleServer tSimpleServer = new TSimpleServer(tArgs);
        //  启动服务
        tSimpleServer.serve();
    }

}
