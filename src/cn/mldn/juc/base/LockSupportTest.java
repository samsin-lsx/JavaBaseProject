package cn.mldn.juc.base;

import java.util.concurrent.locks.LockSupport;

/**
 * 阻塞原语：是对Thread类中的suspend()（挂起线程暂停执行）方法和resume()（恢复线程执行）方法的补充完善
 * @author lishangxing
 */
public class LockSupportTest {
	private static String msg = null;
	public static void main(String[] args) {
		Thread mainThread = Thread.currentThread();
		new Thread(() -> {
			try {
				msg = "老婆，我好想你的啊。";
			} finally {
				LockSupport.unpark(mainThread);
			}
		}).start();
		LockSupport.park(mainThread);
		System.out.println("****** 主线程执行完毕！msg = " + msg);
	}
}
