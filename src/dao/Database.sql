IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'NhomQuyen')
BEGIN
    CREATE TABLE NhomQuyen (
        MaNQ VARCHAR(50) NOT NULL PRIMARY KEY,
        TenPhanQuyen NVARCHAR(100)
    )
    INSERT INTO NhomQuyen (MaNQ, TenPhanQuyen) VALUES 
    ('NQ01', N'Quản Lý'),
    ('NQ02', N'Nhân Viên Bán Hàng'),
    ('NQ03', N'Nhân Viên Kho')
END;

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Quyen')
BEGIN
    CREATE TABLE Quyen (
        MaQuyen VARCHAR(50) NOT NULL PRIMARY KEY,
        TenQuyen NVARCHAR(100)
    )
    INSERT INTO Quyen (MaQuyen, TenQuyen) VALUES 
    ('Q01', N'Quản lý sản phẩm'),
    ('Q02', N'Quản lý nhân viên'),
    ('Q03', N'Bán hàng'),
    ('Q04', N'Nhập kho')
END;

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'PhanQuyen')
BEGIN
    CREATE TABLE PhanQuyen (
        MaQuyen VARCHAR(50) NOT NULL,
        MaNQ VARCHAR(50) NOT NULL,
        PRIMARY KEY (MaQuyen, MaNQ)
    )
    INSERT INTO PhanQuyen (MaQuyen, MaNQ) VALUES 
    ('Q01', 'NQ01'), ('Q02', 'NQ01'), ('Q03', 'NQ01'), ('Q04', 'NQ01'),
    ('Q03', 'NQ02'),
    ('Q04', 'NQ03')
END;

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'TaiKhoan')
BEGIN
    CREATE TABLE TaiKhoan (
        TenDangNhap VARCHAR(50) NOT NULL PRIMARY KEY,
        MatKhau VARCHAR(255) NOT NULL,
        maNQ VARCHAR(50),
        TrangThai BIT
    )
    INSERT INTO TaiKhoan (TenDangNhap, MatKhau, maNQ, TrangThai) VALUES 
    ('admin', '123456', 'NQ01', 1),
    ('nhanvien1', '123456', 'NQ02', 1),
    ('nhanvienkho', '123456', 'NQ03', 1)
END;

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'DanhMuc')
BEGIN
    CREATE TABLE DanhMuc (
        MaDM VARCHAR(50) NOT NULL PRIMARY KEY,
        TenDM NVARCHAR(100),
        TrangThai BIT
    )
    INSERT INTO DanhMuc (MaDM, TenDM, TrangThai) VALUES 
    ('DM01', N'Cà Phê', 1),
    ('DM02', N'Trà Sữa', 1),
    ('DM03', N'Nước Ngọt', 1),
    ('DM04', N'Đồ Ăn Nhẹ', 1)
END;

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'NhaCungCap')
BEGIN
    CREATE TABLE NhaCungCap (
        MaNCC VARCHAR(50) NOT NULL PRIMARY KEY,
        TenNCC NVARCHAR(255),
        SoDienThoai VARCHAR(20),
        DiaChi NVARCHAR(255),
        TrangThai BIT
    )
    INSERT INTO NhaCungCap (MaNCC, TenNCC, SoDienThoai, DiaChi, TrangThai) VALUES 
    ('NCC01', N'Công ty Suntory PepsiCo', '02839123456','Việt Nam', 1),
    ('NCC02', N'Trung Nguyên Legend', '02839123789','Cam', 1),
    ('NCC03', N'Vinamilk', '02839123999','Mỹ', 1)
