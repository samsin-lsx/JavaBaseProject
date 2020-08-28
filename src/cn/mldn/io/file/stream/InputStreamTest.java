package cn.mldn.io.file.stream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * 字节输入流读取数据
 * @author lishangxing
 */
public class InputStreamTest {
	public static void main(String[] args) {
		File file = new File("E:" + File.separator + "hello" + File.separator + "mldn.txt");
		InputStream input = null;
		try {
			input = new FileInputStream(file);
			byte[] data = new byte[1024]; // 开辟一个缓冲区读取数据
			try {
				int len = input.read(data); // 读取数据，数据全部保存在字节数组之中，返回读取个数
				System.out.println("【" + new String(data, 0, len) + "】");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
