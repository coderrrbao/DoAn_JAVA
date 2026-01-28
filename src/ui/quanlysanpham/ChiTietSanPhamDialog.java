package ui.quanlysanpham;

import java.io.File;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import bus.DanhMucBUS;
import bus.NhaCungCapBUS;
import bus.SanPhamBUS;
import bus.SizeBUS;
import dto.ChiTietCongThuc;
import dto.CongThuc;
import dto.DanhMuc;
import dto.NhaCungCap;
import dto.SanPham;
import dto.Size;
import util.TaoUI;

public class ChiTietSanPhamDialog extends JDialog {

    private JTextField tfMaSanPham, tfTenSanPham, tfCanhBao, tfGiaBan, tfDungTich;
    private JTable tblSize;
    private DefaultTableModel modelSize;
    private JButton btnChonAnh, btnLuuThayDoi, btnSua, btnXemCongThuc, btnXoaSize, btnThemSize, btnSuaSize, btnThemSp,
            btnLamMoi;
    private JLabel lblAnh;
    private JFileChooser fileChooser;
    private JComboBox cbLoaiNuoc, cbDanhMuc, cbTrangThaiXuLy, cbNhaCungCap;
    private SanPham sanPham;

    private DanhMucBUS danhMucBUS = new DanhMucBUS();
    private NhaCungCapBUS nhaCungCapBUS = new NhaCungCapBUS();

    private XemCongThucDialog xemCongThucDialog;
    private QuanLySanPhamUI quanLySanPhamUI;

    private JPanel formCongThuc;

