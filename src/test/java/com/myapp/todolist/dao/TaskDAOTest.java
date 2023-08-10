package com.myapp.todolist.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.myapp.todolist.model.Task;
import com.myapp.todolist.util.DatabaseTestUtil;
import com.myapp.todolist.util.DatabaseUtil.DatabaseException;

class TaskDAOTest {

	private TaskDAO  taskDAO;
	
	@BeforeEach
	void setUp() throws SQLException {
	    try (Connection conn = DatabaseTestUtil.getTestConnection();
	         PreparedStatement pstmt = conn.prepareStatement(
	        		 new String(Files.readAllBytes(Paths.get("src/test/resources/init-test-db.sql")), StandardCharsets.UTF_8)
	        		 )){
	        pstmt.executeUpdate();
	    } catch (IOException e) {
	        throw new DatabaseException("SQLファイルの読み込み中にエラーがおきました", e);
  	    } catch (SQLException e) {
  	     	throw new DatabaseException("SQL実行中にエラーが発生しました",e);
  	    }
	}
	
	@AfterEach
	void tearDown() {
		
	}

	@Test
	void testFindAll() {
		
		List<Task> result = taskDAO.findAll();
		
		assertEquals(2, result.size());
		assertArrayEquals({1, "Test Task 1", "Description for task 1", false, "2023-08-30"},result[0]);
		
	}

	@Test
	void testInsert() {
		fail("まだ実装されていません");
	}
/*
	@Test
	void testUpdate() {
		fail("まだ実装されていません");
	}

	@Test
	void testDelete() {
		fail("まだ実装されていません");
	}
*/
}
