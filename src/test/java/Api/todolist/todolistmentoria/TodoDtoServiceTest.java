package Api.todolist.todolistmentoria;

import Api.todolist.todolistmentoria.dto.TodoDto;
import Api.todolist.todolistmentoria.todorepository.TodoRepopsitory;
import Api.todolist.todolistmentoria.todoservice.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TodoDtoServiceTest {

    @InjectMocks
    private TodoService todoService;

    @Mock
    private TodoRepopsitory todoRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateTodo() {
        TodoDto todo = new TodoDto();
        Mockito.when(todoRepository.save(todo)).thenReturn(todo);
        TodoDto createdTodo = todoService.createTodo(todo);

        assertEquals(todo, createdTodo);
    }

    @Test
    public void testListAllTodo() {
        List<TodoDto> todos = new ArrayList<>();
        Mockito.when(todoRepository.findAll()).thenReturn(todos);
        List<TodoDto> result = todoService.listAllTodo();
        assertEquals(todos, result);
    }

    @Test
    public void testFindTodoById() {
        Long todoId = 1L;
        TodoDto todo = new TodoDto();
        todo.setId(todoId);

        Mockito.when(todoRepository.findById(todoId)).thenReturn(Optional.of(todo));

        ResponseEntity<TodoDto> result = todoService.findTodoById(todoId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(todo, result.getBody());
    }

    @Test
    public void testUpdateTodoById() {
        Long todoId = 1L;

        TodoDto todoToUpdate = new TodoDto();
        todoToUpdate.setId(todoId);

        Mockito.when(todoRepository.findById(todoId)).thenReturn(Optional.of(todoToUpdate));

        Mockito.when(todoRepository.save(todoToUpdate)).thenReturn(todoToUpdate);

        ResponseEntity<TodoDto> result = todoService.updateTodoById(todoToUpdate, todoId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(todoToUpdate, result.getBody());
    }

    @Test
    public void testDeleteById() {
        Long todoId = 1L;

        Mockito.when(todoRepository.findById(todoId)).thenReturn(Optional.of(new TodoDto()));

        Mockito.doNothing().when(todoRepository).deleteById(todoId);

        ResponseEntity<Object> result = todoService.deleteById(todoId);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }
}
