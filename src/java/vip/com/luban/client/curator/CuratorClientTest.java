package com.luban.client.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * @Auther: wei.yang.nj
 * @Date: 2019-8-14 14:31
 * @Description:
 */
public class CuratorClientTest {
    public static void main(String[] args) throws Exception {
//        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181")
//                .sessionTimeoutMs(30000)
//                .connectionTimeoutMs(30000)
//                .canBeReadOnly(false)
//                .retryPolicy(new ExponentialBackoffRetry(1000, Integer.MAX_VALUE))
//                .namespace("")
//                .defaultData(null)
//                .build();
        CuratorFramework client = CuratorFrameworkFactory.newClient( "127.0.0.1:2181", new RetryNTimes(10,2000  ) );

        client.start();
        client.create().withMode( CreateMode.PERSISTENT ).forPath( "/luban","test".getBytes() );



        NodeCache nodeCache = new NodeCache( client,"/luban" );
        nodeCache.start();
        nodeCache.getListenable().addListener( new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("change data");
            }
        } );
        System.in.read();
    }
}
