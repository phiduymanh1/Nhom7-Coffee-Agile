CREATE DATABASE DUAN11
GO

USE DUAN11
GO

-- Bảng Đăng Nhập=======================================================================
CREATE TABLE login(
    ID int PRIMARY KEY,
    TaiKhoan NVARCHAR(50) NOT NULL,
    MatKhau NVARCHAR(50) NOT NULL,
    ChucVu NVARCHAR(50) NOT NULL
);
GO

-- Bảng Nhân Viên=======================================================================
CREATE TABLE NhanVien(
    MaNV NVARCHAR(50) PRIMARY KEY,
    TenNV NVARCHAR(50) NOT NULL,
    GioiTinh NVARCHAR(3) NOT NULL,
    NgaySinh DATE NOT NULL,
    SDT NVARCHAR(50) NOT NULL,
    ChucVu NVARCHAR(50) NOT NULL,
    Luong FLOAT NOT NULL,
    QueQuan NVARCHAR(50) NOT NULL,
    ID int NOT NULL,
    FOREIGN KEY (ID) REFERENCES login(ID)
);
GO

-- Bảng Voucher =======================================================================
CREATE TABLE Voucher(
    MaVoucher NVARCHAR(50) PRIMARY KEY,
    TenV NVARCHAR(50) NOT NULL,
    SoTienGiamVND FLOAT,
    SoTienGiamPhanTram NVARCHAR(50),
    NgayBatDau DATE NOT NULL,
    NgayKetThuc DATE NOT NULL,
    TrangThai NVARCHAR(50) NOT NULL
);
GO

-- Bảng Khách Hàng =======================================================================
CREATE TABLE KhachHang(
    MaKH NVARCHAR(50) PRIMARY KEY,
    TenKH NVARCHAR(50) NOT NULL,
    GioiTinh NVARCHAR(3),
    DiaChi NVARCHAR(50),
    SDT NVARCHAR(50) NOT NULL,
    LoaiKhacHang NVARCHAR(50),
    DiemThanhVien INT
);
GO

-- Bảng Sản Phẩm =======================================================================
CREATE TABLE SanPham(
    MaSP NVARCHAR(50) PRIMARY KEY,
    TenSP NVARCHAR(50) NOT NULL,
	SoLuong INT not null,
    GiaTien FLOAT NOT NULL, 
    TrangThai NVARCHAR(50) NOT NULL
);
go
create table thanhToan(
	thanhToan_id int identity(1,1) primary key,
	hinhThucThanhToan nvarchar(100) not null
)
GO

-- Bảng Hóa Đơn =======================================================================
CREATE TABLE HoaDon (
    MaHD NVARCHAR(50) PRIMARY KEY,
    MaNV NVARCHAR(50) NOT NULL,
    SDT NVARCHAR(50),
    NgayTao DATETIME DEFAULT GETDATE(),
    TongTien DECIMAL(18, 2),
    TrangThai NVARCHAR(50),
    MaKH NVARCHAR(50),
    MaVoucher NVARCHAR(50),
	thanhToan_id int,
    DiaChi NVARCHAR(50),
    FOREIGN KEY (MaKH) REFERENCES KhachHang(MaKH),
    FOREIGN KEY (MaVoucher) REFERENCES Voucher(MaVoucher),
    FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV),
	FOREIGN KEY (thanhToan_id) REFERENCES thanhToan(thanhToan_id)
);

GO

-- Bảng Hóa Đơn Chi Tiết =======================================================================
CREATE TABLE HoaDonChiTiet(
    MaHD NVARCHAR(50) NOT NULL,
    MaSP NVARCHAR(50) NOT NULL,
    SoLuongMua INT NOT NULL,
    GiaTien FLOAT NOT NULL,
    ThanhTien decimal(18, 2) not null,
    TrangThai NVARCHAR(50) NOT NULL,
    GhiChu NVARCHAR(50),
    PRIMARY KEY (MaHD, MaSP),
    FOREIGN KEY (MaSP) REFERENCES SanPham(MaSP),
    FOREIGN KEY (MaHD) REFERENCES HoaDon(MaHD)
);
GO
INSERT INTO thanhToan (hinhThucThanhToan) VALUES
( N'Tiền mặt'),
( N'Chuyển khoản');
GO

