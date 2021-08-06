package Servlet;

import Connection.Connect;
import DAO.ProductDAO;
import Model.GioHang;
import Model.PhanLoai;
import Model.SanPham;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/cart")
public class AddToCart extends HttpServlet {
    ProductDAO dao;
    Connect connect = new Connect();
    @Override
    public void init() throws ServletException {
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
            case "add" -> insertCart(req, resp);
            case "remove" -> removeFromCart(req, resp);
            case "increase" -> quantityControl(req,resp);
            case "decrease" -> quantityControl(req,resp);
            case "removeall" -> removeAll(req,resp);
            default -> listCart(req, resp);
        }
    }

    private void insertCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        int id = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession();
        List<SanPham> list = new ArrayList<>();
        GioHang moi = null;
        try {
            list = dao.getSanPham();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        ArrayList<GioHang> gioHangs = new ArrayList<>();
        ArrayList<GioHang> hangs = (ArrayList<GioHang>) session.getAttribute("cart");
        for (SanPham sanPham : list){
            if (sanPham.getId() == id) {
                moi = new GioHang(id,sanPham.getTen(),sanPham.getGia(),sanPham.getMota(), sanPham.getHinhanh(),sanPham.getPhanloai());
                break;
            }
        }
        moi.setSoluong(1);
        if (hangs == null) {
            gioHangs.add(moi);
            session.setAttribute("cart", gioHangs);
            response.sendRedirect("/");
        } else {
            gioHangs = hangs;
            boolean exist = false;
            for (GioHang g : gioHangs) {
                if (g.getId() == id) {
                    exist = true;
                }
            }
            if (!exist) {
                gioHangs.add(moi);
                response.sendRedirect("/");
            } else {
                response.sendRedirect("/cart");
            }
        }
    }

    private void listCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        ArrayList<GioHang> gioHangs = new ArrayList<>();
        ArrayList<GioHang> hangs = (ArrayList<GioHang>) session.getAttribute("cart");
        if (hangs == null) {
            request.setAttribute("cartlist", gioHangs);
            request.setAttribute("cart", hangs);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/cart.jsp");
            dispatcher.forward(request, response);
        } else {
            gioHangs = hangs;
            request.setAttribute("cart", hangs);
            request.setAttribute("cartlist", gioHangs);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/cart.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void removeFromCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        int id = Integer.parseInt(request.getParameter("id"));
        ArrayList<GioHang> gioHangs = (ArrayList<GioHang>) request.getSession().getAttribute("cart");
        if (gioHangs != null) {
            for (GioHang gioHang : gioHangs) {
                if (gioHang.getId() == id) {
                    gioHangs.remove(gioHangs.indexOf(gioHang));
                    break;
                }
            }
        }
        response.sendRedirect("/cart.jsp");
    }
    private void removeAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        ArrayList<GioHang> gioHangs = (ArrayList<GioHang>) request.getSession().getAttribute("cart");
        if (gioHangs != null) {
            gioHangs.removeAll(gioHangs);
        }
        response.sendRedirect("/cart.jsp");
    }

    private void quantityControl(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        String action = request.getParameter("action");
        ArrayList<GioHang> gioHangs = (ArrayList<GioHang>) request.getSession().getAttribute("cart");
        int id = Integer.parseInt(request.getParameter("id"));
        switch (action){
            case "increase" -> {
                for(GioHang gioHang : gioHangs){
                    if(gioHang.getId() == id){
                        int quantity = gioHang.getSoluong();
                        quantity++;
                        gioHang.setSoluong(quantity);
                        response.sendRedirect("/cart");
                    }
                }
            }
            case "decrease" -> {
                for(GioHang gioHang : gioHangs){
                    if(gioHang.getId() == id){
                        int quantity = gioHang.getSoluong();
                        quantity--;
                        gioHang.setSoluong(quantity);
                        response.sendRedirect("/cart");
                    }
                }
            }
        }
    }
}
