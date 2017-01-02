package org.jve.siv.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SpaceBarListener implements KeyListener{
	
	ImageViewer viewer = null;
	
	public SpaceBarListener(ImageViewer viewer)
	{
		this.viewer = viewer;
	}

	public void keyTyped(KeyEvent e) 
	{
		// TODO Auto-generated method stub
		
	}

	public void keyPressed(KeyEvent e) 
	{
		if (e.getKeyCode() == KeyEvent.VK_SPACE)
			viewer.next();
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
			viewer.exit();
	}

	public void keyReleased(KeyEvent e) 
	{
		/*if (e.getKeyCode() == KeyEvent.VK_SPACE)
			next = true;*/
	}
	

}
