package cn.mldn.nio.buffer;

import java.nio.ByteBuffer;

/**
 * 字节缓冲区的基本使用
 * @author lishangxing
 */
public class ByteBufferTest {
	public static void main(String[] args) {
		String str = "www.mldn.cn";
		ByteBuffer buffer = ByteBuffer.allocate(20); // 设置缓冲区的容量大小为20
		System.out.println("【1】缓冲区存放数据前：capacity = " + buffer.capacity() + "、limit = " + buffer.limit() + "、position = " + buffer.position());
		buffer.put(str.getBytes()); // 存放数据到缓冲区
		System.out.println("【2】缓冲区存放数据后：capacity = " + buffer.capacity() + "、limit = " + buffer.limit() + "、position = " + buffer.position());
		buffer.flip(); // 重设缓冲区，将输入与输出进行转换
		System.out.println("【3】重设缓冲区：capacity = " + buffer.capacity() + "、limit = " + buffer.limit() + "、position = " + buffer.position());
		while (buffer.hasRemaining()) { // 判断缓冲区中是否有数据
			System.out.print(buffer.get() + "、"); // 返回字节数据
		}
		buffer.clear(); // 清空缓冲区
		System.out.println("\n【4】清空缓冲区：capacity = " + buffer.capacity() + "、limit = " + buffer.limit() + "、position = " + buffer.position());
	}
}
