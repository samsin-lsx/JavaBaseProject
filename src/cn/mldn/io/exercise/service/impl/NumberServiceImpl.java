package cn.mldn.io.exercise.service.impl;

import cn.mldn.io.exercise.service.INumberService;
import cn.mldn.io.exercise.util.InputUtil;

public class NumberServiceImpl implements INumberService {

	@Override
	public int[] stat(int count) {
		int[] result = new int[2]; // 定义返回的结果
		int[] data = new int[count]; // 开辟一个输入的数字数组
		for (int x = 0; x< data.length; x++) { // 数字的循环输入
//			data[x] = InputUtil.getInt("请输入第" + (x + 1) + "个数字：");
			data[x] = InputUtil.getInt(x + 1);
		}
		result[0] = data[0]; // 假设是最大值
		result[1] = data[0]; // 假设是最小值
		for (int x = 0; x < data.length; x++) {
			if (data[x] > result[0]) {
				result[0] = data[x];
			}
			if (data[x] < result[1]) {
				result[1] = data[x];
			}
		}
		return result;
	}

	@Override
	public int[] countNumber() {
		int[] result = new int[] {0, 0};
		String str = InputUtil.getString("请输入数字信息：");
		String[] numArray = str.split(""); // 按照每个字符拆分
		for (int x = 0; x < numArray.length; x++) {
			if (Integer.parseInt(numArray[x]) % 2 == 0) {
				result[0]++; // 偶数个数加1
			} else {
				result[1]++; // 奇数个数加1
			}
		}
		return result;
	}

}
