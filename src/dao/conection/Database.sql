IF NOT EXISTS (SELECT *
FROM sys.tables
WHERE name = 'NhomQuyen')
BEGIN
    CREATE TABLE NhomQuyen
    (
        MaNQ VARCHAR(50) NOT NULL PRIMARY KEY,
        TenNhomQuyen NVARCHAR(100),
        TrangThai BIT
    )
    INSERT INTO NhomQuyen
        (MaNQ, TenNhomQuyen, TrangThai)
    VALUES
        ('NQ01', N'Quản Lý Cửa Hàng', 1),
        ('NQ02', N'Nhân Viên Bán Hàng', 1),
        ('NQ03', N'Nhân Viên Kho', 1),
        ('NQ04', N'Nhân Viên Pha Chế', 1),
        ('NQ05', N'Kế Toán', 1),
        ('NQ06', N'Bảo Vệ', 1),
        ('NQ07', N'Quản Lý Nhân Sự', 1),
        ('NQ08', N'Giám Sát Ca', 1),
        ('NQ09', N'Marketing', 1),
        ('NQ10', N'Admin Hệ Thống', 1)
END;
IF NOT EXISTS (SELECT *
FROM sys.tables
WHERE name = 'Quyen')
BEGIN
    CREATE TABLE Quyen
    (
        MaQuyen VARCHAR(50) NOT NULL PRIMARY KEY,
        MaNQ VARCHAR(50) NOT NULL,
        TenQuyen NVARCHAR(100)
    )
    INSERT INTO Quyen
        (MaQuyen, MaNQ, TenQuyen)
    VALUES
        ('Q01', 'NQ01', N'Quản lý sản phẩm'),
        ('Q02', 'NQ01', N'Quản lý nhân viên'),
        ('Q03', 'NQ02', N'Bán hàng'),
        ('Q04', 'NQ03', N'Nhập kho'),
        ('Q05', 'NQ01', N'Xem báo cáo doanh thu'),
        ('Q06', 'NQ09', N'Quản lý khuyến mãi'),
        ('Q07', 'NQ02', N'Quản lý khách hàng'),
        ('Q08', 'NQ08', N'Duyệt phiếu hủy'),
        ('Q09', 'NQ10', N'Cấu hình hệ thống'),
        ('Q10', 'NQ03', N'Xuất kho'),
        ('Q11', 'NQ05', N'Xem lịch sử giao dịch'),
        ('Q12', 'NQ04', N'Chỉnh sửa công thức')
END;


/* =============================================
   4. BẢNG TÀI KHOẢN (Accounts)
   ============================================= */
IF NOT EXISTS (SELECT *
FROM sys.tables
WHERE name = 'TaiKhoan')
BEGIN
    CREATE TABLE TaiKhoan
    (
        TenTaiKhoan NVARCHAR(50),
        TenDangNhap NVARCHAR(50) NOT NULL PRIMARY KEY,
        MatKhau VARCHAR(255) NOT NULL,
        maNQ VARCHAR(50),
        TrangThai BIT
    )
    INSERT INTO TaiKhoan
        (TenTaiKhoan, TenDangNhap, MatKhau, maNQ, TrangThai)
    VALUES
        (N'Lê Huy Hoàng', 'admin', '123456', 'NQ01', 1),
        (N'Nguyễn Hoài Bảo', 'NV01', '123456', 'NQ02', 1),
        (N'Phạm Hữu Phú Ngáo', 'NV02', '123456', 'NQ03', 1),
        (N'Trần Thị Thu Ngân', 'nhanvien1', '123456', 'NQ02', 1),
        (N'Lê Văn Kho', 'nhanvienkho', '123456', 'NQ03', 1),
        (N'Phạm Văn Pha Chế', 'phache01', '123456', 'NQ04', 1),
        (N'Lý Thị Pha Chế 2', 'phache02', '123456', 'NQ04', 1),
        (N'Ngô Văn Bảo Vệ', 'baove01', '123456', 'NQ06', 1),
        (N'Đỗ Thị Kế Toán', 'ketoan01', '123456', 'NQ05', 1),
        (N'Hoàng Văn Giám Sát', 'giamsat01', '123456', 'NQ08', 1),
        (N'Vũ Thị Marketing', 'marketing01', '123456', 'NQ09', 1),
        (N'Bùi Văn Sale 2', 'sale02', '123456', 'NQ02', 1),
        (N'Trịnh Thị Sale 3', 'sale03', '123456', 'NQ02', 1),
        (N'Phan Văn Kho 2', 'kho02', '123456', 'NQ03', 1)
END;

/* =============================================
   5. BẢNG DANH MỤC (Categories)
   ============================================= */
IF NOT EXISTS (SELECT *
FROM sys.tables
WHERE name = 'DanhMuc')
BEGIN
    CREATE TABLE DanhMuc
    (
        MaDM VARCHAR(50) NOT NULL PRIMARY KEY,
        TenDM NVARCHAR(100),
        TrangThai BIT
    )
    INSERT INTO DanhMuc
        (MaDM, TenDM, TrangThai)
    VALUES
        ('DM01', N'Cà Phê', 1),
        ('DM02', N'Trà Sữa', 1),
        ('DM03', N'Nước Ngọt', 1),
        ('DM04', N'Đồ Ăn Nhẹ', 1),
        ('DM05', N'Sinh Tố & Nước Ép', 1),
        ('DM06', N'Đá Xay (Ice Blended)', 1),
        ('DM07', N'Trà Trái Cây', 1),
        ('DM08', N'Sữa Chua', 1),
        ('DM09', N'Bánh Ngọt', 1),
        ('DM10', N'Topping', 1),
        ('DM11', N'Combo Khuyến Mãi', 1)
END;

/* =============================================
   6. BẢNG NHÀ CUNG CẤP (Suppliers)
   ============================================= */
IF NOT EXISTS (SELECT *
FROM sys.tables
WHERE name = 'NhaCungCap')
BEGIN
    CREATE TABLE NhaCungCap
    (
        MaNCC VARCHAR(50) NOT NULL PRIMARY KEY,
        TenNCC NVARCHAR(255),
        SoDienThoai VARCHAR(20),
        DiaChi NVARCHAR(255),
        TrangThai BIT
    )
    INSERT INTO NhaCungCap
        (MaNCC, TenNCC, SoDienThoai, DiaChi, TrangThai)
    VALUES
        ('NCC01', N'Công ty Suntory PepsiCo', '02839123456', N'Quận 1, TP.HCM', 1),
        ('NCC02', N'Trung Nguyên Legend', '02839123789', N'Buôn Ma Thuột, Đắk Lắk', 1),
        ('NCC03', N'Vinamilk', '02839123999', N'Quận 7, TP.HCM', 1),
        ('NCC04', N'Công ty Coca-Cola VN', '02839111222', N'Thủ Đức, TP.HCM', 1),
        ('NCC05', N'Nông Trại Cầu Đất Farm', '0633888999', N'Đà Lạt, Lâm Đồng', 1),
        ('NCC06', N'Thực Phẩm Ân Nam', '0283555666', N'Bình Thạnh, TP.HCM', 1),
        ('NCC07', N'Bao Bì Tân Phú', '0283777888', N'Tân Phú, TP.HCM', 1),
        ('NCC08', N'Nguyên Liệu Pha Chế Việt', '0909123123', N'Quận 10, TP.HCM', 1),
        ('NCC09', N'Nestlé Việt Nam', '0283666777', N'Biên Hòa, Đồng Nai', 1),
        ('NCC10', N'Công ty CP Acecook', '0283888999', N'Tân Bình, TP.HCM', 1),
        ('NCC11', N'Công ty TNHH Richs', '0283444555', N'Bình Dương', 1)
