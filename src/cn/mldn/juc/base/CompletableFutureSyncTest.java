package cn.mldn.juc.base;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * 此类的最大好处是提供了所有等待线程的执行触发点
 * @author lishangxing
 */
public class CompletableFutureSyncTest {

	public static void main(String[] args) throws Exception {
		CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
			System.out.println("【FUTURE】将军正在温柔乡里做美梦呢，还没睡醒。");
			try {
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("【FUTURE】将军睡醒了，准备攻打胖子。");
		});
		for (int x = 0; x < 10; x++) {
			new Thread(() -> {
				System.out.println("BEFORE【" + Thread.currentThread().getName() + "】进入到炮兵阵营，等待命令，准备开炮！");
				try {
					System.out.println("【" + Thread.currentThread().getName() + "】接收到命令，立刻开炮。" + future.get());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}, "炮兵 - " + x).start();
		}
	}

}