package com.tuling.jucdemo.sync;

import org.openjdk.jol.info.ClassLayout;

import com.tuling.jucdemo.factory.UnsafeFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Fox
 */
@Slf4j
public class SyncDemo2 {

    private static int counter = 0;



    public static void main(String[] args) throws InterruptedException {
        int[] nums = {1,25,7,8,9,3,4};
        Arrays.sort(nums);
        System.out.println(Arrays.toString(nums));
        System.out.println(Arrays.binarySearch(nums,25));
        int[] nums1 = Arrays.copyOf(nums,3);
        System.out.println(Arrays.toString(nums1));

        List<String> list = new ArrayList<>();
        list.add("i");
        list.add("g");
        list.add("b");
        list.add("t");
        list.add("i");
        System.out.println(list.toString());
        Collections.sort(list);
        System.out.println("max:"+Collections.max(list));

        Collections.replaceAll(list,"i","q");
        System.out.println(list.toString());

        Collections.fill(list,"i");
        System.out.println(list.toString());
    }
}
