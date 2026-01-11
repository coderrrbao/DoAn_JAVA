package ui.main;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Frame;

import javax.swing.JPanel;

import ui.thongke.ThongKeUI;
import ui.banhang.BanHangUI;
import ui.hoadon.HoaDonUI;
import ui.khachhang.KhachHangUI;
import ui.nhacungcap.NhaCungCapUI;
import ui.nhanvien.NhanVienUI;
import ui.nhapkho.NhapKhoUI;
import ui.quanlysanpham.QuanLySanPhamUI;

public class ContentPaner extends JPanel {

    private QuanLySanPhamUI sanPhamUI;
    private NhaCungCapUI nhaCungCapUI;
    private NhapKhoUI nhapKhoUI;
    private ThongKeUI thongKeUI;
    private BanHangUI banHangUI;
    private KhachHangUI khachHangUI;
    private HoaDonUI hoaDonUI;
    private NhanVienUI nhanVienUI;

    public ContentPaner(Frame ouner) {

        setLayout(new CardLayout());
        setBackground(Color.white);
        sanPhamUI = new QuanLySanPhamUI();
        nhaCungCapUI = new NhaCungCapUI();
        nhapKhoUI = new NhapKhoUI(ouner);
        thongKeUI = new ThongKeUI();
        banHangUI = new BanHangUI();
        khachHangUI = new KhachHangUI();
        hoaDonUI = new HoaDonUI();
        nhanVienUI = new NhanVienUI();

        add(sanPhamUI, "Quản lý sản phẩm");
        add(nhaCungCapUI, "Nhà cung cấp");
        add(nhapKhoUI, "Nhập kho");
        add(thongKeUI, "Thống kê");
        add(banHangUI, "Bán hàng");
        add(khachHangUI, "Khách hàng");
        add(hoaDonUI, "Hóa đơn");
        add(nhanVienUI, "Nhân viên");
    }

    public void loadAll() {

    }
}
