package DAO;

import Model.KhachHang;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private Connection connection;
    private String query;
    private PreparedStatement statement;
    private ResultSet rs;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public void insertUser(String username, String password, String name, Date dob, String phone, String email, String address) throws SQLException {
        query = "insert into users (username, password, ten, ngaysinh, sodienthoai, email, diachi) VALUE (?,?,?,?,?,?,?)";
        statement = connection.prepareStatement(query);
        statement.setString(1, username);
        statement.setString(2, password);
        statement.setString(3, name);
        statement.setDate(4, dob);
        statement.setString(5, phone);
        statement.setString(6, email);
        statement.setString(7, address);
        statement.executeUpdate();
    }

    public void insertUserByAdmin(String username, String password, String name, Date dob, String phone, String email, String address, String role) throws SQLException {
        query = "insert into users (username, password, ten, ngaysinh, sodienthoai, email, diachi,role) VALUE (?,?,?,?,?,?,?,?)";
        statement = connection.prepareStatement(query);
        statement.setString(1, username);
        statement.setString(2, password);
        statement.setString(3, name);
        statement.setDate(4, dob);
        statement.setString(5, phone);
        statement.setString(6, email);
        statement.setString(7, address);
        statement.setString(8, role);
        statement.executeUpdate();
    }

    public void editUserInfo(int id, String name, Date dob, String phone, String email, String address) throws SQLException {
        query = "update users set ten=?,ngaysinh=?,sodienthoai=?,email=?,diachi=? where id=?";
        statement = connection.prepareStatement(query);
        statement.setString(1, name);
        statement.setDate(2, dob);
        statement.setString(3, phone);
        statement.setString(4, email);
        statement.setString(5, address);
        statement.setInt(6, id);
        statement.executeUpdate();
    }

    public void changePassword(int id, String password) throws SQLException {
        query = "update users set password=? where id=?";
        statement = connection.prepareStatement(query);
        statement.setString(1, password);
        statement.setInt(2, id);
        statement.executeUpdate();
    }

    public List<KhachHang> login() throws SQLException {
        List<KhachHang> user = new ArrayList<>();
        query = "select * from users;";
        statement = connection.prepareStatement(query);
        rs = statement.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String username = rs.getString("username");
            String password = rs.getString("password");
            String role = rs.getString("role");
            String ten = rs.getString("ten");
            Date dob = rs.getDate("ngaysinh");
            String phone = rs.getString("sodienthoai");
            String email = rs.getString("email");
            String diachi = rs.getString("diachi");
            user.add(new KhachHang(id, username, password, ten, dob, phone, email, diachi, role));
        }
        return user;
    }

    public void deleteAccount(int id) throws SQLException {
        query = "delete from users where id=?";
        statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        statement.executeUpdate();
    }

    public void editUserInfoAdmin(int id, String name, Date dob, String phone, String email, String address, String role) throws SQLException {
        query = "update users set ten=?,ngaysinh=?,sodienthoai=?,email=?,diachi=?,role=? where id=?";
        statement = connection.prepareStatement(query);
        statement.setString(1, name);
        statement.setDate(2, dob);
        statement.setString(3, phone);
        statement.setString(4, email);
        statement.setString(5, address);
        statement.setString(6, role);
        statement.setInt(7, id);
        statement.executeUpdate();
    }

    public KhachHang getAccount(int id) throws SQLException {
        query = "select * from users where id=?";
        KhachHang user = null;
        statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        rs = statement.executeQuery();
        while (rs.next()) {
            String username = rs.getString("username");
            String password = rs.getString("password");
            String role = rs.getString("role");
            String ten = rs.getString("ten");
            Date dob = rs.getDate("ngaysinh");
            String phone = rs.getString("sodienthoai");
            String email = rs.getString("email");
            String diachi = rs.getString("diachi");
            user = new KhachHang(id, username, password, ten, dob, phone, email, diachi, role);
        }
        return user;
    }
}
