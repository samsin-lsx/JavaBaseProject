package cn.mldn.nio.lock;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.concurrent.TimeUnit;

/**
 * 文件锁
 * @author lishangxing
 */
public class FileLockTest {
	public static void main(String[] args) {
		File file = new File("E:" + File.separator + "hello" + File.separator + "mldn.txt");
		FileOutputStream output = null;
		FileChannel channel = null;
		try {
			output = new FileOutputStream(file);
			channel = output.getChannel(); // 获取文件通道
			FileLock fileLock = channel.tryLock(); // 尝试获取文件锁
			if (fileLock != null) { // 已经获取到了文件锁
				System.out.println("*********** 【" + file.getName() + "】将锁定30秒钟的时间，请稍等 ***********");
				TimeUnit.SECONDS.sleep(30);
				fileLock.release();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (channel != null) {
				try {
					channel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
