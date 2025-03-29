/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author admin
 */
public class model_khachhang {
    private String MaKH;
    private String TenKh;
    private String GioiTinh;
    private String DiaChi;
    private String SDT;
    private String LoaiKhacHang;
    private int DiemThanhVien;
    private int soLanMua;

    public model_khachhang() {
    }

    public model_khachhang(String MaKH, String TenKh, String GioiTinh, String DiaChi, String SDT, String LoaiKhacHang, int DiemThanhVien, int soLanMua) {
        this.MaKH = MaKH;
        this.TenKh = TenKh;
        this.GioiTinh = GioiTinh;
        this.DiaChi = DiaChi;
        this.SDT = SDT;
        this.LoaiKhacHang = LoaiKhacHang;
        this.DiemThanhVien = DiemThanhVien;
        this.soLanMua = soLanMua;
    }

    public model_khachhang(String MaKH, String TenKh, String GioiTinh, String DiaChi, String SDT, String LoaiKhacHang, int DiemThanhVien) {
        this.MaKH = MaKH;
        this.TenKh = TenKh;
        this.GioiTinh = GioiTinh;
        this.DiaChi = DiaChi;
        this.SDT = SDT;
        this.LoaiKhacHang = LoaiKhacHang;
        this.DiemThanhVien = DiemThanhVien;
    }

    public String getMaKH() {
        return MaKH;
    }

    public void setMaKH(String MaKH) {
        this.MaKH = MaKH;
    }

    public String getTenKh() {
        return TenKh;
    }

    public void setTenKh(String TenKh) {
        this.TenKh = TenKh;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(String GioiTinh) {
        this.GioiTinh = GioiTinh;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getLoaiKhacHang() {
        return LoaiKhacHang;
    }

    public void setLoaiKhacHang(String LoaiKhacHang) {
        this.LoaiKhacHang = LoaiKhacHang;
    }

    public int getDiemThanhVien() {
        return DiemThanhVien;
    }

    public void setDiemThanhVien(int DiemThanhVien) {
        this.DiemThanhVien = DiemThanhVien;
    }

    public int getSoLanMua() {
        return soLanMua;
    }

    public void setSoLanMua(int soLanMua) {
        this.soLanMua = soLanMua;
    }

   
   
}
