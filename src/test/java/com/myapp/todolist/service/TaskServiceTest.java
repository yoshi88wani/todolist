package com.myapp.todolist.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.myapp.todolist.dao.TaskDAO;
import com.myapp.todolist.model.Task;
import com.myapp.todolist.util.DatabaseUtil;

class TaskServiceTest {
	
	private TaskService taskService;
	private TaskDAO mockTaskDAO;
	
	@BeforeEach
	void setUp() {
		mockTaskDAO = Mockito.mock(TaskDAO.class);
		taskService = new TaskService(mockTaskDAO);
	}

    @Test
    public void testGetAllTasks() {
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
    public void testGetAllTasksDatabaseException() {
        // Mockの準備
        when(mockTaskDAO.findAll()).thenThrow(new DatabaseUtil.DatabaseException("タスクの取得中にエラーが発生しました。"));

        // findAllメソッドがDatabaseExceptionをスローすることを期待
        assertThrows(DatabaseUtil.DatabaseException.class, () -> {
            taskService.getAllTasks();
        });
    }

    @Test
    public void testAddTask() {
        Task task = new Task();

        // メソッドの呼び出し
        taskService.addTask(task);

        // 検証
        verify(mockTaskDAO).insert(task);
    }
    
    @Test
    public void testAddTaskDatabaseException() {
        Task task = new Task();

        // Mockの準備
        doThrow(new DatabaseUtil.DatabaseException("タスクの追加中にエラーが発生しました")).when(mockTaskDAO).insert(any(Task.class));

        // insertメソッドがDatabaseExceptionをスローすることを期待
        assertThrows(DatabaseUtil.DatabaseException.class, () -> {
            taskService.addTask(task);
        });
    }

}
