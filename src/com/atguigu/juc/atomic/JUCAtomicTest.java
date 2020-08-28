package com.atguigu.juc.atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class JUCAtomicTest {
	public static void main(String[] args) {
		AtomicThread at = new AtomicThread();
		for (int x = 0; x < 10; x++) {
			new Thread(at).start();
		}
	}
}
class AtomicThread implements Runnable {
	// private volatile int serialNumber = 0;
	private AtomicInteger serialNumber = new AtomicInteger();

	@Override
	public void run() {
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("线程执行 x = " + getSerialNumber());
	}
	
	public int getSerialNumber() {
		// return this.serialNumber++;
		return this.serialNumber.getAndIncrement();
	}
	
}
