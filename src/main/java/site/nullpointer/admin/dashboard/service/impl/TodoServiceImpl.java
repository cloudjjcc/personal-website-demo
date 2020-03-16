package site.nullpointer.admin.dashboard.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.nullpointer.admin.dashboard.dto.TodoVO;
import site.nullpointer.admin.dashboard.entiy.TodoEntity;
import site.nullpointer.admin.dashboard.repository.TodoRepository;
import site.nullpointer.admin.dashboard.service.TodoService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TodoServiceImpl implements TodoService {
    @Autowired
    private TodoRepository todoRepository;
    @Override
    public List<TodoVO> getTodoList() {
        List<TodoVO> todos=new ArrayList<>();
        todoRepository.findAllByOrderByCreateTimeDesc().forEach(entity->todos.add(new TodoVO(entity)));
        return todos;
    }

    @Override
    public void saveOrUpdateTodo(TodoVO todoVO) {
        todoRepository.save(todoVO.toEntity());
    }

    @Override
    public void deleteTodo(String id) {
        todoRepository.deleteById(id);
    }
}
