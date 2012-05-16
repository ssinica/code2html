package com.ss.code2html.engine;

public enum CodeType {

	CSS, BASH, JAVA;

	public static CodeType getByName(String name, CodeType defaultValue) {
		for (CodeType th : values()) {
			if (th.name().equalsIgnoreCase(name)) {
				return th;
			}
		}
		return defaultValue;
	}

}
