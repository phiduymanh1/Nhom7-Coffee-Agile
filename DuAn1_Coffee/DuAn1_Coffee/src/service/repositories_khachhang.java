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

    

}
