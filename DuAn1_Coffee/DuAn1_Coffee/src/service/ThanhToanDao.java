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
import java.util.List;
import model.ThanhToan;

/**
 *
 * @author tranl
 */
public class ThanhToanDao {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;
    
    public List<ThanhToan> selectAll() {
        sql = "select thanhToan_id, hinhThucThanhToan from thanhToan";
        List<ThanhToan> listThanhToan = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareCall(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ThanhToan tT = new ThanhToan(rs.getInt(1), rs.getString(2));
                listThanhToan.add(tT);
            }
            return listThanhToan;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
