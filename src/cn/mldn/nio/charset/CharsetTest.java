package cn.mldn.nio.charset;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.SortedMap;

/**
 * 文件编码处理类
 * @author lishangxing
 */
public class CharsetTest {
	public static void main(String[] args) {
		SortedMap<String, Charset> map = Charset.availableCharsets();
		for (Map.Entry<String, Charset> entry : map.entrySet()) {
			System.out.println(entry.getKey() + " = " + entry.getValue());
		}
	}
}
