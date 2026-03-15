package ui.xuatkho.nguyenlieu;

import bus.LoNguyenLieuBUS;
import bus.NguyenLieuBUS;
import bus.PhieuHuyNguyenLieuBUS;
import dto.LoNguyenLieu;
import dto.NguyenLieu;
import dto.PhieuHuyNguyenLieu;
import ui.component.Search_Item;
import ui.login.PhienDangNhap;
import util.TaoUI;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class XuatKhoNguyenLieuDialog extends JDialog {
    private JTable tblKho, tblChoXuat;
    private DefaultTableModel modelKho, modelChoXuat;
    private JTextField txtMaNL, txtTenNL, txtSoLuong, txtMaLo, txtLyDo;
    private JButton btnThem, btnXacNhan;
    private Search_Item search_Item;
    private XuatKhoNguyenLieuPanel parentPanel;

    public XuatKhoNguyenLieuDialog(XuatKhoNguyenLieuPanel parent) {
        super((Frame) null, "Tạo Phiếu Hủy Nguyên Liệu", true);
        this.parentPanel = parent;
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel main = new JPanel(new GridLayout(1, 2, 10, 0));

        JPanel left = new JPanel(new BorderLayout(0, 10));

        search_Item = new Search_Item(250, 32);

        left.add(search_Item, BorderLayout.NORTH);

        modelKho = new DefaultTableModel(new String[] { "Mã NL", "Mã Lô", "Hạn SD", "Tồn", "Giá Nhập" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JScrollPane scrollKho = TaoUI.taoTableScroll(modelKho);
        tblKho = (JTable) scrollKho.getViewport().getView();
        tblKho.getTableHeader().setReorderingAllowed(false);

        left.add(scrollKho, BorderLayout.CENTER);

        JPanel right = new JPanel(new BorderLayout(0, 10));

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 10));

        txtMaNL = new JTextField();
        txtMaNL.setEditable(false);
        txtTenNL = new JTextField();
        txtTenNL.setEditable(false);
        txtMaLo = new JTextField();
        txtMaLo.setEditable(false);
        txtSoLuong = new JTextField();
        txtLyDo = new JTextField();

        form.add(taoDong("Mã Nguyên liệu:", txtMaNL));
        form.add(taoDong("Tên Nguyên liệu:", txtTenNL));
        form.add(taoDong("Mã Lô:", txtMaLo));
        form.add(taoDong("Số lượng hủy:", txtSoLuong));
        form.add(taoDong("Lý do hủy:", txtLyDo));

        btnThem = new JButton("Thêm vào danh sách chờ");
        JPanel pnBtnThem = new JPanel();
        pnBtnThem.setLayout(new BoxLayout(pnBtnThem, BoxLayout.X_AXIS));
        pnBtnThem.add(btnThem);
        TaoUI.setFixSize(btnThem, 475, 32);

        form.add(pnBtnThem);

        modelChoXuat = new DefaultTableModel(new String[] { "Mã NL", "Tên NL", "SL Hủy", "Mã Lô", "Giá Nhập" }, 0) {
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

        loadDataKhoNL();
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

            txtSoLuong.setEditable(false);
            txtLyDo.setEditable(false);

            this.setTitle("Xem thông tin tạo phiếu hủy (Chế độ chỉ đọc)");
        }
        this.revalidate();
        this.repaint();
    }

    private void loadDataKhoNL() {
        modelKho.setRowCount(0);
        String keyword = search_Item != null && search_Item.getTextSearch() != null
                ? search_Item.getTextSearch().toString().toLowerCase()
                : "";

        ArrayList<LoNguyenLieu> listLo = LoNguyenLieuBUS.getLoNguyenLieuBUS().layListLoNguyenLieu();
        for (LoNguyenLieu lo : listLo) {
            if (lo.getSoLuong() > 0 && lo.getMaLoNL().toLowerCase().contains(keyword)) {
                modelKho.addRow(new Object[] {
                        lo.getMaNL(), lo.getMaLoNL(), lo.getHanSuDung(), lo.getSoLuong(), lo.getGiaNhap()
                });
            }
        }
    }

    private void ganSuKien() {

        search_Item.setEvent(() -> {
            loadDataKhoNL();
        });

        tblKho.getSelectionModel().addListSelectionListener(e -> {
            int row = tblKho.getSelectedRow();
            if (row != -1) {
                txtMaNL.setText(modelKho.getValueAt(row, 0).toString());
                txtMaLo.setText(modelKho.getValueAt(row, 1).toString());
                NguyenLieu nl = NguyenLieuBUS.getNguyenLieuBUS().timNguyenLieu(txtMaNL.getText());
                txtTenNL.setText(nl != null ? nl.getTenNL() : "N/A");
                txtSoLuong.requestFocus();
            }
        });

        btnThem.addActionListener(e -> {
            try {
                int r = tblKho.getSelectedRow();
                if (r == -1) {
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn nguyên liệu từ kho!");
                    return;
                }

                double sl = Double.parseDouble(txtSoLuong.getText());
                double tonKho = Double.parseDouble(modelKho.getValueAt(r, 3).toString());
                String maLo = txtMaLo.getText();

                if (sl <= 0 || sl > tonKho) {
                    JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ hoặc vượt quá tồn kho!");
                    return;
                }

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
                    modelChoXuat.addRow(new Object[] {
                            txtMaNL.getText(),
                            txtTenNL.getText(),
                            sl,
                            maLo,
                            modelKho.getValueAt(r, 4)
                    });
                }
                txtSoLuong.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng hợp lệ!");
            }
        });

        btnXacNhan.addActionListener(e -> {
            if (modelChoXuat.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn ít nhất 1 nguyên liệu để hủy!");
                return;
            }
            double tongTien = 0;
            Object[][] data = new Object[modelChoXuat.getRowCount()][5];
            for (int i = 0; i < modelChoXuat.getRowCount(); i++) {
                for (int j = 0; j < 5; j++)
                    data[i][j] = modelChoXuat.getValueAt(i, j);
                tongTien += Double.parseDouble(data[i][2].toString()) * Double.parseDouble(data[i][4].toString());
            }

            PhieuHuyNguyenLieu ph = new PhieuHuyNguyenLieu();

            String maNV = PhienDangNhap.getUser() != null ? PhienDangNhap.getUser().getMaNV() : "";
            ph.setMaNV(maNV);
            ph.setLyDo(txtLyDo.getText());
            ph.setTongTien(tongTien);

            if (PhieuHuyNguyenLieuBUS.getPhieuHuyNguyenLieuBUS().thucHienHuy(ph, data)) {
                parentPanel.loadDuLieu();
                JOptionPane.showMessageDialog(this, "Tạo phiếu hủy thành công!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Tạo phiếu hủy thất bại!");
            }
        });
    }

    public static void main(String[] args) {
        XuatKhoNguyenLieuDialog xuatKhoNguyenLieuDialog = new XuatKhoNguyenLieuDialog(null);
        xuatKhoNguyenLieuDialog.setVisible(true);
    }
}