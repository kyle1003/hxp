package cn.hurry.util;

import org.springframework.web.multipart.MultipartFile;

/**
 * 获取上传文件后缀名
 * 
 * @author ZengQingWei
 * 
 */
public class FileSuffix {
	public static String getFileSuffix(MultipartFile filePath) {
		String imageFileName = filePath.getOriginalFilename();
		return imageFileName.substring(imageFileName.lastIndexOf(".") + 1, imageFileName.length()).toLowerCase();
	}
}
