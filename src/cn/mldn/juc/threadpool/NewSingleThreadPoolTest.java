package cn.mldn.juc.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 创建单线程池
 * @author lishangxing
 */
public class NewSingleThreadPoolTest {
	public static void main(String[] args) {
		ExecutorService service = Executors.newSingleThreadExecutor();
		for (int x = 0; x < 10; x++) {
			service.submit(() -> {
				System.out.println(Thread.currentThread().getName() + "线程操作。");
			});
		}
		service.shutdown();
	}
}
