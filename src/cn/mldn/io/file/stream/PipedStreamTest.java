package cn.mldn.io.file.stream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * 管道流
 * @author lishangxing
 */
public class PipedStreamTest {

	public static void main(String[] args) throws IOException {
		SendThread send = new SendThread();
		ReceiveThread receive = new ReceiveThread();
		send.getOutput().connect(receive.getInput()); // 进行管道连接
		new Thread(send, "消息发送线程").start();
		new Thread(receive, "消息接收线程").start();
	}

}
class SendThread implements Runnable {
	private PipedOutputStream output; // 发送消息属于管道输出流
	public SendThread() {
		this.output = new PipedOutputStream(); // 实例化管道输出流
	}
	@Override
	public void run() {
		for (int x = 0; x < 10; x++) {
			try {
				this.output.write(("【第" + (x + 1) + "次信息发送 - " + Thread.currentThread().getName() + "】www.samsin.org\n").getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			this.output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public PipedOutputStream getOutput() {
		return output;
	}
}
class ReceiveThread implements Runnable {
	private PipedInputStream input; // 接收消息属于管道输入流
	public ReceiveThread() {
		this.input = new PipedInputStream();
	}
	@Override
	public void run() {
		byte[] data = new byte[1024];
		int len = 0;
		ByteArrayOutputStream bos = new ByteArrayOutputStream(); // 创建内存输出流
		try {
			while ((len = this.input.read(data)) != -1) {
				bos.write(data, 0, len); // 所有的数据保存到内存输出流中
			}
			System.out.println("【" + Thread.currentThread().getName() + " - 接收消息】\n" + new String(bos.toByteArray()));
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public PipedInputStream getInput() {
		return input;
	}
}