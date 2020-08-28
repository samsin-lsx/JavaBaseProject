package com.atguigu.juc.list;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch：闭锁，在完成某些运算时，只有其他线程的运算全部完成，当前运算才能继续执行
 * @author lishangxing
 */
public class CountDownLatchTest {
	public static void main(String[] args) {
		final CountDownLatch countDownLatch = new CountDownLatch(5);
		CountDownLatchThread cdlt = new CountDownLatchThread(countDownLatch);
		long startTime = System.currentTimeMillis();
		for (int x = 0; x < 5; x++) {
			new Thread(cdlt).start();
		}
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("耗费时间为：" + (endTime - startTime));
	}
}
class CountDownLatchThread implements Runnable {
	private CountDownLatch countDownLatch;
	public CountDownLatchThread(CountDownLatch countDownLatch) {
		this.countDownLatch = countDownLatch;
	}
	@Override
	public void run() {
		synchronized (this) {
			try {
				for (int x = 0; x < 50000; x++) {
					if (x % 2 == 0) {
						System.out.println(x);
					}
				}
			} finally {
				countDownLatch.countDown();
			}
		}
	}
}
