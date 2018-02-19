package com.example.demo_consumers.test;

import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhuxiujie
 * @since 2018/2/19
 */

public class InvkeCountTest {

    private static String URL = "http://localhost:8080/hello";
    private static int MaxCount = 2000;
    private static int MaxThread = 300;

    private static RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {


        ExecutorService executorService = Executors.newFixedThreadPool(MaxThread);//300并发
        for (int i = 0; i < MaxCount; i++) {
            executorService.execute(new CustomRunnable(i));
        }
    }

    public static class CustomRunnable implements Runnable {

        private int index = 0;

        public CustomRunnable(int index) {
            this.index = index;
        }

        @Override
        public void run() {
            restTemplate.getForObject(URL, String.class);
            //System.out.println("index:" + index + "done");
            if (index == (MaxCount - 1)) {
                System.out.println("===============================================all is done================================================");
                System.out.println("==========================================");
                System.out.println("==========================================");
                System.out.println("==========================================");
                System.out.println("==========================================");
                System.out.println("==========================================");
                System.out.println("===============================================all is done================================================");
            }
        }
    }
}
