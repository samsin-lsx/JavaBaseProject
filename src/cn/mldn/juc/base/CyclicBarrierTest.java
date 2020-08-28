package cn.mldn.juc.base;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class CyclicBarrierTest {

	public static void main(String[] args) throws Exception {
		CyclicBarrier cb = new CyclicBarrier(2); // 设置凑够2个线程就执行
		for (int x = 0; x < 3; x++) {
			int sec = x;
			new Thread(() -> {
				System.out.println("【" + Thread.currentThread().getName() + "】 - 等待开始");
				try {
					if (sec == 2) {
						cb.reset(); // 进行重置
						System.out.println("【重置处理】****** " + Thread.currentThread().getName());
					} else {
						TimeUnit.SECONDS.sleep(2);
						cb.await(3, TimeUnit.SECONDS); // 设置等待的时间
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("【" + Thread.currentThread().getName() + "】 - 等待结束");
			}, "游玩者 - " + x).start();
		}
	}

}
