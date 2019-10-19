package com.samwang.queue;

import java.util.Scanner;

/***
 * 队列是一个有序列表,可以用数组或是链表来实现
 * 遵循先入先出原则
 *
 */
public class SingleQueue {


    public static void main(String[] args) {
        ArrayQueue arr = new ArrayQueue(3);
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


/***
 * 使用数组模拟队列-编写一个ArrayQueue类
 */
class ArrayQueue {

    //表示数组的最大容量
    private int maxSize;
    //队列头
    private int front;
    //队列尾
    private int rear;
    //该数据用于存放数据,模拟队列
    private int[] arr;

    //创建队列的构造器
    public ArrayQueue(int maxSize) {
        this.maxSize = maxSize;
        front = -1;//指向队列头部,分析出front是指向队列头的前一个位置
        rear = -1;//指向队列尾,指向队列尾的数据(即是队列最后一个数据)
        arr = new int[maxSize];
    }

    public boolean isFull() {
        //如果队列尾==最大值-1,说明数据满
        return rear >= maxSize - 1;
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
       //rear后移
       rear++;
       arr[rear] = n;
    }

    //获取队列的数据,出队列
    public int getQueue(){
        //判断队列是否空
        if(isEmpty()){
            //通过抛出异常
            throw new RuntimeException("队列空,不能取数据");
        }
        //front后移
        front++;
        return arr[front];
    }

    //显示队列的所有数据
    public void showQueue(){
        //遍历
        if(isEmpty()){
            System.out.println("队列空,没有数据~");
            return;
        }
        for(int i = 0; i < arr.length; i++) {
            System.out.printf("arr[%d]=%d\n", i , arr[i]);
        }
    }


    //显示队列的头数据,注意不是取出数据
    public int headQueue() {
        //判断
        if(isEmpty()){
            System.out.println("队列空的,没有数据~");
        }
        return arr[front + 1];
    }

}
