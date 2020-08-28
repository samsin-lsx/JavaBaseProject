package cn.mldn.io.file.stream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * 字节内存操作流：实现小写字母转大写的操作
 * @author lishangxing
 */
public class ByteArrayStreamTest {

	public static void main(String[] args) throws Exception {
		String str = "www.samsin.org";
		InputStream input = new ByteArrayInputStream(str.getBytes()); // 将字符串读取到内存中
		ByteArrayOutputStream output = new ByteArrayOutputStream(); // 读取内存中的数据
		int data = 0;
		while ((data = input.read()) != -1) { // 每次读取一个字节
			output.write(Character.toUpperCase(data)); // 保存数据
		}
		byte[] results = output.toByteArray(); // 使用子类自己的扩展功能
		System.out.println(new String(results)); // 自己处理字节数据
		input.close();
		output.close();
	}

}
