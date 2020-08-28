package cn.mldn.nio.tcpaio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * 使用AIO模型实现服务器端程序
 * @author lishangxing
 */
public class AIOEchoServer {

	public static void main(String[] args) throws Exception {
		new Thread(new AIOServerThread()).start();
	}

}

/**
 * 定义AIO服务器处理线程
 * 
 * @author lishangxing
 */
class AIOServerThread implements Runnable {
	private static final int PORT = 9999;
	private CountDownLatch latch = null; // 保证服务器端线程执行完毕后结束
	private AsynchronousServerSocketChannel serverChannel = null; // 异步服务处理通道对象

	public AIOServerThread() throws Exception {
		this.latch = new CountDownLatch(1); // 服务器端线程只有1个
		this.serverChannel = AsynchronousServerSocketChannel.open(); // 打开异步通道
		this.serverChannel.bind(new InetSocketAddress(PORT)); // 绑定服务器端口
		System.out.println("服务器启动成功，在" + PORT + "端口上进行监听，等待客户端连接………");
	}

	@Override
	public void run() { // 在线程启动中等待连接
		this.serverChannel.accept(this, new AcceptHandler()); // 等待客户端连接
		try {
			this.latch.await(); // 持续等待状态
			System.out.println("服务器连接失败，停止运行……");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public AsynchronousServerSocketChannel getServerChannel() {
		return serverChannel;
	}

	public CountDownLatch getLatch() {
		return latch;
	}
}

class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel, AIOServerThread> {

	@Override
	public void completed(AsynchronousSocketChannel channel, AIOServerThread aioThread) {
		aioThread.getServerChannel().accept(aioThread, this); // 接收连接
		ByteBuffer buffer = ByteBuffer.allocate(100); // 开辟一个接收的缓冲区
		channel.read(buffer, buffer, new EchoHandler(channel)); // 创建另外一个异步处理类实现回应处理
	}

	@Override
	public void failed(Throwable exc, AIOServerThread aioThread) {
		System.out.println("服务器连接处理失败！");
		aioThread.getLatch().countDown(); // 减1，解除阻塞状态
	}

}

/**
 * 实现异步回调处理类
 * 
 * @author lishangxing
 */
class EchoHandler implements CompletionHandler<Integer, ByteBuffer> {
	private AsynchronousSocketChannel clientChannel; // 客户端回调对象
	private boolean exit = false; // 回应是否结束，如果为exit = true表示不再接收

	public EchoHandler(AsynchronousSocketChannel clientChannel) {
		this.clientChannel = clientChannel;
	}

	@Override
	public void completed(Integer result, ByteBuffer buffer) {
		buffer.flip(); // 如果要读取数据则应该要重置缓冲区
		String readMessage = new String(buffer.array(), 0, buffer.remaining()).trim(); // 接收读取的数据
		System.err.println("【服务器端读取的数据】" + readMessage);
		String resultMessage = "【ECHO】" + readMessage; // 保存数据的回应处理信息
		if ("exit".equalsIgnoreCase(readMessage)) {
			resultMessage = "【EXIT】拜拜，下次再见！";
			this.exit = true; // 输出信息之后不再需要数据读取了
		}
		this.echoWrite(resultMessage); // 回应处理
	}
	
	@Override
	public void failed(Throwable exc, ByteBuffer buffer) {
		this.closeClient();
	}
	
	private void closeClient() {
		// System.out.println("客户端连接有错误，中断与此客户端的连接处理！");
		try {
			this.clientChannel.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void echoWrite(String result) {
		ByteBuffer buffer = ByteBuffer.allocate(100); // 设置回应缓冲区
		buffer.put(result.getBytes()); // 信息回应处理
		buffer.flip();
		this.clientChannel.write(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
			@Override
			public void completed(Integer result, ByteBuffer attachment) {
				if (buffer.hasRemaining()) { // 有数据的情况才进行写入
					EchoHandler.this.clientChannel.write(buffer, buffer, this); // 进行数据的输出操作
				} else {
					if (EchoHandler.this.exit == false) { // 还可以继续读取
						ByteBuffer readBuffer = ByteBuffer.allocate(100);
						EchoHandler.this.clientChannel.read(readBuffer, readBuffer, new EchoHandler(EchoHandler.this.clientChannel));
					}
				}
			}

			@Override
			public void failed(Throwable exc, ByteBuffer attachment) {
				EchoHandler.this.closeClient();
			}
		});
	}

}