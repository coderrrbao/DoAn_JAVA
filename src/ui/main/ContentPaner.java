package ui.main;

import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JPanel;

import ui.thongke.ThongKeUI;
import ui.nhacungcap.NhaCungCapUI;
import ui.nhapkho.NhapKhoUI;
import ui.quanlysanpham.QuanLySanPhamUI;

public class ContentPaner extends JPanel {

    private QuanLySanPhamUI sanPhamUI;
    private NhaCungCapUI nhaCungCapUI;
    private NhapKhoUI nhapKhoUI;
    private ThongKeUI thongKeUI;

    public ContentPaner() {

        setLayout(new CardLayout());
        setBackground(Color.white);
        sanPhamUI = new QuanLySanPhamUI();
        nhaCungCapUI = new NhaCungCapUI();
        nhapKhoUI = new NhapKhoUI();
        thongKeUI = new ThongKeUI();

        add(sanPhamUI, "Quản lý sản phẩm");
        add(nhaCungCapUI, "Nhà cung cấp");
        add(nhapKhoUI, "Nhập kho");
        add(thongKeUI, "Thống kê");
    }

    public void loadAll() {

    }
}
