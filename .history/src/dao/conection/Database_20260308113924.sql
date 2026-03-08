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
        ('AD', N'Admin', 1),
        ('QL', N'Quản lý', 1),
        ('NVK', N'Nhân viên kho', 1),
        ('NVBH', N'Nhân viên bán hàng', 1)
END;
IF NOT EXISTS (SELECT *
FROM sys.tables
WHERE name = 'Quyen')
BEGIN
    CREATE TABLE Quyen
    (
        MaQuyen VARCHAR(50) NOT NULL PRIMARY KEY,
        TenQuyen NVARCHAR(100)
    )

    INSERT INTO Quyen
        (MaQuyen, TenQuyen)
    VALUES
        -- 1. Quản lý sản phẩm
        ('Q01', N'QLSP_TAO'),
        ('Q02', N'QLSP_XEM'),
        ('Q03', N'QLSP_SUA'),
        ('Q04', N'QLSP_XOA'),
        -- 2. Nguyên liệu
        ('Q05', N'NL_TAO'),
        ('Q06', N'NL_XEM'),
        ('Q07', N'NL_SUA'),
        ('Q08', N'NL_XOA'),
        -- 3. Nhà cung cấp
        ('Q09', N'NCC_TAO'),
        ('Q10', N'NCC_XEM'),
        ('Q11', N'NCC_SUA'),
        ('Q12', N'NCC_XOA'),
        -- 4. Nhập kho
        ('Q13', N'NK_TAO'),
        ('Q14', N'NK_XEM'),
        ('Q15', N'NK_SUA'),
        ('Q16', N'NK_XOA'),
        -- 5. Tồn kho
        ('Q17', N'TKHO_TAO'),
        ('Q18', N'TKHO_XEM'),
        ('Q19', N'TKHO_SUA'),
        ('Q20', N'TKHO_XOA'),
        -- 6. Xuất kho
        ('Q21', N'XK_TAO'),
        ('Q22', N'XK_XEM'),
        ('Q23', N'XK_SUA'),
        ('Q24', N'XK_XOA'),
        -- 7. Kiểm kê
        ('Q25', N'KK_TAO'),
        ('Q26', N'KK_XEM'),
        ('Q27', N'KK_SUA'),
        ('Q28', N'KK_XOA'),
        -- 8. Bán hàng
        ('Q29', N'BH_TAO'),
        ('Q30', N'BH_XEM'),
        ('Q31', N'BH_SUA'),
        ('Q32', N'BH_XOA'),
        -- 9. Hóa đơn
        ('Q33', N'HD_TAO'),
        ('Q34', N'HD_XEM'),
        ('Q35', N'HD_SUA'),
        ('Q36', N'HD_XOA'),
        -- 10. Khách hàng
        ('Q37', N'KH_TAO'),
        ('Q38', N'KH_XEM'),
        ('Q39', N'KH_SUA'),
        ('Q40', N'KH_XOA'),
        -- 11. Hạng thành viên
        ('Q41', N'HTV_TAO'),
        ('Q42', N'HTV_XEM'),
        ('Q43', N'HTV_SUA'),
        ('Q44', N'HTV_XOA'),
        -- 12. Nhân viên
        ('Q45', N'NV_TAO'),
        ('Q46', N'NV_XEM'),
        ('Q47', N'NV_SUA'),
        ('Q48', N'NV_XOA'),
        -- 13. Tài khoản
        ('Q49', N'TK_TAO'),
        ('Q50', N'TK_XEM'),
        ('Q51', N'TK_SUA'),
        ('Q52', N'TK_XOA'),
        -- 14. Phân quyền
        ('Q53', N'PQ_TAO'),
        ('Q54', N'PQ_XEM'),
        ('Q55', N'PQ_SUA'),
        ('Q56', N'PQ_XOA'),
        -- 15. Thống kê
        ('Q57', N'TKE_TAO'),
        ('Q58', N'TKE_XEM'),
        ('Q59', N'TKE_SUA'),
        ('Q60', N'TKE_XOA'),
        -- 16. Khuyến mãi
        ('Q61', N'KM_TAO'),
        ('Q62', N'KM_XEM'),
        ('Q63', N'KM_SUA'),
        ('Q64', N'KM_XOA')
END;

