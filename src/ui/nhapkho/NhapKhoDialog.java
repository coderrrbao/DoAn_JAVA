package ui.nhapkho;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import ui.component.Search_Item;
import util.TaoUI;

public class NhapKhoDialog extends JDialog {
    Search_Item search_Item;
    private JTextField txtMaSp;
    private JTextField txtLoaiSp;
    private JTextField txtTenSp;
    private JTextField txtGiaNhap;
    private JTextField txtSoLuong;

    private JTextField txtMaPN;
    private JTextField txtTongSP;
    private JTextField txtNhanVien;
    private JComboBox<String> cbNhaCungCap;

    private JLabel lblTongTienHienThi;
    private JButton btnNhapHang;

    private JTable tblKhoHang;
    private DefaultTableModel modelKhoHang;

    private JTable tblChiTietPhieuNhap;
    private DefaultTableModel modelChiTietPhieuNhap;
    private JButton themSpPNHBtn;
    public NhapKhoDialog(Frame owner) {
        super(owner, "Nhập Kho Sản Phẩm", true);
        setSize(900, 680);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        JPanel top = new JPanel();
        TaoUI.suaBorderChoPanel(top, 0, 0, 5, 0);
        TaoUI.setFixSize(top, 3000, 300);
        add(top, BorderLayout.NORTH);

        JPanel center = new JPanel();
        add(center, BorderLayout.CENTER);

        JPanel listSpPanel = TaoUI.taoPanelBorderLayout(450, 3000);

        modelKhoHang = new DefaultTableModel();
        modelKhoHang.addColumn("Mã sp");
        modelKhoHang.addColumn("Tên sp");
        modelKhoHang.addColumn("Giá nhập");
        modelKhoHang.addColumn("Số lượng");

        Object[][] data = {
                { "SP001", "Sting Dâu", 8500, 24 },
                { "SP002", "Coca Cola", 9000, 50 },
        };
        for (Object[] row : data)
            modelKhoHang.addRow(row);

        JScrollPane scroll = TaoUI.taoTableScroll(modelKhoHang);

        tblKhoHang = (JTable) scroll.getViewport().getView();

        tblKhoHang.getColumnModel().getColumn(0).setPreferredWidth(80);
        tblKhoHang.getColumnModel().getColumn(1).setPreferredWidth(200);
        tblKhoHang.getColumnModel().getColumn(2).setPreferredWidth(100);
        tblKhoHang.getColumnModel().getColumn(3).setPreferredWidth(70);

        listSpPanel.add(scroll, BorderLayout.CENTER);
        search_Item = new Search_Item(Integer.MAX_VALUE, 30);
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

        themSpPNHBtn = new JButton("Thêm vào phiếu");
        JPanel ctnThemSp = new JPanel(new FlowLayout(FlowLayout.LEFT));
        TaoUI.suaBorderChoPanel(ctnThemSp, 0,8,0,0);
        ctnThemSp.add(themSpPNHBtn);
        chiTietSp.add(Box.createRigidArea(new Dimension(0,20)));
        chiTietSp.add(ctnThemSp);

        JPanel chiTietPhieuNhapPanel = TaoUI.taoPanelBorderLayout(500, 3000);

        modelChiTietPhieuNhap = new DefaultTableModel();
        modelChiTietPhieuNhap.addColumn("Mã sp");
        modelChiTietPhieuNhap.addColumn("Tên sp");
        modelChiTietPhieuNhap.addColumn("Giá nhập");
        modelChiTietPhieuNhap.addColumn("Số lượng");
        modelChiTietPhieuNhap.addColumn("Loại sản phẩm");

        modelChiTietPhieuNhap.addRow(new Object[] { "SP001", "Bút bi Thiên Long", 2500, 100, "Văn phòng phẩm" });

        JScrollPane scrollPaneCTPN = TaoUI.taoTableScroll(modelChiTietPhieuNhap);
        tblChiTietPhieuNhap = (JTable) scrollPaneCTPN.getViewport().getView();

        JPanel titleCTPN = TaoUI.taoPanelBoxLayoutNgang(3000, 60);
        TaoUI.suaBorderChoPanel(titleCTPN, 0, 20, 0, 0);
        JLabel titleCTPNJLabel = new JLabel("Danh sách các sản phẩm nhập");
        titleCTPNJLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleCTPN.add(titleCTPNJLabel);

        chiTietPhieuNhapPanel.add(scrollPaneCTPN, BorderLayout.CENTER);
        chiTietPhieuNhapPanel.add(titleCTPN, BorderLayout.NORTH);

        center.setLayout(new BorderLayout());
        center.add(chiTietPhieuNhapPanel, BorderLayout.WEST);
        TaoUI.suaBorderChoPanel(chiTietPhieuNhapPanel, 0, 0, 8, 0);

        int gap = 20;
        JPanel xacNhanNH = TaoUI.taoPanelBoxLayoutDoc(380 - gap, 400);
        TaoUI.suaBorderChoPanel(xacNhanNH, 0, 10, 0, 10);
        center.add(xacNhanNH, BorderLayout.CENTER);

        JPanel infoNH1 = TaoUI.taoPanelBoxLayoutNgang(380 - gap, 65);
        xacNhanNH.add(infoNH1);
        xacNhanNH.add(Box.createVerticalStrut(10));

        JPanel maPNInput = TaoUI.taoPanelBoxLayoutDoc(150, 65);
        JPanel titleMaPN = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titleMaPN.add(new JLabel("Mã PN"));
        maPNInput.add(titleMaPN);

        txtMaPN = new JTextField();
        TaoUI.setFixSize(txtMaPN, 150, 40);
        maPNInput.add(txtMaPN);
        infoNH1.add(maPNInput);

        JPanel tongSPInput = TaoUI.taoPanelBoxLayoutDoc(150, 65);
        JPanel titleTongSP = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titleTongSP.add(new JLabel("Tổng SP"));
        tongSPInput.add(titleTongSP);

        txtTongSP = new JTextField();
        TaoUI.setFixSize(txtTongSP, 150, 40);
        tongSPInput.add(txtTongSP);

        infoNH1.add(Box.createHorizontalGlue());
        infoNH1.add(tongSPInput);

        JPanel infoNH2 = TaoUI.taoPanelBoxLayoutNgang(380 - gap, 65);
        xacNhanNH.add(infoNH2);
        xacNhanNH.add(Box.createVerticalStrut(10));

        JPanel nvNhapInput = TaoUI.taoPanelBoxLayoutDoc(380 - gap, 65);
        JPanel titleNVNhap = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titleNVNhap.add(new JLabel("Nhân viên nhập"));
        nvNhapInput.add(titleNVNhap);

        txtNhanVien = new JTextField();
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
        cbNhaCungCap.addItem("NCC A");
        cbNhaCungCap.addItem("NCC B");
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

        JTextField[] nonEditFields = { txtLoaiSp, txtTenSp, txtGiaNhap, txtTongSP, txtNhanVien };

        for (JTextField field : nonEditFields) {
            field.setEditable(false);
            field.setBackground(Color.WHITE);
        }
        txtMaSp.setEditable(false);
        txtMaPN.setEditable(false);
    }

    // public static void main(String[] args) {
    //     Frame frame = new Frame();
    //     frame.setVisible(true);
    //     Dialog dialog = new NhapKhoDialog(frame);
    //     dialog.setVisible(true);
    // }
}