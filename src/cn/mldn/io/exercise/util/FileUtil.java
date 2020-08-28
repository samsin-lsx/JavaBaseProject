package cn.mldn.io.exercise.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class FileUtil {
	/**
	 * 实现数据的追加并保存到文件的操作
	 * @param file 文件路径
	 * @param content 输入的学生成绩
	 * @return 保存成功返回true，否则返回false
	 */
	public static boolean append(File file, String content) {
		PrintStream out = null;
		try {
			out = new PrintStream(new FileOutputStream(file, true));
			out.print(content); // 输出数据
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
	/**
	 * 实现数据从文件中读取的操作
	 * @param file 文件路径
	 * @return 从文件读取的数据
	 */
	public static String load(File file) {
		Scanner scan = null;
		try {
			scan = new Scanner(file); // 文件加载
			if (scan.hasNext()) {
				String str = scan.next();
				return str;
			}
			return null;
		} catch (Exception e) {
			return null;
		} finally {
			if (scan != null) {
				scan.close();
			}
		}
	}
}
