package cn.mldn.juc.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 优先级阻塞队列的基本使用
 * @author lishangxing
 */
public class PriorityBlockingQueueTest {

	public static void main(String[] args) {
		BlockingQueue<String> queue = new PriorityBlockingQueue<>(5);
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
		for (int x = 0; x < 5; x++) {
			new Thread(() -> {
				while (true) {
					try {
						TimeUnit.SECONDS.sleep(1);
						if (queue.isEmpty()) { // 队列的内容为空
							break;
						}
						System.out.println("【｛消费数据：" + Thread.currentThread().getName() + "｝】" + queue.take());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}, "消费者 - " + x).start();
		}
	}

}
