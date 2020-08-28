package cn.mldn.io.exercise.test;

import java.util.Arrays;

import cn.mldn.io.exercise.factory.Factory;
import cn.mldn.io.exercise.service.IStudentService;
import cn.mldn.io.exercise.util.InputUtil;

public class StudentScoreMenu {
	public StudentScoreMenu() {
		this.choose();
	}
	
	public void choose() {
		this.show();
		String choose = InputUtil.getString("请选择输入的菜单：");
		switch (choose) {
		case "1": {
			String str = InputUtil.getString("请输入学生成绩（格式为姓名:成绩|姓名:成绩|...）：");
			IStudentService studentService = Factory.getIStudentServiceInstance();
			studentService.append(str); // 追加数据
			this.choose();
		}
		case "2": {
			System.out.println(Arrays.toString(Factory.getIStudentServiceInstance().getScoreData()));
			this.choose();
		}
		case "0": {
			System.out.println("退出成绩输入界面，拜拜！");
			System.exit(1);
		}
		default: {
			System.out.println("您输入的操作不合法，请确认后再次选择要操作的菜单。");
			this.choose();
		}
		}
	}
	
	public void show() {
		System.out.println("【1】追加学生成绩数据；");
		System.out.println("【2】读取学生成绩数据；");
		System.out.println("【0】结束程序执行操作。");
	}
}
