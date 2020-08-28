package cn.mldn.io.file.stream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 文件拷贝模型
 * @author lishangxing
 */
public class FileCopyTest {
	public static void main(String[] args) throws Exception {
		if (args.length != 2) { // 程序执行出错
			System.out.println("命令执行错误，执行结构：java FileCopyTest 拷贝源文件路径 拷贝目标文件路径");
			System.exit(1);
		}
		long start = System.currentTimeMillis();
		FileUtil fu = new FileUtil(args[0], args[1]);
		if (new File(args[0]).isFile()) { // 文件拷贝
			System.out.println(fu.copyFile() ? "文件拷贝成功！" : "文件拷贝失败！");
		} else { // 目录拷贝
			System.out.println(fu.copyDir() ? "目录拷贝成功！" : "目录拷贝失败！");
		}
		long end = System.currentTimeMillis();
		System.out.println("拷贝完成的时间：" + (end - start));
	}
}
/**
 * 负责实现文件或者目录的拷贝处理
 * @author lishangxing
 */
class FileUtil {
	private File srcFile; // 源文件路径
	private File desFile; // 目标文件路径
	public FileUtil(String src, String des) {
		this(new File(src), new File(des));
	}
	public FileUtil(File srcFile, File desFile) {
		this.srcFile = srcFile;
		this.desFile = desFile;
	}
	public boolean copyDir() throws Exception {
		try {
			this.copyDirImpl(this.srcFile);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	private void copyDirImpl(File file) throws Exception {
		if (file.isDirectory()) { // 是目录
			File[] results = file.listFiles(); // 列出全部目录组成
			if (results != null) {
				for (int x = 0; x < results.length; x++) {
					this.copyDirImpl(results[x]);
				}
			}
		} else { // 是文件
			String newFilePath = file.getPath().replace(this.srcFile.getPath() + File.separator, "");
			File newFile = new File(this.desFile, newFilePath); // 拷贝的目标路径
			this.copyFileImpl(file, newFile);
		}
	}
	public boolean copyFile() throws Exception {
		if (!this.srcFile.exists()) {
			System.out.println("拷贝的源文件不存在！");
			return false;
		}
		return this.copyFileImpl(this.srcFile, this.desFile);
	}
	private boolean copyFileImpl(File srcFile, File desFile) throws Exception {
		if (!desFile.getParentFile().exists()) {
			desFile.getParentFile().mkdirs(); // 创建父目录
		}
		byte[] data = new byte[1024]; // 开辟一个读取的缓冲区
		InputStream input = null;
		OutputStream output = null;
		try {
			input = new FileInputStream(srcFile);
			output = new FileOutputStream(desFile);
			int len = 0;
			while ((len = input.read(data)) != -1) {
				output.write(data, 0, len);
			}
			return true;
		} catch (Exception e) {
			throw e;
		} finally {
			if (input != null) {
				input.close();
			}
			if (output != null) {
				output.close();
			}
		}
	}
}