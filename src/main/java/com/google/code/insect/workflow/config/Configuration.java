package com.google.code.insect.workflow.config;

import java.io.InputStream;

public interface Configuration {
	public InputStream getConfigFileStream();
	public String getFileName();
}
