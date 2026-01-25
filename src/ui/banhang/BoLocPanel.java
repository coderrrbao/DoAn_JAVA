package ui.banhang;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import bus.SanPhamBUS;
import dto.DanhMuc;
import util.TaoUI;

public class BoLocPanel extends JPanel {

    private JTextField txtTenSanPham;
    private JComboBox<String> cbLoaiSanPham;
    private JComboBox<DanhMuc> cbDanhMuc;
    private SanPhamBUS sanPhamBUS;


    private JButton btnApDung;
    private JButton btnLamMoi;

    public BoLocPanel() {

        TaoUI.taoPanelBoxLayoutDoc(this, Integer.MAX_VALUE, 140);
        TaoUI.suaBorderChoPanel(this, 0, 0, 0, 0);


        JPanel titleMain = TaoUI.taoPanelCanGiua(Integer.MAX_VALUE, 40);
        titleMain.setBackground(new Color(225, 235, 245));
        titleMain.add(new JLabel("Bộ lọc"));
        add(titleMain);


        JPanel topPanel = TaoUI.taoPanelFlowLayout(Integer.MAX_VALUE, 60, 5, 0);


        JPanel pnTenSP = TaoUI.taoPanelBoxLayoutDoc(200, Integer.MAX_VALUE);

        JPanel titleTenSP = TaoUI.taoPanelCanGiua(Integer.MAX_VALUE, 30);
        titleTenSP.add(new JLabel("Tên sản phẩm"));
        pnTenSP.add(titleTenSP);

        txtTenSanPham = new JTextField();
        JPanel searchArea = TaoUI.taoPanelBorderLayout(200, 25);
        searchArea.add(txtTenSanPham, BorderLayout.CENTER);
        pnTenSP.add(searchArea);

        topPanel.add(pnTenSP);


        JPanel pnLoaiSP = TaoUI.taoPanelBoxLayoutDoc(100, Integer.MAX_VALUE);

        JPanel titleLoai = TaoUI.taoPanelCanGiua(Integer.MAX_VALUE, 30);
        titleLoai.add(new JLabel("Loại sản phẩm"));
        pnLoaiSP.add(titleLoai);

        String[] loai = { "Có sẵn", "Pha chế" };
        cbLoaiSanPham = new JComboBox<>(loai);
        sanPhamBUS = new SanPhamBUS();


        cbLoaiSanPham.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        cbLoaiSanPham.setFont(new Font("Arial", Font.PLAIN, 10));
        pnLoaiSP.add(cbLoaiSanPham);

        topPanel.add(pnLoaiSP);


        JPanel pnDanhMuc = TaoUI.taoPanelBoxLayoutDoc(100, Integer.MAX_VALUE);

        JPanel titleDanhMuc = TaoUI.taoPanelCanGiua(Integer.MAX_VALUE, 30);
        titleDanhMuc.add(new JLabel("Danh mục"));
        pnDanhMuc.add(titleDanhMuc);

        cbDanhMuc = new JComboBox<>();
        cbDanhMuc.addItem(new DanhMuc("ALL", "Tất cả"));

        for (DanhMuc dm : sanPhamBUS.layDanhMucDangHoatDong()) {
            cbDanhMuc.addItem(dm);
        }

        cbDanhMuc.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        cbDanhMuc.setFont(new Font("Arial", Font.PLAIN, 10));
        pnDanhMuc.add(cbDanhMuc);

        cbDanhMuc.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        cbDanhMuc.setFont(new Font("Arial", Font.PLAIN, 10));
        pnDanhMuc.add(cbDanhMuc);

        topPanel.add(pnDanhMuc);

        add(topPanel);


        btnApDung = new JButton("Áp dụng");
        btnLamMoi = new JButton("Làm mới");

        JPanel buttonsPanel = TaoUI.taoPanelBoxLayoutNgang(Integer.MAX_VALUE, 30);
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        buttonsPanel.add(btnLamMoi);
        buttonsPanel.add(btnApDung);

        add(buttonsPanel);
        ganSuKien();
    }

    private void ganSuKien() {

        btnApDung.addActionListener(e -> {
            String ten = txtTenSanPham.getText().trim();
            String loai = cbLoaiSanPham.getSelectedItem().toString();
            DanhMuc dm = (DanhMuc) cbDanhMuc.getSelectedItem();

            System.out.println("Áp dụng bộ lọc:");
            System.out.println("Tên: " + ten);
            System.out.println("Loại: " + loai);
            System.out.println("Danh mục: " + dm.getMaDM());
        });

        btnLamMoi.addActionListener(e -> {
            txtTenSanPham.setText("");
            cbLoaiSanPham.setSelectedIndex(0);
            cbDanhMuc.setSelectedIndex(0);

            System.out.println("Đã làm mới bộ lọc");
        });
    }
}
