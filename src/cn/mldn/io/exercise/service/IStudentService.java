package cn.mldn.io.exercise.service;

import cn.mldn.io.exercise.vo.Student;

public interface IStudentService {
	/**
	 * 实现学生成绩的降序输出
	 * @return 返回已经排序好的学生成绩
	 */
	public Student[] getScoreData();
	
	/**
	 * 追加数据并保存到文件中
	 * @param str 输入的学生成绩数据
	 */
	public void append(String str);
}
