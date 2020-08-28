package cn.mldn.io.exercise.service;

public interface IStringService {
	/**
	 * 实现输入字符串的追加处理
	 * @param str 输入的字符串（每次输入的字符串都以“|”进行分割）
	 */
	public void append(String str);
	
	/**
	 * 实现字符串的逆序输出
	 * @return 返回一个逆序的数组
	 */
	public String[] reverse();
}