    public ChiTietSanPhamDialog(SanPham sanPham, QuanLySanPhamUI quanLySanPhamUI) {
        super((JFrame) null, "Chi tiết sản phẩm", true);
        this.quanLySanPhamUI = quanLySanPhamUI;
        this.sanPham = sanPham;

        this.setSize(400, 700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        xemCongThucDialog = new XemCongThucDialog(this, sanPham);
        lblAnh = TaoUI.taoJlabelAnh(null, 200, 200);
        lblAnh.setAlignmentX(CENTER_ALIGNMENT);

        btnChonAnh = new JButton("Chọn ảnh");

        tfMaSanPham = new JTextField();
        tfTenSanPham = new JTextField();
        tfCanhBao = new JTextField();
        tfDungTich = new JTextField();
        tfGiaBan = new JTextField();

        cbLoaiNuoc = new JComboBox<>();
        cbDanhMuc = new JComboBox<>();
        cbTrangThaiXuLy = new JComboBox<>();

        btnLuuThayDoi = new JButton("Lưu");
        btnSua = new JButton("Sửa");
        btnXemCongThuc = new JButton("Xem công thức");
        btnXoaSize = new JButton("Xóa");
        btnThemSize = new JButton("Thêm");
        btnSuaSize = new JButton("Sửa");
        btnThemSp = new JButton("Thêm");
        btnLamMoi = new JButton("Làm mới");

        modelSize = new DefaultTableModel();
        fileChooser = new JFileChooser();

        initGUI();
        ganSuKien();
        settupGiaoDien(sanPham);

    }

    private void initGUI() {
        JPanel chitietPanel = TaoUI.taoPanelBoxLayoutDoc(400, 750);
        chitietPanel = TaoUI.suaBorderChoPanel(chitietPanel, 0, 10, 10, 10);

        JPanel buttons = TaoUI.taoPanelCanGiua(400, 25);
        buttons.add(btnChonAnh);

        JPanel thongTin1 = TaoUI.taoPanelBoxLayoutNgang(400, 35);

        if (sanPham != null) {
            thongTin1.add(TaoUI.taoFieldText("Mã sản phẩm", 80, 80, 30, 5, tfMaSanPham));

        }
        thongTin1.add(Box.createHorizontalGlue());
        thongTin1.add(TaoUI.taoFieldText("Tên sản phẩm", 80, 100, 30, 5, tfTenSanPham));

        JPanel thongTin2 = TaoUI.taoPanelBoxLayoutNgang(400, 35);
        thongTin2.add(TaoUI.taoFieldText("Cảnh báo", 80, 80, 30, 5, tfCanhBao));
        thongTin2.add(Box.createHorizontalGlue());
        NhaCungCapBUS nhaCungCapBUS = new NhaCungCapBUS();
        ArrayList<NhaCungCap> dsNCC = nhaCungCapBUS.laylistNhaCungCap();
        String[] listNCC = new String[dsNCC.size() + 1];
        for (int i = 0; i < dsNCC.size(); i++) {
            listNCC[i + 1] = dsNCC.get(i).getTenNCC();
        }
        listNCC[0] = "--Nhà cung cấp --";
        cbNhaCungCap = new JComboBox<>(listNCC);
        TaoUI.setFixSize(cbNhaCungCap, 180, 30);
        thongTin2.add(cbNhaCungCap);

        JPanel thongTin3 = TaoUI.taoPanelBoxLayoutNgang(400, 35);
        thongTin3.add(TaoUI.taoFieldText("Dung tích(ml)", 80, 80, 30, 5, tfDungTich));
        thongTin3.add(Box.createHorizontalGlue());
        thongTin3.add(TaoUI.taoFieldText("Giá bán", 80, 100, 30, 5, tfGiaBan));

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
        luaChonTrangThaiXuLy.add("Chờ xử lý");
        luaChonTrangThaiXuLy.add("Ẩn");
        cbTrangThaiXuLy = new JComboBox<>(luaChonTrangThaiXuLy.toArray());
        cbTrangThaiXuLy.setFont(cbTrangThaiXuLy.getFont().deriveFont(11.0f));
        thongTin5_1.add(new JLabel("Trạng thái"));
        thongTin5_1.add(Box.createRigidArea(new Dimension(10, 0)));
        thongTin5_1.add(cbTrangThaiXuLy);
        thongTin5_1.add(Box.createHorizontalGlue());

        JPanel pnlFooter = TaoUI.taoPanelCanGiua(400, 30);

        btnSua.setPreferredSize(new Dimension(100, 35));

        btnLuuThayDoi.setPreferredSize(new Dimension(150, 35));
        btnLuuThayDoi.setEnabled(false);

        pnlFooter.add(btnSua);
        pnlFooter.add(btnLuuThayDoi);

        JPanel taoSanPhamPanel = TaoUI.taoPanelCanGiua(400, 30);
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
        chitietPanel.add(Box.createRigidArea(new Dimension(0, 3)));
        chitietPanel.add(thongTin5);
        chitietPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        if (sanPham != null) {
            chitietPanel.add(thongTin5_1);
        }

        chitietPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        formCongThuc = TaoUI.taoPanelBorderLayout(400, 150);
        JPanel titleThongTin6 = TaoUI.taoPanelBoxLayoutNgang(400, 25);
        formCongThuc.add(btnXemCongThuc);
        titleThongTin6.add(new JLabel("Bảng size"));
        titleThongTin6.add(Box.createHorizontalGlue());

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

        formCongThuc.add(titleThongTin6, BorderLayout.NORTH);

        modelSize.addColumn("Mã Size");
        modelSize.addColumn("Tên size");
        modelSize.addColumn("Giá thêm(%)");
        modelSize.addColumn("Nguyên liệu thêm(%)");

        JScrollPane scrollPaneSize = TaoUI.taoTableScroll(modelSize);
        tblSize = (JTable) scrollPaneSize.getViewport().getView();
        tblSize.removeColumn(tblSize.getColumnModel().getColumn(0));
        formCongThuc.add(scrollPaneSize, BorderLayout.CENTER);
        chitietPanel.add(formCongThuc);
        formCongThuc.setVisible(false);

        chitietPanel.add(Box.createVerticalGlue());
        if (sanPham != null) {
            chitietPanel.add(pnlFooter);
        } else {
            chitietPanel.add(taoSanPhamPanel);
        }

        this.add(chitietPanel);
    }

    public void settupGiaoDien(SanPham sanPham) {
        if (sanPham == null) {
            tfMaSanPham.setText("");
            tfTenSanPham.setText("");
            tfCanhBao.setText("");
            tfGiaBan.setText("");
            tfDungTich.setText("");
            lblAnh.setIcon(TaoUI.taoImageIcon("../assets/img/douongmd.png", 200, 200));
            modelSize.setRowCount(0);
            cbDanhMuc.setSelectedIndex(0);
            cbLoaiNuoc.setSelectedIndex(0);
            cbNhaCungCap.setSelectedIndex(0);
            cbTrangThaiXuLy.setSelectedIndex(0);
            formCongThuc.setVisible(false);
            repaint();
            return;
        }
        lblAnh.setIcon(TaoUI.taoImageIcon(sanPham.getAnh(), 200, 200));
        tfMaSanPham.setText(sanPham.getMaSP());
        tfTenSanPham.setText(sanPham.getTenSP());
        tfCanhBao.setText(String.valueOf(sanPham.getMucCanhBao()));
        cbNhaCungCap.setSelectedItem(sanPham.getNhaCungCap() != null ? sanPham.getNhaCungCap().getTenNCC() : "");
        tfGiaBan.setText(String.valueOf(sanPham.getGiaBan()));
        tfDungTich.setText(String.valueOf(sanPham.getTheTich()));
        cbDanhMuc.setSelectedItem(sanPham.getDanhMuc() != null ? sanPham.getDanhMuc().getTenDM() : "");
        cbLoaiNuoc.setSelectedItem(sanPham.getLoaiNuoc() != null ? sanPham.getLoaiNuoc() : "");
        cbTrangThaiXuLy.setSelectedItem(sanPham.getTrangThaiXuLy());
        btnThemSize.setEnabled(false);
        btnSuaSize.setEnabled(false);
        btnXoaSize.setEnabled(false);

        modelSize.setRowCount(0);
        if (sanPham.getListSize() != null) {
            for (Size size : sanPham.getListSize()) {
                modelSize.addRow(
                        new Object[] { size.getMaSize(), size.getTenSize(), size.getPhanTramGia(),
                                size.getPhanTramNL() });
            }
        }
        
        xemCongThucDialog.capNhapDuLieu(sanPham);
        this.sanPham = sanPham;
        anThaotacSua();
    }

    public void themSizeVaoBang(Size size) {
        modelSize.addRow(
                new Object[] { size.getMaSP(), size.getTenSize(), size.getPhanTramGia(), size.getPhanTramNL() });
    }

    private void anThaotacSua() {
        tfMaSanPham.setEditable(false);
        tfTenSanPham.setEditable(false);
        tfCanhBao.setEditable(false);
        cbNhaCungCap.setEnabled(false);
        tfGiaBan.setEditable(false);
        tfDungTich.setEditable(false);
        cbDanhMuc.setEnabled(false);
        cbLoaiNuoc.setEnabled(false);
        cbNhaCungCap.setEditable(false);
        cbTrangThaiXuLy.setEnabled(false);
        btnLuuThayDoi.setEnabled(false);
        btnSua.setEnabled(true);
        btnChonAnh.setEnabled(false);
        btnThemSize.setEnabled(false);
        btnSuaSize.setEnabled(false);
        btnXoaSize.setEnabled(false);
        btnXemCongThuc.setEnabled(false);

    }

    private void batThaoTacSua() {
        tfMaSanPham.setEditable(false);
        tfTenSanPham.setEditable(true);
        tfCanhBao.setEditable(true);
        cbNhaCungCap.setEnabled(true);
        tfGiaBan.setEditable(true);
        tfDungTich.setEditable(true);
        cbDanhMuc.setEnabled(true);
        cbLoaiNuoc.setEnabled(true);
        cbTrangThaiXuLy.setEnabled(true);
        btnLuuThayDoi.setEnabled(true);
        cbNhaCungCap.setEnabled(true);
        btnSua.setEnabled(false);
        btnChonAnh.setEnabled(true);
        btnThemSize.setEnabled(true);
        btnSuaSize.setEnabled(true);
        btnXoaSize.setEnabled(true);
        btnXemCongThuc.setEnabled(true);
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
            settupGiaoDien(null);
            this.repaint();
        });

