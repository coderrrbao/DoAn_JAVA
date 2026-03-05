package ui.nhapkho.sanpham;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import bus.LoSanPhamBUS;
import bus.NhaCungCapBUS;
import bus.PhieuNhapSanPhamBUS;
import bus.SanPhamBUS;
import dto.ChiTietNhaCungCap;
import dto.LoSanPham;
import dto.NhaCungCap;
import dto.PhieuNhapSanPham;
import dto.SanPham;
import ui.component.Search_Item;
import ui.login.PhienDangNhap;
import util.TaoTinNhan;
import util.TaoUI;

public class NhapKhoSanPhamDialog extends JDialog {
    Search_Item search_Item;
    private JTextField txtMaSp;
    private JTextField txtLoaiSp;
    private JTextField txtTenSp;
    private JTextField txtGiaNhap;
    private JTextField txtSoLuong;
    private JDateChooser txtNgaySx;
    private JDateChooser txtHanSuDung;

    private JTextField txtNhanVien;
    private JComboBox<String> cbNhaCungCap;

    private JLabel lblTongTienHienThi;
    private JButton btnNhapHang;

    private JTable tblKhoHang;
    private DefaultTableModel modelKhoHang;

    private JTable tblChiTietPhieuNhap;
    private DefaultTableModel modelChiTietPhieuNhap;
    private JButton themSpPNHBtn, xoaCTBtn;

    NhapKhoSanPhamPanel nhapKhoSanPhamPanel;

