package cn.mldn.io.file;

import java.io.File;

/**
 * 使用递归实现指定目录下的文件进行重命名操作
 * @author lishangxing
 */
public class FileRenameTest {
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		File file = new File("E:\\源代码比对拷贝\\");
		renameDir(file);
		long end = System.currentTimeMillis();
		System.out.println("重命名【" + file.toPath() + "】下的文件共花费的时间：" + (end - start));
	}
	public static void renameDir(File file) {
		if (file.isDirectory()) { // 是一个目录
			File[] results = file.listFiles();
			for (int x = 0; x < results.length; x++) {
				renameDir(results[x]);
			}
		} else { // 是一个文件则必须进行重命名操作
			String fileName = null;
			if (file.getName().contains(".")) {
				fileName = file.getName().substring(0, file.getName().lastIndexOf(".")) + ".txt";
			} else {
				fileName = file.getName() + ".txt";
			}
			File newFile = new File(file.getParentFile(), fileName);
			file.renameTo(newFile);
		}
	}
}
