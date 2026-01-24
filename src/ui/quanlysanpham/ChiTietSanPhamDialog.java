package ui.quanlysanpham;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import bus.DanhMucBUS;
import bus.NhaCungCapBus;
import bus.SanPhamBUS;
import bus.SizeBUS;
import dto.DanhMuc;
import dto.NhaCungCap;
import dto.SanPham;
import dto.Size;
import util.TaoUI;

public class ChiTietSanPhamDialog extends JDialog {

    private JTextField tfMaSanPham, tfTenSanPham, tfCanhBao, tfNhaCungCap,
            tfGiaNhap, tfGiaBan, tfSoLuongTon, tfDungTich;
    private JTable tblSize;
    private DefaultTableModel modelSize;
    private JButton btnChonAnh, btnLuuThayDoi, btnSua, btnXemCongThuc, btnXoaSize, btnThemSize, btnSuaSize, btnThemSp,
            btnLamMoi;
    private JLabel lblAnh;
    private JFileChooser fileChooser;
    private JComboBox cbLoaiNuoc, cbDanhMuc, cbTrangThaiXuLy;
    private SanPham sanPham;

    private DanhMucBUS danhMucBUS = new DanhMucBUS();
    private NhaCungCapBus nhaCungCapBUS = new NhaCungCapBus();

    private XemCongThucDialog xemCongThucDialog = new XemCongThucDialog(null, sanPham);
    private QuanLySanPhamUI quanLySanPhamUI;

