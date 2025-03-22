/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import JDBC.DBConnect;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.model_khachhang;

/**
 *
 * @author admin
 */
public class repositories_khachhang {

    public static List<model_khachhang> getKhachHang() {
        List<model_khachhang> list = new ArrayList<>();
        try {
            Connection con = DBConnect.getConnection();
            String sql = "select MaKH,TenKH,GioiTinh,DiaChi,SDT,LoaiKhacHang,DiemThanhVien from KhachHang";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String MaKH = rs.getString(1);
                String TenKh = rs.getString(2);
                String GioiTinh = rs.getString(3);
                String DiaChi = rs.getString(4);
                String SDT = rs.getString(5);
                String LoaiKhachHang = rs.getString(6);
                int DiemThanhVien = rs.getInt(7);
                model_khachhang khachang = new model_khachhang(MaKH, TenKh, GioiTinh, DiaChi, SDT, LoaiKhachHang, DiemThanhVien);
                list.add(khachang);

            }

            return list;
        } catch (Exception e) {
        }
        return null;
    }

    public static void them(model_khachhang kh) {
        try {
            Connection con = DBConnect.getConnection();
            String sql = " INSERT INTO KhachHang(MaKH,TenKH,GioiTinh,DiaChi,SDT,LoaiKhacHang,DiemThanhVien) VALUES(?,?,?,?,?,?,?) ";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, kh.getMaKH());
            ps.setString(2, kh.getTenKh());
            ps.setString(3, kh.getGioiTinh());
            ps.setString(4, kh.getDiaChi());
            ps.setString(5, kh.getSDT());
            ps.setString(6, kh.getLoaiKhacHang());
            ps.setInt(7, kh.getDiemThanhVien());
            ps.executeUpdate();
        } catch (Exception e) {
        }

    }

       public static void update( model_khachhang kh){
      try {
          Connection con = DBConnect.getConnection();
          String sql = """
                       UPDATE KhachHang SET  TenKH=?, GioiTinh=? ,DiaChi=?,SDT=?,LoaiKhacHang=? , DiemThanhVien=? WHERE  MaKH =?
                       """;
          PreparedStatement ps = con.prepareStatement(sql);
              ps.setString(1,kh.getTenKh() );
              ps.setString(2,kh.getGioiTinh() );
              ps.setString(3,kh.getDiaChi());
              ps.setString(4,kh.getSDT());
              ps.setString(5,kh.getLoaiKhacHang() );
              ps.setInt(6,kh.getDiemThanhVien() );
              ps.setString(7,kh.getMaKH() );
          ps.executeUpdate();
      } catch (Exception e) {
          e.printStackTrace(); // Log the exception for debugging
      }
  }

    public boolean exitByMa(String MaKH) {
        String sql = "Select count(*) from KhachHang where MaKH =? ";
        try {
            Connection con = DBConnect.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setObject(1, MaKH);
            ResultSet rs;
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void delete(String maKH) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBConnect.getConnection();
            con.setAutoCommit(false);

            String sql1 = "DELETE FROM HoaDonChiTiet WHERE MaHD IN (SELECT MaHD FROM HoaDon WHERE MaKH = ?)";
            ps = con.prepareStatement(sql1);
            ps.setString(1, maKH);
            ps.executeUpdate();

            String sql2 = "DELETE FROM HoaDon WHERE MaKH = ?";
            ps = con.prepareStatement(sql2);
            ps.setString(1, maKH);
            ps.executeUpdate();

            String sql3 = "DELETE FROM KhachHang WHERE MaKH = ?";
            ps = con.prepareStatement(sql3);
            ps.setString(1, maKH);
            ps.executeUpdate();

            con.commit();
        } catch (Exception e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
    }

    public List<model_khachhang> TimkiemKH(String searchQuery) {
        List<model_khachhang> result = new ArrayList<>();
        // Giả sử bạn có một danh sách tất cả khách hàng
        List<model_khachhang> allKhachHang = getKhachHang();

        for (model_khachhang kh : allKhachHang) {
            if (kh.getMaKH().contains(searchQuery)
                    || kh.getTenKh().contains(searchQuery)
                    || kh.getSDT().contains(searchQuery)) {
                result.add(kh);
            }
        }
        return result;
    }

    // từ đây là của thêm kh nhanh
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;

    public static List<model_khachhang> getKhachHangNhanh() {
        List<model_khachhang> list = new ArrayList<>();
        try {
            Connection con = DBConnect.getConnection();
            String sql = "select MaKH,TenKH,GioiTinh,DiaChi,SDT,LoaiKhacHang,DiemThanhVien from KhachHang";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String MaKH = rs.getString(1);
                String TenKH = rs.getString(2);
                String GioiTinh = rs.getString(3);
                String DiaChi = rs.getString(4);
                String SDT = rs.getString(5);
                String LoaiKhacHang = rs.getString(6);
                int DiemThanhVien = rs.getInt(7);

                model_khachhang kh = new model_khachhang(MaKH, TenKH, GioiTinh, DiaChi, SDT, LoaiKhacHang, DiemThanhVien);
                list.add(kh);
            }

            return list;
        } catch (Exception e) {
        }
        return null;
    }

    public Integer themKH(model_khachhang kh) {
        Integer row = null;

        try {
            Connection con = DBConnect.getConnection();
            String sql = " INSERT INTO KhachHang(MaKH,TenKH,GioiTinh,DiaChi,SDT,LoaiKhacHang,DiemThanhVien) VALUES(?,?,?,?,?,?,?) ";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, kh.getMaKH());
            ps.setString(2, kh.getTenKh());
            ps.setString(3, kh.getGioiTinh());
            ps.setString(4, kh.getDiaChi());
            ps.setString(5, kh.getSDT());
            ps.setString(6, kh.getLoaiKhacHang());
            ps.setInt(7, kh.getDiemThanhVien());
            row = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return row;
    }

    public List<model_khachhang> selectAll() {
        sql = "SELECT \n"
                + "    KhachHang.MaKH,\n"
                + "    KhachHang.TenKH,\n"
                + "    KhachHang.GioiTinh,\n"
                + "    KhachHang.DiaChi,\n"
                + "    KhachHang.SDT,\n"
                + "    KhachHang.LoaiKhacHang,\n"
                + "    KhachHang.DiemThanhVien,\n"
                + "    COUNT(CASE WHEN HoaDon.TrangThai = N'Da Thanh Toan' THEN 1 END) AS soLanMua\n"
                + "FROM \n"
                + "    KhachHang\n"
                + "LEFT JOIN \n"
                + "    HoaDon ON HoaDon.MaKH = KhachHang.MaKH\n"
                + "GROUP BY \n"
                + "    KhachHang.MaKH,\n"
                + "    KhachHang.TenKH,\n"
                + "    KhachHang.GioiTinh,\n"
                + "    KhachHang.DiaChi,\n"
                + "    KhachHang.SDT,\n"
                + "    KhachHang.LoaiKhacHang,\n"
                + "    KhachHang.DiemThanhVien;";

        List<model_khachhang> lst = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                lst.add(new model_khachhang(
                        rs.getString("MaKh"),
                        rs.getString("TenKH"),
                        rs.getString("GioiTinh"),
                        rs.getString("DiaChi"),
                        rs.getString("SDT"),
                        rs.getString("LoaiKhacHang"),
                        rs.getInt("DiemThanhVien"),
                        rs.getInt("soLanMua")));
            }
            return lst;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
