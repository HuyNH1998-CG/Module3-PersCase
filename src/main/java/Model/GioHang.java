package Model;

public class GioHang extends SanPham{
    private int soluong;

    public GioHang() {
    }

    public GioHang(int id, String ten, float gia, String mota, String hinhanh, String phanloai, int soluong) {
        super(id, ten, gia, mota, hinhanh, phanloai);
        this.soluong = soluong;
    }

    public GioHang(int id, String ten, float gia, String mota, String hinhanh, String phanloai) {
        super(id, ten, gia, mota, hinhanh, phanloai);
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