-- Insert into login
INSERT INTO login (ID, TaiKhoan, MatKhau, ChucVu)
VALUES (1, 'quanly', '12345', 'QuanLy'),
       (2, 'nhanvien1', '12345', 'NhanVien');
GO

-- Insert into Voucher
INSERT INTO Voucher (MaVoucher, TenV, SoTienGiamVND, SoTienGiamPhanTram, NgayBatDau, NgayKetThuc, TrangThai)
VALUES 
    ('V001', 'Voucher 1', 1000, '10%', '2024-07-01', '2024-12-31', 'Hoat Dong'),
    ('V002', 'Voucher 2', 2000, '20%', '2024-07-01', '2024-12-31', 'Hoat Dong'),
    ('V003', 'Voucher 3', 1500, '15%', '2024-08-01', '2024-12-31', 'Khong Hoat Dong'),
    ('V004', 'Voucher 4', 3000, '25%', '2024-07-01', '2024-12-31', 'Hoat Dong'),
    ('V005', 'Voucher 5', 500, '5%', '2024-09-01', '2024-12-31', 'Khong Hoat Dong'),
    ('V006', 'Voucher 6', 1000, '10%', '2024-07-01', '2024-12-31', 'Hoat Dong'),
    ('V007', 'Voucher 7', 2000, '20%', '2024-10-01', '2024-12-31', 'Khong Hoat Dong'),
    ('V008', 'Voucher 8', 1500, '15%', '2024-07-01', '2024-11-30', 'Hoat Dong'),
    ('V009', 'Voucher 9', 2500, '25%', '2024-11-01', '2024-12-31', 'Khong Hoat Dong'),
    ('V010', 'Voucher 10', 5000, '5%', '2024-07-01', '2024-12-31', 'Hoat Dong');
GO

-- Insert into KhachHang
INSERT INTO KhachHang (MaKH, TenKH, GioiTinh, DiaChi, SDT, LoaiKhacHang, DiemThanhVien)
VALUES 
    ('KH000', N'Khach Hang Ban Le', '', '', '', '', NULL),
    ('KH001', N'Nguyễn Văn Nam', 'Nam', 'Hà Nội', '0987654321', 'VIP', 10000),
    ('KH002', N'Nguyễn Thị Cao', 'Nu', 'Nghệ An', '0956743341', 'Thuong', 5000),
    ('KH003', N'Hoàng Xuân Quân 3', 'Nam', 'Hà Tĩnh', '0356229816', 'VIP', 10000),
    ('KH004', N'Trần Văn Lợi', 'Nu', 'Cao Bằng', '0392055785', 'Thuong', 5000),
    ('KH005', N'Nguyễn Anh Dũng', 'Nam', 'Bắc Cạn', '0946214478', 'VIP', 10000),
    ('KH006', N'Trần Thị Xuân', 'Nu', 'Lai Châu', '0946345267', 'Thuong', 5000),
    ('KH007', N'Lê Quý Lâm', 'Nam', 'Hà Nội', '0946214578', 'VIP', 10000),
    ('KH008', N'Nguyễn Thị Ánh Hồng', 'Nu', 'Hà Nội', '0384453418', 'Thuong', 5000),
    ('KH009', N'Nguyễn Đức Phương Nam', 'Nam', 'Hà Nội', '0987657453', 'VIP', 10000),
    ('KH010', N'Bùi Thị Thảo', 'Nu', 'Phú Thọ', '0354678432', 'Thuong', 5000);
GO

