package ui.quanlysanpham;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import bus.QuanLySanPhamBUS;
import dto.NhaCungCap;
import dto.SanPham;
import ui.component.Search_Item;
import util.TaoUI;

public class QuanLySanPhamUI extends JPanel {

    private JButton themSpBtn, xuaFileBtn, chuyenPageTraiBtn, chuyenPagePhaibtn;
    private Search_Item search_Item;
    private JComboBox<String> cbLoaiNuoc, cbNhaCungCap;
    private JPanel danhSachSp;
    private JFrame owner;
    private ArrayList<SanPham> listSanPham;
    private ArrayList<SanPham> listSanPhamLoc;
    private JLabel numberPage;
    private String[] loai = { "Loại nước", "Có sẵn", "Pha chế" };
    private String[] ncc = new String[0];

    private QuanLySanPhamBUS quanLySanPhamBUS;

    public QuanLySanPhamUI(JFrame owner) {
        this.owner = owner;
        this.listSanPham = new ArrayList<>();
        this.listSanPhamLoc = new ArrayList<>();
        quanLySanPhamBUS = new QuanLySanPhamBUS();

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

        cbLoaiNuoc = new JComboBox<>(loai);
        cbLoaiNuoc.setMaximumSize(new Dimension(130, 32));

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

        content.add(taoThanhPhanTrang(), BorderLayout.SOUTH);
        content.add(danhSachSp, BorderLayout.CENTER);
        JScrollPane scrollPane = TaoUI.taoScrollPane(content);
        add(scrollPane, BorderLayout.CENTER);
    }

    public JPanel taoThanhPhanTrang() {
        JPanel thanhPTPanel = TaoUI.taoPanelCanGiua(450, 30);
        for (int i = 1; i < 10; i++) {
            JButton btn = new JButton(String.valueOf(i));
            TaoUI.addItem(thanhPTPanel, btn, 10, true);
        }
        return thanhPTPanel;
    }

    private void ganSuKienChoNut() {
        themSpBtn.addActionListener(e -> {
            JDialog ctSpDialog = new ChiTietSanPhamDialog(null);
        });

        cbLoaiNuoc.addActionListener(e -> locSanPham());
        cbNhaCungCap.addActionListener(e -> locSanPham());
        search_Item.setEvent(this::locSanPham);
    }

    public void loadDataFromDatabase() {
        listSanPham = quanLySanPhamBUS.layListSanPham();
        ArrayList<String> luaChonNCC = quanLySanPhamBUS.layLuaChonNCC();
        luaChonNCC.add(0, "Nhà cung cấp");
        ncc = luaChonNCC.toArray(new String[0]);
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(ncc);
        cbNhaCungCap.setModel(model);
        veLaiDanhSach(listSanPham);
    }

    private void locSanPham() {
        listSanPhamLoc.clear();
        String luaChonLoaiNuoc = cbLoaiNuoc.getSelectedItem().toString();
        String luaChonNCC = cbNhaCungCap.getSelectedItem().toString();
        for (SanPham sanPham : listSanPham) {
            if ((sanPham.getNhaCungCap().getTenNCC().equals(luaChonNCC) || luaChonNCC.equals("Nhà cung cấp")) &&
                    (sanPham.getLoaiNuoc().equals(luaChonLoaiNuoc) || luaChonLoaiNuoc.equals("Loại nước"))
                    && sanPham.getTrangThai()
                    && sanPham.getTenSP().toUpperCase().contains(search_Item.getTextSearch().trim().toUpperCase())) {
                listSanPhamLoc.add(sanPham);
            }
        }
        veLaiDanhSach(listSanPhamLoc);
    }

    private void veLaiDanhSach(ArrayList<SanPham> list) {
        danhSachSp.removeAll();
        for (SanPham sp : list) {
            danhSachSp.add(new SanPhamItem(sp));
        }
        danhSachSp.revalidate();
        danhSachSp.repaint();
    }
}