package ui.quanlysanpham;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import bus.DanhMucBUS;
import bus.SanPhamBUS;
import dto.ChiTietCongThuc;
import dto.CongThuc;
import dto.DanhMuc;
import dto.SanPham;
import dto.Size;
import ui.login.LoginUI;
import ui.login.PhienDangNhap;
import util.Anh;
import util.TaoUI;

public class ChiTietSanPhamDialog extends JDialog {

    private JTextField tfMaSanPham, tfTenSanPham, tfCanhBao, tfGiaBan, tfDungTich;
    private JTable tblSize;
    private DefaultTableModel modelSize;
    private JButton btnChonAnh, btnLuuThayDoi, btnSua, btnXemCongThuc, btnXoaSize, btnThemSize, btnSuaSize, btnThemSp,
            btnLamMoi;
    private JLabel lblAnh;
    private JFileChooser fileChooser;
    private JComboBox<String> cbLoaiNuoc, cbDanhMuc, cbTrangThaiXuLy;
    private SanPham sanPham;

    private DanhMucBUS danhMucBUS = new DanhMucBUS();

    private XemCongThucDialog xemCongThucDialog;
    private JPanel formCongThuc;

    public ChiTietSanPhamDialog(SanPham sanPham, QuanLySanPhamUI quanLySanPhamUI) {
        super((JFrame) null, "Chi tiết sản phẩm", true);
        this.sanPham = sanPham;

        this.setSize(400, 700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        lblAnh = TaoUI.taoJlabelAnh(null, 200, 200);
        lblAnh.setAlignmentX(CENTER_ALIGNMENT);

        btnChonAnh = new JButton("Chọn ảnh");
        xemCongThucDialog = new XemCongThucDialog(this, sanPham);
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
        btnXemCongThuc = new JButton("Công thức");
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
        suaLaiGiaoDienTheoQuyen();
    }

    private void initGUI() {
        JPanel chitietPanel = TaoUI.taoPanelBoxLayoutDoc(400, 750);
        chitietPanel = TaoUI.suaBorderChoPanel(chitietPanel, 0, 10, 10, 10);

        JPanel buttons = TaoUI.taoPanelCanGiua(400, 25);
        buttons.add(btnChonAnh);

        JPanel thongTin1 = TaoUI.taoPanelBoxLayoutNgang(400, 35);

        thongTin1.add(TaoUI.taoFieldText("Tên sản phẩm", 80, 280, 30, 5, tfTenSanPham));

        JPanel thongTin2 = TaoUI.taoPanelBoxLayoutNgang(400, 35);
        if (sanPham != null) {
            thongTin2.add(TaoUI.taoFieldText("Mã sản phẩm", 80, 80, 30, 5, tfMaSanPham));
        }
        thongTin2.add(Box.createHorizontalGlue());
        thongTin2.add(TaoUI.taoFieldText("Cảnh báo", 60, 100, 30, 5, tfCanhBao));

        JPanel thongTin3 = TaoUI.taoPanelBoxLayoutNgang(400, 35);
        thongTin3.add(TaoUI.taoFieldText("Dung tích(ml)", 80, 80, 30, 5, tfDungTich));
        thongTin3.add(Box.createHorizontalGlue());
        thongTin3.add(TaoUI.taoFieldText("Giá bán", 60, 100, 30, 5, tfGiaBan));

        JPanel thongTin5 = TaoUI.taoPanelBoxLayoutNgang(400, 35);

        DanhMucBUS danhMucBUS = new DanhMucBUS();
        ArrayList<String> luaChonDanhMuc = danhMucBUS.layLuaChonDanhMuc();
        luaChonDanhMuc.add(0, "-- Danh mục --");
        ArrayList<String> luaChonLoaiNuoc = new ArrayList<>();
        luaChonLoaiNuoc.add("-- Loại nước --");
        luaChonLoaiNuoc.add("Có sẵn");
        luaChonLoaiNuoc.add("Pha chế");
        cbDanhMuc = new JComboBox<>(luaChonDanhMuc.toArray(new String[0]));
        cbDanhMuc.setFont(cbDanhMuc.getFont().deriveFont(11.0f));
        TaoUI.setFixSize(cbDanhMuc, 100, 35);
        cbLoaiNuoc = new JComboBox<>(luaChonLoaiNuoc.toArray(new String[0]));
        cbLoaiNuoc.setFont(cbLoaiNuoc.getFont().deriveFont(11.0f));
        TaoUI.setFixSize(cbLoaiNuoc, 100, 35);
        thongTin5.add(new JLabel("Loại nước"));
        thongTin5.add(Box.createRigidArea(new Dimension(25, 0)));
        thongTin5.add(cbLoaiNuoc);
        thongTin5.add(Box.createHorizontalGlue());
        thongTin5.add(new JLabel("Danh mục"));
        thongTin5.add(Box.createRigidArea(new Dimension(10, 0)));
        thongTin5.add(cbDanhMuc);

        JPanel thongTin5_1 = TaoUI.taoPanelBoxLayoutNgang(400, 35);
        ArrayList<String> luaChonTrangThaiXuLy = new ArrayList<>();
        luaChonTrangThaiXuLy.add("-- Tất cả --");
        luaChonTrangThaiXuLy.add("Đã xác nhận");
        luaChonTrangThaiXuLy.add("Chờ xử lý");
        luaChonTrangThaiXuLy.add("Ẩn");
        cbTrangThaiXuLy = new JComboBox<>(luaChonTrangThaiXuLy.toArray(new String[0]));
        cbTrangThaiXuLy.setFont(cbTrangThaiXuLy.getFont().deriveFont(11.0f));
        thongTin5_1.add(new JLabel("Trạng thái"));
        thongTin5_1.add(Box.createRigidArea(new Dimension(25, 0)));
        thongTin5_1.add(cbTrangThaiXuLy);
        thongTin5_1.add(Box.createHorizontalGlue());

        JPanel pnlFooter = TaoUI.taoPanelCanGiua(400, 30);

        btnSua.setPreferredSize(new Dimension(100, 35));

        btnLuuThayDoi.setPreferredSize(new Dimension(150, 35));
        btnLuuThayDoi.setEnabled(false);

        pnlFooter.add(btnSua);
        pnlFooter.add(Box.createRigidArea(new Dimension(5, 0)));
        pnlFooter.add(btnLuuThayDoi);

        JPanel taoSanPhamPanel = TaoUI.taoPanelCanGiua(400, 30);
        btnLamMoi.setPreferredSize(new Dimension(150, 35));
        btnThemSp.setPreferredSize(new Dimension(150, 35));

        taoSanPhamPanel.add(btnThemSp);
        pnlFooter.add(Box.createRigidArea(new Dimension(5, 0)));
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
            cbTrangThaiXuLy.setSelectedIndex(0);
            formCongThuc.setVisible(false);
            repaint();
            return;
        }
        lblAnh.setIcon(TaoUI.taoImageIcon(sanPham.getAnh(), 200, 200));
        tfMaSanPham.setText(sanPham.getMaSP());
        tfTenSanPham.setText(sanPham.getTenSP());
        tfCanhBao.setText(String.valueOf(sanPham.getMucCanhBao()));
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
        tfGiaBan.setEditable(false);
        tfDungTich.setEditable(false);
        cbDanhMuc.setEnabled(false);
        cbLoaiNuoc.setEnabled(false);
        cbTrangThaiXuLy.setEnabled(false);
        btnLuuThayDoi.setEnabled(false);
        btnSua.setEnabled(true);
        btnChonAnh.setEnabled(false);
        btnThemSize.setEnabled(false);
        btnSuaSize.setEnabled(false);
        btnXoaSize.setEnabled(false);
        xemCongThucDialog.tacThaoTacSua();
    }

