package ui.quanlysanpham;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import bus.SanPhamBUS;
import dto.SanPham;
import util.TaoUI;

public class ChiTietSanPhamDialog extends JDialog {

    private JTextField tfMaSanPham, tfTenSanPham, tfCanhBao, tfNhaCungCap,
            tfGiaNhap, tfGiaBan, tfSoLuongTon, tfDungTich;
    private JTable tblSize;
    private DefaultTableModel modelSize;
    private JButton btnChonAnh, btnLuuThayDoi, btnSua, btnXemCt;
    private JLabel lblAnh;
    private JFileChooser fileChooser;
    private JComboBox cbLoaiNuoc, cbDanhMuc;
    private SanPham sanPham;

    public ChiTietSanPhamDialog(SanPham sanPham) {
        super((JFrame) null, "Chi tiết sản phẩm", true);
        this.sanPham = sanPham;
        this.setSize(400, 680);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel chitietPanel = TaoUI.taoPanelBoxLayoutDoc(400, 750);
        chitietPanel = TaoUI.suaBorderChoPanel(chitietPanel, 0, 10, 10, 10);
        lblAnh = TaoUI.taoJlabelAnh(null, 200, 200);
        lblAnh.setAlignmentX(CENTER_ALIGNMENT);
        JPanel buttons = TaoUI.taoPanelCanGiua(400, 40);
        btnChonAnh = new JButton("Chọn ảnh");
        buttons.add(btnChonAnh);

        JPanel thongTin1 = TaoUI.taoPanelBoxLayoutNgang(400, 35);
        tfMaSanPham = new JTextField();
        tfTenSanPham = new JTextField();
        thongTin1.add(TaoUI.taoFieldText("Mã sản phẩm", 80, 80, 30, 5, tfMaSanPham));
        thongTin1.add(Box.createHorizontalGlue());
        thongTin1.add(TaoUI.taoFieldText("Tên sản phẩm", 80, 100, 30, 5, tfTenSanPham));

        JPanel thongTin2 = TaoUI.taoPanelBoxLayoutNgang(400, 35);
        tfCanhBao = new JTextField();
        tfNhaCungCap = new JTextField();
        thongTin2.add(TaoUI.taoFieldText("Cảnh báo", 80, 80, 30, 5, tfCanhBao));
        thongTin2.add(Box.createHorizontalGlue());
        thongTin2.add(TaoUI.taoFieldText("Nhà cung cấp", 80, 100, 30, 5, tfNhaCungCap));

        JPanel thongTin3 = TaoUI.taoPanelBoxLayoutNgang(400, 35);
        tfGiaNhap = new JTextField();
        tfGiaBan = new JTextField();
        thongTin3.add(TaoUI.taoFieldText("Giá nhập", 80, 80, 30, 5, tfGiaNhap));
        thongTin3.add(Box.createHorizontalGlue());
        thongTin3.add(TaoUI.taoFieldText("Giá bán", 80, 100, 30, 5, tfGiaBan));

        JPanel thongTin4 = TaoUI.taoPanelBoxLayoutNgang(400, 35);
        tfSoLuongTon = new JTextField();
        tfDungTich = new JTextField();
        thongTin4.add(TaoUI.taoFieldText("Số lượng tồn", 80, 80, 30, 5, tfSoLuongTon));
        thongTin4.add(Box.createHorizontalGlue());
        thongTin4.add(TaoUI.taoFieldText("Dung tích (ml)", 80, 100, 30, 5, tfDungTich));

        JPanel thongTin5 = TaoUI.taoPanelBoxLayoutNgang(400, 35);

        SanPhamBUS quanLySanPhamBUS = new SanPhamBUS();
        ArrayList<String> luaChonDanhMuc = quanLySanPhamBUS.layLuaChonDanhMuc();
        ArrayList<String> luaChonLoaiNuoc = new ArrayList<>();
        luaChonLoaiNuoc.add("Có sẵn");
        luaChonLoaiNuoc.add("Pha chế");
        cbDanhMuc = new JComboBox<>(luaChonDanhMuc.toArray());
        cbLoaiNuoc = new JComboBox<>(luaChonLoaiNuoc.toArray());
        thongTin5.add(new JLabel("Loại nước"));
        thongTin5.add(Box.createRigidArea(new Dimension(10, 0)));
        thongTin5.add(cbLoaiNuoc);
        thongTin5.add(Box.createHorizontalGlue());
        thongTin5.add(new JLabel("Danh mục"));
        thongTin5.add(Box.createRigidArea(new Dimension(15, 0)));
        thongTin5.add(cbDanhMuc);

        JPanel thongTin6 = TaoUI.taoPanelBorderLayout(400, 150);
        JPanel titleThongTin6 = TaoUI.taoPanelBoxLayoutNgang(400, 25);
        btnXemCt = new JButton("Xem công thức");
        thongTin6.add(btnXemCt);
        titleThongTin6.add(new JLabel("Bảng size"));
        titleThongTin6.add(Box.createHorizontalGlue());
        titleThongTin6.add(btnXemCt);

        thongTin6.add(titleThongTin6, BorderLayout.NORTH);

        modelSize = new DefaultTableModel();
        modelSize.addColumn("Mã Size");
        modelSize.addColumn("Tên size");
        modelSize.addColumn("Giá thêm(%)");
        modelSize.addColumn("Nguyên liệu thêm(%)");
        modelSize.addRow(new Object[] { "S", "Small (Nhỏ)", 0, 0 });
        modelSize.addRow(new Object[] { "M", "Medium (Vừa)", 10, 15 });
        modelSize.addRow(new Object[] { "L", "Large (Lớn)", 20, 25 });
        modelSize.addRow(new Object[] { "XL", "Extra Large", 35, 40 });

        JScrollPane scrollPane = TaoUI.taoTableScroll(modelSize);
        thongTin6.add(scrollPane, BorderLayout.CENTER);

        JPanel pnlFooter = TaoUI.taoPanelCanGiua(400, 50);

        btnSua = new JButton("Sửa");
        btnSua.setPreferredSize(new Dimension(100, 35));

        btnLuuThayDoi = new JButton("Lưu thay đổi");
        btnLuuThayDoi.setPreferredSize(new Dimension(150, 35));
        btnLuuThayDoi.setEnabled(false);

        pnlFooter.add(btnSua);
        pnlFooter.add(btnLuuThayDoi);

        chitietPanel.add(lblAnh);
        chitietPanel.add(buttons);
        chitietPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        chitietPanel.add(thongTin1);
        chitietPanel.add(thongTin2);
        chitietPanel.add(thongTin3);
        chitietPanel.add(thongTin4);
        chitietPanel.add(thongTin5);
        chitietPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        chitietPanel.add(thongTin6);
        chitietPanel.add(Box.createVerticalGlue());
        chitietPanel.add(pnlFooter);

        this.add(chitietPanel);

        capNhapDuLieu(sanPham);
        ganSuKien();
        this.repaint();
        this.setVisible(true);

    }

