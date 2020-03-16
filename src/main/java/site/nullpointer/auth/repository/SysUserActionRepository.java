package site.nullpointer.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import site.nullpointer.auth.entity.SysAction;
@Repository
public interface SysUserActionRepository extends JpaRepository<SysAction,String> {

}
