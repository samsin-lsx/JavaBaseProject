package cn.mldn.juc.array;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 并发单值集合类：Set<br>
 * 使用场景：需要存储用户的公共资源信息，允许多个线程同时写入数据
 * @author lishangxing
 */
public class CopyOnWriteArraySetTest {
	public static void main(String[] args) {
		Set<String> all = new CopyOnWriteArraySet<>();
		for (int x = 0; x < 10; x++) {
			int temp = x;
			new Thread(() -> {
				for (int y = 0; y < 5; y++) {
					all.add(Thread.currentThread().getName() + " - " + temp + " - " + y);
					System.out.println(all);
				}
			}).start();
		}
	}
}