END;

/* =============================================
   7. BẢNG SẢN PHẨM (Products - 50 Records)
   ============================================= */
IF NOT EXISTS (SELECT *
FROM sys.tables
WHERE name = 'SanPham')
BEGIN
    CREATE TABLE SanPham
    (
        MaSP VARCHAR(50) NOT NULL PRIMARY KEY,
        TenSP NVARCHAR(255),
        MaDM VARCHAR(50),
        GiaBan DECIMAL(18, 2),
        MaNCC VARCHAR(50),
        LoaiNuoc NVARCHAR(50),
        Anh NVARCHAR(MAX),
        TheTich INT,
        MucCanhBao INT,
        TrangThaiXuLy NVARCHAR(50),
        TrangThai BIT
    )

    INSERT INTO SanPham
        (MaSP, TenSP, MaDM, GiaBan, MaNCC, LoaiNuoc, Anh, TheTich, MucCanhBao, TrangThai, TrangThaiXuLy)
    VALUES
        -- DM03: Nước Ngọt
        ('SP01', N'Pepsi Lon', 'DM03', 15000, 'NCC01', N'Có sẵn', '/assets/img/pepsi.png', 330, 10, 1, N'Đã xác nhận'),
        ('SP26', N'Coca Cola', 'DM03', 15000, 'NCC04', N'Có sẵn', '/assets/img/pepsi.png', 330, 10, 1, N'Đã xác nhận'),
        ('SP27', N'7Up', 'DM03', 15000, 'NCC01', N'Có sẵn', '/assets/img/pepsi.png', 330, 10, 1, N'Đã xác nhận'),
        ('SP28', N'Sting Dâu', 'DM03', 18000, 'NCC01', N'Có sẵn', '/assets/img/pepsi.png', 330, 10, 1, N'Đã xác nhận'),
        ('SP29', N'Redbull Thái', 'DM03', 20000, 'NCC06', N'Có sẵn', '/assets/img/pepsi.png', 250, 10, 1, N'Đã xác nhận'),
        ('SP30', N'Nước Suối Dasani', 'DM03', 10000, 'NCC04', N'Có sẵn', '/assets/img/pepsi.png', 500, 20, 1, N'Đã xác nhận'),
        ('SP31', N'Soda Schweppes', 'DM03', 18000, 'NCC04', N'Có sẵn', '/assets/img/pepsi.png', 330, 5, 1, N'Đã xác nhận'),

        -- DM01: Cà Phê
        ('SP02', N'Cà Phê Đen Đá', 'DM01', 25000, 'NCC02', N'Pha chế', '/assets/img/pepsi.png', 500, 10, 1, N'Đã xác nhận'),
        ('SP03', N'Cà Phê Sữa Đá', 'DM01', 30000, 'NCC02', N'Pha chế', '/assets/img/pepsi.png', 500, 10, 1, N'Đã xác nhận'),
        ('SP05', N'Bạc Xỉu', 'DM01', 32000, 'NCC02', N'Pha chế', '/assets/img/pepsi.png', 500, 10, 1, N'Đã xác nhận'),
        ('SP06', N'Cà Phê Muối', 'DM01', 35000, 'NCC02', N'Pha chế', '/assets/img/pepsi.png', 500, 10, 1, N'Đã xác nhận'),
        ('SP07', N'Americano Đá', 'DM01', 28000, 'NCC02', N'Pha chế', '/assets/img/pepsi.png', 500, 10, 1, N'Đã xác nhận'),
        ('SP08', N'Cappuccino Nóng', 'DM01', 45000, 'NCC02', N'Pha chế', '/assets/img/pepsi.png', 350, 10, 1, N'Đã xác nhận'),
        ('SP09', N'Latte Đá', 'DM01', 45000, 'NCC02', N'Pha chế', '/assets/img/pepsi.png', 500, 10, 1, N'Đã xác nhận'),
        ('SP10', N'Espresso', 'DM01', 25000, 'NCC02', N'Pha chế', '/assets/img/pepsi.png', 50, 10, 1, N'Đã xác nhận'),
        ('SP11', N'Mocha', 'DM01', 50000, 'NCC02', N'Pha chế', '/assets/img/pepsi.png', 500, 10, 1, N'Đã xác nhận'),

        -- DM02: Trà Sữa
        ('SP04', N'Trà Sữa Truyền Thống', 'DM02', 35000, 'NCC08', N'Pha chế', '/assets/img/pepsi.png', 500, 10, 1, N'Đã xác nhận'),
        ('SP12', N'Trà Sữa Thái Xanh', 'DM02', 35000, 'NCC08', N'Pha chế', '/assets/img/pepsi.png', 500, 10, 1, N'Đã xác nhận'),
        ('SP13', N'Trà Sữa Thái Đỏ', 'DM02', 35000, 'NCC08', N'Pha chế', '/assets/img/pepsi.png', 500, 10, 1, N'Đã xác nhận'),
        ('SP14', N'Trà Sữa Oolong', 'DM02', 40000, 'NCC08', N'Pha chế', '/assets/img/pepsi.png', 500, 10, 1, N'Đã xác nhận'),
        ('SP15', N'Trà Sữa Khoai Môn', 'DM02', 42000, 'NCC08', N'Pha chế', '/assets/img/pepsi.png', 500, 10, 1, N'Đã xác nhận'),
        ('SP16', N'Trà Sữa Matcha', 'DM02', 45000, 'NCC08', N'Pha chế', '/assets/img/pepsi.png', 500, 10, 1, N'Đã xác nhận'),
        ('SP17', N'Trà Sữa Socola', 'DM02', 45000, 'NCC08', N'Pha chế', '/assets/img/pepsi.png', 500, 10, 1, N'Đã xác nhận'),
        ('SP18', N'Sữa Tươi Trân Châu Đường Đen', 'DM02', 45000, 'NCC03', N'Pha chế', '/assets/img/pepsi.png', 500, 10, 1, N'Đã xác nhận'),
        ('SP19', N'Hồng Trà Macchiato', 'DM02', 38000, 'NCC08', N'Pha chế', '/assets/img/pepsi.png', 500, 10, 1, N'Đã xác nhận'),

        -- DM07: Trà Trái Cây
        ('SP20', N'Trà Đào Cam Sả', 'DM07', 45000, 'NCC05', N'Pha chế', '/assets/img/pepsi.png', 700, 10, 1, N'Đã xác nhận'),
        ('SP21', N'Trà Vải Hoa Hồng', 'DM07', 45000, 'NCC05', N'Pha chế', '/assets/img/pepsi.png', 700, 10, 1, N'Đã xác nhận'),
        ('SP22', N'Trà Ổi Hồng', 'DM07', 40000, 'NCC05', N'Pha chế', '/assets/img/pepsi.png', 700, 10, 1, N'Đã xác nhận'),
        ('SP23', N'Trà Tắc Xí Muội', 'DM07', 30000, 'NCC05', N'Pha chế', '/assets/img/pepsi.png', 700, 10, 1, N'Đã xác nhận'),
        ('SP24', N'Trà Dâu Tây', 'DM07', 48000, 'NCC05', N'Pha chế', '/assets/img/pepsi.png', 700, 10, 1, N'Đã xác nhận'),
        ('SP25', N'Lục Trà Chanh Mật Ong', 'DM07', 35000, 'NCC05', N'Pha chế', '/assets/img/pepsi.png', 700, 10, 1, N'Đã xác nhận'),

        -- DM05: Sinh Tố & Nước Ép
        ('SP32', N'Sinh Tố Bơ', 'DM05', 50000, 'NCC05', N'Pha chế', '/assets/img/pepsi.png', 500, 5, 1, N'Đã xác nhận'),
        ('SP33', N'Sinh Tố Xoài', 'DM05', 45000, 'NCC05', N'Pha chế', '/assets/img/pepsi.png', 500, 5, 1, N'Đã xác nhận'),
        ('SP34', N'Sinh Tố Dâu', 'DM05', 50000, 'NCC05', N'Pha chế', '/assets/img/pepsi.png', 500, 5, 1, N'Đã xác nhận'),
        ('SP35', N'Nước Ép Cam', 'DM05', 40000, 'NCC05', N'Pha chế', '/assets/img/pepsi.png', 350, 10, 1, N'Đã xác nhận'),
        ('SP36', N'Nước Ép Táo', 'DM05', 45000, 'NCC05', N'Pha chế', '/assets/img/pepsi.png', 350, 10, 1, N'Đã xác nhận'),
        ('SP37', N'Nước Ép Dưa Hấu', 'DM05', 35000, 'NCC05', N'Pha chế', '/assets/img/pepsi.png', 350, 10, 1, N'Đã xác nhận'),
        ('SP38', N'Nước Ép Cà Rốt', 'DM05', 35000, 'NCC05', N'Pha chế', '/assets/img/pepsi.png', 350, 10, 1, N'Đã xác nhận'),

        -- DM06: Đá Xay
        ('SP39', N'Matcha Đá Xay', 'DM06', 55000, 'NCC08', N'Pha chế', '/assets/img/pepsi.png', 500, 5, 1, N'Đã xác nhận'),
        ('SP40', N'Cookie Đá Xay', 'DM06', 55000, 'NCC08', N'Pha chế', '/assets/img/pepsi.png', 500, 5, 1, N'Đã xác nhận'),
        ('SP41', N'Cà Phê Đá Xay', 'DM06', 55000, 'NCC02', N'Pha chế', '/assets/img/pepsi.png', 500, 5, 1, N'Đã xác nhận'),

        -- DM04: Đồ Ăn Nhẹ & DM09: Bánh Ngọt
        ('SP42', N'Bánh Mì Que', 'DM04', 15000, 'NCC10', N'Có sẵn', '/assets/img/pepsi.png', 100, 10, 1, N'Đã xác nhận'),
        ('SP43', N'Khô Gà Lá Chanh', 'DM04', 50000, 'NCC06', N'Có sẵn', '/assets/img/pepsi.png', 200, 10, 1, N'Đã xác nhận'),
        ('SP44', N'Hạt Hướng Dương', 'DM04', 25000, 'NCC06', N'Có sẵn', '/assets/img/pepsi.png', 150, 20, 1, N'Đã xác nhận'),
        ('SP45', N'Tiramisu', 'DM09', 45000, 'NCC11', N'Có sẵn', '/assets/img/pepsi.png', 150, 5, 1, N'Đã xác nhận'),
        ('SP46', N'Mousse Chanh Dây', 'DM09', 45000, 'NCC11', N'Có sẵn', '/assets/img/pepsi.png', 150, 5, 1, N'Đã xác nhận'),
        ('SP47', N'Bánh Croissant', 'DM09', 30000, 'NCC11', N'Có sẵn', '/assets/img/pepsi.png', 100, 5, 1, N'Đã xác nhận'),
        ('SP48', N'Bánh Flan', 'DM09', 20000, 'NCC11', N'Có sẵn', '/assets/img/pepsi.png', 100, 10, 1, N'Đã xác nhận'),

        -- DM08: Sữa Chua
        ('SP49', N'Sữa Chua Trân Châu', 'DM08', 35000, 'NCC03', N'Pha chế', '/assets/img/pepsi.png', 300, 10, 1, N'Đã xác nhận'),
        ('SP50', N'Sữa Chua Dẻo', 'DM08', 30000, 'NCC03', N'Có sẵn', '/assets/img/pepsi.png', 100, 10, 1, N'Đã xác nhận')
