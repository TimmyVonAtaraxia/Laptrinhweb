package JDBC.controllers;

import java.io.IOException;

import JDBC.service.UserService;
import JDBC.service.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@SuppressWarnings("serial")
@WebServlet("/forgot-password")
public class ForgotPasswordController extends HttpServlet {
    private UserService userService = new UserServiceImpl(); // Giả sử đã có UserService

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/forgot-password.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String email = req.getParameter("email");
        String alertMsg = "";

        if (email == null || email.isEmpty()) {
            alertMsg = "Email không được rỗng";
            req.setAttribute("alert", alertMsg);
            req.getRequestDispatcher("/forgot-password.jsp").forward(req, resp);
            return;
        }

        // Kiểm tra email tồn tại trong database
        if (userService.checkExistEmail(email)) {
            // Chuyển hướng đến reset-password.jsp với email
            req.setAttribute("email", email);
            req.getRequestDispatcher("/reset-password.jsp").forward(req, resp);
        } else {
            alertMsg = "Email không tồn tại trong hệ thống";
            req.setAttribute("alert", alertMsg);
            req.getRequestDispatcher("/forgot-password.jsp").forward(req, resp);
        }
    }
}
