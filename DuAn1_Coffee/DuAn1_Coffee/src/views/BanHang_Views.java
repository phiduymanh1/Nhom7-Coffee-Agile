/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package views;

import Login.Server_Login;
import TrangChu.DangNhap;
import model.HoaDon;
import model.HoaDonChiTiet;
import service.HoaDonChiTietDao;
import service.HoaDonDao;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import model.ThanhToan;
import model.model_khachhang;
import service.BanHangDao;
import service.ThanhToanDao;

/**
 *
 * @author tranl
 */
public class BanHang_Views extends javax.swing.JPanel {

    private ThanhToanDao ttDAO = new ThanhToanDao();
    private HoaDonDao hddao = new HoaDonDao();
    private HoaDonChiTietDao hdctdao = new HoaDonChiTietDao();
    private BanHangDao bhdao = new BanHangDao();
    public static String sdtKH;
    public static String hoTenKH;
    private DefaultTableModel dtm = new DefaultTableModel();
    private int i = -1;
    private ArrayList<HoaDonChiTiet> lst = new ArrayList<>();
    private DefaultTableModel dtmGioHang;

    /**
     * Creates new form BanHang_Views
     */
    public BanHang_Views() {
        initComponents();
        loadDataToTableHD(bhdao.selectHDCho());

        this.loadDataToTableSPCT(bhdao.selectSPCT());
        tuDongTinhTienKhachDua();
//        tuDongTimKH();
        setComboThanhToan();
    }

    private void fillToTableGioHang(List<HoaDonChiTiet> lst) {
        dtmGioHang = (DefaultTableModel) tblGioHang.getModel();
        dtmGioHang.setRowCount(0);
        for (HoaDonChiTiet hoaDonChiTiet : lst) {
            dtmGioHang.addRow(new Object[]{
                hoaDonChiTiet.getMaSP(),
                hoaDonChiTiet.getTenSP(),
                hoaDonChiTiet.getGiaTien(),
                hoaDonChiTiet.getSoLuongMua(),
                hoaDonChiTiet.getSoLuongMua() * hoaDonChiTiet.getGiaTien()
            });
        }
    }

    private void loadDataToTableSPCT(List<HoaDonChiTiet> lst) {
        DefaultTableModel model = new DefaultTableModel();
        model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);
        for (HoaDonChiTiet spct : lst) {
            model.addRow(new Object[]{
                spct.getMaSP(),
                spct.getTenSP(),
                spct.getGiaTien(),
                spct.getSoLuongMua(),
                spct.getTrangThai()
            });
        }
    }

