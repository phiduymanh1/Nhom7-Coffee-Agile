/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import JDBC.DBConnect;
import Login.Server_Login;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.HoaDon;
import model.HoaDonChiTiet;
import model.Model_SanPham;
import model.model_khachhang;


/**
 *
 * @author Hoàng Quân
 */
public class BanHangDao {
    
    Connection con = null;
    ResultSet rs = null;
    String sql = null;
    PreparedStatement ps = null;
    
    public ArrayList<HoaDon> selectHDCho() {
        ArrayList<HoaDon> lst = new ArrayList<>();
        String nhanVien_ma = Server_Login.user.getMaNV();
        
        try {
            String sqlLocal = "select * from HoaDon where TrangThai like N'Chờ thanh toán' and MaNV like '" + nhanVien_ma + "' order by NgayTao desc";
            Connection cn = DBConnect.getConnection();
            PreparedStatement pstm = cn.prepareStatement(sqlLocal);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHD(rs.getString("MaHD"));
                hd.setNgayTao(rs.getString("NgayTao"));
                hd.setTongTien(rs.getBigDecimal("TongTien"));
                hd.setMaNV(rs.getString("MaNV"));
                hd.setMaKH(rs.getString("MaKH"));
                hd.setTrangThai(rs.getString("TrangThai"));
                lst.add(hd);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return lst;
    }
    
    public int insertHoaDonCho(HoaDon hDC) {
        
        sql = "INSERT INTO HoaDon(MaHD, MaNV, TongTien, TrangThai, MaKH,thanhToan_id) VALUES (?, ?, ?, ?, ?,?)";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareCall(sql);
            ps.setObject(1, hDC.getMaHD());
            ps.setObject(2, hDC.getMaNV());
            ps.setObject(3, hDC.getTongTien());
            ps.setObject(4, hDC.getTrangThai());
            ps.setObject(5, hDC.getMaKH());
            ps.setObject(6, hDC.getMaThanhToan());
            
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public int huyHoaDonCho(String hoaDon_id, String hDCT_id,String maSP) {
        sql = "{CALL HUYHOADON(?,?,?)}";
        
        try {
            con = DBConnect.getConnection();
            ps = con.prepareCall(sql);
            ps.setObject(1, hoaDon_id);
            ps.setObject(2, hDCT_id);    
            ps.setObject(3, maSP);

            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public HoaDonChiTiet selectHDCTByHD_idANDSPCT_id(String hoaDon_id, String hDCT_id) {
        List<HoaDonChiTiet> lst = new ArrayList<>();
        try {
            String sqlLocal = """
            SELECT HoaDonChiTiet.MaHD,
                   SanPham.MaSP,
                   SanPham.TenSP,
                   HoaDonChiTiet.SoLuongMua AS soLuong,
                   HoaDonChiTiet.GiaTien AS giaBan,
                   HoaDonChiTiet.TrangThai
            FROM HoaDonChiTiet
            JOIN HoaDon ON HoaDon.MaHD = HoaDonChiTiet.MaHD
            JOIN SanPham ON SanPham.MaSP = HoaDonChiTiet.MaSP
            WHERE HoaDonChiTiet.MaHD LIKE ?
              AND SanPham.MaSP LIKE ?
              AND HoaDonChiTiet.TrangThai LIKE N'Chờ thanh toán';
        """;
            Connection cn = DBConnect.getConnection();
            PreparedStatement pstm = cn.prepareStatement(sqlLocal);
            pstm.setObject(1, hoaDon_id);
            pstm.setObject(2, hDCT_id);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                HoaDonChiTiet hdct = new HoaDonChiTiet();
                hdct.setMaHD(rs.getString("MaHD"));
                hdct.setMaSP(rs.getString("MaSP"));
                hdct.setTenSP(rs.getString("TenSP"));
                hdct.setGiaTien(rs.getFloat("giaBan"));
                hdct.setSoLuongMua(rs.getInt("soLuong"));
                hdct.setTrangThai(rs.getString("TrangThai"));
                lst.add(hdct);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            // return null;
        }
        if (!lst.isEmpty()) {
            return lst.get(0);
        } else {
            return null;
        }
    }
    
    public ArrayList<HoaDonChiTiet> selectHDCT(String id) {
        ArrayList<HoaDonChiTiet> lst = new ArrayList<>();
        try {
            String sqlLocal = """
            SELECT HoaDonChiTiet.MaHD,
                   SanPham.MaSP,
                   SanPham.TenSP,
                   HoaDonChiTiet.SoLuongMua AS soLuong,
                   HoaDonChiTiet.GiaTien AS giaBan,
                   HoaDonChiTiet.TrangThai
            FROM HoaDonChiTiet
            JOIN HoaDon ON HoaDon.MaHD = HoaDonChiTiet.MaHD
            JOIN SanPham ON SanPham.MaSP = HoaDonChiTiet.MaSP
            WHERE HoaDonChiTiet.MaHD LIKE ?
              AND HoaDonChiTiet.TrangThai LIKE N'Chờ thanh toán';
        """;
            Connection cn = DBConnect.getConnection();
            PreparedStatement pstm = cn.prepareStatement(sqlLocal);
            pstm.setString(1, id);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                HoaDonChiTiet hdct = new HoaDonChiTiet();
                hdct.setMaHD(rs.getString("MaHD"));
                hdct.setMaSP(rs.getString("MaSP"));
                hdct.setTenSP(rs.getString("TenSP"));
                hdct.setGiaTien(rs.getFloat("giaBan"));
                hdct.setSoLuongMua(rs.getInt("soLuong"));
                // Bỏ qua không thiết lập thuộc tính thanhTien
                hdct.setTrangThai(rs.getString("TrangThai"));
                lst.add(hdct);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lst;
    }
    
    public int insertHoaDonCTPROC(HoaDonChiTiet hDCT) {
        sql = "{CALL ThemSanPhamVaoHoaDonChiTiet(?,?,?)}"; // Sử dụng biến sql đã khai báo
        int result = 0;
        
        try {
            // Thiết lập kết nối
            con = DBConnect.getConnection();
            // Tạo đối tượng PreparedStatement
            ps = con.prepareCall(sql);
            
            if (hDCT != null) {
                // Thiết lập các tham số
                ps.setObject(1, hDCT.getMaHD());
                ps.setObject(2, hDCT.getMaSP());
                ps.setObject(3, hDCT.getSoLuongMua());

                // Thực thi câu lệnh và nhận kết quả
                result = ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng PreparedStatement và Connection
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return result;
    }
    
    public ArrayList<HoaDonChiTiet> selectSPCT() {
        ArrayList<HoaDonChiTiet> lst = new ArrayList<>();
        try {
            String sqlLocal = """
                         select * from SanPham
                                               
                         """;
            Connection cn = DBConnect.getConnection();
            PreparedStatement pstm = cn.prepareStatement(sqlLocal);
            ResultSet rsLocal = pstm.executeQuery();
            while (rsLocal.next()) {
                HoaDonChiTiet spct = new HoaDonChiTiet();
                spct.setMaSP(rsLocal.getString("MaSP"));
                spct.setTenSP(rsLocal.getString("TenSP"));
                spct.setGiaTien(rsLocal.getFloat("GiaTien"));
                spct.setSoLuongMua(rsLocal.getInt("SoLuong"));
                spct.setTrangThai(rsLocal.getString("TrangThai"));
                
                lst.add(spct);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return lst;
    }
    
    public int duaHDCTVeTrangThaiHuy(String hDCT_id, String sPCT_id) {
        sql = "{CALL duaHDCTVeTrangThaiHuy(?,?)}";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareCall(sql);
            ps.setObject(1, hDCT_id);
            ps.setObject(2, sPCT_id);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public int suaSLSPTrongGio(String maHD, String maSP, int soLuongThayDoi, int soLuongTrongGio) {
        String sql = "{CALL CHINHSUASOLUONGSP(?, ?, ?, ?)}";
        try {
            // Mở kết nối cơ sở dữ liệu
            con = DBConnect.getConnection();

            // Tạo đối tượng CallableStatement để gọi thủ tục
            ps = con.prepareCall(sql);

            // Cài đặt các tham số cho thủ tục
            ps.setString(1, maHD);      // Mã hóa đơn
            ps.setString(2, maSP);           // Mã sản phẩm
            ps.setInt(3, soLuongThayDoi);    // Số lượng thay đổi
            ps.setInt(4, soLuongTrongGio);   // Số lượng trong giỏ hàng

            // Thực thi thủ tục và trả về số hàng bị ảnh hưởng
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            // Đảm bảo đóng các tài nguyên
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public Model_SanPham selectSoLuongSPAnđonGia(String maSP) {
        sql = """
               SELECT  [MaSP]
                                ,[SoLuong]
                                ,[GiaTien]
                                ,[TenSP]
                                ,[TrangThai]
                            FROM [dbo].[SanPham] 
                            WHERE MaSP = ?
              """;
        int soLuong = 0;
        float donGia;
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, maSP);
            rs = ps.executeQuery();
            Model_SanPham spct = new Model_SanPham();
            while (rs.next()) {
                spct.setSoLuong(rs.getInt("SoLuong"));
                spct.setGiaTien(rs.getFloat("GiaTien"));
                soLuong = spct.getSoLuong();
            }
            return spct;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public model_khachhang selectKHbySDT(String sdt) {
        sql = """
           SELECT 
                 KhachHang.MaKH,
                 TenKH,
                 GioiTinh,
                 KhachHang.DiaChi,
                 KhachHang.SDT,
                 LoaiKhacHang,
                 DiemThanhVien,
                 COUNT(HoaDon.MaKH) AS soLanMua
             FROM 
                 dbo.KhachHang 
             LEFT JOIN 
                 HoaDon ON HoaDon.MaKH = KhachHang.MaKH 
             WHERE 
                 KhachHang.SDT = ?
             GROUP BY 
                 KhachHang.MaKH,
                 TenKH,
                 GioiTinh,
                 KhachHang.DiaChi,
                 KhachHang.SDT,
                 LoaiKhacHang,
                 DiemThanhVien;
         """;
        List<model_khachhang> listKH = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, sdt);
            rs = ps.executeQuery();
            while (rs.next()) {
                listKH.add(new model_khachhang(
                        rs.getString("MaKH"),
                        rs.getString("TenKH"),
                        rs.getString("GioiTinh"),
                        rs.getString("DiaChi"),
                        rs.getString("SDT"),
                        rs.getString("LoaiKhacHang"),
                        rs.getInt("DiemThanhVien"),
                        rs.getInt("soLanMua")
                ));
                
            }
            if (!listKH.isEmpty()) {
                return listKH.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public int thanhToan(String hoaDon_id, BigDecimal tongTien, int maThanhToan, String maKH,String diaChi) {
        sql = "{CALL THANHTOANHOADON(?,?,?,?,?)}";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareCall(sql);
            ps.setObject(1, hoaDon_id);
            ps.setObject(2, tongTien);
            ps.setObject(3, maThanhToan);
            ps.setObject(4, maKH);        
            ps.setObject(5, diaChi);

            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
     
     public ArrayList<HoaDonChiTiet> timKiem(String maSPct,String tenSPct, String trangThaict){
        ArrayList<HoaDonChiTiet> list = new ArrayList<>();
        sql = "select hdct.MaSP,sp.TenSP,sp.GiaTien,sp.SoLuong,sp.TrangThai from HoaDonChiTiet hdct join sanPham sp on sp.MaSP = hdct.MaSP where hdct.MaSP like ? or sp.TenSP like ? or sp.TrangThai like ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1,'%'+maSPct+'%');
            ps.setObject(3, '%'+tenSPct+'%');
            ps.setObject(2,'%'+trangThaict+'%');
            rs = ps.executeQuery();
            while(rs.next()){
                HoaDonChiTiet spct = new HoaDonChiTiet();
                spct.setMaSP(rs.getString("MaSP"));
                spct.setTenSP(rs.getString("TenSP"));
                spct.setGiaTien(rs.getFloat("GiaTien"));
                spct.setSoLuongMua(rs.getInt("SoLuong"));
                spct.setTrangThai(rs.getString("TrangThai"));
                list.add(spct);
            }
            return list;
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }
     
     
}
