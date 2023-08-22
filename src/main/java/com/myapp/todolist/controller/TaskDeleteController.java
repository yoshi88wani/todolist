package com.myapp.todolist.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myapp.todolist.dao.TaskDAO;
import com.myapp.todolist.service.TaskService;

/**
 * タスクの削除に関する操作をハンドルするコントローラークラス
 */
@WebServlet("/tasks/delete")
public class TaskDeleteController extends HttpServlet {

	private TaskService taskService;
	
	@Override
	public void init() throws ServletException {
	    TaskDAO taskDAO = new TaskDAO();
	    this.taskService = new TaskService(taskDAO);  
	}

    /**
     * HTTP POSTメソッドをハンドルし、タスクの削除を反映して一覧画面を返す。
     * 形式が不正な場合は、エラーメッセージを表示します。
     *
     * @param req  HTTPリクエスト
     * @param res  HTTPレスポンス
     * @throws ServletException サーブレットの実行中に例外が発生した場合
     * @throws IOException I/O例外が発生した場合
     */
    @Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
    	int id = Integer.parseInt(req.getParameter("id"));
    	
    	taskService.deleteTask(id);
    	
		//一覧ページへ戻る
		res.sendRedirect(req.getContextPath() + "/tasks");
    }
}
