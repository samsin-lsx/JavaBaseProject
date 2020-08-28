package cn.mldn.juc.threadpool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * 有返回值的结果任务：RecursiveTask<br>
 * 实现1-100的累加操作
 * @author lishangxing
 */
public class ForkJoinRecursiveTaskTest {

	public static void main(String[] args) throws Exception {
		AddTask task = new AddTask(0, 100);
		ForkJoinPool pool = new ForkJoinPool();
		Future<Integer> future = pool.submit(task);
		System.out.println(future.get());
	}

}

@SuppressWarnings("serial")
class AddTask extends RecursiveTask<Integer> {
	private int start;
	private int end;
	public AddTask(int start, int end) { // 传入计算的开始值和结束值
		this.start = start;
		this.end = end;
	}

	@Override
	protected Integer compute() { // 进行数据的分支处理计算
		int sum = 0;
		if (end - start < 100) { // 开启了分支
			for (int x = start; x <= end; x++) {
				sum += x;
			}
		} else {
			int middle = (start + end) / 2; // 拆分的中间值
			AddTask leftTask = new AddTask(start, middle); // 0-50的累加
			AddTask rightTask = new AddTask(middle + 1, end); // 51-100的累加
			leftTask.fork(); // 表示开启下一个分支计算
			rightTask.fork();
			sum = leftTask.join() + rightTask.join(); // 两个分支的计算结果进行合并
			return sum;
		}
		return sum;
	}
	
}
