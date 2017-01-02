package org.jve.siv.gui;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.jve.siv.engine.SivCoder;

public class Siv {
	
	
	
	public static void main(String args[])
	{
		//Pop up an input box with text ( What is your name ? )  
		String pwd=JOptionPane.showInputDialog(null,"Password ?"); 
		
		String msg = "Encoder fichiers ?";
		int result = JOptionPane.showConfirmDialog(null, msg, "Démarrage", JOptionPane.YES_NO_OPTION) ;
		if (result != JOptionPane.NO_OPTION)
			importFiles(pwd);
		else
			exportFiles(pwd);
		//showFiles(pwd);
		System.exit(0);
	}

	private static void exportFiles(String pwd) 
	{
		boolean termine = false;
		while (!termine)
		{
			JFileChooser fc;
	    	fc = new JFileChooser("");
	    	fc.setMultiSelectionEnabled(true);
	    	int returnVal = fc.showOpenDialog(null);
	    	
	    	File[] res = null;
	        if(returnVal == JFileChooser.APPROVE_OPTION) 
	        {
	           res = fc.getSelectedFiles();
	        }
	        
	        if (res != null)
	        {
	        	for (int i=0; i<res.length; i++)
	        	{
	        		SivCoder.decodeFile(pwd, res[i].getPath(), res[i].getPath());
	        		System.out.println("Fichier ["+res[i].getPath()+"] decrypte");
	        	}
	        }
	    	
			String msg = "Decoder plus de fichiers ?";
			int result = JOptionPane.showConfirmDialog(null, msg, "Démarrage", JOptionPane.YES_NO_OPTION) ;
			termine = (result == JOptionPane.NO_OPTION);
		}
	}
	
	public static void exportFiles(String pwd, ArrayList<File> files, File destRep) 
	{    
        if (files != null)
        {
        	for (int i=0; i<files.size(); i++)
        	{
        		SivCoder.decodeFile(pwd, files.get(i).getPath(), destRep);
        		System.out.println("Fichier ["+files.get(i).getPath()+"] decrypte");
        	}
        }	
	}

	private static void importFiles(String pwd) 
	{
		boolean termine = false;
		while (!termine)
		{
			JFileChooser fc;
	    	fc = new JFileChooser("");
	    	fc.setMultiSelectionEnabled(true);
	    	int returnVal = fc.showOpenDialog(null);
	    	
	    	File[] res = null;
	        if(returnVal == JFileChooser.APPROVE_OPTION) 
	        {
	           res = fc.getSelectedFiles();
	        }
	        
	        if (res != null)
	        {
	        	for (int i=0; i<res.length; i++)
	        	{
	        		SivCoder.encodeFile(pwd, res[i].getPath(), res[i].getPath());
	        		System.out.println("Fichier ["+res[i].getPath()+"] crypte");
	        	}
	        }
	    	
			String msg = "Encoder plus de fichiers ?";
			int result = JOptionPane.showConfirmDialog(null, msg, "Démarrage", JOptionPane.YES_NO_OPTION) ;
			termine = (result == JOptionPane.NO_OPTION);
		}
	}
	
	public static void importFiles(String pwd, ArrayList<File> files, File destRep) 
	{
		if (files != null)
        {
        	for (int i=0; i<files.size(); i++)
        	{
        		SivCoder.encodeFile(pwd, files.get(i).getPath(), destRep);
        		System.out.println("Fichier ["+files.get(i).getPath()+"] crypte");
        	}
        }
	}
	
}
