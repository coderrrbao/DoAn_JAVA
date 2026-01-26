package ui.quanlysanpham;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dto.Size;
import util.TaoUI;

public class SizeDialog extends JDialog {
    private JTextField tfTenSize, tfNLThem, tfGiaThem;
    private JButton themBtn, lamMoiBtn, suaBtn, luuBtn;
    ChiTietSanPhamDialog chiTietSanPhamDialog;

    private Size size;

    public SizeDialog(JDialog ouner, Size size) {
        super(ouner, "Thêm size");
        this.size = size;
        chiTietSanPhamDialog = (ChiTietSanPhamDialog) ouner;
        setSize(400, 250);
        setLocationRelativeTo(ouner);
        setLayout(new BorderLayout());
        JPanel center = TaoUI.taoPanelCanGiua(400, 300);
        initCenter(center);
        initButtons();
        loadDuLieu();
        ganSuKien();
        setVisible(true);
    }

    private void loadDuLieu() {
        if (size == null) {
            return;
        }
        tfTenSize.setText(size.getTenSize());
        tfNLThem.setText(String.valueOf(size.getPhanTramNL()));
        tfGiaThem.setText(String.valueOf(size.getPhanTramGia()));

        tfGiaThem.setEditable(false);
        tfNLThem.setEditable(false);
        tfTenSize.setEditable(false);
    }

    private void initCenter(JPanel center) {
        tfGiaThem = new JTextField();
        tfNLThem = new JTextField();
        tfTenSize = new JTextField();
        JPanel input1 = TaoUI.taoFieldText("Tên size", 80, 260, 30, 5, tfTenSize);
        JPanel input2 = TaoUI.taoFieldText("%NL thêm", 80, 260, 30, 5, tfNLThem);
        JPanel input3 = TaoUI.taoFieldText("%Giá thêm", 80, 260, 30, 5, tfGiaThem);

        TaoUI.addItem(center, input1, 15, false);
        TaoUI.addItem(center, input2, 15, false);
        TaoUI.addItem(center, input3, 15, false);
        add(center, BorderLayout.CENTER);
    }

    private void initButtons() {
        JPanel buttonPanel = TaoUI.taoPanelCanGiua(400, 50);
        themBtn = new JButton("Thêm");
        lamMoiBtn = new JButton("Làm mới");
        suaBtn = new JButton("Sửa");
        luuBtn = new JButton("Lưu");

        if (size == null) {
            TaoUI.addItem(buttonPanel, themBtn, 5, true);
            TaoUI.addItem(buttonPanel, lamMoiBtn, 5, true);
        } else {
            TaoUI.addItem(buttonPanel, suaBtn, 5, true);
            TaoUI.addItem(buttonPanel, luuBtn, 5, true);
        }
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void ganSuKien() {
        themBtn.addActionListener(e -> {
            Size size = new Size("", "", tfTenSize.getText(), Integer.parseInt(tfGiaThem.getText()),
                    Integer.parseInt(tfNLThem.getText()));
            chiTietSanPhamDialog.themSizeVaoBang(size);
            dispose();
        });
        suaBtn.addActionListener(e -> {
            tfGiaThem.setEditable(true);
            tfNLThem.setEditable(true);
            tfTenSize.setEditable(true);
        });
    }

}
