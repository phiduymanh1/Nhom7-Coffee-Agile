package Login;

import JDBC.DBConnect;
import Login.Model_Login;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.NhanVien;

public class Server_Login {
    public static Model_Login getLogin(String username, String password) {
        try {
            Connection con = DBConnect.getConnection();
            String sql = "SELECT * FROM login WHERE TaiKhoan = ? AND MatKhau = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("ID");
                String taiKhoan = rs.getString("TaiKhoan");
                String matKhau = rs.getString("MatKhau");
                String chucVu = rs.getString("ChucVu");
               return new Model_Login(id, taiKhoan, matKhau, chucVu);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static NhanVien user = null;

    public static void dangXuat() {
        Server_Login.user = null;
    }

    public static boolean daDangNhap() {
        return Server_Login.user != null;
    }

//    public static boolean trangThaiTaiKhoan() {
//        return "Hoạt động".equals(Server_Login.user.getTrangThai());
//    }

    public static boolean phanQuyen() {
            return Server_Login.daDangNhap() && Server_Login.user.getChucVuLogin().equals("QuanLy");
    }
}
