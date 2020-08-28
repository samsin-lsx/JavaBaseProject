package cn.mldn.io.exercise.service;

public interface INumberService {
	/**
	 * 实现所输入数据中的最大值和最小值
	 * @param count 输入的数据个数
	 * @return 包含有两个内容：第一个为最大值、第二个为最小值
	 */
	public int[] stat(int count);
	
	/**
	 * 实现统计奇偶数的个数
	 * @return 第一个为偶数的个数，第二个为奇数的个数
	 */
	public int[] countNumber();
}
