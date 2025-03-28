/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;


import JDBC.DBConnect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import model.Model_SanPham;
public class Repo_SanPham {
    private Connection con = null;
    private PreparedStatement pr = null;
    private ResultSet rs = null;
    private String sql = null;
    
    public ArrayList<Model_SanPham> getAll(){
        ArrayList<Model_SanPham> listSP = new ArrayList<>();
        sql = "Select MaSP,TenSP,SoLuong,GiaTien,TrangThai from SanPham ";
        try {
            con = DBConnect.getConnection();
            pr = con.prepareStatement(sql);
            rs = pr.executeQuery();
            while(rs.next()){
                String maSP,tenSP,trangThai;
                float giaTien;
                int soLuong;
                maSP = rs.getString(1);
                tenSP = rs.getString(2);
                soLuong = rs.getInt(3);
                giaTien = rs.getFloat(4);
                trangThai = rs.getString(5);
                Model_SanPham sp = new Model_SanPham(maSP, tenSP, soLuong, giaTien, trangThai);
                listSP.add(sp);
            }
            return listSP;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
     public int them(Model_SanPham m2){
        sql = "Insert into SanPham(MaSP,TenSP,SoLuong,GiaTien,TrangThai) values (?,?,?,?,?)";
        try {
            con = DBConnect.getConnection();
            pr = con.prepareStatement(sql);
            pr.setObject(1, m2.getMaSP());
            pr.setObject(2, m2.getTenSP());
            pr.setObject(3, m2.getSoLuong());
            pr.setObject(4, m2.getGiaTien());
            pr.setObject(5, m2.getTrangThai());
            return pr.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    public boolean  exitByma(String maSP){
        sql = "select count (*) from SanPham where MaSP =?";
        try {
            con = DBConnect.getConnection();
            pr = con.prepareStatement(sql);
            pr.setObject(1, maSP);
            rs = pr.executeQuery();
            if(rs.next()){
                return rs.getInt(1)>0;
            }
        } catch (Exception e) {
            e.printStackTrace();   
        }
        return false;
    }
     public int sua(Model_SanPham m2 ,String maSPcs){
        sql = "Update SanPham set TenSP =?,SoLuong=?,GiaTien=?,TrangThai=? where MaSP =?";
        try {
            con = DBConnect.getConnection();
            pr = con.prepareStatement(sql);
            pr.setObject(5, maSPcs);
            pr.setObject(1, m2.getTenSP());
            pr.setObject(2, m2.getSoLuong());
            pr.setObject(3, m2.getGiaTien());
            pr.setObject(4, m2.getTrangThai());
            return pr.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
   
}
