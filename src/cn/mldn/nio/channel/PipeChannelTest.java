package cn.mldn.nio.channel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * 管道流
 * @author lishangxing
 */
public class PipeChannelTest {
	public static void main(String[] args) throws Exception {
		Pipe pipe = Pipe.open(); // 打开管道流
		new Thread(() -> {
			String msg = "【" + Thread.currentThread().getName() + "】www.mldn.cn";
			Pipe.SinkChannel sinkChannel = pipe.sink(); // 获取管道输出流
			ByteBuffer buffer = ByteBuffer.allocate(50); // 设置缓冲区的大小
			buffer.put(msg.getBytes()); // 数据存放到缓冲区
			buffer.flip(); // 重设缓冲区
			while (buffer.hasRemaining()) {
				try {
					sinkChannel.write(buffer);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}, "发送数据线程").start();
		new Thread(() -> {
			Pipe.SourceChannel sourceChannel = pipe.source(); // 打开管道输入流
			ByteBuffer buffer = ByteBuffer.allocate(50);
			try {
				int count = sourceChannel.read(buffer); // 通过管道读取缓冲区的数据
				buffer.flip();
				System.out.println("｛接收端｝" + new String(buffer.array(), 0, count));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}, "接收数据线程").start();
	}
}
