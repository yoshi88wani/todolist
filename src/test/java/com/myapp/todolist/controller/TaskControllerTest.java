package com.myapp.todolist.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.myapp.todolist.model.Task;
import com.myapp.todolist.service.TaskService;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {
	
	@InjectMocks
	private TaskController taskController;
	@Mock
    private TaskService mockTaskService;
	@Mock
    private HttpServletRequest mockReq;
	@Mock
    private HttpServletResponse mockRes;
    @Mock
    private RequestDispatcher mockDispatcher;

    @BeforeEach
    void setUp() {
    	
    }

	@Test
	void testDoGet() throws ServletException, IOException{
		
		//準備
        List<Task> tasks = Arrays.asList(new Task(), new Task());
        when(mockTaskService.getAllTasks()).thenReturn(tasks);
        when(mockReq.getRequestDispatcher("/WEB-INF/views/tasks.jsp")).thenReturn(mockDispatcher);
        
		//実行
		taskController.doGet(mockReq, mockRes);
		
		//検証
		verify(mockReq).setAttribute("tasks", mockTaskService.getAllTasks());
		verify(mockDispatcher).forward(mockReq, mockRes);
	}

	@Test
	void testDoPostSuccess() throws ServletException, IOException{
		
		//準備
		when(mockReq.getParameter("title")).thenReturn("Test Title");
		when(mockReq.getParameter("description")).thenReturn("Test Description");
		when(mockReq.getParameter("dueDate")).thenReturn("2023-08-23");
		
		//実行
		taskController.doPost(mockReq, mockRes);
		
		//検証
		verify(mockTaskService).addTask(any(Task.class));
		verify(mockRes).sendRedirect(anyString());	
	}
	
	@Test
	void tesDoPostError() throws ServletException, IOException{
		
		//準備
        List<Task> tasks = Arrays.asList(new Task(), new Task());
        when(mockTaskService.getAllTasks()).thenReturn(tasks);
        when(mockReq.getRequestDispatcher("/WEB-INF/views/tasks.jsp")).thenReturn(mockDispatcher);
        
        when(mockReq.getParameter("title")).thenReturn("");
        when(mockReq.getParameter("dueDate")).thenReturn("2023-13-01"); 
        
		//実行
		taskController.doPost(mockReq, mockRes);
		
		//検証
		verify(mockReq).setAttribute(eq("errorMessages"),
				argThat((List<String> list) -> list.contains("タイトルは必須です。") 
						&& list.contains("提供された日付が正しくありません。正しい形式（YYYY-MM-DD）で入力してください。")));
		verify(mockDispatcher).forward(mockReq, mockRes);
	}

}
