<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>Hello</title>
</head>

<style>
    <%--    Add css for table, rows, column here--%>
</style>

<body>
<div>
    <h1>
        <b>Hello, ${user}!</b>
    </h1>
</div>

<div>
    <h1>System Users</h1>
</div>

<div>
    <table>
        <tr>
            <td>User Id</td>
            <td>User Name</td>
            <td>User Surname</td>
            <td>Birth date</td>
            <td>Is Deleted</td>
            <td>Created</td>
            <td>Changed</td>
            <td>Edit</td>
            <td>Delete</td>
            <%--            <td>Weight</td>--%>
        </tr>
        <c:forEach var="user" items="${users}">
            <tr>
                <td>${user.id}</td>
                <td>${user.userName}</td>
                <td>${user.surname}</td>
                <td>${user.birth}</td>
                <td>${user.isDeleted}</td>
                <td><fmt:formatDate value="${user.creationDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td><fmt:formatDate value="${user.modificationDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <%--                <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${user.weight}"/></td>--%>
                <td>
                    <button>Edit</button>
                </td>
                <td>
                    <button>Delete</button>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
