package cn.mldn.io.exercise.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import cn.mldn.io.exercise.service.IFileService;
import cn.mldn.io.exercise.util.InputUtil;

public class FileServiceImpl implements IFileService {
	private String name;
	private String content;
	
	public FileServiceImpl() {
		this.name = InputUtil.getString("请输入保存文件的名称：");
		this.content = InputUtil.getString("请输入保存文件的内容：");
	}

	@Override
	public boolean save() {
		File file = new File(IFileService.SAVE_DIR + this.name);
		PrintWriter out = null;
		try {
			out = new PrintWriter(new FileOutputStream(file));
			out.print(this.content);
			return true;
		} catch (FileNotFoundException e) {
			return false;
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

}
