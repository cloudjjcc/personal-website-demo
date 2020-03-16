package site.nullpointer.auth.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name="sys_resource")
public class SysResource implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7340744400638859619L;
	@Id
	@GeneratedValue(generator = "uuid2" )   //指定生成器名称
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator" )  //生成器名称，uuid生成类
	private String id;// id
	private int resourceType;
	private String resourceName;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getResourceType() {
		return resourceType;
	}
	public void setResourceType(int resourceType) {
		this.resourceType = resourceType;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	
}
