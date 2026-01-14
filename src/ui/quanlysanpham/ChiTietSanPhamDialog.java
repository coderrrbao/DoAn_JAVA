package ui.quanlysanpham;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import dto.SanPham;
import util.TaoUI;

public class ChiTietSanPhamDialog extends JDialog {

    private JTextField tfMaSanPham, tfTenSanPham, tfLoaiNuoc, tfNhaCungCap, 
                       tfGiaNhap, tfGiaBan, tfSoLuongTon, tfDungTich, 
                       tfHanSanXuat, tfHanSuDung;
    private JComboBox<String> cbTrangThai;
    private JTable tblSize;
    private DefaultTableModel modelSize;
    private JButton btnChonAnh, btnLuuThayDoi, btnSua;
    private JLabel lblAnh;

    public ChiTietSanPhamDialog(SanPham sanPham) {
        super((JFrame) null, "Chi tiết sản phẩm", true);

        this.setSize(400, 720);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel chitietPanel = TaoUI.taoPanelBoxLayoutDoc(400, 750);
        chitietPanel = TaoUI.suaBorderChoPanel(chitietPanel, 0, 10, 10, 10);
        
        lblAnh = TaoUI.taoJlabelAnh(sanPham.getAnh(), 200, 200);
        lblAnh.setAlignmentX(CENTER_ALIGNMENT);
        
        JPanel buttons = TaoUI.taoPanelCanGiua(400, 40);
        btnChonAnh = new JButton("Chọn ảnh");
        buttons.add(btnChonAnh);

        JPanel thongTin1 = TaoUI.taoPanelBoxLayoutNgang(400, 35);
        tfMaSanPham = new JTextField(sanPham.getMa());
        tfTenSanPham = new JTextField(sanPham.getTen());
        thongTin1.add(TaoUI.taoFieldText("Mã sản phẩm", 80, 80, 30, 5, tfMaSanPham));
        thongTin1.add(Box.createHorizontalGlue());
        thongTin1.add(TaoUI.taoFieldText("Tên sản phẩm", 80, 100, 30, 5, tfTenSanPham));

        JPanel thongTin2 = TaoUI.taoPanelBoxLayoutNgang(400, 35);
        tfLoaiNuoc = new JTextField(sanPham.getLoaiNuoc());
        tfNhaCungCap = new JTextField(sanPham.getNhaCungCap() != null ? sanPham.getNhaCungCap().getTen() : "");
        thongTin2.add(TaoUI.taoFieldText("Loại nước", 80, 80, 30, 5, tfLoaiNuoc));
        thongTin2.add(Box.createHorizontalGlue());
        thongTin2.add(TaoUI.taoFieldText("Nhà cung cấp", 80, 100, 30, 5, tfNhaCungCap));

        JPanel thongTin3 = TaoUI.taoPanelBoxLayoutNgang(400, 35);
        tfGiaNhap = new JTextField(String.valueOf(sanPham.getGiaNhap()));
        tfGiaBan = new JTextField(String.valueOf(sanPham.getGiaBan()));
        thongTin3.add(TaoUI.taoFieldText("Giá nhập", 80, 80, 30, 5, tfGiaNhap));
        thongTin3.add(Box.createHorizontalGlue());
        thongTin3.add(TaoUI.taoFieldText("Giá bán", 80, 100, 30, 5, tfGiaBan));

        JPanel thongTin4 = TaoUI.taoPanelBoxLayoutNgang(400, 35);
        tfSoLuongTon = new JTextField(String.valueOf(sanPham.getSoLuongTon()));
        tfDungTich = new JTextField(String.valueOf(sanPham.getDungTichMl()));
        thongTin4.add(TaoUI.taoFieldText("Số lượng tồn", 80, 80, 30, 5, tfSoLuongTon));
        thongTin4.add(Box.createHorizontalGlue());
        thongTin4.add(TaoUI.taoFieldText("Dung tích (ml)", 80, 100, 30, 5, tfDungTich));

        JPanel thongTin5 = TaoUI.taoPanelBoxLayoutNgang(400, 35);
        tfHanSanXuat = new JTextField(sanPham.getHanSanXuat() != null ? sanPham.getHanSanXuat().toString() : "");
        tfHanSuDung = new JTextField(sanPham.getHanSuDung() != null ? sanPham.getHanSuDung().toString() : "");
        thongTin5.add(TaoUI.taoFieldText("Hạn sản xuất", 80, 80, 30, 5, tfHanSanXuat));
        thongTin5.add(Box.createHorizontalGlue());
        thongTin5.add(TaoUI.taoFieldText("Hạn sử dụng", 80, 100, 30, 5, tfHanSuDung));

        JPanel thongTin6 = TaoUI.taoPanelBoxLayoutNgang(400, 35);
        thongTin6.add(new JLabel("Trạng thái: "));
        String[] loai = { "Còn hàng", "Hết hàng", "Ngừng kinh doanh" };
        cbTrangThai = new JComboBox<>(loai);
        cbTrangThai.setSelectedItem(sanPham.getTrangThai() ? "Còn hàng" : "Ngừng kinh doanh");
        cbTrangThai.setPreferredSize(new Dimension(150, 35));
        cbTrangThai.setMaximumSize(new Dimension(200, 35));
        thongTin6.add(cbTrangThai);

        JPanel thongTin7 = TaoUI.taoPanelBorderLayout(400, 150);
        JPanel titleThongTin7 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titleThongTin7.add(new JLabel("Bảng size"));
        thongTin7.add(titleThongTin7, BorderLayout.NORTH);
        
        modelSize = new DefaultTableModel();
        modelSize.addColumn("Mã Size");
        modelSize.addColumn("Tên size");
        modelSize.addColumn("Giá thêm(%)");
        modelSize.addColumn("Nguyên liệu thêm(%)");
        modelSize.addRow(new Object[] { "S", "Small (Nhỏ)", 0, 0 });
        modelSize.addRow(new Object[] { "M", "Medium (Vừa)", 10, 15 });
        modelSize.addRow(new Object[] { "L", "Large (Lớn)", 20, 25 });
        modelSize.addRow(new Object[] { "XL", "Extra Large", 35, 40 });
        
        tblSize = new JTable(modelSize);
        JScrollPane scrollPane = new JScrollPane(tblSize);
        thongTin7.add(scrollPane, BorderLayout.CENTER);

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
        chitietPanel.add(thongTin6);
        chitietPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        chitietPanel.add(thongTin7);
        chitietPanel.add(Box.createVerticalGlue());
        chitietPanel.add(pnlFooter);

        this.add(chitietPanel);
        this.setVisible(true);
    }
}