    public NhapKhoSanPhamDialog(NhapKhoSanPhamPanel nhapKhoSanPhamPanel) {
        super((Frame) null, "Nhập Kho Sản Phẩm", true);
        setSize(900, 710);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        this.nhapKhoSanPhamPanel = nhapKhoSanPhamPanel;
        JPanel top = new JPanel();
        TaoUI.suaBorderChoPanel(top, 0, 0, 5, 0);
        TaoUI.setFixSize(top, 3000, 320);
        add(top, BorderLayout.NORTH);

        JPanel center = new JPanel();
        add(center, BorderLayout.CENTER);

        JPanel listSpPanel = TaoUI.taoPanelBorderLayout(450, 3000);

        modelKhoHang = new DefaultTableModel();
        modelKhoHang.addColumn("Mã sp");
        modelKhoHang.addColumn("Tên sp");
        modelKhoHang.addColumn("Giá nhập");
        modelKhoHang.addColumn("SL");
        modelKhoHang.addColumn("Loại SP");

        JScrollPane scroll = TaoUI.taoTableScroll(modelKhoHang);

        tblKhoHang = (JTable) scroll.getViewport().getView();

        tblKhoHang.getColumnModel().getColumn(0).setPreferredWidth(80);
        tblKhoHang.getColumnModel().getColumn(1).setPreferredWidth(180);
        tblKhoHang.getColumnModel().getColumn(2).setPreferredWidth(100);
        tblKhoHang.getColumnModel().getColumn(3).setPreferredWidth(70);

        listSpPanel.add(scroll, BorderLayout.CENTER);
        search_Item = new Search_Item(Integer.MAX_VALUE, 32);
        listSpPanel.add(search_Item, BorderLayout.NORTH);

        JPanel chiTietSp = TaoUI.taoPanelBoxLayoutDoc(3000, 3000);
        top.setLayout(new BorderLayout());
        top.add(listSpPanel, BorderLayout.WEST);
        top.add(chiTietSp, BorderLayout.CENTER);
        TaoUI.suaBorderChoPanel(chiTietSp, 0, 20, 0, 20);

        JPanel info1 = TaoUI.taoPanelBoxLayoutNgang(380, 65);

        JPanel maSpInput = TaoUI.taoPanelBoxLayoutDoc(150, 65);
        JPanel titleMaSp = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titleMaSp.add(new JLabel("Mã Sp"));
        maSpInput.add(titleMaSp);

        txtMaSp = new JTextField();
        TaoUI.setFixSize(txtMaSp, 3000, 40);
        maSpInput.add(txtMaSp);
        info1.add(maSpInput);

        info1.add(Box.createHorizontalGlue());

        JPanel loaiSpInput = TaoUI.taoPanelBoxLayoutDoc(200, 65);
        JPanel titleLoaiSp = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titleLoaiSp.add(new JLabel("Loại sản phẩm"));
        loaiSpInput.add(titleLoaiSp);

        txtLoaiSp = new JTextField();
        TaoUI.setFixSize(txtLoaiSp, 3000, 40);
        loaiSpInput.add(txtLoaiSp);
        info1.add(loaiSpInput);
        chiTietSp.add(info1);

        JPanel info2 = TaoUI.taoPanelFlowLayout(380, 65, 0, 0);
        JPanel tenSpInput = TaoUI.taoPanelBoxLayoutDoc(380, 65);
        JPanel titleTenSp = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titleTenSp.add(new JLabel("Tên sản phẩm"));

        txtTenSp = new JTextField();
        TaoUI.setFixSize(txtTenSp, 400, 40);
        tenSpInput.add(titleTenSp);
        tenSpInput.add(Box.createVerticalStrut(5));
        tenSpInput.add(txtTenSp);
        info2.add(tenSpInput);
        chiTietSp.add(info2);

        JPanel info3 = TaoUI.taoPanelBoxLayoutNgang(380, 65);

        JPanel giaInput = TaoUI.taoPanelBoxLayoutDoc(150, 65);
        JPanel titleGia = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titleGia.add(new JLabel("Giá nhập"));
        giaInput.add(titleGia);

        txtGiaNhap = new JTextField();
        TaoUI.setFixSize(txtGiaNhap, 3000, 40);
        giaInput.add(txtGiaNhap);

        JPanel soLuongInput = TaoUI.taoPanelBoxLayoutDoc(150, 65);
        JPanel titleSoLuong = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titleSoLuong.add(new JLabel("Số lượng"));
        soLuongInput.add(titleSoLuong);

        txtSoLuong = new JTextField();
        TaoUI.setFixSize(txtSoLuong, 3000, 40);
        soLuongInput.add(txtSoLuong);

        info3.add(giaInput);
        info3.add(Box.createRigidArea(new Dimension(10, 0)));
        info3.add(soLuongInput);
        chiTietSp.add(info3);

        JPanel info4 = TaoUI.taoPanelBoxLayoutNgang(380, 65);

        JPanel ngayInput = TaoUI.taoPanelBoxLayoutDoc(150, 65);
        JPanel titleNgay = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titleNgay.add(new JLabel("Ngày SX"));
        ngayInput.add(titleNgay);

        txtNgaySx = new JDateChooser();
        txtNgaySx.setDateFormatString("yyyy-MM-dd");
        TaoUI.setFixSize(txtNgaySx, 3000, 40);
        ngayInput.add(txtNgaySx);

        JPanel hanSDInput = TaoUI.taoPanelBoxLayoutDoc(150, 65);
        JPanel titleHanSD = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titleHanSD.add(new JLabel("Hạn SD"));
        hanSDInput.add(titleHanSD);

        txtHanSuDung = new JDateChooser();
        txtHanSuDung.setDateFormatString("yyyy-MM-dd");
        TaoUI.setFixSize(txtHanSuDung, 3000, 40);
        hanSDInput.add(txtHanSuDung);

        info4.add(ngayInput);
        info4.add(Box.createRigidArea(new Dimension(10, 0)));
        info4.add(hanSDInput);
        chiTietSp.add(info3);
        chiTietSp.add(info4);

        themSpPNHBtn = new JButton("Thêm vào phiếu");
        TaoUI.setFixSize(themSpPNHBtn, 130, 35);
        JPanel ctnThemSp = new JPanel(new FlowLayout(FlowLayout.LEFT));
        TaoUI.suaBorderChoPanel(ctnThemSp, 0, 3, 0, 0);
        ctnThemSp.add(themSpPNHBtn);
        chiTietSp.add(Box.createRigidArea(new Dimension(0, 10)));
        chiTietSp.add(ctnThemSp);

        JPanel chiTietPhieuNhapPanel = TaoUI.taoPanelBorderLayout(500, 3000);

        modelChiTietPhieuNhap = new DefaultTableModel();
        modelChiTietPhieuNhap.addColumn("Mã sp");
        modelChiTietPhieuNhap.addColumn("Tên sp");
        modelChiTietPhieuNhap.addColumn("Giá nhập");
        modelChiTietPhieuNhap.addColumn("Số lượng");
        modelChiTietPhieuNhap.addColumn("Ngày SX");
        modelChiTietPhieuNhap.addColumn("Hạn SD");

        JScrollPane scrollPaneCTPN = TaoUI.taoTableScroll(modelChiTietPhieuNhap);
        tblChiTietPhieuNhap = (JTable) scrollPaneCTPN.getViewport().getView();
        tblChiTietPhieuNhap.getColumnModel().getColumn(0).setPreferredWidth(60);
        tblChiTietPhieuNhap.getColumnModel().getColumn(3).setPreferredWidth(60);

        JPanel titleCTPN = TaoUI.taoPanelBoxLayoutDoc(500, 80);
        TaoUI.suaBorderChoPanel(titleCTPN, 0, 20, 0, 0);
        JLabel titleCTPNJLabel = new JLabel("Danh sách các sản phẩm nhập");
        JPanel titleCTPNJPanel = TaoUI.taoPanelBoxLayoutNgang(500, 50);
        titleCTPNJPanel.add(titleCTPNJLabel);
        titleCTPNJLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleCTPN.add(Box.createVerticalGlue());
        titleCTPN.add(titleCTPNJPanel);
        JPanel buttonPn = TaoUI.taoPanelBoxLayoutNgang(500, 20);
        buttonPn.add(Box.createHorizontalGlue());
        xoaCTBtn = new JButton("Xóa");
        buttonPn.add(xoaCTBtn);

        titleCTPN.add(buttonPn);
        titleCTPN.add(Box.createRigidArea(new Dimension(0, 3)));
        chiTietPhieuNhapPanel.add(scrollPaneCTPN, BorderLayout.CENTER);
        chiTietPhieuNhapPanel.add(titleCTPN, BorderLayout.NORTH);

        center.setLayout(new BorderLayout());
        center.add(chiTietPhieuNhapPanel, BorderLayout.WEST);
        TaoUI.suaBorderChoPanel(chiTietPhieuNhapPanel, 0, 0, 8, 0);

        int gap = 20;
        JPanel xacNhanNH = TaoUI.taoPanelBoxLayoutDoc(380 - gap, 400);
        TaoUI.suaBorderChoPanel(xacNhanNH, 0, 10, 0, 10);
        center.add(xacNhanNH, BorderLayout.CENTER);

        xacNhanNH.add(Box.createRigidArea(new Dimension(0, 80)));

        JPanel infoNH2 = TaoUI.taoPanelBoxLayoutNgang(380 - gap, 65);
        xacNhanNH.add(infoNH2);
        xacNhanNH.add(Box.createVerticalStrut(10));

        JPanel nvNhapInput = TaoUI.taoPanelBoxLayoutDoc(380 - gap, 65);
        JPanel titleNVNhap = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titleNVNhap.add(new JLabel("Nhân viên nhập"));
        nvNhapInput.add(titleNVNhap);

        txtNhanVien = new JTextField();
        txtNhanVien.setText(PhienDangNhap.getUser() != null ? PhienDangNhap.getUser().getMaNV() : "");
        TaoUI.setFixSize(txtNhanVien, 380 - gap, 40);
        nvNhapInput.add(txtNhanVien);
        infoNH2.add(nvNhapInput);

        JPanel infoNH3 = TaoUI.taoPanelBoxLayoutNgang(380 - gap, 65);
        xacNhanNH.add(infoNH3);
        xacNhanNH.add(Box.createVerticalStrut(10));

        JPanel nccInput = TaoUI.taoPanelBoxLayoutDoc(380 - gap, 65);
        JPanel titleNCC = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titleNCC.add(new JLabel("Nhà cung cấp"));
        nccInput.add(titleNCC);

        cbNhaCungCap = new JComboBox<>();
        NhaCungCapBUS nhaCungCapBUS = NhaCungCapBUS.getNhaCungCapBUS();
        cbNhaCungCap.addItem("Nhà cung cấp");
        for (String tenNCC : nhaCungCapBUS.layLuaChonNCCSanPham()) {
            cbNhaCungCap.addItem(tenNCC);
        }
        TaoUI.setFixSize(cbNhaCungCap, 380 - gap, 40);
        nccInput.add(cbNhaCungCap);
        infoNH3.add(nccInput);

        JPanel infoNH4 = TaoUI.taoPanelBoxLayoutDoc(380 - gap, 90);
        xacNhanNH.add(infoNH4);

        JPanel titleTongTien = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        JLabel lblTitle = new JLabel("Tổng tiền: ");

        lblTongTienHienThi = new JLabel("0 VNĐ");
        lblTongTienHienThi.setForeground(Color.RED);
        lblTongTienHienThi.setFont(new Font("Arial", Font.BOLD, 18));

        titleTongTien.add(lblTitle);
        titleTongTien.add(lblTongTienHienThi);
        infoNH4.add(titleTongTien);

        infoNH4.add(Box.createVerticalStrut(10));

        JPanel panelBtn = new JPanel();
        panelBtn.setLayout(new BoxLayout(panelBtn, BoxLayout.X_AXIS));

        btnNhapHang = new JButton("NHẬP HÀNG");
        TaoUI.setFixSize(btnNhapHang, 380 - gap, 40);
        panelBtn.add(btnNhapHang);
        infoNH4.add(panelBtn);

        JTextField[] nonEditFields = { txtLoaiSp, txtTenSp, txtGiaNhap };

        for (JTextField field : nonEditFields) {
            field.setEditable(false);
            field.setBackground(Color.WHITE);
        }
        txtMaSp.setEditable(false);
        txtNhanVien.setEditable(false);
        ganSuKien();
        loadDuLieu();
        suaLaiGiaoDienTheoQuyen();
    }

