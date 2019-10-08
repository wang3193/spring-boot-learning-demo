package com.samwang.concurrent;

import com.samwang.sys.User;

import java.util.concurrent.atomic.*;

public class atomic {

    public static void main(String[] args) {
        vars();
    }

    public static void vars(){
        AtomicBoolean atomicBoolean = new AtomicBoolean();
        System.out.println(atomicBoolean.get());
        atomicBoolean.compareAndSet(false, true);
        System.out.println(atomicBoolean.get());
        atomicBoolean.getAndSet(false);
        System.out.println(atomicBoolean.get());
        atomicBoolean.lazySet(true);
        System.out.println(atomicBoolean.get());
        AtomicBoolean trueBoolean = new AtomicBoolean(true);
        System.out.println(trueBoolean.get());
        AtomicInteger atomicInteger = new AtomicInteger();
        System.out.println(atomicInteger.get());
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(10);
        atomicIntegerArray.addAndGet(2,4);
        System.out.println(atomicIntegerArray.toString());
        AtomicLong atomicLong = new AtomicLong();
        AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = new AtomicIntegerFieldUpdater() {
            @Override
            public boolean compareAndSet(Object obj, int expect, int update) {
                return false;
            }

            @Override
            public boolean weakCompareAndSet(Object obj, int expect, int update) {
                return false;
            }

            @Override
            public void set(Object obj, int newValue) {

            }

            @Override
            public void lazySet(Object obj, int newValue) {

            }

            @Override
            public int get(Object obj) {
                return 0;
            }
        };
        AtomicLongArray atomicLongArray = new AtomicLongArray(10);
        AtomicReference<User> atomicReference = new AtomicReference();
        AtomicStampedReference<User> atomicStampedReference = new AtomicStampedReference(User.class, 10);
    }
}
