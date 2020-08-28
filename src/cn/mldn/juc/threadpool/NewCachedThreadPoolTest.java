package cn.mldn.juc.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 创建无限量大小的线程池
 * @author lishangxing
 */
public class NewCachedThreadPoolTest {
	public static void main(String[] args) {
		ExecutorService service = Executors.newCachedThreadPool(); // 创建无限量大小的线程池对象
		for (int x = 0; x < 10; x++) {
			service.submit(() -> { // 线程池负责启动
				System.out.println(Thread.currentThread().getName() + "线程操作。");
			});
		}
		service.shutdown(); // 关闭线程池
	}
}