-- Insert into NhanVien
INSERT INTO NhanVien (MaNV, TenNV, GioiTinh, NgaySinh, SDT, ChucVu, Luong, QueQuan, ID)
VALUES 
    ('NV001', N'Nguyễn Văn A', 'Nam', '1990-01-01', '0123456789', 'QuanLy', 5000000, 'Hà Nội', 1),
    ('NV002', N'Trần Thị B', 'Nu', '1992-02-02', '0123456790', 'NhanVien', 6000000, 'Hà Nội', 2),
    ('NV003', N'Lê Văn C', 'Nam', '1991-03-03', '0123456791', 'NhanVien', 5500000, 'Phú Thọ', 2),
    ('NV004', N'Phùng Thị D', 'Nu', '1993-04-04', '0123456792', 'NhanVien', 6500000, 'Hà Nội', 2),
    ('NV005', N'Lương Văn E', 'Nam', '1989-05-05', '0123456793', 'NhanVien', 5200000, 'Hà Nội', 2),
    ('NV006', N'Phan Ánh G', 'Nu', '1994-06-06', '0123456794', 'NhanVien', 6200000, 'Nam Định', 2),
    ('NV007', N'Đặng Văn Chung', 'Nam', '1995-07-07', '0123456795', 'NhanVien', 5700000, 'Hà Nội', 2),
    ('NV008', N'Lương Thị Nữ', 'Nu', '1988-08-08', '0123456796', 'NhanVien', 6300000, 'Bắc Ninh', 2),
    ('NV009', N'Cao Văn Hảo', 'Nam', '1996-09-09', '0123456797', 'NhanVien', 5400000, 'Hà Nội', 2),
    ('NV010', N'Phùng Thị Kim', 'Nu', '1997-10-10', '0123456798', 'NhanVien', 6400000, 'Hưng Yên', 2);
GO

-- Insert into SanPham
INSERT INTO SanPham (MaSP, TenSP, SoLuong, GiaTien, TrangThai)
VALUES 
    ('SP001', N'Cà Phê Đá', 100, 10000, N'Còn hàng' ),
    ('SP002', N'Bạc Xỉu', 100, 20000,N'Còn hàng' ),
    ('SP003', N'Cà phê Cherry', 100, 15000, N'Còn hàng' ),
    ('SP004', N'Cà phê Moka', 100, 30000, N'Còn hàng' ),
    ('SP005', N'Cà phê Latte', 100, 50000, N'Còn hàng' ),
    ('SP006', N'Cà phê Arabica', 100, 10000,N'Còn hàng' ),
    ('SP007', N'Cà phê Robusta', 100, 20000, N'Còn hàng' ),
    ('SP008', N'Cà phê Robusta', 100, 15000, N'Còn hàng' ),
    ('SP009', N'Cà phê Cappuccino', 100, 30000, N'Còn hàng' );

-- Insert into HoaDon
GO

-- Insert into HoaDon
INSERT INTO HoaDon (MaHD, MaNV, SDT, NgayTao, TongTien, TrangThai, MaKH, MaVoucher,thanhToan_id, DiaChi)
VALUES 
    ('HD001', 'NV001', '0123456789', '2024-07-01', 200000,  N'Hoàn thành', 'KH001', 'V001',1, 'Dia Chi 1'),
    ('HD002', 'NV002', '0123456790', '2024-07-02', 300000,  N'Hoàn thành', 'KH002', 'V002',1, 'Dia Chi 2'),
    ('HD003', 'NV001', '0123456791', '2024-07-03', 150000,  N'Hoàn thành', 'KH003', 'V003',1, 'Dia Chi 3'),
    ('HD004', 'NV002', '0123456792', '2024-07-04', 25000,  N'Hoàn thành', 'KH004', 'V004',1, 'Dia Chi 4'),
    ('HD005', 'NV001', '0123456793', '2024-07-05', 350000,  N'Hoàn thành', 'KH005', 'V005',1, 'Dia Chi 5'),
    ('HD006', 'NV002', '0123456794', '2024-07-06', 100000, N'Hoàn thành', 'KH006', 'V006',2, 'Dia Chi 6'),
    ('HD007', 'NV001', '0123456795', '2024-07-07', 200000,  N'Hoàn thành', 'KH007', 'V007',1, 'Dia Chi 7'),
    ('HD008', 'NV002', '0123456796', '2024-07-08', 150000,  N'Hoàn thành', 'KH008', 'V008',1, 'Dia Chi 8'),
    ('HD009', 'NV001', '0123456797', '2024-07-09', 300000,  N'Hoàn thành', 'KH009', 'V009',2, 'Dia Chi 9'),
    ('HD010', 'NV002', '0123456798', '2024-07-10', 50000,  N'Hoàn thành', 'KH010', 'V010',1, 'Dia Chi 10');
