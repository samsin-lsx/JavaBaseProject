package cn.mldn.jvm;

/**
 * 获取JVM的类加载器
 * @author lishangxing
 */
public class ClassloaderTest {
	public static void main(String[] args) {
		System.out.println(Member.class.getClassLoader());
		System.out.println(Member.class.getClassLoader().getParent());
		System.out.println(Member.class.getClassLoader().getParent().getParent());
	}
}
class Member {
	
}