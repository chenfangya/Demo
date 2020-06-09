package com.cfy.leecode.mianshi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

/**
 * @author created by ChenFangYa
 * @date 2020年6月5日---下午3:37:28
 * @action
 */
public class Demo {

	/**
	 * 实现一个算法，确定一个字符串 s 的所有字符是否全都不同。
	 */
	@Test
	public void isUnique() {
		String astr = "abca";
		boolean result = true;
//		Set<Character> set = new HashSet<>();
//		char[] arr = astr.toCharArray();
//		for (int i = 0; i < arr.length; i++) {
//			set.add(arr[i]);
//		}
//		result = Objects.equals(arr.length, set.size());

		long low64 = 0;
		long high64 = 0;

		for (char c : astr.toCharArray()) {
			if (c >= 64) {
				long bitIndex = 1L << c - 64;
				if ((high64 & bitIndex) != 0) {
					result = false;
				}

				high64 |= bitIndex;
			} else {
				long bitIndex = 1L << c;
				if ((low64 & bitIndex) != 0) {
					result = false;
				}

				low64 |= bitIndex;
			}

		}
		System.out.println(result);
	}

	/**
	 * 给定两个字符串 s1 和 s2，请编写一个程序，确定其中一个字符串的字符重新排列后，能否变成另一个字符串。
	 */
	@Test
	public void CheckPermutation() {
		String s1 = "aec";
		String s2 = "cba";
		boolean result = true;
		if (s1.length() != s2.length()) {
			result = false;
		}
		char[] a1 = s1.toCharArray();
		char[] a2 = s2.toCharArray();
		Arrays.sort(a1);
		Arrays.sort(a2);
		result = Objects.equals(String.valueOf(a1), String.valueOf(a2));
		System.out.println(result);
	}

	/**
	 * URL化。编写一种方法，将字符串中的空格全部替换为%20。假定该字符串尾部有足够的空间存放新增字符，并且知道字符串的“真实”长度。
	 * （注：用Java实现的话，请使用字符数组实现，以便直接在数组上操作。）
	 */
	@Test
	public void replaceSpaces() {
		String S = "      ab  c    ";
		int length = S.length();
		int st = 0;
		char[] val = S.toCharArray();
		while ((st < length) && (val[length - 1] <= ' ')) {
			length--;
		}
		String sb = S.substring(0, length);
		String replaceAll = sb.replaceAll(" ", "%20");
		System.out.println(replaceAll);
	}

	/**
	 * 给定一个字符串，编写一个函数判定其是否为某个回文串的排列之一。
	 * 
	 * 回文串是指正反两个方向都一样的单词或短语。排列是指字母的重新排列。
	 * 
	 * 回文串不一定是字典当中的单词。 输入："tactcoa" 输出：true（排列有"tacocat"、"atcocta"，等等）
	 */
	@Test
	public void canPermutePalindrome() {
		String s = "tactcoa";
//		Set<Character> set = new HashSet<>();
//		for (char c : s.toCharArray()) {
//			if (!set.add(c)) {
//				set.remove(c);
//			}
//		}
//		System.out.println(set.size() <= 1);

		int[] map = new int[256];
		int count = 0;
		for (char c : s.toCharArray()) {
			if ((map[c]++ & 1) == 1) {
				count--;
			} else {
				count++;
			}
		}
		System.out.println(count <= 1);
	}

	/**
	 * 字符串有三种编辑操作:插入一个字符、删除一个字符或者替换一个字符。 给定两个字符串，编写一个函数判定它们是否只需要一次(或者零次)编辑。
	 * 
	 * 输入: first = "pale" second = "ple" 输出: True
	 */
	@Test
	public static void oneEditAway(String first, String second) {
		boolean result = true;
		if (first == null || second == null) result = false;
        int len1 = first.length();
        int len2 = second.length();
        if (Math.abs(len1 - len2) > 1) result = false;
        if (len2 > len1) oneEditAway(second, first);
        
        // 保持第一个比第二个长
        for (int i = 0; i < len2; i++){
            if (first.charAt(i) != second.charAt(i)){
                // 如果是长度相同字符串，那就比较下一个，如果长度不一样，那就从该字符开始进行比较。
            	result = first.substring(i + 1).equals(second.substring(len1 == len2 ? i + 1 : i));
            }
        }
		System.out.println(result);
	}
	
	
	public static void main(String[] args) {
		oneEditAway("pale", "ple");
	}
}
