package cn.mldn.nio.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class InputUtil {
	private static final BufferedReader KEYBOARD_INPUT = new BufferedReader(new InputStreamReader(System.in));
	
	private InputUtil() {}
	
	/**
	 * 输入数据
	 * @param prompt 提示信息
	 * @return 返回输入的字符串信息
	 * @throws Exception
	 */
	public static String getString(String prompt) throws Exception {
		boolean flag = true;
		String str = null;
		while (flag) {
			System.out.print(prompt);
			str = KEYBOARD_INPUT.readLine();
			if (str == null || "".equals(str)) {
				System.out.println("输入的信息有误，请重新输入！");
			} else {
				flag = false;
			}
		}
		return str;
	}
}