IF NOT EXISTS (SELECT *
FROM sys.tables
WHERE name = 'PhanQuyen')
BEGIN
    CREATE TABLE PhanQuyen
    (
        MaNQ VARCHAR(50) NOT NULL,
        MaQuyen VARCHAR(50) NOT NULL,
        TrangThai BIT DEFAULT 1,
    )

    INSERT INTO PhanQuyen
        (MaNQ, MaQuyen)
    VALUES
        -- A. ADMIN (Sở hữu toàn bộ quyền từ Q01 -> Q64)
        ('AD', 'Q01'),
        ('AD', 'Q02'),
        ('AD', 'Q03'),
        ('AD', 'Q04'),
        ('AD', 'Q05'),
        ('AD', 'Q06'),
        ('AD', 'Q07'),
        ('AD', 'Q08'),
        ('AD', 'Q09'),
        ('AD', 'Q10'),
        ('AD', 'Q11'),
        ('AD', 'Q12'),
        ('AD', 'Q13'),
        ('AD', 'Q14'),
        ('AD', 'Q15'),
        ('AD', 'Q16'),
        ('AD', 'Q17'),
        ('AD', 'Q18'),
        ('AD', 'Q19'),
        ('AD', 'Q20'),
        ('AD', 'Q21'),
        ('AD', 'Q22'),
        ('AD', 'Q23'),
        ('AD', 'Q24'),
        ('AD', 'Q25'),
        ('AD', 'Q26'),
        ('AD', 'Q27'),
        ('AD', 'Q28'),
        ('AD', 'Q29'),
        ('AD', 'Q30'),
        ('AD', 'Q31'),
        ('AD', 'Q32'),
        ('AD', 'Q33'),
        ('AD', 'Q34'),
        ('AD', 'Q35'),
        ('AD', 'Q36'),
        ('AD', 'Q37'),
        ('AD', 'Q38'),
        ('AD', 'Q39'),
        ('AD', 'Q40'),
        ('AD', 'Q41'),
        ('AD', 'Q42'),
        ('AD', 'Q43'),
        ('AD', 'Q44'),
        ('AD', 'Q45'),
        ('AD', 'Q46'),
        ('AD', 'Q47'),
        ('AD', 'Q48'),
        ('AD', 'Q49'),
        ('AD', 'Q50'),
        ('AD', 'Q51'),
        ('AD', 'Q52'),
        ('AD', 'Q53'),
        ('AD', 'Q54'),
        ('AD', 'Q55'),
        ('AD', 'Q56'),
        ('AD', 'Q57'),
        ('AD', 'Q58'),
        ('AD', 'Q59'),
        ('AD', 'Q60'),
        ('AD', 'Q61'),
        ('AD', 'Q62'),
        ('AD', 'Q63'),
        ('AD', 'Q64'),

        -- B. QUẢN LÝ (Tất cả quyền trừ Tài khoản Q49-Q52 và Phân quyền Q53-Q56)
        ('QL', 'Q01'),
        ('QL', 'Q02'),
        ('QL', 'Q03'),
        ('QL', 'Q04'),
        ('QL', 'Q05'),
        ('QL', 'Q06'),
        ('QL', 'Q07'),
        ('QL', 'Q08'),
        ('QL', 'Q09'),
        ('QL', 'Q10'),
        ('QL', 'Q11'),
        ('QL', 'Q12'),
        ('QL', 'Q13'),
        ('QL', 'Q14'),
        ('QL', 'Q15'),
        ('QL', 'Q16'),
        ('QL', 'Q17'),
        ('QL', 'Q18'),
        ('QL', 'Q19'),
        ('QL', 'Q20'),
        ('QL', 'Q21'),
        ('QL', 'Q22'),
        ('QL', 'Q23'),
        ('QL', 'Q24'),
        ('QL', 'Q25'),
        ('QL', 'Q26'),
        ('QL', 'Q27'),
        ('QL', 'Q28'),
        ('QL', 'Q29'),
        ('QL', 'Q30'),
        ('QL', 'Q31'),
        ('QL', 'Q32'),
        ('QL', 'Q33'),
        ('QL', 'Q34'),
        ('QL', 'Q35'),
        ('QL', 'Q36'),
        ('QL', 'Q37'),
        ('QL', 'Q38'),
        ('QL', 'Q39'),
        ('QL', 'Q40'),
        ('QL', 'Q41'),
        ('QL', 'Q42'),
        ('QL', 'Q43'),
        ('QL', 'Q44'),
        ('QL', 'Q45'),
        ('QL', 'Q46'),
        ('QL', 'Q47'),
        ('QL', 'Q48'),
        ('QL', 'Q57'),
        ('QL', 'Q58'),
        ('QL', 'Q59'),
        ('QL', 'Q60'),
        ('QL', 'Q61'),
        ('QL', 'Q62'),
        ('QL', 'Q63'),
        ('QL', 'Q64'),

        -- C. NHÂN VIÊN KHO (Sản phẩm, Nguyên liệu, NCC, Nhập/Xuất/Tồn/Kiểm kê)
        ('NVK', 'Q02'),
        ('NVK', 'Q05'),
        ('NVK', 'Q06'),
        ('NVK', 'Q07'),
        ('NVK', 'Q08'),
        ('NVK', 'Q09'),
        ('NVK', 'Q10'),
        ('NVK', 'Q11'),
        ('NVK', 'Q12'),
        ('NVK', 'Q13'),
        ('NVK', 'Q14'),
        ('NVK', 'Q15'),
        ('NVK', 'Q17'),
        ('NVK', 'Q18'),
        ('NVK', 'Q21'),
        ('NVK', 'Q22'),
        ('NVK', 'Q25'),
        ('NVK', 'Q26'),
        ('NVK', 'Q27'),

        -- D. NHÂN VIÊN BÁN HÀNG (Sản phẩm, Bán hàng, Hóa đơn, Khách hàng, HTV, KM)
        ('NVBH', 'Q02'),
        ('NVBH', 'Q29'),
        ('NVBH', 'Q30'),
        ('NVBH', 'Q33'),
        ('NVBH', 'Q34'),
        ('NVBH', 'Q37'),
        ('NVBH', 'Q38'),
        ('NVBH', 'Q39'),
        ('NVBH', 'Q42'),
        ('NVBH', 'Q58'),
        ('NVBH', 'Q62')
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
        MaTK VARCHAR(50) NOT NULL PRIMARY KEY,
        TenDangNhap VARCHAR(50),
        MaNV VARCHAR(50),
        MatKhau VARCHAR(255) NOT NULL,
        maNQ VARCHAR(50),
        TrangThaiXuLy NVARCHAR(50),
        TrangThai BIT
    )
    INSERT INTO TaiKhoan
        (MaTK,MaNV, TenDangNhap, MatKhau, maNQ,TrangThaiXuLy, TrangThai)
    VALUES
        ('TK01', 'NV01', 'admin', '123456', 'AD', N'Đang hoạt động', 1),
        ('TK02', 'NV02', 'NV01', '123456', 'QL', N'Đang hoạt động', 1),
        ('TK03', 'NV01', 'NV02', '123456', 'QL', N'Đang hoạt động', 1)
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
        ('NCC11', N'Công ty TNHH Richs', '0283444555', N'Bình Dương', 1),
        ('NCC_NL_01', N'Kho Sỉ Nguyên Liệu Global', '0911000111', N'Quận Thủ Đức, TP.HCM', 1),
        ('NCC_NL_02', N'Nông Sản Sạch Đà Lạt', '0911000222', N'Đức Trọng, Lâm Đồng', 1),
        ('NCC_SP_01', N'Công ty Nước Giải Khát Tân Hiệp', '0911000333', N'Thuận An, Bình Dương', 1),
        ('NCC_SP_02', N'Bánh Ngọt Kinh Đô', '0911000444', N'Quận 1, TP.HCM', 1),
        ('NCC_BOTH_01', N'Tập đoàn Masan Group', '0911000555', N'Quận 7, TP.HCM', 1)
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
        (MaSP, TenSP, MaDM, GiaBan, LoaiNuoc, Anh, TheTich, MucCanhBao, TrangThai, TrangThaiXuLy)
    VALUES
        -- DM03: Nước Ngọt
        ('SP01', N'Pepsi Lon', 'DM03', 15000, N'Có sẵn', '/assets/img/pepsi.png', 330, 10, 1, N'Đã xác nhận'),
        ('SP26', N'Coca Cola', 'DM03', 15000, N'Có sẵn', '/assets/img/pepsi.png', 330, 10, 1, N'Đã xác nhận'),
        ('SP27', N'7Up', 'DM03', 15000, N'Có sẵn', '/assets/img/pepsi.png', 330, 10, 1, N'Đã xác nhận'),
        ('SP28', N'Sting Dâu', 'DM03', 18000, N'Có sẵn', '/assets/img/pepsi.png', 330, 10, 1, N'Đã xác nhận'),
        ('SP29', N'Redbull Thái', 'DM03', 20000, N'Có sẵn', '/assets/img/pepsi.png', 250, 10, 1, N'Đã xác nhận'),
        ('SP30', N'Nước Suối Dasani', 'DM03', 10000, N'Có sẵn', '/assets/img/pepsi.png', 500, 20, 1, N'Đã xác nhận'),
        ('SP31', N'Soda Schweppes', 'DM03', 18000, N'Có sẵn', '/assets/img/pepsi.png', 330, 5, 1, N'Đã xác nhận'),

        -- DM01: Cà Phê
        ('SP02', N'Cà Phê Đen Đá', 'DM01', 25000, N'Pha chế', '/assets/img/pepsi.png', 500, 10, 1, N'Đã xác nhận'),
        ('SP03', N'Cà Phê Sữa Đá', 'DM01', 30000, N'Pha chế', '/assets/img/pepsi.png', 500, 10, 1, N'Đã xác nhận'),
        ('SP05', N'Bạc Xỉu', 'DM01', 32000, N'Pha chế', '/assets/img/pepsi.png', 500, 10, 1, N'Đã xác nhận'),
        ('SP06', N'Cà Phê Muối', 'DM01', 35000, N'Pha chế', '/assets/img/pepsi.png', 500, 10, 1, N'Đã xác nhận'),
        ('SP07', N'Americano Đá', 'DM01', 28000, N'Pha chế', '/assets/img/pepsi.png', 500, 10, 1, N'Đã xác nhận'),
        ('SP08', N'Cappuccino Nóng', 'DM01', 45000, N'Pha chế', '/assets/img/pepsi.png', 350, 10, 1, N'Đã xác nhận'),
        ('SP09', N'Latte Đá', 'DM01', 45000, N'Pha chế', '/assets/img/pepsi.png', 500, 10, 1, N'Đã xác nhận'),
        ('SP10', N'Espresso', 'DM01', 25000, N'Pha chế', '/assets/img/pepsi.png', 50, 10, 1, N'Đã xác nhận'),
        ('SP11', N'Mocha', 'DM01', 50000, N'Pha chế', '/assets/img/pepsi.png', 500, 10, 1, N'Đã xác nhận'),

        -- DM02: Trà Sữa
        ('SP04', N'Trà Sữa Truyền Thống', 'DM02', 35000, N'Pha chế', '/assets/img/pepsi.png', 500, 10, 1, N'Đã xác nhận'),
        ('SP12', N'Trà Sữa Thái Xanh', 'DM02', 35000, N'Pha chế', '/assets/img/pepsi.png', 500, 10, 1, N'Đã xác nhận'),
        ('SP13', N'Trà Sữa Thái Đỏ', 'DM02', 35000, N'Pha chế', '/assets/img/pepsi.png', 500, 10, 1, N'Đã xác nhận'),
        ('SP14', N'Trà Sữa Oolong', 'DM02', 40000, N'Pha chế', '/assets/img/pepsi.png', 500, 10, 1, N'Đã xác nhận'),
        ('SP15', N'Trà Sữa Khoai Môn', 'DM02', 42000, N'Pha chế', '/assets/img/pepsi.png', 500, 10, 1, N'Đã xác nhận'),
        ('SP16', N'Trà Sữa Matcha', 'DM02', 45000, N'Pha chế', '/assets/img/pepsi.png', 500, 10, 1, N'Đã xác nhận'),
        ('SP17', N'Trà Sữa Socola', 'DM02', 45000, N'Pha chế', '/assets/img/pepsi.png', 500, 10, 1, N'Đã xác nhận'),
        ('SP18', N'Sữa Tươi Trân Châu Đường Đen', 'DM02', 45000, N'Pha chế', '/assets/img/pepsi.png', 500, 10, 1, N'Đã xác nhận'),
        ('SP19', N'Hồng Trà Macchiato', 'DM02', 38000, N'Pha chế', '/assets/img/pepsi.png', 500, 10, 1, N'Đã xác nhận'),

        -- DM07: Trà Trái Cây
        ('SP20', N'Trà Đào Cam Sả', 'DM07', 45000, N'Pha chế', '/assets/img/pepsi.png', 700, 10, 1, N'Đã xác nhận'),
        ('SP21', N'Trà Vải Hoa Hồng', 'DM07', 45000, N'Pha chế', '/assets/img/pepsi.png', 700, 10, 1, N'Đã xác nhận'),
        ('SP22', N'Trà Ổi Hồng', 'DM07', 40000, N'Pha chế', '/assets/img/pepsi.png', 700, 10, 1, N'Đã xác nhận'),
        ('SP23', N'Trà Tắc Xí Muội', 'DM07', 30000, N'Pha chế', '/assets/img/pepsi.png', 700, 10, 1, N'Đã xác nhận'),
        ('SP24', N'Trà Dâu Tây', 'DM07', 48000, N'Pha chế', '/assets/img/pepsi.png', 700, 10, 1, N'Đã xác nhận'),
        ('SP25', N'Lục Trà Chanh Mật Ong', 'DM07', 35000, N'Pha chế', '/assets/img/pepsi.png', 700, 10, 1, N'Đã xác nhận'),

        -- DM05: Sinh Tố & Nước Ép
        ('SP32', N'Sinh Tố Bơ', 'DM05', 50000, N'Pha chế', '/assets/img/pepsi.png', 500, 5, 1, N'Đã xác nhận'),
        ('SP33', N'Sinh Tố Xoài', 'DM05', 45000, N'Pha chế', '/assets/img/pepsi.png', 500, 5, 1, N'Đã xác nhận'),
        ('SP34', N'Sinh Tố Dâu', 'DM05', 50000, N'Pha chế', '/assets/img/pepsi.png', 500, 5, 1, N'Đã xác nhận'),
        ('SP35', N'Nước Ép Cam', 'DM05', 40000, N'Pha chế', '/assets/img/pepsi.png', 350, 10, 1, N'Đã xác nhận'),
        ('SP36', N'Nước Ép Táo', 'DM05', 45000, N'Pha chế', '/assets/img/pepsi.png', 350, 10, 1, N'Đã xác nhận'),
        ('SP37', N'Nước Ép Dưa Hấu', 'DM05', 35000, N'Pha chế', '/assets/img/pepsi.png', 350, 10, 1, N'Đã xác nhận'),
        ('SP38', N'Nước Ép Cà Rốt', 'DM05', 35000, N'Pha chế', '/assets/img/pepsi.png', 350, 10, 1, N'Đã xác nhận'),

        -- DM06: Đá Xay
        ('SP39', N'Matcha Đá Xay', 'DM06', 55000, N'Pha chế', '/assets/img/pepsi.png', 500, 5, 1, N'Đã xác nhận'),
        ('SP40', N'Cookie Đá Xay', 'DM06', 55000, N'Pha chế', '/assets/img/pepsi.png', 500, 5, 1, N'Đã xác nhận'),
        ('SP41', N'Cà Phê Đá Xay', 'DM06', 55000, N'Pha chế', '/assets/img/pepsi.png', 500, 5, 1, N'Đã xác nhận'),

        -- DM04: Đồ Ăn Nhẹ & DM09: Bánh Ngọt
        ('SP42', N'Bánh Mì Que', 'DM04', 15000, N'Có sẵn', '/assets/img/pepsi.png', 100, 10, 1, N'Đã xác nhận'),
        ('SP43', N'Khô Gà Lá Chanh', 'DM04', 50000, N'Có sẵn', '/assets/img/pepsi.png', 200, 10, 1, N'Đã xác nhận'),
        ('SP44', N'Hạt Hướng Dương', 'DM04', 25000, N'Có sẵn', '/assets/img/pepsi.png', 150, 20, 1, N'Đã xác nhận'),
        ('SP45', N'Tiramisu', 'DM09', 45000, N'Có sẵn', '/assets/img/pepsi.png', 150, 5, 1, N'Đã xác nhận'),
        ('SP46', N'Mousse Chanh Dây', 'DM09', 45000, N'Có sẵn', '/assets/img/pepsi.png', 150, 5, 1, N'Đã xác nhận'),
        ('SP47', N'Bánh Croissant', 'DM09', 30000, N'Có sẵn', '/assets/img/pepsi.png', 100, 5, 1, N'Đã xác nhận'),
        ('SP48', N'Bánh Flan', 'DM09', 20000, N'Có sẵn', '/assets/img/pepsi.png', 100, 10, 1, N'Đã xác nhận'),

        -- DM08: Sữa Chua
        ('SP49', N'Sữa Chua Trân Châu', 'DM08', 35000, N'Pha chế', '/assets/img/pepsi.png', 300, 10, 1, N'Đã xác nhận'),
        ('SP50', N'Sữa Chua Dẻo', 'DM08', 30000, N'Có sẵn', '/assets/img/pepsi.png', 100, 10, 1, N'Đã xác nhận'),

        -- DM10: Topping
        ('SP_TOP01', N'Trân Châu Đen', 'DM10', 5000, N'Pha chế', '/assets/img/pepsi.png', 0, 10, 1, N'Đã xác nhận'),
        ('SP_TOP02', N'Thạch Phô Mai', 'DM10', 7000, N'Pha chế', '/assets/img/pepsi.png', 0, 10, 1, N'Đã xác nhận'),
        ('SP_TOP03', N'Kem Cheese', 'DM10', 10000, N'Pha chế', '/assets/img/pepsi.png', 0, 10, 1, N'Đã xác nhận')
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
        -- NHÓM 1: CÀ PHÊ
        -- SP02: Đen đá
        ('SZ02_S', 'SP02', N'Size S', 0, 100, 1),
        ('SZ02_M', 'SP02', N'Size M', 15, 125, 1),
        ('SZ02_L', 'SP02', N'Size L', 25, 150, 1),
        -- SP03: Sữa đá
        ('SZ03_S', 'SP03', N'Size S', 0, 100, 1),
        ('SZ03_M', 'SP03', N'Size M', 15, 125, 1),
        ('SZ03_L', 'SP03', N'Size L', 25, 150, 1),
        -- SP05: Bạc xỉu
        ('SZ05_S', 'SP05', N'Size S', 0, 100, 1),
        ('SZ05_M', 'SP05', N'Size M', 15, 125, 1),
        ('SZ05_L', 'SP05', N'Size L', 25, 150, 1),
        -- SP06: Cà phê muối
        ('SZ06_S', 'SP06', N'Size S', 0, 100, 1),
        ('SZ06_M', 'SP06', N'Size M', 15, 125, 1),
        ('SZ06_L', 'SP06', N'Size L', 25, 150, 1),
        -- SP07: Americano
        ('SZ07_S', 'SP07', N'Size S', 0, 100, 1),
        ('SZ07_M', 'SP07', N'Size M', 15, 125, 1),
        ('SZ07_L', 'SP07', N'Size L', 25, 150, 1),
        -- SP08: Cappuccino
        ('SZ08_S', 'SP08', N'Size S', 0, 100, 1),
        ('SZ08_M', 'SP08', N'Size M', 15, 125, 1),
        ('SZ08_L', 'SP08', N'Size L', 25, 150, 1),
        -- SP09: Latte
        ('SZ09_S', 'SP09', N'Size S', 0, 100, 1),
        ('SZ09_M', 'SP09', N'Size M', 15, 125, 1),
        ('SZ09_L', 'SP09', N'Size L', 25, 150, 1),
        -- SP11: Mocha
        ('SZ11_S', 'SP11', N'Size S', 0, 100, 1),
        ('SZ11_M', 'SP11', N'Size M', 15, 125, 1),
        ('SZ11_L', 'SP11', N'Size L', 25, 150, 1),

        -- NHÓM 2: TRÀ SỮA & TRÀ TRÁI CÂY
        -- SP04: Truyền thống
        ('SZ04_S', 'SP04', N'Size S', 0, 100, 1),
        ('SZ04_M', 'SP04', N'Size M', 15, 125, 1),
        ('SZ04_L', 'SP04', N'Size L', 25, 150, 1),
        -- SP12: Thái Xanh
        ('SZ12_S', 'SP12', N'Size S', 0, 100, 1),
        ('SZ12_M', 'SP12', N'Size M', 15, 125, 1),
        ('SZ12_L', 'SP12', N'Size L', 25, 150, 1),
        -- SP13: Thái Đỏ
        ('SZ13_S', 'SP13', N'Size S', 0, 100, 1),
        ('SZ13_M', 'SP13', N'Size M', 15, 125, 1),
        ('SZ13_L', 'SP13', N'Size L', 25, 150, 1),
        -- SP14 -> SP19 (Các loại trà sữa khác)
        ('SZ14_S', 'SP14', N'Size S', 0, 100, 1),
        ('SZ14_M', 'SP14', N'Size M', 15, 125, 1),
        ('SZ14_L', 'SP14', N'Size L', 25, 150, 1),
        ('SZ15_S', 'SP15', N'Size S', 0, 100, 1),
        ('SZ15_M', 'SP15', N'Size M', 15, 125, 1),
        ('SZ15_L', 'SP15', N'Size L', 25, 150, 1),
        ('SZ16_S', 'SP16', N'Size S', 0, 100, 1),
        ('SZ16_M', 'SP16', N'Size M', 15, 125, 1),
        ('SZ16_L', 'SP16', N'Size L', 25, 150, 1),
        ('SZ17_S', 'SP17', N'Size S', 0, 100, 1),
        ('SZ17_M', 'SP17', N'Size M', 15, 125, 1),
        ('SZ17_L', 'SP17', N'Size L', 25, 150, 1),
        ('SZ18_S', 'SP18', N'Size S', 0, 100, 1),
        ('SZ18_M', 'SP18', N'Size M', 15, 125, 1),
        ('SZ18_L', 'SP18', N'Size L', 25, 150, 1),
        ('SZ19_S', 'SP19', N'Size S', 0, 100, 1),
        ('SZ19_M', 'SP19', N'Size M', 15, 125, 1),
        ('SZ19_L', 'SP19', N'Size L', 25, 150, 1),
        -- SP20 -> SP25 (Trà trái cây)
        ('SZ20_S', 'SP20', N'Size S', 0, 100, 1),
        ('SZ20_M', 'SP20', N'Size M', 15, 125, 1),
        ('SZ20_L', 'SP20', N'Size L', 25, 150, 1),
        ('SZ21_S', 'SP21', N'Size S', 0, 100, 1),
        ('SZ21_M', 'SP21', N'Size M', 15, 125, 1),
        ('SZ21_L', 'SP21', N'Size L', 25, 150, 1),
        ('SZ22_S', 'SP22', N'Size S', 0, 100, 1),
        ('SZ22_M', 'SP22', N'Size M', 15, 125, 1),
        ('SZ22_L', 'SP22', N'Size L', 25, 150, 1),
        ('SZ23_S', 'SP23', N'Size S', 0, 100, 1),
        ('SZ23_M', 'SP23', N'Size M', 15, 125, 1),
        ('SZ23_L', 'SP23', N'Size L', 25, 150, 1),
        ('SZ24_S', 'SP24', N'Size S', 0, 100, 1),
        ('SZ24_M', 'SP24', N'Size M', 15, 125, 1),
        ('SZ24_L', 'SP24', N'Size L', 25, 150, 1),
        ('SZ25_S', 'SP25', N'Size S', 0, 100, 1),
        ('SZ25_M', 'SP25', N'Size M', 15, 125, 1),
        ('SZ25_L', 'SP25', N'Size L', 25, 150, 1),

        -- NHÓM 3: MÓN ĐẶC THÙ (CHỈ 1 SIZE TIÊU CHUẨN)
        -- SP10: Espresso
        ('SZ10_STD', 'SP10', N'Tiêu chuẩn', 0, 100, 1),
        -- SP32 -> SP38: Sinh tố / Nước ép
        ('SZ32_STD', 'SP32', N'Tiêu chuẩn', 0, 100, 1),
        ('SZ33_STD', 'SP33', N'Tiêu chuẩn', 0, 100, 1),
        ('SZ34_STD', 'SP34', N'Tiêu chuẩn', 0, 100, 1),
        ('SZ35_STD', 'SP35', N'Tiêu chuẩn', 0, 100, 1),
        ('SZ36_STD', 'SP36', N'Tiêu chuẩn', 0, 100, 1),
        ('SZ37_STD', 'SP37', N'Tiêu chuẩn', 0, 100, 1),
        ('SZ38_STD', 'SP38', N'Tiêu chuẩn', 0, 100, 1),
        -- SP39 -> SP41: Đá xay
        ('SZ39_STD', 'SP39', N'Tiêu chuẩn', 0, 100, 1),
        ('SZ40_STD', 'SP40', N'Tiêu chuẩn', 0, 100, 1),
        ('SZ41_STD', 'SP41', N'Tiêu chuẩn', 0, 100, 1),
        -- SP49: Sữa chua
        ('SZ49_STD', 'SP49', N'Tiêu chuẩn', 0, 100, 1)
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
        Gia DECIMAL(18, 2),
        DonVi NVARCHAR(50),
        MucCanhBao INT,
        TrangThai BIT
    )
    INSERT INTO NguyenLieu
        (MaNL, TenNL, Gia, DonVi, MucCanhBao, TrangThai)
    VALUES
        ('NL01', N'Hạt Cà Phê Robusta', 200000, 'kg', 5, 1),
        ('NL02', N'Sữa Đặc Ngôi Sao', 25000, 'hop', 10, 1),
        ('NL03', N'Đường Cát Trắng', 15000, 'kg', 5, 1),
        ('NL04', N'Sữa Tươi Không Đường', 30000, 'lit', 10, 1),
        ('NL05', N'Bột Matcha Đài Loan', 500000, 'kg', 2, 1),
        ('NL06', N'Trân Châu Đen', 40000, 'kg', 5, 1),
        ('NL07', N'Syrup Đào', 150000, 'chai', 3, 1),
        ('NL08', N'Trà Đen Túi Lọc', 80000, 'goi', 10, 1),
        ('NL09', N'Kem Béo Thực Vật', 60000, 'hop', 5, 1),
        ('NL10', N'Bột Cacao', 120000, 'kg', 3, 1),
        ('NL11', N'Ly Nhựa 500ml', 500, 'cai', 100, 1),
        ('NL12', N'Ống Hút', 200, 'cai', 200, 1),
        ('NL13', N'Đá Viên Tinh Khiết', 20000, 'bao', 2, 1),
        ('NL14', N'Xoài Tươi', 40000, 'kg', 5, 1)
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
        ('CT01', 'SP01', 1),
        -- Cà phê
        ('CT02', 'SP02', 1),
        ('CT03', 'SP03', 1),
        ('CT05', 'SP05', 1),
        ('CT06', 'SP06', 1),
        ('CT07', 'SP07', 1),
        ('CT08', 'SP08', 1),
        ('CT09', 'SP09', 1),
        ('CT11', 'SP11', 1),

        -- Trà sữa & Trà
        ('CT04', 'SP04', 1),
        ('CT04_M', 'SP04', 1),
        ('CT04_L', 'SP04', 1),
        ('CT12', 'SP12', 1),
        ('CT13', 'SP13', 1),
        ('CT14', 'SP14', 1),
        ('CT15', 'SP15', 1),
        ('CT16', 'SP16', 1),
        ('CT17', 'SP17', 1),
        ('CT18', 'SP18', 1),
        ('CT19', 'SP19', 1),
        ('CT20', 'SP20', 1),
        ('CT21', 'SP21', 1),
        ('CT22', 'SP22', 1),
        ('CT23', 'SP23', 1),
        ('CT24', 'SP24', 1),
        ('CT25', 'SP25', 1),

        -- Món 1 size
        ('CT10', 'SP10', 1),
        -- Espresso
        ('CT32', 'SP32', 1),
        -- Bơ
        ('CT33', 'SP33', 1),
        ('CT34', 'SP34', 1),
        ('CT35', 'SP35', 1),
        ('CT36', 'SP36', 1),
        ('CT37', 'SP37', 1),
        ('CT38', 'SP38', 1),
        ('CT39', 'SP39', 1),
        ('CT40', 'SP40', 1),
        ('CT41', 'SP41', 1),
        ('CT49', 'SP49', 1),

        -- Công thức cho Topping
        ('CT_TOP01', 'SP_TOP01', 1),
        ('CT_TOP02', 'SP_TOP02', 1)
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
        TrangThai BIT
    )
    INSERT INTO ChiTietCongThuc
        (MaCTCT,MaCT, MaNL, SoLuong,TrangThai)
    VALUES
        ('CTCT01', 'CT01', 'NL01', 0.02, 1),
        ('CTCT02', 'CT01', 'NL02', 0.03, 1),
        ('CTCT03', 'CT02', 'NL01', 0.025, 1),
        ('CTCT04', 'CT02', 'NL03', 0.01, 1),
        ('CTCT05', 'CT03', 'NL01', 0.01, 1),
        ('CTCT06', 'CT03', 'NL02', 0.03, 1),
        ('CTCT07', 'CT03', 'NL06', 0.05, 1),
        ('CTCT08', 'CT04', 'NL05', 0.01, 1),
        ('CTCT09', 'CT04', 'NL04', 0.1, 1),
        ('CTCT10', 'CT06', 'NL08', 0.01, 1),
        ('CTCT11', 'CT06', 'NL07', 0.03, 1),
        ('CTCT12', 'CT05', 'NL02', 0.02, 1),
        ('CTCT13', 'CT01', 'NL13', 0.2, 1),

        -- Topping tốn nguyên liệu
        ('CTCT_TOP01', 'CT_TOP01', 'NL06', 0.05, 1),
        ('CTCT_TOP02', 'CT_TOP02', 'NL09', 0.05, 1),

        -- Trà sữa truyền thống Size M (Gấp rưỡi Size S)
        ('CTCT_04M_1', 'CT04_M', 'NL05', 0.015, 1),
        ('CTCT_04M_2', 'CT04_M', 'NL04', 0.15, 1),

        -- Trà sữa truyền thống Size L (Gấp đôi Size S)
        ('CTCT_04L_1', 'CT04_L', 'NL05', 0.02, 1),
        ('CTCT_04L_2', 'CT04_L', 'NL04', 0.2, 1),
        -- Đá bi
        ('CTCT14', 'CT01', 'NL11', 1, 1)
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
        NgayVaoLam DATE,
        SDT VARCHAR(20),
        DiaChi NVARCHAR(255),
        ChucVu NVARCHAR(255),
        Anh NVARCHAR(255),
        TrangThai BIT
    )

    INSERT INTO NhanVien
        (MaNV, TenNV, GioiTinh, NgaySinh, NgayVaoLam, SDT, DiaChi, ChucVu, Anh, TrangThai)
    VALUES
        ('NV01', N'Nguyễn Hoài Bảo', N'Nam', '2000-01-01', '2026-03-08', '0901234567', N'Địa chỉ 1','Nhân viên bán hàng' '/assets/img/goku.png', 1),
        ('NV02', N'Phạm Hữu Phú', N'Nam', '2000-02-02', '2026-03-08', '0902345678', N'Địa chỉ 2', NULL, 1),
        ('NV03', N'Lê Huy Hoàng', N'Nam', '1995-05-05', '2026-03-08', '0903456789', N'Địa chỉ 3', NULL, 1)
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
        NgayBan DATETIME,
        TongTien DECIMAL(18, 2),
        TienKhuyenMai DECIMAL(18, 2),
        TrangThai BIT
    )
    INSERT INTO HoaDon
        (MaHD, MaNV, MaKH, MaKM, NgayBan, TongTien, TienKhuyenMai, TrangThai)
    VALUES
        ('HD_TK_01', 'NV01', 'KH001', NULL, '2024-03-15 08:15:00', 125000, 0, 1),
        -- 8h sáng
        ('HD_TK_02', 'NV02', 'KH002', NULL, '2024-03-15 08:45:00', 80000, 0, 1),
        -- 8h sáng
        ('HD_TK_03', 'NV01', 'KH003', NULL, '2024-03-15 09:30:00', 210000, 0, 1),
        -- 9h sáng (Đông khách)
        ('HD_TK_04', 'NV03', 'KH004', NULL, '2024-03-15 09:50:00', 150000, 0, 1),
        -- 9h sáng
        ('HD_TK_05', 'NV02', 'KH005', NULL, '2024-03-15 12:10:00', 350000, 0, 1),
        -- 12h trưa
        ('HD_TK_06', 'NV01', 'KH006', NULL, '2024-03-15 14:20:00', 90000, 0, 1),
        -- 14h chiều
        ('HD_TK_07', 'NV03', 'KH007', NULL, '2024-03-15 19:15:00', 450000, 0, 1),
        -- 19h tối (Đỉnh điểm)
        ('HD_TK_08', 'NV02', 'KH008', NULL, '2024-03-15 19:40:00', 200000, 0, 1),
        -- 19h tối
        ('HD_TK_09', 'NV01', 'KH009', NULL, '2024-03-15 21:05:00', 110000, 0, 1),
        -- 21h tối
        ('HD_TK_10', 'NV01', 'KH001', NULL, '2024-03-01 10:00:00', 1500000, 0, 1),
        ('HD_TK_11', 'NV02', 'KH002', NULL, '2024-03-05 14:00:00', 2200000, 0, 1),
        ('HD_TK_12', 'NV03', 'KH003', NULL, '2024-03-10 16:30:00', 1800000, 0, 1),
        ('HD_TK_13', 'NV01', 'KH004', NULL, '2024-03-12 11:00:00', 3100000, 0, 1),
        ('HD_TK_14', 'NV02', 'KH005', NULL, '2024-03-20 09:15:00', 950000, 0, 1),
        ('HD_TK_15', 'NV01', 'KH006', NULL, '2024-03-25 18:45:00', 2500000, 0, 1),
        ('HD_TK_16', 'NV03', 'KH007', NULL, '2024-03-28 20:00:00', 1200000, 0, 1),
        ('HD_TK_17', 'NV01', 'KH008', NULL, '2023-01-15 10:00:00', 5500000, 0, 1),
        ('HD_TK_18', 'NV02', 'KH009', NULL, '2023-05-20 15:00:00', 8200000, 0, 1),
        ('HD_TK_19', 'NV03', 'KH010', NULL, '2023-12-24 19:00:00', 12000000, 0, 1),
        -- Giáng sinh 2023
        -- Năm 2024
        ('HD_TK_20', 'NV01', 'KH001', NULL, '2024-01-05 10:00:00', 4500000, 0, 1),
        ('HD_TK_21', 'NV02', 'KH002', NULL, '2024-02-14 19:30:00', 6500000, 0, 1),
        -- Valentine 2024
        ('HD_TK_22', 'NV03', 'KH003', NULL, '2024-04-30 20:00:00', 9000000, 0, 1),
        -- Lễ 30/4
        ('HD_TK_23', 'NV01', 'KH004', NULL, '2024-06-01 09:00:00', 5000000, 0, 1),
        ('HD_TK_24', 'NV02', 'KH005', NULL, '2024-09-02 18:00:00', 8500000, 0, 1),
        -- Lễ 2/9
        ('HD_TK_25', 'NV01', 'KH006', NULL, '2024-11-20 10:00:00', 4200000, 0, 1)


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
        MaCTHD VARCHAR(50) NOT NULL PRIMARY KEY,
        MaHD VARCHAR(50) NOT NULL,
        MaSP VARCHAR(50) NOT NULL,
        MaSize VARCHAR(50) NULL,
        SoLuong INT,
        Gia DECIMAL(18, 2),
        FOREIGN KEY (MaHD) REFERENCES HoaDon(MaHD),
        FOREIGN KEY (MaSP) REFERENCES SanPham(MaSP)
    )
    INSERT INTO ChiTietHoaDon
        (MaCTHD,MaHD, MaSP, MaSize, SoLuong, Gia)
    VALUES
        ('CTHD_TK_01', 'HD_TK_01', 'SP02', 'SZ02_M', 2, 25000),
        ('CTHD_TK_02', 'HD_TK_01', 'SP04', 'SZ04_L', 1, 35000),
        ('CTHD_TK_03', 'HD_TK_02', 'SP32', 'SZ32_STD', 1, 50000),
        ('CTHD_TK_04', 'HD_TK_03', 'SP05', 'SZ05_M', 3, 32000),
        ('CTHD_TK_05', 'HD_TK_04', 'SP12', 'SZ12_M', 2, 35000),
        ('CTHD_TK_06', 'HD_TK_05', 'SP20', 'SZ20_L', 5, 45000),
        ('CTHD_TK_07', 'HD_TK_06', 'SP45', NULL, 2, 45000),
        ('CTHD_TK_08', 'HD_TK_07', 'SP39', 'SZ39_STD', 4, 55000),
        ('CTHD_TK_09', 'HD_TK_08', 'SP18', 'SZ18_M', 2, 45000),
        ('CTHD_TK_10', 'HD_TK_09', 'SP01', NULL, 5, 15000)
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
        MaPN VARCHAR(50)NOT NULL PRIMARY KEY,
        NgayNhap DATE,
        MaNV VARCHAR(50),
        TongTien DECIMAL(18, 2),
        MaNCC VARCHAR(50),
        GhiChu NVARCHAR(MAX),
        TrangThaiXuLy NVARCHAR(50),
        TrangThai BIT
    )
    INSERT INTO PhieuNhapSanPham
        (MaPN, NgayNhap, MaNV, TongTien, MaNCC,GhiChu ,TrangThaiXuLy, TrangThai)
    VALUES
        ('PNSP01', '2024-01-10', 'NV03', 8000000, 'NCC01', N'Đã xác nhận', N'Đã xác nhận', 1),
        ('PNSP02', '2024-01-11', 'NV03', 5000000, 'NCC04', N'Đã xác nhận', N'Đã xác nhận', 1),

        ('PNSP03', '2024-01-15', 'NV03', 2000000, 'NCC10', N'Đã xác nhận', N'Đã xác nhận', 1),
        ('PNSP04', '2024-02-01', 'NV03', 8000000, 'NCC01', N'Đã xác nhận', N'Đã xác nhận', 1),
        ('PNSP05', '2024-02-05', 'NV03', 1500000, 'NCC11', N'Đã xác nhận', N'Đã xác nhận', 1)
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
        GiaNhap DECIMAL(18, 2),
        TrangThaiXuLy NVARCHAR(50),
        TrangThai BIT
    )
    INSERT INTO LoSanPham
        (MaLoSP, MaPN, MaSP, SoLuong, NgayNhap, NgaySanXuat, HanSuDung, GiaNhap, TrangThaiXuLy,TrangThai)
    VALUES
        ('LOSP01', 'PNSP01', 'SP01', 1000, '2024-01-10', '2024-01-01', '2025-01-01', 8000000, N'Đã xác nhận', 1),
        ('LOSP02', 'PNSP02', 'SP26', 500, '2024-01-11', '2024-01-01', '2024-12-31', 4000000, N'Đã xác nhận', 1),
        ('LOSP03', 'PNSP03', 'SP42', 250, '2024-01-15', '2024-01-14', '2024-01-20', 2000000, N'Đã xác nhận', 1),
        -- Bánh mì que date ngắn
        ('LOSP04', 'PNSP04', 'SP27', 1000, '2024-02-01', '2024-01-15', '2025-01-15', 8000000, N'Đã xác nhận', 1),
        ('LOSP05', 'PNSP05', 'SP45', 100, '2024-02-05', '2024-02-04', '2024-02-10', 1500000, N'Đã xác nhận', 1)
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
        MaPN VARCHAR(50) NOT NULL PRIMARY KEY,
        NgayNhap DATE,
        MaNV VARCHAR(50),
        TongTien DECIMAL(18, 2),
        MaNCC VARCHAR(50),
        TrangThaiXuLy NVARCHAR(50),
        GhiChu NVARCHAR(255),
        TrangThai BIT
    )
    INSERT INTO PhieuNhapNguyenLieu
        (MaPN, NgayNhap, MaNV, TongTien, MaNCC, TrangThaiXuLy, GhiChu, TrangThai)
    VALUES
        ('PNNL01', '2024-01-12', 'NV03', 5000000, 'NCC02', N'Đã xác nhận', N'Đã xác nhận', 1),
        ('PNNL02', '2024-01-13', 'NV02', 3000000, 'NCC03', N'Đã xác nhận', N'Đã xác nhận', 1),
        ('PNNL03', '2024-01-20', 'NV03', 10000000, 'NCC08', N'Đã xác nhận', N'Đã xác nhận', 1),
        ('PNNL04', '2024-02-01', 'NV02', 2000000, 'NCC05', N'Đã xác nhận', N'Đã xác nhận', 1)
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
        SoLuong FLOAT,
        NgayNhap DATE,
        NgaySanXuat DATE,
        HanSuDung DATE,
        GiaNhap DECIMAL(18, 2),
        TrangThaiXuLy VARCHAR(50),
        TrangThai BIT
    )

    INSERT INTO LoNguyenLieu
        (MaLoNL, MaPN, MaNL, SoLuong, NgayNhap, NgaySanXuat, HanSuDung, GiaNhap, TrangThaiXuLy, TrangThai)
    VALUES
        ('LONL01', 'PNNL01', 'NL01', 25.0, '2024-01-12', '2024-01-01', '2024-06-01', 150000, N'Đã xác nhận', 1),
        -- Cà phê
        ('LONL02', 'PNNL02', 'NL02', 120.0, '2024-01-13', '2024-01-01', '2024-07-01', 250000, N'Đã xác nhận', 1),
        -- Sữa đặc
        ('LONL03', 'PNNL03', 'NL05', 20.5, '2024-01-20', '2023-12-01', '2024-12-01', 800000, N'Đã xác nhận', 1),
        -- Matcha (Có số thập phân)
        ('LONL04', 'PNNL03', 'NL06', 250.0, '2024-01-20', '2024-01-15', '2024-03-15', 300000, N'Đã xác nhận', 1),
        -- Trân châu
        ('LONL05', 'PNNL04', 'NL07', 15.0, '2024-02-01', '2024-01-01', '2025-01-01', 120000, N'Đã xác nhận', 1),
        -- Nguyên liệu (test)
        ('LONL_NL04', 'PNNL01', 'NL04', 10000, '2024-01-01', '2024-01-01', '2030-12-31', 30000, N'Đã xác nhận', 1),
        -- Sữa tươi
        ('LONL_NL05', 'PNNL01', 'NL05', 10000, '2024-01-01', '2024-01-01', '2030-12-31', 50000, N'Đã xác nhận', 1),
        -- Matcha/Trà
        ('LONL_NL08', 'PNNL01', 'NL08', 10000, '2024-01-01', '2024-01-01', '2030-12-31', 80000, N'Đã xác nhận', 1),
        -- Trà đen
        ('LONL_NL09', 'PNNL01', 'NL09', 10000, '2024-01-01', '2024-01-01', '2030-12-31', 60000, N'Đã xác nhận', 1),
        -- Kem béo
        ('LONL_NL11', 'PNNL01', 'NL11', 10000, '2024-01-01', '2024-01-01', '2030-12-31', 500, N'Đã xác nhận', 1),
        -- Ly nhựa
        ('LONL_NL13', 'PNNL01', 'NL13', 10000, '2024-01-01', '2024-01-01', '2030-12-31', 20000, N'Đã xác nhận', 1)
