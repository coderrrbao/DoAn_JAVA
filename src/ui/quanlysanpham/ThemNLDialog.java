package ui.quanlysanpham;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import bus.NguyenLieuBUS;
import dto.NguyenLieu;
import ui.component.Search_Item;
import util.TaoUI;

public class ThemNLDialog extends JDialog {
    private Search_Item search_Item;
    private DefaultTableModel model;
    private JTextField tfDinhLuong;
    private JButton themBtn, lamMoiBtn;
    private JTable table;
    private ArrayList<NguyenLieu> listNguyenLieu;
    private  ArrayList<NguyenLieu> listNguyenLieuLoc;
    private XemCongThucDialog xemCongThucDialog;

    public ThemNLDialog(JDialog ouner) {
        super(ouner, "Thêm nguyên liêu vào công thức");
        xemCongThucDialog = (XemCongThucDialog)ouner;
        setSize(400, 400);
        setLocationRelativeTo(ouner);
        setLayout(new BorderLayout());
        TaoUI.suaBorderChoPanel((JPanel) getContentPane(), 2, 0, 0, 0);
        initGUI();

        NguyenLieuBUS nguyenLieuBUS = new NguyenLieuBUS();
        listNguyenLieu = nguyenLieuBUS.layListNguyenLieu();
        dayListNguyenLieuVaoBang(listNguyenLieu);
        ganSuKien();
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

        JPanel bottom = TaoUI.taoPanelCanGiua(400, 100);
        tfDinhLuong = new JTextField();
        JPanel input1 = TaoUI.taoFieldText("Định lượng", 80, 80, 30, 5, tfDinhLuong);
        JPanel buttoPanel = TaoUI.taoPanelCanGiua(400, 40);
        themBtn = new JButton("Thêm");
        lamMoiBtn = new JButton("làm mới");
        TaoUI.addItem(buttoPanel, themBtn, 5, true);
        TaoUI.addItem(buttoPanel, lamMoiBtn, 5, true);

        TaoUI.addItem(bottom, input1, 10, false);
        TaoUI.addItem(bottom, buttoPanel, 10, false);

        add(bottom, BorderLayout.SOUTH);
    }

    public void locNguyenLieu(){
         model.setRowCount(0);
        String tuKhoa  =  search_Item.getTextSearch();
        listNguyenLieuLoc  = new ArrayList<>();
        for (NguyenLieu  nguyenLieu  :  listNguyenLieu){
            if (nguyenLieu.getTenNL().toUpperCase().contains(tuKhoa.toUpperCase())){
                listNguyenLieuLoc.add(nguyenLieu);
            }
        }
        dayListNguyenLieuVaoBang(listNguyenLieuLoc);
    }

    public void themNLVaoBang(NguyenLieu nguyenLieu) {
        model.addRow(new Object[] { nguyenLieu.getMaNL(), nguyenLieu.getTenNL(), nguyenLieu.getGia(),
                nguyenLieu.getDonVi() });
    }

    private void dayListNguyenLieuVaoBang(ArrayList<NguyenLieu> listNguyenLieu) {
        for (NguyenLieu nguyenLieu : listNguyenLieu) {
            themNLVaoBang(nguyenLieu);
        }
    }

    private void ganSuKien(){
        search_Item.setEvent(this::locNguyenLieu);
        themBtn.addActionListener(e->{
            int dinhLuong = Integer.parseInt(tfDinhLuong.getText());
            int dongDangChon  = table.getSelectedRow();
            String ma =  table.getValueAt(dongDangChon, 0).toString();
            NguyenLieuBUS nguyenLieuBUS =   new NguyenLieuBUS();
            NguyenLieu  nguyenLieu  = nguyenLieuBUS.timNguyenLieu(ma);
            xemCongThucDialog.themNLVaoBang(nguyenLieu, dinhLuong);
            dispose();
        });
    }

}