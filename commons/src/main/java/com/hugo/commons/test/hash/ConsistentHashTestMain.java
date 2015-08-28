package com.hugo.commons.test.hash;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * @Author : hugo
 * @Date : 15/3/14 下午1:33.
 */
public class ConsistentHashTestMain {
    public static void main(String[] args) {
        Set<String> nodes = Sets.newHashSet();
        nodes.add("A");
        nodes.add("B");
        nodes.add("C");
        ConsistentHash<String> consistentHash = new ConsistentHash<String>(new HashFunction(), 160, nodes);
        consistentHash.add("D");
        System.out.println(consistentHash.getSize());  //640
        System.out.println(consistentHash.get("test5"));
    }

}
