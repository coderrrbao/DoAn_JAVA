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
        ('TK02', 'NV02', 'quanly1', '123456', 'QL', N'Đang hoạt động', 1),
        ('TK03', 'NV03', 'kho1', '123456', 'NVK', N'Đang hoạt động', 1),
        ('TK04', 'NV04', 'banhang1', '123456', 'NVBH', N'Đang hoạt động', 1),
        ('TK05', 'NV09', 'quanly2', '123456', 'QL', N'Đang hoạt động', 1)
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
        ('DM05', N'Sinh Tố & Nước Ép', 1),
        ('DM06', N'Đá Xay', 1),
        ('DM07', N'Trà Trái Cây', 1),
        ('DM08', N'Sữa Chua', 1),
        ('DM10', N'Topping', 1)
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
        ('NCC04', N'Thực Phẩm Ân Nam', '0283555666', N'Bình Thạnh, TP.HCM', 1),
        ('NCC05', N'Nguyên Liệu Pha Chế Việt', '0909123123', N'Quận 10, TP.HCM', 1)
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
        ('SP01', N'Cà Phê Đen Đá', 'DM01', 25000, N'Pha chế', '/assets/img/SP01.png', 500, 10, 1, N'Đã xác nhận'),
        ('SP02', N'Cà Phê Sữa Đá', 'DM01', 30000, N'Pha chế', '/assets/img/SP02.png', 500, 10, 1, N'Đã xác nhận'),
        ('SP03', N'Bạc Xỉu', 'DM01', 35000, N'Pha chế', '/assets/img/SP03.png', 500, 10, 1, N'Đã xác nhận'),
        ('SP04', N'Trà Sữa Truyền Thống', 'DM02', 35000, N'Pha chế', '/assets/img/SP04.png', 500, 10, 1, N'Đã xác nhận'),
        ('SP05', N'Trà Sữa Thái Xanh', 'DM02', 40000, N'Pha chế', '/assets/img/SP05.png', 500, 10, 1, N'Đã xác nhận'),
        ('SP06', N'Trà Đào Cam Sả', 'DM07', 45000, N'Pha chế', '/assets/img/SP06.png', 700, 10, 1, N'Đã xác nhận'),
        ('SP07', N'Sinh Tố Bơ', 'DM05', 50000, N'Pha chế', '/assets/img/SP07.png', 500, 5, 1, N'Đã xác nhận'),
        ('SP08', N'Nước Ép Cam', 'DM05', 45000, N'Pha chế', '/assets/img/SP08.png', 350, 10, 1, N'Đã xác nhận'),
        ('SP09', N'Matcha Đá Xay', 'DM06', 55000, N'Pha chế', '/assets/img/SP09.png', 500, 5, 1, N'Đã xác nhận'),
        ('SP10', N'Sữa Chua Trân Châu', 'DM08', 40000, N'Pha chế', '/assets/img/SP10.png', 350, 10, 1, N'Đã xác nhận'),

        -- 10 Món Có Sẵn (Đóng lon/chai)
        ('SP11', N'Pepsi Lon', 'DM03', 15000, N'Có sẵn', '/assets/img/SP11.png', 330, 20, 1, N'Đã xác nhận'),
        ('SP12', N'Coca Cola Lon', 'DM03', 15000, N'Có sẵn', '/assets/img/SP12.png', 330, 20, 1, N'Đã xác nhận'),
        ('SP13', N'7Up Lon', 'DM03', 15000, N'Có sẵn', '/assets/img/SP13.png', 330, 20, 1, N'Đã xác nhận'),
        ('SP14', N'Sting Dâu', 'DM03', 18000, N'Có sẵn', '/assets/img/SP14.png', 330, 15, 1, N'Đã xác nhận'),
        ('SP15', N'Redbull Thái', 'DM03', 20000, N'Có sẵn', '/assets/img/SP15.png', 250, 15, 1, N'Đã xác nhận'),
        ('SP16', N'Nước Suối Dasani', 'DM03', 10000, N'Có sẵn', '/assets/img/SP16.png', 500, 30, 1, N'Đã xác nhận'),
        ('SP17', N'Mirinda Cam', 'DM03', 15000, N'Có sẵn', '/assets/img/SP17.png', 330, 20, 1, N'Đã xác nhận'),
        ('SP18', N'Mountain Dew', 'DM03', 18000, N'Có sẵn', '/assets/img/SP18.png', 330, 20, 1, N'Đã xác nhận'),
        ('SP19', N'Nước Khoáng Revive', 'DM03', 20000, N'Có sẵn', '/assets/img/SP19.png', 500, 20, 1, N'Đã xác nhận'),
        ('SP20', N'Nước Thể Thao Aquarius', 'DM03', 20000, N'Có sẵn', '/assets/img/SP20.png', 500, 20, 1, N'Đã xác nhận'),

        -- 10 Món Nước Bổ Sung Thêm (Để đủ 30 SP)
        ('SP21', N'Trà Sữa Khoai Môn', 'DM02', 45000, N'Pha chế', '/assets/img/SP21.png', 500, 10, 1, N'Đã xác nhận'),
        ('SP22', N'Trà Vải Hạt Chia', 'DM07', 45000, N'Pha chế', '/assets/img/SP22.png', 700, 10, 1, N'Đã xác nhận'),
        ('SP23', N'Trà Dâu Tây Tươi', 'DM07', 45000, N'Pha chế', '/assets/img/SP23.png', 700, 10, 1, N'Đã xác nhận'),
        ('SP24', N'Sữa Chua Dẻo', 'DM08', 25000, N'Có sẵn', '/assets/img/SP24.png', 150, 15, 1, N'Đã xác nhận'),
        ('SP25', N'Sinh Tố Dâu', 'DM05', 55000, N'Pha chế', '/assets/img/SP25.png', 500, 5, 1, N'Đã xác nhận'),
        ('SP26', N'Nước Ép Dưa Hấu', 'DM05', 40000, N'Pha chế', '/assets/img/SP26.png', 350, 10, 1, N'Đã xác nhận'),
        ('SP27', N'Nước Trái Cây Nutriboost', 'DM03', 20000, N'Có sẵn', '/assets/img/SP27.png', 300, 15, 1, N'Đã xác nhận'),
        ('SP28', N'Trà Ô Long Tea+', 'DM03', 18000, N'Có sẵn', '/assets/img/SP28.png', 450, 20, 1, N'Đã xác nhận'),
        ('SP29', N'Trà Xanh Không Độ', 'DM03', 18000, N'Có sẵn', '/assets/img/SP29.png', 450, 20, 1, N'Đã xác nhận'),
        ('SP30', N'Cà Phê Muối', 'DM01', 35000, N'Pha chế', '/assets/img/SP30.png', 500, 10, 1, N'Đã xác nhận'),

        -- 3 Món Topping
        ('SP_TOP01', N'Trân Châu Đen', 'DM10', 5000, N'Pha chế', '/assets/img/SP_TOP01.png', 0, 10, 1, N'Đã xác nhận'),
        ('SP_TOP02', N'Thạch Phô Mai', 'DM10', 7000, N'Pha chế', '/assets/img/SP_TOP02.png', 0, 10, 1, N'Đã xác nhận'),
        ('SP_TOP03', N'Kem Cheese', 'DM10', 10000, N'Pha chế', '/assets/img/SP_TOP03.png', 0, 10, 1, N'Đã xác nhận')
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
        ('SZ01_S', 'SP01', N'Size S', 0, 100, 1),
        ('SZ01_M', 'SP01', N'Size M', 15, 125, 1),
        ('SZ01_L', 'SP01', N'Size L', 25, 150, 1),
        ('SZ02_S', 'SP02', N'Size S', 0, 100, 1),
        ('SZ02_M', 'SP02', N'Size M', 15, 125, 1),
        ('SZ02_L', 'SP02', N'Size L', 25, 150, 1),
        ('SZ03_S', 'SP03', N'Size S', 0, 100, 1),
        ('SZ03_M', 'SP03', N'Size M', 15, 125, 1),
        ('SZ03_L', 'SP03', N'Size L', 25, 150, 1),
        ('SZ04_S', 'SP04', N'Size S', 0, 100, 1),
        ('SZ04_M', 'SP04', N'Size M', 15, 125, 1),
        ('SZ04_L', 'SP04', N'Size L', 25, 150, 1),
        ('SZ05_S', 'SP05', N'Size S', 0, 100, 1),
        ('SZ05_M', 'SP05', N'Size M', 15, 125, 1),
        ('SZ05_L', 'SP05', N'Size L', 25, 150, 1),
        ('SZ06_S', 'SP06', N'Size S', 0, 100, 1),
        ('SZ06_M', 'SP06', N'Size M', 15, 125, 1),
        ('SZ06_L', 'SP06', N'Size L', 25, 150, 1),
        -- Sinh tố, Nước ép, Đá xay, Sữa chua (1 Size Tiêu chuẩn)
        ('SZ07_STD', 'SP07', N'Tiêu chuẩn', 0, 100, 1),
        ('SZ08_STD', 'SP08', N'Tiêu chuẩn', 0, 100, 1),
        ('SZ09_STD', 'SP09', N'Tiêu chuẩn', 0, 100, 1),
        ('SZ10_STD', 'SP10', N'Tiêu chuẩn', 0, 100, 1)
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
        ('NL03', N'Đường Cát Trắng', 20000, 'kg', 5, 1),
        ('NL04', N'Sữa Tươi Không Đường', 35000, 'lit', 10, 1),
        ('NL05', N'Bột Trà Sữa Thượng Hạng', 150000, 'kg', 5, 1),
        ('NL06', N'Bột Trà Thái Xanh', 180000, 'kg', 5, 1),
        ('NL07', N'Trà Đào Túi Lọc Cozy', 45000, 'hop', 5, 1),
        ('NL08', N'Syrup Đào Teisseire', 180000, 'chai', 3, 1),
        ('NL09', N'Bơ Sáp Đắk Lắk', 40000, 'kg', 5, 1),
        ('NL10', N'Cam Sành Tươi', 30000, 'kg', 10, 1),
        ('NL11', N'Bột Matcha Nhật Bản', 500000, 'kg', 2, 1),
        ('NL12', N'Trân Châu Đen Đài Loan', 50000, 'kg', 10, 1),
        ('NL13', N'Sữa Chua Vinamilk', 6000, 'hop', 50, 1),
        ('NL14', N'Đá Viên Tinh Khiết', 20000, 'bao', 5, 1),
        ('NL15', N'Ly Nhựa Nắp Cầu 500ml', 800, 'cai', 200, 1)
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
        ('CT02', 'SP02', 1),
        ('CT03', 'SP03', 1),
        ('CT04', 'SP04', 1),
        ('CT05', 'SP05', 1),
        ('CT06', 'SP06', 1),
        ('CT07', 'SP07', 1),
        ('CT08', 'SP08', 1),
        ('CT09', 'SP09', 1),
        ('CT10', 'SP10', 1),
        ('CT11', 'SP_TOP01', 1),
        -- Công thức cho Trân Châu Đen
        ('CT12', 'SP_TOP02', 1),
        -- Công thức cho Thạch Phô Mai
        ('CT13', 'SP_TOP03', 1)
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
        ('CTCT01', 'CT01', 'NL01', 0.025, 1),
        ('CTCT02', 'CT01', 'NL03', 0.015, 1),
        ('CTCT03', 'CT01', 'NL14', 0.200, 1),
        ('CTCT04', 'CT01', 'NL15', 1.000, 1),
        -- Cà phê sữa đá (Cà phê, Sữa đặc, Đá, Ly)
        ('CTCT05', 'CT02', 'NL01', 0.025, 1),
        ('CTCT06', 'CT02', 'NL02', 0.030, 1),
        ('CTCT07', 'CT02', 'NL14', 0.200, 1),
        ('CTCT08', 'CT02', 'NL15', 1.000, 1),
        -- Bạc xỉu (Cà phê, Sữa đặc, Sữa tươi, Đá, Ly)
        ('CTCT09', 'CT03', 'NL01', 0.010, 1),
        ('CTCT10', 'CT03', 'NL02', 0.020, 1),
        ('CTCT11', 'CT03', 'NL04', 0.050, 1),
        ('CTCT12', 'CT03', 'NL14', 0.200, 1),
        ('CTCT13', 'CT03', 'NL15', 1.000, 1),
        -- Trà sữa truyền thống (Bột trà sữa, Sữa đặc, Trân châu, Đá, Ly)
        ('CTCT14', 'CT04', 'NL05', 0.040, 1),
        ('CTCT15', 'CT04', 'NL02', 0.020, 1),
        ('CTCT16', 'CT04', 'NL12', 0.050, 1),
        ('CTCT17', 'CT04', 'NL14', 0.300, 1),
        ('CTCT18', 'CT04', 'NL15', 1.000, 1),
        -- Trà sữa Thái xanh (Bột Thái xanh, Sữa đặc, Trân châu, Đá, Ly)
        ('CTCT19', 'CT05', 'NL06', 0.040, 1),
        ('CTCT20', 'CT05', 'NL02', 0.020, 1),
        ('CTCT21', 'CT05', 'NL12', 0.050, 1),
        ('CTCT22', 'CT05', 'NL14', 0.300, 1),
        ('CTCT23', 'CT05', 'NL15', 1.000, 1),
        -- Trà đào cam sả (Trà đào túi, Syrup đào, Cam tươi, Đá, Ly)
        ('CTCT24', 'CT06', 'NL07', 1.000, 1),
        ('CTCT25', 'CT06', 'NL08', 0.030, 1),
        ('CTCT26', 'CT06', 'NL10', 0.050, 1),
        ('CTCT27', 'CT06', 'NL14', 0.300, 1),
        ('CTCT28', 'CT06', 'NL15', 1.000, 1),
        -- Sinh tố Bơ (Bơ tươi, Sữa đặc, Sữa tươi, Đá, Ly)
        ('CTCT29', 'CT07', 'NL09', 0.150, 1),
        ('CTCT30', 'CT07', 'NL02', 0.030, 1),
        ('CTCT31', 'CT07', 'NL04', 0.050, 1),
        ('CTCT32', 'CT07', 'NL14', 0.200, 1),
        ('CTCT33', 'CT07', 'NL15', 1.000, 1),
        -- Nước ép Cam (Cam tươi, Đường, Đá, Ly)
        ('CTCT34', 'CT08', 'NL10', 0.250, 1),
        ('CTCT35', 'CT08', 'NL03', 0.020, 1),
        ('CTCT36', 'CT08', 'NL14', 0.200, 1),
        ('CTCT37', 'CT08', 'NL15', 1.000, 1),
        -- Matcha Đá xay (Bột Matcha, Sữa tươi, Sữa đặc, Đá, Ly)
        ('CTCT38', 'CT09', 'NL11', 0.015, 1),
        ('CTCT39', 'CT09', 'NL04', 0.060, 1),
        ('CTCT40', 'CT09', 'NL02', 0.020, 1),
        ('CTCT41', 'CT09', 'NL14', 0.300, 1),
        ('CTCT42', 'CT09', 'NL15', 1.000, 1),
        -- Sữa chua trân châu (Sữa chua hộp, Trân châu, Đá, Ly)
        ('CTCT43', 'CT10', 'NL13', 1.000, 1),
        ('CTCT44', 'CT10', 'NL12', 0.050, 1),
        ('CTCT45', 'CT10', 'NL14', 0.150, 1),
        ('CTCT46', 'CT10', 'NL15', 1.000, 1),
        ('CTCT47', 'CT11', 'NL12', 0.050, 1),
        -- 50g trân châu khô
        ('CTCT48', 'CT11', 'NL03', 0.010, 1),
        -- 10g đường

        -- CT12: Thạch Phô Mai (Tạm dùng Sữa tươi, Đường) - Định lượng 1 phần
        ('CTCT49', 'CT12', 'NL04', 0.030, 1),
        -- 30ml sữa tươi
        ('CTCT50', 'CT12', 'NL03', 0.010, 1),
        -- 10g đường

        -- CT13: Kem Cheese (Dùng Sữa tươi, Sữa đặc tạo form) - Định lượng 1 phần
        ('CTCT51', 'CT13', 'NL04', 0.040, 1),
        -- 40ml sữa tươi
        ('CTCT52', 'CT13', 'NL02', 0.015, 1)
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
        ChucVu NVARCHAR(255),
        Anh NVARCHAR(255),
        TrangThai BIT
    )

    INSERT INTO NhanVien
        (MaNV, TenNV, GioiTinh, NgaySinh, SDT, DiaChi, ChucVu, Anh,TrangThai)
    VALUES
        ('NV01', N'Nguyễn Hoài Bảo', N'Nam', '2000-01-01', '0901234567', N'Dĩ An, Bình Dương', N'Admin', '/assets/img/NV01.png', 1),
        ('NV02', N'Phạm Hữu Phú', N'Nam', '1998-02-02', '0902345678', N'Quận 1, TP.HCM', N'Quản lý', '/assets/img/NV02.png', 1),
        ('NV03', N'Lê Huy Hoàng', N'Nam', '1995-05-05', '0903456789', N'Thủ Đức, TP.HCM', N'Nhân viên kho', '/assets/img/NV03.png', 1),
        ('NV04', N'Trần Thị Mai', N'Nữ', '2001-08-15', '0904567890', N'Biên Hòa, Đồng Nai', N'Nhân viên bán hàng', '/assets/img/NV04.png', 1),
        ('NV05', N'Nguyễn Văn An', N'Nam', '1999-12-20', '0905678901', N'Dĩ An, Bình Dương', N'Nhân viên bán hàng', '/assets/img/NV05.png', 1),
        ('NV06', N'Phạm Thị Nụ', N'Nữ', '2002-03-10', '0906789012', N'Quận 9, TP.HCM', N'Nhân viên bán hàng', '/assets/img/NV06.png', 1),
        ('NV07', N'Võ Văn Kiệt', N'Nam', '1997-11-11', '0907890123', N'Thuận An, Bình Dương', N'Nhân viên kho', '/assets/img/NV07.png', 1),
        ('NV08', N'Đặng Thái Sơn', N'Nam', '2000-07-07', '0908901234', N'Tân Bình, TP.HCM', N'Nhân viên bán hàng', '/assets/img/NV08.png', 1),
        ('NV09', N'Trương Ngọc Ánh', N'Nữ', '1996-09-09', '0909012345', N'Quận 3, TP.HCM', N'Quản lý', '/assets/img/NV09.png', 1),
        ('NV10', N'Bùi Tiến Dũng', N'Nam', '2001-04-30', '0910123456', N'Gò Vấp, TP.HCM', N'Nhân viên bán hàng', '/assets/img/NV10.png', 1)
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
        ('CTHD_TK_01', 'HD_TK_01', 'SP02', 'SZ02_M', 2, 30000),
        ('CTHD_TK_02', 'HD_TK_01', 'SP04', 'SZ04_L', 1, 35000),
        ('CTHD_TK_03', 'HD_TK_02', 'SP07', 'SZ07_STD', 1, 50000),
        ('CTHD_TK_04', 'HD_TK_03', 'SP05', 'SZ05_M', 3, 40000),
        ('CTHD_TK_05', 'HD_TK_04', 'SP12', NULL, 2, 15000),
        ('CTHD_TK_06', 'HD_TK_05', 'SP06', 'SZ06_L', 5, 45000),
        ('CTHD_TK_07', 'HD_TK_06', 'SP20', NULL, 2, 45000),
        ('CTHD_TK_08', 'HD_TK_07', 'SP09', 'SZ09_STD', 4, 55000),
        ('CTHD_TK_09', 'HD_TK_08', 'SP08', 'SZ08_STD', 2, 45000),
        ('CTHD_TK_10', 'HD_TK_09', 'SP01', 'SZ01_M', 5, 25000)
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
        ('PNSP01', '2024-01-10', 'NV03', 225000000, 'NCC01', N'Nhập hàng Tết đợt 1', N'Đã xác nhận', 1),
        ('PNSP02', '2024-01-15', 'NV03', 180000000, 'NCC01', N'Nhập hàng Tết đợt 2', N'Đã xác nhận', 1),
        ('PNSP03', '2024-02-01', 'NV03', 150000000, 'NCC04', N'Nhập kho đầu tháng', N'Đã xác nhận', 1),
        ('PNSP04', '2024-02-15', 'NV03', 200000000, 'NCC01', N'Nhập bổ sung', N'Đã xác nhận', 1),
        ('PNSP05', '2023-05-01', 'NV03', 45000000, 'NCC03', N'Nhập hàng cũ (Đã hết hạn)', N'Đã xác nhận', 1),
        ('PNSP06', '2025-10-01', 'NV03', 350000000, 'NCC01', N'Nhập hàng chuẩn bị cuối năm', N'Đã xác nhận', 1),
        ('PNSP07', '2026-01-15', 'NV03', 280000000, 'NCC04', N'Nhập kho đầu năm 2026', N'Đã xác nhận', 1)
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
        ('LOSP01', 'PNSP01', 'SP11', 5000, '2024-01-10', '2024-01-01', '2025-01-01', 9000, N'Đã xác nhận', 1),
        ('LOSP02', 'PNSP01', 'SP12', 5000, '2024-01-10', '2024-01-01', '2025-01-01', 9000, N'Đã xác nhận', 1),
        ('LOSP03', 'PNSP01', 'SP13', 5000, '2024-01-10', '2024-01-01', '2025-01-01', 9000, N'Đã xác nhận', 1),

        -- PNSP02: Nhập nước tăng lực và nước suối
        ('LOSP04', 'PNSP02', 'SP14', 5000, '2024-01-15', '2024-01-05', '2025-01-05', 12000, N'Đã xác nhận', 1),
        ('LOSP05', 'PNSP02', 'SP15', 3000, '2024-01-15', '2024-01-05', '2025-01-05', 14000, N'Đã xác nhận', 1),
        ('LOSP06', 'PNSP02', 'SP16', 10000, '2024-01-15', '2024-01-10', '2026-01-10', 4000, N'Đã xác nhận', 1),

        -- PNSP03: Nhập nước trái cây, thể thao
        ('LOSP07', 'PNSP03', 'SP17', 3000, '2024-02-01', '2024-01-20', '2025-01-20', 9000, N'Đã xác nhận', 1),
        ('LOSP08', 'PNSP03', 'SP18', 3000, '2024-02-01', '2024-01-20', '2025-01-20', 10000, N'Đã xác nhận', 1),
        ('LOSP09', 'PNSP03', 'SP19', 3000, '2024-02-01', '2024-01-20', '2025-01-20', 12000, N'Đã xác nhận', 1),
        ('LOSP10', 'PNSP03', 'SP20', 3000, '2024-02-01', '2024-01-20', '2025-01-20', 12000, N'Đã xác nhận', 1),

        -- PNSP04: Nhập trà đóng chai & sữa chua dẻo
        ('LOSP11', 'PNSP04', 'SP24', 2000, '2024-02-15', '2024-02-10', '2024-08-10', 15000, N'Đã xác nhận', 1),
        ('LOSP12', 'PNSP04', 'SP27', 4000, '2024-02-15', '2024-02-01', '2025-02-01', 13000, N'Đã xác nhận', 1),
        ('LOSP13', 'PNSP04', 'SP28', 5000, '2024-02-15', '2024-02-05', '2025-02-05', 11000, N'Đã xác nhận', 1),
        ('LOSP14', 'PNSP04', 'SP29', 5000, '2024-02-15', '2024-02-05', '2025-02-05', 11000, N'Đã xác nhận', 1),

        ('LOSP15', 'PNSP05', 'SP11', 1000, '2023-05-01', '2023-04-01', '2024-04-01', 9000, N'Đã xác nhận', 1),
        ('LOSP16', 'PNSP05', 'SP12', 1000, '2023-05-01', '2023-04-01', '2024-04-01', 9000, N'Đã xác nhận', 1),
        ('LOSP17', 'PNSP05', 'SP24', 500, '2023-05-01', '2023-04-20', '2023-10-20', 15000, N'Đã xác nhận', 1),

        -- Các lô chưa hết hạn (HSD năm 2026, 2027)
        ('LOSP18', 'PNSP06', 'SP13', 8000, '2025-10-01', '2025-09-01', '2026-09-01', 9000, N'Đã xác nhận', 1),
        ('LOSP19', 'PNSP06', 'SP14', 5000, '2025-10-01', '2025-09-15', '2026-09-15', 12000, N'Đã xác nhận', 1),
        ('LOSP20', 'PNSP06', 'SP16', 10000, '2025-10-01', '2025-09-20', '2027-09-20', 4000, N'Đã xác nhận', 1),
        ('LOSP21', 'PNSP07', 'SP11', 6000, '2026-01-15', '2026-01-05', '2027-01-05', 9000, N'Đã xác nhận', 1),
        ('LOSP22', 'PNSP07', 'SP12', 6000, '2026-01-15', '2026-01-05', '2027-01-05', 9000, N'Đã xác nhận', 1),
        ('LOSP23', 'PNSP07', 'SP27', 4000, '2026-01-15', '2026-01-10', '2027-01-10', 13000, N'Đã xác nhận', 1),
        ('LOSP24', 'PNSP07', 'SP28', 4000, '2026-01-15', '2026-01-10', '2027-01-10', 11000, N'Đã xác nhận', 1)
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
        ('PNNL01', '2024-01-12', 'NV03', 150000000, 'NCC02', N'Đã xác nhận', N'Nhập trà, cà phê số lượng lớn', 1),
        ('PNNL02', '2024-01-13', 'NV02', 200000000, 'NCC03', N'Đã xác nhận', N'Nhập sữa và chế phẩm sữa', 1),
        ('PNNL03', '2024-01-20', 'NV03', 120000000, 'NCC05', N'Đã xác nhận', N'Nhập topping và phụ liệu', 1),
        ('PNNL04', '2024-02-01', 'NV02', 80000000, 'NCC04', N'Đã xác nhận', N'Nhập trái cây và đá', 1),
        ('PNNL05', '2023-08-10', 'NV02', 85000000, 'NCC03', N'Đã xác nhận', N'Nhập nguyên liệu cũ (Đã hết hạn)', 1),
        ('PNNL06', '2025-11-20', 'NV03', 420000000, 'NCC02', N'Đã xác nhận', N'Nhập kho số lượng lớn cuối năm', 1),
        ('PNNL07', '2026-02-10', 'NV02', 150000000, 'NCC04', N'Đã xác nhận', N'Nhập kho đầu năm 2026', 1),
        ('PNNL08', '2026-03-05', 'NV03', 135000000, 'NCC05', N'Đã xác nhận', N'Nhập nguyên liệu đầu tháng 3/2026 dự trữ bán lễ', 1)
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
        ('LONL01', 'PNNL01', 'NL01', 500.0, '2024-01-12', '2024-01-01', '2025-01-01', 150000, N'Đã xác nhận', 1),
        -- Cà phê (500 kg)
        ('LONL02', 'PNNL01', 'NL03', 1000.0, '2024-01-12', '2024-01-05', '2026-01-05', 16000, N'Đã xác nhận', 1),
        -- Đường (1000 kg)
        ('LONL03', 'PNNL01', 'NL05', 300.0, '2024-01-12', '2024-01-05', '2025-01-05', 120000, N'Đã xác nhận', 1),
        -- Bột trà sữa (300 kg)
        ('LONL04', 'PNNL01', 'NL06', 200.0, '2024-01-12', '2024-01-05', '2025-01-05', 140000, N'Đã xác nhận', 1),
        -- Bột trà Thái (200 kg)
        ('LONL05', 'PNNL01', 'NL07', 500.0, '2024-01-12', '2024-01-05', '2026-01-05', 35000, N'Đã xác nhận', 1),
        -- Trà đào túi lọc (500 hộp)

        -- PNNL02: Sữa các loại
        ('LONL06', 'PNNL02', 'NL02', 2000.0, '2024-01-13', '2024-01-01', '2025-01-01', 20000, N'Đã xác nhận', 1),
        -- Sữa đặc (2000 hộp)
        ('LONL07', 'PNNL02', 'NL04', 3000.0, '2024-01-13', '2024-01-10', '2024-07-10', 28000, N'Đã xác nhận', 1),
        -- Sữa tươi (3000 lít)
        ('LONL08', 'PNNL02', 'NL13', 5000.0, '2024-01-13', '2024-01-10', '2024-03-10', 5000, N'Đã xác nhận', 1),
        -- Sữa chua (5000 hộp)

        -- PNNL03: Topping, Syrup, Ly nhựa, Matcha
        ('LONL09', 'PNNL03', 'NL08', 300.0, '2024-01-20', '2024-01-01', '2025-01-01', 150000, N'Đã xác nhận', 1),
        -- Syrup đào (300 chai)
        ('LONL10', 'PNNL03', 'NL11', 100.0, '2024-01-20', '2023-12-15', '2024-12-15', 400000, N'Đã xác nhận', 1),
        -- Matcha (100 kg)
        ('LONL11', 'PNNL03', 'NL12', 1500.0, '2024-01-20', '2024-01-15', '2024-07-15', 35000, N'Đã xác nhận', 1),
        -- Trân châu (1500 kg)
        ('LONL12', 'PNNL03', 'NL15', 100000.0, '2024-01-20', '2024-01-01', '2030-01-01', 500, N'Đã xác nhận', 1),
        -- Ly nhựa (100,000 cái)

        -- PNNL04: Trái cây tươi & Đá
        ('LONL13', 'PNNL04', 'NL09', 200.0, '2024-02-01', '2024-01-30', '2024-02-15', 30000, N'Đã xác nhận', 1),
        -- Bơ sáp (200 kg)
        ('LONL14', 'PNNL04', 'NL10', 300.0, '2024-02-01', '2024-01-30', '2024-02-15', 20000, N'Đã xác nhận', 1),
        -- Cam sành (300 kg)
        ('LONL15', 'PNNL04', 'NL14', 2000.0, '2024-02-01', '2024-02-01', '2024-02-05', 15000, N'Đã xác nhận', 1),
        ('LONL16', 'PNNL05', 'NL04', 1000, '2023-08-10', '2023-08-01', '2024-02-01', 28000, N'Đã xác nhận', 1),
        ('LONL17', 'PNNL05', 'NL09', 100, '2023-08-10', '2023-08-05', '2023-08-20', 30000, N'Đã xác nhận', 1),
        ('LONL18', 'PNNL05', 'NL13', 2000, '2023-08-10', '2023-08-01', '2023-10-01', 5000, N'Đã xác nhận', 1),

        -- Các lô nguyên liệu chưa hết hạn (HSD 2026, 2027, 2028)
        ('LONL19', 'PNNL06', 'NL01', 800, '2025-11-20', '2025-11-01', '2026-11-01', 150000, N'Đã xác nhận', 1),
        ('LONL20', 'PNNL06', 'NL02', 3000, '2025-11-20', '2025-11-05', '2026-11-05', 20000, N'Đã xác nhận', 1),
        ('LONL21', 'PNNL06', 'NL05', 500, '2025-11-20', '2025-11-10', '2026-11-10', 120000, N'Đã xác nhận', 1),
        ('LONL22', 'PNNL06', 'NL12', 2000, '2025-11-20', '2025-11-15', '2026-05-15', 35000, N'Đã xác nhận', 1),
        ('LONL23', 'PNNL07', 'NL04', 4000, '2026-02-10', '2026-02-05', '2026-08-05', 28000, N'Đã xác nhận', 1),
        ('LONL24', 'PNNL07', 'NL10', 500, '2026-02-10', '2026-02-08', '2026-03-25', 20000, N'Đã xác nhận', 1),
        ('LONL25', 'PNNL07', 'NL03', 2000, '2026-02-10', '2026-01-20', '2028-01-20', 16000, N'Đã xác nhận', 1),
        -- Bột Trà Sữa Thượng Hạng (Hạn sử dụng: 15/02/2027)
        ('LONL26', 'PNNL08', 'NL05', 500.0, '2026-03-05', '2026-02-15', '2027-02-15', 120000, N'Đã xác nhận', 1),

        -- Syrup Đào Teisseire (Hạn sử dụng: 01/02/2028)
        ('LONL27', 'PNNL08', 'NL08', 200.0, '2026-03-05', '2026-02-01', '2028-02-01', 150000, N'Đã xác nhận', 1),

        -- Bột Matcha Nhật Bản (Hạn sử dụng: 20/02/2027)
        ('LONL28', 'PNNL08', 'NL11', 100.0, '2026-03-05', '2026-02-20', '2027-02-20', 400000, N'Đã xác nhận', 1),

        -- Ly Nhựa Nắp Cầu 500ml (Hạn sử dụng: 10/01/2030)
        ('LONL29', 'PNNL08', 'NL15', 10000.0, '2026-03-05', '2026-01-10', '2030-01-10', 500, N'Đã xác nhận', 1)
