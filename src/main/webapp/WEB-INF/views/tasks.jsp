<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>タスク一覧</title>
</head>
<body>

<h1>タスク一覧</h1>

<!-- エラーメッセージの表示 -->
<c:if test="${not empty errorMessages}">
    <ul class="error-messages">
        <c:forEach items="${errorMessages}" var="errorMessage">
            <li style="color:red;"><c:out value="${errorMessage}" /></li>
        </c:forEach>
    </ul>
</c:if>

<!-- タスクの追加フォーム -->
<form action="${pageContext.request.contextPath}/tasks" method="post">
    <label>タイトル*: <input type="text" name="title"></label><br>
    <label>説明: <textarea name="description"></textarea></label><br>
    <label>期限: <input type="date" name="dueDate" value="${inputValue}"></label><br>
    <input type="submit" value="タスクを追加">
</form>

<!-- タスクの一覧表示 -->
<table border="1">
    <tr>
        <th>ID</th>
        <th>タイトル</th>
        <th>説明</th>
        <th>期限</th>
        <th>状態</th>
        <th>操作</th>
    </tr>
    <c:forEach items="${tasks}" var="task">
        <tr>
        	<td>${task.id}</td>
            <td>${task.title}</td>
            <td>${task.description}</td>
            <td>${task.dueDate}</td>
            <td>${task.completed ? "完了" :"未了" }</td>
            <td>
            	<a href="edit-task?id=${task.id}">編集</a>
            	<a href="delete-task?id=${task.id}" onclick="return confirm('本当に削除しますか？');">削除</a>
           	</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
