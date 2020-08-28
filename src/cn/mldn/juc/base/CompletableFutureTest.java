package cn.mldn.juc.base;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFutureTest {

	public static void main(String[] args) throws Exception {
		CompletableFuture<String> future = new CompletableFuture<>();
		for (int x = 0; x < 10; x++) {
			new Thread(() -> {
				System.out.println("BEFORE【" + Thread.currentThread().getName() + "】进入到炮兵阵营，等待命令，准备开炮！");
				try {
					String cmd = future.get();
					if ("fire".equalsIgnoreCase(cmd)) {
						System.out.println("【" + Thread.currentThread().getName() + "】接收到【" + cmd + "】命令，立刻开炮。");
					} else if ("cancel".equalsIgnoreCase(cmd)) {
						System.out.println("【" + Thread.currentThread().getName() + "】接收到【" + cmd + "】命令，回家睡觉。");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}, "炮兵 - " + x).start();
		}
		TimeUnit.SECONDS.sleep(3); // 等待3秒钟
		future.complete("cancel"); // 发送命令
	}

}