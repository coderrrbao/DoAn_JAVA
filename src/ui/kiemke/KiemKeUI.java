package ui.kiemke;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import bus.PhieuKiemKeBUS;
import dao.PhieuKiemKeDAO;
import dao.conection.DBConnection;
import dto.PhieuKiemKe;
import ui.component.LocNgay_Item;
import ui.login.PhienDangNhap;
import util.TaoTinNhan;
import util.TaoUI;

public class KiemKeUI extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private LocNgay_Item locNgay;
    private JButton btnThem;
    private JButton btnSua, btnXoa, btnXuatExcel, btnNhapExcel, btnXemCt;
    private PhieuKiemKeBUS phieuKiemKeBUS = PhieuKiemKeBUS.getPhieuKiemKeBUS();
    private ArrayList<PhieuKiemKe> listPhieuKiemKe = new ArrayList<>();

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
                if (model.getValueAt(row, 8).equals("Đã xác nhận")) {
                    JOptionPane.showMessageDialog(null, "Phiểu kiểm kê đã xác nhận không thể sửa", "Thông báo",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    PhieuKiemKe phieuKiemKe = phieuKiemKeBUS.timPhieuKiemKe(model.getValueAt(row, 0).toString());
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

        btnXemCt.addActionListener(e -> {
            int dongChon = table.getSelectedRow();
            if (dongChon >= 0) {
                PhieuKiemKe phieuKiemKe = phieuKiemKeBUS.timPhieuKiemKe(model.getValueAt(dongChon, 0).toString());
            
                ChiTietKiemKeDialog chiTietKiemKeDialog = new ChiTietKiemKeDialog(null, phieuKiemKe);
                chiTietKiemKeDialog.setVisible(true);
            } else {
                TaoTinNhan.showAutoCloseMessage("Vui lòng chọn dòng để xem chi tiết", "Thông báo", 1);
            }
        });

        btnXoa.addActionListener(e -> {
            int dongChon = table.getSelectedRow();
            if (dongChon >= 0) {
                if (phieuKiemKeBUS
                        .xoaPhieuKiemKe(phieuKiemKeBUS.timPhieuKiemKe(model.getValueAt(dongChon, 0).toString()))) {
                    TaoTinNhan.showAutoCloseMessage("Đã xóa phiếu kiểm kê thành thông", "Thông báo", 1);
                    loaiDuLieu();
                } else {
                    TaoTinNhan.showAutoCloseMessage("Đã xóa phiếu kiểm kê thất bại", "Thông báo", 1);
                }
            } else {
                TaoTinNhan.showAutoCloseMessage("Vui lòng chọn dòng để xóa", "Thông báo", 1);
            }
        });

        // --- XỬ LÝ XUẤT EXCEL ---
        btnXuatExcel.addActionListener(e -> {
        
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn nơi lưu Phiếu Kiểm Kê");
            fileChooser.setSelectedFile(new File("danhsachphieukiemke.xlsx"));

            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                String filePath = file.getAbsolutePath();

                if (!filePath.toLowerCase().endsWith(".xlsx")) {
                    filePath += ".xlsx";
                }
                if (phieuKiemKeBUS.xuatExcel(filePath)) {
                    JOptionPane.showMessageDialog(this, "Xuất thành công!", "Thành công",
                            JOptionPane.INFORMATION_MESSAGE);
                        loaiDuLieu();
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi khi ghi file Excel!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // --- XỬ LÝ NHẬP EXCEL ---
        btnNhapExcel.addActionListener(e -> {
            // 1. Cấu hình hộp thoại mở file
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn file Excel danh sách kiểm kê");
            fileChooser
                    .setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel Files (.xlsx)", "xlsx"));

            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();

                // 2. Gọi BUS thực hiện nhập file (Hàm này nên trả về danh sách hoặc boolean
                PhieuKiemKeBUS phieuKiemKeBUS = PhieuKiemKeBUS.getPhieuKiemKeBUS();
                // Giả sử hàm nhapExcel(File file) xử lý logic đọc và lưu vào DB
                boolean success = phieuKiemKeBUS.nhapExcel(file);

                if (success) {
                    // 3. Cập nhật lại UI
                    loaiDuLieu(); // Hàm load lại table của bạn

                    JOptionPane.showMessageDialog(this, "Nhập dữ liệu từ file Excel thành công!",
                            "Thành công", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "File trống, sai định dạng hoặc lỗi đọc file!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void loaiDuLieu() {
        model.setRowCount(0);

        listPhieuKiemKe = phieuKiemKeBUS.layListKiemKe();
        for (PhieuKiemKe phieuKiemKe : listPhieuKiemKe) {
            if (locNgay.ngayTrongKhoan(phieuKiemKe.getNgayKiem())) {
                model.addRow(new Object[] { phieuKiemKe.getMaKK(), phieuKiemKe.getMaNV(),
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
        btnNhapExcel.setPreferredSize(new Dimension(100, 32));
        top.add(btnNhapExcel);

        btnXuatExcel = new JButton("Xuất Excel");
        btnXuatExcel.setPreferredSize(new Dimension(100, 32));
        top.add(btnXuatExcel);
        btnXemCt = new JButton("Xem chi tiết");
        btnXemCt.setPreferredSize(new Dimension(100, 32));
        top.add(btnXemCt);

        return top;
    }

    private JPanel taoPanelTable() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = { "Mã Phiếu Kiểm", "Mã NV", "Ngày kiểm", "Mã lô", "Loại lô", "SL sổ sách",
                "SL thực tế", "Chênh lệch", "Trạng thái" };
        model = new DefaultTableModel(columns, 0);
        JScrollPane scrollPane = TaoUI.taoTableScroll(model);
        table = (JTable) scrollPane.getViewport().getView();
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

}