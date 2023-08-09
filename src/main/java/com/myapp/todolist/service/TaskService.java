package com.myapp.todolist.service;

import java.util.List;

import com.myapp.todolist.dao.TaskDAO;
import com.myapp.todolist.model.Task;

public class TaskService {
	
	private TaskDAO taskDAO = new TaskDAO();
	
	/**
	 * タスクの全件取得
	 * 
	 * @return タスクのリスト
	 */
	public List<Task> getAllTasks() {
		return taskDAO.findAll();
	}
	
	/**
     * タスクを追加します。
     * 
     * @param task 追加するタスク
     */
    public void addTask(Task task) {
        taskDAO.insert(task);
    }

    /**
     * タスクを更新します。
     * 
     * @param task 更新するタスク
     */
    public void updateTask(Task task) {
        taskDAO.update(task);
    }

    /**
     * タスクを削除します。
     * 
     * @param taskId 削除するタスクのID
     */
    public void deleteTask(int taskId) {
        taskDAO.delete(taskId);
    }

}