END;

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'SanPham')
BEGIN
    CREATE TABLE SanPham (
        MaSP VARCHAR(50) NOT NULL PRIMARY KEY,
        TenSP NVARCHAR(255),
        MaDM VARCHAR(50),
        GiaNhap DECIMAL(18, 2),
        GiaBan DECIMAL(18, 2),
        MaNCC VARCHAR(50),
        SoLuongTon INT,
        LoaiNuoc NVARCHAR(50),
        Anh NVARCHAR(MAX),
        TheTich INT,
        MucCanhBao INT,
        TrangThai BIT
    )
    INSERT INTO SanPham (MaSP, TenSP, MaDM, GiaNhap, GiaBan, MaNCC, SoLuongTon, LoaiNuoc, Anh, TheTich, MucCanhBao, TrangThai) VALUES 
    ('SP01', N'Pepsi Lon', 'DM03', 8000, 15000, 'NCC01', 100, N'Có sẵn', '/assets/img/pepsi.png', 330, 10, 1),
    ('SP02', N'Cà Phê Đen Đá', 'DM01', 12000, 25000, 'NCC02', 50, N'Pha chế', '/assets/img/pepsi.png', 500, 10, 1),
    ('SP03', N'Cà Phê Sữa Đá', 'DM01', 15000, 30000, 'NCC02', 45, N'Pha chế', '/assets/img/pepsi.png', 500, 10, 1),
    ('SP04', N'Trà Sữa Truyền Thống', 'DM02', 18000, 35000, 'NCC03', 60, N'Có sẵn', '/assets/img/pepsi.png', 500, 10, 1)
END;

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Size')
BEGIN
    CREATE TABLE Size (
        MaSize VARCHAR(50) NOT NULL PRIMARY KEY,
        MaSP VARCHAR(50),
        TenSize NVARCHAR(50),
        PhanTramGia INT,
        PhanTramNL INT,
        TrangThai BIT
    )
    INSERT INTO Size (MaSize, MaSP, TenSize, PhanTramGia, PhanTramNL, TrangThai) VALUES 
    ('SZ01', 'SP02', N'Nhỏ', 0, 100, 1),
    ('SZ02', 'SP02', N'Vừa', 10, 120, 1),
    ('SZ03', 'SP02', N'Lớn', 20, 150, 1),
    ('SZ04', 'SP04', N'Vừa', 0, 100, 1),
    ('SZ05', 'SP04', N'Lớn', 15, 130, 1)
END;

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'NguyenLieu')
BEGIN
    CREATE TABLE NguyenLieu (
        MaNL VARCHAR(50) NOT NULL PRIMARY KEY,
        TenNL NVARCHAR(255),
        MaNCC VARCHAR(50),
        TenNhaCC NVARCHAR(255),
        Gia DECIMAL(18, 2),
        DonVi NVARCHAR(50),
        MucCanhBao INT,
        TrangThai BIT
    )
    INSERT INTO NguyenLieu (MaNL, TenNL, MaNCC, TenNhaCC, Gia, DonVi, MucCanhBao, TrangThai) VALUES 
    ('NL01', N'Hạt Cà Phê Robusta', 'NCC02', N'Trung Nguyên Legend', 200000, 'kg', 5, 1),
    ('NL02', N'Sữa Đặc Ngôi Sao', 'NCC03', N'Vinamilk', 25000, 'hop', 10, 1),
    ('NL03', N'Đường Cát Trắng', 'NCC01', N'Công ty Suntory PepsiCo', 15000, 'kg', 5, 1)
END;

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'CongThuc')
BEGIN
    CREATE TABLE CongThuc (
        MaCT VARCHAR(50) NOT NULL PRIMARY KEY,
        MaSP VARCHAR(50),
        TrangThai BIT
    )
    INSERT INTO CongThuc (MaCT, MaSP, TrangThai) VALUES 
    ('CT01', 'SP03', 1)
END;

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'ChiTietCongThuc')
BEGIN
    CREATE TABLE ChiTietCongThuc (
        MaCT VARCHAR(50) NOT NULL,
        MaNL VARCHAR(50) NOT NULL,
        SoLuong DECIMAL(18, 4),
        PRIMARY KEY (MaCT, MaNL)
    )
    INSERT INTO ChiTietCongThuc (MaCT, MaNL, SoLuong) VALUES 
    ('CT01', 'NL01', 0.02),
    ('CT01', 'NL02', 0.03)
