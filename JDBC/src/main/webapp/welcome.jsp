<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Chào mừng</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<style>
    body {
        font-family: Arial, sans-serif;
        background: #f8f8f8;
    }
    .login-container {
        width: 400px;
        margin: 60px auto;
        background: #fff;
        border: 1px solid #ddd;
        padding: 30px;
        border-radius: 5px;
        box-shadow: 0 2px 5px rgba(0,0,0,0.1);
    }
    h2 {
        text-align: center;
        margin-bottom: 25px;
        color: #444;
    }
    .welcome-message {
        text-align: center;
        margin-bottom: 20px;
        color: #333;
    }
    .btn-logout {
        width: 100%;
        padding: 10px;
        border: none;
        background: #e74c3c;
        color: white;
        font-size: 16px;
        cursor: pointer;
        border-radius: 3px;
    }
    .btn-logout:hover {
        background: #c0392b;
    }
</style>
</head>
<body>
    <div class="login-container">
        <h2>Chào mừng</h2>
        <c:if test="${sessionScope.account != null}">
            <div class="welcome-message">
                <h3>Bạn đã đăng nhập với tài khoản: ${sessionScope.account.userName}</h3>
            </div>
            <form action="${pageContext.request.contextPath}/logout" method="get">
                <button type="submit" class="btn-logout">Đăng xuất</button>
            </form>
        </c:if>
        <c:if test="${sessionScope.username != null}">
            <div class="welcome-message">
                <h3>Bạn đã đăng nhập (cookie) với tài khoản: ${sessionScope.username}</h3>
            </div>
            <form action="${pageContext.request.contextPath}/logout" method="get">
                <button type="submit" class="btn-logout">Đăng xuất</button>
            </form>
        </c:if>
    </div>
</body>
</html>