package com.google.code.insect.workflow.util;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

public class FileConfig {

	public static File getFile(String name) {
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		File file = null;
		if (file == null) {
			try {
				file = new File(classLoader.getResource(name).getFile());
			} catch (Exception e) {
				file = null;
			}
		}

		if (file == null) {
			try {
				file = new File(classLoader.getResource('/' + name).getFile());
			} catch (Exception e) {
				file = null;
			}
		}

		if (file == null) {
			try {
				file = new File(classLoader.getResource("META-INF/" + name)
						.getFile());
			} catch (Exception e) {
				file = null;
			}
		}

		if (file == null) {
			try {
				file = new File(classLoader.getResource("/META-INF/" + name)
						.getFile());
			} catch (Exception e) {
				file = null;
			}
		}
		return file;

	}

	public static InputStream getInputStream(String name) {
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		InputStream is = null;

		if ((name != null) && (name.indexOf(":/") > -1)) {
			try {
				is = new URL(name).openStream();
			} catch (Exception e) {
			}
		}

		if (is == null) {
			try {
				is = classLoader.getResourceAsStream(name);
			} catch (Exception e) {
			}
		}

		if (is == null) {
			try {
				is = classLoader.getResourceAsStream('/' + name);
			} catch (Exception e) {
			}
		}

		if (is == null) {
			try {
				is = classLoader.getResourceAsStream("META-INF/" + name);
			} catch (Exception e) {
			}
		}

		if (is == null) {
			try {
				is = classLoader.getResourceAsStream("/META-INF/" + name);
			} catch (Exception e) {
			}
		}

		return is;

	}
}
