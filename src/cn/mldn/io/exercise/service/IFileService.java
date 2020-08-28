package cn.mldn.io.exercise.service;

import java.io.File;

public interface IFileService {
	public static final String SAVE_DIR = "E:" + File.separator + "hello" + File.separator;
	/**
	 * 实现文件的保存处理
	 * @return 如果保存成功返回true，否则返回false
	 */
	public boolean save();
}
