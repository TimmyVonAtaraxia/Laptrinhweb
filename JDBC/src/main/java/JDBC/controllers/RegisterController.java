package JDBC.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import JDBC.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String fullname = req.getParameter("fullname");
        String alertMsg = "";

        if (username == null || username.trim().isEmpty() || email == null || email.trim().isEmpty() ||
            password == null || password.trim().isEmpty() || fullname == null || fullname.trim().isEmpty()) {
            alertMsg = "Vui lòng điền đầy đủ thông tin!";
            req.setAttribute("alert", alertMsg);
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
            return;
        }

        DBConnection dbConn = new DBConnection();
        try (Connection conn = dbConn.getConnectionW()) {
            // Kiểm tra username hoặc email đã tồn tại
            String checkSql = "SELECT COUNT(*) FROM [User] WHERE username = ? OR email = ?";
            PreparedStatement checkPs = conn.prepareStatement(checkSql);
            checkPs.setString(1, username);
            checkPs.setString(2, email);
            ResultSet rs = checkPs.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                alertMsg = "Tên đăng nhập hoặc email đã tồn tại!";
                req.setAttribute("alert", alertMsg);
                req.getRequestDispatcher("/register.jsp").forward(req, resp);
                return;
            }

            // Thêm người dùng mới
            String sql = "INSERT INTO [User] (username, password, email, fullname) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password); // Nên hash password
            ps.setString(3, email);
            ps.setString(4, fullname);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                alertMsg = "Đăng ký thành công! <a href='login'>Đăng nhập ngay</a>";
                req.setAttribute("success", alertMsg);
            } else {
                alertMsg = "Đăng ký thất bại!";
                req.setAttribute("alert", alertMsg);
            }
        } catch (SQLException e) {
            alertMsg = "Lỗi SQL: " + e.getMessage();
            System.err.println("SQL Error: " + e.getMessage());
            req.setAttribute("alert", alertMsg);
        } catch (Exception e) {
            alertMsg = "Lỗi hệ thống: " + e.getMessage();
            System.err.println("System Error: " + e.getMessage());
            req.setAttribute("alert", alertMsg);
        }

        req.getRequestDispatcher("/register.jsp").forward(req, resp);
    }
}