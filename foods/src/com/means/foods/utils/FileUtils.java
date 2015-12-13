package com.means.foods.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.os.Environment;
import android.util.Xml.Encoding;
/**
 * �ļ�����
 * @author Administrator
 *
 */
public class FileUtils {

	private String SDPATH;
	
	Context context;

	public String getSDPATH() {
		return SDPATH;
	}
	public FileUtils(Context context) {
		//�õ���ǰ�ⲿ�洢�豸��Ŀ¼
		// /SDCARD
	    this.context = context;
//		SDPATH = Environment.getDataDirectory() + "/";
	    SDPATH = context.getFilesDir().getPath()+"/";
	}
	/**
	 * ��SD���ϴ����ļ�
	 * 
	 * @throws IOException
	 */
	public File creatSDFile(String fileName) throws IOException {
		File file = new File(SDPATH + fileName);
		file.createNewFile();
		return file;
	}
	
	/**
	 * ��SD���ϴ���Ŀ¼
	 * 
	 * @param dirName
	 */
	public File creatSDDir(String dirName) {
		File dir = new File(SDPATH,"hfsj");
		dir.mkdirs();
		return dir;
	}

	/**
	 * �ж�SD���ϵ��ļ����Ƿ����?
	 */
	public boolean isFileExist(String fileName){
		File file = new File(SDPATH + fileName);
		return file.exists();
	}
	
	/**
	 * ��һ��InputStream��������д�뵽SD����
	 */
	public int write2SDFromInput(String path,String fileName,InputStream input){
		File file = null;
		OutputStream output = null;
		try{
//			creatSDDir(path);
//			file = creatSDFile(path + fileName);
			output = context.openFileOutput(fileName, Context.MODE_WORLD_READABLE+Context.MODE_WORLD_WRITEABLE);
//			output = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
		//	byte buffer [] = new byte[100 * 1024];
			int len = 0;
			while ((len = input.read(buffer)) != -1) {
				output.write(buffer, 0, len);
			}
//			while((input.read(buffer)) != -1){
//				output.write(buffer);
//			}
			output.flush();
			return 1;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				output.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		return 0;
	}
}