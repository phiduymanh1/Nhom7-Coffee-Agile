/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import JDBC.DBConnect;
import model.HoaDonChiTiet;

/**
 *
 * @author tranl
 */
public class HoaDonChiTietDao {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;

    public List<HoaDonChiTiet> selectAll() {
        List<HoaDonChiTiet> listHDCT = new ArrayList<>();
        sql = "select * from hoaDonChiTiet order by MaHD";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonChiTiet hdct = new HoaDonChiTiet(rs.getString(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getFloat(4),
                        rs.getBigDecimal(5),
                        rs.getString(6),
                        rs.getString(7));
                listHDCT.add(hdct);
            }
            return listHDCT;
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return null;
    }

    public List<HoaDonChiTiet> selectHDCTByMaHD(String maHD) {
        List<HoaDonChiTiet> listHDCT = new ArrayList<>();
        sql = "select * from HoaDonChiTiet where MaHD like ? and TrangThai like N'Con Hang'";

        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, maHD);
            rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonChiTiet hdct = new HoaDonChiTiet(rs.getString(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getFloat(4),
                        rs.getBigDecimal(5),
                        rs.getString(6),
                        rs.getString(7));

                listHDCT.add(hdct);
            }
            return listHDCT;
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return null;
    }

    public ArrayList<HoaDonChiTiet> selectHDCT(String ma) {
        ArrayList<HoaDonChiTiet> listHDCT = new ArrayList<>();
        try {
            sql = "SELECT \n"
                    + "    hoaDon.MaHD AS maHD,                 \n"
                    + "    sanPham.MaSP AS maSP,              \n"
                    + "    sanPham.TenSP AS tenSP,                     \n"
                    + "    hoaDonChiTiet.SoLuongMua AS soLuongMua,      \n"
                    + "    hoaDonChiTiet.GiaTien AS giaTien,     \n"
                    + "    hoaDonChiTiet.ThanhTien AS thanhTien,  -- Thay thế SoTienGiam bằng ThanhTien\n"
                    + "    hoaDonChiTiet.TrangThai AS trangThai, \n"
                    + "    hoaDonChiTiet.GhiChu AS ghiChu\n"
                    + "FROM \n"
                    + "    hoaDonChiTiet\n"
                    + "JOIN \n"
                    + "    HoaDon hoaDon ON hoaDon.MaHD = hoaDonChiTiet.MaHD\n"
                    + "JOIN \n"
                    + "    SanPham sanPham ON sanPham.MaSP = hoaDonChiTiet.MaSP\n"
                    + "WHERE \n"
                    + "    hoaDonChiTiet.MaHD LIKE ?\n"
                    + "    AND (\n"
                    + "        hoaDonChiTiet.TrangThai LIKE N'Da Giao Hang' or hoaDonChiTiet.TrangThai LIKE N'Hoàn thành' \n"
                    + "        OR hoaDonChiTiet.TrangThai LIKE N'Chờ thanh toán'\n"
                    + "    );";

            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, ma);
            rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonChiTiet hdct = new HoaDonChiTiet();
                hdct.setMaHD(rs.getString("maHD"));
                hdct.setMaSP(rs.getString("maSP"));
                hdct.setTenSP(rs.getString("tenSP"));
                hdct.setSoLuongMua(rs.getInt("soLuongMua"));
                hdct.setGiaTien(rs.getFloat("giaTien"));
                hdct.setThanhTien(rs.getBigDecimal("thanhTien"));
                hdct.setTrangThai(rs.getString("trangThai"));
                hdct.setGhiChu(rs.getString("ghiChu"));

                listHDCT.add(hdct);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return listHDCT;
    }
}
