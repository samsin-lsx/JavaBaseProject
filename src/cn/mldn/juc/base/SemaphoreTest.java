package cn.mldn.juc.base;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 信号量处理
 * 
 * @author lishangxing
 */
public class SemaphoreTest {
	public static void main(String[] args) {
		Semaphore sem = new Semaphore(2); // 设置两个可以办理的窗口
		Random random = new Random(); // 模拟用户办理的时间
		for (int x = 0; x < 10; x++) {
			new Thread(() -> { // 每一个线程就是需要办理业务的人员
				if (sem.availablePermits() > 0) { // 有空余的窗口办理
					System.out.println("【" + Thread.currentThread().getName() + "】进入银行，此时银行没有人排队！");
				} else { // 没有空余的窗口位置
					System.out.println("【" + Thread.currentThread().getName() + "】进到银行，需要排队等待办理。");
				}
				try {
					sem.acquire(); // 从信号量中获得许可
					System.out.println("【" + Thread.currentThread().getName() + "】{START}开始办理业务。");
					TimeUnit.SECONDS.sleep(random.nextInt(10)); // 模拟用户办理的时间延迟
					System.out.println("【" + Thread.currentThread().getName() + "】{END}结束办理业务。");
					sem.release(); // 用户离开窗口，释放窗口使用权
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}, "客户 - " + x).start();
		}
	}
}
