package cn.hurry.util;

import java.io.File;
import java.util.Date;
import java.util.Random;

import com.oreilly.servlet.multipart.FileRenamePolicy;

/**
 * 文件命名策略
 * 
 * @author ZhouHao
 */
public class RenamePolicyCos implements FileRenamePolicy {

	public File rename(File uploadFile) {
		String newName = getNewFileName(uploadFile.getName());
		File renameFile = new File(uploadFile.getParent(), newName);
		return renameFile;
	}

	private String getNewFileName(String fileName) {
		StringBuffer newName = new StringBuffer();
		if (null != fileName && !"".equals(fileName)) {
			String type = "";
			String name = "";
			if (fileName.indexOf(".") != -1) {
				type = fileName.substring(fileName.indexOf("."));
				name = fileName.substring(0, fileName.indexOf("."));
			}else{
				name = fileName;
			}
			name.replace(",", "，");
			//newName.append(name);
			newName.append(getSuffix());
			newName.append(type);
		}
		return newName.toString();
	}
	
	
	private String getSuffix(){
		StringBuffer suffix = new StringBuffer();
		String now = new Date().getTime()+"";
		suffix.append(now);
		//suffix.append("_");
		Random random = new Random();
		String randomValue = String.valueOf(random.nextInt(1000));
		suffix.append(randomValue);
		return suffix.toString();
	}

}