package com.win.test.nginx;

import com.win.test.TestFile;

public class ConfigRewriteAndReloadTest {

	public static void main(String[] args) {
		TestFile file = new TestFile(System.getProperty("user.dir"));
		StringBuffer buffer = new StringBuffer();
		buffer.append("\tupstream TestAddUpstream {").append("\r\n")
			.append("\t\t# simple round-robin").append("\r\n")
			.append("\t\tserver www.baidu.com:80;").append("\r\n")
			.append("\t\tcheck interval=3000 rise=2 fall=5 timeout=1000 type=http;").append("\r\n")
			.append("\t}");
		file.addUpstream(buffer.toString());
		file.removeUpstream("TestAddUpstream");
	}

}
