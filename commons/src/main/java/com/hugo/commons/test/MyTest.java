package com.hugo.commons.test;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

import java.lang.reflect.Constructor;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author : hugo
 * @Date : 15/3/4 下午3:31.
 */
public class MyTest {

    public static void main(String[] args) {

        System.out.println(MyTest.getServerIps());

//        new MyTest().test4();
    }

    public void test() {
        System.out.println(DateTime.now().toString());
        System.out.println(DateTime.now().toString(ISODateTimeFormat.hourMinute()));
        System.out.println(DateTime.now().toString(ISODateTimeFormat.dateHour()));
        System.out.println(DateTime.now().toString(ISODateTimeFormat.hourMinuteSecondMillis()));
        System.out.println(DateTime.now().toString(ISODateTimeFormat.dateHourMinuteSecond()));
    }

    public void test1() {
        try {
            Class<A> aClass = (Class<A>) Class.forName("com.hugo.commons.test.A");
            A a1 = aClass.newInstance();
            Constructor constructor = A.class.getConstructor(Integer.class);
            A a2 = (A) constructor.newInstance(10);
            Class<A> aClass1 = (Class<A>) ClassLoader.getSystemClassLoader().loadClass("com.hugo.commons.test.A");
            A a3 = aClass1.newInstance();

            System.out.println(a1);
            System.out.println(a2);
            System.out.println(a3);

            Map<String, String> hashMap = Maps.newHashMap();

            hashMap.put("sf", "sf");
            hashMap.put("sf", "sf");
            hashMap.put("sf", "sf");
            hashMap.put("sf", "sf");
            hashMap.put("sf1", "sf");

            System.out.printf(hashMap.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void test2() {
        System.out.println(Math.floor(7.5));
        System.out.println(Math.ceil(8.1));
        System.out.println((int) (Math.random() * 10));
        System.out.println(new Random().nextInt(10));
        System.out.println(Math.round(-11.61));//它表示“四舍五入”，算法为Math.floor(x+0.5)，即将原来的数字加上0.5后再向下取整
        System.out.println(Math.round(11.5));
        System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
    }

    public void test3() {
        int[] arr = {1, 2, 3, 4, 5};
        int[] arr1 = Arrays.copyOf(arr, arr.length + 1);
        arr1[5] = 6;
        for (int i = 0; i < arr1.length; i++) {
            System.out.print(arr1[i] + " ");
        }
        System.out.println("arr1.length" + arr1.length);
    }


    public void test4() {

        List<SortObject> lists = Lists.newArrayListWithCapacity(5);

        for (int i = 5; i > 0; i--) {
            lists.add(new SortObject("name" + i, i));
        }

        Collections.sort(lists, new Comparator<SortObject>() {

            @Override
            public int compare(SortObject o1, SortObject o2) {

                return o1.getGender().compareTo(o2.getGender());
            }
        });

        for (SortObject sortObject : lists) {
            System.out.println(sortObject.getName() + "  " + sortObject.getGender());
        }

    }


    private static String getServerIps() {
        Enumeration<NetworkInterface> netInterfaces = null;
        List<String> ips = Lists.newArrayListWithCapacity(4);
        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = netInterfaces.nextElement();
                Enumeration<InetAddress> ipss = ni.getInetAddresses();
                while (ipss.hasMoreElements()) {
                    String ip = ipss.nextElement().getHostAddress();
                    if ((!"127.0.0.1".equals(ip)) && (!ips.contains(ip))
                            && match(ip, "\\d+\\.\\d+\\.\\d+\\.\\d+")) {
                        ips.add(ip);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringBuilder buf = new StringBuilder(64);
        if (ips.size() <= 0) {
            buf.append("?");
        } else {
            for (int i = 0; i < ips.size(); i++) {
                if (i != 0) {
                    buf.append(",");
                }
                buf.append(ips.get(i));
            }
        }
        buf.append("");
        return buf.toString();
    }

    /**
     * src为空则直接返回false
     */
    public static boolean match(String src, String regex) {
        if (src == null) {
            return false;
        }
        if (regex == null) {
            return false;
        }
        Matcher matcher = Pattern.compile(regex).matcher(src);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

}


class SortObject {

    private String name;
    private Integer gender;

    public SortObject(String name, Integer gender) {
        this.name = name;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }
}



















