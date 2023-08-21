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
 * タスクの編集に関する操作をハンドルするコントローラークラス
 */
@WebServlet("/edit-task")
public class TaskEditController extends HttpServlet {
	
	private TaskService taskService;
	
	@Override
	public void init() throws ServletException {
	    TaskDAO taskDAO = new TaskDAO();
	    this.taskService = new TaskService(taskDAO);  
	}

    /**
     * HTTP GETメソッドをハンドルし、タスクを取得して編集画面に渡す。
     *
     * @param req  HTTPリクエスト
     * @param res  HTTPレスポンス
     * @throws ServletException サーブレットの実行中に例外が発生した場合
     * @throws IOException I/O例外が発生した場合
     */
    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
    	int id = Integer.parseInt(req.getParameter("id"));
    	
		Task task = taskService.getTaskById(id);
		req.setAttribute("task", task);
		req.getRequestDispatcher("/WEB-INF/views/editTask.jsp").forward(req, res);
	}

    /**
     * HTTP POSTメソッドをハンドルし、タスクの編集を反映して一覧画面を返す。
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
		
    	int id = Integer.parseInt(req.getParameter("id"));
    	
		List<String> errors = new ArrayList<>();
		
	    // タイトルの検証
	    String title = req.getParameter("title");
	    if (title == null || title.trim().isEmpty()) {
	        errors.add("タイトルは必須です。");
	    }

	    // 日付の検証
	    LocalDate dueDate = null;
	    try {
	        dueDate = LocalDate.parse(req.getParameter("dueDate"));
	    } catch (DateTimeException e) {
	        errors.add("提供された日付が正しくありません。正しい形式（YYYY-MM-DD）で入力してください。");
	    }
	    
	    // エラーメッセージがある場合、JSPにフォワードして表示
	    if (!errors.isEmpty()) {
	        req.setAttribute("errorMessages", errors);
	        req.setAttribute("task", taskService.getTaskById(id));
	        req.getRequestDispatcher("/WEB-INF/views/editTask.jsp").forward(req, res);
	        return;
	    }
	    
	    // タスクの更新
	    Task task = taskService.getTaskById(id);
	    task.setTitle(title);
	    task.setDescription(req.getParameter("description"));
	    task.setDueDate(dueDate);
	    String completedStr = req.getParameter("completed");
	    boolean completed = "true".equals(completedStr);
	    task.setCompleted(completed);
	    taskService.updateTask(task);
		
		//一覧ページへ戻る
		res.sendRedirect(req.getContextPath() + "/tasks");
		
	}

}
