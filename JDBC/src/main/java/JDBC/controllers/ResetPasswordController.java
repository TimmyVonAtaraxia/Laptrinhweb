package JDBC.controllers;

import java.io.IOException;

import JDBC.service.UserService;
import JDBC.service.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/reset-password")
public class ResetPasswordController extends HttpServlet {
    private UserService userService = new UserServiceImpl(); // Giả sử đã có UserService

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        if (email == null || email.isEmpty()) {
            req.setAttribute("alert", "Email không hợp lệ");
            req.getRequestDispatcher("/forgot-password.jsp").forward(req, resp);
            return;
        }
        req.setAttribute("email", email);
        req.getRequestDispatcher("/reset-password.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String email = req.getParameter("email");
        String newPassword = req.getParameter("newPassword");
        String confirmPassword = req.getParameter("confirmPassword");
        String alertMsg = "";

        if (newPassword == null || newPassword.length() < 6 || !newPassword.equals(confirmPassword)) {
            alertMsg = "Mật khẩu mới phải ít nhất 6 ký tự và khớp xác nhận";
            req.setAttribute("alert", alertMsg);
            req.setAttribute("email", email);
            req.getRequestDispatcher("/reset-password.jsp").forward(req, resp);
            return;
        }

        // Cập nhật mật khẩu vào database (giả sử dùng UserService)
        if (userService.updatePassword(email, newPassword)) {
            alertMsg = "Mật khẩu đã được cập nhật thành công! <a href='login'>Đăng nhập</a>";
            req.setAttribute("success", alertMsg);
        } else {
            alertMsg = "Lỗi cập nhật mật khẩu";
            req.setAttribute("alert", alertMsg);
        }
        req.getRequestDispatcher("/reset-password.jsp").forward(req, resp);
    }
}

