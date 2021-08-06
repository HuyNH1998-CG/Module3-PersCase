package DAO;


import Model.PhanLoai;
import Model.SanPham;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private Connection connection;
    private String query;
    private PreparedStatement statement;
    private ResultSet rs;

    public ProductDAO(Connection connection) {
        this.connection = connection;
    }

    public void insertPrd(String name, float price, String hinhanh,String mota, int loai, int trongkho) throws SQLException {
        query = "insert into product (tenhang,giatien,hinhanh,mota,loai,trongkho) value(?,?,?,?,?,?);";
        statement = connection.prepareStatement(query);
        statement.setString(1,name);
        statement.setFloat(2,price);
        statement.setString(3,hinhanh);
        statement.setString(4,mota);
        statement.setInt(5,loai);
        statement.setInt(6,trongkho);
        statement.executeUpdate();
    }

    public void editPrd(int id, String name, float price, String hinhanh,String mota, int loai, int trongkho) throws SQLException {
        query = "update product set tenhang=?,giatien=?,hinhanh=?,mota=?,loai=?,trongkho=? where id = ?";
        statement = connection.prepareStatement(query);
        statement.setInt(7,id);
        statement.setString(1,name);
        statement.setFloat(2,price);
        statement.setString(3,hinhanh);
        statement.setString(4,mota);
        statement.setInt(5,loai);
        statement.setInt(6,trongkho);
        statement.executeUpdate();
    }

    public void deletePrd(int id) throws SQLException {
        query = "delete from product where id=?";
        statement = connection.prepareStatement(query);
        statement.setInt(1,id);
        statement.executeUpdate();
    }

    public List<SanPham> getSanPham() throws SQLException {
        List<SanPham> sanPhams = new ArrayList<>();
        query = "select product.id,product.tenhang,product.hinhanh,product.giatien,product.mota,product.trongkho,product.tinhtrang,tencategory as loai from product inner join category c on product.loai = c.id";
        statement = connection.prepareStatement(query);
        rs = statement.executeQuery();
        while (rs.next()){
            int id = rs.getInt("id");
            String name = rs.getString("tenhang");
            String image = rs.getString("hinhanh");
            float price = rs.getFloat("giatien");
            String description = rs.getString("mota");
            int inventory = rs.getInt("trongkho");
            String status = rs.getString("tinhtrang");
            String category = rs.getString("loai");
            sanPhams.add(new SanPham(id,name,price,description,image,category,inventory,status));
        }
        return sanPhams;
    }

    public SanPham getSanPhamById(int id) throws SQLException {
        SanPham sanPhams = null;
        query = "select * from product where id=?";
        statement = connection.prepareStatement(query);
        statement.setInt(1,id);
        rs = statement.executeQuery();
        while (rs.next()){
            String name = rs.getString("tenhang");
            String image = rs.getString("hinhanh");
            float price = rs.getFloat("giatien");
            String description = rs.getString("mota");
            int inventory = rs.getInt("trongkho");
            String status = rs.getString("tinhtrang");
            String category = String.valueOf(rs.getInt("loai"));
            sanPhams = new SanPham(id,name,price,description,image,category,inventory,status);
        }
        return sanPhams;
    }

    public List<PhanLoai> getCategory () throws SQLException {
        List<PhanLoai> list = new ArrayList<>();
        query = "select * from category";
        statement = connection.prepareStatement(query);
        rs = statement.executeQuery();
        while (rs.next()){
            int id = rs.getInt("id");
            String name = rs.getString("tencategory");
            list.add(new PhanLoai(id,name));
        }
        return list;
    }

    public void insertCat(String name) throws SQLException {
        query = "insert into category (tencategory) value (?)";
        statement = connection.prepareStatement(query);
        statement.setString(1,name);
        statement.executeUpdate();
    }
    public void editCat(int id, String name) throws SQLException {
        query = "update category set tencategory = ? where id = ?";
        statement = connection.prepareStatement(query);
        statement.setInt(2,id);
        statement.setString(1,name);
        statement.executeUpdate();
    }

    public void deleteCat(int id) throws SQLException {
        query = "delete from category where id=?";
        statement = connection.prepareStatement(query);
        statement.setInt(1,id);
        statement.executeUpdate();
    }

    public PhanLoai selectCatId(int id) throws SQLException {
        PhanLoai loai = null;
        query = "select * from category where id=?";
        statement = connection.prepareStatement(query);
        statement.setInt(1,id);
        rs = statement.executeQuery();
        while (rs.next()){
            String name = rs.getString("tencategory");
            loai = new PhanLoai(id,name);
        }
        return loai;
    }

    public List<SanPham> getSanPhamByCat(int catID) throws SQLException {
        List<SanPham> sanPhams = new ArrayList<>();
        query = "select product.id,product.tenhang,product.hinhanh,product.giatien,product.mota,product.trongkho,product.tinhtrang,tencategory as loai from product inner join category c on product.loai = c.id where c.id=?";
        statement = connection.prepareStatement(query);
        statement.setInt(1,catID);
        rs = statement.executeQuery();
        while (rs.next()){
            int id = rs.getInt("id");
            String name = rs.getString("tenhang");
            String image = rs.getString("hinhanh");
            float price = rs.getFloat("giatien");
            String description = rs.getString("mota");
            int inventory = rs.getInt("trongkho");
            String status = rs.getString("tinhtrang");
            String category = rs.getString("loai");
            sanPhams.add(new SanPham(id,name,price,description,image,category,inventory,status));
        }
        return sanPhams;
    }
    public List<SanPham> getSanPhamByCatName(int catID,String fName) throws SQLException {
        List<SanPham> sanPhams = new ArrayList<>();
        query = "select product.id,product.tenhang,product.hinhanh,product.giatien,product.mota,product.trongkho,product.tinhtrang,tencategory as loai from product inner join category c on product.loai = c.id where c.id=? and tenhang like '%" + fName + "%'";
        statement = connection.prepareStatement(query);
        statement.setInt(1,catID);
        rs = statement.executeQuery();
        while (rs.next()){
            int id = rs.getInt("id");
            String name = rs.getString("tenhang");
            String image = rs.getString("hinhanh");
            float price = rs.getFloat("giatien");
            String description = rs.getString("mota");
            int inventory = rs.getInt("trongkho");
            String status = rs.getString("tinhtrang");
            String category = rs.getString("loai");
            sanPhams.add(new SanPham(id,name,price,description,image,category,inventory,status));
        }
        return sanPhams;
    }
    public List<SanPham> getSanPhamByName(String fName) throws SQLException {
        List<SanPham> sanPhams = new ArrayList<>();
        query = "select product.id,product.tenhang,product.hinhanh,product.giatien,product.mota,product.trongkho,product.tinhtrang,tencategory as loai from product inner join category c on product.loai = c.id where tenhang like '%" + fName + "%'";
        statement = connection.prepareStatement(query);
        rs = statement.executeQuery();
        while (rs.next()){
            int id = rs.getInt("id");
            String name = rs.getString("tenhang");
            String image = rs.getString("hinhanh");
            float price = rs.getFloat("giatien");
            String description = rs.getString("mota");
            int inventory = rs.getInt("trongkho");
            String status = rs.getString("tinhtrang");
            String category = rs.getString("loai");
            sanPhams.add(new SanPham(id,name,price,description,image,category,inventory,status));
        }
        return sanPhams;
    }
}