END;

/* =============================================
   8. BẢNG SIZE (Sizes)
   ============================================= */
IF NOT EXISTS (SELECT *
FROM sys.tables
WHERE name = 'Size')
BEGIN
    CREATE TABLE Size
    (
        MaSize VARCHAR(50) NOT NULL PRIMARY KEY,
        MaSP VARCHAR(50),
        TenSize NVARCHAR(50),
        PhanTramGia INT,
        PhanTramNL INT,
        TrangThai BIT
    )
    INSERT INTO Size
        (MaSize, MaSP, TenSize, PhanTramGia, PhanTramNL, TrangThai)
    VALUES
        ('SZ01', 'SP02', N'Nhỏ', 0, 100, 1),
        ('SZ02', 'SP02', N'Vừa', 10, 120, 1),
        ('SZ03', 'SP02', N'Lớn', 20, 150, 1),
        ('SZ04', 'SP04', N'Vừa', 0, 100, 1),
        ('SZ05', 'SP04', N'Lớn', 15, 130, 1),
        ('SZ06', 'SP03', N'Nhỏ', 0, 100, 1),
        ('SZ07', 'SP03', N'Lớn', 15, 130, 1),
        ('SZ08', 'SP12', N'M', 0, 100, 1),
        ('SZ09', 'SP12', N'L', 15, 140, 1),
        ('SZ10', 'SP13', N'M', 0, 100, 1),
        ('SZ11', 'SP13', N'L', 15, 140, 1),
        ('SZ12', 'SP20', N'Khổng Lồ', 30, 180, 1),
        ('SZ13', 'SP18', N'Vừa', 0, 100, 1),
        ('SZ14', 'SP18', N'Lớn', 20, 130, 1),
        ('SZ15', 'SP32', N'Ly Tiêu Chuẩn', 0, 100, 1)
