package ui.nhapkho.nguyenlieu;

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

import bus.LoNguyenLieuBUS;
import bus.NhaCungCapBUS;
import bus.PhieuNhapNguyenLieuBUS;
import bus.NguyenLieuBUS;
import dto.ChiTietNhaCungCap;
import dto.LoNguyenLieu;
import dto.NhaCungCap;
import dto.PhieuNhapNguyenLieu;
import dto.NguyenLieu;
import ui.component.Search_Item;
import ui.login.PhienDangNhap;
import util.TaoTinNhan;
import util.TaoUI;

public class NhapKhoNguyenLieuDialog extends JDialog {
    Search_Item search_Item;
    private JTextField txtMaNl;
    private JTextField txtTenNl;
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
    private JButton themNlPNHBtn, xoaCTBtn;

    NhapKhoNguyenLieuPanel nhapKhoNguyenLieuPanel;

    public NhapKhoNguyenLieuDialog(NhapKhoNguyenLieuPanel nhapKhoNguyenLieuPanel) {
        super((Frame) null, "Nhập Kho Nguyên Liệu", true);
        setSize(900, 710);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        this.nhapKhoNguyenLieuPanel = nhapKhoNguyenLieuPanel;
        JPanel top = new JPanel();
        TaoUI.suaBorderChoPanel(top, 0, 0, 5, 0);
        TaoUI.setFixSize(top, 3000, 320);
        add(top, BorderLayout.NORTH);

        JPanel center = new JPanel();
        add(center, BorderLayout.CENTER);

        JPanel listNlPanel = TaoUI.taoPanelBorderLayout(450, 3000);

        modelKhoHang = new DefaultTableModel();
        modelKhoHang.addColumn("Mã NL");
        modelKhoHang.addColumn("Tên NL");
        modelKhoHang.addColumn("Giá nhập");
        modelKhoHang.addColumn("SL");

        JScrollPane scroll = TaoUI.taoTableScroll(modelKhoHang);

        tblKhoHang = (JTable) scroll.getViewport().getView();

        tblKhoHang.getColumnModel().getColumn(0).setPreferredWidth(80);
        tblKhoHang.getColumnModel().getColumn(1).setPreferredWidth(180);
        tblKhoHang.getColumnModel().getColumn(2).setPreferredWidth(100);
        tblKhoHang.getColumnModel().getColumn(3).setPreferredWidth(70);

        listNlPanel.add(scroll, BorderLayout.CENTER);
        search_Item = new Search_Item(Integer.MAX_VALUE, 30);
        listNlPanel.add(search_Item, BorderLayout.NORTH);

        JPanel chiTietNl = TaoUI.taoPanelBoxLayoutDoc(3000, 3000);
        top.setLayout(new BorderLayout());
        top.add(listNlPanel, BorderLayout.WEST);
        top.add(chiTietNl, BorderLayout.CENTER);
        TaoUI.suaBorderChoPanel(chiTietNl, 0, 20, 0, 20);

        JPanel info1 = TaoUI.taoPanelBoxLayoutNgang(380, 65);

        JPanel maNlInput = TaoUI.taoPanelBoxLayoutDoc(150, 65);
        JPanel titleMaNl = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titleMaNl.add(new JLabel("Mã NL"));
        maNlInput.add(titleMaNl);

        txtMaNl = new JTextField();
        TaoUI.setFixSize(txtMaNl, 3000, 40);
        maNlInput.add(txtMaNl);
        info1.add(maNlInput);

        info1.add(Box.createHorizontalGlue());
        chiTietNl.add(info1);

        JPanel info2 = TaoUI.taoPanelFlowLayout(380, 65, 0, 0);
        JPanel tenNlInput = TaoUI.taoPanelBoxLayoutDoc(380, 65);
        JPanel titleTenNl = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titleTenNl.add(new JLabel("Tên nguyên liệu"));

        txtTenNl = new JTextField();
        TaoUI.setFixSize(txtTenNl, 400, 40);
        tenNlInput.add(titleTenNl);
        tenNlInput.add(Box.createVerticalStrut(5));
        tenNlInput.add(txtTenNl);
        info2.add(tenNlInput);
        chiTietNl.add(info2);

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
        chiTietNl.add(info3);

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
        chiTietNl.add(info4);

        themNlPNHBtn = new JButton("Thêm vào phiếu");
        TaoUI.setFixSize(themNlPNHBtn, 130, 35);
        JPanel ctnThemNl = new JPanel(new FlowLayout(FlowLayout.LEFT));
        TaoUI.suaBorderChoPanel(ctnThemNl, 0, 3, 0, 0);
        ctnThemNl.add(themNlPNHBtn);
        chiTietNl.add(Box.createRigidArea(new Dimension(0, 10)));
        chiTietNl.add(ctnThemNl);

        JPanel chiTietPhieuNhapPanel = TaoUI.taoPanelBorderLayout(500, 3000);

        modelChiTietPhieuNhap = new DefaultTableModel();
        modelChiTietPhieuNhap.addColumn("Mã NL");
        modelChiTietPhieuNhap.addColumn("Tên NL");
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
        JLabel titleCTPNJLabel = new JLabel("Danh sách các nguyên liệu nhập");
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
        
        for (String tenNCC : nhaCungCapBUS.layLuaChonNCCNguyenLieu()) {
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

        JTextField[] nonEditFields = { txtTenNl, txtGiaNhap };

        for (JTextField field : nonEditFields) {
            field.setEditable(false);
            field.setBackground(Color.WHITE);
        }
        txtMaNl.setEditable(false);
        txtNhanVien.setEditable(false);
        ganSuKien();
        loadDuLieu();
    }

    public void loadDuLieu() {
        NhaCungCapBUS nhaCungCapBUS = NhaCungCapBUS.getNhaCungCapBUS();
        modelKhoHang.setRowCount(0);

        NhaCungCap nhaCungCap = nhaCungCapBUS.timNhaCungCapTheoTen(cbNhaCungCap.getSelectedItem().toString());
        NguyenLieuBUS nguyenLieuBUS = NguyenLieuBUS.getNguyenLieuBUS();
        LoNguyenLieuBUS loNguyenLieuBUS = new LoNguyenLieuBUS();
        if (nhaCungCap != null) {
            for (ChiTietNhaCungCap chiTietNhaCungCap : nhaCungCap.getListChiTietNhaCungCap()) {
                // Kiểm tra loại đối tượng là "Nguyên liệu"
                if (chiTietNhaCungCap.getLoaiDoiTuong().equals("Nguyên liệu")) {
                    NguyenLieu nguyenLieu = nguyenLieuBUS.timNguyenLieu(chiTietNhaCungCap.getMaDoiTuong());
                    modelKhoHang.addRow(
                            new Object[] { nguyenLieu.getMaNL(), nguyenLieu.getTenNL(), chiTietNhaCungCap.getGiaNhap(),
                                    loNguyenLieuBUS.laySoLuongNguyenLieuTrongKho(nguyenLieu.getMaNL()) });
                }
            }
        }
    }

    private void lamMoi() {
        txtGiaNhap.setText("");
        txtMaNl.setText("");
        txtSoLuong.setText("");
        txtTenNl.setText("");
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
                    String maNl = modelKhoHang.getValueAt(selectedRow, 0).toString();
                    String tenNl = modelKhoHang.getValueAt(selectedRow, 1).toString();
                    String giaNhap = modelKhoHang.getValueAt(selectedRow, 2).toString();
                    
                    txtMaNl.setText(maNl);
                    txtTenNl.setText(tenNl);
                    txtGiaNhap.setText(giaNhap);

                    txtSoLuong.requestFocus();
                    txtSoLuong.selectAll();
                }
            }
        });

        cbNhaCungCap.addActionListener(e -> {
            lamMoi();
            loadDuLieu();
        });

        themNlPNHBtn.addActionListener(e -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            String ngaySx = (txtNgaySx.getDate() != null) ? sdf.format(txtNgaySx.getDate()) : "";
            String hanSD = (txtHanSuDung.getDate() != null) ? sdf.format(txtHanSuDung.getDate()) : "";
            modelChiTietPhieuNhap.addRow(new Object[] { txtMaNl.getText(), txtTenNl.getText(), txtGiaNhap.getText(),
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
            PhieuNhapNguyenLieu phieuNhapNguyenLieu = dongGoiPhieuNhapNguyenLieu();
            PhieuNhapNguyenLieuBUS phieuNhapNguyenLieuBUS = PhieuNhapNguyenLieuBUS.getPhieuNhapNguyenLieuBUS();

            if (phieuNhapNguyenLieuBUS.themPhieuNhapNguyenLieu(phieuNhapNguyenLieu)) {
                TaoTinNhan.showAutoCloseMessage("Thêm phiếu nhập thành công", "Thông báo", 1);
                nhapKhoNguyenLieuPanel.loadDuLieu();
            } else {
                TaoTinNhan.showAutoCloseMessage("Thêm phiếu nhập thất bại", "Thông báo", 1);
            }
            dispose();
        });
    }

    private Double layTongChiPhi(String tien) {
        try {
            return Double.parseDouble(tien.substring(0, tien.length() - 4));
        } catch (Exception e) {
            return 0.0;
        }
    }

    public PhieuNhapNguyenLieu dongGoiPhieuNhapNguyenLieu() {
        PhieuNhapNguyenLieu phieuNhapNguyenLieu = new PhieuNhapNguyenLieu();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        NhaCungCapBUS nhaCungCapBUS = NhaCungCapBUS.getNhaCungCapBUS();

        phieuNhapNguyenLieu.setNgayNhap(sdf.format(new Date()));
        phieuNhapNguyenLieu.setMaNV(txtNhanVien.getText());
        phieuNhapNguyenLieu.setTongTien(layTongChiPhi(lblTongTienHienThi.getText()));
        NhaCungCap nhaCungCap = nhaCungCapBUS.timNhaCungCapTheoTen(cbNhaCungCap.getSelectedItem().toString());
        phieuNhapNguyenLieu.setMaNCC(nhaCungCap != null ? nhaCungCap.getMaNCC() : "");
        phieuNhapNguyenLieu.setTrangThaiXuLy("Đang xử lí");

        ArrayList<LoNguyenLieu> listLoNguyenLieu = new ArrayList<>();
        for (int i = 0; i < modelChiTietPhieuNhap.getRowCount(); i++) {
            LoNguyenLieu loNguyenLieu = new LoNguyenLieu();
            loNguyenLieu.setMaNL(modelChiTietPhieuNhap.getValueAt(i, 0).toString());
            loNguyenLieu.setGiaNhap(Double.parseDouble(modelChiTietPhieuNhap.getValueAt(i, 2).toString()));
            loNguyenLieu.setSoLuong(Double.parseDouble(modelChiTietPhieuNhap.getValueAt(i, 3).toString()));
            loNguyenLieu.setNgayNhap(sdf.format(new Date()));
            loNguyenLieu.setNgaySanXuat(modelChiTietPhieuNhap.getValueAt(i, 4).toString());
            loNguyenLieu.setHanSuDung(modelChiTietPhieuNhap.getValueAt(i, 5).toString());
            listLoNguyenLieu.add(loNguyenLieu);
        }
        phieuNhapNguyenLieu.setListLoNguyenLieu(listLoNguyenLieu);
        return phieuNhapNguyenLieu;
    }
}