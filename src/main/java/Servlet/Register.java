package Servlet;

import Connection.Connect;
import DAO.UserDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/register")
public class Register extends HttpServlet {
    UserDAO dao;
    Connect connect = new Connect();
    @Override
    public void init() throws ServletException {
        dao = new UserDAO(connect.getConnection());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        RequestDispatcher dispatcher = req.getRequestDispatcher("/login.jsp");
        dispatcher.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        register(req, resp);
    }

    private void register(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        Date dob = Date.valueOf(request.getParameter("dob"));
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        try {
            dao.insertUser(username,password,name,dob,phone,email,address);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        response.sendRedirect("/");
    }
}
