package cn.mldn.io.file.stream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * 打印输出流<br>
 * 使用场景：只要是内容的输出都只需要使用PrintWriter、PrintStream进行操作
 * @author lishangxing
 */
public class PrintWriterTest {

	public static void main(String[] args) throws Exception {
		File file = new File("E:" + File.separator + "hello" + File.separator + "mldn.txt");
		PrintWriter pw = new PrintWriter(new FileOutputStream(file));
		pw.println("姓名：张三");
		pw.print("年龄：");
		pw.println("90");
		pw.close();
	}

}