GO

-- Insert into HoaDonChiTiet
INSERT INTO HoaDonChiTiet (MaHD, MaSP, SoLuongMua, GiaTien, ThanhTien, TrangThai, GhiChu)
VALUES 
    ('HD001', 'SP001', 1, 10000, 10000,  N'Hoàn thành', 'Ghi Chu 1'),
    ('HD002', 'SP002', 2, 20000, 40000,  N'Hoàn thành', 'Ghi Chu 2'),
    ('HD003', 'SP003', 3, 15000, 45000,  N'Hoàn thành', 'Ghi Chu 3'),
    ('HD004', 'SP004', 1, 30000, 30000,  N'Hoàn thành', 'Ghi Chu 4'),
    ('HD005', 'SP005', 2, 50000, 100000,  N'Hoàn thành', 'Ghi Chu 5'),
    ('HD006', 'SP006', 1, 10000, 10000,  N'Hoàn thành', 'Ghi Chu 6'),
    ('HD007', 'SP007', 3, 20000, 60000, N'Hoàn thành', 'Ghi Chu 7'),
    ('HD008', 'SP008', 2, 15000, 30000,  N'Hoàn thành', 'Ghi Chu 8'),
    ('HD009', 'SP009', 1, 30000, 30000,  N'Hoàn thành', 'Ghi Chu 9'),
    ('HD010', 'SP001', 2, 10000, 20000,  N'Hoàn thành', 'Ghi Chu 10');
GO


-- Select statements
SELECT * FROM login;
SELECT * FROM Voucher;
SELECT * FROM KhachHang;
SELECT * FROM NhanVien;
SELECT * FROM SanPham;
SELECT * FROM HoaDon;
SELECT * FROM HoaDonChiTiet;

GO
CREATE OR ALTER PROCEDURE HUYHOADON
    @MAHOADON NVARCHAR(50),
    @MAHD NVARCHAR(50), -- Mã hóa đơn chi tiết cần phải huỷ
    @MASP NVARCHAR(50)
AS
BEGIN
    DECLARE @SOLUONG INT;

    -- Lấy số lượng sản phẩm từ bảng HoaDonChiTiet
    SELECT @SOLUONG = SoLuongMua 
    FROM HoaDonChiTiet 
    WHERE MaHD = @MAHOADON AND MaSP = @MASP AND TrangThai = N'Chờ thanh toán';

    -- Cập nhật số lượng sản phẩm trong bảng SanPham
    UPDATE SanPham
    SET SoLuong = SoLuong + @SOLUONG,
        TrangThai = CASE 
                        WHEN SoLuong + @SOLUONG > 0 THEN N'Còn hàng' 
                        ELSE N'Hết hàng' 
                    END
    WHERE MaSP = @MASP;

    -- Cập nhật trạng thái hóa đơn
    UPDATE HoaDon
    SET TrangThai = N'Đã huỷ'
    WHERE MaHD = @MAHOADON;

    -- Cập nhật trạng thái chi tiết hóa đơn
    UPDATE HoaDonChiTiet
    SET TrangThai = N'Đã huỷ'
    WHERE MaHD = @MAHD AND MaSP = @MASP AND TrangThai = N'Chờ thanh toán';
END;
GO






GO
CREATE OR ALTER PROCEDURE ThemSanPhamVaoHoaDonChiTiet
    @MaHoaDon NVARCHAR(50),
    @MaSanPham NVARCHAR(50),
    @SoLuong INT