-- Đá viên
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
        MaPH VARCHAR(50) NOT NULL PRIMARY KEY,
        NgayHuy DATE,
        MaNV VARCHAR(50),
        LyDo NVARCHAR(MAX),
        TongGiaTri DECIMAL(18, 2),
        TrangThaiXuLy NVARCHAR(50),
        TrangThai BIT
    )
    INSERT INTO PhieuHuySanPham
        (MaPH, NgayHuy, MaNV, LyDo, TongGiaTri, TrangThaiXuLy, TrangThai)
    VALUES
        ('PHSP01', '2024-02-20', 'NV03', N'Sản phẩm hết hạn sử dụng', 4000000, N'Đã xác nhận', 1),
        ('PHSP02', '2024-02-21', 'NV03', N'Hư hỏng do quá trình vận chuyển', 1500000, N'Đã xác nhận', 1)
END;

IF NOT EXISTS (SELECT *
FROM sys.tables
WHERE name = 'ChiTietPhieuHuySanPham')
BEGIN
    CREATE TABLE ChiTietPhieuHuySanPham
    (
        MaPH VARCHAR(50) NOT NULL,
        MaLo VARCHAR(50) NOT NULL,
        SoLuong FLOAT,
        DonGia DECIMAL(18, 2),
        PRIMARY KEY (MaPH, MaLo),
        CONSTRAINT FK_CTPHSP_PhieuHuy FOREIGN KEY (MaPH) REFERENCES PhieuHuySanPham(MaPH),
        CONSTRAINT FK_CTPHSP_LoSP FOREIGN KEY (MaLo) REFERENCES LoSanPham(MaLoSP)
    )
    INSERT INTO ChiTietPhieuHuySanPham
        (MaPH, MaLo, SoLuong, DonGia)
    VALUES
        ('PHSP01', 'LOSP02', 10, 400000),
        ('PHSP02', 'LOSP05', 5, 300000)
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
        MaPH VARCHAR(50) NOT NULL PRIMARY KEY,
        NgayHuy DATE,
        MaNV VARCHAR(50),
        LyDo NVARCHAR(MAX),
        TongTien DECIMAL(18, 2),
        TrangThaiXuLy NVARCHAR(50),
        TrangThai BIT
    )
    INSERT INTO PhieuHuyNguyenLieu
        (MaPH, NgayHuy, MaNV, LyDo, TongTien, TrangThaiXuLy, TrangThai)
    VALUES
        ('PHNL01', '2024-02-22', 'NV03', N'Nguyên liệu bị ẩm mốc', 300000, N'Đã xác nhận', 1),
        ('PHNL02', '2024-02-23', 'NV03', N'Đổ vỡ bao bì', 250000, N'Đã xác nhận', 1)
