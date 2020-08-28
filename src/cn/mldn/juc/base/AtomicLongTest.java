package cn.mldn.juc.base;

import java.util.concurrent.atomic.AtomicLong;

public class AtomicLongTest {
	public static void main(String[] args) {
		AtomicLong num = new AtomicLong(10000);
		System.out.println("数据自减：" + num.decrementAndGet());
		System.out.println("数据自增：" + num.incrementAndGet());
	}
}
