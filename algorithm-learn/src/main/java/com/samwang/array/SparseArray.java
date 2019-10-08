package com.samwang.array;

import java.lang.reflect.Array;

/***
 * 稀疏数组
 */
public class SparseArray {

    /***
     * 稀疏数组:当数组中有多个重复值,使用稀疏数组可以节约存储空间
     * 通过记录共几行几列,有多少不同值
     * 记录不同值和位置,达到压缩数组的目的
     */


    public static void main(String[] args) {
        int [][] arr = new int [8][10];
        arr[2][3] = 1;
        arr[5][7] = 2;
        printArr(arr);
        System.out.println("=================================================");
        int [][] arr1 = compression(arr);
        printArr(arr1);
        System.out.println("=================================================");
        int [][] arr2 = recovery(arr1);
        printArr(arr2);
    }


    public static void printArr(int[][] arr){
        for(int[] a : arr){
            for(int i : a){
                System.out.printf("%d\t", i);
            }
            System.out.println();
        }
    }

    /***
     * 创建稀疏数组
     */
    public static int[][] compression(int[][] arrays){
        /***
         * 便利二维数组,获取有效数据个数
         * 依据有效数据个数创建稀疏数组
         * 将二维数组的长,宽,和有效数据个数放入稀疏数组第一行中
         * 将有效数据写入稀疏数组中
         */
        /***
         * 设定数字0为无意义数据,循环得出有意义数据的数量
         */
        int sum = 0;
        for(int[] arr : arrays){
            for (int i : arr){
               if(i != 0){
                   sum ++;
               }
            }
        }

        //创建稀疏数组
        int[][] arr = new int[sum+1][3];
        //初始化第一行数据
        arr[0][0] = arrays.length;
        arr[0][1] = arrays[0].length;
        arr[0][2] = sum;
        //创建记录有效数据个数的变量
        int count = 0;
        //遍历数组查询有效数据
        for(int i = 0; i < arrays.length; i++) {
            for (int j = 0; j < arrays[i].length; j++) {
                if (arrays[i][j] != 0) {
                    count++;
                    arr[count][0] = i;
                    arr[count][1] = j;
                    arr[count][2] = arrays[i][j];
                }
            }
        }
        return arr;
    }

    /***
     * 还原稀疏数组
     */
    public static int[][] recovery(int[][] arrays){
        /***
         * 读取稀疏数组
         * 使用稀疏数组第一行数据创建二维数组
         * 遍历稀疏数组,将有效值写入二维数组中
         */
        int[][] arr = new int [arrays[0][0]][arrays[0][1]];
        for(int i = 1; i < arrays.length; i++){
            arr[arrays[i][0]][arrays[i][1]] = arrays[i][2];
        }
        return arr;
    }

}
