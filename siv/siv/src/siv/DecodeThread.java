package siv;

import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

public class DecodeThread extends Thread {
	
	private boolean decoded[] = null;
	private Vector<File> toDecode = null;
	private String pwd = null;
	private File destDir = null;
	
	public synchronized void setDecoded(boolean decoded[])
	{
		this.decoded = decoded;
	}
	
	public void setFiles(Vector<File> files)
	{
		this.toDecode = files;
	}
	
	public Vector<File> getFiles()
	{
		return toDecode;
	}
	
	public synchronized boolean[] getDecoded()
	{
		return decoded;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPwd() {
		return pwd;
	}

	public void setDestDir(File destDir) {
		this.destDir = destDir;
	}

	public File getDestDir() {
		return destDir;
	}
	
	@Override
	public void run() 
	{
    	System.out.println("------> Lauching decode process");
		for (int i=0; i<toDecode.size(); i++)
		{
    		SivCoder.decodeFile(pwd, toDecode.get(i).getPath(), destDir);
    		System.out.println("Fichier ["+toDecode.get(i).getPath()+"] decrypte");
    		decoded[i] = true;
		}
    	System.out.println("------> Decode process finished");
	}
	
}
