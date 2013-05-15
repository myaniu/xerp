package com.google.code.insect.workflow.config;


import java.io.InputStream;
import java.io.Serializable;

import com.google.code.insect.workflow.comm.Common;
import com.google.code.insect.workflow.util.FileConfig;

public class DefaultConfiguration implements Configuration, Serializable {

	public InputStream getConfigFileStream() {
		return FileConfig.getInputStream(Common.DEFAUT_CONFIG_FILE);
	}

	public String getFileName() {
		return Common.DEFAUT_CONFIG_FILE;
	}

}
