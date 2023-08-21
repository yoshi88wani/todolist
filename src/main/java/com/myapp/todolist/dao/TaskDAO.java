package com.myapp.todolist.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.myapp.todolist.model.Task;
import com.myapp.todolist.util.DatabaseUtil;

/**
 * タスクに関するデータベース操作を管理するDAOクラス
 */
public class TaskDAO {
    /**
     * タスク全件取得
     * 
     * @return tasks タスクのリスト
     */
    public List<Task> findAll() {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks";
        
        try (
                Connection conn = DatabaseUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()
             ){      
	            while (rs.next()) {
	                Task task = new Task();
	                task.setId(rs.getInt("id"));
	                task.setTitle(rs.getString("title"));
	                task.setDescription(rs.getString("description"));
	                task.setCompleted(rs.getBoolean("completed"));
	                task.setDueDate(rs.getDate("dueDate").toLocalDate());
	                tasks.add(task);
	           }
               return tasks;
            } catch (SQLException e) {
                throw new DatabaseUtil.DatabaseException("タスクの取得中にエラーが発生しました。", e);
            }
    }
    
    /**
     * タスク一件取得
     * 
     * @param id タスクID
     * @return task 該当タスク
     */
    public Task findById(int id) {
    	Task task = null;
    	String sql = "SELECT * FROM tasks WHERE id = ?";
    	
    	try (
    			Connection conn = DatabaseUtil.getConnection();
        		PreparedStatement pstmt = conn.prepareStatement(sql)
    		){
	    	    pstmt.setInt(1, id);
	    		try (ResultSet rs = pstmt.executeQuery()) {
	    			if(rs.next()) {
	    				task = new Task();
	    	    		task.setId(rs.getInt("id"));
	    	            task.setTitle(rs.getString("title"));
	    	            task.setDescription(rs.getString("description"));
	    	            task.setCompleted(rs.getBoolean("completed"));
	    	            task.setDueDate(rs.getDate("dueDate").toLocalDate());
	    			} else {
	    				throw new DatabaseUtil.DatabaseException("指定されたタスクが見つかりません。");
	    			}
	    		}
	    } catch(SQLException e) {
	    	throw new DatabaseUtil.DatabaseException("タスクの１件取得中にエラーが発生しました。", e);
	    }
    	return task;    		
    }
    
    /**
     * 新しいタスクを追加します。
     *
     * @param task 追加するタスク
     */
    public void insert(Task task) {
    	String sql = "INSERT INTO tasks(title, description, dueDate) VALUES (?, ?, ?)";
    	
    	try (
    			Connection conn = DatabaseUtil.getConnection();
    		    PreparedStatement pstmt = conn.prepareStatement(sql)
    		){
	            pstmt.setString(1, task.getTitle());
	            pstmt.setString(2, task.getDescription());
	            pstmt.setDate(3, java.sql.Date.valueOf(task.getDueDate()));
	            
	            pstmt.executeUpdate();
    		    
      	} catch (SQLException e) {
      		throw new DatabaseUtil.DatabaseException("タスクの追加中にエラーが発生しました",e);
      	}
    }
    
    /**
     * タスクを更新します。
     * 
     * @param task 更新するタスク
     */
    public void update(Task task) {
    	String sql = "UPDATE tasks SET title=?, description=?, completed=?, dueDate=? WHERE id=?";
    	
    	try (Connection conn = DatabaseUtil.getConnection();
    			PreparedStatement pstmt = conn.prepareStatement(sql)) {
    		
    		pstmt.setString(1, task.getTitle());
    		pstmt.setString(2, task.getDescription());
    		pstmt.setBoolean(3, task.isCompleted());
    		pstmt.setDate(4, java.sql.Date.valueOf(task.getDueDate()));
    		pstmt.setInt(5, task.getId());
    		
    		pstmt.executeUpdate();
    		
    	} catch (SQLException e) {
    		throw new DatabaseUtil.DatabaseException("タスクの更新中にエラーが発生しました", e);
    	}
    }
    
    /**
     * タスクを削除します。
     * 
     * @param taskId 削除するタスクのID
     */
    public void delete(int taskId) {
    	String sql = "DELETE FROM tasks WHERE id = ?";
    	
    	try (Connection conn = DatabaseUtil.getConnection();
    			PreparedStatement pstmt = conn.prepareStatement(sql)) {
    		
    		pstmt.setInt(1, taskId);
    		pstmt.executeUpdate();
    		
    	} catch (SQLException e) {
    		throw new DatabaseUtil.DatabaseException("タスクの削除中にエラーが発生しました", e);
    	}
    }
}

