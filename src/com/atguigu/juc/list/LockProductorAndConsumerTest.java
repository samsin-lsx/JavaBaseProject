package com.atguigu.juc.list;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者与消费者：使用Lock显式锁的方式实现
 * @author lishangxing
 */
public class LockProductorAndConsumerTest {
	public static void main(String[] args) {
		Clerk clerk = new Clerk();
		Productor productor = new Productor(clerk);
		Consumer consumer = new Consumer(clerk);
		new Thread(productor, "生产者A").start();
		new Thread(consumer, "消费者B").start();
		new Thread(productor, "生产者C").start();
		new Thread(consumer, "消费者D").start();
	}
}
class Clerk {
	private int product = 0;
	private Lock lock = new ReentrantLock();
	Condition condition = lock.newCondition();
	// 进货
	public void get() {
		lock.lock();
		try {
			while (product >= 1) { // 避免虚假唤醒
				System.out.println("产品已满！");
				try {
					condition.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(Thread.currentThread().getName() + "：" + ++product);
			condition.signalAll();
		} finally {
			lock.unlock();
		}
	}
	
	public void sale() {
		lock.lock();
		try {
			while (this.product <= 0) { // 避免虚假唤醒
				System.out.println("缺货！");
				try {
					condition.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(Thread.currentThread().getName() + "：" + --product);
			condition.signalAll();
		} finally {
			lock.unlock();
		}
	}
}
class Productor implements Runnable {
	private Clerk clerk;
	public Productor(Clerk clerk) {
		this.clerk = clerk;
	}
	@Override
	public void run() {
		for (int x = 0; x < 20; x++) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.clerk.get();
		}
	}
}
class Consumer implements Runnable {
	private Clerk clerk;
	public Consumer(Clerk clerk) {
		this.clerk = clerk;
	}
	@Override
	public void run() {
		for (int x = 0; x < 20; x++) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.clerk.sale();
		}
	}
}