package ui.quanlysanpham;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import bus.NguyenLieuBUS;
import dto.ChiTietCongThuc;
import dto.CongThuc;
import dto.NguyenLieu;
import ui.component.Search_Item;
import util.TaoUI;

public class ChiTietCTDialog extends JDialog {
    private Search_Item search_Item;
    private DefaultTableModel model;
    private JTextField tfDinhLuong, tfTenNguyenLieu, tfMaNguyenLieu;
    private JButton themBtn, lamMoiBtn, suaBtn, luuBtn;
    private JTable table;
    private ArrayList<NguyenLieu> listNguyenLieu;
    private ArrayList<NguyenLieu> listNguyenLieuLoc;
    private XemCongThucDialog xemCongThucDialog;
    private ChiTietCongThuc chiTietCongThuc;
    boolean editTable = false;
    private int dong;

    public ChiTietCTDialog(JDialog ouner, ChiTietCongThuc chiTietCongThuc) {
        super(ouner, "Thêm nguyên liêu vào công thức");
        this.chiTietCongThuc = chiTietCongThuc;
        xemCongThucDialog = (XemCongThucDialog) ouner;
        setSize(400, 400);
        setLocationRelativeTo(ouner);
        setLayout(new BorderLayout());
        TaoUI.suaBorderChoPanel((JPanel) getContentPane(), 2, 0, 0, 0);
        initGUI();

        NguyenLieuBUS nguyenLieuBUS = new NguyenLieuBUS();
        listNguyenLieu = nguyenLieuBUS.layListNguyenLieu();
        dayListNguyenLieuVaoBang(listNguyenLieu);
        ganSuKien();
        capNhapDuLieu();
        setVisible(true);

    }

    public ChiTietCTDialog(JDialog ouner, ChiTietCongThuc chiTietCongThuc, int dong) {
        super(ouner, "Thêm nguyên liêu vào công thức");
        this.chiTietCongThuc = chiTietCongThuc;
        xemCongThucDialog = (XemCongThucDialog) ouner;
        setSize(400, 400);
        setLocationRelativeTo(ouner);
        setLayout(new BorderLayout());
        TaoUI.suaBorderChoPanel((JPanel) getContentPane(), 2, 0, 0, 0);
        initGUI();

        NguyenLieuBUS nguyenLieuBUS = new NguyenLieuBUS();
        listNguyenLieu = nguyenLieuBUS.layListNguyenLieu();
        dayListNguyenLieuVaoBang(listNguyenLieu);
        ganSuKien();
        capNhapDuLieu();
        setVisible(true);

    }

    private void initGUI() {
        JPanel center = TaoUI.taoPanelBorderLayout(400, 300);
        search_Item = new Search_Item(400, 30);
        model = new DefaultTableModel();
        model.addColumn("Mã NL");
        model.addColumn("Tên NL");
        model.addColumn("Giá NL");
        model.addColumn("Đơn vị");
        JScrollPane scrollPane = TaoUI.taoTableScroll(model);
        table = (JTable) scrollPane.getViewport().getView();

        center.add(search_Item, BorderLayout.NORTH);
        center.add(scrollPane, BorderLayout.CENTER);
        add(center, BorderLayout.CENTER);

        JPanel bottom = TaoUI.taoPanelCanGiua(400, 150);
        tfDinhLuong = new JTextField();
        tfTenNguyenLieu = new JTextField();
        tfMaNguyenLieu = new JTextField();
        JPanel input1 = TaoUI.taoFieldText("Định lượng", 80, 80, 30, 5, tfDinhLuong);
        JPanel inputNL1 = TaoUI.taoFieldText("Mã NL", 50, 60, 30, 5, tfMaNguyenLieu);
        JPanel inputNL2 = TaoUI.taoFieldText("Tên NL", 50, 130, 30, 5, tfTenNguyenLieu);
        JPanel inputNL = TaoUI.taoPanelCanGiua(400, 30);
        TaoUI.addItem(inputNL, inputNL1, 20, true);
        TaoUI.addItem(inputNL, inputNL2, 20, true);

        JPanel buttoPanel = TaoUI.taoPanelCanGiua(400, 40);

        themBtn = new JButton("Thêm");
        lamMoiBtn = new JButton("làm mới");
        suaBtn = new JButton("Sửa");
        luuBtn = new JButton("Lưu");

        if (chiTietCongThuc != null) {
            TaoUI.addItem(buttoPanel, suaBtn, 5, true);
            TaoUI.addItem(buttoPanel, luuBtn, 5, true);
        } else {
            TaoUI.addItem(buttoPanel, themBtn, 5, true);
            TaoUI.addItem(buttoPanel, lamMoiBtn, 5, true);
        }
        TaoUI.addItem(bottom, inputNL, 10, false);
        TaoUI.addItem(bottom, input1, 10, false);
        TaoUI.addItem(bottom, buttoPanel, 10, false);

        add(bottom, BorderLayout.SOUTH);
    }

