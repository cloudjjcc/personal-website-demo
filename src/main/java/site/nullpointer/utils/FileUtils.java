package site.nullpointer.utils;

import org.springframework.util.Assert;

/**
 * <p>类路径 : site.nullpointer.common.utils.FileUtils
 * <p>类描述 : 文件方法工具类
 * <p>类详情 : 无
 * 
 * @author  王金灿
 * @version 1.0.0
 *<p>
 *--------------------------------------------------------------<br>
 * 修改履历：<br>
 *        <li> 2018年5月30日，wangjc，创建文件；<br>
 *--------------------------------------------------------------<br>
 *</p>
 */
public class FileUtils {
	/**
	 * <p>功能描述 : 获取文件扩展名
	 * <p>详细说明 : 无
	 *
	 * @return 
	 * @author  王金灿
	 *<p>
	 *--------------------------------------------------------------<br>
	 * 修改履历：<br>
	 *        <li> 2018年5月30日，wangjc，创建方法；<br>
	 *--------------------------------------------------------------<br>
	 *</p>
	 */
	public static String getFileExt(String fileName) {
		Assert.hasText(fileName,"文件名不能为空！");
		return fileName.substring(fileName.lastIndexOf(".")+1);
	}
}
