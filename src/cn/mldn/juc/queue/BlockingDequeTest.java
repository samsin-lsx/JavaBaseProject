package cn.mldn.juc.queue;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * 双端阻塞队列
 * @author lishangxing
 */
public class BlockingDequeTest {

	public static void main(String[] args) {
		BlockingDeque<String> deque = new LinkedBlockingDeque<>();
		for (int x = 0; x < 3; x++) {
			new Thread(() -> {
				for (int y = 0; y < 5; y++) {
					try {
						TimeUnit.SECONDS.sleep(1);
						String str = null;
						if (y % 2 == 0) {
							str = "【[FIRST]生产数据｛" + Thread.currentThread().getName() + "｝】y = " + y;
							deque.addFirst(str);
						} else {
							str = "【[LAST]生产数据｛" + Thread.currentThread().getName() + "｝】";
							deque.addLast(str);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}, "生产者 - x" + x).start();
		}
		new Thread(() -> {
			while (true) {
				try {
					TimeUnit.SECONDS.sleep(1);
					System.out.println("【[FIRST]消费数据｛" + Thread.currentThread().getName() + "｝】" + deque.takeFirst());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, "消费者-FIRST").start();
		new Thread(() -> {
			try {
				TimeUnit.SECONDS.sleep(1);
				System.out.println("【[LAST]消费数据｛" + Thread.currentThread().getName() + "｝】" + deque.takeLast());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}, "消费者-LAST").start();
	}

}
