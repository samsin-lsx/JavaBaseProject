package cn.mldn.io.exercise.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class InputUtil {
	public static final BufferedReader INPUT = new BufferedReader(new InputStreamReader(System.in));
	
	private InputUtil() {}
	
	/**
	 * 实现键盘输入字符串的操作
	 * @param prompt 提示文字
	 * @return 返回输入的字符串
	 */
	public static String getString(String prompt) {
		String str = null;
		boolean flag = true;
		while (flag) {
			System.out.print(prompt);
			try {
				str = INPUT.readLine();
				if (!"".equals(str)) {
					flag = false;
				} else {
					System.out.println("输入的内容不允许为空！");
				}
			} catch (IOException e) {
				System.out.println("输入的内容不允许为空！");
			}
		}
		return str;
	}
	
	/**
	 * 实现键盘接收数字的操作
	 * @param prompt 提示文字
	 * @return 返回输入的数字
	 */
	public static int getInt(String prompt) {
		int num = 0;
		boolean flag = true;
		while (flag) {
			@SuppressWarnings("resource")
			Scanner scan = new Scanner(System.in);
			System.out.print(prompt);
			if (scan.hasNext("\\d+")) {
				num = Integer.parseInt(scan.next("\\d+"));
				flag = false;
			} else {
				System.out.println("您输入的不是数字！");
			}
		}
		return num;
	}
	
	/**
	 * 实现键盘接收数字的操作
	 * @param 输入的第几次
	 * @return 返回输入的数字
	 */
	public static int getInt(int times) {
		int num = 0;
		boolean flag = true;
		while (flag) {
			System.out.print("请输入第" + times + "个数字：");
			String str = null;
			try {
				str = INPUT.readLine();
				if (!"".equals(str)) {
					if (str.matches("\\d+")) {
						num = Integer.parseInt(str);
						flag = false;
					}
				} else {
					System.out.println("输入的内容不允许为空！");
				}
			} catch (IOException e) {
				System.out.println("您输入的不是数字！");
			}
		}
		return num;
	}
}
