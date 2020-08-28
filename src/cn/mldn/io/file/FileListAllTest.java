package cn.mldn.io.file;

import java.io.File;

/**
 * 列出指定目下的所有文件信息
 * @author lishangxing
 */
public class FileListAllTest {
	public static void main(String[] args) {
		File file = new File("E:" + File.separator + "源代码比对拷贝" + File.separator);
		listDir(file);
	}
	public static void listDir(File file) {
		if (file.isDirectory()) { // 如果是一个目录
			File[] results = file.listFiles();
			if (results != null) {
				for (int x = 0; x < results.length; x++) {
					listDir(results[x]);
				}
			}
		}
		System.out.println(file);
	}
}
