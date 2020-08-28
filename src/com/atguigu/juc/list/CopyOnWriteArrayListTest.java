package com.atguigu.juc.list;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * CopyOnWriteArrayList：写入并复制
 * 使用场景：并发迭代操作多时推荐使用，如果是添加操作多时，则不推荐，因为每次添加时都会进行复制，内存开销非常大，效率低。
 * @author lishangxing
 */
public class CopyOnWriteArrayListTest {
	public static void main(String[] args) {
		CopyOnWriteArrayListThread cowalt = new CopyOnWriteArrayListThread();
		for (int i = 0; i < 10; i++) {
			new Thread(cowalt).start();
		}
	}
}
class CopyOnWriteArrayListThread implements Runnable {
	// private static List<String> lists = Collections.synchronizedList(new ArrayList<String>());
	private static CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<String>();
	
	static {
		list.add("AA");
		list.add("BB");
		list.add("CC");
		list.add("DD");
	}

	@Override
	public void run() {
		Iterator<String> iter = list.iterator();
		while (iter.hasNext()) {
			System.out.println(iter.next());
			list.add("AA");
		}
	}
	
}
