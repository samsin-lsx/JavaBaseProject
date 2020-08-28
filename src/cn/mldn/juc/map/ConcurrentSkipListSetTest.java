package cn.mldn.juc.map;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * 快速定位查询的速度很快
 * @author lishangxing
 */
public class ConcurrentSkipListSetTest {
	public static void main(String[] args) {
		Set<String> all = new ConcurrentSkipListSet<>();
		for (int x = 0; x < 20; x++) {
			int temp = x;
			new Thread(() -> {
				for (int y = 0; y < 10; y++) {
					all.add(Thread.currentThread().getName() + " x = " + temp + "、y = " + y);
				}
			}).start();
		}
		System.out.println(all.contains("Thread-0 x = 0、y = 0"));
	}
}
