package com.tuling.jucdemo.sync;

import org.openjdk.jol.info.ClassLayout;

import com.tuling.jucdemo.factory.UnsafeFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Fox
 */
@Slf4j
public class SyncDemo2 {

    private static int counter = 0;



    public static void main(String[] args) throws InterruptedException {
        String url = "https://psfile.ppdai.com/image/2302161703517401886061420.pdf";
        System.out.println(url.split("com/")[1]);
        String date = "2022-04";
        System.out.println(date.split("-").length);
        ConcurrentHashMap map = new ConcurrentHashMap();
        map.get("");
        map.remove("");
        map.put("","");
    }
}
