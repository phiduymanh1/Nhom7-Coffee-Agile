/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;


import JDBC.DBConnect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import Model.Model_SanPham;
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

}
