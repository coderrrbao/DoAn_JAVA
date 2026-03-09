package ui.main;

import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import ui.banhang.BanHangUI;
import ui.hangthanhvien.HangThanhVienUI;
import ui.hoadon.HoaDonUI;
import ui.khachhang.KhachHangUI;
import ui.khuyenmai.KhuyenMaiUI;
import ui.kiemke.KiemKeUI;
import ui.nguyenlieu.NguyenLieuUI;
import ui.nhacungcap.NhaCungCapUI;
import ui.nhanvien.NhanVienUI;
import ui.nhapkho.NhapKhoUI;
import ui.phanquyen.NhomQuyenUI;
import ui.quanlysanpham.QuanLySanPhamUI;
import ui.taikhoan.TaiKhoanUI;
import ui.thongke.ThongKeUI;
import ui.tonkho.TonKhoUI;
import ui.xuatkho.XuatKhoUI;
import util.Luong;

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
  private NhomQuyenUI phanQuyenUI;
  private NguyenLieuUI nguyenLieuUI;
  private HangThanhVienUI hangThanhVienUI;
  private CardLayout cardLayout;

  public ContentPaner(JFrame owner) {
    cardLayout = new CardLayout();
    setLayout(cardLayout);
    setBackground(Color.white);
    quanLySanPhamUI = new QuanLySanPhamUI(owner);
    add(quanLySanPhamUI, "Quản lý sản phẩm");
    Luong.handleDatabaseTask(
        () -> {
          nhaCungCapUI = new NhaCungCapUI();
          nguyenLieuUI = new NguyenLieuUI();
          nhapKhoUI = new NhapKhoUI();
          thongKeUI = new ThongKeUI();
          banHangUI = new BanHangUI();
          khachHangUI = new KhachHangUI();
          hoaDonUI = new HoaDonUI();
          nhanVienUI = new NhanVienUI();
          taiKhoanUI = new TaiKhoanUI();
          xuatKhoUI = new XuatKhoUI(owner);
          tonKhoUI = new TonKhoUI();
          khuyenMaiUI = new KhuyenMaiUI();
          phanQuyenUI = new NhomQuyenUI();
          kiemKeUI = new KiemKeUI();
          hangThanhVienUI = new HangThanhVienUI();
        },
        () -> {
          add(nhaCungCapUI, "Nhà cung cấp");
          add(nguyenLieuUI, "Nguyên liệu");
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
          add(hangThanhVienUI, "Hạng thành viên");

          banHangUI.setOnThanhToanSuccess(() -> {
            if (hoaDonUI != null) {
              hoaDonUI.loadData();
            }
          });

          revalidate();
          repaint();
        });
  }

  public void switchPage(String name) {
    cardLayout.show(this, name);
  }

  public void suaLaiGiaoDienTheoQuyen() {

    if (quanLySanPhamUI != null)
      quanLySanPhamUI.suaLaiGiaoDienTheoQuyen();
    if (nhaCungCapUI != null)
      nhaCungCapUI.suaLaiGiaoDienTheoQuyen();
    if (nguyenLieuUI != null)
      nguyenLieuUI.suaLaiGiaoDienTheoQuyen();
    if (nhapKhoUI != null)
      nhapKhoUI.suaLaiGiaoDienTheoQuyen();
    if (khachHangUI != null)
      khachHangUI.suaLaiGiaoDienTheoQuyen();
    if (nhanVienUI != null)
      nhanVienUI.suaLaiGiaoDienTheoQuyen();
    if (taiKhoanUI != null)
      taiKhoanUI.suaLaiGiaoDienTheoQuyen();
    if (xuatKhoUI != null)
      xuatKhoUI.suaLaiGiaoDienTheoQuyen();
    if (tonKhoUI != null)
      tonKhoUI.suaLaiGiaoDienTheoQuyen();
    if (khuyenMaiUI != null)
      khuyenMaiUI.suaLaiGiaoDienTheoQuyen();
    if (phanQuyenUI != null)
      phanQuyenUI.suaLaiGiaoDienTheoQuyen();
    if (kiemKeUI != null)
      kiemKeUI.suaLaiGiaoDienTheoQuyen();
    if (hangThanhVienUI != null)
      hangThanhVienUI.suaLaiGiaoDienTheoQuyen();
    this.revalidate();
    this.repaint();
  }

  public void loadAllData() {
    quanLySanPhamUI.loadDataFromDatabase();
    nhaCungCapUI.loadDuLieu();
    nguyenLieuUI.loadDataToTable();
    nhaCungCapUI.loadDuLieu();
    hoaDonUI.loadData();
    khachHangUI.hienThiDanhSachKhachHang();
    hoaDonUI.loadData();
    nhanVienUI.hienThiDanhSachNhanVien();
    taiKhoanUI.hienThiDanhSachTaiKhoan();
    xuatKhoUI.loadData();
    tonKhoUI.loadData();
    khuyenMaiUI.loadDataToTable();
    phanQuyenUI.loadDuLieu();
    kiemKeUI.loaiDuLieu();
    hangThanhVienUI.loadDataToTable();
  }
}