END;

/* =============================================
   9. BẢNG NGUYÊN LIỆU (Ingredients)
   ============================================= */
IF NOT EXISTS (SELECT *
FROM sys.tables
WHERE name = 'NguyenLieu')
BEGIN
    CREATE TABLE NguyenLieu
    (
        MaNL VARCHAR(50) NOT NULL PRIMARY KEY,
        TenNL NVARCHAR(255),
        MaNCC VARCHAR(50),
        TenNhaCC NVARCHAR(255),
        Gia DECIMAL(18, 2),
        DonVi NVARCHAR(50),
        MucCanhBao INT,
        TrangThai BIT
    )
    INSERT INTO NguyenLieu
        (MaNL, TenNL, MaNCC, TenNhaCC, Gia, DonVi, MucCanhBao, TrangThai)
    VALUES
        ('NL01', N'Hạt Cà Phê Robusta', 'NCC02', N'Trung Nguyên Legend', 200000, 'kg', 5, 1),
        ('NL02', N'Sữa Đặc Ngôi Sao', 'NCC03', N'Vinamilk', 25000, 'hop', 10, 1),
        ('NL03', N'Đường Cát Trắng', 'NCC01', N'Công ty Suntory PepsiCo', 15000, 'kg', 5, 1),
        ('NL04', N'Sữa Tươi Không Đường', 'NCC03', N'Vinamilk', 30000, 'lit', 10, 1),
        ('NL05', N'Bột Matcha Đài Loan', 'NCC08', N'Nguyên Liệu Pha Chế Việt', 500000, 'kg', 2, 1),
        ('NL06', N'Trân Châu Đen', 'NCC08', N'Nguyên Liệu Pha Chế Việt', 40000, 'kg', 5, 1),
        ('NL07', N'Syrup Đào', 'NCC06', N'Thực Phẩm Ân Nam', 150000, 'chai', 3, 1),
        ('NL08', N'Trà Đen Túi Lọc', 'NCC05', N'Nông Trại Cầu Đất Farm', 80000, 'goi', 10, 1),
        ('NL09', N'Kem Béo Thực Vật', 'NCC11', N'Công ty TNHH Richs', 60000, 'hop', 5, 1),
        ('NL10', N'Bột Cacao', 'NCC09', N'Nestlé Việt Nam', 120000, 'kg', 3, 1),
        ('NL11', N'Ly Nhựa 500ml', 'NCC07', N'Bao Bì Tân Phú', 500, 'cai', 100, 1),
        ('NL12', N'Ống Hút', 'NCC07', N'Bao Bì Tân Phú', 200, 'cai', 200, 1),
        ('NL13', N'Đá Viên Tinh Khiết', 'NCC01', N'Công ty Suntory PepsiCo', 20000, 'bao', 2, 1),
        ('NL14', N'Xoài Tươi', 'NCC05', N'Nông Trại Cầu Đất Farm', 40000, 'kg', 5, 1)
END;

/* =============================================
   10. BẢNG CÔNG THỨC (Recipes)
   ============================================= */
IF NOT EXISTS (SELECT *
FROM sys.tables
WHERE name = 'CongThuc')
BEGIN
    CREATE TABLE CongThuc
    (
        MaCT VARCHAR(50) NOT NULL PRIMARY KEY,
        MaSP VARCHAR(50),
        TrangThai BIT
    )
    INSERT INTO CongThuc
        (MaCT, MaSP, TrangThai)
    VALUES
        ('CT01', 'SP03', 1),
        -- Cà phê sữa
        ('CT02', 'SP02', 1),
        -- Cà phê đen
        ('CT03', 'SP04', 1),
        -- Trà sữa
        ('CT04', 'SP16', 1),
        -- Matcha
        ('CT05', 'SP32', 1),
        -- Sinh tố bơ
        ('CT06', 'SP20', 1),
        -- Trà đào
        ('CT07', 'SP18', 1),
        -- Sữa tươi TCĐĐ
        ('CT08', 'SP05', 1),
        -- Bạc xỉu
        ('CT09', 'SP39', 1),
        -- Matcha đá xay
        ('CT10', 'SP09', 1)
-- Latte
END;

/* =============================================
   11. BẢNG CHI TIẾT CÔNG THỨC (Recipe Details)
   ============================================= */
IF NOT EXISTS (SELECT *
FROM sys.tables
WHERE name = 'ChiTietCongThuc')
BEGIN
    CREATE TABLE ChiTietCongThuc
    (
        MaCTCT VARCHAR(50) NOT NULL,
        MaCT VARCHAR(50) NOT NULL,
        MaNL VARCHAR(50) NOT NULL,
        SoLuong DECIMAL(18, 4),
        PRIMARY KEY (MaCT, MaNL)
    )
    INSERT INTO ChiTietCongThuc
        (MaCTCT,MaCT, MaNL, SoLuong)
    VALUES
        ('CTCT01', 'CT01', 'NL01', 0.02),
        ('CTCT02', 'CT01', 'NL02', 0.03),
        ('CTCT03', 'CT02', 'NL01', 0.025),
        ('CTCT04', 'CT02', 'NL03', 0.01),
        ('CTCT05', 'CT03', 'NL08', 0.01),
        ('CTCT06', 'CT03', 'NL09', 0.03),
        ('CTCT07', 'CT03', 'NL06', 0.05),
        ('CTCT08', 'CT04', 'NL05', 0.01),
        ('CTCT09', 'CT04', 'NL04', 0.1),
        ('CTCT10', 'CT06', 'NL08', 0.01),
        ('CTCT11', 'CT06', 'NL07', 0.03),
        ('CTCT12', 'CT05', 'NL02', 0.02),
        ('CTCT13', 'CT01', 'NL13', 0.2),
        -- Đá bi
        ('CTCT14', 'CT01', 'NL11', 1)
-- Ly
END;

/* =============================================
   12. BẢNG NHÂN VIÊN (Employees)
   ============================================= */
