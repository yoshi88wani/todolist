package com.myapp.todolist.controller;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myapp.todolist.model.Task;
import com.myapp.todolist.service.TaskService;

/**
 * タスクに関する操作をハンドルするコントローラークラス。
 * タスクの一覧表示、追加などの機能を提供します。
 */
@WebServlet("/tasks")
public class TaskController extends HttpServlet {
	
	/** タスクに関連するビジネスロジックを提供するサービスクラスのインスタンス */
	private TaskService taskService = new TaskService();
	
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
     * 日付の形式が不正な場合は、エラーメッセージと共にタスクの一覧ページにリダイレクトします。
     *
     * @param req  HTTPリクエスト
     * @param res  HTTPレスポンス
     * @throws ServletException サーブレットの実行中に例外が発生した場合
     * @throws IOException I/O例外が発生した場合
     */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		//リクエストからタスクのデータを取得
		Task task = new Task();
		task.setTitle(req.getParameter("title"));
		task.setDescription(req.getParameter("description"));
		try {
			
			task.setDueDate(LocalDate.parse(req.getParameter("dueDate")));	
	
		} catch (DateTimeException e) {
			
		    // ユーザーにエラーメッセージを表示
		    req.setAttribute("errorMessage", "提供された日付が正しくありません。正しい形式（YYYY-MM-DD）で入力してください。");
		    
		    //JSPにフォワード
			req.getRequestDispatcher("/WEB-INF/views/tasks.jsp").forward(req, res);
		    return;  
		}
		
		taskService.addTask(task);
		
		//一覧ページへ戻る
		res.sendRedirect(req.getContextPath() + "/tasks");
		
	}
}
