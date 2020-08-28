package cn.mldn.io.file.stream;

import java.io.File;
import java.util.Scanner;

public class ScannerTest {

	public static void main(String[] args) throws Exception {
		Scanner scan = new Scanner(new File("E:" + File.separator + "hello" + File.separator + "mldn.txt"));
		while (scan.hasNext()) {
			scan.useDelimiter("\n");
			System.out.print(scan.next());
		}
		scan.close();
	}

}
