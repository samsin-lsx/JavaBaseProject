package cn.mldn.nio.channel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannerTest {
	public static void main(String[] args) {
		File file = new File("E:" + File.separator + "hello" + File.separator + "mldn.txt");
		FileInputStream input = null;
		ByteArrayOutputStream bos = null;
		FileChannel channel = null;
		try {
			input = new FileInputStream(file);
			channel = input.getChannel(); // 获取文件通道
			ByteBuffer buffer = ByteBuffer.allocate(20); // 开辟缓冲区大小
			bos = new ByteArrayOutputStream(); // 实例化内存输出流
			while (channel.read(buffer) != -1) { // 读取缓冲区中的数据
				buffer.flip(); // 重设缓冲区
				while (buffer.hasRemaining()) { // 将缓冲区中的所有数据输出
					bos.write(buffer.get()); // 将读取到数据写入到内存流中
				}
				buffer.clear(); // 清空缓冲区
			}
			System.out.println(new String(bos.toByteArray()));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (channel != null) {
				try {
					channel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
