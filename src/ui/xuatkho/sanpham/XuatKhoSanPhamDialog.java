package ui.xuatkho.sanpham;

import bus.LoSanPhamBUS;
import bus.PhieuHuySanPhamBUS;
import bus.SanPhamBUS;
import dto.LoSanPham;
import dto.PhieuHuySanPham;
import dto.SanPham;
import ui.component.Search_Item; // Nhớ import Search_Item
import ui.login.PhienDangNhap;
import util.TaoUI;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class XuatKhoSanPhamDialog extends JDialog {
    private JTable tblTonKho, tblChoXuat;
    private DefaultTableModel modelTonKho, modelChoXuat;
    private JTextField txtMaSP, txtSoLuongXuat, txtMaLo, txtLyDo;
    private JButton btnThem, btnXacNhan;
    private Search_Item search_Item; // Khai báo Search_Item
    private XuatKhoSanPhamPanel parentPanel;

    public XuatKhoSanPhamDialog(XuatKhoSanPhamPanel parent) {
        super((Frame) null, "Tạo Phiếu Hủy Sản Phẩm", true);
        this.parentPanel = parent;
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel main = new JPanel(new GridLayout(1, 2, 10, 0));

        JPanel left = new JPanel(new BorderLayout(0, 10));

        search_Item = new Search_Item(250, 32);

        left.add(search_Item, BorderLayout.NORTH);

        modelTonKho = new DefaultTableModel(new String[] { "Mã SP", "Mã Lô", "Hạn SD", "Tồn", "Giá Nhập" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JScrollPane scrollTonKho = TaoUI.taoTableScroll(modelTonKho);
        tblTonKho = (JTable) scrollTonKho.getViewport().getView();
        tblTonKho.getTableHeader().setReorderingAllowed(false);

        left.add(scrollTonKho, BorderLayout.CENTER);

        // ==================== BÊN PHẢI: FORM VÀ DANH SÁCH CHỜ ====================
        JPanel right = new JPanel(new BorderLayout(0, 10));

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 10));

        // Đã xóa txtMaNV
        txtMaSP = new JTextField();
        txtMaSP.setEditable(false);
        txtMaLo = new JTextField();
        txtMaLo.setEditable(false);
        txtSoLuongXuat = new JTextField();
        txtLyDo = new JTextField();

        form.add(taoDong("Mã Sản Phẩm:", txtMaSP));
        form.add(taoDong("Mã Lô:", txtMaLo));
        form.add(taoDong("Số lượng hủy:", txtSoLuongXuat));
        form.add(taoDong("Lý do hủy:", txtLyDo));

        btnThem = new JButton("Thêm vào danh sách chờ");
        JPanel pnBtnThem = new JPanel();
        pnBtnThem.setLayout(new BoxLayout(pnBtnThem, BoxLayout.X_AXIS));
        pnBtnThem.add(btnThem);
        TaoUI.setFixSize(btnThem, 475, 32);

        form.add(pnBtnThem);

        modelChoXuat = new DefaultTableModel(new String[] { "Mã SP", "Tên SP", "SL Hủy", "Mã Lô", "Giá Nhập" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JScrollPane scrollChoXuat = TaoUI.taoTableScroll(modelChoXuat);
        tblChoXuat = (JTable) scrollChoXuat.getViewport().getView();
        tblChoXuat.getTableHeader().setReorderingAllowed(false);

        btnXacNhan = new JButton("XÁC NHẬN");
        btnXacNhan.setBackground(new Color(220, 53, 69));
        btnXacNhan.setForeground(Color.WHITE);
        btnXacNhan.setPreferredSize(new Dimension(0, 40));
        btnXacNhan.setFont(new Font("Arial", Font.BOLD, 14));

        JPanel pnXacNhan = new JPanel(new BorderLayout());
        pnXacNhan.add(btnXacNhan, BorderLayout.CENTER);

        right.add(form, BorderLayout.NORTH);
        right.add(scrollChoXuat, BorderLayout.CENTER);
        right.add(pnXacNhan, BorderLayout.SOUTH);



        main.add(left);
        main.add(right);
        add(main, BorderLayout.CENTER);

        loadData();
        ganSuKien();
        suaLaiGiaoDienTheoQuyen();
    }

    private JPanel taoDong(String tenLabel, JComponent comp) {
        comp.setPreferredSize(new Dimension(Integer.MAX_VALUE, 30));

        JPanel pn = new JPanel(new BorderLayout(0, 5));
        pn.add(new JLabel(tenLabel), BorderLayout.NORTH);
        pn.add(comp, BorderLayout.CENTER);
        pn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        pn.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        return pn;
    }

    public void suaLaiGiaoDienTheoQuyen() {
        var listQuyen = ui.login.PhienDangNhap.getListQuyen();

        if (!listQuyen.contains("XK_TAO")) {
            if (btnThem != null)
                btnThem.setVisible(false);
            if (btnXacNhan != null)
                btnXacNhan.setVisible(false);

            txtSoLuongXuat.setEditable(false);
            txtLyDo.setEditable(false);

            this.setTitle("Xem thông tin tạo phiếu hủy (Chế độ chỉ đọc)");
        }
        this.revalidate();
        this.repaint();
    }

    private void loadData() {
        modelTonKho.setRowCount(0);
        String keyword = search_Item != null && search_Item.getTextSearch() != null
                ? search_Item.getTextSearch().toString().toLowerCase()
                : "";

        ArrayList<LoSanPham> list = LoSanPhamBUS.getLoSanPhamBUS().layListLoSanPham();
        for (LoSanPham lo : list) {
            if (lo.getSoLuong() > 0 && lo.getMaLoSP().toLowerCase().contains(keyword)) {
                modelTonKho.addRow(new Object[] {
                        lo.getMaSP(), lo.getMaLoSP(), lo.getHanSuDung(), lo.getSoLuong(), lo.getGiaNhap()
                });
            }
        }
    }

    private void ganSuKien() {

        search_Item.setEvent(() -> {
            loadData();
        });

        tblTonKho.getSelectionModel().addListSelectionListener(e -> {
            int r = tblTonKho.getSelectedRow();
            if (r != -1) {
                txtMaSP.setText(modelTonKho.getValueAt(r, 0).toString());
                txtMaLo.setText(modelTonKho.getValueAt(r, 1).toString());
                txtSoLuongXuat.requestFocus();
            }
        });

        btnThem.addActionListener(e -> {
            try {
                int r = tblTonKho.getSelectedRow();
                if (r == -1) {
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm từ kho!");
                    return;
                }

                double sl = Double.parseDouble(txtSoLuongXuat.getText());
                double tonKho = Double.parseDouble(modelTonKho.getValueAt(r, 3).toString());
                String maLo = txtMaLo.getText();

                if (sl <= 0 || sl > tonKho) {
                    JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ hoặc vượt quá tồn kho!");
                    return;
                }

                // Kiểm tra cộng dồn nếu mã lô đã có trong bảng chờ xuất
                boolean daTonTai = false;
                for (int i = 0; i < modelChoXuat.getRowCount(); i++) {
                    if (modelChoXuat.getValueAt(i, 3).toString().equals(maLo)) {
                        double slCu = Double.parseDouble(modelChoXuat.getValueAt(i, 2).toString());
                        if ((slCu + sl) > tonKho) {
                            JOptionPane.showMessageDialog(this, "Tổng số lượng xuất vượt quá tồn kho!");
                            return;
                        }
                        modelChoXuat.setValueAt(slCu + sl, i, 2);
                        daTonTai = true;
                        break;
                    }
                }

                if (!daTonTai) {
                    SanPham sp = SanPhamBUS.getSanPhamBUS().timSanPham(txtMaSP.getText());
                    modelChoXuat.addRow(new Object[] {
                            txtMaSP.getText(),
                            (sp != null ? sp.getTenSP() : "SP"),
                            sl,
                            maLo,
                            modelTonKho.getValueAt(r, 4)
                    });
                }
                txtSoLuongXuat.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng hợp lệ!");
            }
        });

        btnXacNhan.addActionListener(e -> {
            if (modelChoXuat.getRowCount() == 0 || txtLyDo.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập lý do và chọn ít nhất 1 sản phẩm để hủy!");
                return;
            }
            double tongTien = 0;
            Object[][] data = new Object[modelChoXuat.getRowCount()][5];
            for (int i = 0; i < modelChoXuat.getRowCount(); i++) {
                for (int j = 0; j < 5; j++)
                    data[i][j] = modelChoXuat.getValueAt(i, j);
                tongTien += Double.parseDouble(data[i][2].toString()) * Double.parseDouble(data[i][4].toString());
            }

            PhieuHuySanPham ph = new PhieuHuySanPham();
            // Lấy mã NV từ Session
            String maNV = PhienDangNhap.getUser() != null ? PhienDangNhap.getUser().getMaNV() : "";
            ph.setMaNV(maNV);
            ph.setLyDo(txtLyDo.getText());
            ph.setTongGiaTri(tongTien);

            if (PhieuHuySanPhamBUS.getPhieuHuySanPhamBUS().thucHienHuy(ph, data)) {
                parentPanel.loadDuLieu();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Tạo phiếu hủy thất bại!");
            }
        });
    }

    public static void main(String[] args) {
        XuatKhoSanPhamDialog xuatKhoSanPhamDialog = new XuatKhoSanPhamDialog(null);
        xuatKhoSanPhamDialog.setVisible(true);
    }
}