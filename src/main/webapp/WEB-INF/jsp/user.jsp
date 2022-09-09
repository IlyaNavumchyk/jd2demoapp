<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>User${id}</title>
</head>

<style>
    table {
        border: 5px solid red;
        border-radius: 10px;
        border-spacing: 2px 2px;
    }

    tr, td, th {
        border: 2px solid black;
    }

    th {
        background-color: yellow;
    }

    button {
        background-color: aqua;
    }
</style>

<body>

<div>
    <h1>User ${id} info</h1>
</div>

<div>
    <table>
        <tr>
            <th>User Id</th>
            <th>User Name</th>
            <th>User Surname</th>
            <th>Birth date</th>
            <th>Is Deleted</th>
            <th>Created</th>
            <th>Changed</th>
            <th>Edit</th>
            <th>Delete</th>
        </tr>
        <tr>
            <td>${user.id}</td>
            <td>${user.userName}</td>
            <td>${user.surname}</td>
            <td>${user.birth}</td>
            <td>${user.isDeleted}</td>
            <td><fmt:formatDate value="${user.creationDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <td><fmt:formatDate value="${user.modificationDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <td>
                <button>Edit</button>
            </td>
            <td>
                <button>Delete</button>
            </td>
        </tr>
    </table>
</div>
</body>
</html>