    private void batThaoTacSua() {
        tfMaSanPham.setEditable(false);
        tfTenSanPham.setEditable(true);
        tfCanhBao.setEditable(true);
        tfGiaBan.setEditable(true);
        tfDungTich.setEditable(true);
        cbDanhMuc.setEnabled(true);
        cbLoaiNuoc.setEnabled(true);
        cbTrangThaiXuLy.setEnabled(true);
        btnLuuThayDoi.setEnabled(true);

        btnSua.setEnabled(false);
        btnChonAnh.setEnabled(true);
        btnThemSize.setEnabled(true);
        btnSuaSize.setEnabled(true);
        btnXoaSize.setEnabled(true);
        xemCongThucDialog.batThaoTacSua();
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

                sanPhamMoi.setTrangThaiXuLy(cbTrangThaiXuLy.getSelectedItem().toString());
                if (fileChooser.getSelectedFile() == null) {
                    sanPhamMoi.setAnh(sanPham.getAnh());
                }
                SanPhamBUS sanPhamBUS = SanPhamBUS.getSanPhamBUS();
                if (sanPhamBUS.capNhapSanPham(sanPham, sanPhamMoi)) {
                    JOptionPane.showMessageDialog(this, "Cập nhật sản phẩm thành công!", "Thành công",
                            JOptionPane.INFORMATION_MESSAGE);
                    anThaotacSua();
                    LoginUI.getLoginUI().getMainFrame().loadAllData();
                    dispose();

                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật sản phẩm thất bại!", "Thất bại",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnThemSp.addActionListener(e -> {
            if (kiemTraDuLieu()) {
                SanPham sanPham = dongGoiSanPham();
                SanPhamBUS sanPhamBUS = SanPhamBUS.getSanPhamBUS();
                if (sanPhamBUS.themSanPham(sanPham)) {
                    JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công!", "Thành công",
                            JOptionPane.INFORMATION_MESSAGE);
                    LoginUI.getLoginUI().getMainFrame().loadAllData();
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm sản phẩm thất bại!", "Thất bại",
                            JOptionPane.ERROR_MESSAGE);
                }
                dispose();
            }

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
        String tenSP = tfTenSanPham.getText().trim();
        if (tenSP.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên sản phẩm không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            tfTenSanPham.requestFocus();
            return false;
        }

