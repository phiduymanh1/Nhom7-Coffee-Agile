/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package views;

import model.HoaDon;
import model.HoaDonChiTiet;
import service.HoaDonChiTietDao;
import service.HoaDonDao;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author tranl
 */
public class HoaDon_Views extends javax.swing.JPanel {

    DefaultTableModel defaultTableModel = new DefaultTableModel();

    private HoaDonDao dao = new HoaDonDao();
    private HoaDonChiTietDao dao2 = new HoaDonChiTietDao();
    double soTrangLe;
    int soTrang;
    int tienLui = 0;
    int viTriTrang = 1;

    /**
     * Creates new form HoaDon_View
     */
    public HoaDon_Views() {
        initComponents();
        this.fillTableHoaDon(dao.phanTrangHoaDon(tienLui));

    }

    private void fillTableHoaDon(List<model.HoaDon> list) {
        DefaultTableModel model = (DefaultTableModel) tblHD.getModel();
        model.setRowCount(0);
        for (model.HoaDon hd : list) {
            model.addRow(hd.toDaTaRow());
        }
    }

    private void loadDataToTableHDCT(List<HoaDonChiTiet> lst) {
        DefaultTableModel model = (DefaultTableModel) tblHDCT.getModel();
        model.setRowCount(0);
        String trangThai = "";
        for (HoaDonChiTiet hdct : lst) {
            if (hdct.getTrangThai().equalsIgnoreCase("Hoàn thành")) {
                trangThai = "<html><font color='green'><b>Hoàn thành</b></font></html>";
            } else if (hdct.getTrangThai().equalsIgnoreCase("Chờ thanh toán")) {
                trangThai = "<html><font color='red'><b>Chờ thanh toán</b></font></html>";
            }

            model.addRow(new Object[]{
                hdct.getMaSP(),
                hdct.getTenSP(),
                hdct.getSoLuongMua(),
                hdct.getGiaTien(),
                hdct.getThanhTien(),
                //                hdct.getTrangThai(),
//                hdct.getGhiChu()
            });
        }
    }

//    public void showData(int index) {
//        HoaDon hd = dao.selectAll().get(index);
//        txtHoaDon.setText(hd.getMaHD());
//        cbo_TrangThai.setSelectedItem(hd.getTrangThai());
//    }

    private void setSoTrang() {
        soTrangLe = Math.ceil((double) dao.selectAll().size() / 5);
        soTrang = (int) soTrangLe;
        lblSoTrangHoaDon.setText(viTriTrang + "/" + soTrang);
    }

    private void next() {
        if (viTriTrang < soTrang) {
            viTriTrang++;
            tienLui += 5;
            fillTableHoaDon(dao.phanTrangHoaDon(tienLui));
            setSoTrang();
        } else {
            first();
        }
    }
// hàm này có chứuc năng lùi lại 1 trang

    private void preview() {
        if (viTriTrang > 1) {
            viTriTrang--;
            tienLui -= 5;
            fillTableHoaDon(dao.phanTrangHoaDon(tienLui));
            setSoTrang();
        } else {
            last();
        }
    }
// hàm này có chức năng trở về trang cuối cùng

    private void last() {
        tienLui = soTrang * 5 - 5;
        viTriTrang = soTrang;
        setSoTrang();
        fillTableHoaDon(dao.phanTrangHoaDon(tienLui));
    }
// hàm này có chức năng trở về trang đầu tiên

    private void first() {
        tienLui = 0;
        viTriTrang = 1;
        setSoTrang();
        fillTableHoaDon(dao.phanTrangHoaDon(tienLui));
    }

    private static void openPdfFile(String filePath) {
        try {
            File file = new File(filePath);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            } else {
                System.out.println("Desktop is not supported");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String removeDiacritics(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        input = input.replace("Đ", "D");
        input = input.replace("đ", "d");
        String decomposed = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(decomposed).replaceAll("");
    }

    private void generateQRCode(String data, String filePath) throws IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 120, 120, Map.of(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L));
            Path path = new File(filePath).toPath();
            BufferedImage image = new BufferedImage(120, 120, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < 120; x++) {
                for (int y = 0; y < 120; y++) {
                    image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }
            ImageIO.write(image, "PNG", path.toFile());
        } catch (WriterException e) {
            throw new IOException(e);
        }
    }

