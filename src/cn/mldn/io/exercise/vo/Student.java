package cn.mldn.io.exercise.vo;

public class Student implements Comparable<Student> {
	private String name;
	private double score;
	public Student(String name, double score) {
		this.name = name;
		this.score = score;
	}

	@Override
	public int compareTo(Student obj) {
		if (this.score > obj.score) {
			return -1;
		} else if (this.score < obj.score) {
			return 1;
		} else {
			return 0;
		}
	}
	
	public String toString() {
		return "姓名：" + this.name + "、成绩：" + this.score;
	}

}
