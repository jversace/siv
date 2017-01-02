package siv;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class FileSplitter {
	
	private static final int SPLIT_SIZE = 10000000;
	
	public static ArrayList<File> splitFile(File file) throws IOException
	{
		ArrayList<File> res = new ArrayList<File>();
		RandomAccessFile f = new RandomAccessFile(file.getPath(), "r");
		int size = (int) f.length();
		int nbfiles = size /  SPLIT_SIZE;
		int reste = size % SPLIT_SIZE;
		System.out.println("Size=["+size+"] - nbFiles=["+nbfiles+"] - reste=["+reste+"]");
		for (int i=0; i< nbfiles; i++)
		{
			byte[] b = new byte[SPLIT_SIZE];
			f.read(b);
			Tools.saveFile(file.getPath()+"_"+i, b);
			res.add(new File(file.getPath()+"_"+i));
		}
		if (reste > 0)
		{
			nbfiles++;
			byte[] b = new byte[reste];
			f.read(b);
			Tools.saveFile(file.getPath()+"_"+nbfiles, b);
			res.add(new File(file.getPath()+"_"+nbfiles));
		}
		
		f.close();
		f = null;
		System.gc();
		
		return res;
	}
	
	public static ArrayList<File> splitFile(File file, File destDir) throws IOException
	{
		ArrayList<File> res = new ArrayList<File>();
		RandomAccessFile f = new RandomAccessFile(file.getPath(), "r");
		int size = (int) f.length();
		int nbfiles = size /  SPLIT_SIZE;
		int reste = size % SPLIT_SIZE;
		System.out.println("Size=["+size+"] - nbFiles=["+nbfiles+"] - reste=["+reste+"]");
		for (int i=0; i< nbfiles; i++)
		{
			byte[] b = new byte[SPLIT_SIZE];
			f.read(b);
			Tools.saveFile(destDir.getName()+File.separator+file.getName()+"_"+i, b);
			res.add(new File(destDir.getName()+File.separator+file.getName()+"_"+i));
		}
		if (reste > 0)
		{
			nbfiles++;
			byte[] b = new byte[reste];
			f.read(b);
			Tools.saveFile(destDir.getName()+File.separator+file.getName()+"_"+nbfiles, b);
			res.add(new File(destDir.getName()+File.separator+file.getName()+"_"+nbfiles));
		}
		
		f.close();
		f = null;
		System.gc();
		
		return res;
	}
	
	public static String mergeFiles(ArrayList<File> files, String res) throws IOException
	{
		//String newFileName = null;
		for (int i=0; i<files.size(); i++)
		{
			if (i==0)
			{
				//newFileName = files.get(i).getPath().substring(0, files.get(i).getPath().lastIndexOf("_") - 1);
				byte[] contents = Tools.loadFiletoBytes(files.get(i));
				files.get(i).setWritable(true);
				files.get(i).delete();
				Tools.saveFile(res, contents);
			}
			else
			{
			    FileWriter fw = new FileWriter(res,true); //the true will append the new data
			    byte[] contents = Tools.loadFiletoBytes(files.get(i));
			    files.get(i).setWritable(true);
			    files.get(i).delete();
			    fw.write(new String(contents));//appends the string to the file
			    fw.close();
			    fw = null;
			    System.gc();
			}
		}
			
		return res;
	}

}