END;

IF NOT EXISTS (SELECT *
FROM sys.tables
WHERE name = 'ChiTietPhieuHuyNguyenLieu')
BEGIN
    CREATE TABLE ChiTietPhieuHuyNguyenLieu
    (
        MaPH VARCHAR(50) NOT NULL,
        MaLo VARCHAR(50) NOT NULL,
        SoLuong FLOAT,
        DonGia DECIMAL(18, 2),
        PRIMARY KEY (MaPH, MaLo),
        CONSTRAINT FK_CTPHNL_PhieuHuy FOREIGN KEY (MaPH) REFERENCES PhieuHuyNguyenLieu(MaPH),
        CONSTRAINT FK_CTPHNL_LoNL FOREIGN KEY (MaLo) REFERENCES LoNguyenLieu(MaLoNL)
    )
    INSERT INTO ChiTietPhieuHuyNguyenLieu
        (MaPH, MaLo, SoLuong, DonGia)
    VALUES
        ('PHNL01', 'LONL01', 2, 150000),
        ('PHNL02', 'LONL02', 1, 250000)
END;

IF NOT EXISTS (SELECT *
FROM sys.tables
WHERE name = 'PhieuKiemKe')
BEGIN
    CREATE TABLE PhieuKiemKe
    (
        MaKK VARCHAR(50) NOT NULL PRIMARY KEY,
        NgayKiem DATE NOT NULL,
        MaLo VARCHAR(50) NOT NULL,
        LoaiLo NVARCHAR(50),
        SoLuongSoSach INT,
        SoLuongThuc INT,
        GhiChu NVARCHAR(MAX),
        MaNV VARCHAR(50),
        TrangThaiXuLy NVARCHAR(50),
        TrangThai BIT DEFAULT 1
    )

    INSERT INTO PhieuKiemKe
        (MaKK, NgayKiem, MaLo, LoaiLo, SoLuongSoSach, SoLuongThuc, GhiChu, MaNV, TrangThaiXuLy,TrangThai)
    VALUES
        ('KK001', '2024-02-01', 'LOSP01', N'Sản phẩm', 1000, 998, N'Hao hụt 2 lon do móp méo', 'NV01', N'Đã xác nhận', 1),
        ('KK002', '2024-02-02', 'LONL01', N'Nguyên liệu', 25, 25, N'Khớp số lượng', 'NV03', N'Đã xác nhận', 1)
