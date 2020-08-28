package cn.mldn.nio.tcpnio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 同步非阻塞IO通讯模型
 * @author lishangxing
 */
public class EchoServer {
	public static final int PORT = 9999; // 设置绑定的端口
	public static void main(String[] args) throws Exception {
		ExecutorService executorService = Executors.newFixedThreadPool(5); // 开辟5个线程大小的线程池
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open(); // 打开一个服务端的Socket连接通道
		serverSocketChannel.configureBlocking(false); // 设置为非阻塞模式
		serverSocketChannel.bind(new InetSocketAddress(PORT)); // 设置服务端的端口绑定
		Selector selector = Selector.open(); // 打开一个选择器，所有的channel都要注册到此选择器之中
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT); // 将ServerSocketChannel统一注册到Selector之中，接收统一的管理
		System.out.println("服务器程序已启动，该程序在" + PORT + "端口上监听，等待客户端连接……");
		// 所有的连接处理都需要被Selector所管理，只要有新的用户连接就必须通过Selector来处理
		// int keySelect = 0; // 接收连接的状态
		while (selector.select() > 0) { // 现在有人连接上了
			Set<SelectionKey> selectedKeys = selector.selectedKeys(); // 获取全部的连接通道信息
			Iterator<SelectionKey> selectionIter = selectedKeys.iterator();
			while (selectionIter.hasNext()) {
				SelectionKey selectionKey = selectionIter.next(); // 获取每一个通道
				if (selectionKey.isAcceptable()) { // 现在模式为接收连接模式
					SocketChannel clientChannel = serverSocketChannel.accept(); // 等待接收
					if (clientChannel != null) { // 若有连接
						executorService.submit(new SocketClientChannelThread(clientChannel));
					}
				}
				selectionIter.remove(); // 移除掉此通道信息
			}
		}
		executorService.shutdown();
		serverSocketChannel.close();
	}
}
class SocketClientChannelThread implements Runnable {
	private SocketChannel clientChannel; // 操作客户端通道
	private boolean flag = true; // 循环标记
	public SocketClientChannelThread(SocketChannel clientChannel) throws Exception {
		this.clientChannel = clientChannel;
		System.out.println("【客户端连接成功】，该客户端的地址为：" + clientChannel.getRemoteAddress());
	}

	@Override
	public void run() {
		ByteBuffer buffer = ByteBuffer.allocate(50);
		try {
			while (this.flag) { // 一直与客户端交互
				if (buffer != null) {
					buffer.clear(); // 由于可能重复使用buffer，需要将之前使用的缓冲区清空
				}
				int readCount = this.clientChannel.read(buffer); // 接收客户端发送过来的消息
				String readMessage = new String(buffer.array(), 0, readCount); // 将接收到的消息变为字符串
				System.out.println("【服务器接收到的消息】" + readMessage);
				String writeMessage = "【ECHO】" + readMessage + "\n";
				if ("exit".equals(readMessage)) {
					writeMessage = "【EXIT】拜拜，下次再见！";
					this.flag = false;
				}
				buffer.clear(); // 清空缓冲区
				buffer.put(writeMessage.getBytes()); // 将数据添加到缓冲区之中
				buffer.flip(); // 重设缓冲区
				this.clientChannel.write(buffer); // 回应内容
			}
		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			if (this.clientChannel != null) {
				try {
					this.clientChannel.close(); // 关闭客户端通道
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
