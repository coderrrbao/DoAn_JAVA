package ui.kiemke;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import bus.PhieuKiemKeBUS;
import dto.PhieuKiemKe;
import ui.component.LocNgay_Item;
import ui.component.Search_Item;
import ui.login.PhienDangNhap;
import util.TaoTinNhan;
import util.TaoUI;

public class KiemKeUI extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private LocNgay_Item locNgay;
    private JButton btnThem;
    private JButton btnSua, btnXoa, btnXuatExcel, btnNhapExcel;
    private PhieuKiemKeBUS phieuKiemKeBUS = PhieuKiemKeBUS.getPhieuKiemKeBUS();

    public KiemKeUI() {
        setLayout(new BorderLayout());

        JPanel centerContainer = new JPanel(new BorderLayout());
        centerContainer.add(taoTopPanel(), BorderLayout.NORTH);
        centerContainer.add(taoPanelTable(), BorderLayout.CENTER);

        add(centerContainer, BorderLayout.CENTER);
        loaiDuLieu();
        ganSuKien();
    }

    public void suaLaiGiaoDienTheoQuyen() {
        var listQuyen = PhienDangNhap.getListQuyen();

        // 1. Quyền Thêm phiếu kiểm kê (KK_TAO)
        if (!listQuyen.contains("KK_TAO")) {
            btnThem.setVisible(false);
        }

        // 2. Quyền Sửa phiếu kiểm kê (KK_SUA)
        if (!listQuyen.contains("KK_SUA")) {
            btnSua.setVisible(false);
        }

        // 3. Quyền Xóa phiếu kiểm kê (KK_XOA)
        if (!listQuyen.contains("KK_XOA")) {
            btnXoa.setVisible(false);
        }
        this.revalidate();
        this.repaint();
    }

    private void ganSuKien() {
        btnThem.addActionListener(e -> {
            ThemPhieuKiemDialog them = new ThemPhieuKiemDialog(this, null);
            them.setVisible(true);
        });
        btnSua.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                if (model.getValueAt(row, 9).equals("Đã xác nhận")) {
                    JOptionPane.showMessageDialog(null, "Phiểu kiểm kê đã xác nhận không thể sửa", "Thông báo",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    PhieuKiemKe phieuKiemKe = phieuKiemKeBUS.timPhieuKiemKe(model.getValueAt(row, 1).toString());
                    ThemPhieuKiemDialog themPhieuKiemDialog = new ThemPhieuKiemDialog(this, phieuKiemKe);
                    themPhieuKiemDialog.setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn dòng để sửa", "Thông báo",
                        JOptionPane.ERROR_MESSAGE);
            }

        });
        locNgay.setEvent(() -> {
            loaiDuLieu();
        });

        btnXoa.addActionListener(e -> {
            int dongChon = table.getSelectedRow();
            if (dongChon >= 0) {
                if (phieuKiemKeBUS
                        .xoaPhieuKiemKe(phieuKiemKeBUS.timPhieuKiemKe(model.getValueAt(dongChon, 1).toString()))) {
                    TaoTinNhan.showAutoCloseMessage("Đã xóa phiếu kiểm kê thành thông", "Thông báo", 1);
                    loaiDuLieu();
                } else {
                    TaoTinNhan.showAutoCloseMessage("Đã xóa phiếu kiểm kê thất bại", "Thông báo", 1);
                }
            } else {
                TaoTinNhan.showAutoCloseMessage("Vui lòng chọn dòng để xóa", "Thông báo", 1);
            }
        });
    }

    public void loaiDuLieu() {
        model.setRowCount(0);

        ArrayList<PhieuKiemKe> listPhieuKiemKe = phieuKiemKeBUS.layListKiemKe();
        int stt = 1;
        for (PhieuKiemKe phieuKiemKe : listPhieuKiemKe) {
            if (locNgay.ngayTrongKhoan(phieuKiemKe.getNgayKiem())) {
                model.addRow(new Object[] { stt++, phieuKiemKe.getMaKK(), phieuKiemKe.getMaNV(),
                        phieuKiemKe.getNgayKiem(),
                        phieuKiemKe.getMaLo(), phieuKiemKe.getLoaiLo(), phieuKiemKe.getSoLuongSoSach(),
                        phieuKiemKe.getSoLuongThuc(), phieuKiemKe.getSoLuongThuc() - phieuKiemKe.getSoLuongSoSach(),
                        phieuKiemKe.getTrangThaiXuLy() });
            }
        }
    }

    private JPanel taoTopPanel() {
        JPanel top = new JPanel();
        top.setPreferredSize(new Dimension(100, 45));
        top.setLayout(new FlowLayout(FlowLayout.LEFT));
        top.setBackground(Color.WHITE);

        locNgay = new LocNgay_Item(400, 32);
        top.add(locNgay);

        btnThem = new JButton("Thêm");
        btnThem.setPreferredSize(new Dimension(80, 32));
        top.add(btnThem);

        btnSua = new JButton("Sửa");
        btnSua.setPreferredSize(new Dimension(btnSua.getPreferredSize().width, 32));
        top.add(btnSua);

        btnXoa = new JButton("Xóa");
        btnXoa.setPreferredSize(new Dimension(80, 32));
        top.add(btnXoa);

        btnNhapExcel = new JButton("Nhập Excel");
        btnNhapExcel.setPreferredSize(new Dimension(80, 32));
        top.add(btnNhapExcel);

        btnXuatExcel = new JButton("Xóa");
        btnXuatExcel.setPreferredSize(new Dimension(80, 32));
        top.add(btnXuatExcel);
        return top;
    }

    private JPanel taoPanelTable() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = { "STT", "Mã Phiếu Kiểm", "Mã NV", "Ngày kiểm", "Mã lô", "Loại lô", "SL sổ sách",
                "SL thực tế", "Chênh lệch", "Trạng thái" };
        model = new DefaultTableModel(columns, 0);
        JScrollPane scrollPane = TaoUI.taoTableScroll(model);
        table = (JTable) scrollPane.getViewport().getView();
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

}