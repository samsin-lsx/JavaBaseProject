package cn.mldn.nio.charset;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

public class EncodeAndDecodeTest {

	public static void main(String[] args) throws CharacterCodingException {
		Charset charset = Charset.forName("UTF-8"); // 设置指定编码字符集
		CharsetEncoder encoder = charset.newEncoder(); // 获取编码类对象
		CharsetDecoder decoder = charset.newDecoder(); // 获取解码类对象
		String str = "上新科技：www.samsin.org";
		CharBuffer charBuffer = CharBuffer.allocate(50);
		charBuffer.put(str); // 将数据保存在字符缓冲区之中
		charBuffer.flip(); // 重设缓冲区
		ByteBuffer byteBuffer = encoder.encode(charBuffer); // 进行编码处理
		System.out.println(decoder.decode(byteBuffer)); // 进行解码处理
	}

}
