package siv;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SivCoder {
	
	public static byte[] encode(String pwd, byte[] toEncode)
	{
		byte[] res = new byte[toEncode.length];
		int pwdSize = pwd.length();

		int pwdPos = 0;
		for (int i=0; i<toEncode.length; i++)
		{
		/*	if (i==4)
				res[i] = (char) encodeByte(toEncode[i], pwd, i, pwdPos, true);
			else*/
				res[i] = encodeByte(toEncode[i], pwd, i, pwdPos);
			pwdPos++;
			if (pwdPos == pwdSize)
				pwdPos = 0;
		}
		
		return res;
		
	}
	
	public static byte[] decode(String pwd, byte[] toDecode)
	{
		byte[] res = new byte[toDecode.length];
		int pwdSize = pwd.length();

		int pwdPos = 0;
		for (int i=0; i<toDecode.length; i++)
		{
			/*if (i==4)
				res[i] = (char) decodeByte(toDecode[i], pwd, i, pwdPos, true);
			else*/
				res[i] = decodeByte(toDecode[i], pwd, i, pwdPos);
			pwdPos++;
			if (pwdPos == pwdSize)
				pwdPos = 0;
		}
		
		return res;
	}
	
	private static byte decodeByte(byte b, String pwd, int i, int pwdPos)
	{
		return decodeByte(b, pwd, i, pwdPos, false);
	}
	
	private static byte decodeByte(byte b, String pwd, int i, int pwdPos, boolean trace) 
	{
		byte res;
		if (trace)
			System.out.println("Byte : ["+b+"], pwd : ["+pwd.getBytes()[pwdPos]+"], i : ["+i+"]");
		// TODO
		if ((i % 2) == 0)
		{
			if (trace)
				System.out.println("1");
			if ((i % 3) == 0)
			{
				if (trace)
					System.out.println("1.1");
				res =  rmBytes(b, pwd.getBytes()[pwdPos] + 50);
			}
			else if ((i % 4) == 0)
			{
				if (trace)
					System.out.println("1.2");
				res =  addBytes(b, pwd.getBytes()[pwdPos] + 4);
			}
			else
			{
				if ((i % 7) == 0)
				{
					if (trace)
						System.out.println("1.3");
					res =  addBytes(b, pwd.getBytes()[pwdPos] + 100);
				}
				else
				{
					if (trace)
						System.out.println("1.4");
					res =  rmBytes(b, pwd.getBytes()[pwdPos] + 17);
				}
			}
		}
		else if ((i % 3) == 0)
		{
			if (trace)
				System.out.println("2");
			if ((i % 10) == 0)
			{
				if (trace)
					System.out.println("2.1");	
				res =  rmBytes(b, pwd.getBytes()[pwdPos] + 86);
			}
			else
			{
				if (trace)
					System.out.println("2.2");
				res =  rmBytes(b, pwd.getBytes()[pwdPos] + 1);
			}
		}
		else if ((i % 5) == 0)
		{
			if (trace)
				System.out.println("3");
			if ((i % 8) == 0)
			{
				if (trace)
					System.out.println("3.1");
				if ((i % 9) == 0)
				{
					if (trace)
						System.out.println("3.1.1");
					res =  addBytes(b, pwd.getBytes()[pwdPos] + 3);
				}
				else
				{
					if (trace)
						System.out.println("3.3.2");
					res =  addBytes(b, pwd.getBytes()[pwdPos] + 200);
				}
			}
			else if ((i % 11) == 0)
			{
				if (trace)
					System.out.println("3.2");
				res =  rmBytes(b, pwd.getBytes()[pwdPos] + 39);
			}
			else
			{
				if (trace)
					System.out.println("3.3");
				res =  rmBytes(b, pwd.getBytes()[pwdPos] + 16);
			}
		}
		else if ((i % 6) == 0)
		{
			if (trace)
				System.out.println("4");
			if ((i % 13) == 0)
			{
				if (trace)
					System.out.println("4.1");
				res =  rmBytes(b, pwd.getBytes()[pwdPos] + 77);
			}
			else
			{
				if (trace)
					System.out.println("4.2");
				res =  rmBytes(b, pwd.getBytes()[pwdPos] + 150);
			}
		}
		else
		{
			if (trace)
				System.out.println("5");
			res =  rmBytes(b, pwd.getBytes()[pwdPos]);
		}
		if (trace)
			System.out.println("Res : [" + res + "]");
		return res;
	}
	
	private static byte encodeByte(byte b, String pwd, int i, int pwdPos)
	{
		return encodeByte(b, pwd, i, pwdPos, false);
	}
	
	private static byte encodeByte(byte b, String pwd, int i, int pwdPos, boolean trace) 
	{
		byte res;
		if (trace)
			System.out.println("Byte : ["+b+"], pwd : ["+pwd.getBytes()[pwdPos]+"], i : ["+i+"]");
		if ((i % 2) == 0)
		{
			if (trace)
				System.out.println("1");
			if ((i % 3) == 0)
			{
				if (trace)
					System.out.println("1.1");
				res = addBytes(b, pwd.getBytes()[pwdPos] + 50);
			}
			else if ((i % 4) == 0)
			{
				if (trace)
					System.out.println("1.2");
				res = rmBytes(b, pwd.getBytes()[pwdPos] + 4);
			}
			else
			{
				if ((i % 7) == 0)
				{
					if (trace)
						System.out.println("1.3");
					res = rmBytes(b, pwd.getBytes()[pwdPos] + 100);
				}
				else
				{
					if (trace)
						System.out.println("1.4");
					res = addBytes(b, pwd.getBytes()[pwdPos] + 17);
				}
			}
		}
		else if ((i % 3) == 0)
		{
			if (trace)
				System.out.println("2");
			if ((i % 10) == 0)
			{
				if (trace)
					System.out.println("2.1");	
				res = addBytes(b, pwd.getBytes()[pwdPos] + 86);
			}
			else
			{
				if (trace)
					System.out.println("2.2");	
				res = addBytes(b, pwd.getBytes()[pwdPos] + 1);
			}
		}
		else if ((i % 5) == 0)
		{
			if (trace)
				System.out.println("3");	
			if ((i % 8) == 0)
			{
				if (trace)
					System.out.println("3.1");	
				if ((i % 9) == 0)
				{
					if (trace)
						System.out.println("3.1.1");	
					res = rmBytes(b, pwd.getBytes()[pwdPos] + 3);
				}
				else
				{
					if (trace)
						System.out.println("3.1.2");
					res = rmBytes(b, pwd.getBytes()[pwdPos] + 200);
				}
			}
			else if ((i % 11) == 0)
			{
				if (trace)
					System.out.println("3.2");
				res = addBytes(b, pwd.getBytes()[pwdPos] + 39);
			}
			else
			{
				if (trace)
					System.out.println("3.3");
				res = addBytes(b, pwd.getBytes()[pwdPos] + 16);
			}
		}
		else if ((i % 6) == 0)
		{
			if (trace)
				System.out.println("4");
			if ((i % 13) == 0)
			{
				if (trace)
					System.out.println("4.1");
				res = addBytes(b, pwd.getBytes()[pwdPos] + 77);
			}
			else
			{
				if (trace)
					System.out.println("4.2");
				res = addBytes(b, pwd.getBytes()[pwdPos] + 150);
			}
		}
		else
		{
			if (trace)
				System.out.println("5");
			res = addBytes(b, pwd.getBytes()[pwdPos]);
		}
		
		if (trace)
			System.out.println("Res : [" + res + "]");
		return res;
	}
	
	private static int byteToUInt(byte b)
	{
		return (b & 0xff);
	}
	
	private static byte UIntToByte(int i)
	{
		if (i<=127) return (byte) i;
		else return (byte) (i - 256);
	}

	private static byte addBytes(byte a, int b)
	{
		//return ((a + b) % 255);
		int res = byteToUInt(a) + b;
		if (res > 256)
			res =  res - 256;
		return UIntToByte(res);
	}
	
	private static byte rmBytes(byte a, int b)
	{
		int res = byteToUInt(a) - b;
		if (res < 0)
			res = 256 + res;
		return UIntToByte(res);
	}
	
	/*private static byte addBytes(byte a, byte b)
	{
		//return ((a + b) % 255);
		byte res = (byte) (a + b + 128);
		if (res > 255)
			res -= 255;
		else return byteres - 128;
	}
	
	private static byte rmBytes(byte a, byte b)
	{
		byte res = (byte) (a - b);
		if (res < -128)
			return 255 + res;
		else return res;
	}*/
	
	private static void encodeFile(String password, String fileName)
	{
		try 
		{
			byte[] contents = Tools.loadFiletoBytes(new File(fileName));
			Tools.saveFile(fileName, encode(password, contents));
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public static void encodeFile(String password, String fileName, String res)
	{
		try 
		{
			ArrayList<File> splitted = FileSplitter.splitFile(new File(fileName));
			
			for (int i = 0; i< splitted.size(); i++)
			{
				byte[] contents = Tools.loadFiletoBytes(splitted.get(i));
				Tools.saveFile(splitted.get(i).getPath(), encode(password, contents));
			}
			
			FileSplitter.mergeFiles(splitted, res);
			
			
			//byte[] contents = Tools.loadFiletoBytes(new File(fileName));
			//Tools.saveFile(res, encode(password, contents));
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public static void encodeFile(String password, String fileName, File destRep)
	{
		try 
		{
			ArrayList<File> splitted = FileSplitter.splitFile(new File(fileName), destRep);
			
			for (int i = 0; i< splitted.size(); i++)
			{
				byte[] contents = Tools.loadFiletoBytes(splitted.get(i));
				Tools.saveFile(splitted.get(i).getPath(), encode(password, contents));
			}
			
			FileSplitter.mergeFiles(splitted, destRep+File.separator+new File(fileName).getName());
			
			
			//byte[] contents = Tools.loadFiletoBytes(new File(fileName));
			//Tools.saveFile(res, encode(password, contents));
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public static void decodeFile(String password, String fileName)
	{
		try 
		{
			byte[] contents = Tools.loadFiletoBytes(new File(fileName));
			Tools.saveFile(fileName, decode(password, contents));
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public static byte[] decodeFileToBytes(String password, String fileName)
	{
		byte[] contents = null;
		try 
		{
			contents = Tools.loadFiletoBytes(new File(fileName));
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return decode(password, contents);
	}
	
	public static void decodeFile(String password, String fileName, String res)
	{
		try 
		{
			ArrayList<File> splitted = FileSplitter.splitFile(new File(fileName));
			for (int i=0; i<splitted.size(); i++)
			{
				byte[] contents = Tools.loadFiletoBytes(splitted.get(i));
				Tools.saveFile(splitted.get(i).getPath(), decode(password, contents));
			}
			
			FileSplitter.mergeFiles(splitted, res);
			
			//byte[] contents = Tools.loadFiletoBytes(new File(fileName));
			//Tools.saveFile(res, decode(password, contents));
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public static void decodeFile(String password, String fileName, File destRep)
	{
		try 
		{
			ArrayList<File> splitted = FileSplitter.splitFile(new File(fileName), destRep);
			for (int i=0; i<splitted.size(); i++)
			{
				byte[] contents = Tools.loadFiletoBytes(splitted.get(i));
				Tools.saveFile(splitted.get(i).getPath(), decode(password, contents));
			}
			
			FileSplitter.mergeFiles(splitted, destRep+File.separator+new File(fileName).getName());
			
			//byte[] contents = Tools.loadFiletoBytes(new File(fileName));
			//Tools.saveFile(res, decode(password, contents));
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public static void main(String args[])
	{
		String source = "Ceci est une chaine à encoder\n elle mesure une certaine taille, bla bla bla bla\net voila !";
		String pwd = "motdepasse";
		System.out.println("Source : ["+source+"]");
		System.out.println("Password : ["+pwd+"]");
		String encoded = new String(encode(pwd, source.getBytes()));
		System.out.println("Encoded : ["+encoded+"]");
		System.out.println("Encoded[0] : ["+(int)encoded.charAt(0)+"]");
		String decoded = new String(decode(pwd, encoded.getBytes()));
		System.out.println("Decoded : ["+decoded+"]");
		
		encodeFile(pwd, "DSC01423.JPG", "res.jpg");
		System.out.println("file menage.docx encoded");
		try {
			System.in.read();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		decodeFile(pwd, "res.jpg");
	}

}
