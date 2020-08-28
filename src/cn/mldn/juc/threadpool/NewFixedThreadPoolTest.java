package cn.mldn.juc.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 创建有限量大小的线程池
 * @author lishangxing
 */
public class NewFixedThreadPoolTest {

	public static void main(String[] args) {
		ExecutorService service = Executors.newFixedThreadPool(3); // 创建有限量代销的线程池对象
		for (int x = 0; x < 10; x++) { // 三个线程负责处理10项业务
			service.submit(() -> {
				System.out.println(Thread.currentThread().getName() + "线程操作。");
			});
		}
		service.shutdown();
	}

}
