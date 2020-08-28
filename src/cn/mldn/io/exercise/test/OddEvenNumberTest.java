package cn.mldn.io.exercise.test;

import java.util.Arrays;

import cn.mldn.io.exercise.factory.Factory;

public class OddEvenNumberTest {

	public static void main(String[] args) {
		System.out.println(Arrays.toString(Factory.getINumberServiceInstance().countNumber()));
	}

}
