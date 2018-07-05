package com.example.demo_consumers.test;



/**
 * @author zhuxiujie
 * @since 2018/2/19
 */

public class InvkeCountTest {

    public static int MaxCount = 5000;
    public static int MaxThread = 5000;

    public static void main(String[] args) {
        //TestJob:26814ms ================================================
        //TestJob.main(args);
        //TestJobMono:
        TestJobMono.main(args);
    }

}
