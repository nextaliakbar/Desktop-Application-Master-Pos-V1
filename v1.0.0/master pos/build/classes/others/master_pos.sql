CREATE DATABASE master_pos;
USE master_pos;

CREATE TABLE pengguna(
	ID_Pengguna VARCHAR(100) NOT NULL PRIMARY KEY,
    Nama VARCHAR(255),
    Username VARCHAR(100),
    Password VARCHAR(100),
    Email VARCHAR(255),
    Level VARCHAR(50),
    Status_Pengguna VARCHAR(20),
    Kode_Verifikasi VARCHAR(50)
);

INSERT INTO pengguna (ID_Pengguna, Nama, Username, Password, Level, Status_Pengguna)
VALUES ('USR-001', 'Owner', 'owner', '12345678', 'Owner', 'Aktif');

CREATE TABLE jenis_barang(
	Kode_Jenis VARCHAR(100) NOT NULL PRIMARY KEY,
    Nama_Jenis VARCHAR(255)
);

CREATE TABLE barang(
	Kode_Barang VARCHAR(100) NOT NULL PRIMARY KEY,
    Nomor_Barcode VARCHAR(255),
    Nama_Barang VARCHAR(255),
    Satuan VARCHAR(50),
    Harga_Beli INT,
    Harga_Jual INT,
    Stok INT,
    Kode_Jenis VARCHAR(100),
    CONSTRAINT fk_barang_jenis_barang FOREIGN KEY (Kode_Jenis)
    REFERENCES jenis_barang(Kode_Jenis)
);

CREATE TABLE tindakan(
	Kode_Tindakan VARCHAR(100) NOT NULL PRIMARY KEY,
    Nama_Tindakan VARCHAR(255),
    Biaya_Tindakan INT
);

CREATE TABLE pasien(
	ID_Pasien VARCHAR(100) NOT NULL PRIMARY KEY,
    Nama VARCHAR(255),
    Jenis_Kelamin VARCHAR(20),
    No_Telp VARCHAR(20),
    Alamat TEXT,
    Email VARCHAR(255),
    Level VARCHAR(50)
);

CREATE TABLE karyawan(
	ID_Karyawan VARCHAR(100) NOT NULL PRIMARY KEY,
    Nama VARCHAR(255),
    No_Telp VARCHAR(100),
    Email VARCHAR(255),
    Jabatan VARCHAR(100),
    Status_Karyawan VARCHAR(25),
    Alamat TEXT
);

CREATE TABLE supplier(
	ID_Supplier VARCHAR(100) NOT NULL PRIMARY KEY,
    Nama VARCHAR(255),
    No_Telp VARCHAR(100),
    Email VARCHAR(255),
    Alamat TEXT
);

CREATE TABLE notifikasi(
	ID_Notifikasi VARCHAR(100) NOT NULL PRIMARY KEY,
    Tanggal_Notifikasi TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Nama_Notifikasi VARCHAR(100) NOT NULL,
    Deskripsi TEXT NOT NULL,
    Jenis_Notifikasi VARCHAR(25),
    Status_Sudah_Dibaca BOOLEAN
);
CREATE TABLE reservasi(
	No_Reservasi VARCHAR(100) NOT NULL PRIMARY KEY,
    Tanggal_Reservasi DATE,
    Tanggal_Kedatangan DATE,
    Jam_Kedatangan TIME,
    Status_Reservasi VARCHAR(50),
    ID_Pasien VARCHAR(100) NOT NULL,
    ID_Pengguna VARCHAR(100) NOT NULL,
    CONSTRAINT reservasi_pasien FOREIGN KEY (ID_Pasien)
    REFERENCES pasien (ID_Pasien),
    CONSTRAINT reservasi_pengguna FOREIGN KEY (ID_Pengguna)
    REFERENCES pengguna (ID_Pengguna)
);

CREATE TABLE pemeriksaan(
	No_Pemeriksaan VARCHAR(100) NOT NULL PRIMARY KEY,
    Tanggal_Pemeriksaan DATE,
    Deskripsi TEXT,
    Status_Pemeriksaan VARCHAR(50),
    Total BIGINT,
    Bayar DOUBLE,
    Kembali DOUBLE,
    Jenis_Pembayaran VARCHAR(50),
    No_Reservasi VARCHAR(100) NOT NULL,
    ID_Pasien VARCHAR(100) NOT NULL,
    ID_Karyawan VARCHAR(100) NOT NULL,
    ID_Pengguna VARCHAR(100) NOT NULL,
    CONSTRAINT pemeriksaan_reservasi FOREIGN KEY (No_Reservasi)
    REFERENCES reservasi (No_Reservasi),
    CONSTRAINT pemeriksaan_pasien FOREIGN KEY (ID_Pasien)
    REFERENCES pasien (ID_Pasien),
    CONSTRAINT pemeriksaan_karyawan FOREIGN KEY (ID_Karyawan)
    REFERENCES karyawan (ID_Karyawan),
    CONSTRAINT pemeriksaan_pengguna FOREIGN KEY (ID_Pengguna)
    REFERENCES pengguna (ID_Pengguna)
);