IF NOT EXISTS (SELECT *
FROM sys.tables
WHERE name = 'NhanVien')
BEGIN
    CREATE TABLE NhanVien
    (
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
    INSERT INTO NhanVien
        (MaNV, TenNV, GioiTinh, NgaySinh, SDT, DiaChi, ChucVu, TaiKhoan, TrangThai)
    VALUES
        ('NV01', N'Nguyễn Văn Quản Lý', N'Nam', '1990-01-01', '0909123456', N'TP.HCM',     N'Cửa hàng trưởng', 'admin', 1),
    ('NV02', N'Trần Thị Thu Ngân', N'Nữ',  '2000-05-15', '0909123457', N'Bình Dương', N'Thu ngân',       'nhanvien1', 1),
    ('NV03', N'Lê Văn Kho',       N'Nam', '1995-08-20', '0909123458', N'Đồng Nai',   N'Thủ kho',       'nhanvienkho', 1),
    ('NV04', N'Phạm Văn Pha Chế', N'Nam', '1998-02-10', '0909222333', N'TP.HCM',     N'Pha chế',       'phache01', 1),
    ('NV05', N'Lý Thị Pha Chế 2', N'Nữ',  '1999-11-20', '0909333444', N'TP.HCM',     N'Pha chế',       'phache02', 1),
    ('NV06', N'Ngô Văn Bảo Vệ',   N'Nam', '1985-06-30', '0909555666', N'Long An',    N'Bảo vệ',        'baove01', 1),
    ('NV07', N'Đỗ Thị Kế Toán',   N'Nữ',  '1992-09-09', '0909777888', N'TP.HCM',     N'Kế toán',       'ketoan01', 1),
    ('NV08', N'Hoàng Văn Giám Sát',N'Nam', '1991-03-03', '0909888999', N'TP.HCM',     N'Giám sát',      'giamsat01', 1),
    ('NV09', N'Vũ Thị Marketing', N'Nữ',  '1997-07-07', '0909000111', N'TP.HCM',     N'Marketing',     'marketing01', 1),
    ('NV10', N'Bùi Văn Sale 2',   N'Nam', '2001-12-12', '0909111222', N'Bình Dương', N'Thu ngân',       'sale02', 1),
    ('NV11', N'Trịnh Thị Sale 3', N'Nữ',  '2002-01-20', '0909333555', N'TP.HCM',     N'Thu ngân',       'sale03', 1),
    ('NV12', N'Phan Văn Kho 2',   N'Nam', '1996-04-25', '0909666777', N'Đồng Nai',   N'Thủ kho',       'kho02', 1)
END;

/* =============================================
   13. BẢNG HẠNG THÀNH VIÊN (Member Tiers)
   ============================================= */
IF NOT EXISTS (SELECT *
FROM sys.tables
WHERE name = 'HangThanhVien')
BEGIN
    CREATE TABLE HangThanhVien
    (
        MaHang VARCHAR(50) NOT NULL PRIMARY KEY,
        TenHang NVARCHAR(100),
        PhanTramGiam INT,
        DieuKien DECIMAL(18, 2),
        TrangThai BIT
    )
    INSERT INTO HangThanhVien
        (MaHang, TenHang, PhanTramGiam, DieuKien, TrangThai)
    VALUES
        ('HTV01', N'Thành Viên Mới', 0, 0, 1),
        ('HTV02', N'Thành Viên Bạc', 5, 1000000, 1),
        ('HTV03', N'Thành Viên Vàng', 10, 5000000, 1),
        ('HTV04', N'Thành Viên Bạch Kim', 15, 10000000, 1),
        ('HTV05', N'Thành Viên Kim Cương', 20, 20000000, 1)
END;

/* =============================================
   14. BẢNG KHÁCH HÀNG (Customers)
   ============================================= */
IF NOT EXISTS (SELECT *
FROM sys.tables
WHERE name = 'KhachHang')
BEGIN
    CREATE TABLE KhachHang
    (
        MaKH VARCHAR(50) NOT NULL PRIMARY KEY,
        TenKH NVARCHAR(100),
        GioiTinh NVARCHAR(10),
        SDT VARCHAR(20),
        TenDaMua DECIMAL(18, 2),
        MaHang VARCHAR(50),
        TrangThai BIT
    )
    INSERT INTO KhachHang
        (MaKH, TenKH, GioiTinh, SDT, TenDaMua, MaHang, TrangThai)
    VALUES
        ('KH001', N'Nguyễn Văn Khách', N'Nam', '0912345678', 1200000, 'HTV02', 1),
        ('KH002', N'Trần Thị Mua', N'Nữ', '0987654321', 50000, 'HTV01', 1),
        ('KH003', N'Lê Văn Giàu', N'Nam', '0911222333', 6000000, 'HTV03', 1),
        ('KH004', N'Phạm Thị Đẹp', N'Nữ', '0922333444', 15000000, 'HTV04', 1),
        ('KH005', N'Hoàng Văn Vip', N'Nam', '0933444555', 25000000, 'HTV05', 1),
        ('KH006', N'Trương Thị Vãng Lai', N'Nữ', '0944555666', 200000, 'HTV01', 1),
        ('KH007', N'Võ Văn Sinh Viên', N'Nam', '0955666777', 800000, 'HTV01', 1),
        ('KH008', N'Đặng Thị Công Sở', N'Nữ', '0966777888', 3500000, 'HTV02', 1),
        ('KH009', N'Bùi Văn Thường Xuyên', N'Nam', '0977888999', 4800000, 'HTV02', 1),
        ('KH010', N'Ngô Thị Trà Sữa', N'Nữ', '0988999000', 12000000, 'HTV04', 1)
END;

/* =============================================
   15. BẢNG KHUYẾN MÃI (Promotions)
   ============================================= */
IF NOT EXISTS (SELECT *
FROM sys.tables
WHERE name = 'KhuyenMai')
BEGIN
    CREATE TABLE KhuyenMai
    (
        MaKM VARCHAR(50) NOT NULL PRIMARY KEY,
        PhanTramGiam INT,
        TuNgay DATE,
        DenNgay DATE,
        TrangThai BIT
    )
    INSERT INTO KhuyenMai
        (MaKM, PhanTramGiam, TuNgay, DenNgay, TrangThai)
    VALUES
        ('KM01', 10, '2023-01-01', '2023-12-31', 0),
        ('KM02', 20, '2024-01-01', '2025-12-31', 1),
        ('KM03', 5, '2024-01-01', '2025-06-30', 1),
        ('KM04', 50, '2024-09-02', '2024-09-02', 1),
        -- Lễ 2/9
        ('KM05', 15, '2024-02-14', '2024-02-14', 0),
        -- Valentine cũ
        ('KM06', 30, '2025-01-01', '2025-01-03', 1),
        -- Năm mới
        ('KM07', 10, '2024-01-01', '2030-12-31', 1)
-- Khuyến mãi dài hạn
END;

/* =============================================
   16. BẢNG HÓA ĐƠN (Invoices)
   ============================================= */
IF NOT EXISTS (SELECT *
FROM sys.tables
WHERE name = 'HoaDon')
BEGIN
    CREATE TABLE HoaDon
    (
        MaHD VARCHAR(50) NOT NULL PRIMARY KEY,
        MaNV VARCHAR(50),
        MaKH VARCHAR(50),
        MaKM VARCHAR(50),
        NgayBan DATE,
        TongTien DECIMAL(18, 2),
        TienKhuyenMai DECIMAL(18, 2),
        TrangThai BIT
    )
    INSERT INTO HoaDon
        (MaHD, MaNV, MaKH, MaKM, NgayBan, TongTien, TienKhuyenMai, TrangThai)
    VALUES
        ('HD001', 'NV02', 'KH001', 'KM02', '2024-01-15', 55000, 11000, 1),
        ('HD002', 'NV02', 'KH002', NULL, '2024-01-16', 15000, 0, 1),
        ('HD003', 'NV02', 'KH003', 'KM07', '2024-01-17', 100000, 10000, 1),
        ('HD004', 'NV10', 'KH004', 'KM02', '2024-01-17', 200000, 40000, 1),
        ('HD005', 'NV11', 'KH005', NULL, '2024-01-18', 45000, 0, 1),
        ('HD006', 'NV02', 'KH001', 'KM03', '2024-01-19', 30000, 1500, 1),
        ('HD007', 'NV10', 'KH006', NULL, '2024-01-20', 80000, 0, 1),
        ('HD008', 'NV11', 'KH007', 'KM02', '2024-01-21', 150000, 30000, 1),
        ('HD009', 'NV02', 'KH008', NULL, '2024-01-22', 25000, 0, 1),
        ('HD010', 'NV10', 'KH009', 'KM07', '2024-01-22', 90000, 9000, 1)
END;

/* =============================================
   17. BẢNG CHI TIẾT HÓA ĐƠN (Invoice Details)
   ============================================= */
IF NOT EXISTS (SELECT *
FROM sys.tables
WHERE name = 'ChiTietHoaDon')
BEGIN
    CREATE TABLE ChiTietHoaDon
    (
        MaHD VARCHAR(50) NOT NULL,
        MaSP VARCHAR(50) NOT NULL,
        MaSize VARCHAR(50) NOT NULL,
        SoLuong INT,
        Gia DECIMAL(18, 2),
        PRIMARY KEY (MaHD, MaSP, MaSize)
    )
    INSERT INTO ChiTietHoaDon
        (MaHD, MaSP, MaSize, SoLuong, Gia)
    VALUES
        ('HD001', 'SP03', 'SZ02', 1, 30000),
        ('HD001', 'SP01', 'SZ01', 1, 15000),
        ('HD003', 'SP18', 'SZ13', 2, 90000),
        -- 45k * 2
        ('HD003', 'SP42', 'SZ01', 1, 15000),
        -- Bánh mì que (Giả sử map size ảo)
        ('HD004', 'SP20', 'SZ12', 4, 180000),
        ('HD005', 'SP20', 'SZ12', 1, 45000),
        ('HD006', 'SP04', 'SZ04', 1, 35000),
        ('HD007', 'SP39', 'SZ01', 1, 55000),
        ('HD007', 'SP45', 'SZ01', 1, 25000)
END;

/* =============================================
   18. BẢNG PHIẾU NHẬP SẢN PHẨM (Product Import)
   ============================================= */
IF NOT EXISTS (SELECT *
FROM sys.tables
WHERE name = 'PhieuNhapSanPham')
BEGIN
    CREATE TABLE PhieuNhapSanPham
    (
        MaLoSP VARCHAR(50) NOT NULL PRIMARY KEY,
        NgayNhap DATE,
        MaNV VARCHAR(50),
        TongTien DECIMAL(18, 2),
        MaNCC VARCHAR(50),
        TrangThaiXuLy NVARCHAR(50),
        TrangThai BIT
    )
    INSERT INTO PhieuNhapSanPham
        (MaLoSP, NgayNhap, MaNV, TongTien, MaNCC, TrangThaiXuLy, TrangThai)
    VALUES
        ('PNSP01', '2024-01-10', 'NV03', 8000000, 'NCC01', N'Đã xác nhận', 1),
        ('PNSP02', '2024-01-11', 'NV03', 5000000, 'NCC04', N'Đã xác nhận', 1),
        ('PNSP03', '2024-01-15', 'NV12', 2000000, 'NCC10', N'Đã xác nhận', 1),
        ('PNSP04', '2024-02-01', 'NV03', 10000000, 'NCC01', N'Đã xác nhận', 1),
        ('PNSP05', '2024-02-05', 'NV12', 1500000, 'NCC11', N'Đã xác nhận', 1)
END;

/* =============================================
   19. BẢNG LÔ SẢN PHẨM (Product Batches)
   ============================================= */
IF NOT EXISTS (SELECT *
FROM sys.tables
WHERE name = 'LoSanPham')
BEGIN
    CREATE TABLE LoSanPham
    (
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
    INSERT INTO LoSanPham
        (MaLoSP, MaPN, MaSP, SoLuong, NgayNhap, NgaySanXuat, HanSuDung, TongTien, TrangThai)
    VALUES
        ('LOSP01', 'PNSP01', 'SP01', 1000, '2024-01-10', '2024-01-01', '2025-01-01', 8000000, 1),
        ('LOSP02', 'PNSP02', 'SP26', 500, '2024-01-11', '2024-01-01', '2024-12-31', 4000000, 1),
        ('LOSP03', 'PNSP03', 'SP42', 250, '2024-01-15', '2024-01-14', '2024-01-20', 2000000, 1),
        -- Bánh mì que date ngắn
        ('LOSP04', 'PNSP04', 'SP27', 1000, '2024-02-01', '2024-01-15', '2025-01-15', 8000000, 1),
        ('LOSP05', 'PNSP05', 'SP45', 100, '2024-02-05', '2024-02-04', '2024-02-10', 1500000, 1)
END;

/* =============================================
   20. BẢNG PHIẾU NHẬP NGUYÊN LIỆU (Ingredient Import)
   ============================================= */
IF NOT EXISTS (SELECT *
FROM sys.tables
WHERE name = 'PhieuNhapNguyenLieu')
BEGIN
    CREATE TABLE PhieuNhapNguyenLieu
    (
        MaLoNL VARCHAR(50) NOT NULL PRIMARY KEY,
        NgayNhap DATE,
        MaNV VARCHAR(50),
        TongTien DECIMAL(18, 2),
        MaNCC VARCHAR(50),
        TrangThaiXuLy NVARCHAR(50),
        TrangThai BIT
    )
    INSERT INTO PhieuNhapNguyenLieu
        (MaLoNL, NgayNhap, MaNV, TongTien, MaNCC, TrangThaiXuLy, TrangThai)
    VALUES
        ('PNNL01', '2024-01-12', 'NV03', 5000000, 'NCC02', N'Đã xác nhận', 1),
        ('PNNL02', '2024-01-13', 'NV12', 3000000, 'NCC03', N'Đã xác nhận', 1),
        ('PNNL03', '2024-01-20', 'NV03', 10000000, 'NCC08', N'Đã xác nhận', 1),
        ('PNNL04', '2024-02-01', 'NV12', 2000000, 'NCC05', N'Đã xác nhận', 1)
END;

/* =============================================
   21. BẢNG LÔ NGUYÊN LIỆU (Ingredient Batches)
   ============================================= */
IF NOT EXISTS (SELECT *
FROM sys.tables
WHERE name = 'LoNguyenLieu')
BEGIN
    CREATE TABLE LoNguyenLieu
    (
        MaLoNL VARCHAR(50) NOT NULL PRIMARY KEY,
        MaPN VARCHAR(50),
        MaNL VARCHAR(50),
        SoLuong INT,
        NgayNhap DATE,
        NgaySanXuat DATE,
        HanSuDung DATE,
        TrangThai BIT
    )
    INSERT INTO LoNguyenLieu
        (MaLoNL, MaPN, MaNL, SoLuong, NgayNhap, NgaySanXuat, HanSuDung, TrangThai)
    VALUES
        ('LONL01', 'PNNL01', 'NL01', 25, '2024-01-12', '2024-01-01', '2024-06-01', 1),
        -- Cà phê
        ('LONL02', 'PNNL02', 'NL02', 120, '2024-01-13', '2024-01-01', '2024-07-01', 1),
        -- Sữa đặc
        ('LONL03', 'PNNL03', 'NL05', 20, '2024-01-20', '2023-12-01', '2024-12-01', 1),
        -- Matcha
        ('LONL04', 'PNNL03', 'NL06', 250, '2024-01-20', '2024-01-15', '2024-03-15', 1),
        -- Trân châu
        ('LONL05', 'PNNL04', 'NL07', 15, '2024-02-01', '2024-01-01', '2025-01-01', 1)
-- Syrup
END;

/* =============================================
   22. BẢNG PHIẾU HỦY SẢN PHẨM (Product Disposal)
   ============================================= */
IF NOT EXISTS (SELECT *
FROM sys.tables
WHERE name = 'PhieuHuySanPham')
BEGIN
    CREATE TABLE PhieuHuySanPham
    (
        MaPH VARCHAR(50) NOT NULL,
        MaLo VARCHAR(50) NOT NULL,
        NgayHuy DATE,
        MaNV VARCHAR(50),
        LyDo NVARCHAR(MAX),
        TongGiaTri DECIMAL(18, 2),
        TrangThaiXuLy NVARCHAR(50),
        TrangThai BIT,
        PRIMARY KEY (MaPH, MaLo)
    )
    INSERT INTO PhieuHuySanPham
        (MaPH, MaLo, NgayHuy, MaNV, LyDo, TongGiaTri, TrangThaiXuLy, TrangThai)
    VALUES
        ('PHSP01', 'LOSP01', '2024-02-01', 'NV01', N'Lon bị móp méo do vận chuyển', 80000, N'Chờ duyệt', 1),
        ('PHSP02', 'LOSP03', '2024-01-21', 'NV01', N'Hết hạn sử dụng', 50000, N'Đã duyệt', 1)
END;

/* =============================================
   23. BẢNG PHIẾU HỦY NGUYÊN LIỆU (Ingredient Disposal)
   ============================================= */
IF NOT EXISTS (SELECT *
FROM sys.tables
WHERE name = 'PhieuHuyNguyenLieu')
BEGIN
    CREATE TABLE PhieuHuyNguyenLieu
    (
        MaPH VARCHAR(50) NOT NULL,
        MaLo VARCHAR(50) NOT NULL,
        NgayHuy DATE,
        MaNV VARCHAR(50),
        LyDo NVARCHAR(MAX),
        TongTien DECIMAL(18, 2),
        TrangThaiXuLy NVARCHAR(50),
        TrangThai BIT,
        PRIMARY KEY (MaPH, MaLo)
    )
    INSERT INTO PhieuHuyNguyenLieu
        (MaPH, MaLo, NgayHuy, MaNV, LyDo, TongTien, TrangThaiXuLy, TrangThai)
    VALUES
        ('PHNL01', 'LONL04', '2024-03-16', 'NV01', N'Trân châu bị hỏng do ẩm mốc', 200000, N'Đã duyệt', 1)
END;

/* =============================================
   KHOÁ NGOẠI (Foreign Keys) - Giữ nguyên không đổi
   ============================================= */
IF NOT EXISTS (SELECT *
FROM sys.foreign_keys
WHERE name = 'FK_TaiKhoan_NhomQuyen')
    ALTER TABLE TaiKhoan ADD CONSTRAINT FK_TaiKhoan_NhomQuyen FOREIGN KEY (maNQ) REFERENCES NhomQuyen(MaNQ);


IF NOT EXISTS (SELECT *
FROM sys.foreign_keys
WHERE name = 'FK_NhanVien_TaiKhoan')
    ALTER TABLE NhanVien ADD CONSTRAINT FK_NhanVien_TaiKhoan FOREIGN KEY (TaiKhoan) REFERENCES TaiKhoan(TenDangNhap);

IF NOT EXISTS (SELECT *
FROM sys.foreign_keys
WHERE name = 'FK_SanPham_DanhMuc')
    ALTER TABLE SanPham ADD CONSTRAINT FK_SanPham_DanhMuc FOREIGN KEY (MaDM) REFERENCES DanhMuc(MaDM);

IF NOT EXISTS (SELECT *
FROM sys.foreign_keys
WHERE name = 'FK_SanPham_NhaCungCap')
    ALTER TABLE SanPham ADD CONSTRAINT FK_SanPham_NhaCungCap FOREIGN KEY (MaNCC) REFERENCES NhaCungCap(MaNCC);

IF NOT EXISTS (SELECT *
FROM sys.foreign_keys
WHERE name = 'FK_Size_SanPham')
    ALTER TABLE Size ADD CONSTRAINT FK_Size_SanPham FOREIGN KEY (MaSP) REFERENCES SanPham(MaSP);

IF NOT EXISTS (SELECT *
FROM sys.foreign_keys
WHERE name = 'FK_NguyenLieu_NhaCungCap')
    ALTER TABLE NguyenLieu ADD CONSTRAINT FK_NguyenLieu_NhaCungCap FOREIGN KEY (MaNCC) REFERENCES NhaCungCap(MaNCC);

IF NOT EXISTS (SELECT *
FROM sys.foreign_keys
WHERE name = 'FK_CongThuc_SanPham')
    ALTER TABLE CongThuc ADD CONSTRAINT FK_CongThuc_SanPham FOREIGN KEY (MaSP) REFERENCES SanPham(MaSP);

IF NOT EXISTS (SELECT *
FROM sys.foreign_keys
WHERE name = 'FK_ChiTietCongThuc_CongThuc')
    ALTER TABLE ChiTietCongThuc ADD CONSTRAINT FK_ChiTietCongThuc_CongThuc FOREIGN KEY (MaCT) REFERENCES CongThuc(MaCT);

IF NOT EXISTS (SELECT *
FROM sys.foreign_keys
WHERE name = 'FK_ChiTietCongThuc_NguyenLieu')
    ALTER TABLE ChiTietCongThuc ADD CONSTRAINT FK_ChiTietCongThuc_NguyenLieu FOREIGN KEY (MaNL) REFERENCES NguyenLieu(MaNL);

IF NOT EXISTS (SELECT *
FROM sys.foreign_keys
WHERE name = 'FK_KhachHang_HangThanhVien')
    ALTER TABLE KhachHang ADD CONSTRAINT FK_KhachHang_HangThanhVien FOREIGN KEY (MaHang) REFERENCES HangThanhVien(MaHang);

IF NOT EXISTS (SELECT *
FROM sys.foreign_keys
WHERE name = 'FK_HoaDon_NhanVien')
    ALTER TABLE HoaDon ADD CONSTRAINT FK_HoaDon_NhanVien FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV);

IF NOT EXISTS (SELECT *
FROM sys.foreign_keys
WHERE name = 'FK_HoaDon_KhachHang')
    ALTER TABLE HoaDon ADD CONSTRAINT FK_HoaDon_KhachHang FOREIGN KEY (MaKH) REFERENCES KhachHang(MaKH);

IF NOT EXISTS (SELECT *
FROM sys.foreign_keys
WHERE name = 'FK_HoaDon_KhuyenMai')
    ALTER TABLE HoaDon ADD CONSTRAINT FK_HoaDon_KhuyenMai FOREIGN KEY (MaKM) REFERENCES KhuyenMai(MaKM);

IF NOT EXISTS (SELECT *
FROM sys.foreign_keys
WHERE name = 'FK_ChiTietHoaDon_HoaDon')
    ALTER TABLE ChiTietHoaDon ADD CONSTRAINT FK_ChiTietHoaDon_HoaDon FOREIGN KEY (MaHD) REFERENCES HoaDon(MaHD);

IF NOT EXISTS (SELECT *
FROM sys.foreign_keys
WHERE name = 'FK_ChiTietHoaDon_SanPham')
    ALTER TABLE ChiTietHoaDon ADD CONSTRAINT FK_ChiTietHoaDon_SanPham FOREIGN KEY (MaSP) REFERENCES SanPham(MaSP);

IF NOT EXISTS (SELECT *
FROM sys.foreign_keys
WHERE name = 'FK_ChiTietHoaDon_Size')
    ALTER TABLE ChiTietHoaDon ADD CONSTRAINT FK_ChiTietHoaDon_Size FOREIGN KEY (MaSize) REFERENCES Size(MaSize);

IF NOT EXISTS (SELECT *
FROM sys.foreign_keys
WHERE name = 'FK_PhieuNhapSP_NhanVien')
    ALTER TABLE PhieuNhapSanPham ADD CONSTRAINT FK_PhieuNhapSP_NhanVien FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV);

