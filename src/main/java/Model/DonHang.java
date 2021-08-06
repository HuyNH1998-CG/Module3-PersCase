package Model;

import java.sql.Date;

public class DonHang extends SanPham{
    private int orderID;
    private int userid;
    private Date date;
    private float total;

    public DonHang() {
    }

    public DonHang(int userid, Date date) {
        this.userid = userid;
        this.date = date;
    }

    public DonHang(int orderID, int userid, Date date, float total) {
        this.orderID = orderID;
        this.userid = userid;
        this.date = date;
        this.total = total;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
