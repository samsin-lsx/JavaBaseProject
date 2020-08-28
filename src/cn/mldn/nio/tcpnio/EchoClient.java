package cn.mldn.nio.tcpnio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import cn.mldn.nio.util.InputUtil;

public class EchoClient {
	public static final String HOST = "localhost";
	public static final int PORT = 9999;

	public static void main(String[] args) throws Exception {
		SocketChannel clientChannel = SocketChannel.open(); // 获取客户端SocketChannel对象
		clientChannel.connect(new InetSocketAddress(HOST, PORT)); // 连接服务器
		ByteBuffer buffer = ByteBuffer.allocate(50); // 设置缓冲区的大小为50，与服务器一样
		boolean flag = true;
		while (flag) { // 一直进行输入
			buffer.clear(); // 清空缓冲区数据
			String msg = InputUtil.getString("请输入要发送的消息：");
			buffer.put(msg.getBytes()); // 将输入的数据保存在缓冲区之中
			buffer.flip(); // 重设缓冲区
			clientChannel.write(buffer);
			buffer.clear(); // 清空缓冲区
			int readCount = clientChannel.read(buffer);
			buffer.flip();
			System.err.println(new String(buffer.array(), 0, readCount));
			if ("exit".equals(msg)) {
				flag = false; // 结束循环
			}
			clientChannel.close();
		}
	}

}
