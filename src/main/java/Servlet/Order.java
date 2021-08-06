package Servlet;

import Connection.Connect;
import DAO.OrderDAO;
import DAO.ProductDAO;
import Model.DonHang;
import Model.GioHang;
import Model.KhachHang;
import Model.PhanLoai;

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
import java.util.Calendar;
import java.util.List;

@WebServlet(urlPatterns = {"/checkout", "/order"})
public class Order extends HttpServlet {
    OrderDAO orderDAO;
    Connect connect = new Connect();
    ProductDAO dao;

    @Override
    public void init() throws ServletException {
        orderDAO = new OrderDAO(connect.getConnection());
        dao = new ProductDAO(connect.getConnection());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        ArrayList<PhanLoai> loais = null;
        try {
            loais = (ArrayList<PhanLoai>) dao.getCategory();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        req.setAttribute("category", loais);
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "insert" -> insertOrder(req, resp);
            case "view" -> viewOrder(req, resp);
            case "delete" -> cancelOrder(req, resp);
            case "detail" -> viewDetail(req, resp);
            default -> viewCheckout(req, resp);
        }
    }

    private void viewDetail(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("utf-8");
        int id = Integer.parseInt(request.getParameter("id"));
        ArrayList<GioHang> list = (ArrayList<GioHang>) orderDAO.getdetail(id);
        int Sum = 0;
        for (GioHang G : list) {
            Sum += (G.getSoluong() * G.getGia());
        }
        request.setAttribute("total", Sum);
        request.setAttribute("orders", list);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/orderdetail.jsp");
        dispatcher.forward(request, response);
    }

    private void viewCheckout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        ArrayList<GioHang> gioHangs = (ArrayList<GioHang>) session.getAttribute("cart");
        int Sum = 0;
        for (GioHang G : gioHangs) {
            Sum += (G.getSoluong() * G.getGia());
        }
        request.setAttribute("total", Sum);
        request.setAttribute("cart", gioHangs);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/checkout.jsp");
        dispatcher.forward(request, response);
    }

    private void viewOrder(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        KhachHang user = (KhachHang) session.getAttribute("user");
        if (user != null) {
            if (user.getRole().equals("admin")) {
                List<DonHang> donHangs = orderDAO.getAllOrder();
                request.setAttribute("DonHang", donHangs);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/order.jsp");
                dispatcher.forward(request, response);
            } else {
                List<DonHang> donHangs = orderDAO.getOrder(user.getId());
                request.setAttribute("DonHang", donHangs);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/order.jsp");
                dispatcher.forward(request, response);
            }
        } else {
            response.sendRedirect("/login");
        }
    }

    private void insertOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        Calendar cal = Calendar.getInstance();
        java.util.Date now = cal.getTime();
        Date date = new Date(now.getTime());
        KhachHang user = (KhachHang) session.getAttribute("user");
        ArrayList<GioHang> gioHangs = (ArrayList<GioHang>) session.getAttribute("cart");
        if (user != null) {
            boolean insert = orderDAO.insertOrder(user.getId(), date);
            if (insert) {
                boolean insert2 = orderDAO.insertOrderDetail(gioHangs);
                if (insert2) {
                    gioHangs.removeAll(gioHangs);
                    session.setAttribute("cart", gioHangs);
                    response.sendRedirect("/checkout?action=view");
                }
            }
        } else {
            response.sendRedirect("/login.jsp");
        }
    }

    private void cancelOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        int id = Integer.parseInt(request.getParameter("id"));
        orderDAO.removeOrder(id);
        response.sendRedirect("/checkout?action=view");
    }
}
