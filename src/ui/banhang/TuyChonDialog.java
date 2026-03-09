package ui.banhang;

import dto.SanPham;
import dto.Size;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class TuyChonDialog extends JDialog {

    private SanPham sanPham;
    private ArrayList<Size> listSize;
    private ArrayList<SanPham> listTopping;

    private Size sizeDuocChon = null;
    private ArrayList<SanPham> toppingDuocChon = new ArrayList<>();
    private boolean isXacNhan = false;

    private JLabel lblTongTien;
    private DecimalFormat df = new DecimalFormat("#,### đ");
    private double tongTienHienTai = 0;

    public TuyChonDialog(Frame parent, SanPham sanPham, ArrayList<Size> listSize, ArrayList<SanPham> listTopping) {
        super(parent, "Tùy chọn món", true);
        this.sanPham = sanPham;
        this.listSize = (listSize != null) ? listSize : new ArrayList<>();
        this.listTopping = (listTopping != null) ? listTopping : new ArrayList<>();

        initUI();
        pack();
        setLocationRelativeTo(parent);
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(450, 550));
        getContentPane().setBackground(Color.WHITE);

        JPanel pnlTop = new JPanel();
        pnlTop.setLayout(new BoxLayout(pnlTop, BoxLayout.Y_AXIS));
        pnlTop.setBackground(Color.WHITE);
        pnlTop.setBorder(new EmptyBorder(15, 20, 10, 20));

        JLabel lblTenMon = new JLabel(sanPham.getTenSP());
        lblTenMon.setFont(new Font("SansSerif", Font.BOLD, 18));

        JLabel lblGiaGoc = new JLabel("Giá gốc: " + df.format(sanPham.getGiaBan()));
        lblGiaGoc.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblGiaGoc.setForeground(Color.GRAY);

        pnlTop.add(lblTenMon);
        pnlTop.add(Box.createVerticalStrut(5));
        pnlTop.add(lblGiaGoc);

        JPanel pnlCenter = new JPanel();
        pnlCenter.setLayout(new BoxLayout(pnlCenter, BoxLayout.Y_AXIS));
        pnlCenter.setBackground(Color.WHITE);
        pnlCenter.setBorder(new EmptyBorder(10, 20, 10, 20));

        if (!listSize.isEmpty()) {
            JLabel lblSizeTitle = new JLabel("🔵 CHỌN SIZE (Bắt buộc)");
            lblSizeTitle.setFont(new Font("SansSerif", Font.BOLD, 14));
            pnlCenter.add(lblSizeTitle);
            pnlCenter.add(Box.createVerticalStrut(5));

            JPanel pnlSizes = new JPanel(new GridLayout(0, 1, 0, 5));
            pnlSizes.setBackground(Color.WHITE);
            ButtonGroup bgSize = new ButtonGroup();

            for (int i = 0; i < listSize.size(); i++) {
                Size size = listSize.get(i);
                String text = size.getTenSize();
                if (size.getPhanTramGia() > 0) {
                    text += " (+ " + size.getPhanTramGia() + "%)";
                }

                JRadioButton radSize = new JRadioButton(text);
                radSize.setBackground(Color.WHITE);
                radSize.setFont(new Font("SansSerif", Font.PLAIN, 14));


                if (i == 2) {
                    radSize.setSelected(true);
                    sizeDuocChon = size;
                }

                radSize.addActionListener(e -> {
                    sizeDuocChon = size;
                    tinhTien();
                });

                bgSize.add(radSize);
                pnlSizes.add(radSize);
            }
            pnlCenter.add(pnlSizes);
            pnlCenter.add(Box.createVerticalStrut(20));
        }

        if (!listTopping.isEmpty()) {
            JLabel lblToppingTitle = new JLabel("🟨 THÊM TOPPING (Tùy chọn)");
            lblToppingTitle.setFont(new Font("SansSerif", Font.BOLD, 14));
            pnlCenter.add(lblToppingTitle);
            pnlCenter.add(Box.createVerticalStrut(10));

            JPanel pnlToppingGrid = new JPanel(new GridLayout(0, 2, 10, 10));
            pnlToppingGrid.setBackground(Color.WHITE);

            for (SanPham topping : listTopping) {
                JCheckBox chkTopping = new JCheckBox(topping.getTenSP() + " (" + df.format(topping.getGiaBan()) + ")");
                chkTopping.setBackground(Color.WHITE);
                chkTopping.setFont(new Font("SansSerif", Font.PLAIN, 13));

                chkTopping.addActionListener(e -> {
                    if (chkTopping.isSelected()) {
                        toppingDuocChon.add(topping);
                    } else {
                        toppingDuocChon.remove(topping);
                    }
                    tinhTien();
                });

                pnlToppingGrid.add(chkTopping);
            }

            JScrollPane scrollTopping = new JScrollPane(pnlToppingGrid);
            scrollTopping.setBorder(null);
            scrollTopping.getVerticalScrollBar().setUnitIncrement(16);
            pnlCenter.add(scrollTopping);
        }

        JPanel pnlBottom = new JPanel(new BorderLayout());
        pnlBottom.setBackground(new Color(245, 245, 250));
        pnlBottom.setBorder(new EmptyBorder(15, 20, 15, 20));

        lblTongTien = new JLabel("Tạm tính: 0 đ");
        lblTongTien.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblTongTien.setForeground(new Color(220, 53, 69));

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pnlButtons.setOpaque(false);

        JButton btnHuy = new JButton("Hủy");
        btnHuy.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnHuy.setFocusPainted(false);
        btnHuy.addActionListener(e -> {
            isXacNhan = false;
            dispose();
        });

        JButton btnThem = new JButton("Thêm vào Đơn");
        btnThem.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnThem.setFocusPainted(false);
        btnThem.addActionListener(e -> {
            isXacNhan = true;
            dispose();
        });

        pnlButtons.add(btnHuy);
        pnlButtons.add(btnThem);

        pnlBottom.add(lblTongTien, BorderLayout.WEST);
        pnlBottom.add(pnlButtons, BorderLayout.EAST);

        add(pnlTop, BorderLayout.NORTH);
        add(pnlCenter, BorderLayout.CENTER);
        add(pnlBottom, BorderLayout.SOUTH);

        tinhTien();
    }

    private void tinhTien() {
        double giaGoc = sanPham.getGiaBan();
        double tienSize = 0;
        double tienTopping = 0;

        if (sizeDuocChon != null) {
            tienSize = giaGoc * (sizeDuocChon.getPhanTramGia() / 100.0);
        }

        for (SanPham tp : toppingDuocChon) {
            tienTopping += tp.getGiaBan();
        }

        tongTienHienTai = giaGoc + tienSize + tienTopping;
        lblTongTien.setText("Tạm tính: " + df.format(tongTienHienTai));
    }

    public boolean isXacNhan() {
        return isXacNhan;
    }

    public Size getSizeDuocChon() {
        return sizeDuocChon;
    }

    public ArrayList<SanPham> getToppingDuocChon() {
        return toppingDuocChon;
    }

    public double getTongTienHienTai() {
        return tongTienHienTai;
    }
}