package org.jve.siv.gui;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jve.siv.engine.DecodeThread;
import org.jve.siv.engine.SivCoder;
import org.jve.siv.util.Logger;

public class SivFrame extends JFrame {
	
	private String mdp = "";
	private String asterixmdp = "MDP : non saisi";
	private File tempDir = new File("temp");
	private File lastDir = null;
	private ImageViewer viewer = null;
	
	private JLabel pwdLabel = new JLabel(asterixmdp);
	
	private Logger logger; 
	
	private Rectangle bounds;
	private boolean fullScreen = false;
	
	
    private class MyDispatcher implements KeyEventDispatcher {
        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if (e.getID() == KeyEvent.KEY_PRESSED) {
            } else if (e.getID() == KeyEvent.KEY_RELEASED) {
            } else if (e.getID() == KeyEvent.KEY_TYPED) {
            	if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            		setFullScreen(false);
            }
            return false;
        }
    }
	
	public SivFrame()
	{
		super("SIV");
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		try{
			logger = Logger.createLogger("Siv.log");
		}
		catch (Exception e){
			System.out.println("Impossible de créer le logger");
		}
		
		if (!tempDir.exists())
			tempDir.mkdir();
		
		logger.info("Lauching...");
		
		File usr = new File("siv.usr");

		
		if (usr.exists())
		{
			Properties props=new Properties();
			try 
			{
				props.load(new FileInputStream(usr));
				String lastDirStr = props.getProperty("lastdir");
				if (lastDirStr != null)
					if (!lastDirStr.equals(""))
					{
						lastDir = new File(lastDirStr);
						logger.info("LASTDIRSTR="+lastDirStr);
					}
			}
			catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		WindowListener l = new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				
				if (viewer != null)
					viewer.releaseImages();
				
		    	logger.info("Deleting temp files...");
		    	try{
		    		File toDelete[] = tempDir.listFiles();
		    		for (int i=0; i<toDelete.length; i++)
		    			toDelete[i].delete();
		    		toDelete = tempDir.listFiles();
		    		for (int i=0; i<toDelete.length; i++)
		    			toDelete[i].delete();
		    		File cache = new File("cache");
		    		toDelete = cache.listFiles();
		    		for (int i=0; i<toDelete.length; i++)
		    			toDelete[i].delete();
		    		//Tools.cleanDirectory(tempDir.getAbsolutePath());
		    		logger.info(tempDir.getAbsolutePath());
		    	}
		    	catch (Exception ex)
		    	{}
		    	logger.info("Temp files deleted...");
		    /*	Tools.checkDirEmpty(tempDir.getAbsolutePath());
		    	Tools.checkDirEmpty("cache");*/
				System.exit(0);
			}
		};


		addWindowListener(l);

		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
	    manager.addKeyEventDispatcher(new MyDispatcher());

		JPanel panneau = new JPanel();
		
		JButton bouton = new JButton("Entrer MDP");
		bouton.addActionListener( new ActionListener() 
			{
				public void actionPerformed(ActionEvent e)
				{
					mdp=JOptionPane.showInputDialog(null,"Password ?", mdp);
					if (mdp != null)
					{
						asterixmdp = "MDP : ";
						for (int i=0; i<mdp.length(); i++)
						{
							if (mdp.charAt(i) != ' ')
								asterixmdp += '*';
							else asterixmdp += ' ';
							if (i > 20)
								break;
						}
						pwdLabel.setText(asterixmdp);
						pack();
					}
				}
			}
		);
		
		panneau.add(pwdLabel);
		panneau.add(bouton);
		
		JButton boutonImp = new JButton("Importer");
		boutonImp.addActionListener( new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				importFiles();
			}

		}
		);
		JButton boutonExp = new JButton("Exporter");
		boutonExp.addActionListener( new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				exportFiles();
			}

		}
		);
		
		JButton boutonView = new JButton("Visualiser");
		boutonView.addActionListener( new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				viewFiles();
			}

		}
		);
		
		JButton boutonFs = new JButton("Full Screen");
		boutonFs.addActionListener( new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				setFullScreen(!fullScreen);
			}

		}
		);
		
		panneau.add(boutonImp);
		panneau.add(boutonExp);
		panneau.add(boutonView);
		panneau.add(boutonFs);

		setContentPane(panneau);
		pack();
		//setSize(200,100);
		setVisible(true);
	}
	
	private void importFiles() 
	{
		if (mdp == null)
		{
			JOptionPane.showMessageDialog(null, "Veuillez d'abord saisir un mot de passe");
			return;
		}
		if (mdp.equals(""))
		{
			JOptionPane.showMessageDialog(null, "Veuillez d'abord saisir un mot de passe");
			return;
		}
		
		ArrayList<File> selectedFiles = selectFiles("Fichiers à importer");
		if (selectedFiles == null)
		{
			JOptionPane.showMessageDialog(null, "Importation annulée");
			return;
		}
		if (selectedFiles.size() == 0)
		{
			JOptionPane.showMessageDialog(null, "Importation annulée");
			return;
		}
		File destDir = selectDir("Répertoire de destination");
		if (destDir == null)
		{
			JOptionPane.showMessageDialog(null, "Importation annulée");
			return;
		}
						
		Siv.importFiles(mdp, selectedFiles, destDir);
		JOptionPane.showMessageDialog(null, "Importation terminée dans "+destDir.getPath());
			
	}
	
	private void exportFiles() 
	{
		if (mdp == null)
		{
			JOptionPane.showMessageDialog(null, "Veuillez d'abord saisir un mot de passe");
			return;
		}
		if (mdp.equals(""))
		{
			JOptionPane.showMessageDialog(null, "Veuillez d'abord saisir un mot de passe");
			return;
		}
		
		ArrayList<File> selectedFiles = selectFiles("Fichiers à exporter");
		if (selectedFiles == null)
		{
			JOptionPane.showMessageDialog(null, "Exportation annulée");
			return;
		}
		if (selectedFiles.size() == 0)
		{
			JOptionPane.showMessageDialog(null, "Exportation annulée");
			return;
		}
		File destDir = selectDir("Répertoire de destination");
		if (destDir == null)
		{
			JOptionPane.showMessageDialog(null, "Exportation annulée");
			return;
		}
		
		Siv.exportFiles(mdp, selectedFiles, destDir);
		JOptionPane.showMessageDialog(null, "Exportation terminée dans "+destDir.getPath());
	}
	
	private void viewFiles() 
	{
	
		logger.info("tenmpdir=["+tempDir+"]");
		
		if (tempDir != null){
			System.out.println("Deleting temp files...");
			File toDelete[] = tempDir.listFiles();
			for (int i=0; i<toDelete.length; i++)
				toDelete[i].delete();
			toDelete = tempDir.listFiles();
			for (int i=0; i<toDelete.length; i++)
				toDelete[i].delete();
			//Tools.cleanDirectory(tempDir.getAbsolutePath());
			logger.info("Temp files deleted...");
		}
		
		if (mdp == null)
		{
			JOptionPane.showMessageDialog(null, "Veuillez d'abord saisir un mot de passe");
			return;
		}
		if (mdp.equals(""))
		{
			JOptionPane.showMessageDialog(null, "Veuillez d'abord saisir un mot de passe");
			return;
		}
		
		File selectDir = selectDir("Choix du répertoire");
		
		if (selectDir == null)
			return;
		
		File[] filesArray = selectDir.listFiles();
    	
    	Vector<File> files = new Vector<File>();
    	for (int i=0; i<filesArray.length;i++)
    	{
    		if (ImageViewer.isImage(filesArray[i])){
    			files.add(filesArray[i]);
    			logger.info("Image found : ["+filesArray[i].getName()+"]");
    		}
    	}
    	
		for (int i=0; i<files.size(); i++)
		{
    		SivCoder.decodeFile(mdp, files.get(i).getPath(), tempDir);
    		logger.info("Fichier ["+files.get(i).getPath()+"] decrypte");
		}
    	
    	
		
		filesArray = tempDir.listFiles();
    	files = new Vector<File>();
    	for (int i=0; i<filesArray.length;i++)
    	{
    		if (ImageViewer.isImage(filesArray[i]))
    			files.add(filesArray[i]);
    	}
		
    	ImageViewer viewer = new ImageViewer();    	
    	viewer.showImages(files);
    	
    /*	DecodeThread decoder = new DecodeThread();
    	decoder.setDestDir(tempDir);
    	decoder.setFiles(files);
    	decoder.setPwd(mdp);
    	boolean decoded[] = new boolean[files.size()];
    	for (int i=0; i<files.size(); i++)
    		decoded[i] = false;
    	decoder.setDecoded(decoded);
    	decoder.start();
    	
    	viewer = new ImageViewer();    	
    	viewer.showImages(tempDir, decoded);*/
    	
    /*	try 
    	{
			decoder.join();
		}
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    	
    	
	}
	
	private ArrayList<File> selectFiles(String message)
	{
		JFileChooser fc;
		if (lastDir != null)
			fc = new JFileChooser(lastDir);
		else 
			fc = new JFileChooser();
    	fc.setMultiSelectionEnabled(true);
    	int returnVal = fc.showDialog(null, message);
    	
    	File[] res = null;
        if(returnVal == JFileChooser.APPROVE_OPTION) 
        {
           res = fc.getSelectedFiles();
        }
        else return null;
        
        ArrayList<File> files = new ArrayList<File>();
        for (int i=0; i<res.length; i++)
        {
        	files.add(res[i]);
        	if (i==0)
        	{
        		lastDir = res[i].getParentFile();
        		saveUsrFile();
        	}
        }
        
        return files;
        
	}
	
	private File selectDir(String message)
	{
		JFileChooser fc;
		if (lastDir != null)
			fc = new JFileChooser(lastDir);
		else 
			fc = new JFileChooser();
    	fc.setMultiSelectionEnabled(false);
    	fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    	int returnVal = fc.showDialog(null, message);
    	
    	File res = null;
        if(returnVal == JFileChooser.APPROVE_OPTION) 
        {
           res = fc.getSelectedFile();
           lastDir = res.getParentFile();
   		   saveUsrFile();
        }
        
        return res;
        
	}
	
	private void saveUsrFile() 
	{
		try 
		{
			Tools.saveFile("siv.usr", ("lastdir="+lastDir.getPath()).replace("\\", "\\\\").getBytes());
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String args[])
	{
		new SivFrame();
	}
	
	public void setFullScreen(boolean fs){
		this.fullScreen = fs;
		if (fs){
			bounds = this.getBounds();
			this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
			this.setUndecorated(true);
			this.setVisible(true);
		}
		else
		{
			this.setBounds(bounds);;
			this.setUndecorated(false);
			this.setVisible(true);
		}
	}

}
