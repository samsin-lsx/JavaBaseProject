package com.atguigu.juc.list;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 实现Callable接口启动多线程
 * @author lishangxing
 */
public class CallableThreadTest {
	public static void main(String[] args) {
		CallableThread ct = new CallableThread();
		FutureTask<Integer> future = new FutureTask<>(ct);
		new Thread(future).start();
		try {
			Integer sum = future.get();
			System.out.println(sum);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
}
class CallableThread implements Callable<Integer> {
	private int sum = 0;
	
	@Override
	public Integer call() throws Exception {
		for (int x = 0; x <= 100; x++) {
			sum += x;
		}
		return sum;
	}
	
}
