package JDBC.controllers;

import java.io.IOException;

import JDBC.service.UserService;
import JDBC.service.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = {"/reset-password"})
public class ResetPasswordController extends HttpServlet{
	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        if (token == null || token.isEmpty()) {
            req.setAttribute("alert", "Token không hợp lệ");
            req.getRequestDispatcher("/views/reset-password.jsp").forward(req, resp);
            return;
        }

        UserService service = new UserServiceImpl();
        if (!service.isTokenValid(token)) {
            req.setAttribute("alert", "Token không hợp lệ hoặc đã hết hạn");
        }
        req.setAttribute("token", token);  // Truyền token cho form
        req.getRequestDispatcher("/views/reset-password.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String token = req.getParameter("token");
        String newPassword = req.getParameter("newPassword");
        String confirmPassword = req.getParameter("confirmPassword");
        String otp = req.getParameter("otp");  // Thêm trường OTP từ form
        String alertMsg = "";

        if (newPassword == null || newPassword.length() < 6 || !newPassword.equals(confirmPassword)) {
            alertMsg = "Mật khẩu mới phải ít nhất 6 ký tự và khớp xác nhận";
            req.setAttribute("alert", alertMsg);
            req.setAttribute("token", token);
            req.getRequestDispatcher("/views/reset-password.jsp").forward(req, resp);
            return;
        }

        UserService service = new UserServiceImpl();
        if (service.resetPassword(token, newPassword, otp)) {
            alertMsg = "Mật khẩu đã được reset thành công! <a href='/login'>Đăng nhập</a>";
            req.setAttribute("success", alertMsg);
            // Xóa token khỏi session nếu cần
        } else {
            alertMsg = "Lỗi reset mật khẩu. Token hoặc OTP không đúng";
            req.setAttribute("alert", alertMsg);
        }
        req.getRequestDispatcher("/views/reset-password.jsp").forward(req, resp);
    }
}
