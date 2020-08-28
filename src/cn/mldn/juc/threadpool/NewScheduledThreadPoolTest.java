package cn.mldn.juc.threadpool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 创建调度线程池
 * @author lishangxing
 */
public class NewScheduledThreadPoolTest {

	public static void main(String[] args) {
		ScheduledExecutorService service = Executors.newScheduledThreadPool(1); // 创建调度线程池
		for (int x = 0; x < 10; x++) {
//			service.schedule(() -> { // 延迟启动（2秒后开始执行）
//				System.out.println(Thread.currentThread().getName() + "执行线程。");
//			}, 2, TimeUnit.SECONDS);
			service.scheduleAtFixedRate(() -> { // 间隔执行（2秒后开始执行，每隔2秒执行一次）
				System.out.println(Thread.currentThread().getName() + "执行线程。");
			}, 2, 2, TimeUnit.SECONDS);
		}
//		service.shutdown();
	}

}
