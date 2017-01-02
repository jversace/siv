package org.jve.siv.engine;

import java.io.File;

public class SivFile {

	public static final int STATUS_UNDEFINED = 0;
	public static final int STATUS_ENCODED_FS = 10;
	public static final int STATUS_ENCODED_MEM = 20;
	public static final int STATUS_DECODED_FS = 30;
	public static final int STATUS_DECODED_MEM = 30;
	
	private File file;
	private int status = STATUS_UNDEFINED;
	
	public SivFile(File file) {
		super();
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
