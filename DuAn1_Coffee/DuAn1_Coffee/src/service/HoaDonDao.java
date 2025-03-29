/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import JDBC.DBConnect;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.HoaDon;

/**
 *
 * @author tranl
 */
public class HoaDonDao {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;

    public List<HoaDon> selectAll() {
        List<HoaDon> listHD = new ArrayList<>();
        sql = "    SELECT MaHD, MaNV, SDT, NgayTao, TongTien, MaKH, MaVoucher, TrangThai, DiaChi FROM HoaDon;";

        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon(
                        rs.getString("MaHD"),
                        rs.getString("MaNV"),
                        rs.getString("MaKH"),
                        rs.getString("SDT"),
                        rs.getString("NgayTao"),
                        rs.getBigDecimal("TongTien"),
                        rs.getString("MaVoucher"),
                        rs.getString("DiaChi"),
                        rs.getString("TrangThai"));
                listHD.add(hd);
            }
            return listHD;
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return null;
    }

    public List<HoaDon> selectAll2() {
        List<HoaDon> listHD = new ArrayList<>();
        sql = "SELECT \n"
                + "    HoaDon.MaHD, \n"
                + "    NhanVien.MaNV, \n"
                + "    NhanVien.TenNV, \n"
                + "	KhachHang.MaKH, \n"
                + "    KhachHang.TenKH, \n"
                + "    KhachHang.SDT, \n"
                + "    HoaDon.NgayTao, \n"
                + "    HoaDon.TongTien, \n"
                + "    HoaDon.MaVoucher, \n"
                + "    HoaDon.TrangThai, \n"
                + "    HoaDon.DiaChi\n"
                + "FROM \n"
                + "    HoaDon\n"
                + "JOIN \n"
                + "    NhanVien ON HoaDon.MaNV = NhanVien.MaNV\n"
                + "JOIN \n"
                + "    KhachHang ON HoaDon.MaKH = KhachHang.MaKH";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon(rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getBigDecimal(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11));

                listHD.add(hd);
            }
            return listHD;
        } catch (Exception e) {
            e.fillInStackTrace();
            return null;
        }
    }

    public List<HoaDon> phanTrangHoaDon(int tienLui) {
        ArrayList<HoaDon> lst = new ArrayList<>();
        try {
            sql = """
            SELECT 
                HoaDon.MaHD, 
                NhanVien.MaNV, 
                NhanVien.TenNV, 
                KhachHang.MaKH, 
                KhachHang.TenKH, 
                KhachHang.SDT, 
                HoaDon.NgayTao, 
                HoaDon.TongTien, 
                HoaDon.MaVoucher, 
                HoaDon.TrangThai,
                HoaDon.DiaChi
            FROM 
                HoaDon
            JOIN 
                NhanVien ON HoaDon.MaNV = NhanVien.MaNV
            JOIN 
                KhachHang ON HoaDon.MaKH = KhachHang.MaKH
            ORDER BY
                HoaDon.NgayTao DESC
            OFFSET ? ROWS
            FETCH NEXT 5 ROWS ONLY
            """;
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, tienLui);
            rs = ps.executeQuery();
            while (rs.next()) {

                HoaDon hd = new HoaDon(rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getBigDecimal(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11));
                lst.add(hd);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return lst;
    }

    public List<HoaDon> findPhanTrangHoaDon(String maHoaDon) {
        ArrayList<HoaDon> lst = new ArrayList<>();
        try {
            sql = """
            SELECT 
                            HoaDon.MaHD, 
                            NhanVien.MaNV, 
                            NhanVien.TenNV, 
                            KhachHang.MaKH, 
                            KhachHang.TenKH, 
                            HoaDon.SDT, 
                            HoaDon.NgayTao, 
                            HoaDon.TongTien, 
                            HoaDon.MaVoucher, 
                            HoaDon.TrangThai,
                            HoaDon.DiaChi
                        FROM 
                            HoaDon
                        JOIN 
                            NhanVien ON HoaDon.MaNV = NhanVien.MaNV
                        JOIN 
                            KhachHang ON HoaDon.MaKH = KhachHang.MaKH
                        WHERE
                            (HoaDon.MaHD LIKE ? OR 
                             KhachHang.MaKH LIKE ? OR 
                             HoaDon.SDT LIKE ?)
                        ORDER BY
                            HoaDon.NgayTao DESC
                        OFFSET 0 ROWS
                        FETCH NEXT 5 ROWS ONLY
            """;
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);

            String searchPattern = "%" + maHoaDon + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setString(3, searchPattern);
            ps.setString(1, maHoaDon);

            rs = ps.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon(rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getBigDecimal(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11));

                lst.add(hd);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return lst;
    }

}
