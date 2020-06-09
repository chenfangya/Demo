package com.cfy.mianshi;

import java.util.Arrays;

/**
 * @author created by ChenFangYa
 * @date 2020年6月4日---下午5:15:03
 * @action [1,2,3,4] [24,12,8,6]
 */
public class Demo6 {

	public static void main(String[] args) {
		int[] nums = new int[] { 1, 2, 3, 4 };
		
		int[] l = new int[nums.length];
        int[] r = new int[nums.length];
        int[] answer = new int[nums.length];

        l[0] = 1;
        for(int i = 1; i < nums.length; i++) {
            l[i] = nums[i - 1] * l[i -1];
        }
        r[nums.length - 1] = 1;
        for(int i = nums.length - 2; i >= 0; i--) {
            r[i] = nums[i +1] * r[i+1];
        }

        for(int i = 0; i < nums.length; i++) {
        	answer[i] = l[i] * r[i];
        }
//		int length = nums.length;
//
//		// L 和 R 分别表示左右两侧的乘积列表
//		int[] L = new int[length];
//		int[] R = new int[length];
//
//		int[] answer = new int[length];
//
//		// L[i] 为索引 i 左侧所有元素的乘积
//		// 对于索引为 '0' 的元素，因为左侧没有元素，所以 L[0] = 1	
//		L[0] = 1;
//		for (int i = 1; i < length; i++) {
//			L[i] = nums[i - 1] * L[i - 1];
//		}
//
//		// R[i] 为索引 i 右侧所有元素的乘积
//		// 对于索引为 'length-1' 的元素，因为右侧没有元素，所以 R[length-1] = 1
//		R[length - 1] = 1;
//		for (int i = length - 2; i >= 0; i--) {
//			R[i] = nums[i + 1] * R[i + 1];
//		}
//
//		// 对于索引 i，除 nums[i] 之外其余各元素的乘积就是左侧所有元素的乘积乘以右侧所有元素的乘积
//		for (int i = 0; i < length; i++) {
//			answer[i] = L[i] * R[i];
//		}

		Arrays.stream(answer).forEach(System.out::println);
	}
}
