package cn.mldn.juc.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * 同步阻塞队列的基本使用
 * @author lishangxing
 */
public class SynchronousQueueTest {

	public static void main(String[] args) {
		BlockingQueue<String> queue = new SynchronousQueue<>(); // 只能保存有一个队列数据
		for (int x = 0; x < 3; x++) {
			new Thread(() -> {
				for (int y = 0; y < 5; y++) {
					try {
						TimeUnit.SECONDS.sleep(1);
						String str = "【｛生产数据：" + Thread.currentThread().getName() + "｝】y = " + y;
						queue.put(str); // 会进入到生产的阻塞状态
						System.out.println(str);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}, "生产者 - " + x).start();
		}
		for (int x = 0; x < 1; x++) {
			new Thread(() -> {
				while (true) {
					try {
						TimeUnit.SECONDS.sleep(1);
						System.out.println("【｛消费数据：" + Thread.currentThread().getName() + "｝】" + queue.take());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}, "消费者 - " + x).start();
		}
	}

}
