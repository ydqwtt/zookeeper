package com.luban.client.zkclient;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.io.IOException;

/**
 * @Auther: wei.yang.nj
 * @Date: 2019-8-13 19:45
 * @Description:
 */
public class ZkClientTest {
    public static void main(String[] args) throws IOException {
        ZkClient zkClient = new ZkClient( "127.0.0.1:2181", 5000,50000,new SerializableSerializer() );
//        zkClient.delete( "/zkData" );
        zkClient.createPersistent( "/zkData","快钱".getBytes() );
        zkClient.unsubscribeDataChanges( "/zkData", new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                System.out.println("数据被改变");
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                System.out.println("数据被删除");
            }
        } );
        System.in.read();
    }
}