    /**
     * Cập nhật hiển thị: Ẩn hoàn toàn các nút tác vụ nếu không có quyền THÊM phiếu
     * nhập sản phẩm
     */
    public void suaLaiGiaoDienTheoQuyen() {
        var listQuyen = PhienDangNhap.getListQuyen();

        // Kiểm tra quyền lập phiếu nhập kho (NK_TAO)
        if (!listQuyen.contains("NK_TAO")) {
            // Ẩn nút xác nhận nhập hàng (bước cuối cùng)
            if (btnNhapHang != null)
                btnNhapHang.setVisible(false);

            // Ẩn nút thêm sản phẩm vào danh sách chờ nhập
            if (themSpPNHBtn != null)
                themSpPNHBtn.setVisible(false);

            // Ẩn nút xóa sản phẩm khỏi danh sách chờ nhập
            if (xoaCTBtn != null)
                xoaCTBtn.setVisible(false);

            // Cập nhật tiêu đề để người dùng biết họ chỉ có thể xem dữ liệu
            this.setTitle("Xem thông tin nhập kho sản phẩm (Chế độ chỉ đọc)");
        }
    }

    public void loadDuLieu() {
        NhaCungCapBUS nhaCungCapBUS = NhaCungCapBUS.getNhaCungCapBUS();
        modelKhoHang.setRowCount(0);

        NhaCungCap nhaCungCap = nhaCungCapBUS.timNhaCungCapTheoTen(cbNhaCungCap.getSelectedItem().toString());
        SanPhamBUS sanPhamBUS = SanPhamBUS.getSanPhamBUS();
        LoSanPhamBUS loSanPhamBUS = new LoSanPhamBUS();
        if (nhaCungCap != null) {
            for (ChiTietNhaCungCap chiTietNhaCungCap : nhaCungCap.getListChiTietNhaCungCap()) {
                if (chiTietNhaCungCap.getLoaiDoiTuong().equals("Sản phẩm")) {
                    SanPham sanPham = sanPhamBUS.timSanPham(chiTietNhaCungCap.getMaDoiTuong());
                    if (sanPham.getTenSP().contains(search_Item.getTextSearch())) {
                        modelKhoHang.addRow(
                                new Object[] { sanPham.getMaSP(), sanPham.getTenSP(), chiTietNhaCungCap.getGiaNhap(),
                                        loSanPhamBUS.laySoLuongSanPhamTrongKho(sanPham.getMaSP()),
                                        sanPham.getLoaiNuoc() });
                    }

                }
            }
        }
    }