    private void xuatFileHoaDon() {
        if (tblHD.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Chọn hoá đơn cần xuất file!!");
        } else if (tblHD.getValueAt(tblHD.getSelectedRow(), 10).toString().equalsIgnoreCase("Đã huỷ")) {
            JOptionPane.showMessageDialog(this, "Hoá đơn " + tblHD.getValueAt(tblHD.getSelectedRow(), 10) + " không thể xuất!!");
        } else if (tblHDCT.getRowCount() > 0) {

            JFileChooser jfile = new JFileChooser();
            jfile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int x = jfile.showSaveDialog(this);
            if (x == JFileChooser.APPROVE_OPTION) {
                String path = jfile.getSelectedFile().getAbsolutePath();
                System.out.println(path);

                Document doc = new Document();
                try {
                    PdfWriter.getInstance(doc, new FileOutputStream(path + "/" + tblHD.getValueAt(tblHD.getSelectedRow(), 0).toString().trim() + ".pdf"));
                    String filePath = path + "/" + tblHD.getValueAt(tblHD.getSelectedRow(), 0).toString().trim() + ".pdf";
                    doc.open();// mở luồng ghi file
                    String hoaDon_id = tblHD.getValueAt(tblHD.getSelectedRow(), 0).toString();
                    String tenKhachHang = tblHD.getValueAt(tblHD.getSelectedRow(), 7).toString();
                    String ngayTaoHoaDon = tblHD.getValueAt(tblHD.getSelectedRow(), 4).toString();
                    String nguoiXuat = tblHD.getValueAt(tblHD.getSelectedRow(), 2).toString();

                    Date now = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String nowS = formatter.format(now);
                    // Thêm mã QR vào PDF
                    String qrCodeFilePath = "qrcode.png"; // Tệp mã QR tạm thời
                    String pdfLink = "" + filePath;
                    generateQRCode(pdfLink, qrCodeFilePath);
                    Image qrCodeImage = Image.getInstance(qrCodeFilePath);
                    qrCodeImage.setAbsolutePosition(350, 550); // Tọa độ của mã QR trong PDF
                    doc.add(qrCodeImage);
                    // Set tiêu đề cho hóa đơn
                    com.itextpdf.text.Font titleFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 32, com.itextpdf.text.Font.BOLD, BaseColor.BLACK);
                    Paragraph title = new Paragraph("HOA DON COFFEE BAN MINH CHO TU BAN", titleFont);
                    title.setAlignment(Paragraph.ALIGN_CENTER);
                    doc.add(title);
                    doc.add(new Paragraph("\n"));

                    // Tạo phông chữ cho các tiêu đề thông tin
                    com.itextpdf.text.Font infoFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 12, com.itextpdf.text.Font.NORMAL, BaseColor.DARK_GRAY);
                    doc.add(new Paragraph("Ma hoa don: " + hoaDon_id, infoFont));
                    doc.add(new Paragraph("Ten khach hang: " + removeDiacritics(tenKhachHang), infoFont));
                    doc.add(new Paragraph("Ngay tao hoa don: " + ngayTaoHoaDon, infoFont));
                    doc.add(new Paragraph("Ngay xuat hoa don: " + nowS, infoFont));
                    doc.add(new Paragraph("Nguoi tao hoa don: " + removeDiacritics(lblNguoiTao.getText()), infoFont));
                    doc.add(new Paragraph("Nguoi xuat hoa don: " + nguoiXuat, infoFont));
                    doc.add(new Paragraph("Tong tien cua hoa don: " + lblTongTien.getText(), infoFont));
                    doc.add(new Paragraph("\n    - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -"));
                    doc.add(new Paragraph("Danh sach san pham da mua"));
                    com.itextpdf.text.Font timesNewRoman = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 12, com.itextpdf.text.Font.BOLD, BaseColor.DARK_GRAY);
                    com.itextpdf.text.Font timesNewRoman2 = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 12, com.itextpdf.text.Font.NORMAL, BaseColor.DARK_GRAY);

