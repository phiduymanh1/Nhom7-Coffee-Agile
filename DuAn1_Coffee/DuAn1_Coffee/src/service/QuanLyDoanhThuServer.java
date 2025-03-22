    /*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
     */
    package service;

import JDBC.DBConnect;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import model.HoaDonChiTiet;
import model.HoaDonDoanhThu;

    /**
     *
     * @author Hoàng Quân
     */
    public class QuanLyDoanhThuServer {
             public static List<HoaDonDoanhThu> getDoanhThu(){
            List<HoaDonDoanhThu> HoaDonList = new ArrayList<>();
            try {
                Connection con = DBConnect.getConnection();
                String sql = """
                           WITH HoaDondoanhthu AS (
                                SELECT 
                                    CAST(NgayTao AS DATE) AS Ngay, -- Get the date part only
                                    COUNT(MaHD) AS SoLuongHoaDon,
                                    SUM(TongTien) AS TongTien
                                FROM HoaDon
                                GROUP BY CAST(NgayTao AS DATE) -- Group by date part only
                            )
                            SELECT 
                                ROW_NUMBER() OVER (ORDER BY Ngay) AS STT,
                                Ngay,
                                SoLuongHoaDon,
                                TongTien
                            FROM HoaDondoanhthu
                            ORDER BY Ngay;
                             """;
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {  
                    Date Ngay = rs.getDate(2);
                    String ma = rs.getString(3);
                    float Tong = rs.getFloat(4);
                    HoaDonDoanhThu hoaDon = new HoaDonDoanhThu(ma, ma, sql, Ngay, Tong, ma, ma, ma, ma);
                    HoaDonList.add(hoaDon);

                }
                return HoaDonList;

            } catch (Exception e) {
                 e.printStackTrace();
            }
            return null;
        }

        public static List<HoaDonDoanhThu> TimKiemTheoNgay(Date ngay) {
    List<HoaDonDoanhThu> HoaDonList = new ArrayList<>();
    try {
        Connection con = DBConnect.getConnection();
        String sql = """
                     SELECT MaHD, MaNV, SDT, NgayTao, TongTien, MaKH, MaVoucher, TrangThai, DiaChi
                     FROM HoaDon
                     WHERE CAST(NgayTao AS DATE) = ?;
                     """;
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setDate(1, new java.sql.Date(ngay.getTime()));  
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {     
            String MaHD = rs.getString(1);
            String MaNV = rs.getString(2);
            String SDT = rs.getString(3);
            Date NgayTao = rs.getDate(4);
            float TongTien = rs.getFloat(5);
            String MaKH = rs.getString(6);
            String MaVoucher = rs.getString(7);
            String DiaChi = rs.getString(8);
            String TrangThai = rs.getString(9);
            HoaDonDoanhThu hoaDon = new HoaDonDoanhThu(MaHD, MaNV, SDT, NgayTao, TongTien, TrangThai, DiaChi, MaKH, MaVoucher);
            HoaDonList.add(hoaDon);
        }
    } catch (Exception e) {
        e.printStackTrace(); 
    }
    return HoaDonList; 
}

        
        
        
         public static List<HoaDonDoanhThu> TimKiemnamThang(Date NgayBatDau, Date NgayKetThuc) {
            List<HoaDonDoanhThu> HoaDonList = new ArrayList<>();
            try {
                Connection con = DBConnect.getConnection();
                String sql = """
                              SELECT MaHD, MaNV, SDT, NgayTao, TongTien, MaKH, MaVoucher, TrangThai, DiaChi,thanhtoan_id
                              FROM HoaDon
                              WHERE NgayTao BETWEEN ? AND ?;
                             """;
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setDate(1, NgayBatDau);  
                ps.setDate(2, NgayKetThuc); 
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String MaHD = rs.getString(1);
                    String MaNV = rs.getString(2);
                    String SDT = rs.getString(3);
                    Date NgayTao = rs.getDate(4);
                    float TongTien = rs.getFloat(5);
                    String MaKH = rs.getString(6);
                    String MaVoucher = rs.getString(7);
                    String DiaChi = rs.getString(8);
                    String TrangThai = rs.getString(9);
                    HoaDonDoanhThu hoaDon = new HoaDonDoanhThu(MaHD, MaNV, SDT, NgayTao, TongTien, TrangThai, DiaChi, MaKH, MaVoucher);
                    HoaDonList.add(hoaDon);
                }
            } catch (Exception e) {
                e.printStackTrace(); 
            }
            return HoaDonList; 
        }
         
         
          public static List<HoaDonChiTiet> getHDCT() {
        List<HoaDonChiTiet> list = new ArrayList<>();
        try {
            Connection con = DBConnect.getConnection();
            String Sql = """
                         SELECT 
                          HDCT.MaSP, 
                          SP.TenSP, 
                          HDCT.SoLuongMua
                          FROM 
                          HoaDonChiTiet HDCT
                          INNER JOIN 
                          SanPham SP ON HDCT.MaSP = SP.MaSP
                          INNER JOIN 
                          HoaDon HD ON HDCT.MaHD = HD.MaHD;
                         """;
            PreparedStatement ps = con.prepareStatement(Sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String maSP = rs.getString(1);
                String tenSP = rs.getString(2);
                int soLuongMua = rs.getInt(3);
                HoaDonChiTiet hdct = new HoaDonChiTiet(maSP, tenSP, soLuongMua);
                list.add(hdct);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
          
          
        public static List<HoaDonChiTiet> gettop5sp(Date ngaybatdausp, Date ngayketthucsp) {
        List<HoaDonChiTiet> list = new ArrayList<>();
        try {
            Connection con = DBConnect.getConnection();
            String Sql = """
                        SELECT TOP 5 
                            HDCT.MaSP, 
                            SP.TenSP, 
                            SUM(HDCT.SoLuongMua) AS SoLuongMua
                        FROM HoaDonChiTiet HDCT
                        INNER JOIN SanPham SP ON HDCT.MaSP = SP.MaSP
                        INNER JOIN HoaDon HD ON HDCT.MaHD = HD.MaHD
                        WHERE HD.NgayTao BETWEEN ? AND ?
                        GROUP BY 
                            HDCT.MaSP, 
                            SP.TenSP
                        ORDER BY SoLuongMua DESC;
                        """;
            PreparedStatement ps = con.prepareStatement(Sql);
            ps.setDate(1, new java.sql.Date(ngaybatdausp.getTime())); 
            ps.setDate(2, new java.sql.Date(ngayketthucsp.getTime())); 
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String maSP = rs.getString("MaSP");
                String tenSP = rs.getString("TenSP");
                int soLuongMua = rs.getInt("SoLuongMua");
                HoaDonChiTiet hdct = new HoaDonChiTiet(maSP, tenSP, soLuongMua);
                list.add(hdct);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

        
        public static List<HoaDonChiTiet> getTop5SPTrongNgay(Date ngay) {
    List<HoaDonChiTiet> list = new ArrayList<>();
    try {
        Connection con = DBConnect.getConnection();
        String sql = """
                    SELECT TOP 5 
                        HDCT.MaSP, 
                        SP.TenSP, 
                        SUM(HDCT.SoLuongMua) AS SoLuongMua
                    FROM HoaDonChiTiet HDCT
                    INNER JOIN SanPham SP ON HDCT.MaSP = SP.MaSP
                    INNER JOIN HoaDon HD ON HDCT.MaHD = HD.MaHD
                    WHERE CAST(HD.NgayTao AS DATE) = ?
                    GROUP BY 
                        HDCT.MaSP, 
                        SP.TenSP
                    ORDER BY SoLuongMua DESC;
                    """;
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setDate(1, new java.sql.Date(ngay.getTime()));
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String maSP = rs.getString("MaSP");
            String tenSP = rs.getString("TenSP");
            int soLuongMua = rs.getInt("SoLuongMua");
            HoaDonChiTiet hdct = new HoaDonChiTiet(maSP, tenSP, soLuongMua);
            list.add(hdct);
        }
        rs.close();
        ps.close();
        con.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}













    }
