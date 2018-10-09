package com.example.demo;

public class TestMain {

	public static void main(String[] args) throws InterruptedException {
		
		long l = System.currentTimeMillis();
		
		Thread.sleep(3000);
		
		System.out.println("test:"+ (System.currentTimeMillis() - l));

	}

}