                    // Tạo bảng
                    PdfPTable tbl = new PdfPTable(4);
                    tbl.setWidthPercentage(100);
                    tbl.setSpacingBefore(10f);
                    tbl.setSpacingAfter(10f);

                    // Tieu de bang
                    PdfPCell cell1 = new PdfPCell(new Phrase("Ten san pham", timesNewRoman));
                    cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    tbl.addCell(cell1);

                    PdfPCell cell2 = new PdfPCell(new Phrase("So luong", timesNewRoman));
                    cell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    tbl.addCell(cell2);

                    PdfPCell cell3 = new PdfPCell(new Phrase("Don gia", timesNewRoman));
                    cell3.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    tbl.addCell(cell3);

                    PdfPCell cell4 = new PdfPCell(new Phrase("Thanh Tien", timesNewRoman));
                    cell4.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    tbl.addCell(cell4);

//                    PdfPCell cell5 = new PdfPCell(new Phrase("Ghi chu", timesNewRoman));
//                    cell5.setBackgroundColor(BaseColor.LIGHT_GRAY);
//                    tbl.addCell(cell5);
                    //  Bang Du lieu san pham
                    for (int i = 0; i < tblHDCT.getRowCount(); i++) {
                        if (!tblHDCT.getValueAt(i, 2).toString().equals("0")) {
                            tbl.addCell(new PdfPCell(new Phrase(removeDiacritics(tblHDCT.getValueAt(i, 1).toString()), timesNewRoman2)));
                            tbl.addCell(new PdfPCell(new Phrase(removeDiacritics(tblHDCT.getValueAt(i, 2).toString()), timesNewRoman2)));
                            tbl.addCell(new PdfPCell(new Phrase(removeDiacritics(tblHDCT.getValueAt(i, 3).toString()), timesNewRoman2)));
                            tbl.addCell(new PdfPCell(new Phrase(removeDiacritics(tblHDCT.getValueAt(i, 4).toString()), timesNewRoman2)));
//                            tbl.addCell(new PdfPCell(new Phrase(removeDiacritics(tblHDCT.getValueAt(i, 5).toString()), timesNewRoman2)));

                        }

                    }

                    PdfPCell totalCell = new PdfPCell(new Phrase("Thanh tien: " + lblTongTien.getText(), timesNewRoman));
                    totalCell.setColspan(8); // Đặt colspan bằng số lượng cột của bảng
                    totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT); // Căn chỉnh về bên phải
                    tbl.addCell(totalCell);
                    doc.add(tbl);

                    com.itextpdf.text.Font titleFont2 = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 20, com.itextpdf.text.Font.BOLD, BaseColor.BLACK);
                    com.itextpdf.text.Font hanTraFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 12, com.itextpdf.text.Font.BOLD, BaseColor.BLACK);
                    doc.add(new Paragraph("\n    - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -"));

