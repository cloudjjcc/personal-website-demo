package site.nullpointer.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import site.nullpointer.auth.entity.SysUser;

@Repository
public interface SysUserRepository extends JpaRepository<SysUser, String> {
	/**
	 * 根据用户名查找用户
	 * 
	 * @return
	 */
	SysUser findSysUserByUsername(String username);

	/**
	 * 根据id查找用户
	 * @param userId
	 * @return
	 */
    SysUser findSysUserById(String userId);

    /**
     * 根据昵称查找用户
     * @param nickname
     * @return
     */
    SysUser findSysUserByNickname(String nickname);
}
