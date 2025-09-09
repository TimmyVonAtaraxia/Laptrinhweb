package JDBC.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/logout")
public class LogoutController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lấy session hiện tại
        HttpSession session = req.getSession(false);
        if (session != null) {
            // Xóa session
            session.invalidate();
        }

        // Xóa cookie "remember me" nếu có
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(LoginController.COOKIE_REMEMBER)) {
                    cookie.setMaxAge(0); // Xóa cookie bằng cách đặt thời gian sống về 0
                    resp.addCookie(cookie);
                    break;
                }
            }
        }

        // Chuyển hướng về trang login
        resp.sendRedirect(req.getContextPath() + "/login");
    }
}