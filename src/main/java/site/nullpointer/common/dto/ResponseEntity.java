package site.nullpointer.common.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
/**
 * <p>类路径 : com.example.demo.auth.dto.ResponseEntity
 * <p>类描述 : 标准响应实体
 * <p>类详情 : 无
 * 
 * @author  王金灿
 * @version 1.0.0
 *<p>
 *--------------------------------------------------------------<br>
 * 修改履历：<br>
 *        <li> 2018年4月5日，wangjc，创建文件；<br>
 *--------------------------------------------------------------<br>
 *</p>
 */
public class ResponseEntity implements Serializable{

	/**long serialVersionUID: TODO属性声明*/
	private static final long serialVersionUID = -8211224164884476501L;
	//请求是否成功
	private boolean success;
	//响应数据
	private Object data;
	private transient Map<String,Object> mapData;
	public ResponseEntity addKeyValuePair(String key,Object value) {
		if(mapData==null) {
			mapData=new HashMap<String, Object>();
			setData(mapData);
		}
		mapData.put(key, value);
		return this;
	}
	public ResponseEntity success(boolean success) {
		setSuccess(success);
		return this;
	}
	public ResponseEntity data(Object data) {
		if(mapData!=null) {
			addKeyValuePair("data", data);
		}else {
			setData(data);
		}
		return this;
	}
	public String toJSONString() {
		return JSON.toJSONString(this);
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
