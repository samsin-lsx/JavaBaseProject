package cn.mldn.juc.threadpool;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池异步交互
 * @author lishangxing
 */
public class CompletionServiceTest {
	public static void main(String[] args) throws Exception {
		ExecutorService service = Executors.newCachedThreadPool();
		CompletionService<String> completions = new ExecutorCompletionService<>(service);
		for (int x = 0; x < 10; x++) {
			int temp = x;
			completions.submit(() -> {
				return Thread.currentThread().getName() + " - x = " + temp;
			});
		}
		for (int x = 0; x < 10; x++) {
			System.out.println(completions.take().get());
		}
		service.shutdown();
	}
}