END;



IF NOT EXISTS (SELECT *
FROM sys.tables
WHERE name = 'ChiTietNhaCungCap')
BEGIN
    CREATE TABLE ChiTietNhaCungCap
    (
        MaCTNCC VARCHAR(50) PRIMARY KEY,
        -- Đã sửa thành VARCHAR và bỏ IDENTITY
        MaNCC VARCHAR(50) NOT NULL,
        LoaiDoiTuong NVARCHAR(50) NOT NULL,
        MaDoiTuong VARCHAR(50) NOT NULL,
        GiaNhap DECIMAL(18, 2),
        TrangThai BIT DEFAULT 1,
    )

    -- Lưu ý: Khi INSERT bạn phải tự cung cấp giá trị cho MaCTNCC
    INSERT INTO ChiTietNhaCungCap
        (MaCTNCC, MaNCC, LoaiDoiTuong, MaDoiTuong, GiaNhap)
    VALUES
        ('CT001', 'NCC_NL_01', N'Nguyên liệu', 'NL01', 180000),
        ('CT002', 'NCC_NL_01', N'Nguyên liệu', 'NL05', 450000),
        ('CT003', 'NCC_NL_02', N'Nguyên liệu', 'NL14', 35000),
        ('CT004', 'NCC_NL_02', N'Nguyên liệu', 'NL03', 12000)

    INSERT INTO ChiTietNhaCungCap
        (MaCTNCC, MaNCC, LoaiDoiTuong, MaDoiTuong, GiaNhap)
    VALUES
        ('CT005', 'NCC_SP_01', N'Sản phẩm', 'SP01', 9000),
        ('CT006', 'NCC_SP_01', N'Sản phẩm', 'SP27', 9000),
        ('CT007', 'NCC_SP_02', N'Sản phẩm', 'SP45', 35000),
        ('CT008', 'NCC_SP_02', N'Sản phẩm', 'SP47', 22000)

    INSERT INTO ChiTietNhaCungCap
        (MaCTNCC, MaNCC, LoaiDoiTuong, MaDoiTuong, GiaNhap)
    VALUES
        ('CT009', 'NCC_BOTH_01', N'Nguyên liệu', 'NL04', 28000),
        ('CT010', 'NCC_BOTH_01', N'Sản phẩm', 'SP30', 6000)

    ALTER TABLE ChiTietNhaCungCap 
    ADD CONSTRAINT FK_CTNCC_NhaCungCap FOREIGN KEY (MaNCC) REFERENCES NhaCungCap(MaNCC)
