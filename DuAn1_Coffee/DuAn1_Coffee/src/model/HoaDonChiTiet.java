/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.math.BigDecimal;

/**
 *
 * @author tranl
 */
public class HoaDonChiTiet {

    private String maHD;
    private String maSP;
    private String tenSP;
    private int soLuongMua;
    private float giaTien;
    private BigDecimal thanhTien;
    private String trangThai;
    private String ghiChu;
    public HoaDonChiTiet() {
    }

    public HoaDonChiTiet(String maHD, String maSP, String tenSP, int soLuongMua, float giaTien, BigDecimal thanhTien, String trangThai, String ghiChu) {
        this.maHD = maHD;
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.soLuongMua = soLuongMua;
        this.giaTien = giaTien;
        this.thanhTien = thanhTien;
        this.trangThai = trangThai;
        this.ghiChu = ghiChu;
    }
      public HoaDonChiTiet( String maSP, String tenSP, int soLuongMua) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.soLuongMua = soLuongMua;
    }
      
     

    

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

   

    public int getSoLuongMua() {
        return soLuongMua;
    }

    public void setSoLuongMua(int soLuongMua) {
        this.soLuongMua = soLuongMua;
    }

    public float getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(float giaTien) {
        this.giaTien = giaTien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public BigDecimal getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(BigDecimal thanhTien) {
        this.thanhTien = thanhTien;
    }

   

    public HoaDonChiTiet(String maHD, String maSP, int soLuongMua, float giaTien,BigDecimal thanhTien, String trangThai, String ghiChu ) {
        this.maHD = maHD;
        this.maSP = maSP;
        this.soLuongMua = soLuongMua;
        this.giaTien = giaTien;
        this.thanhTien = thanhTien;
        this.trangThai = trangThai;
        this.ghiChu = ghiChu;
    }

    public HoaDonChiTiet(String maHD, String maSP, int soLuongMua, float giaTien, String trangThai) {
        this.maHD = maHD;
        this.maSP = maSP;
        this.soLuongMua = soLuongMua;
        this.giaTien = giaTien;
        this.trangThai = trangThai;
    }

    public HoaDonChiTiet(String maSP, String tenSP, int soLuongMua, float giaTien) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.soLuongMua = soLuongMua;
        this.giaTien = giaTien;
    }

   
    
}
