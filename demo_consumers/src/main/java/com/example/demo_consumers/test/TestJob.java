package com.example.demo_consumers.test;

import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestJob {

    // private static String URL = "http://localhost:18080/mono";
    private static String URL = "http://localhost:18080";


    private static RestTemplate restTemplate = new RestTemplate();

    private static ExecutorService executorService = Executors.newFixedThreadPool(InvkeCountTest.MaxThread);//300并发

    private static int index=0;
    private static Date date;

    public static void main(String[] args) {

        index=0;

        date=new Date();
        for (int i = 0; i < InvkeCountTest.MaxCount; i++) {
            executorService.execute(new TestJob.CustomRunnable(i));
        }
    }

    public static class CustomRunnable implements Runnable {

        private int index = 0;

        public CustomRunnable(int index) {
            this.index = index;
        }

        @Override
        public void run() {
            String s=  restTemplate.getForObject(URL, String.class);
           // System.out.println("===================="+s+"======================");
            index++;

            if(index==InvkeCountTest.MaxCount){
                System.out.println(TestJob.class.getSimpleName()+":"+(new Date().getTime()-date.getTime())+"ms ================================================");
            }else {
               // System.out.println("==done=");
            }
        }
    }
}
