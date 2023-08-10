package com.myapp.todolist.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.myapp.todolist.util.DatabaseTestUtil;
import com.myapp.todolist.util.DatabaseUtil.DatabaseException;

class TaskDAOTest {

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
