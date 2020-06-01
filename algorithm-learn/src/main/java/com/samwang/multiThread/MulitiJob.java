package com.samwang.multiThread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

public class MulitiJob {

    private static final ForkJoinPool FORK_JOIN_POOL = new ForkJoinPool(2 * 4);

    public static void main(String[] args) {
        MulitiJob job = new MulitiJob();
//        job.testCountDownLatch();
//        job.testCyclicBarrier();
//        job.testCompletionService();
//        job.testCompletableFuture();
        job.futureTest();
    }

    public void testCountDownLatch() {
        List<Integer> batchList = new ArrayList<>();
        for(int i = 0; i< 1000; i++) {
            batchList.add(i);
        }
        final CountDownLatch countDownLatch = new CountDownLatch(batchList.size());
        batchList.forEach(e -> FORK_JOIN_POOL.execute(() ->{
            System.out.println("执行数据-"+e);
            countDownLatch.countDown();
        }));
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("执行完毕");
    }

    public void testCyclicBarrier() {
        List<Integer> batchList = new ArrayList<>();
        for(int i = 0; i< 1000; i++) {
            batchList.add(i);
        }
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(batchList.size(), ()->{
            System.out.println("操作完成");
            testCyclicBarrier();
        });
        batchList.forEach(e -> FORK_JOIN_POOL.execute(() ->{
            try {
                System.out.println("执行数据-"+e);
                cyclicBarrier.await();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("异常");
            }
        }));
    }

    public void testCompletionService() {
        List<Integer> batchList = new ArrayList<>();
        for(int i = 0; i< 1000; i++) {
            batchList.add(i);
        }
        CompletionService completionService = new ExecutorCompletionService(FORK_JOIN_POOL);
        batchList.forEach(e->{
            completionService.submit(() -> {
                System.out.println("执行数据-"+ e);
                return e;
            });
        });
        batchList.forEach(e->{
            try {
                Object o = completionService.take().get();
                System.out.println(o.toString());
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } catch (ExecutionException ex) {
                ex.printStackTrace();
            }
        });
        System.out.println("处理完成");
    }

    public void testCompletableFuture() {
        List<Integer> batchList = new ArrayList<>();
        for(int i = 0; i< 1000; i++) {
            batchList.add(i);
        }
        List<CompletableFuture> futureList = new ArrayList<>();
        batchList.forEach(e->{
            final CompletableFuture future = CompletableFuture.supplyAsync(()->{
                System.out.println("处理数据-"+e);
                return e;
            }, FORK_JOIN_POOL);
            futureList.add(future);
        });
        for(CompletableFuture future : futureList) {
            try {
                System.out.println(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
//        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0])).join();
        System.out.println("处理完成");
    }

    public void futureTest() {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("future1 finished!");
            return "future1 finished!";
        });
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("future2 finished!");
            return "future2 finished!";
        });
        CompletableFuture<Void> combindFuture = CompletableFuture.allOf(future1, future2);
        try {
            System.out.println("all"+combindFuture.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("future1: " + future1.isDone() + " future2: " + future2.isDone());
    }

}