IF NOT EXISTS (SELECT *
FROM sys.foreign_keys
WHERE name = 'FK_PhieuNhapSP_NCC')
    ALTER TABLE PhieuNhapSanPham ADD CONSTRAINT FK_PhieuNhapSP_NCC FOREIGN KEY (MaNCC) REFERENCES NhaCungCap(MaNCC);

IF NOT EXISTS (SELECT *
FROM sys.foreign_keys
WHERE name = 'FK_PhieuNhapNL_NhanVien')
    ALTER TABLE PhieuNhapNguyenLieu ADD CONSTRAINT FK_PhieuNhapNL_NhanVien FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV);

IF NOT EXISTS (SELECT *
FROM sys.foreign_keys
WHERE name = 'FK_PhieuNhapNL_NCC')
    ALTER TABLE PhieuNhapNguyenLieu ADD CONSTRAINT FK_PhieuNhapNL_NCC FOREIGN KEY (MaNCC) REFERENCES NhaCungCap(MaNCC);

IF NOT EXISTS (SELECT *
FROM sys.foreign_keys
WHERE name = 'FK_LoSanPham_PhieuNhapSP')
    ALTER TABLE LoSanPham ADD CONSTRAINT FK_LoSanPham_PhieuNhapSP FOREIGN KEY (MaPN) REFERENCES PhieuNhapSanPham(MaLoSP);

