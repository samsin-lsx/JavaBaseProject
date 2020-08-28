package cn.mldn.juc.queue;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 延迟队列的基本使用
 * @author lishangxing
 */
public class DelayQueueTest {
	public static void main(String[] args) throws Exception {
		System.out.println("准备聚会中……");
		DelayQueue<Member> queue = new DelayQueue<>(); // 设置延迟队列
		queue.add(new Member("张三", 3, TimeUnit.SECONDS));
		queue.add(new Member("李四", 6, TimeUnit.SECONDS));
		while (!queue.isEmpty()) { // 如果聚会还有人
			Delayed delayed = queue.poll(); // 从队列中取出数据
			System.out.println("【poll = ｛" + delayed + "｝】" + System.currentTimeMillis());
			TimeUnit.MILLISECONDS.sleep(500);
		}
	}
}

class Member implements Delayed {
	private String name; // 聚会的姓名
	private long expire; // 失效时间：人员离开的时间：单位为毫秒
	private long delay; // 设置延迟时间：单位为毫秒

	/**
	 * 设置参与队列之中的用户信息
	 * @param name 用户的姓名
	 * @param delay 延迟时间
	 * @param unit 时间处理单位（传入的秒变为毫秒）
	 */
	public Member(String name, long delay, TimeUnit unit) {
		this.name = name;
		this.delay = TimeUnit.MILLISECONDS.convert(delay, unit); // 保存延迟时间
		this.expire = System.currentTimeMillis() + this.delay; // 失效时间：当前时间 + 延迟时间
	}

	@Override
	public int compareTo(Delayed o) { // 决定优先级队列的弹出操作
		return (int) (this.delay - this.getDelay(TimeUnit.MILLISECONDS));
	}

	@Override
	public long getDelay(TimeUnit unit) { // 计算延迟时间是否已经到达
		return unit.convert(this.expire - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
	}

	@Override
	public String toString() {
		return this.name + "预计" + this.delay + "离开，现在已经到点了。";
	}
}