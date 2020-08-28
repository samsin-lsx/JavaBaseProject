package cn.mldn.juc.base;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch使用：等待其他线程执行完成之后再进行执行
 * @author lishangxing
 */
public class CountDownLatchTest {
	public static void main(String[] args) throws InterruptedException {
		CountDownLatch cdl = new CountDownLatch(2); // 需要等待2个线程执行完之后才可以继续执行
		for (int x = 0; x < 2; x++) {
			new Thread(() -> {
				System.out.println("【" + Thread.currentThread().getName() + "】应用子线程执行完毕。");
				cdl.countDown(); // 减少等待的线程个数
			}, "线程对象 - " + x).start();
		}
		cdl.await(); // 等待计数的结束（个数为0再执行）
		System.out.println("****** 主线程 ****** 所有的线程执行完毕了。");
	}
}