IF NOT EXISTS (SELECT *
FROM sys.foreign_keys
WHERE name = 'FK_LoSanPham_SanPham')
    ALTER TABLE LoSanPham ADD CONSTRAINT FK_LoSanPham_SanPham FOREIGN KEY (MaSP) REFERENCES SanPham(MaSP);

IF NOT EXISTS (SELECT *
FROM sys.foreign_keys
WHERE name = 'FK_LoNguyenLieu_PhieuNhapNL')
    ALTER TABLE LoNguyenLieu ADD CONSTRAINT FK_LoNguyenLieu_PhieuNhapNL FOREIGN KEY (MaPN) REFERENCES PhieuNhapNguyenLieu(MaLoNL);

IF NOT EXISTS (SELECT *
FROM sys.foreign_keys
WHERE name = 'FK_LoNguyenLieu_NguyenLieu')
    ALTER TABLE LoNguyenLieu ADD CONSTRAINT FK_LoNguyenLieu_NguyenLieu FOREIGN KEY (MaNL) REFERENCES NguyenLieu(MaNL);

IF NOT EXISTS (SELECT *
FROM sys.foreign_keys
WHERE name = 'FK_PhieuHuySP_NhanVien')
    ALTER TABLE PhieuHuySanPham ADD CONSTRAINT FK_PhieuHuySP_NhanVien FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV);

