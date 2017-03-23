package com.win.nginx;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;

public class ConfigRewriteAndReload {
	private final String DEFAULT_NGINX_LOCATION = "/usr/local/nginx/";
	private final String CONF = "conf/nginx.conf";
	private final String NAINX_RELOAD = "sbin/nginx -s reload";
	private String NGINX_LOCATION;
	private StringBuffer headTemp = null;
	private StringBuffer footTemp = null;
	private File config = null;

	public ConfigRewriteAndReload() {
		this(null);
	}

	public ConfigRewriteAndReload(String location) {
		NGINX_LOCATION = null == location ? DEFAULT_NGINX_LOCATION : location;
		if (!NGINX_LOCATION.endsWith(File.separator)) {
			NGINX_LOCATION += File.separator;
		}
	}

	public boolean addUpstream(String insertString) {
		boolean ret = true;
		init();
		try {
			writeToTemp();
			writeToConf(insertString);
		} catch (IOException e) {
			e.printStackTrace();
			ret = false;
		}
		close();
		ret = reloadConfig();
		return ret;
	}

	public boolean removeUpstream(String upstream) {
		boolean ret = true;
		init();
		try {
			writeToTemp(upstream);
			writeToConf();
		} catch (IOException e) {
			e.printStackTrace();
			ret = false;
		}
		close();
		ret = reloadConfig();
		return ret;
	}

	private void init() {
		headTemp = new StringBuffer();
		footTemp = new StringBuffer();
		config = new File(NGINX_LOCATION + CONF);
	}

	private void writeToTemp() throws IOException {
		Reader reader = new FileReader(config);
		BufferedReader bffReader = new BufferedReader(reader);
		String stringBuffer = null;
		while (null != (stringBuffer = bffReader.readLine())) {
			headTemp.append(stringBuffer + "\r\n");
			if ("http {".equals(stringBuffer.trim()))
				break;
		}
		while (null != (stringBuffer = bffReader.readLine())) {
			footTemp.append("\r\n").append(stringBuffer);
		}
		bffReader.close();
	}

	private void writeToTemp(String upstream) throws IOException {
		Reader reader = new FileReader(config);
		String tag = "upstream " + upstream + " {";
		BufferedReader bffReader = new BufferedReader(reader);
		String stringBuffer = null;
		while (null != (stringBuffer = bffReader.readLine())) {
			if (tag.equals(stringBuffer.trim()))
				break;
			headTemp.append(stringBuffer + "\r\n");
		}
		while (null != (stringBuffer = bffReader.readLine()))
			if ("}".equals(stringBuffer.trim()))
				break;
		while (null != (stringBuffer = bffReader.readLine())) {
			footTemp.append(stringBuffer).append("\r\n");
		}
		bffReader.close();
	}

	private void writeToConf() throws IOException {
		OutputStream os = new FileOutputStream(config);
		os.write(headTemp.toString().getBytes());
		os.write(footTemp.toString().getBytes());
		os.close();
	}

	private void writeToConf(String insertVal) throws IOException {
		OutputStream os = new FileOutputStream(config);
		os.write(headTemp.toString().getBytes());
		os.write(insertVal.getBytes());
		os.write(footTemp.toString().getBytes());
		os.close();
	}

	private boolean reloadConfig() {
		boolean ret = true;
		Process process = null;
		Runtime rt = Runtime.getRuntime();
		try {
			process = rt.exec(NGINX_LOCATION + NAINX_RELOAD);
			InputStream is = process.getErrorStream();
			Reader reader = new InputStreamReader(is);
			char[] buffer = new char[108];
			int readNum = -1;
			StringBuffer error = new StringBuffer();
			while (-1 != (readNum = reader.read(buffer))) {
				error.append(buffer, 0, readNum);
			}
			if (0 != error.length()) {
				System.out.println("ERROR: " + error.toString());
				ret = false;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			ret = false;
		}
		return ret;
	}

	private void close() {
		headTemp = null;
		footTemp = null;
		config = null;
	}
}
