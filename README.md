Proyek ini adalah implementasi dasar dari aplikasi desktop menggunakan **Java Swing**. Aplikasi ini menampilkan antarmuka grafis sederhana (GUI) yang memuat tabel data dan label judul untuk sistem perpustakaan.

Kode ini dibuat menggunakan *GUI Builder* (NetBeans) dan menunjukkan struktur dasar pemrograman berbasis event dan objek di Java.

## 📋 Deskripsi Singkat

Program ini menjalankan sebuah `JFrame` (Jendela utama) yang berisi:
- **JLabel**: Menampilkan teks judul "PERPUSTAKAAN".
- **JTable**: Menampilkan tabel kosong (sebagai kerangka) yang dibungkus dalam `JScrollPane`.

## 📚 Implementasi Materi Pembelajaran

Berdasarkan silabus/materi yang dipelajari, berikut adalah konsep-konsep yang **diterapkan secara langsung** di dalam kode `GUIIJINNNN.java` ini:

### 1. Dasar Pemrograman Berorientasi Objek (OOP)
- **Class, Object, Method:**
  - `public class GUIIJINNNN` (Class).
  - `new GUIIJINNNN()` (Object instantiation).
  - `initComponents()`, `main()` (Method).
- **Constructor:**
  - `public GUIIJINNNN() { ... }` digunakan untuk inisialisasi komponen GUI.
- **Variabel & Reference Types:**
  - Penggunaan variabel instance seperti `javax.swing.JTable jTable1`.
  - Penggunaan `static final` untuk Logger.

### 2. Struktur Kontrol & Tipe Data
- **Array dan Collection:**
  - Digunakan saat mendefinisikan model tabel: `new Object [][] { ... }` dan `new String [] { ... }`.
- **Kondisional & Looping:**
  - Terdapat dalam blok `main` (generated code) untuk mengatur *Look and Feel* (Nimbus), menggunakan `for` loop dan `if` statement.
- **Character dan String:**
  - Penggunaan tipe data `String` untuk judul kolom tabel dan argumen method main.

### 3. Konsep Lanjutan OOP
- **Enkapsulasi:**
  - Variabel komponen dideklarasikan sebagai `private` (misal: `private javax.swing.JTable jTable1`) untuk menyembunyikan detail implementasi dari luar kelas.
- **Inheritance (Pewarisan):**
  - Kelas ini mewarisi sifat dari `javax.swing.JFrame` (`public class GUIIJINNNN extends javax.swing.JFrame`).
- **Polimorfisme:**
  - Terjadi secara implisit saat objek `GUIIJINNNN` diperlakukan sebagai `JFrame`.

### 4. Fitur Lanjutan & GUI
- **Exception Handling:**
  - Blok `try-catch` di dalam method `main` untuk menangani potensi error saat memuat tema visual (*Look and Feel*).
- **Multithreading:**
  - Menggunakan `java.awt.EventQueue.invokeLater(...)` untuk memastikan pembuatan GUI berjalan di atas *Event Dispatch Thread* (EDT), yang merupakan standar keamanan thread dalam Swing.
- **GUI Programming:**
  - Penggunaan komponen Swing (`JFrame`, `JTable`, `JScrollPane`, `JLabel`) dan *Layout Manager* (`GroupLayout`).

## 🚀 Cara Menjalankan Program

### Prasyarat
- Java Development Kit (JDK) 8 atau lebih baru.
- IDE yang mendukung Java (disarankan **Apache NetBeans** karena kode ini menggunakan layout manager bawaan NetBeans).

### Langkah-langkah
1. **Clone** atau **Download** repositori ini.
2. Buka proyek di IDE Anda.
3. Jalankan file `GUIIJINNNN.java`.
4. Jendela aplikasi akan muncul menampilkan tabel perpustakaan.

## 🎬 Video Penjelasan

Penjelasan lengkap mengenai struktur kode, konsep PBO yang diterapkan, serta demo jalannya program dapat dilihat pada video di bawah ini.

**[▶️ Tonton Video Penjelasan di Sini](https://youtu.be/E72o7j9E1BQ)**

---

---
**Anggota:** AQILA RAMDAN FUADY LATIEF L0324005

KHUDZAIFAH HANNAN BURHANUDIN L0324018

NADHIFAL AZHARUDDIYA ATMAJA L0324027
