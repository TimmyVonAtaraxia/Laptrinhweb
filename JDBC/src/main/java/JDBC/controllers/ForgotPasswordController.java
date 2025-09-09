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
@WebServlet(urlPatterns = {"/forgot-password"})
public class ForgotPasswordController extends HttpServlet{
	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("account") != null) {
            resp.sendRedirect(req.getContextPath() + "/waiting");
            return;
        }
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

        UserService service = new UserServiceImpl();
        String otp = service.requestPasswordReset(email);
        if (otp != null) {
            req.setAttribute("otp", otp);  // Hiển thị OTP trên form
            req.setAttribute("success", "Vui lòng nhập mã OTP sau để reset mật khẩu.");
            req.setAttribute("email", email);  // Lưu email để dùng lại
        } else {
            alertMsg = "Email không tồn tại hoặc lỗi hệ thống";
            req.setAttribute("alert", alertMsg);
        }
        req.getRequestDispatcher("/forgot-password.jsp").forward(req, resp);
    }
}
