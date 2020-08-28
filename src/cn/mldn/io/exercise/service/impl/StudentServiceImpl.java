package cn.mldn.io.exercise.service.impl;

import java.io.File;
import java.util.Arrays;

import cn.mldn.io.exercise.service.IStudentService;
import cn.mldn.io.exercise.util.FileUtil;
import cn.mldn.io.exercise.vo.Student;

public class StudentServiceImpl implements IStudentService {
	private static final File SAVE_DIR = new File("E:" + File.separator + "hello" + File.separator + "sutdent.txt");
	private String content; // 接收输入的学生成绩，格式为：姓名:成绩|姓名:成绩|姓名:成绩
	private Student[] students;
	
	public StudentServiceImpl() {
		this.content = FileUtil.load(SAVE_DIR);
		this.handle();
	}
	
	/**
	 * 进行字符串数据的处理
	 */
	public void handle() {
		if (this.content == null || "".equals(this.content)) {
			return;
		}
		String[] result = this.content.split("\\|");
		this.students = new Student[result.length];
		for (int x = 0; x < result.length; x++) {
			String[] temp = result[x].split(":");
			this.students[x] = new Student(temp[0], Double.parseDouble(temp[1]));
		}
	}

	@Override
	public Student[] getScoreData() {
		Arrays.sort(this.students);
		return this.students;
	}

	@Override
	public void append(String str) {
		if (str.startsWith("|")) { // 如果最前面有“|”
			str = str.substring(1); // 截取“|”后面的部分
		}
		if (!str.endsWith("|")) { // 数据格式正确，可以追加
			str = str + "|"; // 加上“|”与后面的数据进行分割 
		}
		FileUtil.append(SAVE_DIR, str); // 数据追加处理
	}

}
