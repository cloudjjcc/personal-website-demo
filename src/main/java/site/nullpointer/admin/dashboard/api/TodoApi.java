package site.nullpointer.admin.dashboard.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.nullpointer.admin.base.api.AdminBaseApi;
import site.nullpointer.admin.dashboard.dto.TodoVO;
import site.nullpointer.admin.dashboard.service.TodoService;
import site.nullpointer.common.dto.ResponseEntity;

@RestController
public class TodoApi extends AdminBaseApi {
    @Autowired
    private TodoService todoService;

    @GetMapping("/todos")
    public ResponseEntity getTodoList() {
        return new ResponseEntity().success(true).data(todoService.getTodoList());
    }

    @PostMapping("/todo")
    public ResponseEntity saveOrUpdateTodo(@RequestBody TodoVO todoVO) {
        todoService.saveOrUpdateTodo(todoVO);
        return new ResponseEntity().success(true);
    }

    @DeleteMapping("/todo/{id}")
    public ResponseEntity deleteTodo(@PathVariable String id) {
        todoService.deleteTodo(id);
        return new ResponseEntity().success(true);
    }
}