    private void lamMoi() {
        txtGiaNhap.setText("");
        txtMaSp.setText("");
        txtLoaiSp.setText("");
        txtSoLuong.setText("");
        txtTenSp.setText("");
        modelChiTietPhieuNhap.setRowCount(0);
        lblTongTienHienThi.setText("0 VNĐ");
    }

    private double tinhTongTienNhap() {
        double tong = 0;
        for (int i = 0; i < modelChiTietPhieuNhap.getRowCount(); i++) {
            tong += Double.parseDouble(modelChiTietPhieuNhap.getValueAt(i, 2).toString())
                    * Double.parseDouble(modelChiTietPhieuNhap.getValueAt(i, 3).toString());
        }
        return tong;
    }

    private void ganSuKien() {
        tblKhoHang.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tblKhoHang.getSelectedRow();

                if (selectedRow != -1) {
                    String maSp = modelKhoHang.getValueAt(selectedRow, 0).toString();
                    String tenSp = modelKhoHang.getValueAt(selectedRow, 1).toString();
                    String giaNhap = modelKhoHang.getValueAt(selectedRow, 2).toString();
                    String loaiNuoc = modelKhoHang.getValueAt(selectedRow, 4).toString();
                    txtMaSp.setText(maSp);
                    txtTenSp.setText(tenSp);
                    txtGiaNhap.setText(giaNhap);

                    txtLoaiSp.setText(loaiNuoc);

                    txtSoLuong.requestFocus();
                    txtSoLuong.selectAll();
                }
            }
        });

        cbNhaCungCap.addActionListener(e -> {
            lamMoi();
            loadDuLieu();
        });

        themSpPNHBtn.addActionListener(e -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            String ngaySx = (txtNgaySx.getDate() != null) ? sdf.format(txtNgaySx.getDate()) : "";
            String hanSD = (txtHanSuDung.getDate() != null) ? sdf.format(txtHanSuDung.getDate()) : "";
            modelChiTietPhieuNhap.addRow(new Object[] { txtMaSp.getText(), txtTenSp.getText(), txtGiaNhap.getText(),
                    txtSoLuong.getText(), ngaySx, hanSD });
            lblTongTienHienThi.setText(tinhTongTienNhap() + " VNĐ");

        });

        xoaCTBtn.addActionListener(e -> {
            int dongChon = tblChiTietPhieuNhap.getSelectedRow();
            if (dongChon >= 0) {
                modelChiTietPhieuNhap.removeRow(dongChon);
                lblTongTienHienThi.setText(tinhTongTienNhap() + " VNĐ");

            } else {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn dòng để xóa", "Thông báo",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        btnNhapHang.addActionListener(e -> {
            PhieuNhapSanPham phieuNhapSanPham = dongGoiPhieuNhapSanPham();
            PhieuNhapSanPhamBUS phieuNhapSanPhamBUS = PhieuNhapSanPhamBUS.getPhieuNhapSanPhamBUS();

            if (phieuNhapSanPhamBUS.themPhieuNhapSanPham(phieuNhapSanPham)) {
                TaoTinNhan.showAutoCloseMessage("Thêm phiếu nhập thành công", "Thông báo", 1);
                nhapKhoSanPhamPanel.loadDuLieu();
            } else {
                TaoTinNhan.showAutoCloseMessage("Thêm phiếu nhập thất bại", "Thông báo", 1);
            }
            dispose();

        });

        search_Item.setEvent(() -> {
            loadDuLieu();
        });
    }

    private Double layTongChiPhi(String tien) {
        try {
            return Double.parseDouble(tien.substring(0, tien.length() - 4));
        } catch (Exception e) {
            return 0.0;
        }
    }

    public PhieuNhapSanPham dongGoiPhieuNhapSanPham() {
        PhieuNhapSanPham phieuNhapSanPham = new PhieuNhapSanPham();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        NhaCungCapBUS nhaCungCapBUS = NhaCungCapBUS.getNhaCungCapBUS();

        phieuNhapSanPham.setNgayNhap(sdf.format(new Date()));
        phieuNhapSanPham.setMaNV(txtNhanVien.getText());
        phieuNhapSanPham.setTongTien(layTongChiPhi(lblTongTienHienThi.getText()));
        NhaCungCap nhaCungCap = nhaCungCapBUS.timNhaCungCapTheoTen(cbNhaCungCap.getSelectedItem().toString());
        phieuNhapSanPham.setMaNCC(nhaCungCap != null ? nhaCungCap.getMaNCC() : "");
        phieuNhapSanPham.setTrangThaiXuLy("Đang xử lý");

        ArrayList<LoSanPham> listLoSanPham = new ArrayList<>();
        for (int i = 0; i < modelChiTietPhieuNhap.getRowCount(); i++) {
            LoSanPham loSanPham = new LoSanPham();
            loSanPham.setMaSP(modelChiTietPhieuNhap.getValueAt(i, 0).toString());
            loSanPham.setGiaNhap(Double.parseDouble(modelChiTietPhieuNhap.getValueAt(i, 2).toString()));
            loSanPham.setSoLuong(Double.parseDouble(modelChiTietPhieuNhap.getValueAt(i, 3).toString()));
            loSanPham.setNgayNhap(sdf.format(new Date()));
            loSanPham.setNgaySanXuat(modelChiTietPhieuNhap.getValueAt(i, 4).toString());
            loSanPham.setHanSuDung(modelChiTietPhieuNhap.getValueAt(i, 5).toString());
            listLoSanPham.add(loSanPham);
        }
        phieuNhapSanPham.setListLoSanPham(listLoSanPham);
        return phieuNhapSanPham;
    }
}