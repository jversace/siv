package org.jve.siv.engine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jve.siv.gui.Tools;

public class IterativeFileMerger {
	
	ArrayList<File> files;
	int pos = 0;

	public IterativeFileMerger(ArrayList<File> files) {
		this.files = files;
	}
	
	public void reset(){
		pos = 0;
	}

	public byte[] getNext() throws IOException{
		if (pos<files.size()){
			byte[] res = Tools.loadFiletoBytes(files.get(pos));
			pos++;
			return res;
		}
		else return null;
	}

}
