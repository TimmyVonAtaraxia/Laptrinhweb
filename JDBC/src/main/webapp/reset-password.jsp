<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Reset Mật Khẩu</title>
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
    .input-group {
        display: flex;
        margin-bottom: 15px;
        border: 1px solid #ccc;
        border-radius: 3px;
        overflow: hidden;
    }
    .input-group span {
        background: #f2f2f2;
        padding: 10px;
        border-right: 1px solid #ccc;
        color: #555;
        min-width: 40px;
        text-align: center;
    }
    .input-group input {
        border: none;
        padding: 10px;
        flex: 1;
    }
    .input-group input:focus {
        outline: none;
    }
    .form-options {
        display: flex;
        justify-content: space-between;
        font-size: 14px;
        margin-bottom: 15px;
    }
    .form-options label {
        color: #333;
    }
    .form-options a {
        color: #007bff;
        text-decoration: none;
    }
    .form-options a:hover {
        text-decoration: underline;
    }
    .btn-login {
        width: 100%;
        padding: 10px;
        border: none;
        background: #0099e6;
        color: white;
        font-size: 16px;
        cursor: pointer;
        border-radius: 3px;
    }
    .btn-login:hover {
        background: #007acc;
    }
    .register-link {
        text-align: center;
        margin-top: 20px;
        font-size: 14px;
        color: #555;
    }
    .register-link a {
        color: #007bff;
        text-decoration: none;
    }
    .register-link a:hover {
        text-decoration: underline;
    }
    .alert {
        padding: 10px;
        color: white;
        background: #e74c3c;
        text-align: center;
        margin-bottom: 15px;
        border-radius: 3px;
    }
    .success {
        padding: 10px;
        color: white;
        background: #2ecc71;
        text-align: center;
        margin-bottom: 15px;
        border-radius: 3px;
    }
</style>
</head>
<body>
    <div class="login-container">
        <form action="reset-password" method="post">
            <h2>Reset Mật Khẩu</h2>

            <c:if test="${alert != null}">
                <div class="alert">${alert}</div>
            </c:if>
            <c:if test="${success != null}">
                <div class="success">${success}</div>
            </c:if>

            <c:if test="${token != null}">
                <input type="hidden" name="token" value="${token}">

                <!-- OTP -->
                <div class="input-group">
                    <span><i class="fa fa-lock"></i></span>
                    <input type="text" name="otp" placeholder="Nhập mã OTP" required>
                </div>

                <!-- New Password -->
                <div class="input-group">
                    <span><i class="fa fa-lock"></i></span>
                    <input type="password" name="newPassword" placeholder="Mật Khẩu Mới" required minlength="6">
                </div>

                <!-- Confirm Password -->
                <div class="input-group">
                    <span><i class="fa fa-lock"></i></span>
                    <input type="password" name="confirmPassword" placeholder="Xác Nhận Mật Khẩu" required>
                </div>

                <button type="submit" class="btn-login">Reset Mật Khẩu</button>
            </c:if>

            <div class="register-link">
                Quay lại <a href="login">Đăng Nhập</a>
            </div>
        </form>
    </div>
</body>
</html>