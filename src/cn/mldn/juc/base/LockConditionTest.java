package cn.mldn.juc.base;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockConditionTest {
	public static void main(String[] args) {
		DataBuffer<String> buffer = new DataBuffer<>();
		for (int x = 0; x < 3; x++) { // 创建三个写入线程
			new Thread(() -> {
				for (int y = 0; y < 2; y++) { // 每个线程每次写入2个数据
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					buffer.put(Thread.currentThread().getName() + "写入数据，y = " + y);
				}
			}, "生产者 - " + x).start();
		}
		for (int x = 0; x < 5; x++) { // 创建五个读取线程
			new Thread(() -> {
				while (true) {
					try {
						TimeUnit.SECONDS.sleep(3);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("【（" + Thread.currentThread().getName() + "）CONSUMER】" + buffer.get());
				}
			}, "消费者 - " + x).start();
		}
	}
}
class DataBuffer<T> { // 进行数据的缓冲控制，该缓冲可以保存各种数据类型的操作
	private static final int MAX_LENGTH = 5; // 保存数组的长度
	private Object[] data = new Object[MAX_LENGTH]; // 定义一个进行数据保存的数组
	private Lock myLock = new ReentrantLock(); // 创建数据锁
	private Condition putCondition = myLock.newCondition(); // 数据保存的控制
	private Condition getCondition = myLock.newCondition(); // 数据读取的控制
	private int putIndex = 0; // 数据保存的索引
	private int getIndex = 0; // 数据读取的索引
	private int count = 0; // 当前保存的元素个数
	@SuppressWarnings("unchecked")
	public T get() {
		Object takeObject = null;
		this.myLock.lock();
		try {
			if (this.count == 0) {
				this.getCondition.await(); // 读取的线程就要等待
			}
			takeObject = this.data[this.getIndex++]; // 读取指定索引数据
			if (this.getIndex == MAX_LENGTH) {
				this.getIndex = 0; // 重置开始读取，重置索引为0
			}
			this.count--; // 逐个读取数据，需要进行减1操作
			this.putCondition.signal(); // 唤醒写入线程
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.myLock.unlock();
		}
		return (T) takeObject;
	}
	public void put(T t) { // 实现数据缓冲的写入操作
		this.myLock.lock();
		try {
			if (this.count == MAX_LENGTH) { // 如果数据已经满了
				this.putCondition.await();
			}
			this.data[this.putIndex++] = t; // 保存当前的数据
			if (this.putIndex == MAX_LENGTH) { // 如果写入缓冲的数组索引写满了
				this.putIndex = 0; // 重置数组操作的索引值
			}
			this.count++; // 保存个数的增加1
			this.getCondition.signal(); // 唤醒消费线程
			System.out.println("【（" + Thread.currentThread().getName() + "）写入缓冲-put()】" + t);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.myLock.unlock();
		}
	}
}