//    void clearForm() {
//        txttensp.setText("");
//        // xóa trắng form khi thực hiện xong việc tìm kiếm
//    }
    private void loadDataToTableHD(List<HoaDon> lst) {
        DefaultTableModel model = new DefaultTableModel();
        model = (DefaultTableModel) tblHoaDon.getModel();
        model.setRowCount(0);
        for (HoaDon hd : lst) {
            model.addRow(new Object[]{
                hd.getMaHD(),
                hd.getNgayTao(),
                hd.getMaNV(),
                hd.getMaKH(),
                hd.getTrangThai()
            });
        }
    }

    private void setComboThanhToan() {
        DefaultComboBoxModel modelc = (DefaultComboBoxModel) cboHinhThucThanhToan.getModel();
        modelc.removeAllElements();
        for (ThanhToan tt : ttDAO.selectAll()) {
            modelc.addElement(tt.getHinhThucThanhToan());
        }
    }

    private void loadDataToTableHDCT(List<HoaDonChiTiet> lst) {
        DefaultTableModel model = new DefaultTableModel();
        model = (DefaultTableModel) tblGioHang.getModel();
        model.setRowCount(0);
        for (HoaDonChiTiet hdct : lst) {
            model.addRow(new Object[]{
                hdct.getMaSP(),
                hdct.getTenSP(),
                hdct.getGiaTien(),
                hdct.getSoLuongMua(),
                hdct.getSoLuongMua() * hdct.getGiaTien()

            });
        }
    }

    public void chonKH(String tenKH, String sdt) {
        txtTenKH.setText(tenKH);
        txtSDT.setText(sdt);

    }

    private String maHDTuDong() {
        int soMaHD = hddao.selectAll().size();
        String ma = "HD" + String.format("%03d", ++soMaHD);
        return ma;
    }

    private String maHDCTTuDong() {
        int soMaHD = hdctdao.selectAll().size();
        String ma = "HDCT" + String.format("%03d", ++soMaHD);
        return ma;
    }

    private void moThemNhanhKhachHang() {

        DangNhap tc = new DangNhap();
        new QLKhachHang(tc, true).setVisible(true);
    }

    private void addHDCho() {
        HoaDon hd = readFormHDCho();
        if (bhdao.insertHoaDonCho(hd) != 0) {
            JOptionPane.showMessageDialog(this, "Tạo hoá đơn chờ thành công!!!");
            loadDataToTableHD(bhdao.selectHDCho());
        }
    }

    public void clearFormThanhToan() {
        lblMaHD.setText("");
        lblThanhToan.setText("");
        lblTongTien.setText("");
        lblTienThua.setText("");
        lblTienThuaThieu.setText("");
        txtTienKhachDua.setText("");
        txtSDT.setText("");
        txtTenKH.setText("Khách bán lẻ");
    }

    HoaDon readFormHDCho() {
        int maThanhToan = 1;
        for (ThanhToan tt : ttDAO.selectAll()) {
            if (cboHinhThucThanhToan.getSelectedItem().toString().equalsIgnoreCase(tt.getHinhThucThanhToan())) {
                maThanhToan = tt.getThanhToan_id();
            }
        }
        return new HoaDon(maHDTuDong(), Server_Login.user.getMaNV(), "KH000", BigDecimal.valueOf(0), maThanhToan, "Chờ thanh toán");
    }

    private void huyHoaDonCho() {
        // Hiển thị hộp thoại xác nhận
        int confirmation = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn hủy hóa đơn không?", "Xác nhận hủy hóa đơn", JOptionPane.YES_NO_OPTION);

        // Kiểm tra phản hồi từ người dùng
        if (confirmation == JOptionPane.YES_OPTION) {
            if (tblGioHang.getRowCount() <= 0) {
                // Kiểm tra xem có hàng nào được chọn trong bảng hóa đơn
                int selectedRowHD = tblHoaDon.getSelectedRow();
                if (selectedRowHD != -1) {
                    // Xác định ID hóa đơn từ bảng hóa đơn
                    String hoaDon_id = tblHoaDon.getValueAt(selectedRowHD, 0).toString();
                    String hDCT_id = "";
                    String maSP = "";

                    bhdao.huyHoaDonCho(hoaDon_id, hDCT_id, maSP);
                    JOptionPane.showMessageDialog(this, "Đã huỷ hóa đơn");
                    loadDataToTableHD(bhdao.selectHDCho());
                    loadDataToTableHDCT(bhdao.selectHDCT(""));
                } else {
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn để hủy.");
                }
            } else {
                // Hủy từng sản phẩm trong giỏ hàng
                String hoaDon_id = lblMaHD.getText();
                for (int i = 0; i < tblGioHang.getRowCount(); i++) {
                    String maSP = tblGioHang.getValueAt(i, 0).toString();
                    String hDCT_id = bhdao.selectHDCTByHD_idANDSPCT_id(hoaDon_id, maSP).getMaHD();
                    bhdao.huyHoaDonCho(hoaDon_id, hDCT_id, maSP);
                }
                JOptionPane.showMessageDialog(this, "Đã huỷ hóa đơn");
                loadDataToTableHD(bhdao.selectHDCho());
                loadDataToTableHDCT(bhdao.selectHDCT(""));
                loadDataToTableSPCT(bhdao.selectSPCT());
            }
        } else {
            // Nếu người dùng chọn không hủy
            JOptionPane.showMessageDialog(this, "Hủy hóa đơn đã bị hủy.");
        }
    }

    private BigDecimal tinhTongTien() throws NumberFormatException {
        // TODO add your handling code here:
        BigDecimal tongTien2 = BigDecimal.ZERO;
        for (int i = 0; i < tblGioHang.getRowCount(); i++) {
            BigDecimal donGia = BigDecimal.valueOf(Double.parseDouble(tblGioHang.getValueAt(i, 2).toString()));
            int soLuong = Integer.parseInt(tblGioHang.getValueAt(i, 3).toString());
            BigDecimal tongTien1 = donGia.multiply(BigDecimal.valueOf(soLuong));
            tongTien2 = tongTien2.add(tongTien1);
        }

        lblTongTien.setText(tongTien2 + "");
        lblThanhToan.setText(tongTien2 + "");
        return tongTien2;
    }

    int soLuongSanPham = 0;

    boolean checkFormHDCT() {
        String soLuongSanPhamString;

        do {
            soLuongSanPhamString = JOptionPane.showInputDialog(this, "Mời nhập số lượng!!");
            if (soLuongSanPhamString == null) {
                return false;
            }

            if (soLuongSanPhamString.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không được để trống số lượng!!");
                return false;
            } else {
                try {
                    soLuongSanPham = Integer.parseInt(soLuongSanPhamString);

                    if (soLuongSanPham <= 0) {
                        JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0!!");
                        return false;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Số lượng phải là số!!");
                    return false;
                }
            }
            if (soLuongSanPham > Integer.parseInt(tblSanPham.getValueAt(tblSanPham.getSelectedRow(), 3).toString())) {
                JOptionPane.showMessageDialog(this, "Số lượng tồn không đủ!!");
            } else {
                break;
            }
        } while (true);

        return true;
    }

    HoaDonChiTiet readFormHDCT() {
        int index = tblSanPham.getSelectedRow();
        if (index == -1) {
            // Không có hàng nào được chọn
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một sản phẩm.");
            return null;
        }

        String sPCT_id = tblSanPham.getValueAt(index, 0).toString();
        float giaBan = Float.parseFloat(tblSanPham.getValueAt(index, 2).toString().trim());
        String maHD = tblHoaDon.getValueAt(tblHoaDon.getSelectedRow(), 0).toString();

        if (checkFormHDCT()) {
            return new HoaDonChiTiet(
                    maHD,
                    sPCT_id,
                    soLuongSanPham,
                    giaBan,
                    "Chờ thanh toán"
            );
        } else {
            return null;
        }
    }

    private void addHDCTPROC() {
        HoaDonChiTiet hdct = readFormHDCT();
        if (bhdao.insertHoaDonCTPROC(hdct) > 0) {
            int index = tblHoaDon.getSelectedRow();
            String id = tblHoaDon.getValueAt(index, 0).toString();
            loadDataToTableHDCT(bhdao.selectHDCT(id));
            loadDataToTableSPCT(bhdao.selectSPCT());
            lblMaHD.setText(id);
            tinhTongTien();
        }
    }

    private void doiTrangThaiHDCTPROC() {
        int index = tblGioHang.getSelectedRow();
        if (index >= 0) {
            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn muốn xoá sản phẩm này?",
                    "XOÁ SẢN PHẨM RA KHỎI GIỎ HÀNG",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                // Lấy mã hóa đơn từ giao diện
                String hoaDon_id = lblMaHD.getText();

                // Lấy mã sản phẩm từ bảng giỏ hàng
                String maSP = tblGioHang.getValueAt(index, 0).toString();

                // Lấy thông tin chi tiết hóa đơn dựa trên mã hóa đơn và mã sản phẩm
                HoaDonChiTiet hdct = bhdao.selectHDCTByHD_idANDSPCT_id(hoaDon_id, maSP);

                if (hdct != null) {
                    String hDCT_id = hdct.getMaHD();

                    // Gọi phương thức để đưa hóa đơn chi tiết về trạng thái hủy
                    bhdao.duaHDCTVeTrangThaiHuy(hDCT_id, maSP);

                    // Tải lại dữ liệu vào bảng hóa đơn chi tiết và sản phẩm chi tiết
                    loadDataToTableHDCT(bhdao.selectHDCT(hoaDon_id));
                    loadDataToTableSPCT(bhdao.selectSPCT());
                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin chi tiết hóa đơn!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để xoá!!");
        }
    }

    boolean checkSoLuongThayDoi() {
        String soLuongSanPhamString;
        do {
            soLuongSanPhamString = JOptionPane.showInputDialog(this, "Mời nhập số lượng cần thay đổi!!");
            if (soLuongSanPhamString == null) {
                return false;
            }

            if (soLuongSanPhamString.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không được để trống số lượng thay đổi!!");
                return false;
            } else {
                try {
                    soLuongSanPhamThayDoi = Integer.parseInt(soLuongSanPhamString);

                    if (soLuongSanPhamThayDoi <= 0) {
                        JOptionPane.showMessageDialog(this, "Số lượng thay đổi phải lớn hơn 0!!");
                        return false;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Số lượng thay dổi phải là số!!");
                    return false;
                }
            }
            if (soLuongSanPhamThayDoi > bhdao.selectSoLuongSPAnđonGia(tblGioHang.getValueAt(tblGioHang.getSelectedRow(), 0) + "").getSoLuong() + Integer.parseInt(tblGioHang.getValueAt(tblGioHang.getSelectedRow(), 3) + "")) {
                JOptionPane.showMessageDialog(this, "Số lượng tồn không đủ!!");
            } else {
                break;
            }
        } while (true);

        return true;
    }

    private void tuDongTinhTienKhachDua() {
        txtTienKhachDua.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateTienThua();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateTienThua();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Not used for plain text components
            }

            private void updateTienThua() {
                try {
                    String tienKhachDuaStr = txtTienKhachDua.getText();
                    if (!tienKhachDuaStr.isEmpty()) {
                        BigDecimal tienKhachDua = new BigDecimal(tienKhachDuaStr);
                        BigDecimal tienThua = tienKhachDua.subtract(tinhTongTien());

                        if (tienThua.compareTo(BigDecimal.ZERO) >= 0) {
                            lblTienThuaThieu.setText("Tiền thừa:");
                        } else {
                            lblTienThuaThieu.setText("Tiền thiếu:");
                        }

                        lblTienThua.setText(String.valueOf(tienThua.abs()));
                    } else {
                        // Chuỗi trống, có thể thực hiện các xử lý khác tùy thuộc vào yêu cầu của bạn.
                        // Hiện tại, tôi chỉ đặt lblTienThua và lblTienThuaThieu về giá trị mặc định.
                        lblTienThua.setText("0.00");
                        lblTienThuaThieu.setText("Tiền thừa:");
                    }
                } catch (NumberFormatException ex) {
                    lblTienThua.setText("Nhập số hợp lệ");
                    lblTienThuaThieu.setText("");
                }
            }
        });
    }

    private void tuDongTimKH() {
        txtSDT.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                //bhd.selectKHbySDT(txtSDT.getText());
                if (null != bhdao.selectKHbySDT(txtSDT.getText())) {
                    txtTenKH.setText(bhdao.selectKHbySDT(txtSDT.getText()).getTenKh());
                    lblBaoLoiKH.setText("");
                } else {
                    txtTenKH.setText("");
                    lblBaoLoiKH.setText("Khách hàng không tồn tại!!");
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                // bhd.selectKHbySDT(txtSDT.getText());
                if (null != bhdao.selectKHbySDT(txtSDT.getText())) {
                    txtTenKH.setText(bhdao.selectKHbySDT(txtSDT.getText()).getTenKh());
                    lblBaoLoiKH.setText("");
                } else {
                    txtTenKH.setText("");
                    lblBaoLoiKH.setText("Khách hàng không tồn tại!!");
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Not used for plain text components
            }

        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupGioHang = new javax.swing.JPopupMenu();
        JMniSuaSL = new javax.swing.JMenuItem();
        JMniXoaSL = new javax.swing.JMenuItem();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        btnTaoHoaDon = new javax.swing.JButton();
        btnHuyHoaDon = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblGioHang = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtTim = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        btnTimKiem = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lblMaHD = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblTienThuaThieu = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cboHinhThucThanhToan = new javax.swing.JComboBox<>();
        lblTongTien = new javax.swing.JLabel();
        lblThanhToan = new javax.swing.JLabel();
        lblTienThua = new javax.swing.JLabel();
        txtTienKhachDua = new javax.swing.JTextField();
        btnLamMoi = new javax.swing.JButton();
        btnThanhToan = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        btnThemNhanhKhachHang = new javax.swing.JButton();
        lblBaoLoiKH = new javax.swing.JLabel();
        txtTenKH = new javax.swing.JTextField();

        JMniSuaSL.setText("Sửa ");
        JMniSuaSL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JMniSuaSLActionPerformed(evt);
            }
        });
        jPopupGioHang.add(JMniSuaSL);

        JMniXoaSL.setText("Xóa");
        JMniXoaSL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JMniXoaSLActionPerformed(evt);
            }
        });
        jPopupGioHang.add(JMniXoaSL);

        setBackground(new java.awt.Color(251, 227, 203));
        setPreferredSize(new java.awt.Dimension(1200, 650));

        jPanel2.setBackground(new java.awt.Color(251, 227, 203));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hóa đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã hóa đơn", "Ngày tạo", "Nhân viên", "Khách hàng", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblHoaDon);

        btnTaoHoaDon.setBackground(new java.awt.Color(0, 204, 51));
        btnTaoHoaDon.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnTaoHoaDon.setText("Tạo hoá đơn");
        btnTaoHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoHoaDonActionPerformed(evt);
            }
        });

        btnHuyHoaDon.setBackground(new java.awt.Color(0, 204, 51));
        btnHuyHoaDon.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnHuyHoaDon.setText("Hủy hóa đơn");
        btnHuyHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyHoaDonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 687, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnTaoHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnHuyHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(btnTaoHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(btnHuyHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(251, 227, 203));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Giỏ hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        tblGioHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sản phầm", "Tên sản phẩm", "Đơn giá", "Số lượng", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblGioHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGioHangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblGioHang);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(251, 227, 203));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sản Phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        jLabel1.setText("Tìm kiếm:");

        txtTim.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTimFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTimFocusLost(evt);
            }
        });

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Đơn giá", "Số lượng tồn", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblSanPham);

        btnTimKiem.setBackground(new java.awt.Color(0, 204, 51));
        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        btnReset.setBackground(new java.awt.Color(0, 204, 51));
        btnReset.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 829, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtTim, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnTimKiem)
                        .addGap(18, 18, 18)
                        .addComponent(btnReset)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtTim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiem)
                    .addComponent(btnReset))
                .addGap(13, 13, 13)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(251, 227, 203));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Thanh Toán", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel5.setMaximumSize(new java.awt.Dimension(357, 638));
        jPanel5.setPreferredSize(new java.awt.Dimension(683, 689));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Mã hóa đơn:");

        lblMaHD.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblMaHD.setText("Hóa đơn");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Tổng tiền:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Thanh toán:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Tiền khách đưa:");

        lblTienThuaThieu.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("Hình thức thanh toán:");

        cboHinhThucThanhToan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblTongTien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTongTien.setText("VND");

        lblThanhToan.setBackground(new java.awt.Color(0, 204, 0));
        lblThanhToan.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblThanhToan.setText("VND");

        lblTienThua.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTienThua.setText("VND");

        btnLamMoi.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnLamMoi.setText("Làm mới");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        btnThanhToan.setBackground(new java.awt.Color(0, 204, 51));
        btnThanhToan.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnThanhToan.setText("Thanh toán");
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        jSeparator1.setBackground(new java.awt.Color(102, 204, 255));

        jSeparator2.setBackground(new java.awt.Color(102, 204, 255));

        jSeparator3.setBackground(new java.awt.Color(102, 204, 255));

        jSeparator4.setBackground(new java.awt.Color(102, 204, 255));

        jPanel1.setBackground(new java.awt.Color(251, 227, 203));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Tên khách hàng:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("SĐT");

        txtSDT.setAutoscrolls(false);

        btnThemNhanhKhachHang.setBackground(new java.awt.Color(0, 204, 51));
        btnThemNhanhKhachHang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThemNhanhKhachHang.setText("Chọn");
        btnThemNhanhKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemNhanhKhachHangActionPerformed(evt);
            }
        });

        lblBaoLoiKH.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblBaoLoiKH.setForeground(new java.awt.Color(255, 51, 51));

        txtTenKH.setText("Khách bán lẻ");
        txtTenKH.setAutoscrolls(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTenKH)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnThemNhanhKhachHang))
                            .addComponent(lblBaoLoiKH, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 6, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnThemNhanhKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblBaoLoiKH, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(50, 50, 50)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTienThua)
                            .addComponent(txtTienKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTongTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator2)
                            .addComponent(lblThanhToan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator3)
                            .addComponent(lblMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTienThuaThieu)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel10)
                                            .addComponent(jLabel8))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnLamMoi)
                                            .addComponent(cboHinhThucThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(btnThanhToan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 9, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lblMaHD))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lblTongTien))
                .addGap(5, 5, 5)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblThanhToan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtTienKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTienThuaThieu)
                    .addComponent(lblTienThua))
                .addGap(5, 5, 5)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(cboHinhThucThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(btnLamMoi)
                .addGap(18, 18, 18)
                .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 605, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(36, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        // TODO add your handling code here:
        btnThanhToan.setEnabled(true);
        //txtTenKH.setText("Khách bán lẻ");
        int index = tblHoaDon.getSelectedRow();
        String id = tblHoaDon.getValueAt(index, 0).toString();
        loadDataToTableHDCT(bhdao.selectHDCT(id));
        lblMaHD.setText(id);
        tinhTongTien();
    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void btnTaoHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoHoaDonActionPerformed
        // TODO add your handling code here:
        int choice = JOptionPane.showConfirmDialog(this, "Bạn muốn tạo hoá đơn mới?", "TẠO HOÁ ĐƠN MỚI", JOptionPane.YES_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            addHDCho();
        }
    }//GEN-LAST:event_btnTaoHoaDonActionPerformed

    private void btnHuyHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyHoaDonActionPerformed
        // TODO add your handling code here:
        int index = tblHoaDon.getSelectedRow();

        if (index >= 0) {
            String maHD = tblHoaDon.getValueAt(index, 0).toString();
            int choice = JOptionPane.showConfirmDialog(this, "Bạn muốn huỷ hoá đơn: " + maHD + "?", "HUỶ HOÁ ĐƠN", JOptionPane.YES_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                huyHoaDonCho();
                clearFormThanhToan();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hoá đơn cần huỷ!!");
        }
    }//GEN-LAST:event_btnHuyHoaDonActionPerformed

    private void tblGioHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGioHangMouseClicked
        // TODO add your handling code here:
        jPopupGioHang.show(tblGioHang, evt.getX(), evt.getY());
    }//GEN-LAST:event_tblGioHangMouseClicked

    private void txtTimFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimFocusGained
        //        // TODO add your handling code here:
        //         if (txtTim.getText().equals("Tìm theo mã, tên")) {
        //            txtTim.setText(null);
        //            txtTim.requestFocus();
        //            RemovePleacehoderStyle(txtTim);
        //        }
    }//GEN-LAST:event_txtTimFocusGained

    private void txtTimFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimFocusLost
        //        // TODO add your handling code here:
        //         if (txtTim.getText().length() == 0) {
        //            AddPleacehoderStyle(txtTim);
        //            txtTim.setText("Tìm theo mã, tên");
        //
        //        }
    }//GEN-LAST:event_txtTimFocusLost

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        // TODO add your handling code here:
        int index = tblHoaDon.getSelectedRow();
        if (index < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hoá đơn trước khi thêm sản phẩm!!");
        } else {
            addHDCTPROC();
        }
    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
        //        testAuTo();
        String maSPct = txtTim.getText().trim();
        String tenSPct = txtTim.getText().trim();
        String trangThaict = txtTim.getText().trim();

        if (bhdao.timKiem(maSPct, tenSPct, trangThaict).isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy danh sách");
        } else {
            this.loadDataToTableSPCT(bhdao.timKiem(maSPct, tenSPct, trangThaict));
            this.txtTim.setText("");
            JOptionPane.showMessageDialog(this, "Danh sách được tìm thấy");
        }
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // TODO add your handling code here:
        clearFormThanhToan();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        try {
            if (tblHoaDon.getSelectedRow() >= 0) {
                if (tblGioHang.getRowCount() > 0) {
                    if (Double.valueOf(txtTienKhachDua.getText()) < Double.valueOf(lblTongTien.getText())) {
                        JOptionPane.showMessageDialog(this, "Tiền khách đưa thiếu");
                    } else {
                        if (!lblBaoLoiKH.getText().equalsIgnoreCase("Khách hàng không tồn tại!!")) {
                            int choice = JOptionPane.showConfirmDialog(this, "Bạn có muốn thanh toán hoá đơn này?", "THANH TOÁN", JOptionPane.YES_OPTION);
                            if (choice == JOptionPane.YES_OPTION) {
                                int maThanhToan = 1;
                                for (ThanhToan tt : ttDAO.selectAll()) {
                                    if (cboHinhThucThanhToan.getSelectedItem().toString().equalsIgnoreCase(tt.getHinhThucThanhToan())) {
                                        maThanhToan = tt.getThanhToan_id();
                                    }
                                }
                                String hoaDon_id = tblHoaDon.getValueAt(tblHoaDon.getSelectedRow(), 0).toString();
                                BigDecimal tongTien = BigDecimal.valueOf(Double.parseDouble(lblTongTien.getText()));
                                String maKH = "KH000";
                                String diaChi = ""; // Khởi tạo biến diaChi
                                model_khachhang kh = bhdao.selectKHbySDT(txtSDT.getText());
                                if (kh != null) {
                                    maKH = kh.getMaKH();
                                    diaChi = kh.getDiaChi(); // Lấy địa chỉ từ đối tượng KhachHang
                                }
                                if (bhdao.thanhToan(hoaDon_id, tongTien, maThanhToan, maKH, diaChi) > 0) {
                                    JOptionPane.showMessageDialog(this, "Thanh toán thành công!!");
                                    loadDataToTableHD(bhdao.selectHDCho());
                                    loadDataToTableHDCT(bhdao.selectHDCT(""));
                                    clearFormThanhToan();
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Khách hàng không tồn tại!!");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Hoá đơn chưa có sản phẩm");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Chọn hoá đơn trước khi thanh toán");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Tiền khách đưa không hợp lệ!!");
        }

    }//GEN-LAST:event_btnThanhToanActionPerformed

    private void btnThemNhanhKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemNhanhKhachHangActionPerformed

        BanHang_Views.sdtKH = txtSDT.getText();
        moThemNhanhKhachHang();
        System.out.println("MỜ thêm nhanh");
        chonKH(BanHang_Views.hoTenKH, BanHang_Views.sdtKH);
    }//GEN-LAST:event_btnThemNhanhKhachHangActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        txtTim.setText("");
        loadDataToTableSPCT(bhdao.selectSPCT());
    }//GEN-LAST:event_btnResetActionPerformed
    int soLuongSanPhamThayDoi = 0;

    private void JMniSuaSLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMniSuaSLActionPerformed
        if (checkSoLuongThayDoi()) {
            String hoaDon_id = lblMaHD.getText();
            String sPCT_id = tblGioHang.getValueAt(tblGioHang.getSelectedRow(), 0).toString();
            String hDCT_id = bhdao.selectHDCTByHD_idANDSPCT_id(hoaDon_id, sPCT_id).getMaSP(); // Sử dụng getMaSP() hoặc phương thức tương ứng

            int soLuongSanPhamThayDoi = Integer.parseInt(JOptionPane.showInputDialog("Nhập số lượng thay đổi:")); // Thay thế bằng cách lấy input chính xác
            int soLuongSanPhamTrongGio = Integer.parseInt(tblGioHang.getValueAt(tblGioHang.getSelectedRow(), 3).toString());

            // Gọi phương thức sửa số lượng với 4 tham số
            bhdao.suaSLSPTrongGio(hoaDon_id, hDCT_id, soLuongSanPhamThayDoi, soLuongSanPhamTrongGio);

            // Tải lại dữ liệu để cập nhật bảng
            loadDataToTableHDCT(bhdao.selectHDCT(hoaDon_id));
            loadDataToTableSPCT(bhdao.selectSPCT());
            tinhTongTien();

            System.out.println("HHIIHH chỉnh sửa nè");
        }

    }//GEN-LAST:event_JMniSuaSLActionPerformed

    private void JMniXoaSLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMniXoaSLActionPerformed
        doiTrangThaiHDCTPROC();
        tinhTongTien();
    }//GEN-LAST:event_JMniXoaSLActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem JMniSuaSL;
    private javax.swing.JMenuItem JMniXoaSL;
    private javax.swing.JButton btnHuyHoaDon;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnTaoHoaDon;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnThemNhanhKhachHang;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JComboBox<String> cboHinhThucThanhToan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPopupMenu jPopupGioHang;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel lblBaoLoiKH;
    private javax.swing.JLabel lblMaHD;
    private javax.swing.JLabel lblThanhToan;
    private javax.swing.JLabel lblTienThua;
    private javax.swing.JLabel lblTienThuaThieu;
    private javax.swing.JLabel lblTongTien;
    private javax.swing.JTable tblGioHang;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTienKhachDua;
    private javax.swing.JTextField txtTim;
    // End of variables declaration//GEN-END:variables
}