    public ChiTietSanPhamDialog(SanPham sanPham,QuanLySanPhamUI quanLySanPhamUI) {
        super((JFrame) null, "Chi tiết sản phẩm", true);
        this.quanLySanPhamUI = quanLySanPhamUI;
        this.sanPham = sanPham;
        this.setSize(400, 700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel chitietPanel = TaoUI.taoPanelBoxLayoutDoc(400, 750);
        chitietPanel = TaoUI.suaBorderChoPanel(chitietPanel, 0, 10, 10, 10);
        lblAnh = TaoUI.taoJlabelAnh(null, 200, 200);
        lblAnh.setAlignmentX(CENTER_ALIGNMENT);
        JPanel buttons = TaoUI.taoPanelCanGiua(400, 25);
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
        luaChonDanhMuc.add(0, "-- Danh mục --");
        ArrayList<String> luaChonLoaiNuoc = new ArrayList<>();
        luaChonLoaiNuoc.add("-- Loại nước --");
        luaChonLoaiNuoc.add("Có sẵn");
        luaChonLoaiNuoc.add("Pha chế");
        cbDanhMuc = new JComboBox<>(luaChonDanhMuc.toArray());
        cbDanhMuc.setFont(cbDanhMuc.getFont().deriveFont(11.0f));
        TaoUI.setFixSize(cbDanhMuc, 100, 35);
        cbLoaiNuoc = new JComboBox<>(luaChonLoaiNuoc.toArray());
        cbLoaiNuoc.setFont(cbLoaiNuoc.getFont().deriveFont(11.0f));
        thongTin5.add(new JLabel("Loại nước"));
        thongTin5.add(Box.createRigidArea(new Dimension(10, 0)));
        thongTin5.add(cbLoaiNuoc);
        thongTin5.add(Box.createHorizontalGlue());
        thongTin5.add(new JLabel("Danh mục"));
        thongTin5.add(Box.createRigidArea(new Dimension(15, 0)));
        thongTin5.add(cbDanhMuc);

        JPanel thongTin5_1 = TaoUI.taoPanelBoxLayoutNgang(400, 35);
        ArrayList<String> luaChonTrangThaiXuLy = new ArrayList<>();
        luaChonTrangThaiXuLy.add("-- Tất cả --");
        luaChonTrangThaiXuLy.add("Đã xác nhận");
        luaChonTrangThaiXuLy.add("Đang xử lý");
        luaChonTrangThaiXuLy.add("Ẩn");
        cbTrangThaiXuLy = new JComboBox<>(luaChonTrangThaiXuLy.toArray());
        cbTrangThaiXuLy.setFont(cbTrangThaiXuLy.getFont().deriveFont(11.0f));
        thongTin5_1.add(new JLabel("Trạng thái"));
        thongTin5_1.add(Box.createRigidArea(new Dimension(10, 0)));
        thongTin5_1.add(cbTrangThaiXuLy);
        thongTin5_1.add(Box.createHorizontalGlue());

        JPanel pnlFooter = TaoUI.taoPanelCanGiua(400, 30);

        btnSua = new JButton("Sửa");
        btnSua.setPreferredSize(new Dimension(100, 35));

        btnLuuThayDoi = new JButton("Lưu thay đổi");
        btnLuuThayDoi.setPreferredSize(new Dimension(150, 35));
        btnLuuThayDoi.setEnabled(false);

        pnlFooter.add(btnSua);
        pnlFooter.add(btnLuuThayDoi);

        JPanel taoSanPhamPanel = TaoUI.taoPanelCanGiua(400, 30);
        btnThemSp = new JButton("Thêm");
        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setPreferredSize(new Dimension(150, 35));
        btnThemSp.setPreferredSize(new Dimension(150, 35));

        taoSanPhamPanel.add(btnThemSp);
        taoSanPhamPanel.add(btnLamMoi);

        chitietPanel.add(lblAnh);
        chitietPanel.add(buttons);
        chitietPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        chitietPanel.add(thongTin1);
        chitietPanel.add(thongTin2);
        chitietPanel.add(thongTin3);
        chitietPanel.add(thongTin4);
        chitietPanel.add(Box.createRigidArea(new Dimension(0, 3)));
        chitietPanel.add(thongTin5);
        chitietPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        if (sanPham != null) {
            chitietPanel.add(thongTin5_1);
        }

        chitietPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        if (sanPham == null || sanPham.getLoaiNuoc().equals("Pha chế")) {
            JPanel thongTin6 = TaoUI.taoPanelBorderLayout(400, 150);
            JPanel titleThongTin6 = TaoUI.taoPanelBoxLayoutNgang(400, 25);
            btnXemCongThuc = new JButton("Xem công thức");
            btnXemCongThuc.addActionListener(e -> {
                xemCongThucDialog.setVisible(true);
            });
            thongTin6.add(btnXemCongThuc);
            titleThongTin6.add(new JLabel("Bảng size"));
            titleThongTin6.add(Box.createHorizontalGlue());
            btnXoaSize = new JButton("Xóa");
            btnThemSize = new JButton("Thêm");
            btnSuaSize = new JButton("Sửa");
            TaoUI.setFixSize(btnSuaSize, 45, 20);
            TaoUI.setFixSize(btnXoaSize, 45, 20);
            TaoUI.setFixSize(btnThemSize, 45, 20);
            TaoUI.setFixSize(btnXemCongThuc, 100, 20);
            Font commonFont = new Font("Segoe UI", Font.BOLD, 12);
            btnThemSize.setFont(commonFont);
            btnSuaSize.setFont(commonFont);
            btnXoaSize.setFont(commonFont);
            btnXemCongThuc.setFont(commonFont);
            btnSuaSize.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
            btnXoaSize.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
            btnThemSize.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
            btnXemCongThuc.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));

            titleThongTin6.add(btnThemSize);
            titleThongTin6.add(Box.createRigidArea(new Dimension(3, 0)));
            titleThongTin6.add(btnXoaSize);
            titleThongTin6.add(Box.createRigidArea(new Dimension(3, 0)));
            titleThongTin6.add(btnSuaSize);
            titleThongTin6.add(Box.createRigidArea(new Dimension(3, 0)));
            titleThongTin6.add(btnXemCongThuc);

            thongTin6.add(titleThongTin6, BorderLayout.NORTH);

            modelSize = new DefaultTableModel();
            modelSize.addColumn("Mã Size");
            modelSize.addColumn("Tên size");
            modelSize.addColumn("Giá thêm(%)");
            modelSize.addColumn("Nguyên liệu thêm(%)");

            JScrollPane scrollPaneSize = TaoUI.taoTableScroll(modelSize);
            thongTin6.add(scrollPaneSize, BorderLayout.CENTER);
            chitietPanel.add(thongTin6);
        }

