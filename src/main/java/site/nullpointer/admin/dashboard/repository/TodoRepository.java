package site.nullpointer.admin.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.nullpointer.admin.dashboard.entiy.TodoEntity;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity,String> {
    List<TodoEntity> findAllByOrderByCreateTimeDesc();
}
