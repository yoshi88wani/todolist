package com.myapp.todolist.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.myapp.todolist.model.Task;
import com.myapp.todolist.util.DatabaseUtil;
import com.myapp.todolist.util.DatabaseUtil.DatabaseException;

@ExtendWith(MockitoExtension.class)
class TaskDAOTest {

	@InjectMocks
	private TaskDAO taskDAO;
	
	@BeforeEach
	void setUp() throws SQLException {
	    try (Connection conn = DatabaseUtil.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(
	        		 new String(Files.readAllBytes(Paths.get("src/test/resources/init-test-db.sql")), StandardCharsets.UTF_8)
	        		 )){
	        pstmt.executeUpdate();
	    } catch (IOException e) {
	        throw new DatabaseException("SQLファイルの読み込み中にエラーがおきました", e);
  	    } catch (SQLException e) {
  	     	throw new DatabaseException("beforeEach:SQL実行中にエラーが発生しました",e);
  	    }
	}

	@Test
	void testFindAll() {
		//実行
		List<Task> results = taskDAO.findAll();
		
		//検証
		assertEquals(2, results.size());		
		
		Task result1 = results.get(0);
		assertEquals(1, result1.getId());
		assertEquals("Test Task 1", result1.getTitle());
		assertEquals("Description for task 1", result1.getDescription());
		assertEquals(false, result1.isCompleted());
		assertEquals(LocalDate.parse("2023-08-30"), result1.getDueDate());

		Task result2 = results.get(1);
		assertEquals(2, result2.getId());
		assertEquals("Test Task 2", result2.getTitle());
		assertEquals("Description for task 2", result2.getDescription());
		assertEquals(true, result2.isCompleted());
		assertEquals(LocalDate.parse("2023-08-31"), result2.getDueDate());
	}
	
	@Test
	void testFindById() {
		//実行
		Task result = taskDAO.findById(1);
		
		//検証
		assertEquals(1, result.getId());
		assertEquals("Test Task 1", result.getTitle());
		assertEquals("Description for task 1", result.getDescription());
		assertEquals(false, result.isCompleted());
		assertEquals(LocalDate.parse("2023-08-30"), result.getDueDate());
	}

	@Test
	void testInsert() {
		//実行
		Task task = new Task("Test Task 3","Description for task 3",LocalDate.parse("2023-08-31"));
		taskDAO.insert(task);
		
		//検証
		List<Task> results = taskDAO.findAll();
		assertEquals(3, results.size());
		
		Task result = results.get(2);
		assertEquals(3, result.getId());
		assertEquals("Test Task 3", result.getTitle());
		assertEquals("Description for task 3", result.getDescription());
		assertEquals(false, result.isCompleted());
		assertEquals(LocalDate.parse("2023-08-31"), result.getDueDate());
	}

	@Test
	void testUpdate() {
		//実行
		Task task = new Task("Test Task 3","Description for task 3",LocalDate.parse("2023-08-31"));
		task.setId(1);
		task.setCompleted(true);
		taskDAO.update(task);
		
		//検証
		Task result = taskDAO.findById(1);
		assertEquals(1, result.getId());
		assertEquals("Test Task 3", result.getTitle());
		assertEquals("Description for task 3", result.getDescription());
		assertEquals(true, result.isCompleted());
		assertEquals(LocalDate.parse("2023-08-31"), result.getDueDate());
		
	}

	@Test
	void testDelete() {
		//実行
		taskDAO.delete(1);
		
		//検証
		List<Task> result = taskDAO.findAll();
		assertEquals(1 ,result.size());
	}
}