AS
BEGIN
    -- Khai báo các biến
    DECLARE @GiaTien FLOAT;                      -- Giá tiền của sản phẩm
    DECLARE @ThanhTien DECIMAL(18,2);           -- Thành tiền
    DECLARE @TrangThaiHDCT NVARCHAR(50);        -- Trạng thái của chi tiết hóa đơn
    DECLARE @SoLuongTon INT;                    -- Số lượng tồn kho

    -- Lấy giá tiền và số lượng tồn kho của sản phẩm từ bảng SanPham
    SELECT @GiaTien = GiaTien, @SoLuongTon = SoLuong
    FROM SanPham
    WHERE MaSP = @MaSanPham;

    -- Kiểm tra số lượng tồn kho có đủ để thêm vào hóa đơn không
    IF @SoLuongTon < @SoLuong
    BEGIN
        RAISERROR ('Số lượng tồn kho không đủ để thêm vào hóa đơn.', 16, 1);
        RETURN;
    END

    -- Đặt trạng thái hóa đơn chi tiết là 'Chờ thanh toán'
    SET @TrangThaiHDCT = N'Chờ thanh toán';

    -- Tính thành tiền = số lượng x giá tiền
    SET @ThanhTien = @SoLuong * @GiaTien;

    -- Thêm sản phẩm vào hóa đơn chi tiết
    IF EXISTS (SELECT 1 FROM HoaDonChiTiet WHERE MaHD = @MaHoaDon AND MaSP = @MaSanPham)
    BEGIN
        -- Nếu sản phẩm đã tồn tại trong hóa đơn chi tiết thì thực hiện cập nhật lại số lượng sản phẩm
        UPDATE HoaDonChiTiet
        SET SoLuongMua = SoLuongMua + @SoLuong,  -- Cập nhật số lượng mua, cộng dồn nếu sản phẩm đã tồn tại
            GiaTien = @GiaTien,
            ThanhTien = SoLuongMua * @GiaTien  -- Cập nhật lại thành tiền dựa trên số lượng mới
        WHERE MaHD = @MaHoaDon AND MaSP = @MaSanPham AND TrangThai = N'Chờ thanh toán';
    END
    ELSE
    BEGIN
        -- Nếu sản phẩm chưa tồn tại thì thực hiện thêm một bản ghi mới vào hóa đơn chi tiết
        INSERT INTO HoaDonChiTiet (MaHD, MaSP, SoLuongMua, GiaTien, ThanhTien, TrangThai, GhiChu)
        VALUES (@MaHoaDon, @MaSanPham, @SoLuong, @GiaTien, @ThanhTien, @TrangThaiHDCT, NULL);
    END;

    -- Cập nhật tổng tiền của hóa đơn
    UPDATE HoaDon
    SET TongTien = (
        SELECT COALESCE(SUM(ThanhTien), 0)
        FROM HoaDonChiTiet
        WHERE MaHD = @MaHoaDon
    )
    WHERE MaHD = @MaHoaDon;

    -- Cập nhật số lượng sản phẩm trong bảng SanPham
    UPDATE SanPham
    SET SoLuong = @SoLuongTon - @SoLuong,  -- Giảm số lượng tồn kho
        TrangThai = CASE 
            WHEN @SoLuongTon - @SoLuong = 0 THEN N'Hết hàng' 
            ELSE TrangThai 
        END
    WHERE MaSP = @MaSanPham;
END;

GO


CREATE OR ALTER PROCEDURE THANHTOANHOADON
    @MAHOADON NVARCHAR(50),
    @TONGTIEN DECIMAL(18,2),
    @MATHANHTOAN INT,
    @MAKH NVARCHAR(50)
AS
BEGIN
    -- Cập nhật thông tin hóa đơn
    UPDATE HoaDon
    SET
        TongTien = @TONGTIEN,
        TrangThai = N'Hoàn thành', 
        thanhToan_id = @MATHANHTOAN,
        MaKH = @MAKH,
        NgayTao = GETDATE()  -- Cập nhật thời gian hiện tại khi thanh toán
    WHERE MaHD = @MAHOADON;

    -- Cập nhật trạng thái chi tiết hóa đơn
    UPDATE HoaDonChiTiet
    SET TrangThai = N'Hoàn thành'
    WHERE TrangThai = N'Chờ thanh toán' AND MaHD = @MAHOADON;
END



GO
CREATE OR ALTER PROCEDURE CHINHSUASOLUONGSP
    @MaHD NVARCHAR(50),          -- Mã hóa đơn
    @MaSP NVARCHAR(50),          -- Mã sản phẩm
    @SoLuongThayDoi INT,         -- Số lượng thay đổi
    @SoLuongTrongGio INT         -- Số lượng trong giỏ hàng