        chitietPanel.add(Box.createVerticalGlue());
        if (sanPham != null) {
            chitietPanel.add(pnlFooter);
        } else {
            chitietPanel.add(taoSanPhamPanel);
        }

        this.add(chitietPanel);

        capNhapDuLieu(sanPham);
        ganSuKien();
        this.repaint();
        this.setVisible(true);

    }

    private void capNhapDuLieu(SanPham sanPham) {
        if (sanPham == null) {
            tfMaSanPham.setText("");
            tfTenSanPham.setText("");
            tfCanhBao.setText("");
            tfNhaCungCap.setText("");
            tfGiaNhap.setText("");
            tfGiaBan.setText("");
            tfSoLuongTon.setText("");
            tfDungTich.setText("");
            lblAnh.setIcon(TaoUI.taoImageIcon("../assets/img/douongmd.png", 200, 200));
            modelSize.setRowCount(0);
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

        if (sanPham.getListSize() != null) {
            for (Size size : sanPham.getListSize()) {
                modelSize.addRow(
                        new Object[] { size.getMaSize(), size.getTenSize(), size.getPhanTramGia(),
                                size.getPhanTramNL() });
            }
        }

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


        btnLamMoi.addActionListener(e -> {
            capNhapDuLieu(null);
            this.repaint();
        });

        btnSua.addActionListener(e -> {
            tfMaSanPham.setEditable(false);
            tfTenSanPham.setEditable(true);
            tfCanhBao.setEditable(true);
            tfNhaCungCap.setEditable(true);
            tfGiaNhap.setEditable(true);
            tfGiaBan.setEditable(true);
            tfSoLuongTon.setEditable(true);
            tfDungTich.setEditable(true);
            cbDanhMuc.setEnabled(true);
            cbLoaiNuoc.setEnabled(true);
            btnLuuThayDoi.setEnabled(true);
            btnSua.setEnabled(false);
            btnChonAnh.setEnabled(true);
        });

        btnLuuThayDoi.addActionListener(e -> {
            if (kiemTraDuLieu()) {
                dongGoiSanPham();
                SanPhamBUS sanPhamBUS = new SanPhamBUS();
                if (sanPhamBUS.capNhapSanPham(sanPham)) {
                    JOptionPane.showMessageDialog(this, "Cập nhật sản phẩm thành công!", "Thành công",
                            JOptionPane.INFORMATION_MESSAGE);
                    tfMaSanPham.setEditable(false);
                    tfTenSanPham.setEditable(false);
                    tfCanhBao.setEditable(false);
                    tfNhaCungCap.setEditable(false);
                    tfGiaNhap.setEditable(false);
                    tfGiaBan.setEditable(false);
                    tfSoLuongTon.setEditable(false);
                    tfDungTich.setEditable(false);
                    cbDanhMuc.setEnabled(false);
                    cbLoaiNuoc.setEnabled(false);
                    btnLuuThayDoi.setEnabled(false);
                    btnSua.setEnabled(true);
                    btnChonAnh.setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật sản phẩm thất bại!", "Thất bại",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnThemSp.addActionListener(e -> {
            if (kiemTraDuLieu()) {
                SanPham sanPham = dongGoiSanPham();
                SanPhamBUS sanPhamBUS = new SanPhamBUS();
                if (sanPhamBUS.themSanPham(sanPham)) {
                    JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công!", "Thành công",
                            JOptionPane.INFORMATION_MESSAGE);
                    capNhapDuLieu(null);
                    this.repaint();
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm sản phẩm thất bại!", "Thất bại",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnThemSize.addActionListener(e -> {
            if (sanPham != null) {
                // new ThemSizeDialog(this, sanPham);
                capNhapDuLieu(sanPham);
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng lưu sản phẩm trước khi thêm size!", "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        btnSuaSize.addActionListener(e -> {
            int row = tblSize.getSelectedRow();
            if (row >= 0) {
                String maSize = (String) modelSize.getValueAt(row, 0);
                if (sanPham.getListSize() != null) {
                    for (Size size : sanPham.getListSize()) {
                        if (size.getMaSize().equals(maSize)) {
                            // new SuaSizeDialog(this, size, sanPham);
                            capNhapDuLieu(sanPham);
                            break;
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn size để sửa!", "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        btnXoaSize.addActionListener(e -> {
            int row = tblSize.getSelectedRow();
            if (row >= 0) {
                String maSize = (String) modelSize.getValueAt(row, 0);
                if (sanPham.getListSize() != null) {
                    for (Size size : sanPham.getListSize()) {
                        if (size.getMaSize().equals(maSize)) {
                            SizeBUS sizeBUS = new SizeBUS();
                            // if (sizeBUS.XoaSanPham(maSize)) {
                            //     sanPham.getListSize().remove(size);
                            //     modelSize.removeRow(row);
                            //     JOptionPane.showMessageDialog(this, "Xóa size thành công!", "Thành công",
                            //             JOptionPane.INFORMATION_MESSAGE);
                            // } else {
                            //     JOptionPane.showMessageDialog(this, "Xóa size thất bại!", "Thất bại",
                            //             JOptionPane.ERROR_MESSAGE);
                            // }
                            // break;
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn size để xóa!", "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        if (sanPham != null) {
            tfMaSanPham.setEditable(false);
            tfTenSanPham.setEditable(false);
            tfCanhBao.setEditable(false);
            tfNhaCungCap.setEditable(false);
            tfGiaNhap.setEditable(false);
            tfGiaBan.setEditable(false);
            tfSoLuongTon.setEditable(false);
            tfDungTich.setEditable(false);
            cbDanhMuc.setEnabled(false);
            cbLoaiNuoc.setEnabled(false);
            btnChonAnh.setEnabled(false);
            tblSize = new JTable(modelSize);
        }
    }

    private boolean kiemTraDuLieu() {
        if (tfTenSanPham.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cbDanhMuc.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn danh mục!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cbLoaiNuoc.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn loại nước!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            Long.parseLong(tfGiaNhap.getText());
            Long.parseLong(tfGiaBan.getText());
            Long.parseLong(tfDungTich.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Giá nhập, giá bán, dung tích phải là số!", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public SanPham dongGoiSanPham() {
        xemCongThucDialog.dongGoiCongThuc();
        SanPham sp = new SanPham();
        sp.setMaSP(tfMaSanPham.getText());
        sp.setTenSP(tfTenSanPham.getText());
        sp.setLoaiNuoc((String) cbLoaiNuoc.getSelectedItem());
        DanhMuc danhMuc = danhMucBUS.timDanhMuc((String) cbDanhMuc.getSelectedItem());
        sp.setDanhMuc(danhMuc);
        sp.setGiaBan(Long.parseLong(tfGiaBan.getText()));
        sp.setGiaNhap(Long.parseLong(tfGiaNhap.getText()));
        sp.setTheTich(Integer.parseInt(tfDungTich.getText()));
        sp.setMucCanhBao(Integer.parseInt(tfCanhBao.getText()));
        NhaCungCap ncc = nhaCungCapBUS.timNhaCungCapTheoTen((String) tfNhaCungCap.getText());
        sp.setNhaCungCap(ncc);
        sp.setCongThuc(xemCongThucDialog.getCongThuc());
        sp.setListSize(dongGoiListSize());
        sp.setTrangThaiXuLy("Chờ xử lý");

        if (lblAnh.getIcon() != null) {
            luuAnh();
            sp.setAnh(tfMaSanPham.getText() + ".png");
        }

        this.sanPham = sp;
        return sanPham;
    }

    private ArrayList<Size> dongGoiListSize() {
        ArrayList<Size> listSize = new ArrayList<>();
        for (int i = 0; i < modelSize.getRowCount(); i++) {
            Size size = new Size(modelSize.getValueAt(i, 0).toString(), modelSize.getValueAt(i, 1).toString(),
                    modelSize.getValueAt(i, 2).toString(),
                    Integer.parseInt(modelSize.getValueAt(i, 3).toString()),
                    Integer.parseInt(modelSize.getValueAt(i, 5).toString()));
            listSize.add(size);
        }
        return listSize;
    }

    private void luuAnh() {
    }
}