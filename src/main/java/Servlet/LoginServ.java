package Servlet;

import Connection.Connect;
import DAO.UserDAO;
import Model.KhachHang;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

@WebServlet (urlPatterns = "/login")
public class LoginServ extends HttpServlet {
    UserDAO dao;
    Connect connect = new Connect();
    @Override
    public void init() throws ServletException {
        dao = new UserDAO(connect.getConnection());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setCharacterEncoding("utf-8");
        String action = req.getParameter("action");
        if (action == null){
            action = "";
        }
        if (action.equals("logout")){
            HttpSession session = req.getSession();
            session.setAttribute("user",null);
            resp.sendRedirect("/");
        }
        else {
            RequestDispatcher dispatcher = req.getRequestDispatcher("/login.jsp");
            dispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("utf-8");
        login(req, resp);
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        try {
            HttpSession session = request.getSession();
            ArrayList<KhachHang> list = (ArrayList<KhachHang>) dao.login();
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            boolean loggedin = false;
            for(KhachHang K : list){
                if(K.getUsername().equals(username)){
                    if(K.getPassword().equals(password)){
                        session.setAttribute("user",K);
                        loggedin = true;
                    }
                }
            }
            if(loggedin){
                KhachHang user = (KhachHang) session.getAttribute("user");
                if(user.getRole().equals("admin")){
                    response.sendRedirect("/adminhome");
                } else {
                    response.sendRedirect("/");
                }
            } else {
                response.sendRedirect("/login");
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
