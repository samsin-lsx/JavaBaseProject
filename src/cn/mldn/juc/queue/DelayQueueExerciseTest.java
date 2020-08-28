package cn.mldn.juc.queue;

import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 延迟队列的经典案例：老师监督学生考试的概念
 * @author lishangxing
 */
public class DelayQueueExerciseTest {
	public static void main(String[] args) throws Exception {
		Random rand = new Random(); // 模拟学生的交卷时间
		DelayQueue<Student> studentsDelayQueue = new DelayQueue<>();
		for (int x = 0; x < 45; x++) {
			int time = rand.nextInt(10);
			while (time < 9) { // 必须保证考试时间最少为3秒钟，如果不足3秒钟则重新计算
				time = rand.nextInt(10);
			}
			studentsDelayQueue.put(new Student("学生 - " + x, time, TimeUnit.SECONDS));
		}
		new Thread(new Teacher(studentsDelayQueue, 45)).start();
	}
}
class Teacher implements Runnable { // 老师监督考试多线程
	private int studentCount = 0; // 学生考试的数量
	private int submitCount = 0; // 保存学生交卷的数量
	private DelayQueue<Student> studentsDelayQueue = null;
	public Teacher(DelayQueue<Student> studentsDelayQueue, int studentCount) {
		this.studentsDelayQueue = studentsDelayQueue; // 保存所有的学生交卷信息
		this.studentCount = studentCount; // 保存学生数量
	}
	@Override
	public void run() {
		System.out.println("*************** 学生们开始答题 ***************");
		try {
			while (this.submitCount < this.studentCount) { // 表示还有人未交卷
				Student student = this.studentsDelayQueue.poll(); // 取出队列中的学生信息
				if (student != null) { // 有队列信息取出，表示有人交卷了
					student.exam(); // 交卷处理
					this.submitCount++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("*************** 学生们结束考试 ***************");
	}
	
}
class Student implements Delayed {
	private String name;
	private long submitTime; // 交卷时间，使用毫秒单位
	private long workTime; // 实际的考试时间
	
	public Student(String name, long workTime, TimeUnit unit) {
		this.name = name;
		this.workTime = TimeUnit.MILLISECONDS.convert(workTime, TimeUnit.MILLISECONDS);
		this.submitTime = System.currentTimeMillis() + this.workTime; // 保存交卷时间
	}
	
	public void exam() { // 考试交卷处理
		System.out.println("【" + this.name + "交卷 - ｛预计交卷时间：" + this.submitTime + "｝】实际交卷时间：" + System.currentTimeMillis() + "、考试花费时间：" + this.workTime);
	}

	@Override
	public int compareTo(Delayed o) {
		return (int) (this.workTime - this.getDelay(TimeUnit.MILLISECONDS));
	}

	@Override
	public long getDelay(TimeUnit unit) { // 获取延迟时间
		return unit.convert(this.submitTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
	}
	
}