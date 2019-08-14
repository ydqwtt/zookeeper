package com.luban.client.curator;

import com.google.common.collect.Lists;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.retry.RetryNTimes;

import javax.sound.midi.Soundbank;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: wei.yang.nj
 * @Date: 2019-8-14 15:24
 * @Description:
 */
public class LeadLatchExample {
    public static void main(String[] args) throws Exception {
        List<CuratorFramework> curatorFrameworkList = Lists.newArrayList();
        List<LeaderLatch> leaderLatchList = Lists.newArrayList();

        for (int i = 0; i < 10; i++) {
            CuratorFramework client = CuratorFrameworkFactory.newClient( "127.0.0.1:2181", new RetryNTimes(10,2000  ) );
            curatorFrameworkList.add(client);
            client.start();

            LeaderLatch leaderLatch = new LeaderLatch(client,  "/LeaderLatch",client+"#"+i);
            leaderLatchList.add(  leaderLatch);
            leaderLatch.start();
        }
        TimeUnit.SECONDS.sleep( 5 );
        for (LeaderLatch leaderLatch : leaderLatchList) {
            if(leaderLatch.hasLeadership()){
                System.out.println("当前是领导节点:"+leaderLatch.getId());
                break;
            }
        }
        System.in.read();

        for (CuratorFramework curatorFramework : curatorFrameworkList) {
            curatorFramework.close();
        }
    }
}
