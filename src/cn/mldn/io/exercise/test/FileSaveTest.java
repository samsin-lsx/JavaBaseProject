package cn.mldn.io.exercise.test;

import cn.mldn.io.exercise.factory.Factory;
import cn.mldn.io.exercise.service.IFileService;

/**
 * 练习题2：从键盘输入文件的内容和要保存的文件名称，然后根据输入的名称创建文件，并将内容保存到文件中
 * @author lishangxing
 */
public class FileSaveTest {
	public static void main(String[] args) {
		IFileService fileService = Factory.getIFileServiceInstance();
		System.out.println(fileService.save());
	}
}
