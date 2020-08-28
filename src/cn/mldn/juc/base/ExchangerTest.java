package cn.mldn.juc.base;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

public class ExchangerTest {

	public static void main(String[] args) {
		Exchanger<String> exc = new Exchanger<>(); // 准备一个交换空间
		for (int x = 0; x < 3; x++) { // 定义3个消费线程
			new Thread(() -> {
				while (true) {
					try {
						String data = exc.exchange(null);
						TimeUnit.SECONDS.sleep(2);
						if (data != null) { // 取得了生产的数据
							System.out.println("【" + Thread.currentThread().getName() + "】" + data);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}, "消费者 - " + x).start();
		}
		for (int x = 0; x < 2; x++) { // 定义2个生产者线程
			int temp = x;
			new Thread(new Runnable() {
				@Override
				public void run() {
					for (int y = 0; y < 2; y++) {
						String data = "老婆 - 我爱你第" + temp + "次：" + y;
						try {
							TimeUnit.SECONDS.sleep(2);
							exc.exchange(data); // 将生产的数据保存在交换空间
							System.out.println("【" + Thread.currentThread().getName() + "】生产了数据：" + data);
						} catch (InterruptedException e) {
							e.printStackTrace();
						} 
					}
				}
			}, "生产者 - " + x).start();
		}
	}

}
