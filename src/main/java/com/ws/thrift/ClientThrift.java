package com.ws.thrift;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * 测试Thrift 客户端
 */
public class ClientThrift {

    public static void main(String[] args) throws Exception {
        TTransport transport = new TSocket("localhost", 9090, 1000);
        // 协议要和服务端一致
        TProtocol protocol = new TBinaryProtocol(transport);
        SharedService.Client client = new SharedService.Client(protocol);
        transport.open();
        SharedStruct struct = client.getStruct(88);
        System.out.println(struct.key);
        System.out.println(struct.value);
    }
}
