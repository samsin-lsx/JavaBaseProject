package cn.mldn.juc.threadpool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 无返回值的任务结果：RecursiveAction
 * 实现1-100的累加操作
 * @author lishangxing
 */
public class ForkJoinRecursiveActionTest {
	public static void main(String[] args) throws Exception {
		CountSave save = new CountSave();
		AddHandleTask task = new AddHandleTask(0, 100, save);
		ForkJoinPool pool = new ForkJoinPool();
		pool.submit(task);
		while (!task.isDone()) { // 如果当前的任务还没有执行完毕
			TimeUnit.MILLISECONDS.sleep(100);
			System.out.println("活跃线程：" + pool.getActiveThreadCount() + "、允许的最大并发线程数：" + pool.getParallelism());
		}
		if (task.isCompletedNormally()) { // 分支任务正常执行完成
			System.out.println("计算的结果为：" + save.getSum());
		}
	}
}
@SuppressWarnings("serial")
class AddHandleTask extends RecursiveAction {
	private int start;
	private int end;
	private CountSave save;
	public AddHandleTask(int start, int end, CountSave save) {
		this.start = start;
		this.end = end;
		this.save = save;
	}

	@Override
	protected void compute() {
		if (end - start < 100) {
			int sum = 0;
			for (int x = start; x <= end; x++) {
				sum += x;
			}
			this.save.add(sum); // 保存计算结果
		} else {
			int middle = (start + end) / 2;
			AddHandleTask leftTask = new AddHandleTask(start, middle, save); // 0-50的累加
			AddHandleTask rightTask = new AddHandleTask(middle + 1, end, this.save); // 51-100的累加
			super.invokeAll(leftTask, rightTask); // 并行执行的任务
		}
	}
	
}
/**
 * 用于保存数据处理结果
 * @author lishangxing
 */
class CountSave {
	private Lock lock = new ReentrantLock();
	private int sum = 0; // 保存处理结果
	public void add(int sum) {
		this.lock.lock();
		try {
			this.sum += sum;
		} finally {
			this.lock.unlock();
		}
	}
	public int getSum() {
		return this.sum;
	}
}