        if (cbDanhMuc.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn danh mục phù hợp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (cbTrangThaiXuLy.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn Trạng thái phù hợp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cbLoaiNuoc.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn loại nước (Có sẵn hoặc Pha chế)!", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            long giaBan = Long.parseLong(tfGiaBan.getText().trim());
            int dungTich = Integer.parseInt(tfDungTich.getText().trim());
            int canhBao = Integer.parseInt(tfCanhBao.getText().trim());

            if (giaBan < 0) {
                JOptionPane.showMessageDialog(this, "Giá bán không được là số âm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if (dungTich <= 0) {
                JOptionPane.showMessageDialog(this, "Dung tích phải lớn hơn 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if (canhBao < 0) {
                JOptionPane.showMessageDialog(this, "Mức cảnh báo không được âm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Giá bán, dung tích và mức cảnh báo phải là số nguyên!", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (cbLoaiNuoc.getSelectedItem().toString().equals("Pha chế")) {
            if (modelSize.getRowCount() == 0) {
                int result = JOptionPane.showConfirmDialog(this,
                        "Sản phẩm pha chế chưa có bảng Size. Bạn có chắc chắn muốn tiếp tục?",
                        "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.NO_OPTION)
                    return false;
            }

            if (xemCongThucDialog != null) {
                CongThuc ct = xemCongThucDialog.dongGoiCongThuc();
                if (ct == null || ct.getListChiTietCongThuc() == null || ct.getListChiTietCongThuc().isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Sản phẩm pha chế phải có ít nhất một nguyên liệu trong công thức!", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        }

        return true;
    }

    public SanPham dongGoiSanPham() {
        SanPham sp = new SanPham();
        sp.setMaSP(sanPham == null ? "" : sanPham.getMaSP());
        sp.setTenSP(tfTenSanPham.getText());
        sp.setLoaiNuoc((String) cbLoaiNuoc.getSelectedItem());
        DanhMuc danhMuc = danhMucBUS.timDanhMucTheoTen((String) cbDanhMuc.getSelectedItem());
        sp.setDanhMuc(danhMuc);
        sp.setGiaBan(Long.parseLong(tfGiaBan.getText()));
        sp.setTheTich(Integer.parseInt(tfDungTich.getText()));
        sp.setMucCanhBao(Integer.parseInt(tfCanhBao.getText()));
        sp.setCongThuc(xemCongThucDialog != null ? xemCongThucDialog.dongGoiCongThuc() : null);

        for (ChiTietCongThuc chiTietCongThuc : sp.getCongThuc().getListChiTietCongThuc()) {
            System.out.println(chiTietCongThuc.getMaCT() + " " + chiTietCongThuc.getNguyenLieu().getMaNL());
        }

        sp.setListSize(dongGoiListSize());
        sp.setTrangThaiXuLy("Chờ xử lý");

        if (lblAnh.getIcon() != null) {
            SanPhamBUS sanPhamBUS = SanPhamBUS.getSanPhamBUS();
            sp.setAnh(Anh.luuAnhSP(sanPhamBUS.layMaSanPhamKhaDung(), fileChooser));
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

    public void suaLaiGiaoDienTheoQuyen() {
        HashSet<String> listQuyen = PhienDangNhap.getListQuyen();
        if (!listQuyen.contains("QLSP_SUA")) {
            btnSua.setVisible(false);
            btnLuuThayDoi.setVisible(false);
            btnSuaSize.setVisible(false);
            btnChonAnh.setVisible(false);
        }

        if (!listQuyen.contains("QLSP_TAO")) {
            btnThemSp.setVisible(false);
            btnThemSize.setVisible(false);
            btnLamMoi.setVisible(false);
        }

        if (!listQuyen.contains("QLSP_XOA")) {
            btnXoaSize.setVisible(false);
        }
        xemCongThucDialog.suaLaiGiaoDienTheoQuyen();
        this.revalidate();
        this.repaint();
    }

    public JButton getBtnSua() {
        return btnSua;
    }

    public void inChiTietSanPham(SanPham sp) {
        if (sp == null) {
            System.out.println("Sản phẩm không tồn tại!");
            return;
        }

        System.out.println("----------------------------------------------");
        System.out.println(String.format("%-15s: %s", "Mã sản phẩm", sp.getMaSP()));
        System.out.println(String.format("%-15s: %s", "Tên sản phẩm", sp.getTenSP()));
        System.out.println(
                String.format("%-15s: %s", "Danh mục", (sp.getDanhMuc() != null ? sp.getDanhMuc().getTenDM() : "N/A")));
        System.out.println(String.format("%-15s: %,d VNĐ", "Giá bán", sp.getGiaBan()));
        System.out.println(String.format("%-15s: %s", "Loại nước", sp.getLoaiNuoc()));
        System.out.println(String.format("%-15s: %d ml", "Thể tích", sp.getTheTich()));

        if (sp.getCongThuc() != null && sp.getCongThuc().getListChiTietCongThuc() != null) {
            System.out.println("Công thức pha chế:");
            for (ChiTietCongThuc ct : sp.getCongThuc().getListChiTietCongThuc()) {
                String tenNL = (ct.getNguyenLieu() != null) ? ct.getNguyenLieu().getTenNL() : "Nguyên liệu ẩn";
                System.out.println(String.format("   + %-15s: %.2f %s",
                        tenNL,
                        ct.getSoLuong(),
                        (ct.getNguyenLieu() != null ? ct.getNguyenLieu().getDonVi() : "")));
            }
        } else {
            System.out.println("Công thức: (Trống)");
        }
        System.out.println("----------------------------------------------");
    }

    public static void main(String[] args) {
        SanPham sanPham = SanPhamBUS.getSanPhamBUS().timSanPham("SP0sds2");
        ChiTietSanPhamDialog chiTietSanPhamDialog = new ChiTietSanPhamDialog(sanPham, null);
        chiTietSanPhamDialog.setVisible(true);
    }
}