IF NOT EXISTS (SELECT *
FROM sys.foreign_keys
WHERE name = 'FK_PhieuHuySP_LoSanPham')
    ALTER TABLE PhieuHuySanPham ADD CONSTRAINT FK_PhieuHuySP_LoSanPham FOREIGN KEY (MaLo) REFERENCES LoSanPham(MaLoSP);

IF NOT EXISTS (SELECT *
FROM sys.foreign_keys
WHERE name = 'FK_PhieuHuyNL_NhanVien')
    ALTER TABLE PhieuHuyNguyenLieu ADD CONSTRAINT FK_PhieuHuyNL_NhanVien FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV);

IF NOT EXISTS (SELECT *
FROM sys.foreign_keys
WHERE name = 'FK_PhieuHuyNL_LoNguyenLieu')
    ALTER TABLE PhieuHuyNguyenLieu ADD CONSTRAINT FK_PhieuHuyNL_LoNguyenLieu FOREIGN KEY (MaLo) REFERENCES LoNguyenLieu(MaLoNL);

IF NOT EXISTS (SELECT *
FROM sys.foreign_keys
WHERE name = 'FK_Quyen_NhomQuyen')
    ALTER TABLE Quyen ADD CONSTRAINT FK_Quyen_NhomQuyen
    FOREIGN KEY (MaNQ) REFERENCES NhomQuyen(MaNQ);