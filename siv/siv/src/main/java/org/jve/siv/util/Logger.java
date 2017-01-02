package org.jve.siv.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
	
	private PrintStream out;
	
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy.dd.MM HH:mm:ss");
	
	public static final int MSGTYPE_INFO = 0;
	public static final int MSGTYPE_WARNING = 1;
	public static final int MSGTYPE_DEBUG = 2;
	public static final int MSGTYPE_ERROR = 3;
	
	private static final String[] messageTypes = {"INFO","WARN","DEBG","ERRO"};
	
	public static Logger createLogger(String fileName) throws IOException{
		Logger logger = new Logger();
		
		if (isDevelopment())
			logger.out = System.out;
		else{
			File f = new File(fileName);
			if (!f.exists())
				f.createNewFile();
			logger.out = new PrintStream(new FileOutputStream (f));
		}
		
		return logger;
	}
	
	private static boolean isDevelopment() {
		/* try {
			return new File(".").getCanonicalPath().contains("workspace");
		} catch (IOException e) {
			return false;
		}*/
		return false;
	}

	
	private void message(int msgType, String msgStr){
		out.println(format.format(new Date()) + " " + messageTypes[msgType] + " - " + msgStr);
	}
	
	public void info(String msgStr){
		message(MSGTYPE_INFO, msgStr);
	}
	public void warning(String msgStr){
		message(MSGTYPE_WARNING, msgStr);
	}
	public void debug(String msgStr){
		message(MSGTYPE_DEBUG, msgStr);
	}
	public void error(String msgStr){
		message(MSGTYPE_ERROR, msgStr);
	}
}
