package cn.mldn.juc.map;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ConcurrentHashMap：在写入数据的时候使用独占锁、在读取数据的时候使用了共享锁，保证数据读取的性能。<br>
 * 使用场景：在快递小哥送餐的时候需要记录快递员的轨迹数据，需要5秒写一次，但是用户可能有很多个人查看位置，此时是写入的次数较多、读取的次数很大，则需要使用ConcurrentHashMap集合。
 * @author lishangxing
 */
public class ConcurrentHashMapTest {
	public static void main(String[] args) {
		Map<String, String> all = new ConcurrentHashMap<>();
		for (int x = 0; x < 10; x++) {
			int temp = x;
			new Thread(() -> {
				for (int y = 0; y < 5; y++) {
					all.put(Thread.currentThread().getName(),  " x = " + temp + " y = " + y);
					System.out.println(all);
				}
			}).start();
		}
	}
}
