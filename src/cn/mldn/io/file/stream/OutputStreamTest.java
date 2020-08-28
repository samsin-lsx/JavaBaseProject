package cn.mldn.io.file.stream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class OutputStreamTest {

	public static void main(String[] args) {
		File file = new File("E:" + File.separator + "hello" + File.separator + "mldn.txt");
		if (file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		OutputStream output = null;
		try {
			output = new FileOutputStream(file);
			String str = "www.mldn.cn，我是一个爱学习的人。";
			output.write(str.getBytes()); // 将字符串变为字节数组
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				if (output != null) {
					output.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
