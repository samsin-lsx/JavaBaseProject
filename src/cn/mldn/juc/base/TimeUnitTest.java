package cn.mldn.juc.base;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeUnitTest {
	public static void main(String[] args) {
		long time = TimeUnit.MILLISECONDS.convert(3, TimeUnit.DAYS);
		System.out.println("三天后的毫秒数：" + time);
		long threeDaysTime = System.currentTimeMillis() + time;
		System.out.println("三天后的日期：" + new Date(threeDaysTime));
		System.out.println("三天后的日期：" + new SimpleDateFormat("yyyy-MM-dd").format(new Date(threeDaysTime)));
	}
}
