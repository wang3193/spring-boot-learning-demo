package com.samwang.queue;

import java.util.Scanner;

public class CricleQueue {

    //代码分析
    //主要需要考虑到环形如何处理尾部指针和头部指针超过数组容量的情况
    //此处使用%数组容量来解决该问题
    public static void main(String[] args) {
        ArrayQueue2 arr = new ArrayQueue2(5);
        char key = ' ';
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;
        while (loop) {
            System.out.println("s(show): 显示队列");
            System.out.println("e(exit): 退出程序");
            System.out.println("a(add): 添加数据到队列");
            System.out.println("g(get): 从队列取出数据");
            System.out.println("h(head): 查看队列头的数据");
            key = scanner.next().charAt(0);
            switch (key) {
                case 's':
                    arr.showQueue();
                    break;
                case 'a':
                    System.out.println("输出一个数");
                    int value = scanner.nextInt();
                    arr.addQueue(value);
                    break;
                case 'g':
                    int res = arr.getQueue();
                    System.out.printf("取出的数据是%d\n", res);
                    break;
                case 'h':
                    int r = arr.headQueue();
                    System.out.printf("队列头的数据是%d\n", r);
                    break;
                case 'e':
                    scanner.close();
                    loop = false;
                    break;
                default:
                    break;
            }
        }
    }

}


class ArrayQueue2{

    //表示数组的最大容量
    private int maxSize;
    //队列头
    private int front;
    //队列尾
    private int rear;
    //该数据用于存放数据,模拟队列
    private int[] arr;

    //创建队列的构造器
    public ArrayQueue2(int maxSize) {
        //因为需要在尾部预留一位,所以实际建立的数组为传入数字+1
        this.maxSize = maxSize + 1;
        front = 0;//指向队列头部,分析出front是指向队列第一个元素的位置
        rear = 0;//指向队列尾,指向队列尾元素后一个位置
        arr = new int[maxSize + 1];
    }

    public boolean isFull() {
        //如果(rear+1)%maxize == front,说明队列是满的.
        //因为是环形数组,当数组超过一轮循环时,最后的一个数据位置在第一个数据位置前一个,说明该数组已满
        return (rear + 1) % maxSize == front;
    }

    public boolean isEmpty() {
        //数据尾和数据头相等,说明数组是空的
        return rear == front;
    }


    public void addQueue(int n) {
        if(isFull()){
            System.out.println("队列满,不能加入数据~");
            return;
        }
        //此处先插入数据再将数据后移
        arr[rear] = n;
        //后移数据时要考虑超过一轮数组的情况,不能直接加1,而应该对数组长度做取摸运算,防止脚标越界
        rear = (rear + 1) % maxSize;
    }

    //获取队列的数据,出队列
    public int getQueue(){
        //判断队列是否空
        if(isEmpty()){
            //通过抛出异常
            throw new RuntimeException("队列空,不能取数据");
        }
        //此处因为front为数组第一个数据脚标需要先取出数据再做后移操作
        int tmp = arr[front];
        //front 后移时也要考虑脚标越界情况,所以需要做取摸操作
        front = (front + 1) % maxSize;
        return tmp;
    }

    //显示队列的所有数据
    public void showQueue(){
        //遍历
        if(isEmpty()){
            System.out.println("队列空,没有数据~");
            return;
        }
        //此处数组前部可能有数据,所以实际操作应该从front取到rear,因为考虑到rear操过一个循环可能小于front的情况
        //取出元素有效个数的情况为(rear + maxsize - front) % maxize;
        //所以i 应该小于 front + 有效个数;
        for(int i = front; i < front + size(); i++) {
            System.out.printf("arr[%d]=%d\n", i % maxSize , arr[i % maxSize]);
        }
    }

    /***
     * 获取数组有效元素个数
     * @return
     */
    public int size(){
        return (rear + maxSize - front) % maxSize;
    }

    //显示队列的头数据,注意不是取出数据
    public int headQueue() {
        //判断
        if(isEmpty()){
            System.out.println("队列空的,没有数据~");
        }
        return arr[front];
    }


}
