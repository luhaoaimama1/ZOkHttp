package com.zone.okhttp.utils;

import java.io.File;
import java.io.IOException;

public class MediaTypeUtils {
	/**
	 * According to the suffix to determine whether it is a picture file
	 * @param file
	 * @return true is picture ,false is not
	 */
	public static boolean isImage(File file) {
		String fileName = file.getName();
		int typeIndex = fileName.lastIndexOf(".");
		if (typeIndex != -1) {
			String fileType = fileName.substring(typeIndex + 1).toLowerCase();
			if (fileType != null
					&& (fileType.equals("jpg") || fileType.equals("gif")
					|| fileType.equals("png")
					|| fileType.equals("jpeg")
					|| fileType.equals("bmp")
					|| fileType.equals("wbmp")
					|| fileType.equals("ico") || fileType.equals("jpe"))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Get file type  D:\psb.jpg-----jpg  have not dot
	 * @param file
	 * @return   File suffix
	 */
	public static String getFileSuffix(File file){
		String fileName = file.getName();
		int typeIndex = fileName.lastIndexOf(".");
		if(typeIndex != -1){
			return fileName.substring(typeIndex + 1).toLowerCase();
		}else{
			return "";
		}
	}
	/**
	 * Get the file upload type
	 * 
	 * @param file
	 * @return Picture format for image/jpg, image/png and so on. Non picture is application/octet-stream
	 */
	public static  String getContentType(File file) {
		if (MediaTypeUtils.isImage(file)) {
			// Converts the value returned by formatname to lower case, by default to upper case R
			return "image/" + getFileSuffix(file).toLowerCase();
		} 
			return "application/octet-stream";
	}
}
