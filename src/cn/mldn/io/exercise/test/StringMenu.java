package cn.mldn.io.exercise.test;

import java.util.Arrays;

import cn.mldn.io.exercise.factory.Factory;
import cn.mldn.io.exercise.service.IStringService;
import cn.mldn.io.exercise.util.InputUtil;

public class StringMenu {
	private IStringService stringService;
	
	public StringMenu() {
		this.stringService = Factory.getIStringServiceInstance();
		this.choose();
	}
	
	public void choose() {
		this.show();
		String choose = InputUtil.getString("请选择要操作的菜单项：");
		switch (choose) {
		case "1": {
			String str = InputUtil.getString("请输入字符串：");
			this.stringService.append(str); // 保存输入的字符串数据
			this.choose(); // 重复出现输入
		}
		case "2": {
			String[] result = this.stringService.reverse();
			System.out.println(Arrays.toString(result)); // 输出字符串
			this.choose(); // 重复出现输入
		}
		case "0": {
			System.out.println("程序已退出，拜拜！");
			System.exit(1);
		}
		default: {
			System.out.println("您输入的菜单项不合法，请确认后再次进行输入。");
			this.choose(); // 重复出现输入
		}
		}
	}

	/**
	 * 实现输入菜单选择
	 */
	public void show() {
		System.out.println("【1】追加字符串数据；");
		System.out.println("【2】逆序显示字符串数据；");
		System.out.println("【0】结束程序。");
	}
}