//                    Paragraph hanTra = new Paragraph("Han tra hang 2 ngay sau khi thanh toan hoa don", hanTraFont);
//                    doc.add(hanTra);
//
//                        Paragraph title2 = new Paragraph("CHUC QUY KHACH MUA SAM VUI VE!!!!", titleFont2);
//                    doc.add(title2);
//                    doc.add(new Paragraph("\n"));
                    //Tạo footer bằng bảng
                    PdfPTable footer = new PdfPTable(1);
                    footer.setWidthPercentage(100);
                    footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                    doc.add(new Paragraph("\n"));

                    com.itextpdf.text.Font footerFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 12, com.itextpdf.text.Font.BOLD, BaseColor.BLACK);

                    // Thêm nội dung footer với font Times New Roman
                    PdfPCell footerCell = new PdfPCell(new Phrase("             ----------------------------- CAM ON QUY KHACH. HEN GAP LAI ! ---------------------------", footerFont));
                    footer.addCell(footerCell);
                    doc.add(footer);
                    doc.close();
                    System.out.println("Xuất hoá đơn bán lẻ nè hhihi");
                    int choice = JOptionPane.showConfirmDialog(this, "Xem hoá đơn vừa tạo?", "XEM HOÁ ĐƠN", JOptionPane.YES_OPTION);
                    if (choice == JOptionPane.YES_OPTION) {
                        openPdfFile(filePath);
                    }
                    JOptionPane.showMessageDialog(this, "File PDF đã được xuất thành công!");

                } catch (DocumentException | IOException ex) {
                    Logger.getLogger(HoaDon_Views.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi xuất file PDF!");
                } finally {
                    doc.close();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Hoá đơn đã trả hết hàng!!");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblHDCT = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        lblMaHD = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        lblNguoiTao = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        lblNguoiMua = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        lblTongTien = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        lblNgayTao = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        jLabel14 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        lblTrangThai = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        txtHoaDon = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHD = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        btnFirst = new javax.swing.JButton();
        btnPreview = new javax.swing.JButton();
        lblSoTrangHoaDon = new javax.swing.JLabel();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setBackground(new java.awt.Color(251, 227, 203));
        setPreferredSize(new java.awt.Dimension(1200, 650));

        jPanel4.setBackground(new java.awt.Color(251, 227, 203));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Hóa Đơn Chi Tiết"));

        tblHDCT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sản phầm", "Tên sản phẩm", "Số lượng", "Gia tiền", "Thanh tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHDCT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHDCTMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblHDCT);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1166, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 16, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(251, 227, 203));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Mã HĐ:");

        lblMaHD.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Người tạo:");

        lblNguoiTao.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        lblNguoiMua.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Người mua:");

        lblTongTien.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("Tổng tiền:");

        lblNgayTao.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("Ngày tạo:");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("Đia chỉ:");

        txtGhiChu.setColumns(20);
        txtGhiChu.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtGhiChu.setRows(5);
        jScrollPane3.setViewportView(txtGhiChu);

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("Trạng thái:");

        lblTrangThai.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblTrangThai.setForeground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel14)
                                .addGap(18, 18, 18)
                                .addComponent(lblTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(51, 51, 51))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lblNguoiTao, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lblNguoiMua))
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                            .addComponent(jLabel12)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(lblNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                            .addComponent(jLabel11)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                                            .addComponent(lblTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.LEADING))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                            .addComponent(jLabel13)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jSeparator5)
                                        .addComponent(jSeparator6)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel8))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(4, 4, 4)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(lblNguoiTao))
                .addGap(1, 1, 1)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(lblNguoiMua))
                .addGap(8, 8, 8)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(lblTongTien))
                .addGap(1, 1, 1)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(lblNgayTao))
                .addGap(1, 1, 1)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(lblTrangThai))
                .addGap(1, 1, 1)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(46, 46, 46))
        );

        jPanel2.setBackground(new java.awt.Color(251, 227, 203));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        txtHoaDon.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtHoaDonFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtHoaDonFocusLost(evt);
            }
        });
        txtHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHoaDonActionPerformed(evt);
            }
        });

        btnTimKiem.setText("Tim Kiem");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnTimKiem)
                .addGap(245, 245, 245))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã HD", "Mã NV", "Tên NV", "SDT", "Ngay tạo", "Tổng tiền", "Ma KH", "Ten KH", "Voucher", "Địa chỉ", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHDMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblHD);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Lọc hóa đơn");

        btnFirst.setText("|<");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnPreview.setText("<");
        btnPreview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviewActionPerformed(evt);
            }
        });

        lblSoTrangHoaDon.setText("Số trang HD");

        btnNext.setText(">");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLast.setText(">|");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(0, 204, 51));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Xuất file hoá đơn");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(211, 211, 211)
                .addComponent(btnFirst)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPreview)
                .addGap(18, 18, 18)
                .addComponent(lblSoTrangHoaDon)
                .addGap(18, 18, 18)
                .addComponent(btnNext)
                .addGap(18, 18, 18)
                .addComponent(btnLast)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(19, 19, 19))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(2, 2, 2)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFirst)
                    .addComponent(btnPreview)
                    .addComponent(lblSoTrangHoaDon)
                    .addComponent(btnNext)
                    .addComponent(btnLast)
                    .addComponent(jButton1))
                .addGap(50, 50, 50))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 360, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(66, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblHDCTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHDCTMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblHDCTMouseClicked

    private void txtHoaDonFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHoaDonFocusGained

    }//GEN-LAST:event_txtHoaDonFocusGained

    private void txtHoaDonFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHoaDonFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHoaDonFocusLost

    private void txtHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHoaDonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHoaDonActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        String searchQuery = txtHoaDon.getText().trim();

        // Kiểm tra tính hợp lệ đầu vào
        if (searchQuery.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin tìm kiếm!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Tìm kiếm hóa đơn
        List<HoaDon> result = dao.findPhanTrangHoaDon(searchQuery);

        if (result.isEmpty()) {
            // Nếu không tìm thấy kết quả
            JOptionPane.showMessageDialog(this, "Không tìm thấy hóa đơn nào!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Cập nhật bảng kết quả tìm kiếm
            fillTableHoaDon(result);
        }
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void tblHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHDMouseClicked
        // TODO add your handling code here:
        String trangThai = tblHD.getValueAt(tblHD.getSelectedRow(), 10).toString();
        if (trangThai.equalsIgnoreCase("Đã huỷ")) {
            trangThai = "<html><font color='red'>Đã huỷ</font></html>";
        } else if (trangThai.equalsIgnoreCase("Hoàn thành")) {
            trangThai = "<html><font color='green'>Hoàn thành</font></html>";
        }
        String hoaDon_id = tblHD.getValueAt(tblHD.getSelectedRow(), 0).toString();
        loadDataToTableHDCT(dao2.selectHDCT(hoaDon_id));
        lblMaHD.setText(tblHD.getValueAt(tblHD.getSelectedRow(), 0).toString());
        lblNgayTao.setText(tblHD.getValueAt(tblHD.getSelectedRow(), 4).toString());
        lblNguoiMua.setText(tblHD.getValueAt(tblHD.getSelectedRow(), 7).toString());
        lblNguoiTao.setText(tblHD.getValueAt(tblHD.getSelectedRow(), 2).toString());
        lblTongTien.setText(tblHD.getValueAt(tblHD.getSelectedRow(), 5).toString() == null ? "0.00" : tblHD.getValueAt(tblHD.getSelectedRow(), 5).toString());
        //   lblTrangThai.setText(tblHD.getValueAt(tblHD.getSelectedRow(), 8).toString());
        lblTrangThai.setText(trangThai);
        if (tblHD.getValueAt(tblHD.getSelectedRow(), 9) != null) {
            txtGhiChu.setText(tblHD.getValueAt(tblHD.getSelectedRow(), 9).toString());
        } else {
            txtGhiChu.setText("");
        }
    }//GEN-LAST:event_tblHDMouseClicked

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        // TODO add your handling code here:
        first();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPreviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviewActionPerformed
        preview();
    }//GEN-LAST:event_btnPreviewActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        next();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        // TODO add your handling code here:
        last();
    }//GEN-LAST:event_btnLastActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        this.xuatFileHoaDon();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPreview;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JLabel lblMaHD;
    private javax.swing.JLabel lblNgayTao;
    private javax.swing.JLabel lblNguoiMua;
    private javax.swing.JLabel lblNguoiTao;
    private javax.swing.JLabel lblSoTrangHoaDon;
    private javax.swing.JLabel lblTongTien;
    private javax.swing.JLabel lblTrangThai;
    private javax.swing.JTable tblHD;
    private javax.swing.JTable tblHDCT;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtHoaDon;
    // End of variables declaration//GEN-END:variables
}
