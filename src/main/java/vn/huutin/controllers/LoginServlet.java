package vn.huutin.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Chuyển hướng sang trang login.html
        response.sendRedirect(request.getContextPath() + "/login.html");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");

        // lấy dữ liệu từ form
        String user = request.getParameter("username");
        String pass = request.getParameter("password");

        if ("trung".equals(user) && "123".equals(pass)) {
            // tạo cookie
            Cookie cookie = new Cookie("username", user);
            cookie.setMaxAge(30); // sống 30s
            response.addCookie(cookie);

            // chuyển sang HelloServlet
            response.sendRedirect(request.getContextPath() + "/hello");
        } else {
            // nếu sai thì quay lại login.html
            response.sendRedirect(request.getContextPath() + "/login.html");
        }
    }
}
