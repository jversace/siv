/**
 * 
 */
package siv;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.StringTokenizer;


/**
 * <p>
 * Cet(te) classe / interface / enum  est utilisé(e) par ... pour ...
 * </p>
 * <p>
 * <b>© Copyright 2009 CTI - État de Genève.</b>
 * </p>
 * <p>
 * <b>Société</b> : CTI - État de Genève
 * {@link <a href="http://www.ge.ch/cti/welcome.html"> CTI - État de Genève </a>}
 * </p>
 * <p>
 * <b>Projet</b> : precomposition
 * </p>
 * <p>
 * <b>Historique des modifications</b> :
 * <br>
 * <br>
 * 23 sept. 2009 - création du fichier.
 * <br>
 * <!-- date - {@link <a href="">lien vers JIRA</a>} -->
 * <br>
 * </p>
 * @author versacej, machop
 */
public class Tools {

	/**
	 * Converti le contenu d'un fichier texte en string.
	 * @param fileName le fullpath sur le fichier à lire.
	 * @return la String correspondant au contenu du fichier.
	 * @throws IOException
	 */
	public static String loadFile(String fileName) throws IOException 
	{
		File file = new File(fileName);
		return loadFile(file);
		
	}
	
	public static String loadFile(File file) throws IOException 
	{
	    FileInputStream fis = null;
	    BufferedInputStream bis = null;
	    String fileStr = "";

	    fis = new FileInputStream(file);
    
	    // Here BufferedInputStream is added for fast reading.
	    bis = new BufferedInputStream(fis);
	    
	    int available = bis.available();
	    while (available > 0)
	    {
	    	byte[] buffer = new byte[available];
	    	bis.read(buffer);
	    	for (int i=0; i<buffer.length;i++)
	    		fileStr += String.valueOf((byte) buffer[i]);
	    	available = bis.available();
	    }
	    
	    // dispose all the resources after using them.
	    fis.close();
	    bis.close();
	    
	    fis = null;
	    bis = null;
	    System.gc();
		
		return fileStr;
	}
	
	public static byte[] loadFiletoBytes(File file) throws IOException 
	{
		RandomAccessFile f = new RandomAccessFile(file.getPath(), "r");;
		byte[] b = new byte[(int)f.length()];
		f.read(b);
		f.close();
		f = null;
		System.gc();
		return b;
	}
	
	public static void saveFile(String fileName, byte[] contents) throws IOException 
	{
		File fileIn = new File(fileName);
		
		if (! fileIn.exists()) {
			new File(Tools.getPathFromFile(fileIn)).mkdirs();
		}

		FileOutputStream fos = new FileOutputStream(fileName);
		fos.write(contents);
		fos.flush();
		fos.close();
		fos = null;
		System.gc();
	}

	public static String getPathFromFile(File f){
		return f.getAbsolutePath().substring(0,  (f.getAbsolutePath().length() - f.getName().length() ) );
	}
	
	public static String getNomFichierFromFullPath(String aFullPath){
		StringTokenizer stk = new StringTokenizer(aFullPath, "\\");
		String t=stk.nextToken();
		while(stk.hasMoreTokens()){
			t = stk.nextToken();
		}
		return t.trim();
	}
	
	public static String getNomFichierSansExtension(String fileName) {
		StringTokenizer stk = new StringTokenizer(fileName, ".");
		return stk.nextToken().trim();		
	}
	
	public static String getNomFichierSansExtensionFromFullPath(String aFullPath){
		return getNomFichierSansExtension(getNomFichierFromFullPath(aFullPath));
	}
	
	public static void cleanDirectory(String dirName)
	{
		String tmpDirName = dirName;
		if (tmpDirName.endsWith(File.separator))
			tmpDirName += File.separator;
		Runtime runtime = Runtime.getRuntime();
		String[] args = { "cmd.exe", "/C", "DEL", "/Q", tmpDirName+ "*"};
		try 
		{
			final Process process = runtime.exec(args);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void checkDirEmpty(String dirName)
	{
		Runtime runtime = Runtime.getRuntime();
		String[] args = { "explorer.exe", dirName};
		try 
		{
			final Process process = runtime.exec(args);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
