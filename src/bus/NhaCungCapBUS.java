package bus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dao.NhaCungCapDAO;
import dto.ChiTietNhaCungCap;
import dto.NguyenLieu;
import dto.NhaCungCap;
import dto.SanPham;

public class NhaCungCapBUS {

    private static NhaCungCapBUS nhaCungCapBUS = null;

    public static NhaCungCapBUS getNhaCungCapBUS() {
        if (nhaCungCapBUS == null) {
            nhaCungCapBUS = new NhaCungCapBUS();
        }
        return nhaCungCapBUS;
    }

    private NhaCungCapDAO nhaCungCapDAO = new NhaCungCapDAO();
    private ArrayList<NhaCungCap> listNhaCungCap;
    private boolean canUpdate = false;

    public void khoitao() {
        listNhaCungCap = nhaCungCapDAO.layListNhaCungCap();
        ArrayList<ChiTietNhaCungCap> listChiTietNCC = ChiTietNhaCungCapBUS.getChiTietNhaCungCapBUS()
                .layListChiTietNhaCungCap();

        Map<String, ArrayList<ChiTietNhaCungCap>> map = new HashMap<>();
        for (NhaCungCap nhaCungCap : listNhaCungCap) {
            map.put(nhaCungCap.getMaNCC(), new ArrayList<ChiTietNhaCungCap>());
        }
        for (ChiTietNhaCungCap chiTietNhaCungCap : listChiTietNCC) {
            ArrayList<ChiTietNhaCungCap> listCTNCC = map.get(chiTietNhaCungCap.getMaNCC());
            if (listCTNCC != null) {
                listCTNCC.add(chiTietNhaCungCap);
                map.put(chiTietNhaCungCap.getMaNCC(), listCTNCC);
            }
        }
        SanPhamBUS sanPhamBUS = SanPhamBUS.getSanPhamBUS();
        NguyenLieuBUS nguyenLieuBUS = NguyenLieuBUS.getNguyenLieuBUS();
        for (NhaCungCap nhaCungCap : listNhaCungCap) {
            for (ChiTietNhaCungCap chiTietNhaCungCap : map.get(nhaCungCap.getMaNCC())) {
                if (chiTietNhaCungCap.getLoaiDoiTuong().equals("Sản phẩm")) {
                    nhaCungCap.themSanPham(sanPhamBUS.timSanPham(chiTietNhaCungCap.getMaDoiTuong()));
                }
                if (chiTietNhaCungCap.getLoaiDoiTuong().equals("Nguyên liệu")) {
                    nhaCungCap.themNguyenLieu(nguyenLieuBUS.timNguyenLieu(chiTietNhaCungCap.getMaDoiTuong()));
                }
            }
        }
    }

    public ArrayList<NhaCungCap> laylistNhaCungCap() {
        if (canUpdate || listNhaCungCap == null) {
            khoitao();
            canUpdate = false;
        }
        return listNhaCungCap;
    }

    public NhaCungCap timNhaCungCap(String ma) {
        if (canUpdate || listNhaCungCap == null) {
            khoitao();
            canUpdate = false;
        }
        for (NhaCungCap ncc : listNhaCungCap) {
            if (ncc.getMaNCC().equals(ma)) {
                return ncc;
            }
        }
        return null;
    }

    public NhaCungCap timNhaCungCapTheoTen(String ten) {
        if (canUpdate || listNhaCungCap == null) {
            khoitao();
            canUpdate = false;
        }
        for (NhaCungCap ncc : listNhaCungCap) {
            if (ncc.getTenNCC().equalsIgnoreCase(ten)) {
                return ncc;
            }
        }
        return null;
    }

    public ArrayList<String> layLuaChonNCC() {
        if (canUpdate || listNhaCungCap == null) {
            khoitao();
            canUpdate = false;
        }
        ArrayList<String> list = new ArrayList<>();
        for (NhaCungCap nhaCungCap : listNhaCungCap) {
            list.add(nhaCungCap.getTenNCC());
        }
        return list;
    }

    public ArrayList<String> layLuaChonNCCNguyenLieu() {
        if (canUpdate || listNhaCungCap == null) {
            khoitao();
            canUpdate = false;
        }
        ArrayList<String> list = new ArrayList<>();
        for (NhaCungCap nhaCungCap : listNhaCungCap) {
            if (nhaCungCap.getListNguyenLieuCungCap() != null) {
                list.add(nhaCungCap.getTenNCC());
            }

        }
        return list;
    }

    public ArrayList<String> layLuaChonNCCSanPham() {
        if (canUpdate || listNhaCungCap == null) {
            khoitao();
            canUpdate = false;
        }
        ArrayList<String> list = new ArrayList<>();
        for (NhaCungCap nhaCungCap : listNhaCungCap) {
            if (nhaCungCap.getListSanPhamCungCap() != null) {
                list.add(nhaCungCap.getTenNCC());
            }

        }
        return list;
    }

    public void danhDauCanCapNhat() {
        this.canUpdate = true;
    }

    public static void main(String[] args) {
        System.out.println("---------- KIỂM TRA DỮ LIỆU NHÀ CUNG CẤP ----------");

        // 1. Khởi tạo BUS (Hàm khoitao() sẽ tự động chạy)
        NhaCungCapBUS nccBUS = NhaCungCapBUS.getNhaCungCapBUS();

        // 2. Lấy danh sách tất cả nhà cung cấp
        ArrayList<NhaCungCap> listNCC = nccBUS.laylistNhaCungCap();

        if (listNCC == null || listNCC.isEmpty()) {
            System.out.println("Danh sách Nhà cung cấp trống hoặc lỗi kết nối!");
            return;
        }

        // 3. Duyệt qua từng nhà cung cấp để kiểm tra khả năng cung cấp
        for (NhaCungCap ncc : listNCC) {
            System.out.println("\n-------------------------------------------");
            System.out.println("Mã NCC: " + ncc.getMaNCC());
            System.out.println("Tên NCC: " + ncc.getTenNCC());
            System.out.println("Địa chỉ: " + ncc.getDiaChi());

            // Kiểm tra danh sách Sản phẩm cung cấp
            ArrayList<SanPham> listSP = ncc.getListSanPhamCungCap();
            if (listSP != null && !listSP.isEmpty()) {
                System.out.println("  => Cung cấp [" + listSP.size() + "] Sản phẩm:");
                for (SanPham sp : listSP) {
                    System.out.println("     + " + sp.getMaSP() + " - " + sp.getTenSP());
                }
            } else {
                System.out.println("  => Không cung cấp Sản phẩm nào.");
            }

            // Kiểm tra danh sách Nguyên liệu cung cấp
            ArrayList<NguyenLieu> listNL = ncc.getListNguyenLieuCungCap();
            if (listNL != null && !listNL.isEmpty()) {
                System.out.println("  => Cung cấp [" + listNL.size() + "] Nguyên liệu:");
                for (NguyenLieu nl : listNL) {
                    System.out.println("     + " + nl.getMaNL() + " - " + nl.getTenNL());
                }
            } else {
                System.out.println("  => Không cung cấp Nguyên liệu nào.");
            }
        }

        // 4. Test các hàm lọc ComboBox
        System.out.println("\n===========================================");
        System.out.println("TEST CÁC HÀM LỌC LỰA CHỌN (COMBOBOX):");
        System.out.println("Lọc NCC Nguyên Liệu: " + nccBUS.layLuaChonNCCNguyenLieu());
        System.out.println("Lọc NCC Sản Phẩm: " + nccBUS.layLuaChonNCCSanPham());
    }

}