    private void capNhapDuLieu(SanPham sanPham) {
        if (sanPham == null) {
            return;
        }
        lblAnh.setIcon(TaoUI.taoImageIcon(sanPham.getAnh(), 200, 200));
        tfMaSanPham.setText(sanPham.getMaSP());
        tfTenSanPham.setText(sanPham.getTenSP());
        tfCanhBao.setText(sanPham.getLoaiNuoc());
        tfNhaCungCap.setText(sanPham.getNhaCungCap().getTenNCC());
        tfGiaNhap.setText(String.valueOf(sanPham.getGiaNhap()));
        tfGiaBan.setText(String.valueOf(sanPham.getGiaBan()));
        tfSoLuongTon.setText(String.valueOf(sanPham.getSoLuongTon()));
        tfDungTich.setText(String.valueOf(sanPham.getTheTich()));
    }

    private void ganSuKien() {
        btnChonAnh.addActionListener(e -> {
            fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Hình ảnh", "jpg", "png", "jpeg"));
            int result = fileChooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File fileAnh = fileChooser.getSelectedFile();
                ImageIcon icon = new ImageIcon(fileAnh.getAbsolutePath());
                lblAnh.setIcon(icon);
            }
        });
        btnXemCt.addActionListener(e -> {
            JDialog xemChiTietCt = new XemCongThucDialog(this,sanPham);
        });
    }
}