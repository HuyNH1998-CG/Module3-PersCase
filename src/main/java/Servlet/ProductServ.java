package Servlet;

import Connection.Connect;
import DAO.ProductDAO;
import Model.GioHang;
import Model.KhachHang;
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

@WebServlet(urlPatterns = {"", "/adminhome"})
public class ProductServ extends HttpServlet {
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
            case "createCat" -> showCreateCat(req, resp);
            case "createPrd" -> showCreate(req, resp);
            case "editPrd" -> {
                try {
                    showEdit(req, resp);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
            case "deletePrd" -> {
                try {
                    delete(req, resp);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
            case "editCat" -> {
                try {
                    showEditCat(req, resp);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
            case "deleteCat" -> {
                try {
                    deleteCat(req, resp);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
            case "search" -> {
                try {
                    searchProduct(req, resp);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
            default -> {
                try {
                    viewProduct(req, resp);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
            case "createCat" -> {
                try {
                    createCat(req, resp);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
            case "createPrd" -> {
                try {
                    create(req, resp);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
            case "editPrd" -> {
                try {
                    edit(req, resp);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
            case "editCat" -> {
                try {
                    editCat(req, resp);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
            case "search" -> {
                try {
                    searchProduct(req, resp);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
            case "search2" -> {
                try {
                    searchProduct2(req, resp);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    private void showCreate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("utf-8");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/create.jsp");
        dispatcher.forward(request, response);
    }

    private void showCreateCat(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("utf-8");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/createCat.jsp");
        dispatcher.forward(request, response);
    }

    private void showEdit(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        request.setCharacterEncoding("utf-8");
        int id = Integer.parseInt(request.getParameter("id"));
        SanPham toedit = dao.getSanPhamById(id);
        request.setAttribute("sanpham",toedit);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/editPrd.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditCat(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        request.setCharacterEncoding("utf-8");
        int id = Integer.parseInt(request.getParameter("id"));
        PhanLoai toedit = dao.selectCatId(id);
        request.setAttribute("cat",toedit);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/editCat.jsp");
        dispatcher.forward(request, response);
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        request.setCharacterEncoding("utf-8");
        int id = Integer.parseInt(request.getParameter("id"));
        dao.deletePrd(id);
        response.sendRedirect("/");
    }

    private void deleteCat(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        request.setCharacterEncoding("utf-8");
        int id = Integer.parseInt(request.getParameter("id"));
        dao.deleteCat(id);
        response.sendRedirect("/");
    }

    private void create(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        request.setCharacterEncoding("utf-8");
        String name = request.getParameter("name");
        float price = Float.parseFloat(request.getParameter("price"));
        String desc = request.getParameter("desc");
        String image = request.getParameter("image");
        int category = Integer.parseInt(request.getParameter("category"));
        int amount = Integer.parseInt(request.getParameter("amount"));
        dao.insertPrd(name,price,desc,image,category,amount);
        response.sendRedirect("/");
    }

    private void createCat(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        request.setCharacterEncoding("utf-8");
        String name = request.getParameter("name");
        dao.insertCat(name);
        response.sendRedirect("/");
    }

    private void edit(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        request.setCharacterEncoding("utf-8");
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        float price = Float.parseFloat(request.getParameter("price"));
        String desc = request.getParameter("desc");
        String image = request.getParameter("image");
        int category = Integer.parseInt(request.getParameter("category"));
        int amount = Integer.parseInt(request.getParameter("amount"));
        dao.editPrd(id,name,price,image,desc,category,amount);
        response.sendRedirect("/");
    }

    private void editCat(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        request.setCharacterEncoding("utf-8");
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        dao.editCat(id,name);
        response.sendRedirect("/");
    }

    private void viewProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        request.setCharacterEncoding("utf-8");
        List<SanPham> list = new ArrayList<>();
        HttpSession session = request.getSession();
        list = dao.getSanPham();
        ArrayList<PhanLoai> loais = (ArrayList<PhanLoai>) dao.getCategory();
        request.setAttribute("category", loais);
        ArrayList<GioHang> gioHangs = (ArrayList<GioHang>) request.getSession().getAttribute("cart");
        if (gioHangs != null) {
            request.setAttribute("cart", gioHangs);
        }
        request.setAttribute("product", list);
        KhachHang user = (KhachHang) session.getAttribute("user");
        if (user != null) {
            if (user.getRole().equals("admin")) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/adminhome.jsp");
                dispatcher.forward(request, response);
            } else {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/home.jsp");
                dispatcher.forward(request, response);
            }
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/home.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void searchProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        request.setCharacterEncoding("utf-8");
        List<SanPham> list = new ArrayList<>();
        HttpSession session = request.getSession();
        int id = Integer.parseInt(request.getParameter("id"));
        list = dao.getSanPhamByCat(id);
        ArrayList<PhanLoai> loais = (ArrayList<PhanLoai>) dao.getCategory();
        request.setAttribute("category", loais);
        ArrayList<GioHang> gioHangs = (ArrayList<GioHang>) request.getSession().getAttribute("cart");
        if (gioHangs != null) {
            request.setAttribute("cart", gioHangs);
        }
        request.setAttribute("product", list);
        KhachHang user = (KhachHang) session.getAttribute("user");
        if (user != null) {
            if (user.getRole().equals("admin")) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/adminhome.jsp");
                dispatcher.forward(request, response);
            } else {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/home.jsp");
                dispatcher.forward(request, response);
            }
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/home.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void searchProduct2(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        request.setCharacterEncoding("utf-8");
        List<SanPham> list = new ArrayList<>();
        HttpSession session = request.getSession();
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        if(id > 0 ){
            list = dao.getSanPhamByCatName(id,name);
        } else {
            list = dao.getSanPhamByName(name);
        }
        ArrayList<PhanLoai> loais = (ArrayList<PhanLoai>) dao.getCategory();
        request.setAttribute("category", loais);
        ArrayList<GioHang> gioHangs = (ArrayList<GioHang>) request.getSession().getAttribute("cart");
        if (gioHangs != null) {
            request.setAttribute("cart", gioHangs);
        }
        request.setAttribute("product", list);
        KhachHang user = (KhachHang) session.getAttribute("user");
        if (user != null) {
            if (user.getRole().equals("admin")) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/adminhome.jsp");
                dispatcher.forward(request, response);
            } else {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/home.jsp");
                dispatcher.forward(request, response);
            }
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/home.jsp");
            dispatcher.forward(request, response);
        }
    }
}
