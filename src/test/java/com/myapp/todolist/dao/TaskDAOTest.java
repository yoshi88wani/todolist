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

import com.myapp.todolist.model.Task;
import com.myapp.todolist.util.DatabaseUtil;
import com.myapp.todolist.util.DatabaseUtil.DatabaseException;

class TaskDAOTest {

	private TaskDAO taskDAO;
	
	@BeforeEach
	void setUp() throws SQLException {
		taskDAO = new TaskDAO();
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
		
		List<Task> result = taskDAO.findAll();
		assertEquals(2, result.size());		
		
		Task task1 = result.get(0);
		assertEquals(1, task1.getId());
		assertEquals("Test Task 1", task1.getTitle());
		assertEquals("Description for task 1", task1.getDescription());
		assertEquals(false, task1.isCompleted());
		assertEquals(LocalDate.parse("2023-08-30"), task1.getDueDate());

		Task task2 = result.get(1);
		assertEquals(2, task2.getId());
		assertEquals("Test Task 2", task2.getTitle());
		assertEquals("Description for task 2", task2.getDescription());
		assertEquals(true, task2.isCompleted());
		assertEquals(LocalDate.parse("2023-08-31"), task2.getDueDate());
	}
	
	@Test
	void testFindById() {
		fail("まだ実装されていません");
	}

	@Test
	void testInsert() {
		Task task = new Task("Test Task 3","Description for task 3",LocalDate.parse("2023-08-31"));
		
		taskDAO.insert(task);		
		List<Task> result = taskDAO.findAll();
		assertEquals(3, result.size());
		
		Task task3 = result.get(2);
		assertEquals(3, task3.getId());
		assertEquals("Test Task 3", task3.getTitle());
		assertEquals("Description for task 3", task3.getDescription());
		assertEquals(false, task3.isCompleted());
		assertEquals(LocalDate.parse("2023-08-31"), task3.getDueDate());
		
	}

	@Test
	void testUpdate() {
		fail("まだ実装されていません");
	}

	@Test
	void testDelete() {
		fail("まだ実装されていません");
	}
}
