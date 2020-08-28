package cn.mldn.io.exercise.test;

import cn.mldn.io.exercise.factory.Factory;
import cn.mldn.io.exercise.service.INumberService;

/**
 * 练习题1：实现输入指定个数的整数（5个），并求出5个整数的最大值、最小值
 * @author lishangxing
 */
public class NumberTest {
	public static void main(String[] args) {
		INumberService numberService = Factory.getINumberServiceInstance();
		int[] result = numberService.stat(5);
		System.out.println("最大值：" + result[0] + "、最小值：" + result[1]);
	}
}
