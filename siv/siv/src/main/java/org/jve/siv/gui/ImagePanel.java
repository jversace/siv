package org.jve.siv.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

	BufferedImage image = null;
	FileInputStream stream = null;
	
	public ImagePanel()
	{
		ImageIO.setCacheDirectory(new File("cache"));
		//ImageIO.setUseCache(true);
		System.out.println(ImageIO.getCacheDirectory().getAbsolutePath());
	}
	
	public void setImage(File img)
	{
		try 
		{
			System.out.println(img.getPath());
			if (stream == null)
			{
				stream = new FileInputStream(img);
			}
			else
			{
				stream.close();
				stream = new FileInputStream(img);
			}
			this.image = ImageIO.read(stream);
			this.setSize(image.getWidth(), image.getHeight());
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}
	
	public void releaseImage()
	{
		if (stream != null)
		{
			try 
			{
				stream.close();
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		this.image = null;
		/*try {
			ImageIO.read(new File(new String()));
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		System.gc();
		
	}
	
    public void paintComponent(Graphics g) {
    	if (image != null)
    	{
    		Graphics2D graphics2d = (Graphics2D) g;
    		graphics2d.drawImage(image, 0, 0, null);
    		super.paintComponents(g);
    	}
    }

	
}
