package com.samwang.multiThread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
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
//        job.futureTest();
//        job.testForkJoin();
        job.testForkJoinSort();
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

    public void testForkJoin(){
        double numbers[] = new double[1000];
        for(int i =  0; i < 1000; i++) {
            numbers[i] = i+1;
        }
        ForkJoinDemo demo = new ForkJoinDemo(numbers);
        FORK_JOIN_POOL.invoke(demo);
        System.out.println("END:"+demo.join());
    }

    public void testForkJoinSort() {
        int numbers[] = new int[1000];
        for(int i = 0; i < 1000; i++) {
            numbers[i] = new Random().nextInt();
        }
//        System.out.println(numbers.length);
        System.out.println(Arrays.toString(numbers));
        ForkJoinSort sort = new ForkJoinSort(numbers);
        FORK_JOIN_POOL.invoke(sort);
//        System.out.println(sort.join().length);
        System.out.println("END:"+Arrays.toString(sort.join()));
    }

}

class ForkJoinDemo extends RecursiveTask<Double>{

    //分界线，当一个数组的长度 < 1000 就不再继续拆分
    public static final int THRESHOLD = 100;
    //数组
    private double[] values;

    public ForkJoinDemo(double[] values) {
        this.values = values;
    }

    @Override
    protected Double compute() {
        if(values.length < THRESHOLD) {
            double r = 0d;
            for(Double d : values) {
                r += d;
            }
            System.out.println("result:"+ r);
            return r;
        } else {
            int mid = values.length/2;
            ForkJoinDemo first = new ForkJoinDemo(Arrays.copyOfRange(values, 0, mid));
            first.fork();
            ForkJoinDemo second = new ForkJoinDemo(Arrays.copyOfRange(values, mid, values.length));
            Double result = second.compute();
            Double join = first.join();
            return join + result;
        }
    }
}

class ForkJoinSort extends RecursiveTask<int[]> {

    //分界线，当一个数组的长度 < 1000 就不再继续拆分
    public static final int THRESHOLD = 100;

    private int[] values;

    public ForkJoinSort (int[] values) {
        this.values = values;
    }

    @Override
    protected int[] compute() {
        if(values.length < THRESHOLD) {
            int[] ints = Arrays.stream(values).sorted().toArray();
            return ints;
        }else {
            int mid = values.length / 2;
            ForkJoinSort first = new ForkJoinSort(Arrays.copyOfRange(values, 0, mid));
            first.fork();
            ForkJoinSort second = new ForkJoinSort(Arrays.copyOfRange(values, mid, values.length));
            int[] secondResult = second.compute();
            int[] firstResult = first.join();
            //两个数组混合排序
            int[] combineRsult = combineIntArray(firstResult,secondResult);
            return combineRsult;
        }
    }

    private int[] combineIntArray(int[] a1, int[] a2) {
        int result[] = new int[a1.length + a2.length];
        int a1Len = a1.length;
        int a2Len = a2.length;
        int destLen = result.length;

        for(int index = 0, a1Index = 0, a2Index = 0; index < destLen; index++) {
            int v1 = a1Index >= a1Len?Integer.MAX_VALUE: a1[a1Index];
            int v2 = a2Index >= a2Len?Integer.MAX_VALUE: a2[a2Index];
            if(v1 < v2) {
                a1Index++;
                result[index] = v1;
            }else {
                a2Index++;
                result[index] = v2;
            }
        }

        return result;

    }
}
