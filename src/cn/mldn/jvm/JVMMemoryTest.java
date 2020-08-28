package cn.mldn.jvm;

/**
 * JVM可用内存查看方法
 * @author lishangxing
 */
public class JVMMemoryTest {
	public static void main(String[] args) {
		// 本机电脑内存为16G
		System.out.println("最大内存：" + (Runtime.getRuntime().maxMemory() / 1014 / 1024 / 1024) + "G"); // 默认为本机内存的1/4
		System.out.println("可用内存：" + (Runtime.getRuntime().totalMemory() / 1024 / 1024) + "M"); // 默认为本机内存的1/64
		System.out.println("伸缩区内存：" + ((Runtime.getRuntime().maxMemory() - Runtime.getRuntime().totalMemory()) / 1024 / 1024 / 1024) + "G"); // 默认为最大内存 - 可用内存
	}
}
