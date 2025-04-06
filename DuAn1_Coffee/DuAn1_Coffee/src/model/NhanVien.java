/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author Hoàng Quân
 */
public class NhanVien {

    private String MaNV;
    private String TenNV;
    private String GioiTinh;
    private Date NgaySinh;
    private String SDT;
    private String ChucVu;
    private float Luong;
    private String QueQuan;
    private int ID;
    private String taiKhoan;
    private String matKhau;
    private String chucVuLogin;

    public NhanVien() {
    }

    public NhanVien(String MaNV, String TenNV, String GioiTinh, Date NgaySinh, String SDT, String ChucVu, float Luong, String QueQuan, int ID, String taiKhoan, String matKhau, String chucVuLogin) {
        this.MaNV = MaNV;
        this.TenNV = TenNV;
        this.GioiTinh = GioiTinh;
        this.NgaySinh = NgaySinh;
        this.SDT = SDT;
        this.ChucVu = ChucVu;
        this.Luong = Luong;
        this.QueQuan = QueQuan;
        this.ID = ID;
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.chucVuLogin = chucVuLogin;
    }

    public NhanVien(String MaNV, String TenNV, String GioiTinh, Date NgaySinh, String SDT, String ChucVu, float Luong, String QueQuan, int ID) {
        this.MaNV = MaNV;
        this.TenNV = TenNV;
        this.GioiTinh = GioiTinh;
        this.NgaySinh = NgaySinh;
        this.SDT = SDT;
        this.ChucVu = ChucVu;
        this.Luong = Luong;
        this.QueQuan = QueQuan;
        this.ID = ID;
    }

    public String getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getChucVuLogin() {
        return chucVuLogin;
    }

    public void setChucVuLogin(String chucVuLogin) {
        this.chucVuLogin = chucVuLogin;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public String getTenNV() {
        return TenNV;
    }

    public void setTenNV(String TenNV) {
        this.TenNV = TenNV;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(String GioiTinh) {
        this.GioiTinh = GioiTinh;
    }

    public Date getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(Date NgaySinh) {
        this.NgaySinh = NgaySinh;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getChucVu() {
        return ChucVu;
    }

    public void setChucVu(String ChucVu) {
        this.ChucVu = ChucVu;
    }

    public float getLuong() {
        return Luong;
    }

    public void setLuong(float Luong) {
        this.Luong = Luong;
    }

    public String getQueQuan() {
        return QueQuan;
    }

    public void setQueQuan(String QueQuan) {
        this.QueQuan = QueQuan;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

}
