package site.nullpointer.auth.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="sys_role")
public class SysRole implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8429761200519593553L;
	/**
	 * 
	 */
	@Id
	@GeneratedValue(generator = "uuid2" )   //指定生成器名称
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator" )  //生成器名称，uuid生成类
	private String id;//id
	private String roleName;//角色名
	private String roleDesc;//角色描述
	@ManyToMany(
			cascade={CascadeType.REFRESH},
			fetch=FetchType.LAZY)
	@JoinTable(
			name="sys_role_resource",
			joinColumns= {@JoinColumn(name="role_id")},
			inverseJoinColumns= {@JoinColumn(name="resource_id")})
	private List<SysResource> resources=new ArrayList<SysResource>() ;
	public List<SysResource> getResources() {
		return resources;
	}
	public void setResources(List<SysResource> resources) {
		this.resources = resources;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleDesc() {
		return roleDesc;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	
}