END;

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'NhanVien')
BEGIN
    CREATE TABLE NhanVien (
        MaNV VARCHAR(50) NOT NULL PRIMARY KEY,
        TenNV NVARCHAR(100),
        GioiTinh NVARCHAR(10),
        NgaySinh DATE,
        SDT VARCHAR(20),
        DiaChi NVARCHAR(255),
        ChucVu NVARCHAR(100),
        TaiKhoan VARCHAR(50),
        TrangThai BIT
    )
    INSERT INTO NhanVien (MaNV, TenNV, GioiTinh, NgaySinh, SDT, DiaChi, ChucVu, TaiKhoan, TrangThai) VALUES 
    ('NV01', N'Nguyễn Văn Quản Lý', N'Nam', '1990-01-01', '0909123456', N'TP.HCM', N'Cửa hàng trưởng', 'admin', 1),
    ('NV02', N'Trần Thị Thu Ngân', N'Nữ', '2000-05-15', '0909123457', N'Bình Dương', N'Thu ngân', 'nhanvien1', 1),
    ('NV03', N'Lê Văn Kho', N'Nam', '1995-08-20', '0909123458', N'Đồng Nai', N'Thủ kho', 'nhanvienkho', 1)
END;

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'HangThanhVien')
BEGIN
    CREATE TABLE HangThanhVien (
        MaHang VARCHAR(50) NOT NULL PRIMARY KEY,
        TenHang NVARCHAR(100),
        PhanTramGiam INT,
        DieuKien DECIMAL(18, 2),
        TrangThai BIT
    )
    INSERT INTO HangThanhVien (MaHang, TenHang, PhanTramGiam, DieuKien, TrangThai) VALUES 
    ('HTV01', N'Thành Viên Mới', 0, 0, 1),
    ('HTV02', N'Thành Viên Bạc', 5, 1000000, 1),
    ('HTV03', N'Thành Viên Vàng', 10, 5000000, 1)
END;

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'KhachHang')
BEGIN
    CREATE TABLE KhachHang (
        MaKH VARCHAR(50) NOT NULL PRIMARY KEY,
        TenKH NVARCHAR(100),
        GioiTinh NVARCHAR(10),
        SDT VARCHAR(20),
        TenDaMua DECIMAL(18, 2),
        MaHang VARCHAR(50),
        TrangThai BIT
    )
    INSERT INTO KhachHang (MaKH, TenKH, GioiTinh, SDT, TenDaMua, MaHang, TrangThai) VALUES 
    ('KH001', N'Nguyễn Văn Khách', N'Nam', '0912345678', 1200000, 'HTV02', 1),
    ('KH002', N'Trần Thị Mua', N'Nữ', '0987654321', 50000, 'HTV01', 1)
END;

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'KhuyenMai')
BEGIN
    CREATE TABLE KhuyenMai (
        MaKM VARCHAR(50) NOT NULL PRIMARY KEY,
        PhanTramGiam INT,
        TuNgay DATE,
        DenNgay DATE,
        TrangThai BIT
    )
    INSERT INTO KhuyenMai (MaKM, PhanTramGiam, TuNgay, DenNgay, TrangThai) VALUES 
    ('KM01', 10, '2023-01-01', '2023-12-31', 0),
    ('KM02', 20, '2024-01-01', '2025-12-31', 1)
END;

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'HoaDon')
BEGIN
    CREATE TABLE HoaDon (
        MaHD VARCHAR(50) NOT NULL PRIMARY KEY,
        MaNV VARCHAR(50),
        MaKH VARCHAR(50),
        MaKM VARCHAR(50),
        NgayBan DATE,
        TongTien DECIMAL(18, 2),
        TienKhuyenMai DECIMAL(18, 2),
        TrangThai BIT
    )
    INSERT INTO HoaDon (MaHD, MaNV, MaKH, MaKM, NgayBan, TongTien, TienKhuyenMai, TrangThai) VALUES 
    ('HD001', 'NV02', 'KH001', 'KM02', '2024-01-15', 55000, 11000, 1),
    ('HD002', 'NV02', 'KH002', NULL, '2024-01-16', 15000, 0, 1)
