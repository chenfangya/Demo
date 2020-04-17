package com.cfy.mianshi;

import java.util.ArrayList;
import java.util.List;

/**
*@author    created by ChenFangYa
*@date  2020年1月15日---上午10:56:04
*@action
*/
public class Demo4 {
	
	/*
	 * double N = 1e5; // limit for array size int n; // array size int[] t = new
	 * Array[2 * N] {};
	 * 
	 * void build() { // build the tree for (int i = n - 1; i > 0; --i) t[i] =
	 * t[i<<1] + t[i<<1|1]; }
	 * 
	 * void modify(int p, int value) { // set value at position p for (t[p += n] =
	 * value; p > 1; p >>= 1) t[p>>1] = t[p] + t[p^1]; }
	 * 
	 * int query(int l, int r) { // sum on interval [l, r) int res = 0; for (l += n,
	 * r += n; l < r; l >>= 1, r >>= 1) { if (l&1) res += t[l++]; if (r&1) res +=
	 * t[--r]; } return res; }
	 */
	
	public static void main(String[] args) throws InterruptedException {
//		System.out.println(1 << 30);
//		System.out.println(Integer.MAX_VALUE/2);
//		
		List<Integer> list = new ArrayList<>();
		list.add(1);
		list.add(2);
		list.add(3);
		List<Integer> temp = list.subList(0, 2);
		System.out.println(temp);
		list.removeAll(temp);
		System.out.println(temp);
		System.out.println(list);
		
//		System.out.println(9 >> 1);
	}
}
