package com.myapp.todolist.service;

import java.util.List;

import com.myapp.todolist.dao.TaskDAO;
import com.myapp.todolist.model.Task;

/**
 * タスクに関連するビジネスロジックを提供するサービスクラス
 */
public class TaskService {
	
	private TaskDAO taskDAO;
    // コンストラクタでTaskDAOのインスタンスを受け取る
    public TaskService(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }
    
	/**
	 * タスクの全件取得
	 * 
	 * @return タスクのリスト
	 */
	public List<Task> getAllTasks() {
		return taskDAO.findAll();
	}
	
	/**
	 * タスクの一件取得
	 * 
	 * @param id タスクのID
	 * @return 該当するタスク
	 */
	public Task getTaskById(int id) {
		return taskDAO.findById(id);
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
    public void deleteTask(int id) {
        taskDAO.delete(id);
    }

}