END;

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'ChiTietHoaDon')
BEGIN
    CREATE TABLE ChiTietHoaDon (
        MaHD VARCHAR(50) NOT NULL,
        MaSP VARCHAR(50) NOT NULL,
        MaSize VARCHAR(50) NOT NULL,
        SoLuong INT,
        Gia DECIMAL(18, 2),
        PRIMARY KEY (MaHD, MaSP, MaSize)
    )
    INSERT INTO ChiTietHoaDon (MaHD, MaSP, MaSize, SoLuong, Gia) VALUES 
    ('HD001', 'SP03', 'SZ02', 1, 30000),
    ('HD001', 'SP01', 'SZ01', 1, 15000)
END;

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'PhieuNhapSanPham')
BEGIN
    CREATE TABLE PhieuNhapSanPham (
        MaLoSP VARCHAR(50) NOT NULL PRIMARY KEY, 
        NgayNhap DATE,
        MaNV VARCHAR(50),
        TongTien DECIMAL(18, 2),
        MaNCC VARCHAR(50),
        TrangThai BIT
    )
    INSERT INTO PhieuNhapSanPham (MaLoSP, NgayNhap, MaNV, TongTien, MaNCC, TrangThai) VALUES 
    ('PNSP01', '2024-01-10', 'NV03', 8000000, 'NCC01', 1)
END;

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'LoSanPham')
BEGIN
    CREATE TABLE LoSanPham (
        MaLoSP VARCHAR(50) NOT NULL PRIMARY KEY,
        MaPN VARCHAR(50),
        MaSP VARCHAR(50),
        SoLuong INT,
        NgayNhap DATE,
        NgaySanXuat DATE,
        HanSuDung DATE,
        TongTien DECIMAL(18, 2),
        TrangThai BIT
    )
    INSERT INTO LoSanPham (MaLoSP, MaPN, MaSP, SoLuong, NgayNhap, NgaySanXuat, HanSuDung, TongTien, TrangThai) VALUES 
    ('LOSP01', 'PNSP01', 'SP01', 1000, '2024-01-10', '2024-01-01', '2025-01-01', 8000000, 1)
END;

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'PhieuNhapNguyenLieu')
BEGIN
    CREATE TABLE PhieuNhapNguyenLieu (
        MaLoNL VARCHAR(50) NOT NULL PRIMARY KEY,
        NgayNhap DATE,
        MaNV VARCHAR(50),
        TongTien DECIMAL(18, 2),
        MaNCC VARCHAR(50),
        TrangThai BIT
    )
    INSERT INTO PhieuNhapNguyenLieu (MaLoNL, NgayNhap, MaNV, TongTien, MaNCC, TrangThai) VALUES 
    ('PNNL01', '2024-01-12', 'NV03', 5000000, 'NCC02', 1)
END;

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'LoNguyenLieu')
BEGIN
    CREATE TABLE LoNguyenLieu (
        MaLoNL VARCHAR(50) NOT NULL PRIMARY KEY,
        MaPN VARCHAR(50),
        MaNL VARCHAR(50),
        SoLuong INT,
        NgayNhap DATE,
        NgaySanXuat DATE,
        HanSuDung DATE,
        TrangThai BIT
    )
    INSERT INTO LoNguyenLieu (MaLoNL, MaPN, MaNL, SoLuong, NgayNhap, NgaySanXuat, HanSuDung, TrangThai) VALUES 
    ('LONL01', 'PNNL01', 'NL01', 25, '2024-01-12', '2024-01-01', '2024-06-01', 1)
END;

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'PhieuHuySanPham')
BEGIN
    CREATE TABLE PhieuHuySanPham (
        MaPH VARCHAR(50) NOT NULL,
        MaLo VARCHAR(50) NOT NULL,
        NgayHuy DATE,
        MaNV VARCHAR(50),
        LyDo NVARCHAR(MAX),
        TongGiaTri DECIMAL(18, 2),
        TrangThai BIT,
        PRIMARY KEY (MaPH, MaLo)
    )
    INSERT INTO PhieuHuySanPham (MaPH, MaLo, NgayHuy, MaNV, LyDo, TongGiaTri, TrangThai) VALUES 
    ('PHSP01', 'LOSP01', '2024-02-01', 'NV01', N'Lon bị móp méo do vận chuyển', 80000, 1)
