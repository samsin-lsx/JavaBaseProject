package cn.mldn.io.exercise.factory;

import cn.mldn.io.exercise.service.IFileService;
import cn.mldn.io.exercise.service.INumberService;
import cn.mldn.io.exercise.service.IStringService;
import cn.mldn.io.exercise.service.IStudentService;
import cn.mldn.io.exercise.service.impl.FileServiceImpl;
import cn.mldn.io.exercise.service.impl.NumberServiceImpl;
import cn.mldn.io.exercise.service.impl.StringServiceImpl;
import cn.mldn.io.exercise.service.impl.StudentServiceImpl;

public class Factory {
	private Factory() {}
	public static INumberService getINumberServiceInstance() {
		return new NumberServiceImpl();
	}
	
	public static IFileService getIFileServiceInstance() {
		return new FileServiceImpl();
	}
	
	public static IStringService getIStringServiceInstance() {
		return new StringServiceImpl();
	}
	
	public static IStudentService getIStudentServiceInstance() {
		return new StudentServiceImpl();
	}
}
