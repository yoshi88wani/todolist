package com.myapp.todolist.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.myapp.todolist.dao.TaskDAO;
import com.myapp.todolist.model.Task;
import com.myapp.todolist.util.DatabaseUtil;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
	
	@InjectMocks
	private TaskService taskService;
	@Mock
	private TaskDAO mockTaskDAO;

    @Test
    void testGetAllTasks() {
        // Mockの準備
        Task task1 = new Task();
        Task task2 = new Task();
        when(mockTaskDAO.findAll()).thenReturn(Arrays.asList(task1, task2));

        // メソッドの呼び出し
        List<Task> result = taskService.getAllTasks();

        // 検証
        assertEquals(2, result.size());
        verify(mockTaskDAO).findAll();
    }
    @Test
    void testGetAllTasksDatabaseException() {
        // Mockの準備
        when(mockTaskDAO.findAll()).thenThrow(new DatabaseUtil.DatabaseException("タスクの取得中にエラーが発生しました。"));

        // findAllメソッドがDatabaseExceptionをスローすることを期待
        assertThrows(DatabaseUtil.DatabaseException.class, () -> {
            taskService.getAllTasks();
        });
    }
    
    @Test
    void testGetTaskById() {
    	Task task = new Task();
    	int id = 1;
    	task.setId(id);
    	when(mockTaskDAO.findById(id)).thenReturn(task);
    	
    	Task result = taskService.getTaskById(id);
    	assertEquals(id, result.getId());
    	verify(mockTaskDAO).findById(id);
    }

    @Test
    void testAddTask() {
        Task task = new Task();

        // メソッドの呼び出し
        taskService.addTask(task);

        // 検証
        verify(mockTaskDAO).insert(task);
    }
    
    @Test
    void testAddTaskDatabaseException() {
        Task task = new Task();

        // Mockの準備
        doThrow(new DatabaseUtil.DatabaseException("タスクの追加中にエラーが発生しました")).when(mockTaskDAO).insert(any(Task.class));

        // insertメソッドがDatabaseExceptionをスローすることを期待
        assertThrows(DatabaseUtil.DatabaseException.class, () -> {
            taskService.addTask(task);
        });
    }
    
    @Test
    void testUpdateTask() {
    	Task task = new Task();
    	taskService.updateTask(task);
    	verify(mockTaskDAO).update(task);
    }
    
    @Test
    void testDeleteTask() {
    	Task task = new Task();
    	int id = 1;
    	task.setId(id);
    	taskService.deleteTask(id);
    	verify(mockTaskDAO).delete(id);
    }

}
