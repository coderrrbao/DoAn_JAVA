package ui.main;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ui.thongke.ThongKeUI;
import ui.tonkho.TonKhoUI;
import ui.xuatkho.XuatKhoUI;
import ui.banhang.BanHangUI;
import ui.hoadon.HoaDonUI;
import ui.khachhang.KhachHangUI;
import ui.khuyenmai.KhuyenMaiUI;
import ui.kiemke.KiemKeUI;
import ui.nhacungcap.NhaCungCapUI;
import ui.nhanvien.NhanVienUI;
import ui.nhapkho.NhapKhoUI;
import ui.phanquyen.PhanQuyenUI;
import ui.quanlysanpham.QuanLySanPhamUI;
import ui.taikhoan.TaiKhoanUI;

public class ContentPaner extends JPanel {

    private QuanLySanPhamUI quanLySanPhamUI;
    private NhaCungCapUI nhaCungCapUI;
    private NhapKhoUI nhapKhoUI;
    private ThongKeUI thongKeUI;
    private BanHangUI banHangUI;
    private KhachHangUI khachHangUI;
    private HoaDonUI hoaDonUI;
    private NhanVienUI nhanVienUI;
    private TaiKhoanUI taiKhoanUI;
    private XuatKhoUI xuatKhoUI;
    private TonKhoUI tonKhoUI;
    private KhuyenMaiUI khuyenMaiUI;
    private KiemKeUI kiemKeUI;
    private PhanQuyenUI phanQuyenUI;

    private CardLayout cardLayout;

    public ContentPaner(JFrame owner) {
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        setBackground(Color.white);

        quanLySanPhamUI = new QuanLySanPhamUI(owner);
        nhaCungCapUI = new NhaCungCapUI();
        nhapKhoUI = new NhapKhoUI(owner);
        thongKeUI = new ThongKeUI();
        banHangUI = new BanHangUI();
        khachHangUI = new KhachHangUI();
        hoaDonUI = new HoaDonUI();
        nhanVienUI = new NhanVienUI();
        taiKhoanUI = new TaiKhoanUI();
        xuatKhoUI = new XuatKhoUI();
        tonKhoUI = new TonKhoUI();
        khuyenMaiUI = new KhuyenMaiUI();
        phanQuyenUI = new PhanQuyenUI();
        kiemKeUI = new KiemKeUI();

        add(quanLySanPhamUI, "Quản lý sản phẩm");
        add(nhaCungCapUI, "Nhà cung cấp");
        add(nhapKhoUI, "Nhập kho");
        add(thongKeUI, "Thống kê");
        add(banHangUI, "Bán hàng");
        add(khachHangUI, "Khách hàng");
        add(hoaDonUI, "Hóa đơn");
        add(nhanVienUI, "Nhân viên");
        add(taiKhoanUI, "Tài khoản");
        add(xuatKhoUI, "Xuất kho");
        add(tonKhoUI, "Tồn kho");
        add(khuyenMaiUI, "Khuyến mãi");
        add(phanQuyenUI, "Phân quyền");
        add(kiemKeUI, "Kiểm kê");
    }

    public void switchPage(String name) {
        cardLayout.show(this, name);
    }

    public QuanLySanPhamUI getQuanLySanPhamUI() {
        return quanLySanPhamUI;
    }

    public NhaCungCapUI getNhaCungCapUI() {
        return nhaCungCapUI;
    }

    public NhapKhoUI getNhapKhoUI() {
        return nhapKhoUI;
    }

    public ThongKeUI getThongKeUI() {
        return thongKeUI;
    }

    public BanHangUI getBanHangUI() {
        return banHangUI;
    }

    public KhachHangUI getKhachHangUI() {
        return khachHangUI;
    }

    public HoaDonUI getHoaDonUI() {
        return hoaDonUI;
    }

    public NhanVienUI getNhanVienUI() {
        return nhanVienUI;
    }

    public TaiKhoanUI getTaiKhoanUI() {
        return taiKhoanUI;
    }

    public XuatKhoUI getXuatKhoUI() {
        return xuatKhoUI;
    }

    public TonKhoUI getTonKhoUI() {
        return tonKhoUI;
    }

    public void loadAll() {

    }

}