AS
BEGIN
    -- Khai báo các biến cục bộ
    DECLARE @SoLuongSanPham INT;
    DECLARE @GiaTien FLOAT;
    DECLARE @ThanhTien FLOAT;
    DECLARE @TongTien FLOAT;

    -- Lấy đơn giá của sản phẩm từ bảng SanPham
    SELECT @GiaTien = GiaTien 
    FROM SanPham 
    WHERE MaSP = @MaSP;

    -- Lấy số lượng sản phẩm hiện tại từ bảng SanPham
    SELECT @SoLuongSanPham = SoLuong 
    FROM SanPham 
    WHERE MaSP = @MaSP;

    -- Cập nhật số lượng sản phẩm trong bảng SanPham
    UPDATE SanPham
    SET SoLuong = @SoLuongSanPham + @SoLuongTrongGio - @SoLuongThayDoi,
        TrangThai = CASE
                        WHEN @SoLuongSanPham + @SoLuongTrongGio - @SoLuongThayDoi > 0 THEN N'Còn hàng'
                        ELSE N'Hết hàng'
                    END
    WHERE MaSP = @MaSP;

    -- Tính thành tiền cho sản phẩm trong hóa đơn
    SET @ThanhTien = @GiaTien * @SoLuongThayDoi;

    -- Cập nhật số lượng và thành tiền trong bảng HoaDonChiTiet
    UPDATE HoaDonChiTiet
    SET SoLuongMua = @SoLuongThayDoi, 
        GiaTien = @GiaTien,
        ThanhTien = @ThanhTien,
        TrangThai = CASE
                        WHEN @SoLuongThayDoi > 0 THEN N'Còn hàng'
                        ELSE N'Hết hàng'
                    END
    WHERE MaHD = @MaHD AND MaSP = @MaSP;

    -- Tính tổng tiền của hóa đơn
    SELECT @TongTien = SUM(ThanhTien) 
    FROM HoaDonChiTiet 
    WHERE MaHD = @MaHD;

    -- Cập nhật tổng tiền trong bảng HoaDon
    UPDATE HoaDon
    SET TongTien = @TongTien
    WHERE MaHD = @MaHD;
END;

GO

CREATE OR ALTER PROCEDURE duaHDCTVeTrangThaiHuy
    -- Khai báo các biến đầu vào để xác định chính xác hóa đơn chi tiết cần đưa về trạng thái hủy
    @MaHD NVARCHAR(50),              -- Mã hóa đơn
    @MaSP NVARCHAR(50)               -- Mã sản phẩm
AS
BEGIN
    -- Khai báo biến để lưu số lượng sản phẩm cần hủy
    DECLARE @SoLuongXoa INT;

    -- Lấy số lượng sản phẩm từ bảng HoaDonChiTiet
    SELECT @SoLuongXoa = SoLuongMua
    FROM HoaDonChiTiet
    WHERE MaHD = @MaHD AND MaSP = @MaSP;

    -- Cập nhật trạng thái của chi tiết hóa đơn thành 'Đã hủy'
    UPDATE HoaDonChiTiet
    SET TrangThai = N'Đã hủy'
    WHERE MaHD = @MaHD AND MaSP = @MaSP;

    -- Tính tổng tiền còn lại của hóa đơn sau khi cập nhật trạng thái
    DECLARE @TongTien DECIMAL(18, 2);
    SET @TongTien = (SELECT SUM(GiaTien * SoLuongMua) 
                     FROM HoaDonChiTiet 
                     WHERE TrangThai = N'Chờ thanh toán' AND MaHD = @MaHD);

    -- Cập nhật tổng tiền của hóa đơn
    UPDATE HoaDon
    SET TongTien = @TongTien
    WHERE MaHD = @MaHD;

    -- Cập nhật số lượng sản phẩm trong bảng SanPham
    UPDATE SanPham
    SET SoLuong = SoLuong + @SoLuongXoa,
        TrangThai = CASE 
                        WHEN SoLuong + @SoLuongXoa > 0 THEN N'Còn hàng' 
                        ELSE N'Hết hàng' 
                    END
    WHERE MaSP = @MaSP;
END;

