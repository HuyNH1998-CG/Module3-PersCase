package DAO;

import Model.DonHang;
import Model.GioHang;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    private Connection connection;
    private String query;
    private PreparedStatement statement;
    private ResultSet rs;

    public OrderDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean insertOrder(int uid, Date date) {
        boolean insert = false;
        try {
            query = "insert into productorder (userid, deliverdate) value (?,?);";
            statement = connection.prepareStatement(query);
            statement.setInt(1, uid);
            statement.setDate(2, date);
            insert = statement.executeUpdate() >0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return insert;
    }

    public boolean insertOrderDetail(ArrayList<GioHang> gioHangs) {
        boolean insert = false;
        try {
            query = "select last_insert_id() as id";
            statement = connection.prepareStatement(query);
            rs = statement.executeQuery();
            int id = 0;
            while (rs.next()) {
                id = rs.getInt("id");
            }
            query = "insert into orderdetail value (?,?,?)";
            statement = connection.prepareStatement(query);
            for (GioHang gioHang : gioHangs) {
                statement.setInt(1, id);
                statement.setInt(2, gioHang.getId());
                statement.setInt(3, gioHang.getSoluong());
                insert = statement.executeUpdate() >0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return insert;
    }

    public List<DonHang> getOrder(int id) {
        List<DonHang> orders = new ArrayList<>();
        try {
            query = "select productorder.orderid,productorder.userid,productorder.deliverdate, sum(quantity*giatien) as Total from productorder inner join orderdetail o on productorder.orderid = o.orderid inner join product p on o.productid = p.id where userid =? group by o.orderid";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            rs = statement.executeQuery();
            while (rs.next()) {
                int oid = rs.getInt("orderid");
                int uid = rs.getInt("userid");
                Date date = rs.getDate("deliverdate");
                float total = rs.getFloat("total");
                orders.add(new DonHang(oid, uid, date, total));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    public List<DonHang> getAllOrder() {
        List<DonHang> orders = new ArrayList<>();
        try {
            query = "select productorder.orderid,productorder.userid,productorder.deliverdate, sum(quantity*giatien) as Total from productorder inner join orderdetail o on productorder.orderid = o.orderid inner join product p on o.productid = p.id group by o.orderid";
            statement = connection.prepareStatement(query);
            rs = statement.executeQuery();
            while (rs.next()) {
                int oid = rs.getInt("orderid");
                int uid = rs.getInt("userid");
                Date date = rs.getDate("deliverdate");
                float total = rs.getFloat("total");
                orders.add(new DonHang(oid, uid, date, total));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    public void removeOrder(int id) {
        try {
            query = "delete from orderdetail where orderid=?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
            query = "delete from productorder where orderid=?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<GioHang> getdetail(int id){
        List<GioHang> list = new ArrayList<>();
        try{
            query = "select productid,quantity,tenhang,giatien,hinhanh,tencategory,mota from orderdetail inner join product p on orderdetail.productid = p.id inner join category c on p.loai = c.id where orderid =?";
            statement = connection.prepareStatement(query);
            statement.setInt(1,id);
            rs = statement.executeQuery();
            while (rs.next()){
                int pid = rs.getInt("productid");
                int quantity = rs.getInt("quantity");
                String name = rs.getString("tenhang");
                float price = rs.getFloat("giatien");
                String image = rs.getString("hinhanh");
                String category = rs.getString("tencategory");
                String desc = rs.getString("mota");
                list.add(new GioHang(pid,name,price,desc,image,category,quantity));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