-- Đá viên (2000 bao)
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

    INSERT INTO ChiTietNhaCungCap
        (MaCTNCC, MaNCC, LoaiDoiTuong, MaDoiTuong, GiaNhap)
    VALUES
        ('CT001', 'NCC01', N'Nguyên liệu', 'NL01', 180000),
        -- Sửa mã NCC
        ('CT002', 'NCC01', N'Nguyên liệu', 'NL05', 450000),
        ('CT003', 'NCC02', N'Nguyên liệu', 'NL14', 35000),
        -- Sửa mã NCC
        ('CT004', 'NCC02', N'Nguyên liệu', 'NL03', 12000)

    INSERT INTO ChiTietNhaCungCap
        (MaCTNCC, MaNCC, LoaiDoiTuong, MaDoiTuong, GiaNhap)
    VALUES
        ('CT005', 'NCC03', N'Sản phẩm', 'SP01', 9000),
        -- Sửa mã NCC
        ('CT006', 'NCC03', N'Sản phẩm', 'SP27', 9000),
        ('CT007', 'NCC04', N'Sản phẩm', 'SP15', 35000),
        -- Sửa mã NCC và đổi SP45 -> SP15
        ('CT008', 'NCC04', N'Sản phẩm', 'SP17', 22000)
    -- Sửa mã NCC và đổi SP47 -> SP17

    INSERT INTO ChiTietNhaCungCap
        (MaCTNCC, MaNCC, LoaiDoiTuong, MaDoiTuong, GiaNhap)
    VALUES
        ('CT009', 'NCC05', N'Nguyên liệu', 'NL04', 28000),
        -- Sửa mã NCC
        ('CT010', 'NCC05', N'Sản phẩm', 'SP30', 6000)

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