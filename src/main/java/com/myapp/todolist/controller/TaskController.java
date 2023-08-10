package com.myapp.todolist.controller;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myapp.todolist.dao.TaskDAO;
import com.myapp.todolist.model.Task;
import com.myapp.todolist.service.TaskService;

/**
 * タスクに関する操作をハンドルするコントローラークラス。
 * タスクの一覧表示、追加などの機能を提供します。
 */
@WebServlet("/tasks")
public class TaskController extends HttpServlet {
	
	private TaskService taskService;
	
	@Override
	public void init() throws ServletException {
	    TaskDAO taskDAO = new TaskDAO();
	    this.taskService = new TaskService(taskDAO);  
	}
	
    /**
     * HTTP GETメソッドをハンドルし、タスクの一覧を表示します。
     *
     * @param req  HTTPリクエスト
     * @param res  HTTPレスポンス
     * @throws ServletException サーブレットの実行中に例外が発生した場合
     * @throws IOException I/O例外が発生した場合
     */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//すべてのタスクを取得
		req.setAttribute("tasks", taskService.getAllTasks());
		//JSPにフォワード
		req.getRequestDispatcher("/WEB-INF/views/tasks.jsp").forward(req, res);
	}
	
    /**
     * HTTP POSTメソッドをハンドルし、新しいタスクを追加します。
     * 形式が不正な場合は、エラーメッセージを表示します。
     *
     * @param req  HTTPリクエスト
     * @param res  HTTPレスポンス
     * @throws ServletException サーブレットの実行中に例外が発生した場合
     * @throws IOException I/O例外が発生した場合
     */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		
		List<String> errors = new ArrayList<>();
		
	    // タイトルの検証
	    String title = req.getParameter("title");
	    if (title == null || title.trim().isEmpty()) {
	        errors.add("タイトルは必須です。");
	    }

	    // 日付の検証
	    try {
	        LocalDate.parse(req.getParameter("dueDate"));
	    } catch (DateTimeException e) {
	        errors.add("提供された日付が正しくありません。正しい形式（YYYY-MM-DD）で入力してください。");
	    }
	    
	    // エラーメッセージがある場合、JSPにフォワードして表示
	    if (!errors.isEmpty()) {
	        req.setAttribute("errorMessages", errors);
	        req.setAttribute("tasks", taskService.getAllTasks());
	        req.getRequestDispatcher("/WEB-INF/views/tasks.jsp").forward(req, res);
	        return;
	    }
	    
	    // タスクの追加
	    Task task = new Task();
	    task.setTitle(title);
	    task.setDescription(req.getParameter("description"));
	    task.setDueDate(LocalDate.parse(req.getParameter("dueDate")));
	    taskService.addTask(task);
		
		//一覧ページへ戻る
		res.sendRedirect(req.getContextPath() + "/tasks");
		
	}
}
