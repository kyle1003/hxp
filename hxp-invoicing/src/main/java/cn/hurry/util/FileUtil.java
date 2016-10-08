package cn.hurry.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件工具
 * 
 * @author winkerzy
 * 
 */
public class FileUtil {

	/**
	 * 保存文件到项目同级目录
	 * 
	 * @param request
	 *            http请求
	 * @param dirName
	 *            webapps下的相对目录（不包含开头的\\）
	 * @param multipartFile
	 *            复合文件
	 * @param fileName
	 *            文件保存名称
	 * @throws Exception
	 */
	public static void saveFileInWebapps(HttpServletRequest request, String dirName, MultipartFile multipartFile, String fileName) throws Exception {
		if (multipartFile != null && multipartFile.getSize() > 0) {
			// 获取webApp目录
			String url = request.getSession().getServletContext().getRealPath("");
			// 保存目录完整根路径
			String dirPath = CharacterChanger.replace(url, CharacterChanger.replace(request.getContextPath(), "/", "\\"), "\\" + dirName);
			// 拆分完整保存路径目录
			String splitPath = CharacterChanger.replace(dirPath, "\\", "|");
			String[] dirNames = splitPath.split("\\|");
			// 拼接webapps目录
			String webappsDir = dirNames[0] + "\\" + dirNames[1] + "\\" + dirNames[2];
			// 创建webapps目录下深层迭代目录
			StringBuffer itPathDirSb = new StringBuffer();
			itPathDirSb.append(webappsDir);
			for (int i = 3; i < dirNames.length; i++) {
				itPathDirSb.append("\\");
				itPathDirSb.append(dirNames[i]);
				if (!new File(itPathDirSb.toString()).isDirectory()) {
					new File(itPathDirSb.toString()).mkdir();
				}
			}

			// 保存文件
			File targetFile = new File(itPathDirSb.toString(), fileName);
			multipartFile.transferTo(targetFile);
		}
	}

	/**
	 * 获取指定目录下的所有文件
	 * 
	 * @param path
	 *            目录
	 * @param allFile
	 *            是否查找子目录下的文件
	 * @return allFile为true查询包括子目录的文件否则只返回当前目录的文件,不返回所有不可读的文件
	 */
	public static List<File> getFilesByPath(String path, boolean allFile) {
		List<File> files = new ArrayList<File>();
		try {
			File file = new File(path);
			File filesArr[] = file.listFiles();// 获取当前目录的所有文件
			for (int i = 0; i < filesArr.length; i++) {
				File fileTemp = filesArr[i];
				if (fileTemp.canRead()) { // 文件可读
					if (fileTemp.isFile()) { // 是文件
						files.add(fileTemp); // 添加文件到集合
					}
					if (allFile && fileTemp.isDirectory()) { // 是文件夹并且要查询子目录
						//添加字目录下的文件到集合（使用递归）
						files.addAll(getFilesByPath(fileTemp.getAbsolutePath(), allFile));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return files;
	}

	/**
	 * 创建文件夹
	 * 
	 * @param path
	 */
	public static void createPath(String path) {
		File file = new File(path);
		if (!file.canRead()) {
			file.mkdirs();
		}
	}
}
