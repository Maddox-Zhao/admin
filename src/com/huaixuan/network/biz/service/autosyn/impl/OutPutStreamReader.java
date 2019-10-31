/**
 * 
 */
package com.huaixuan.network.biz.service.autosyn.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * @author TT
 * 
 */
public class OutPutStreamReader {

	public static void main(String[] args) throws IOException {
		File fiel = new File("d:\\test.txt");
		InputStream in  = new FileInputStream(fiel);
		InputStreamReader isr =	new InputStreamReader(new FileInputStream("d:\\test.txt"),"GBK");
//		BufferedReader fr= new	BufferedReader(new InputStreamReader(in,"UTF-8"));
		
		
		OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream("d:\\c.txt"),"GBK");
		BufferedWriter bw = new  BufferedWriter(fw); 
		char[] buf = new char[1024];
		
      int len=0;
      while((len=isr.read(buf))!=-1){
    	  System.out.println(new String(buf,0,len));
    	  fw.write(buf, 0, len);
      }
      
      isr.close();
      fw.flush();
      fw.close();
	}
}