END;


-- 1. Bảng Phân Quyền
IF NOT EXISTS (SELECT *
FROM sys.foreign_keys
WHERE name = 'FK_PhanQuyen_NhomQuyen')
    ALTER TABLE PhanQuyen ADD CONSTRAINT FK_PhanQuyen_NhomQuyen FOREIGN KEY (MaNQ) REFERENCES NhomQuyen(MaNQ);

IF NOT EXISTS (SELECT *
FROM sys.foreign_keys
WHERE name = 'FK_PhanQuyen_Quyen')
    ALTER TABLE PhanQuyen ADD CONSTRAINT FK_PhanQuyen_Quyen FOREIGN KEY (MaQuyen) REFERENCES Quyen(MaQuyen);

-- 2. Bảng Tài Khoản
IF NOT EXISTS (SELECT *
FROM sys.foreign_keys
WHERE name = 'FK_TaiKhoan_NhomQuyen')
    ALTER TABLE TaiKhoan ADD CONSTRAINT FK_TaiKhoan_NhomQuyen FOREIGN KEY (maNQ) REFERENCES NhomQuyen(MaNQ);

IF NOT EXISTS (SELECT *
FROM sys.foreign_keys
WHERE name = 'FK_TaiKhoan_NhanVien')
    ALTER TABLE TaiKhoan ADD CONSTRAINT FK_TaiKhoan_NhanVien FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV);

