package cn.mldn.juc.base;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockTest {
	public static void main(String[] args) {
		Account account = new Account("我的爱人", 15.0);
		double[] money = new double[] {5.0, 300.0, 5000.0, 50000.0, 1000.0};
		for (int x = 0; x < 2; x++) { // 设置两个人存款
			new Thread(() -> {
				for (int y = 0; y < money.length; y++) { // 循环存款次数
					account.saveMoney(money[y]);
				}
			}, "存款人 - " + x).start(); 
		}
		for (int x = 0; x < 10; x++) { // 设置10个人查询账户余额
			new Thread(() -> {
				System.out.println(Thread.currentThread().getName() + "查账，账户名：" + account.getName() + "、账户余额为：" + account.loadMoney());
			}, "查款人 - " + x).start(); 
		}
	}
}
class Account {
	private String name; // 银行账户
	private double asset = 10.0; // 银行总额
	ReadWriteLock rwLock = new ReentrantReadWriteLock(); // 读写锁分离
	public Account(String name, double asset) {
		this.name = name;
		this.asset = asset;
	}
	public boolean saveMoney(double money) { // 银行存款
		this.rwLock.writeLock().lock();
		try {
			System.out.println("【（" + Thread.currentThread().getName() + "）存款-BEFORE】存款金额为：" + money);
			TimeUnit.SECONDS.sleep(1);
			if (money > 0.0) {
				this.asset += money;
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("【（" + Thread.currentThread().getName() + "）存款-AFTER】存款金额为：" + this.asset);
			this.rwLock.writeLock().unlock();
		}
		return false;
	}
	public String getName() {
		return this.name;
	}
	public double loadMoney() { // 银行总额查询
		this.rwLock.readLock().lock();
		try {
			return this.asset;
		} finally {
			this.rwLock.readLock().unlock();
		}
	}
}
