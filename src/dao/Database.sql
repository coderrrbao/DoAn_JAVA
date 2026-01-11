use QL_CUAHANGBANNC;

create table sanpham(
	Ma INT IDENTITY(1,1) PRIMARY KEY,
	Ten NVARCHAR(100),
	Gia DECIMAL(10,2) NOT NULL,
	Anh NVARCHAR(100)
);

INSERT INTO sanpham(Ten,Gia,Anh) VALUES ('Pepsi',10000,'assets/img/pepsi.png');

SELECT *
from sanpham
