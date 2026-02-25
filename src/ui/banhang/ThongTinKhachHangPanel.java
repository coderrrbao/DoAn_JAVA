package ui.banhang;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import bus.ThongtinKhachHangBUS;
import dto.KhachHang;
import util.TaoUI;

public class ThongTinKhachHangPanel extends JPanel {
    private JTextField txtSdt;
    private JTextField txtTenKh;

    private ThongtinKhachHangBUS khachHangBUS;
    private ArrayList<KhachHang> danhSachKH;
    private ArrayList<KhachHang> danhSachGoiY;
    private JPopupMenu popupMenu;
    private JList<String> listGoiY;
    private DefaultListModel<String> listModel;
    private JScrollPane scrollPane;
    private KhachHang khachHangDuocChon = null;
    private boolean isSelecting = false;

    public ThongTinKhachHangPanel() {
        khachHangBUS = new ThongtinKhachHangBUS();
        danhSachKH = new ArrayList<>();
        loadDataKhachHang();

        TaoUI.taoPanelBoxLayoutDoc(this, Integer.MAX_VALUE, 140);
        TaoUI.suaBorderChoPanel(this, 0, 0, 0, 10);
        JPanel title = TaoUI.taoPanelCanGiua(Integer.MAX_VALUE, 40);
        title.setBackground(new Color(225, 235, 245));
        title.add(new JLabel("Thông tin khách hàng"));
        add(title);
        add(Box.createVerticalGlue());

        JPanel input = TaoUI.taoPanelBoxLayoutDoc(Integer.MAX_VALUE, 100);
        txtSdt = new JTextField();
        txtTenKh = new JTextField();
        txtTenKh.setEditable(false);

        JPanel sdt = TaoUI.taoFieldText("Số điện thoại", 100, 250, 30, 3, txtSdt);
        JPanel tenKh = TaoUI.taoFieldText("Tên khách hàng", 100, 250, 30, 3, txtTenKh);
        input = TaoUI.suaBorderChoPanel(input, 10, 10, 10, 10);
        input.add(sdt);
        input.add(Box.createRigidArea(new Dimension(0, 10)));
        input.add(tenKh);
        add(input);

        setupAutoSuggest();
    }

    private void loadDataKhachHang() {
        try {
            danhSachKH = khachHangBUS.getDanhSachKhachHang();
        } catch (Exception e) {
            System.out.println("Cảnh báo: Lỗi kết nối dữ liệu Khách Hàng.");
        }
    }

    private void setupAutoSuggest() {
        popupMenu = new JPopupMenu();
        popupMenu.setFocusable(false);

        listModel = new DefaultListModel<>();
        listGoiY = new JList<>(listModel);
        listGoiY.setFixedCellHeight(30);
        listGoiY.setFocusable(false);

        danhSachGoiY = new ArrayList<>();

        scrollPane = new JScrollPane(listGoiY);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        popupMenu.add(scrollPane);

        listGoiY.setSelectionBackground(new Color(184, 207, 229));
        listGoiY.setSelectionForeground(Color.BLACK);

        txtSdt.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { updateGoiY(); }
            @Override
            public void removeUpdate(DocumentEvent e) { updateGoiY(); }
            @Override
            public void changedUpdate(DocumentEvent e) { updateGoiY(); }
        });

        txtSdt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (popupMenu.isVisible()) {
                    int selectedIndex = listGoiY.getSelectedIndex();
                    if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                        if (selectedIndex < listModel.getSize() - 1) {
                            listGoiY.setSelectedIndex(selectedIndex + 1);
                            listGoiY.ensureIndexIsVisible(selectedIndex + 1);
                        }
                    } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                        if (selectedIndex > 0) {
                            listGoiY.setSelectedIndex(selectedIndex - 1);
                            listGoiY.ensureIndexIsVisible(selectedIndex - 1);
                        }
                    } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        chonKhachHangTuList();
                    }
                }
            }
        });

        listGoiY.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1 || e.getClickCount() == 2) {
                    chonKhachHangTuList();
                }
            }
        });
    }

    private void updateGoiY() {
        if (isSelecting) return;

        khachHangDuocChon = null;
        txtTenKh.setText("");

        String keyword = txtSdt.getText().trim();
        listModel.clear();
        danhSachGoiY.clear();

        if (keyword.isEmpty()) {
            popupMenu.setVisible(false);
            return;
        }

        if (danhSachKH != null) {
            for (KhachHang kh : danhSachKH) {
                if (kh.getSdt() != null && kh.getSdt().contains(keyword)) {
                    danhSachGoiY.add(kh);
                    listModel.addElement(kh.getSdt() + " - " + kh.getTenKH());
                }
            }
        }

        if (listModel.getSize() > 0) {
            int displayCount = Math.min(listModel.getSize(), 5);
            listGoiY.setVisibleRowCount(displayCount);

            int height = (displayCount * 30) + 3;
            scrollPane.setPreferredSize(new Dimension(txtSdt.getWidth(), height));
            popupMenu.pack();

            if (!popupMenu.isVisible()) {
                popupMenu.show(txtSdt, 0, txtSdt.getHeight());
            }

            SwingUtilities.invokeLater(() -> txtSdt.requestFocusInWindow());

        } else {
            popupMenu.setVisible(false);
        }
    }

    private void chonKhachHangTuList() {
        int selectedIndex = listGoiY.getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < danhSachGoiY.size()) {
            khachHangDuocChon = danhSachGoiY.get(selectedIndex);

            isSelecting = true;
            txtSdt.setText(khachHangDuocChon.getSdt());
            txtTenKh.setText(khachHangDuocChon.getTenKH());
            popupMenu.setVisible(false);
            txtSdt.setCaretPosition(txtSdt.getText().length());
            isSelecting = false;
        }
    }

    public KhachHang getKhachHangDuocChon() {
        return khachHangDuocChon;
    }

    public JTextField getTxtSdt() {
        return txtSdt;
    }

    public JTextField getTxtTenKh() {
        return txtTenKh;
    }
}