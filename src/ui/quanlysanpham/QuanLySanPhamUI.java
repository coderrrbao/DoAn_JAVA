package ui.quanlysanpham;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import dto.NhaCungCap;
import dto.SanPham;
import ui.component.Search_Item;
import util.TaoUI;

public class QuanLySanPhamUI extends JPanel {

    private JButton themSpBtn, xuaFileBtn, chuyenPageTraiBtn,chuyenPagePhaibtn;
    private Search_Item search_Item;
    private JComboBox<String> cbLoaiNuoc, cbNhaCungCap;
    private JPanel danhSachSp;
    private JFrame owner;
    private List<SanPham> listSanPhamFull;
    private List<SanPham> listSanPhamLoc;
    private JLabel numberPage;

    public QuanLySanPhamUI(JFrame owner) {
        this.owner = owner;
        this.listSanPhamFull = new ArrayList<>();
        this.listSanPhamLoc = new ArrayList<>();

        setLayout(new BorderLayout());

        initTopBar();
        initMainContent();
        ganSuKienChoNut();

        loadDataFromDatabase();
    }

    private void initTopBar() {
        JPanel top = TaoUI.taoPanelBoxLayoutNgang(3000, 45);
        top.setBackground(Color.WHITE);
        top.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        String[] loai = { "Loại nước", "Pha chế", "Có sẵn" };
        cbLoaiNuoc = new JComboBox<>(loai);
        cbLoaiNuoc.setMaximumSize(new Dimension(130, 32));

        String[] ncc = { "Nhà cung cấp", "Pepsi", "Coca Cola", "Lavie" };
        cbNhaCungCap = new JComboBox<>(ncc);
        cbNhaCungCap.setMaximumSize(new Dimension(150, 32));

        search_Item = new Search_Item(300, 32);

        themSpBtn = new JButton("Thêm sản phẩm");
        themSpBtn.setBackground(new Color(40, 167, 69));
        themSpBtn.setForeground(Color.WHITE);
        TaoUI.setHeightButton(themSpBtn, 32);

        xuaFileBtn = new JButton("Xuất Excel");
        TaoUI.setHeightButton(xuaFileBtn, 32);

        top.add(cbLoaiNuoc);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(cbNhaCungCap);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(search_Item);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(themSpBtn);
        top.add(Box.createRigidArea(new Dimension(5, 0)));
        top.add(xuaFileBtn);
        top.add(Box.createHorizontalGlue());

        add(top, BorderLayout.NORTH);
    }

    private void initMainContent() {
        danhSachSp = TaoUI.taoPanelFlowLayout(450, 850, 10, 10);
        danhSachSp.setBackground(new Color(242, 242, 242));
        JPanel content = new JPanel(new BorderLayout());
        
        content.add(taoThanhPhanTrang(),BorderLayout.SOUTH);
        content.add(danhSachSp, BorderLayout.CENTER);
        JScrollPane scrollPane = TaoUI.taoScrollPane(content);
        add(scrollPane,BorderLayout.CENTER);
    }

    public JPanel taoThanhPhanTrang(){
        JPanel thanhPTPanel = TaoUI.taoPanelCanGiua(450, 30);
        for (int i=1 ;i<10;i++){
            JButton btn = new JButton(String.valueOf(i));
            TaoUI.addItem(thanhPTPanel, btn, 10, true);
        }
        return thanhPTPanel;
    }

    private void ganSuKienChoNut() {
        themSpBtn.addActionListener(e -> {
            NhaCungCap nccPepsi = new NhaCungCap("NCC01", "Suntory PepsiCo", "TP. Hồ Chí Minh", "0123456789");
            SanPham sp = new SanPham();
            sp.setMa("SP001");
            sp.setTen("Pepsi Lon ");
            sp.setLoaiNuoc("Nước ngọt");
            sp.setGiaBan(99999);
            sp.setDungTichMl(320);
            sp.setSoLuongTon(100);
            sp.setAnh("/assets/img/pepsi.png");

            sp.setNhaCungCap(nccPepsi);

            sp.setTrangThai(true);
            JDialog ctSpDialog = new ChiTietSanPhamDialog(sp);
        });

        cbLoaiNuoc.addActionListener(e -> locSanPham());
        cbNhaCungCap.addActionListener(e -> locSanPham());
    }

    public void loadDataFromDatabase() {

        for (int i = 1; i <= 27; i++) {
            SanPham sp = new SanPham();
            sp.setTen("Pepsi Lon " + i);
            sp.setAnh("/assets/img/pepsi.png");
            listSanPhamFull.add(sp);
        }
        veLaiDanhSach(listSanPhamFull);
    }

    private void locSanPham() {

    }

    private void veLaiDanhSach(List<SanPham> list) {
        danhSachSp.removeAll();
        for (SanPham sp : list) {
            danhSachSp.add(new SanPhamItem(sp));
        }
        danhSachSp.revalidate();
        danhSachSp.repaint();
    }
}