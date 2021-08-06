package Servlet;

import Connection.Connect;
import DAO.ProductDAO;
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
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet (urlPatterns = "/user")
public class UserServ extends HttpServlet {
    UserDAO dao;
    Connect connect = new Connect();
    @Override
    public void init() throws ServletException {
        dao = new UserDAO(connect.getConnection());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");

        String action = req.getParameter("action");
        if(action == null){
            action = "";
        }
        switch (action){
            case "create" -> register(req, resp);
            case "edit" -> showEditInfo(req, resp);
            case "changepass" -> showChangepass(req, resp);
            case "delete" -> {
                try {
                    deleteAccount(req, resp);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
            case "manageedit"-> {
                try {
                    showManageUserInfo(req, resp);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
            default -> {
                try {
                    showUserInfo(req,resp);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String action = req.getParameter("action");
        if(action == null){
            action = "";
        }
        switch (action){
            case "edit" -> {
                try {
                    editUserInfo(req, resp);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
            case "changepass" -> {
                try {
                    changePassword(req, resp);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
            case "manageedit"-> {
                try {
                    editUserInfoAdmin(req, resp);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    private void showManageUserInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        request.setCharacterEncoding("utf-8");
        int id = Integer.parseInt(request.getParameter("id"));
        KhachHang user = dao.getAccount(id);
        request.setAttribute("users",user);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/manageuseredit.jsp");
        dispatcher.forward(request,response);
    }

    private void showChangepass(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("utf-8");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/userchangepass.jsp");
        dispatcher.forward(request,response);
    }

    private void showEditInfo(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        KhachHang user = (KhachHang) session.getAttribute("user");
        request.setAttribute("users",user);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/usereditinfo.jsp");
        dispatcher.forward(request,response);
    }

    private void showUserInfo(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ServletException {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        KhachHang user = (KhachHang) session.getAttribute("user");
        if (user != null) {
            if (user.getRole().equals("admin")) {
                ArrayList<KhachHang> khach = (ArrayList<KhachHang>) dao.login();
                request.setAttribute("users", khach);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/manageuser.jsp");
                dispatcher.forward(request, response);
            } else {
                request.setAttribute("users", user);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/userinfo.jsp");
                dispatcher.forward(request, response);
            }
        } else {
            response.sendRedirect("/login");
        }
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
        String role = request.getParameter("role");
        try {
            dao.insertUserByAdmin(username,password,name,dob,phone,email,address,role);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        response.sendRedirect("/login");
    }
    private void editUserInfo(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        KhachHang user = (KhachHang) session.getAttribute("user");
        int id = user.getId();
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        Date dob = Date.valueOf(request.getParameter("dob"));
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        dao.editUserInfo(id,name,dob,phone,email,address);
        response.sendRedirect("/user");
    }

    private void changePassword(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        KhachHang user = (KhachHang) session.getAttribute("user");
        int id = user.getId();
        String password = request.getParameter("password");
        dao.changePassword(id,password);
        response.sendRedirect("/user");
    }

    private void deleteAccount(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        KhachHang user = (KhachHang) session.getAttribute("user");
        int id = user.getId();
        dao.deleteAccount(id);
        response.sendRedirect("/user");
    }

    private void editUserInfoAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        request.setCharacterEncoding("utf-8");
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        Date dob = Date.valueOf(request.getParameter("dob"));
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String role = request.getParameter("role");
        dao.editUserInfoAdmin(id,name,dob,phone,email,address,role);
        response.sendRedirect("/user");
    }
}
