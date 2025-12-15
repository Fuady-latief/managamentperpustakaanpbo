# 📚 Aplikasi Perpustakaan – Java Swing

Aplikasi ini merupakan implementasi sistem peminjaman buku sederhana menggunakan **Java Swing**. Program menampilkan antarmuka grafis (GUI) berupa jendela utama yang memungkinkan pengguna mengelola data buku, melakukan peminjaman, pengembalian, serta penghapusan data buku. Proyek ini dibuat sebagai pemenuhan tugas mata kuliah **Pemrograman Berorientasi Objek**.

---

## 📝 Deskripsi Project

Aplikasi ini menyediakan antarmuka grafis untuk mendukung pengelolaan data perpustakaan, dengan komponen utama:
- **JFrame** sebagai jendela utama aplikasi
- **JLabel** sebagai judul aplikasi
- **JTable** untuk menampilkan data buku (ID, Judul, Penulis, Total, Dipinjam, Tersedia)
- **JScrollPane** untuk navigasi tabel
- **Menu dan tombol aksi**: Tambah Buku, Edit, Pinjam, Kembalikan, Hapus, Simpan, dan Muat
- **GroupLayout** (NetBeans GUI Builder) untuk pengaturan tata letak

Aplikasi dirancang agar alur penggunaan mudah dipahami dan setiap aksi pengguna mendapatkan umpan balik yang jelas.

---

## 🚀 Cara Menjalankan Program

### Prasyarat
- Java Development Kit (**JDK 8** atau lebih baru)
- **Apache NetBeans** (direkomendasikan karena menggunakan GUI Builder)

### Langkah Menjalankan
1. Clone atau download repository project ini
2. Buka project menggunakan Apache NetBeans
3. Jalankan file **GUIIJINNNN.java**
4. Aplikasi akan menampilkan jendela GUI sistem peminjaman buku

---

## 🔄 Alur Singkat Program

1. Program dijalankan dan menginisialisasi seluruh komponen GUI.
2. Data buku ditampilkan dalam bentuk tabel pada jendela utama.
3. Pengguna dapat melakukan aksi tambah, edit, pinjam, kembalikan, dan hapus buku.
4. Aksi penting seperti penghapusan data dilengkapi **dialog konfirmasi**.
5. Setelah aksi dilakukan, aplikasi memberikan **feedback** melalui dialog informasi dan status bar.
6. Seluruh proses GUI dijalankan pada *Event Dispatch Thread (EDT)* agar aplikasi stabil dan responsif.

---

# 🧠 Implementasi Materi Pembelajaran

## 🎓 1. Materi Sebelum UTS

### **A. Pemrograman Berorientasi Objek (OOP)**
- **Class & Object** digunakan sebagai representasi data dan aplikasi.
- **Method & Constructor** mengelola inisialisasi dan aksi GUI.
- **Reference Types** memanfaatkan komponen Swing seperti `JTable`, `JLabel`, dan `JScrollPane`.

### **B. Array & Collection**
Digunakan dalam model tabel untuk menyimpan data buku:
```java
new Object[][] { ... }
new String[] { "ID", "Judul", "Penulis", "Total", "Dipinjam", "Tersedia" }
```

### **C. Variabel & Tipe Data**
Menggunakan tipe data `String`, `int`, serta variabel instance untuk mengelola data dan komponen GUI.

### **D. Exception Handling**
Digunakan untuk menangani kesalahan saat memuat *Look and Feel* dan validasi input agar aplikasi tidak mudah crash.

---

## 🎓 2. Materi Setelah UTS (Non-GUI)

### **Multithreading – Event Dispatch Thread (EDT)**

GUI dijalankan pada *Event Dispatch Thread (EDT)* menggunakan:

```java
EventQueue.invokeLater(() -> {
    new GUIIJINNNN().setVisible(true);
});
```

Pendekatan ini memastikan antarmuka grafis berjalan aman dan sesuai standar Java Swing.

---

# 🖥️ Implementasi GUI

GUI aplikasi dirancang user-friendly dengan:
- Menu aksi lengkap (Tambah, Edit, Pinjam, Kembalikan, Hapus)
- **Dialog konfirmasi** sebelum penghapusan data
- **Feedback pengguna** melalui dialog dan status bar
- Tabel data yang menampilkan kondisi stok buku secara real-time

---

## 🖼️ Screenshot Program

### Tampilan Utama Aplikasi
<p align="center">
  <img width="983" height="614" alt="Tampilan Utama Aplikasi" src="https://github.com/user-attachments/assets/40ddd647-f9c9-440d-b4eb-8f86753124a5" />
</p>

### Dialog Konfirmasi & Feedback
<p align="center">
  <img width="982" height="612" alt="image" src="https://github.com/user-attachments/assets/11573259-bcee-49fd-bf38-ce98cce39cec" />

</p>

<p align="center">
  <img width="980" height="21" alt="image" src="https://github.com/user-attachments/assets/a32a4977-af62-45de-a04b-29c987f5a4aa" />

</p>

---

## 🎬 Video Penjelasan
**[▶️ Tonton Video Penjelasan di Sini](https://youtu.be/E72o7j9E1BQ)**

---

## 👥 Pembagian Tugas Anggota

| Nama | NIM | Tugas |
|------|------|-------|
| **Aqila Ramdan Fuady Latief** | L0324005 | Pengkodean antarmuka GUI dan penyusunan dokumentasi README |
| **Khudzaifah Hannan Burhanudin** | L0324018 | Analisis materi sebelum dan setelah UTS, penyusunan penjelasan konsep, serta penyempurnaan laporan |
| **Nadhifal Azharuddiya Atmaja** | L0324027 | Penyusunan video presentasi dan perancangan struktur program |
