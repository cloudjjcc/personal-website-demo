package site.nullpointer.www.vo;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * <p>类路径 : site.nullpointer.common.vo.CategoryVO
 * <p>类描述 : 文章分类值对象
 * <p>类详情 : 无
 * 
 * @author  王金灿
 * @version 1.0.0
 *<p>
 *--------------------------------------------------------------<br>
 * 修改履历：<br>
 *        <li> 2018年7月13日，wangjc，创建文件；<br>
 *--------------------------------------------------------------<br>
 *</p>
 */
public class CategoryVO implements Serializable{
	/**long serialVersionUID: TODO属性声明*/
	private static final long serialVersionUID = -7253656019791887748L;
	private String name;
	private BigInteger count;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigInteger getCount() {
		return count;
	}
	public void setCount(BigInteger count) {
		this.count = count;
	}
}
