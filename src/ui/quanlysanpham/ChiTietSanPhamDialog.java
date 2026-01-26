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
        tblSize = new JTable(modelSize);
        fileChooser = new JFileChooser();

        initGUI();
        ganSuKien();
        capNhapDuLieu(sanPham);
        setVisible(true);

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
        luaChonTrangThaiXuLy.add("Đang xử lý");
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
        if (sanPham == null || sanPham.getLoaiNuoc().equals("Pha chế")) {
            JPanel thongTin6 = TaoUI.taoPanelBorderLayout(400, 150);
            JPanel titleThongTin6 = TaoUI.taoPanelBoxLayoutNgang(400, 25);
            thongTin6.add(btnXemCongThuc);
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

            thongTin6.add(titleThongTin6, BorderLayout.NORTH);

            if (sanPham != null) {
                modelSize.addColumn("Mã Size");
            }
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
    }

    private void capNhapDuLieu(SanPham sanPham) {
        if (sanPham == null) {
            tfMaSanPham.setText("");
            tfTenSanPham.setText("");
            tfCanhBao.setText("");
            tfGiaBan.setText("");
            tfDungTich.setText("");
            lblAnh.setIcon(TaoUI.taoImageIcon("../assets/img/douongmd.png", 200, 200));
            modelSize.setRowCount(0);
            return;
        }
        lblAnh.setIcon(TaoUI.taoImageIcon(sanPham.getAnh(), 200, 200));
        tfMaSanPham.setText(sanPham.getMaSP());
        tfTenSanPham.setText(sanPham.getTenSP());
        tfCanhBao.setText(String.valueOf(sanPham.getMucCanhBao()));
        cbNhaCungCap.setSelectedItem(sanPham.getNhaCungCap().getTenNCC());
        tfGiaBan.setText(String.valueOf(sanPham.getGiaBan()));
        tfDungTich.setText(String.valueOf(sanPham.getTheTich()));
        cbDanhMuc.setSelectedItem(sanPham.getDanhMuc().getTenDM());
        cbLoaiNuoc.setSelectedItem(sanPham.getLoaiNuoc());
        cbTrangThaiXuLy.setSelectedItem(sanPham.getTrangThaiXuLy());

        if (sanPham.getListSize() != null) {
            for (Size size : sanPham.getListSize()) {
                modelSize.addRow(
                        new Object[] { size.getMaSize(), size.getTenSize(), size.getPhanTramGia(),
                                size.getPhanTramNL() });
            }
        }

    }

    public void themSizeVaoBang(Size size) {
        modelSize.addRow(new Object[] { size.getTenSize(), size.getPhanTramGia(), size.getPhanTramNL() });
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
            cbNhaCungCap.setEnabled(true);
            tfGiaBan.setEditable(true);
            tfDungTich.setEditable(true);
            cbDanhMuc.setEnabled(true);
            cbLoaiNuoc.setEnabled(true);
            btnLuuThayDoi.setEnabled(true);
            cbTrangThaiXuLy.setEnabled(true);
            cbNhaCungCap.setEnabled(true);
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
                    cbNhaCungCap.setEnabled(false);
                    tfGiaBan.setEditable(false);
                    tfDungTich.setEditable(false);
                    cbDanhMuc.setEnabled(false);
                    cbLoaiNuoc.setEnabled(false);
                    cbNhaCungCap.setEditable(false);
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
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm sản phẩm thất bại!", "Thất bại",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
            dispose();
        });

        btnThemSize.addActionListener(e -> {
            new ThemSizeDialog(this);
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
                            // sanPham.getListSize().remove(size);
                            // modelSize.removeRow(row);
                            // JOptionPane.showMessageDialog(this, "Xóa size thành công!", "Thành công",
                            // JOptionPane.INFORMATION_MESSAGE);
                            // } else {
                            // JOptionPane.showMessageDialog(this, "Xóa size thất bại!", "Thất bại",
                            // JOptionPane.ERROR_MESSAGE);
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
        btnXemCongThuc.addActionListener(e -> {
            xemCongThucDialog.setVisible(true);
        });

        if (sanPham != null) {
            tfMaSanPham.setEditable(false);
            tfMaSanPham.setBackground(java.awt.Color.white);

            tfTenSanPham.setEditable(false);
            tfTenSanPham.setBackground(java.awt.Color.WHITE);

            tfCanhBao.setEditable(false);
            tfCanhBao.setBackground(java.awt.Color.WHITE);

            tfGiaBan.setEditable(false);
            tfGiaBan.setBackground(java.awt.Color.WHITE);

            tfDungTich.setEditable(false);
            tfDungTich.setBackground(java.awt.Color.WHITE);
            cbDanhMuc.setEnabled(false);
            cbTrangThaiXuLy.setEnabled(false);

            cbLoaiNuoc.setEnabled(false);
            cbNhaCungCap.setEnabled(false);
            btnChonAnh.setEnabled(false);
        }
    }

    public void inThongTinSanPham(SanPham sp) {
        if (sp == null) {
            System.out.println(">> LỖI: Đối tượng Sản phẩm là NULL.");
            return;
        }

        System.out.println("\n==================== THÔNG TIN SẢN PHẨM ====================");
        // 1. In thông tin cơ bản
        System.out.println("Mã SP          : " + sp.getMaSP());
        System.out.println("Tên SP         : " + sp.getTenSP());

        // Xử lý các object tham chiếu (DanhMuc, NhaCungCap) để tránh null pointer
        String tenDM = (sp.getDanhMuc() != null) ? sp.getDanhMuc().getTenDM() : "---"; // Thay .getTenDM() bằng hàm của
                                                                                       // bạn
        System.out.println("Danh mục       : " + tenDM);

        String tenNCC = (sp.getNhaCungCap() != null) ? sp.getNhaCungCap().getTenNCC() : "---"; // Thay .getTenNCC() bằng
                                                                                               // hàm của bạn
        System.out.println("Nhà cung cấp   : " + tenNCC);

        System.out.printf("Giá bán        : %,d VNĐ\n", sp.getGiaBan());
        System.out.println("Loại nước      : " + sp.getLoaiNuoc());
        System.out.println("Dung tích      : " + sp.getTheTich() + " ml");
        System.out.println("Cảnh báo kho   : " + sp.getMucCanhBao());
        System.out.println("Trạng thái bán : " + (sp.getTrangThai() ? "Đang kinh doanh" : "Ngừng kinh doanh"));
        System.out.println("Trạng thái XL  : " + sp.getTrangThaiXuLy());
        System.out.println("Ảnh            : " + sp.getAnh());

        // 2. In danh sách Size
        System.out.println("\n-------------------- DANH SÁCH SIZE --------------------");
        if (sp.getListSize() != null && !sp.getListSize().isEmpty()) {
            System.out.printf("%-10s | %-15s | %-15s | %-15s\n", "Mã Size", "Tên Size", "% Giá thêm", "% NL thêm");
            for (Size s : sp.getListSize()) {
                System.out.printf("%-10s | %-15s | %-15d | %-15d\n",
                        s.getMaSize(), s.getTenSize(), s.getPhanTramGia(), s.getPhanTramNL());
            }
        } else {
            System.out.println(">> Sản phẩm chưa có Size nào.");
        }

        // 3. In thông tin Công thức
        System.out.println("\n-------------------- CÔNG THỨC PHA CHẾ --------------------");
        CongThuc ct = sp.getCongThuc();
        if (ct != null) {
            System.out.println("Mã công thức: " + ct.getMaCT());

            if (ct.getListChiTietCongThuc() != null && !ct.getListChiTietCongThuc().isEmpty()) {
                System.out.printf("%-20s | %-10s\n", "Tên Nguyên Liệu", "Số Lượng");
                for (ChiTietCongThuc ctct : ct.getListChiTietCongThuc()) {
                    // Giả định class NguyenLieu có hàm getTenNL()
                    String tenNL = (ctct.getNguyenLieu() != null) ? ctct.getNguyenLieu().getTenNL() : "Null NL";
                    System.out.printf("%-20s | %-10.2f\n", tenNL, ctct.getSoLuong());
                }
            } else {
                System.out.println(">> Công thức này chưa có nguyên liệu chi tiết.");
            }
        } else {
            System.out.println(">> Sản phẩm chưa có công thức.");
        }
        System.out.println("===========================================================\n");
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
            Size size = new Size("", "", modelSize.getValueAt(i, 0).toString(),
                    Integer.parseInt(modelSize.getValueAt(i, 1).toString()),
                    Integer.parseInt(modelSize.getValueAt(i, 2).toString()));
            listSize.add(size);
        }
        return listSize;
    }

    private void luuAnh() {
    }
}