END;

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'PhieuHuyNguyenLieu')
BEGIN
    CREATE TABLE PhieuHuyNguyenLieu (
        MaPH VARCHAR(50) NOT NULL,
        MaLo VARCHAR(50) NOT NULL,
        NgayHuy DATE,
        MaNV VARCHAR(50),
        LyDo NVARCHAR(MAX),
        TongTien DECIMAL(18, 2),
        TrangThai BIT,
        PRIMARY KEY (MaPH, MaLo)
    )
END;

IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_TaiKhoan_NhomQuyen')
    ALTER TABLE TaiKhoan ADD CONSTRAINT FK_TaiKhoan_NhomQuyen FOREIGN KEY (maNQ) REFERENCES NhomQuyen(MaNQ);

IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_PhanQuyen_Quyen')
    ALTER TABLE PhanQuyen ADD CONSTRAINT FK_PhanQuyen_Quyen FOREIGN KEY (MaQuyen) REFERENCES Quyen(MaQuyen);

IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_PhanQuyen_NhomQuyen')
    ALTER TABLE PhanQuyen ADD CONSTRAINT FK_PhanQuyen_NhomQuyen FOREIGN KEY (MaNQ) REFERENCES NhomQuyen(MaNQ);

IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_NhanVien_TaiKhoan')
    ALTER TABLE NhanVien ADD CONSTRAINT FK_NhanVien_TaiKhoan FOREIGN KEY (TaiKhoan) REFERENCES TaiKhoan(TenDangNhap);

IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_SanPham_DanhMuc')
    ALTER TABLE SanPham ADD CONSTRAINT FK_SanPham_DanhMuc FOREIGN KEY (MaDM) REFERENCES DanhMuc(MaDM);

IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_SanPham_NhaCungCap')
    ALTER TABLE SanPham ADD CONSTRAINT FK_SanPham_NhaCungCap FOREIGN KEY (MaNCC) REFERENCES NhaCungCap(MaNCC);

IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_Size_SanPham')
    ALTER TABLE Size ADD CONSTRAINT FK_Size_SanPham FOREIGN KEY (MaSP) REFERENCES SanPham(MaSP);

IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_NguyenLieu_NhaCungCap')
    ALTER TABLE NguyenLieu ADD CONSTRAINT FK_NguyenLieu_NhaCungCap FOREIGN KEY (MaNCC) REFERENCES NhaCungCap(MaNCC);

IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_CongThuc_SanPham')
    ALTER TABLE CongThuc ADD CONSTRAINT FK_CongThuc_SanPham FOREIGN KEY (MaSP) REFERENCES SanPham(MaSP);

IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_ChiTietCongThuc_CongThuc')
    ALTER TABLE ChiTietCongThuc ADD CONSTRAINT FK_ChiTietCongThuc_CongThuc FOREIGN KEY (MaCT) REFERENCES CongThuc(MaCT);

IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_ChiTietCongThuc_NguyenLieu')
    ALTER TABLE ChiTietCongThuc ADD CONSTRAINT FK_ChiTietCongThuc_NguyenLieu FOREIGN KEY (MaNL) REFERENCES NguyenLieu(MaNL);

IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_KhachHang_HangThanhVien')
    ALTER TABLE KhachHang ADD CONSTRAINT FK_KhachHang_HangThanhVien FOREIGN KEY (MaHang) REFERENCES HangThanhVien(MaHang);

IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_HoaDon_NhanVien')
    ALTER TABLE HoaDon ADD CONSTRAINT FK_HoaDon_NhanVien FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV);

IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_HoaDon_KhachHang')
    ALTER TABLE HoaDon ADD CONSTRAINT FK_HoaDon_KhachHang FOREIGN KEY (MaKH) REFERENCES KhachHang(MaKH);

IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_HoaDon_KhuyenMai')
    ALTER TABLE HoaDon ADD CONSTRAINT FK_HoaDon_KhuyenMai FOREIGN KEY (MaKM) REFERENCES KhuyenMai(MaKM);

IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_ChiTietHoaDon_HoaDon')
    ALTER TABLE ChiTietHoaDon ADD CONSTRAINT FK_ChiTietHoaDon_HoaDon FOREIGN KEY (MaHD) REFERENCES HoaDon(MaHD);

IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_ChiTietHoaDon_SanPham')
    ALTER TABLE ChiTietHoaDon ADD CONSTRAINT FK_ChiTietHoaDon_SanPham FOREIGN KEY (MaSP) REFERENCES SanPham(MaSP);

IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_ChiTietHoaDon_Size')
    ALTER TABLE ChiTietHoaDon ADD CONSTRAINT FK_ChiTietHoaDon_Size FOREIGN KEY (MaSize) REFERENCES Size(MaSize);

IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_PhieuNhapSP_NhanVien')
    ALTER TABLE PhieuNhapSanPham ADD CONSTRAINT FK_PhieuNhapSP_NhanVien FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV);

IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_PhieuNhapSP_NCC')
    ALTER TABLE PhieuNhapSanPham ADD CONSTRAINT FK_PhieuNhapSP_NCC FOREIGN KEY (MaNCC) REFERENCES NhaCungCap(MaNCC);

IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_PhieuNhapNL_NhanVien')
    ALTER TABLE PhieuNhapNguyenLieu ADD CONSTRAINT FK_PhieuNhapNL_NhanVien FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV);

IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_PhieuNhapNL_NCC')
    ALTER TABLE PhieuNhapNguyenLieu ADD CONSTRAINT FK_PhieuNhapNL_NCC FOREIGN KEY (MaNCC) REFERENCES NhaCungCap(MaNCC);

IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_LoSanPham_PhieuNhapSP')
    ALTER TABLE LoSanPham ADD CONSTRAINT FK_LoSanPham_PhieuNhapSP FOREIGN KEY (MaPN) REFERENCES PhieuNhapSanPham(MaLoSP);

IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_LoSanPham_SanPham')
    ALTER TABLE LoSanPham ADD CONSTRAINT FK_LoSanPham_SanPham FOREIGN KEY (MaSP) REFERENCES SanPham(MaSP);

IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_LoNguyenLieu_PhieuNhapNL')
    ALTER TABLE LoNguyenLieu ADD CONSTRAINT FK_LoNguyenLieu_PhieuNhapNL FOREIGN KEY (MaPN) REFERENCES PhieuNhapNguyenLieu(MaLoNL);

IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_LoNguyenLieu_NguyenLieu')
    ALTER TABLE LoNguyenLieu ADD CONSTRAINT FK_LoNguyenLieu_NguyenLieu FOREIGN KEY (MaNL) REFERENCES NguyenLieu(MaNL);

IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_PhieuHuySP_NhanVien')
    ALTER TABLE PhieuHuySanPham ADD CONSTRAINT FK_PhieuHuySP_NhanVien FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV);

IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_PhieuHuySP_LoSanPham')
    ALTER TABLE PhieuHuySanPham ADD CONSTRAINT FK_PhieuHuySP_LoSanPham FOREIGN KEY (MaLo) REFERENCES LoSanPham(MaLoSP);

IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_PhieuHuyNL_NhanVien')
    ALTER TABLE PhieuHuyNguyenLieu ADD CONSTRAINT FK_PhieuHuyNL_NhanVien FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV);

IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_PhieuHuyNL_LoNguyenLieu')
    ALTER TABLE PhieuHuyNguyenLieu ADD CONSTRAINT FK_PhieuHuyNL_LoNguyenLieu FOREIGN KEY (MaLo) REFERENCES LoNguyenLieu(MaLoNL);