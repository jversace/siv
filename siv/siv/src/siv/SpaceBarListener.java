package siv;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SpaceBarListener implements KeyListener{
	
	ImageViewer viewer = null;
	
	public SpaceBarListener(ImageViewer viewer)
	{
		this.viewer = viewer;
	}

	@Override
	public void keyTyped(KeyEvent e) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		if (e.getKeyCode() == KeyEvent.VK_SPACE)
			viewer.next();
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
			viewer.exit();
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		/*if (e.getKeyCode() == KeyEvent.VK_SPACE)
			next = true;*/
	}
	

}