    private void capNhapDuLieu() {
        if (chiTietCongThuc != null) {
            tfDinhLuong.setText(String.valueOf(chiTietCongThuc.getSoLuong()));
            tfTenNguyenLieu.setText(chiTietCongThuc.getNguyenLieu().getTenNL());
            tfMaNguyenLieu.setText(chiTietCongThuc.getNguyenLieu().getMaNL());
            tfDinhLuong.setEditable(false);
        } else {
            editTable = true;
        }
        tfTenNguyenLieu.setEditable(false);
        tfMaNguyenLieu.setEditable(false);
    }

    public void locNguyenLieu() {
        model.setRowCount(0);
        String tuKhoa = search_Item.getTextSearch();
        listNguyenLieuLoc = new ArrayList<>();
        for (NguyenLieu nguyenLieu : listNguyenLieu) {
            if (nguyenLieu.getTenNL().toUpperCase().contains(tuKhoa.toUpperCase())) {
                listNguyenLieuLoc.add(nguyenLieu);
            }
        }
        dayListNguyenLieuVaoBang(listNguyenLieuLoc);
    }

    private void themNLVaoBang(NguyenLieu nguyenLieu) {
        model.addRow(new Object[] { nguyenLieu.getMaNL(), nguyenLieu.getTenNL(), nguyenLieu.getGia(),
                nguyenLieu.getDonVi() });
    }

    private void dayListNguyenLieuVaoBang(ArrayList<NguyenLieu> listNguyenLieu) {
        for (NguyenLieu nguyenLieu : listNguyenLieu) {
            themNLVaoBang(nguyenLieu);
        }
    }

    private void ganSuKien() {
        search_Item.setEvent(this::locNguyenLieu);
        themBtn.addActionListener(e -> {

            int dongDangChon = table.getSelectedRow();
            if (dongDangChon >= 0) {
                double dinhLuong = Double.parseDouble(tfDinhLuong.getText());

                String ma = table.getValueAt(dongDangChon, 0).toString();
                NguyenLieuBUS nguyenLieuBUS = new NguyenLieuBUS();
                NguyenLieu nguyenLieu = nguyenLieuBUS.timNguyenLieu(ma);
                xemCongThucDialog.themNLVaoBang(chiTietCongThuc == null ? "" : chiTietCongThuc.getMaCTCT(),
                        nguyenLieu.getMaNL(), nguyenLieu.getTenNL(), dinhLuong, nguyenLieu.getDonVi());
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn nguyên liệu", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int row = table.getSelectedRow();
                    if (row != -1 && editTable) {
                        tfMaNguyenLieu.setText(model.getValueAt(row, 0).toString());
                        tfTenNguyenLieu.setText(model.getValueAt(row, 1).toString());
                    }
                }
            }
        });
        suaBtn.addActionListener(e -> {
            editTable = true;
            tfDinhLuong.setEditable(true);
            suaBtn.setEnabled(false);
        });
        lamMoiBtn.addActionListener(e -> {
            tfDinhLuong.setText("");
            tfMaNguyenLieu.setText("");
            tfTenNguyenLieu.setText("");
        });
        luuBtn.addActionListener(e -> {
            for (NguyenLieu nguyenLieu : listNguyenLieu) {
                if (tfMaNguyenLieu.getText().equals(nguyenLieu.getMaNL())) {
                    String maCTCT = chiTietCongThuc == null ? "" : chiTietCongThuc.getMaCTCT();
                    xemCongThucDialog.suaNL(maCTCT, nguyenLieu.getMaNL(), nguyenLieu.getTenNL(),
                            Double.parseDouble(tfDinhLuong.getText()), nguyenLieu.getDonVi(), dong);
                    dispose();
                }

            }
        });
    }
}