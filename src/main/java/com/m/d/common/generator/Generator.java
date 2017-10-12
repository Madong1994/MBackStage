package com.m.d.common.generator;

public class Generator {

	public static void main(String[] args) {
		
		String modelPackage = "com.m.d.common";
		
		String dbHost = "127.0.0.1";
		String dbName = "mchart";
		String dbUser = "root";
		String dbPassword = "123456";
		
		new JGenerator(modelPackage, dbHost, dbName, dbUser, dbPassword).doGenerate();

	}

}
