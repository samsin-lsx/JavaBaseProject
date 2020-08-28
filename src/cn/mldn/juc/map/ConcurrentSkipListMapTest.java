package cn.mldn.juc.map;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * 查询的速度很快
 * @author lishangxing
 */
public class ConcurrentSkipListMapTest {
	public static void main(String[] args) {
		Map<String, String> all = new ConcurrentSkipListMap<>();
		for (int x = 0; x < 20; x++) {
			int temp = x;
			new Thread(() -> {
				for (int y = 0; y < 10; y++) {
					all.put(Thread.currentThread().getName(),  " x = " + temp + "、y = " + y);
				}
			}).start();
		}
		System.out.println(all.get("Thread-0"));
	}
}
