package cn.mldn.juc.base;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {
	public static void main(String[] args) {
		Ticket ticket = new Ticket();
		for (int x = 0; x < 6; x++) {
			new Thread(() -> {
				while (true) {
					ticket.sale();
				}
			}).start();
		}
	}
}

class Ticket {
	Lock lock = new ReentrantLock();
	private int ticket = 10;

	public void sale() {
		lock.lock();
		try {
			if (this.ticket > 0) {
				System.out.println(Thread.currentThread().getName() + "卖票，剩余票数为：" + this.ticket--);
			}
		} finally {
			lock.unlock();
		}
	}
}