        btnSua.addActionListener(e -> {
            batThaoTacSua();
        });

        btnLuuThayDoi.addActionListener(e -> {
            if (kiemTraDuLieu()) {
                SanPham sanPhamMoi = dongGoiSanPham();
                sanPhamMoi.setTrangThaiXuLy("Đã xác nhận");
                if (fileChooser.getSelectedFile() == null) {
                    sanPhamMoi.setAnh(sanPham.getAnh());
                }
                SanPhamBUS sanPhamBUS = new SanPhamBUS();
                if (sanPhamBUS.capNhapSanPham(sanPham, sanPhamMoi)) {
                    JOptionPane.showMessageDialog(this, "Cập nhật sản phẩm thành công!", "Thành công",
                            JOptionPane.INFORMATION_MESSAGE);
                    anThaotacSua();
                    quanLySanPhamUI.loadDataFromDatabase();
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
                    quanLySanPhamUI.loadDataFromDatabase();
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm sản phẩm thất bại!", "Thất bại",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
            dispose();
        });

        btnThemSize.addActionListener(e -> {
            new SizeDialog(this, null);
        });

        btnSuaSize.addActionListener(e -> {
            int row = tblSize.getSelectedRow();
            if (row >= 0) {
                String maSize = (String) modelSize.getValueAt(row, 0);
                if (sanPham != null && sanPham.getListSize() != null) {
                    for (Size size : sanPham.getListSize()) {
                        if (size.getMaSize().equals(maSize)) {
                            new SizeDialog(this, size, row);
                            break;
                        }
                    }
                } else {
                    String ma = modelSize.getValueAt(row, 0).toString();
                    String tenSize = modelSize.getValueAt(row, 1).toString();
                    int ptGia = Integer.parseInt(modelSize.getValueAt(row, 2).toString());
                    int ptNl = Integer.parseInt(modelSize.getValueAt(row, 3).toString());
                    Size size = new Size(ma, "", tenSize, ptGia, ptNl);
                    new SizeDialog(this, size, row);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn size để sửa!", "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        btnXoaSize.addActionListener(e -> {
            int row = tblSize.getSelectedRow();
            if (row >= 0) {
                modelSize.removeRow(row);
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn size để xóa!", "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
            }

        });
        btnXemCongThuc.addActionListener(e -> {
            xemCongThucDialog.setVisible(true);
        });

        cbLoaiNuoc.addActionListener(e -> {
            if (cbLoaiNuoc.getSelectedItem().toString().equals("Pha chế")) {
                formCongThuc.setVisible(true);
            } else {
                formCongThuc.setVisible(false);
            }
        });

    }

    public void suaSizeTrenDong(Size size, int dong) {
        modelSize.setValueAt(size.getTenSize(), dong, 1);
        modelSize.setValueAt(size.getPhanTramGia(), dong, 2);
        modelSize.setValueAt(size.getPhanTramNL(), dong, 3);
    }

    private boolean kiemTraDuLieu() {
        // if (tfTenSanPham.getText().isEmpty()) {
        // JOptionPane.showMessageDialog(this, "Vui lòng nhập tên sản phẩm!", "Lỗi",
        // JOptionPane.ERROR_MESSAGE);
        // return false;
        // }
        // if (cbDanhMuc.getSelectedIndex() == 0) {
        // JOptionPane.showMessageDialog(this, "Vui lòng chọn danh mục!", "Lỗi",
        // JOptionPane.ERROR_MESSAGE);
        // return false;
        // }
        // if (cbLoaiNuoc.getSelectedIndex() == 0) {
        // JOptionPane.showMessageDialog(this, "Vui lòng chọn loại nước!", "Lỗi",
        // JOptionPane.ERROR_MESSAGE);
        // return false;
        // }
        // try {
        // Long.parseLong(tfGiaBan.getText());
        // Long.parseLong(tfDungTich.getText());
        // } catch (NumberFormatException ex) {
        // JOptionPane.showMessageDialog(this, "Giá nhập, giá bán, dung tích phải là
        // số!", "Lỗi",
        // JOptionPane.ERROR_MESSAGE);
        // return false;
        // }
        return true;
    }

    public SanPham dongGoiSanPham() {
        if (xemCongThucDialog != null) {
            xemCongThucDialog.dongGoiCongThuc();
        }
        SanPham sp = new SanPham();
        sp.setMaSP(sanPham == null ? "" : sanPham.getMaSP());
        sp.setTenSP(tfTenSanPham.getText());
        sp.setLoaiNuoc((String) cbLoaiNuoc.getSelectedItem());
        DanhMuc danhMuc = danhMucBUS.timDanhMucTheoTen((String) cbDanhMuc.getSelectedItem());
        sp.setDanhMuc(danhMuc);
        sp.setGiaBan(Long.parseLong(tfGiaBan.getText()));
        sp.setTheTich(Integer.parseInt(tfDungTich.getText()));
        sp.setMucCanhBao(Integer.parseInt(tfCanhBao.getText()));
        NhaCungCap ncc = nhaCungCapBUS.timNhaCungCapTheoTen((String) cbNhaCungCap.getSelectedItem());
        sp.setNhaCungCap(ncc);
        sp.setCongThuc(xemCongThucDialog != null ? xemCongThucDialog.dongGoiCongThuc() : null);
        sp.setListSize(dongGoiListSize());
        sp.setTrangThaiXuLy("Chờ xử lý");
        sp.setTrangThai(true);

        if (lblAnh.getIcon() != null) {
            SanPhamBUS sanPhamBUS = new SanPhamBUS();
            sp.setAnh(sanPhamBUS.luuAnh(sanPhamBUS.layMaSanPhamKhaDung(), fileChooser));
        }

        return sp;
    }

    private ArrayList<Size> dongGoiListSize() {
        ArrayList<Size> listSize = new ArrayList<>();
        for (int i = 0; i < modelSize.getRowCount(); i++) {
            Size size = new Size(modelSize.getValueAt(i, 0).toString(), sanPham == null ? "" : sanPham.getMaSP(),
                    modelSize.getValueAt(i, 1).toString(),
                    Integer.parseInt(modelSize.getValueAt(i, 2).toString()),
                    Integer.parseInt(modelSize.getValueAt(i, 3).toString()));
            listSize.add(size);
        }
        return listSize;
    }
}