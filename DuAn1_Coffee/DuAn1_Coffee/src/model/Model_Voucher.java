/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;



import java.util.Date;

/**
 *
 * @author admin
 */
public class Model_Voucher {
    private String maVoucher;
    private String tenKM;
    private String hinhThuc;
    private float soTienGiam;
    private int soptGiam;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private String trangThai;
    private String dkGiam;
    private float mucGiamToiDa;

    public Model_Voucher() {
    }

    public Model_Voucher(String maVoucher, String tenKM,String hinhThuc, float soTienGiam,int soptGiam, Date ngayBatDau, Date ngayKetThuc, String trangThai,String dkGiam,float mucGiamToiDa) {
        this.maVoucher = maVoucher;
        this.tenKM = tenKM;
        this.hinhThuc=hinhThuc;
        this.soTienGiam = soTienGiam;
        this.soptGiam = soptGiam;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.trangThai = trangThai;
        this.dkGiam = dkGiam;
        this.mucGiamToiDa = mucGiamToiDa;
    }

    public String getMaVoucher() {
        return maVoucher;
    }

    public void setMaVoucher(String maVoucher) {
        this.maVoucher = maVoucher;
    }

    public String getTenKM() {
        return tenKM;
    }

    public void setTenKM(String tenKM) {
        this.tenKM = tenKM;
    }

    public String getHinhThuc() {
        return hinhThuc;
    }

    public void setHinhThuc(String hinhThuc) {
        this.hinhThuc = hinhThuc;
    }
    
    public float getSoTienGiam() {
        return soTienGiam;
    }

    public void setSoTienGiam(float soTienGiam) {
        this.soTienGiam = soTienGiam;
    }

    public int getSoptGiam() {
        return soptGiam;
    }

    public void setSoptGiam(int soptGiam) {
        this.soptGiam = soptGiam;
    }
    
    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getDkGiam() {
        return dkGiam;
    }

    public void setDkGiam(String dkGiam) {
        this.dkGiam = dkGiam;
    }

    public float getMucGiamToiDa() {
        return mucGiamToiDa;
    }

    public void setMucGiamToiDa(float mucGiamToiDa) {
        this.mucGiamToiDa = mucGiamToiDa;
    }
    
    
    
    public Object[] toDaTaRow(){
        return new Object[]{this.getMaVoucher(),this.getTenKM(),this.getHinhThuc(),this.getSoTienGiam(),this.getSoptGiam(),this.getNgayBatDau(),this.getNgayKetThuc(),this.getTrangThai(),this.getDkGiam(),this.getMucGiamToiDa()};
    }
}