-- 3. Bảng Sản Phẩm
IF NOT EXISTS (SELECT *
FROM sys.foreign_keys
WHERE name = 'FK_SanPham_DanhMuc')
    ALTER TABLE SanPham ADD CONSTRAINT FK_SanPham_DanhMuc FOREIGN KEY (MaDM) REFERENCES DanhMuc(MaDM);

IF NOT EXISTS (SELECT *
FROM sys.foreign_keys
WHERE name = 'FK_SanPham_NhaCungCap')
    ALTER TABLE SanPham ADD CONSTRAINT FK_SanPham_NhaCungCap FOREIGN KEY (MaNCC) REFERENCES NhaCungCap(MaNCC);

-- 4. Bảng Size
IF NOT EXISTS (SELECT *
FROM sys.foreign_keys
WHERE name = 'FK_Size_SanPham')
    ALTER TABLE Size ADD CONSTRAINT FK_Size_SanPham FOREIGN KEY (MaSP) REFERENCES SanPham(MaSP);

-- 5. Bảng Công Thức & Chi Tiết Công Thức
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

-- 6. Bảng Khách Hàng
IF NOT EXISTS (SELECT *
FROM sys.foreign_keys
WHERE name = 'FK_KhachHang_HangThanhVien')
    ALTER TABLE KhachHang ADD CONSTRAINT FK_KhachHang_HangThanhVien FOREIGN KEY (MaHang) REFERENCES HangThanhVien(MaHang);

