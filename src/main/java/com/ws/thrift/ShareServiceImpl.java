package com.ws.thrift;

import org.apache.thrift.TException;

/**
 * 实现类
 */
public class ShareServiceImpl implements SharedService.Iface {
    public SharedStruct getStruct(int key) throws TException {
        return new SharedStruct(key,"Hello World");
    }
}
