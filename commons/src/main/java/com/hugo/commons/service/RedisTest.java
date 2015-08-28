package com.hugo.commons.service;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.Arrays;
import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.Transaction;

/**
 * @Author : hugo
 * @Date : 15/3/25 上午10:22.
 * <p/>
 * 三种执行顺序可供选择：默认（MethodSorters.DEFAULT），
 * 按方法名（MethodSorters.NAME_ASCENDING），
 * 和 JVM（MethodSorters.JVM）。
 * <p/>
 * 2. MethodSorters.NAME_ASCENDING （推荐）
 * 按方法名称的进行排序，由于是按字符的字典顺序，所以以这种方式指定执行顺序会始终保持一致；
 * 不过这种方式需要对测试方法有一定的命名规则，如 测试方法均以testNNN开头（NNN表示测试方法序列号 001-999）
 */

/**
 * 在这里对jedis关于事务、管道和分布式的调用方式做一个简单的介绍和对比：
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RedisTest {

    private static Jedis jedis;
    private static ShardedJedis sharding;
    private static ShardedJedisPool pool;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

        JedisShardInfo jedisShardInfo1 = new JedisShardInfo("localhost", 6379);
        JedisShardInfo jedisShardInfo2 = new JedisShardInfo("localhost", 6379);
        JedisShardInfo jedisShardInfo3 = new JedisShardInfo("localhost", 6379);

        jedisShardInfo1.setPassword("hy");
        jedisShardInfo2.setPassword("hy");
        jedisShardInfo3.setPassword("hy");

        //使用相同的ip:port,仅作测试
        List<JedisShardInfo> shards = Arrays.asList(jedisShardInfo1, jedisShardInfo2);


        jedis = new Jedis(jedisShardInfo3);
        sharding = new ShardedJedis(shards);

        pool = new ShardedJedisPool(new JedisPoolConfig(), shards);
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        jedis.disconnect();
        sharding.disconnect();
        pool.destroy();
    }

    /**
     * 普通同步方式
     * 最简单和基础的调用方式
     */

    @Test
    public void test1Normal() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            String result = jedis.set("n" + i, "n" + i);
        }
        long end = System.currentTimeMillis();
        System.out.println("Simple SET: " + ((end - start) / 1000.0) + " seconds");
    }

    /**
     * 事务方式(Transactions)
     * redis的事务很简单，他主要目的是保障，一个client发起的事务中的命令可以连续的执行，而中间不会插入其他client的命令。
     * 我们调用jedis.watch(…)方法来监控key，如果调用后key值发生变化，则整个事务会执行失败。
     * 另外，事务中某个操作失败，并不会回滚其他操作。这一点需要注意。还有，我们可以使用discard()方法来取消事务。
     */

    @Test
    public void test2Trans() {
        long start = System.currentTimeMillis();
        Transaction tx = jedis.multi();
        for (int i = 0; i < 100000; i++) {
            tx.set("t" + i, "t" + i);
        }
        //System.out.println(tx.get("t1000").get());

        List<Object> results = tx.exec();
        long end = System.currentTimeMillis();
        System.out.println("Transaction SET: " + ((end - start) / 1000.0) + " seconds");
    }

    /**
     * 管道(Pipelining)
     * 有时，我们需要采用异步方式，一次发送多个指令，不同步等待其返回结果。
     * 这样可以取得非常好的执行效率。这就是管道，调用方法如下：
     */

    @Test
    public void test3Pipelined() {
        Pipeline pipeline = jedis.pipelined();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            pipeline.set("p" + i, "p" + i);
        }
        System.out.println(jedis.get("p1000"));
        List<Object> results = pipeline.syncAndReturnAll();
        long end = System.currentTimeMillis();
        System.out.println("Pipelined SET: " + ((end - start) / 1000.0) + " seconds");
    }

    /**
     * 管道中调用事务
     * 就Jedis提供的方法而言，是可以做到在管道中使用事务，其代码如下：
     * 但是经测试（见本文后续部分），发现其效率和单独使用事务差不多，甚至还略微差点。
     */

    @Test
    public void test4combPipelineTrans() {
        long start = System.currentTimeMillis();
        Pipeline pipeline = jedis.pipelined();
        pipeline.multi();
        for (int i = 0; i < 100000; i++) {
            pipeline.set("" + i, "" + i);
        }
        pipeline.exec();
        List<Object> results = pipeline.syncAndReturnAll();
        long end = System.currentTimeMillis();
        System.out.println("Pipelined transaction: " + ((end - start) / 1000.0) + " seconds");
    }

    /**
     * 分布式直连同步调用
     * 这个是分布式直接连接，并且是同步调用，每步执行都返回执行结果。类似地，还有异步管道调用。
     */

    @Test
    public void test5shardNormal() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            String result = sharding.set("sn" + i, "n" + i);
        }
        long end = System.currentTimeMillis();
        System.out.println("Simple@Sharing SET: " + ((end - start) / 1000.0) + " seconds");
    }

    /**
     * 分布式直连异步调用
     */

    @Test
    public void test6shardPipelined() {
        ShardedJedisPipeline pipeline = sharding.pipelined();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            pipeline.set("sp" + i, "p" + i);
        }
        List<Object> results = pipeline.syncAndReturnAll();
        long end = System.currentTimeMillis();
        System.out.println("Pipelined@Sharing SET: " + ((end - start) / 1000.0) + " seconds");
    }

    /**
     * 分布式连接池同步调用
     * 如果，你的分布式调用代码是运行在线程中，那么上面两个直连调用方式就不合适了，
     * 因为直连方式是非线程安全的，这个时候，你就必须选择连接池调用。
     */
    @Test
    public void test7shardSimplePool() {
        ShardedJedis one = pool.getResource();

        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            String result = one.set("spn" + i, "n" + i);
        }
        long end = System.currentTimeMillis();
        pool.returnResource(one);
        System.out.println("Simple@Pool SET: " + ((end - start) / 1000.0) + " seconds");
    }

    /**
     * 分布式连接池异步调用
     */

    @Test
    public void test8shardPipelinedPool() {
        ShardedJedis one = pool.getResource();

        ShardedJedisPipeline pipeline = one.pipelined();

        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            pipeline.set("sppn" + i, "n" + i);
        }
        List<Object> results = pipeline.syncAndReturnAll();
        long end = System.currentTimeMillis();
        pool.returnResource(one);
        System.out.println("Pipelined@Pool SET: " + ((end - start) / 1000.0) + " seconds");
    }


    /**
     * 需要注意的地方
     * 1.事务和管道都是异步模式。在事务和管道中不能同步查询结果。比如下面两个调用，都是不允许的：
     *
     *   Transaction tx = jedis.multi();
     *   for (int i = 0; i < 100000; i++) {
     *      tx.set("t" + i, "t" + i);
     *   }
     *   System.out.println(tx.get("t1000").get());  //不允许
     *   List<Object> results = tx.exec();
     *
     *
     *   Pipeline pipeline = jedis.pipelined();
     *   long start = System.currentTimeMillis();
     *   for (int i = 0; i < 100000; i++) {
     *      pipeline.set("p" + i, "p" + i);
     *   }
     *   System.out.println(pipeline.get("p1000").get()); //不允许
     *   List<Object> results = pipeline.syncAndReturnAll();
     *
     *
     * 2.事务和管道都是异步的，个人感觉，在管道中再进行事务调用，没有必要，不如直接进行事务模式。
     * 3.分布式中，连接池的性能比直连的性能略好(见后续测试部分)。
     * 4.分布式调用中不支持事务。
     * 5.因为事务是在服务器端实现，而在分布式中，每批次的调用对象都可能访问不同的机器，所以，没法进行事务。
     */
}
