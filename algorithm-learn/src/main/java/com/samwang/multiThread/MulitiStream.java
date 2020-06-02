package com.samwang.multiThread;

import java.util.stream.LongStream;
import java.util.stream.Stream;

public class MulitiStream {

    public static void main(String[] args) {
//        System.out.println(sum(100));
//        System.out.println(parallelsum(100000));
        long[] numbers = new long[100000];
        for(int i = 0; i < 100000; i++) {
            numbers[i] = i;
        }
        System.out.println(parallelsum2(numbers));
    }

    //单线程
    public static long sum (long n) {
        return Stream.iterate(1l, i -> i + 1).limit(n).reduce(0l,Long::sum);
    }

    //并行流
    //iteraate生成的流很难分解,Integer装箱类型也会影响效率,考虑使用IntStream
    public static long parallelsum(long n) {
        return Stream.iterate(1l, i-> i+1).limit(n).parallel().reduce(0l, Long::sum);
    }

    public static long parallelsum2(long[] numbers) {
        return LongStream.of(numbers).parallel().reduce(0l, Long:: sum);
    }

}
