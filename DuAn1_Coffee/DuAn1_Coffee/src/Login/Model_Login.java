/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Login;

/**
 *
 * @author Hoàng Quân
 */
public class Model_Login {
    private int ID;
    private String TaiKhoan;
    private String MatKhau;
    private String ChucVu;

    public Model_Login() {
//        không tham số
    }

    public Model_Login(int ID, String TaiKhoan, String MatKhau, String ChucVu) {
        this.ID = ID;
        this.TaiKhoan = TaiKhoan;
        this.MatKhau = MatKhau;
        this.ChucVu = ChucVu;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTaiKhoan() {
        return TaiKhoan;
    }

    public void setTaiKhoan(String TaiKhoan) {
        this.TaiKhoan = TaiKhoan;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String MatKhau) {
        this.MatKhau = MatKhau;
    }

    public String getChucVu() {
        return ChucVu;
    }

    public void setChucVu(String ChucVu) {
        this.ChucVu = ChucVu;
    }

   
    
}
