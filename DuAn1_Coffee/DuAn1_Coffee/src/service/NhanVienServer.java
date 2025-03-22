/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import JDBC.DBConnect;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import model.NhanVien;

/**
 *
 * @author Hoàng Quân
 */
public class NhanVienServer {
 Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;
    public static List<NhanVien> getNhanVien() {
        List<NhanVien> list = new ArrayList<>();
        try {
            Connection con = DBConnect.getConnection();
            String sql = "select MaNV,TenNV,GioiTinh,NgaySinh,SDT,ChucVu,Luong,QueQuan,ID from NhanVien";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String MaNV = rs.getString(1);
                String TenNV = rs.getString(2);
                String GioiTinh = rs.getString(3);
                Date NgaySinh = rs.getDate(4);
                String SDT = rs.getString(5);
                String ChucVu = rs.getString(6);
                float Luong = rs.getFloat(7);
                String QueQuan = rs.getString(8);
                int ID = rs.getInt(9);
                NhanVien nv = new NhanVien(MaNV, TenNV, GioiTinh, NgaySinh, SDT, ChucVu, Luong, QueQuan, ID);
                list.add(nv);
            }

            return list;
        } catch (Exception e) {
        }
        return null;
    }

    public static void ThemNhanVien(NhanVien nv) {
        try {
            Connection con = DBConnect.getConnection();
            String sql = "INSERT INTO NhanVien (MaNV, TenNV, GioiTinh, NgaySinh, SDT, ChucVu, Luong, QueQuan, ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nv.getMaNV());
            ps.setString(2, nv.getTenNV());
            ps.setString(3, nv.getGioiTinh());
            ps.setDate(4, new java.sql.Date(nv.getNgaySinh().getTime()));  // Correct date conversion
            ps.setString(5, nv.getSDT());
            ps.setString(6, nv.getChucVu());
            ps.setFloat(7, nv.getLuong());
            ps.setString(8, nv.getQueQuan());
            ps.setInt(9, nv.getID());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean exitByma(String maNV) {
        String sql = "SELECT COUNT(*) FROM NhanVien WHERE MaNV = ?";
        try {
            Connection con = DBConnect.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maNV); // Đặt tham số cho câu truy vấn
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Trả về true nếu có ít nhất một bản ghi
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return false;
    }

    public static void update(NhanVien nv) {
        try {
            Connection con = DBConnect.getConnection();
            String sql = "UPDATE NHANVIEN SET TenNV=?, GioiTinh=?, NgaySinh=?, SDT=?, ChucVu=?, Luong=?, QueQuan=?, ID=? WHERE MaNV=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nv.getTenNV());
            ps.setString(2, nv.getGioiTinh());
            ps.setDate(3, new java.sql.Date(nv.getNgaySinh().getTime()));  // Correct date conversion
            ps.setString(4, nv.getSDT());
            ps.setString(5, nv.getChucVu());
            ps.setFloat(6, nv.getLuong());
            ps.setString(7, nv.getQueQuan());
            ps.setInt(8, nv.getID());
            ps.setString(9, nv.getMaNV());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging
        }
    }
public NhanVien selectByID(String id) {
        List<NhanVien> listNV = new ArrayList<>();
        sql = """
         SELECT 
                 nv.*,
                 l.TaiKhoan,
                 l.MatKhau,
                 l.ChucVu
             FROM 
                 NhanVien nv
             JOIN 
                 login l ON nv.ID = l.ID
             WHERE 
                 l.TaiKhoan = ?;
         """;
//        List<NhanVien> listNV = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                NhanVien nv = new NhanVien(rs.getString("MaNV"),
                        rs.getString("TenNV"),
                        rs.getString("GioiTinh"),
                        rs.getDate("NgaySinh"),
                        rs.getString("SDT"),
                        rs.getString("ChucVu"),
                        rs.getFloat("Luong"),
                        rs.getString("QueQuan"),
                        rs.getInt("ID"),
                        rs.getString("TaiKhoan"),
                        rs.getString("MatKhau"),
                        rs.getString("ChucVu"));
                listNV.add(nv);
            }
            if (!listNV.isEmpty()) {
                return listNV.get(0);
            } else {
                return null;
            }

        } catch (SQLException e) {
            return null;

        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                // Xử lý ngoại lệ trong trường hợp đóng kết nối thất bại
            }
        }
    }
    public static void deleteNhanVien(String maNV) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBConnect.getConnection();
            con.setAutoCommit(false);

            // Xóa dữ liệu từ bảng HoaDonChiTiet liên quan đến nhân viên
            String sql1 = "DELETE FROM HoaDonChiTiet WHERE MaHD IN (SELECT MaHD FROM HoaDon WHERE MaNV = ?)";
            ps = con.prepareStatement(sql1);
            ps.setString(1, maNV);
            ps.executeUpdate();

            // Xóa dữ liệu từ bảng HoaDon liên quan đến nhân viên
            String sql2 = "DELETE FROM HoaDon WHERE MaNV = ?";
            ps = con.prepareStatement(sql2);
            ps.setString(1, maNV);
            ps.executeUpdate();

            // Xóa nhân viên từ bảng NhanVien
            String sql3 = "DELETE FROM NhanVien WHERE MaNV = ?";
            ps = con.prepareStatement(sql3);
            ps.setString(1, maNV);
            ps.executeUpdate();

            // Xóa tài khoản liên quan từ bảng login (nếu cần)
            // (Bạn cần thêm xử lý cho bảng login nếu cần xóa tài khoản liên quan)
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

    public List<NhanVien> TimKiemNV(String searchQuery) {
        List<NhanVien> result = new ArrayList<>();
        List<NhanVien> allNhanVien = getNhanVien();
        for (NhanVien nv : allNhanVien) {
            if (nv.getMaNV().contains(searchQuery)
                    || nv.getTenNV().contains(searchQuery)
                    || nv.getSDT().contains(searchQuery)) {
                result.add(nv);
            }
        }
        return result;
    }
    public boolean exitBySDT(String sdt) {
    String sql = "SELECT COUNT(*) FROM NhanVien WHERE SDT = ?";
    try (Connection con = DBConnect.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        
        ps.setString(1, sdt); // Đặt tham số cho câu truy vấn

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) > 0; // Trả về true nếu có ít nhất một bản ghi
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}

}
