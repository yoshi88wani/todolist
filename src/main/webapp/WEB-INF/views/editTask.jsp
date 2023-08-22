<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>タスク編集</title>
</head>
<body>

<h1>タスク編集</h1>

<!-- エラーメッセージの表示 -->
<c:if test="${not empty errorMessages}">
    <ul class="error-messages">
        <c:forEach items="${errorMessages}" var="errorMessage">
            <li style="color:red;"><c:out value="${errorMessage}" /></li>
        </c:forEach>
    </ul>
</c:if>

<!-- タスクの編集フォーム -->
<form action="${pageContext.request.contextPath}/tasks/edit" method="post">
    <label>タイトル*: <input type="text" name="title" value="${task.title}"></label><br>
    <label>説明: <textarea name="description">${task.description}</textarea></label><br>
    <label>期限: <input type="date" name="dueDate" value="${task.dueDate}"></label><br>
    <label>状態: <input type="checkbox" name="completed" value="true" ${task.completed ? "checked" : ""}></label><br>
    <input type="hidden" name="id" value="${task.id}">
    <input type="submit" value="保存">
</form>

</body>
</html>