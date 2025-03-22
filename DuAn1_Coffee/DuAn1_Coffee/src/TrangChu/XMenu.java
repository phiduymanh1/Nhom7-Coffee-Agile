/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TrangChu;

import Login.Server_Login;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import views.BanHang_Views;
import views.DoanhThu_Views;
import views.HoaDon_Views;
import views.KhachHang_Views;
import views.NhanVien_Views;
import views.SanPham_Views;

import views.Voucher_Views;

/**
 *
 * @author Hoàng Quân
 */
public class XMenu {
        private JPanel root;
    private String selected = "";

    private List<Menu> listItem = null;
    public XMenu(JPanel jpnRoot) {
        root = jpnRoot;
    }


    public void setEvent(List<Menu> list) {
        this.listItem = list;
        for (Menu menu : list) {
            menu.getJlb().addMouseListener(new LabelEvent(menu.getKind(), menu.getJpn(), menu.getJlb()));
        }
    }

    class LabelEvent implements MouseListener {
        private JPanel node;
        private String kind;
        private JPanel jpnItem;
        private JLabel jlbItem;

        public LabelEvent(String kind, JPanel jpnItem, JLabel jlbItem) {
            this.kind = kind;
            this.jpnItem = jpnItem;
            this.jlbItem = jlbItem;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            switch (kind) {
                case "BanHang":
                    node = new BanHang_Views();
                    setView();
                    break;
                case "NhanVien":
                    if (Server_Login.phanQuyen()) {
                        node = new NhanVien_Views();
                        setView();
                        break;
                    } else {
                        JOptionPane.showMessageDialog(root, "Bạn không có quyền Quản lý nhân viên!");
                        break;
                    }
                case "HoaDon":
                    node = new HoaDon_Views();
                    setView();
                    break;
                case "SanPham":
                    node = new SanPham_Views();
                    setView();
                    break;
                case "VouCher":
                    if (Server_Login.phanQuyen()) {
                        node = new Voucher_Views();
                        setView();
                        break;
                    } else {
                        JOptionPane.showMessageDialog(root, "Bạn không có quyền xem Voucher!");
                        break;
                    }
                case "ThongKe":
                    if (Server_Login.phanQuyen()) {
                        node = new DoanhThu_Views();
                        setView();
                        break;
                    } else {
                        JOptionPane.showMessageDialog(root, "Bạn không có quyền xem thống kê!");
                        break;
                    }

                case "KhachHang":
                    node = new KhachHang_Views();
                    setView();
                    break;
                default:
                    break;

            }

        }

        private void setView() {
            root.removeAll();
            root.setLayout(new BorderLayout());
            root.add(node);
            root.validate();
            root.repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

    }
}
