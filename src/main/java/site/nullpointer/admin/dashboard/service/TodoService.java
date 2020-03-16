package site.nullpointer.admin.dashboard.service;

import site.nullpointer.admin.dashboard.dto.TodoVO;
import site.nullpointer.admin.dashboard.entiy.TodoEntity;

import java.util.List;

public interface TodoService {
    /**
     * 获取待办事项列表
     * @return
     */
    List<TodoVO> getTodoList();

    /**
     * 更新待办事项
     */
    void saveOrUpdateTodo(TodoVO todoVO);
    /**
     * 删除待办事项
     */
    void deleteTodo(String id);
}