CREATE TABLE detail_pemeriksaan(
	No_Pemeriksaan VARCHAR(100) NOT NULL,
    Kode_Tindakan VARCHAR(100) NOT NULL,
    Biaya_Tindakan_Final INT,
    Potongan INT,
    Subtotal INT,
    CONSTRAINT detail_pemeriksaan_pemeriksaan FOREIGN KEY (No_Pemeriksaan)
    REFERENCES pemeriksaan (No_Pemeriksaan),
    CONSTRAINT detail_pemeriksaan_tindakan FOREIGN KEY (Kode_Tindakan)
    REFERENCES tindakan (Kode_Tindakan)
);

CREATE TABLE penjualan(
	No_Penjualan VARCHAR(100) NOT NULL PRIMARY KEY,
    Tanggal DATE,
    Total_Penjualan BIGINT,
    Total_Keuntungan BIGINT,
    Bayar DOUBLE,
    Kembali DOUBLE,
    Jenis_Pembayaran VARCHAR(25),
    ID_Pengguna VARCHAR(100),
    CONSTRAINT fk_penjualan_pengguna FOREIGN KEY (ID_Pengguna)
    REFERENCES pengguna (ID_Pengguna)
);

CREATE TABLE detail_penjualan(
	No_Penjualan VARCHAR(100) NOT NULL,
    Kode_Barang VARCHAR(100) NOT NULL,
    Harga_Jual_Final INT,
    Jumlah INT,
    Subtotal INT,
    CONSTRAINT detail_penjualan_penjualan FOREIGN KEY (No_Penjualan) 
    REFERENCES penjualan (No_Penjualan),
    CONSTRAINT detail_penjualan_barang FOREIGN KEY (Kode_Barang)
    REFERENCES barang (Kode_Barang)
);

CREATE TABLE pemesanan(
	No_Pemesanan VARCHAR(100) NOT NULL PRIMARY KEY,
    Tanggal_Pemesanan DATE,
    Status_Pemesanan VARCHAR(50),
    Total_Pemesanan BIGINT,
    Bayar DOUBLE,
    Kembali DOUBLE,
    Jenis_Pembayaran VARCHAR(25),
    ID_Supplier VARCHAR(100),
    ID_Pengguna VARCHAR(100),
    CONSTRAINT pemesanan_supplier FOREIGN KEY (ID_Supplier)
    REFERENCES supplier (ID_Supplier),
    CONSTRAINT pemesanan_pengguna FOREIGN KEY (ID_Pengguna)
    REFERENCES pengguna (ID_Pengguna)
);

CREATE TABLE detail_pemesanan(
	No_Pemesanan VARCHAR(100) NOT NULL,
    Kode_Barang VARCHAR(100) NOT NULL,
    Harga_Beli_Final INT,
    Jumlah INT,
    Subtotal INT
);

CREATE TABLE jenis_pengeluaran(
	No_Jenis VARCHAR(100) NOT NULL PRIMARY KEY,
    Nama_Jenis VARCHAR(255)
);

INSERT INTO jenis_pengeluaran (No_Jenis, Nama_Jenis) VALUES
('JNSPLRN-001', 'Pemesanan Barang'),
('JNSPLRN-002', 'Biaya Operasional'),
('JNSPLRN-003', 'Lain - lain');

CREATE TABLE pengeluaran(
	No_Pengeluaran VARCHAR(100) NOT NULL PRIMARY KEY,
    Tanggal_Pengeluaran DATE,
    Total_Pengeluaran BIGINT,
    Deskripsi TEXT,
    ID_Pengguna VARCHAR(100),
    CONSTRAINT pengeluaran_pengguna FOREIGN KEY (ID_Pengguna)
    REFERENCES pengguna (ID_Pengguna)
);

CREATE TABLE detail_pengeluaran(
	No_Pengeluaran VARCHAR(100) NOT NULL,
    No_Jenis VARCHAR(100) NOT NULL,
    Detail_Jenis VARCHAR(255),
    Subtotal INT
);

CREATE TABLE restok(
	No_Restok VARCHAR(100) NOT NULL PRIMARY KEY,
    Tanggal DATE,
    Total_Biaya BIGINT,
    Status_Restok VARCHAR(50),
    ID_Pengguna VARCHAR(100),
    CONSTRAINT restok_pengguna FOREIGN KEY (ID_Pengguna)
    REFERENCES pengguna (ID_Pengguna)
);

CREATE TABLE detail_restok(
	No_Restok VARCHAR(100) NOT NULL,
    Kode_Barang VARCHAR(100) NOT NULL,
    Jumlah INT,
    Subtotal INT,
    CONSTRAINT detail_restok_restok FOREIGN KEY (No_Restok)
    REFERENCES restok (No_Restok),
    CONSTRAINT detail_restok_barang FOREIGN KEY (Kode_Barang)
    REFERENCES barang (Kode_Barang)
);

CREATE TABLE absensi(
	Tanggal DATE,
    Absensi_Masuk TIME,
    Absensi_Keluar TIME,
    Keterangan VARCHAR(50),
    ID_Karyawan VARCHAR(100) NOT NULL,
    CONSTRAINT absensi_karyawan FOREIGN KEY (ID_Karyawan)
    REFERENCES karyawan (ID_Karyawan)
);

CREATE TABLE promo(
	No_Promo VARCHAR(100) NOT NULL PRIMARY KEY,
    Nama_Promo VARCHAR(255),
    Tanggal_Awal DATE,
    Tanggal_Akhir DATE,
    Banyak_Promo INT,
    Jenis_Promo VARCHAR(50),
    Keterangan VARCHAR(50)
);
