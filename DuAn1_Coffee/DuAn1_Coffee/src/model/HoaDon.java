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
public class HoaDon {

    private String MaHD;
    private String MaNV;
    private String TenNV;
    private String MaKH;
    private String TenKH;
    private String SDT;
    private String NgayTao;
    private BigDecimal TongTien;
    private int maThanhToan;
    private String MaVoucher;
    private String diaChi;
    private String trangThai;

    public HoaDon() {
    }

    public HoaDon(String MaHD, String MaNV, String MaKH, String SDT, String NgayTao, BigDecimal TongTien, String MaVoucher, String diaChi, String trangThai) {
        this.MaHD = MaHD;
        this.MaNV = MaNV;
        this.MaKH = MaKH;
        this.SDT = SDT;
        this.NgayTao = NgayTao;
        this.TongTien = TongTien;
        this.MaVoucher = MaVoucher;
        this.diaChi = diaChi;
        this.trangThai = trangThai;
    }

    public HoaDon(String MaHD, String MaNV, String MaKH, BigDecimal TongTien, int maThanhToan, String trangThai) {
        this.MaHD = MaHD;
        this.MaNV = MaNV;
        this.MaKH = MaKH;
        this.TongTien = TongTien;
        this.maThanhToan = maThanhToan;
        this.trangThai = trangThai;
    }

//    public HoaDon(String MaHD, String MaNV, String MaKH, BigDecimal TongTien, String trangThai) {
//        this.MaHD = MaHD;
//        this.MaNV = MaNV;
//        this.MaKH = MaKH;
//        this.TongTien = TongTien;
//        this.trangThai = trangThai;
//    }

    public int getMaThanhToan() {
        return maThanhToan;
    }

    public void setMaThanhToan(int maThanhToan) {
        this.maThanhToan = maThanhToan;
    }

    

    public String getMaHD() {
        return MaHD;
    }

    public void setMaHD(String MaHD) {
        this.MaHD = MaHD;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getNgayTao() {
        return NgayTao;
    }

    public void setNgayTao(String NgayTao) {
        this.NgayTao = NgayTao;
    }

    public BigDecimal getTongTien() {
        return TongTien;
    }

    public void setTongTien(BigDecimal TongTien) {
        this.TongTien = TongTien;
    }

    public String getMaKH() {
        return MaKH;
    }

    public void setMaKH(String MaKH) {
        this.MaKH = MaKH;
    }

    public String getMaVoucher() {
        return MaVoucher;
    }

    public void setMaVoucher(String MaVoucher) {
        this.MaVoucher = MaVoucher;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public Object[] toDaTaRow() {
        return new Object[]{
            this.MaHD,
            this.MaNV,
            this.TenNV,
            this.SDT,
            this.NgayTao,
            this.TongTien == null ? "0.00" : this.TongTien,
            this.MaKH,
            this.TenKH,
            this.MaVoucher,
            this.trangThai,
            this.diaChi
        };
    }

    public String getTenNV() {
        return TenNV;
    }

    public void setTenNV(String TenNV) {
        this.TenNV = TenNV;
    }

    public String getTenKH() {
        return TenKH;
    }

    public void setTenKH(String TenKH) {
        this.TenKH = TenKH;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public HoaDon(String MaHD, String MaNV, String TenNV, String MaKH, String TenKH, String SDT, String NgayTao, BigDecimal TongTien, String MaVoucher, String diaChi, String trangThai) {
        this.MaHD = MaHD;
        this.MaNV = MaNV;
        this.TenNV = TenNV;
        this.MaKH = MaKH;
        this.TenKH = TenKH;
        this.SDT = SDT;
        this.NgayTao = NgayTao;
        this.TongTien = TongTien;
        this.MaVoucher = MaVoucher;
        this.diaChi = diaChi;
        this.trangThai = trangThai;
    }

}
