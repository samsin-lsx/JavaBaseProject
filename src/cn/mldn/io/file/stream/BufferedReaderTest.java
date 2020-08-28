package cn.mldn.io.file.stream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 缓冲输出流：此类被jdk1.5出现的Scanner类所替代
 * @author lishangxing
 */
public class BufferedReaderTest {

	public static void main(String[] args) throws IOException {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("请输入信息：");
		String msg = input.readLine();
		System.out.println("输入的内容：" + msg);
	}

}
