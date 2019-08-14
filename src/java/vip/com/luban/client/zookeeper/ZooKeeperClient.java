package com.luban.client.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @Auther: wei.yang.nj
 * @Date: 2019-8-13 17:25
 * @Description:
 */
public class ZooKeeperClient {
    public static void main(String[] args) {
        try {
            ZooKeeper client = new ZooKeeper("127.0.0.1:2181", 5000, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    System.out.println("连接的时候:"+event);
                }
            });
            Stat stat = new Stat();

            byte [] b = client.getData( "/data", new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    System.out.println("连接的时候1:"+event.getType());
                }
            } , stat);
            System.out.println(new String (b));
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
