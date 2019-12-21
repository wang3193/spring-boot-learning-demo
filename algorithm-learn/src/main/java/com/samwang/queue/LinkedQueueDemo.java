package com.samwang.queue;

import java.util.LinkedList;

/***
 * 链表实现队列
 */
public class LinkedQueueDemo {

    public static void main(String[] args) {
        LinkedQueue<Integer> queue = new LinkedQueue();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        System.out.println("遍历队列:");
        queue.showQueue();
        System.out.println("队尾元素:"+queue.last());
        System.out.println("队首元素:"+queue.head());
        System.out.println(queue.pop());
        System.out.println(queue.pop());
        System.out.println(queue.pop());
        System.out.println(queue.pop());
    }
}

class LinkedQueue<T> {

    /***
     * 分析,链表实现需要一个头节点
     * 需要一个next元素
     * 需要一个目标元素存储数据
      */

    private Node<T> first;

    private Node<T> last;

    /***
     * 新增方法
     * @param t
     * @return
     */
     Boolean add(T t){
       if(first == null){
           first = new Node<T>(t, null);
           last = first;
       }else {
           Node tmp = new Node<T>(t, null);
           last.next = tmp;
           last = tmp;
       }
       return true;
    }

    T pop(){
        if(isEmpty()){
            return null;
        }
        T tmp = first.getItem();
        first = first.next;
        return tmp;
    }

    T head(){
        if(isEmpty()){
            return null;
        }
        return first.getItem();
    }

    T last(){
        if(isEmpty()){
            return null;
        }
        return last.getItem();
    }

    Boolean isEmpty(){
        return first == null;
    }

    void showQueue(){
        Node tmp = first;
        while( tmp != null){
            System.out.println(tmp.getItem());
            tmp = tmp.next;
        }
    }
}

class Node<T> {
    T item;
    Node<T> next;

    Node(T item, Node next){
       this.item = item;
       this.next = next;
    }

    T getItem(){
        return item;
    }
}
