package cn.mldn.juc.array;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 并发单值集合类：List<br>
 * 使用场景：需要存储用户的公共资源信息，允许多个线程同时写入数据
 * @author lishangxing
 */
public class CopyOnWriteArrayListTest {
	public static void main(String[] args) {
		List<String> all = new CopyOnWriteArrayList<>();
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
