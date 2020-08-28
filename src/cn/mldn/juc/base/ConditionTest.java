package cn.mldn.juc.base;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionTest {
	public static String msg = null;
	public static void main(String[] args) throws InterruptedException {
		Lock myLock = new ReentrantLock();
		Condition condition = myLock.newCondition();
		myLock.lock();
		try {
			new Thread(() -> {
				myLock.lock();
				try {
					msg = "我爱你，老婆！";
					condition.signal();
				} finally {
					myLock.unlock();
				}
			}).start();
			condition.await();
			System.out.println("******** 主线程执行完毕，msg = " + msg);
		} finally {
			myLock.unlock();
		}
	}
}