-- 7. Bảng Hóa Đơn & Chi Tiết Hóa Đơn
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

-- 8. Quản Lý Nhập Sản Phẩm
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
WHERE name = 'FK_LoSanPham_PhieuNhapSP')
    ALTER TABLE LoSanPham ADD CONSTRAINT FK_LoSanPham_PhieuNhapSP FOREIGN KEY (MaPN) REFERENCES PhieuNhapSanPham(MaPN);

IF NOT EXISTS (SELECT *
FROM sys.foreign_keys
WHERE name = 'FK_LoSanPham_SanPham')
    ALTER TABLE LoSanPham ADD CONSTRAINT FK_LoSanPham_SanPham FOREIGN KEY (MaSP) REFERENCES SanPham(MaSP);

-- 9. Quản Lý Nhập Nguyên Liệu
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
WHERE name = 'FK_LoNguyenLieu_PhieuNhapNL')
    ALTER TABLE LoNguyenLieu ADD CONSTRAINT FK_LoNguyenLieu_PhieuNhapNL FOREIGN KEY (MaPN) REFERENCES PhieuNhapNguyenLieu(MaPN);

IF NOT EXISTS (SELECT *
FROM sys.foreign_keys
WHERE name = 'FK_LoNguyenLieu_NguyenLieu')
    ALTER TABLE LoNguyenLieu ADD CONSTRAINT FK_LoNguyenLieu_NguyenLieu FOREIGN KEY (MaNL) REFERENCES NguyenLieu(MaNL);

-- 10. Quản Lý Hủy (Sản Phẩm & Nguyên Liệu)
IF NOT EXISTS (SELECT *
FROM sys.foreign_keys
WHERE name = 'FK_PhieuHuySP_NhanVien')
    ALTER TABLE PhieuHuySanPham ADD CONSTRAINT FK_PhieuHuySP_NhanVien FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV);

IF NOT EXISTS (SELECT *
FROM sys.foreign_keys
WHERE name = 'FK_CTPHSP_PhieuHuy')
    ALTER TABLE ChiTietPhieuHuySanPham ADD CONSTRAINT FK_CTPHSP_PhieuHuy FOREIGN KEY (MaPH) REFERENCES PhieuHuySanPham(MaPH);

IF NOT EXISTS (SELECT *
FROM sys.foreign_keys
WHERE name = 'FK_CTPHSP_LoSP')
    ALTER TABLE ChiTietPhieuHuySanPham ADD CONSTRAINT FK_CTPHSP_LoSP FOREIGN KEY (MaLo) REFERENCES LoSanPham(MaLoSP);

IF NOT EXISTS (SELECT *
FROM sys.foreign_keys
WHERE name = 'FK_PhieuHuyNL_NhanVien')
    ALTER TABLE PhieuHuyNguyenLieu ADD CONSTRAINT FK_PhieuHuyNL_NhanVien FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV);

IF NOT EXISTS (SELECT *
FROM sys.foreign_keys
WHERE name = 'FK_CTPHNL_PhieuHuy')
    ALTER TABLE ChiTietPhieuHuyNguyenLieu ADD CONSTRAINT FK_CTPHNL_PhieuHuy FOREIGN KEY (MaPH) REFERENCES PhieuHuyNguyenLieu(MaPH);

IF NOT EXISTS (SELECT *
FROM sys.foreign_keys
WHERE name = 'FK_CTPHNL_LoNL')
    ALTER TABLE ChiTietPhieuHuyNguyenLieu ADD CONSTRAINT FK_CTPHNL_LoNL FOREIGN KEY (MaLo) REFERENCES LoNguyenLieu(MaLoNL);

-- 11. Bảng Kiểm Kê
IF NOT EXISTS (SELECT *
FROM sys.foreign_keys
WHERE name = 'FK_PhieuKiemKe_NhanVien')
    ALTER TABLE PhieuKiemKe ADD CONSTRAINT FK_PhieuKiemKe_NhanVien FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV);

-- 12. Bảng Chi Tiết Nhà Cung Cấp
IF NOT EXISTS (SELECT *
FROM sys.foreign_keys
WHERE name = 'FK_CTNCC_NhaCungCap')
    ALTER TABLE ChiTietNhaCungCap ADD CONSTRAINT FK_CTNCC_NhaCungCap FOREIGN KEY (MaNCC) REFERENCES NhaCungCap(MaNCC);