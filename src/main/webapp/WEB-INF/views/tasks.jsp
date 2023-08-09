<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>タスク一覧</title>
</head>
<body>

<h1>タスク一覧</h1>

<!-- エラーメッセージの表示 -->
<c:if test="${not empty errorMessage}">
    <div style="color:red;">${errorMessage}</div>
</c:if>

<!-- タスクの追加フォーム -->
<form action="/tasks" method="post">
    <label>タイトル: <input type="text" name="title" /></label><br/>
    <label>説明: <textarea name="description"></textarea></label><br/>
    <label>期限: <input type="date" name="dueDate" value="${inputValue}" /></label><br/>
    <input type="submit" value="タスクを追加" />
</form>

<!-- タスクの一覧表示 -->
<table border="1">
    <tr>
        <th>ID</th>
        <th>タイトル</th>
        <th>説明</th>
        <th>期限</th>
        <th>状態</th>
    </tr>
    <c:forEach items="${tasks}" var="task">
        <tr>
        	<td>${task.id}</td>
            <td>${task.title}</td>
            <td>${task.description}</td>
            <td>${task.dueDate}</td>
            <td>${task.completed ? "完了" :"未了" }</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>