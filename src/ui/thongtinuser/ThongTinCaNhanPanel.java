package ui.thongtinuser;

import java.awt.Dimension;
import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.toedter.calendar.JDateChooser;

import bus.NhanVienBUS;
import dto.NhanVien;
import ui.login.PhienDangNhap;
import ui.login.LoginUI;
import util.Anh;
import util.TaoUI;

public class ThongTinCaNhanPanel extends JPanel {
    private JTextField tfMaNV, tfTenNV, tfSDT, tfDiaChi, tfChucVu;
    private JComboBox<String> cbGioiTinh;
    private JDateChooser dcNgaySinh;
    private JButton btnLuuThongTin, btnSua, btnChonAnh;
    private JLabel lblAnhDaiDien;
    private JFileChooser fileChooser = new JFileChooser();
    private boolean daDoiAnh = false;
    private NhanVien nhanVien = null;
    private JDialog parentDialog;

    public ThongTinCaNhanPanel(JDialog dialog) {
        this.parentDialog = dialog;
        TaoUI.taoPanelBoxLayoutDoc(this, 400, 780);
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        initGUI();

        setDuLieu(PhienDangNhap.getUser());
        tacChucNangSua();
        ganSuKien();
    }

    private void ganSuKien() {
        btnSua.addActionListener(e -> {
            batChucNangSua();
        });

        btnLuuThongTin.addActionListener(e -> {
            if (!kiemTraDuLieu()) {
                return;
            }

            try {
                NhanVienBUS nhanVienBUS = NhanVienBUS.getNhanVienBUS();
                NhanVien nv = dongGoiNhanVien();
                String errorMsg = nhanVienBUS.capNhatNhanVien(nv);

                if (errorMsg == null) {
                    JOptionPane.showMessageDialog(this, "Cập nhật thông tin cá nhân thành công!", "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                    LoginUI.getLoginUI().getMainFrame().loadAllData();
                    if (parentDialog != null) {
                        parentDialog.dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, errorMsg, "Lỗi cập nhật",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi hệ thống: " + ex.getMessage(), "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        btnChonAnh.addActionListener(e -> {
            fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Hình ảnh", "jpg", "png", "jpeg"));
            int result = fileChooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File fileAnh = fileChooser.getSelectedFile();
                ImageIcon icon = new ImageIcon(fileAnh.getAbsolutePath());
                lblAnhDaiDien.setIcon(icon);
                daDoiAnh = true;
            }
        });

    }

    private NhanVien dongGoiNhanVien() {
        if (daDoiAnh) {
            String duongDan = Anh.luuAnhNV(nhanVien.getMaNV(), fileChooser);
            nhanVien.setAnh(duongDan);
            daDoiAnh = false;
        }
        NhanVien nv = new NhanVien();
        nv.setMaNV(tfMaNV.getText());
        nv.setTenNV(tfTenNV.getText());
        nv.setGioiTinh(cbGioiTinh.getSelectedItem().toString());

        Date date = dcNgaySinh.getDate();
        if (date != null) {
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            nv.setNgaySinh(localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }

        nv.setSdt(tfSDT.getText());
        nv.setDiaChi(tfDiaChi.getText());
        nv.setAnh(nhanVien.getAnh());

        return nv;
    }

    private void initGUI() {
        lblAnhDaiDien = TaoUI.taoJlabelAnh("../assets/img/goku.png", 180, 180);
        lblAnhDaiDien.setAlignmentX(CENTER_ALIGNMENT);
        add(lblAnhDaiDien);
        add(Box.createRigidArea(new Dimension(0, 10)));
        JPanel buttonCAPanel = TaoUI.taoPanelCanGiua(400, 30);
        btnChonAnh = new JButton("Chọn ảnh");
        TaoUI.addItem(buttonCAPanel, btnChonAnh, 0, true);
        add(buttonCAPanel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        tfMaNV = new JTextField();
        tfTenNV = new JTextField();
        String[] gt = { "Nam", "Nữ" };
        cbGioiTinh = new JComboBox<>(gt);

        dcNgaySinh = new JDateChooser();
        dcNgaySinh.setDateFormatString("dd/MM/yyyy");
        Dimension sizeDc = new Dimension(120, 35);
        dcNgaySinh.setPreferredSize(sizeDc);
        dcNgaySinh.setMinimumSize(sizeDc);
        dcNgaySinh.setMaximumSize(sizeDc);

        tfSDT = new JTextField();
        tfChucVu = new JTextField();
        tfDiaChi = new JTextField();

        add(taoPanelItemThongTin(400, 35, "Mã NV", "Tên NV", 60, 90, 70, 120, tfMaNV, tfTenNV));
        add(Box.createRigidArea(new Dimension(0, 10)));

        add(taoPanelItemThongTin(400, 35, "Giới tính", "Ngày sinh", 60, 90, 70, 120, cbGioiTinh, dcNgaySinh));
        add(Box.createRigidArea(new Dimension(0, 10)));

        add(taoPanelItemThongTin(400, 35, "SĐT", "Chức vụ", 60, 90, 70, 120, tfSDT, tfChucVu));
        add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel pnlDiaChi = TaoUI.taoFieldText("Địa chỉ", 60, 297, 35, 3, tfDiaChi);
        JPanel thongTinDC = TaoUI.taoPanelBoxLayoutNgang(400, 35);
        thongTinDC.add(pnlDiaChi);
        add(thongTinDC);

        add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel buttonSuaPanel = TaoUI.taoPanelCanGiua(400, 30);
        btnSua = new JButton("Sửa");
        btnLuuThongTin = new JButton("Lưu thông tin");
        btnLuuThongTin.setEnabled(false);
        TaoUI.addItem(buttonSuaPanel, btnSua, 10, true);
        TaoUI.addItem(buttonSuaPanel, btnLuuThongTin, 10, true);
        add(buttonSuaPanel);
    }

    private JPanel taoPanelItemThongTin(int width, int height, String titleInput1, String titleInput2, int wLb1,
            int wInput1, int wLb2, int wInput2, JTextField textField1, JTextField textField2) {

        JPanel thongTin = TaoUI.taoPanelBoxLayoutNgang(width, height);
        JPanel input1JPanel = TaoUI.taoFieldText(titleInput1, wLb1, wInput1, height, 3, textField1);
        JPanel input2JPanel = TaoUI.taoFieldText(titleInput2, wLb2, wInput2, height, 3, textField2);

        thongTin.add(input1JPanel);
        thongTin.add(Box.createHorizontalGlue());
        thongTin.add(input2JPanel);

        return thongTin;
    }

    private JPanel taoPanelItemThongTin(int width, int height, String titleInput1, String titleInput2, int wLb1,
            int wInput1, int wLb2, int wInput2, JComboBox<String> comboBox1, JDateChooser dateChooser) {

        JPanel thongTin = TaoUI.taoPanelBoxLayoutNgang(width, height);

        JPanel ctn1 = new JPanel();
        ctn1.setLayout(new BoxLayout(ctn1, BoxLayout.X_AXIS));
        ctn1.setPreferredSize(new Dimension(wInput1 + wLb1 + 3, height));
        JLabel label1 = new JLabel(titleInput1);
        label1.setPreferredSize(new Dimension(wLb1, height));
        label1.setMaximumSize(new Dimension(wLb1, height));
        label1.setMinimumSize(new Dimension(wLb1, height));
        ctn1.add(label1);
        ctn1.add(Box.createRigidArea(new Dimension(3, 0)));
        comboBox1.setPreferredSize(new Dimension(wInput1, height));
        comboBox1.setMaximumSize(new Dimension(wInput1, height));
        comboBox1.setMinimumSize(new Dimension(wInput1, height));
        ctn1.add(comboBox1);

        JPanel ctn2 = new JPanel();
        ctn2.setLayout(new BoxLayout(ctn2, BoxLayout.X_AXIS));
        ctn2.setPreferredSize(new Dimension(wInput2 + wLb2 + 3, height));
        JLabel label2 = new JLabel(titleInput2);
        label2.setPreferredSize(new Dimension(wLb2, height));
        label2.setMaximumSize(new Dimension(wLb2, height));
        label2.setMinimumSize(new Dimension(wLb2, height));
        ctn2.add(label2);
        ctn2.add(Box.createRigidArea(new Dimension(3, 0)));
        ctn2.add(dateChooser);

        thongTin.add(ctn1);
        thongTin.add(Box.createHorizontalGlue());
        thongTin.add(ctn2);

        return thongTin;
    }

    private void tacChucNangSua() {
        tfChucVu.setEditable(false);
        tfDiaChi.setEditable(false);
        cbGioiTinh.setEnabled(false);
        tfMaNV.setEditable(false);
        dcNgaySinh.setEnabled(false);
        tfSDT.setEditable(false);
        btnChonAnh.setEnabled(false);
        tfTenNV.setEditable(false);

        btnSua.setEnabled(true);
        btnLuuThongTin.setEnabled(false);
    }

    private void batChucNangSua() {
        tfChucVu.setEditable(false);
        tfDiaChi.setEditable(true);
        cbGioiTinh.setEnabled(true);
        dcNgaySinh.setEnabled(true);
        tfSDT.setEditable(true);
        tfTenNV.setEditable(true);
        btnChonAnh.setEnabled(true);
        tfMaNV.setEditable(false);

        btnSua.setEnabled(false);
        btnLuuThongTin.setEnabled(true);
    }

    public void setDuLieu(NhanVien nv) {
        if (nv == null) {
            return;
        }
        nhanVien = nv;
        tfMaNV.setText(nv.getMaNV());
        tfTenNV.setText(nv.getTenNV());
        cbGioiTinh.setSelectedItem(nv.getGioiTinh());

        if (nv.getNgaySinh() != null && !nv.getNgaySinh().trim().isEmpty()) {
            try {
                DateTimeFormatter[] formatters = {
                        DateTimeFormatter.ofPattern("dd/MM/yyyy"),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                        DateTimeFormatter.ofPattern("dd-MM-yyyy")
                };
                LocalDate localDate = null;
                for (DateTimeFormatter formatter : formatters) {
                    try {
                        localDate = LocalDate.parse(nv.getNgaySinh(), formatter);
                        break;
                    } catch (DateTimeParseException ignored) {
                    }
                }

                if (localDate != null) {
                    Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                    dcNgaySinh.setDate(date);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            dcNgaySinh.setDate(null);
        }

        tfSDT.setText(nv.getSdt());
        tfDiaChi.setText(nv.getDiaChi());
        tfChucVu.setText(PhienDangNhap.getTaiKhoan().getNhomQuyen().getTenNhomQuyen());

        ImageIcon icon = TaoUI.taoImageIcon(nhanVien.getAnh(), 180, 180);
        lblAnhDaiDien.setIcon(icon);
    }

    private boolean kiemTraDuLieu() {
        String tenNV = tfTenNV.getText().trim();
        String gioiTinh = cbGioiTinh.getSelectedItem().toString().trim();
        Date ngaySinh = dcNgaySinh.getDate();
        String sdt = tfSDT.getText().trim();
        String diaChi = tfDiaChi.getText().trim();

        if (tenNV.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên nhân viên không được để trống!", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (gioiTinh.isEmpty() || (!gioiTinh.equals("Nam") && !gioiTinh.equals("Nữ"))) {
            JOptionPane.showMessageDialog(this, "Giới tính phải là 'Nam' hoặc 'Nữ'!", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (ngaySinh == null) {
            JOptionPane.showMessageDialog(this, "Ngày sinh không được để trống hoặc sai định dạng!", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        LocalDate localNgaySinh = ngaySinh.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (localNgaySinh.isAfter(LocalDate.now())) {
            JOptionPane.showMessageDialog(this, "Ngày sinh không được là ngày trong tương lai!", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (sdt.isEmpty() || !sdt.matches("\\d{10,11}")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại phải là 10-11 chữ số!", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (diaChi.isEmpty() || diaChi.length() > 255) {
            JOptionPane.showMessageDialog(this, "Địa chỉ không được để trống và không vượt quá 255 ký tự!", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
}