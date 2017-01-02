package siv;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;

public class ImageViewer extends JFrame{
	
	private ImagePanel panel = null;
	private JFrame frame = null;
	
	private ArrayList<File> images = null;
	private int index = 0;
	
	private File tempDir = null;
	private boolean decoded[] = null;
	
	public ImageViewer()
	{
    	panel = new ImagePanel();
    	add(panel);
    	addKeyListener(new SpaceBarListener(this));
    	setVisible(true);	
    	//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/*WindowListener l = new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				System.out.println("Releasing image");
		    	panel.releaseImage();
			}
		};*/
	}
	
	public void releaseImages()
	{
		panel.releaseImage();
	}
    
    private void showImage(File img)
    {
    	panel.setImage(img);
    	//setPreferredSize(new Dimension(panel.getWidth()+(int)(panel.getWidth()*0.05), panel.getHeight()+(int)(panel.getWidth()*0.05)));
    	setPreferredSize(new Dimension(panel.getWidth()+20, panel.getHeight()+40));
    	pack();
    }
    
 /*   public void next()
    {
    	if (images != null)
    	{
    		showImage(images.get(index));
    		index++;
    		if (index == images.size())
    			index = 0;
    	}
    }*/
    
 /*   public void showImages(ArrayList<File> images)
    {
    	this.images = images;
    	next();
    }*/
    
    // testing
    public static void main(String args[])
    {
    	ImageViewer viewer = new ImageViewer();
    	
    	File dirImg = new File("C:\\Users\\Public\\Pictures\\Sample Pictures");
    	File[] filesArray = dirImg.listFiles();
    	
    	ArrayList<File> files = new ArrayList<File>();
    	for (int i=0; i<filesArray.length;i++)
    	{
    		if (isImage(filesArray[i]))
    			files.add(filesArray[i]);
    	}

    	
    	//viewer.showImages(files);
    	
    /*	viewer.showImage(new File("C:\\Users\\Public\\Pictures\\Sample Pictures\\Koala.jpg"));
    	try {
			System.in.read();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	viewer.showImage(new File("C:\\Users\\Public\\Pictures\\Sample Pictures\\Penguins.jpg"));  	*/
    }

	static boolean isImage(File file) 
	{
		return file.getName().toLowerCase().endsWith("jpg")
			|| file.getName().toLowerCase().endsWith("jpeg")
			|| file.getName().toLowerCase().endsWith("gif")
			|| file.getName().toLowerCase().endsWith("pcx")
			|| file.getName().toLowerCase().endsWith("bmp")
			|| file.getName().toLowerCase().endsWith("tif")
			|| file.getName().toLowerCase().endsWith("tiff");
	}

	public void exit() 
	{
		this.dispose();
	}

/*	public void showImages(ArrayList<File> files, boolean[] decoded) 
	{
    	this.images = files;
    	next(decoded);
	}

	private void next(boolean[] decoded) 
	{
    	if (images != null)
    	{
    		while (!decoded[index])
    		{
    			index++;
        		if (index == images.size())
        			index = 0;
    		}
    		showImage(images.get(index));
    		index++;
    		if (index == images.size())
    			index = 0;
    	}
	}*/
	
	private boolean loadImages()
	{
		this.images = new ArrayList<File>();
		File files[] = tempDir.listFiles();
		
		if (files == null)
		{
			return false;
		}
		
		if (files.length == 0)
			return false;
		
		for (int i=0; i<files.length; i++)
			if ((isImage(files[i])) && decoded[i])
				images.add(files[i]);
		
		if (images.size() == 0)
			return false;
		
		return true;
	}
	
	//private void next(File tempDir, boolean[] decoded) 
	public void next()
	{
	//	System.out.println("1 - next");
		boolean imagesExist = loadImages();
	//	System.out.println("2 - next : " + imagesExist);
		
		while (!imagesExist)
			imagesExist = loadImages();
		
	//	System.out.println("3 - next : " + imagesExist);
			//next(tempDir, decoded); 
			//next();
		//while (images.size() == 0){}
			//next(tempDir, decoded);
			//next();
			//return;
		
		
		if (images.size() > index)
		{
	//		System.out.println("4a - next : size=" + images.size() + " index="+index);
			showImage(images.get(index));
			index++;
		}
		else
		{
	//		System.out.println("4b - next : size=" + images.size() + " index="+index);
			index = 0;
			showImage(images.get(index));
			index++;
		}
		
	//	System.out.println("5 - next : fin");
	}

	public void showImages(File tempDir, boolean[] decoded) 
	{
		this.decoded = decoded;
		this.tempDir = tempDir;
		/*this.images = new ArrayList<File>();
		File files[] = tempDir.listFiles();
		for (int i=0; i<files.length; i++)
			if ((isImage(files[i])) && decoded[i])
				images.add(files[i]);*/
		//next(tempDir, decoded);
		next();
	}


}
