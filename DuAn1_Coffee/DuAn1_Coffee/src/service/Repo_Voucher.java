/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;



import JDBC.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import model.Model_Voucher;
public class Repo_Voucher {
   private Connection con = null;
    private PreparedStatement pr = null;
    private ResultSet rs = null;
    private String sql = null;
    
    public ArrayList<Model_Voucher> getAll(){
        ArrayList<Model_Voucher> listV = new ArrayList<>();
        sql = "Select MaVoucher,TenV,HinhThuc,SoTienGiamVND ,SoTienGiamPhanTram,NgayBatDau,NgayKetThuc,TrangThai,DieuKienGiam,MucGiamToiDa from Voucher";
        try {
            con = DBConnect.getConnection();
            pr = con.prepareStatement(sql);
            rs = pr.executeQuery();
            while(rs.next()){
                String maVoucher,tenKM,trangThai,hinhThuc,dkGiam;
                Date ngayBatDau,ngayKetThuc;
                float soTienGiam,mucGiamToiDa;
                int soptGiam;
                maVoucher = rs.getString(1);
                tenKM = rs.getString(2);
                hinhThuc = rs.getString(3);
                soTienGiam = rs.getFloat(4);
                soptGiam = rs.getInt(5);
                ngayBatDau = rs.getDate(6);
                ngayKetThuc = rs.getDate(7);
                trangThai = rs.getString(8);
                dkGiam = rs.getString(9);
                mucGiamToiDa = rs.getFloat(10);
                Model_Voucher mv = new Model_Voucher(maVoucher, tenKM,hinhThuc, soTienGiam,soptGiam, ngayBatDau, ngayKetThuc, trangThai,dkGiam, mucGiamToiDa);
                listV.add(mv);
            }
            return listV;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public int them(Model_Voucher m2){
        sql ="Insert into Voucher (MaVoucher,TenV,HinhThuc,SoTienGiamVND,SoTienGiamPhanTram,NgayBatDau,NgayKetThuc,TrangThai,DieuKienGiam,MucGiamToiDa) values (?,?,?,?,?,?,?,?,?,?)";
        try {
            con = DBConnect.getConnection();
            pr = con.prepareStatement(sql);
            pr.setObject(1, m2.getMaVoucher());
            pr.setObject(2, m2.getTenKM());
            pr.setObject(3, m2.getHinhThuc());
            pr.setObject(4, m2.getSoTienGiam());
            pr.setObject(5, m2.getSoptGiam());
            pr.setObject(6, m2.getNgayBatDau());
            pr.setObject(7, m2.getNgayKetThuc());
            pr.setObject(8, m2.getTrangThai());
            pr.setObject(9, m2.getDkGiam());
            pr.setObject(10, m2.getMucGiamToiDa());
            return pr.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean exitByMa(String maVoucher){
        sql = "Select count(*) from Voucher where MaVoucher =? ";
        try {
            con = DBConnect.getConnection();
            pr = con.prepareStatement(sql);
            pr.setObject(1, maVoucher);
            rs = pr.executeQuery();
            if(rs.next()){
                return rs.getInt(1)>0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public int sua(Model_Voucher m2,String maVouchercs){
        sql ="Update Voucher set TenV =?,HinhThuc=?,SoTienGiamVND =?,SoTienGiamPhanTram=?, NgayBatDau=?,NgayKetThuc =?,TrangThai=?,DieuKienGiam=?,MucGiamToiDa=? where MaVoucher=?";
        try {
            con = DBConnect.getConnection();
            pr = con.prepareStatement(sql);
            pr.setObject(10, maVouchercs);
            pr.setObject(1, m2.getTenKM());
            pr.setObject(2, m2.getHinhThuc());
            pr.setObject(3, m2.getSoTienGiam());
            pr.setObject(4, m2.getSoptGiam());
            pr.setObject(5, m2.getNgayBatDau());
            pr.setObject(6, m2.getNgayKetThuc());
            pr.setObject(7, m2.getTrangThai());
            pr.setObject(8, m2.getDkGiam());
            pr.setObject(9, m2.getMucGiamToiDa());
            return pr.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    public int xoa(String maVouchercx){
        try {
        con = DBConnect.getConnection();

        // Xóa các bản ghi trong bảng HoaDonChiTiet liên quan
        String sql = "DELETE FROM HoaDonChiTiet WHERE MaHD IN (SELECT MaHD FROM HoaDon WHERE MaVoucher = ?)";
        pr = con.prepareStatement(sql);
        pr.setObject(1, maVouchercx);
        pr.executeUpdate();

        // Xóa các bản ghi trong bảng HoaDon liên quan
        sql = "DELETE FROM HoaDon WHERE MaVoucher = ?";
        pr = con.prepareStatement(sql);
        pr.setObject(1, maVouchercx);
        pr.executeUpdate();

        // Cuối cùng, xóa bản ghi trong bảng Voucher
        sql = "DELETE FROM Voucher WHERE MaVoucher = ?";
        pr = con.prepareStatement(sql);
        pr.setObject(1, maVouchercx);
        return pr.executeUpdate();

    } catch (Exception e) {
        e.printStackTrace();
        return 0;
    }
        
    }
     public ArrayList<Model_Voucher> timkiem(String maVocherct, String tenVct,String trangThaict){
        ArrayList<Model_Voucher> listV = new ArrayList<>();
        sql = "Select MaVoucher,TenV,HinhThuc,SoTienGiamVND,SoTienGiamPhanTram,NgayBatDau,NgayKetThuc,TrangThai,DieuKienGiam,MucGiamToiDa from Voucher where MaVoucher like ? or TenV like ? or TrangThai like ?";
        try {
            con = DBConnect.getConnection();
            pr = con.prepareStatement(sql);
            pr.setObject(1,'%'+maVocherct+'%');
            pr.setObject(2,'%'+tenVct+'%');
            pr.setObject(3,'%'+trangThaict+'%');
            rs = pr.executeQuery();
            while(rs.next()){
                String maVoucher,tenKM,trangThai,hinhThuc,dkGiam;
                Date ngayBatDau,ngayKetThuc;
                float soTienGiam,mucGiamToiDa;
                int soptGiam;
                maVoucher = rs.getString(1);
                tenKM = rs.getString(2);
                hinhThuc = rs.getString(3);
                soTienGiam = rs.getFloat(4);
                soptGiam = rs.getInt(5);
                ngayBatDau = rs.getDate(6);
                ngayKetThuc = rs.getDate(7);
                trangThai = rs.getString(8);
                dkGiam= rs.getString(9);
                mucGiamToiDa = rs.getFloat(10);
                Model_Voucher mv = new Model_Voucher(maVoucher, tenKM, hinhThuc, soTienGiam, soptGiam, ngayBatDau, ngayKetThuc, trangThai,dkGiam,mucGiamToiDa);
                listV.add(mv);
            }
            return listV;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
