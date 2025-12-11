📚 Aplikasi Perpustakaan – Java Swing

Proyek ini merupakan implementasi aplikasi desktop sederhana menggunakan Java Swing. Program ini menampilkan antarmuka grafis (GUI) berisi judul aplikasi dan tabel data yang dapat digunakan sebagai dasar sistem perpustakaan. Aplikasi dikembangkan menggunakan GUI Builder NetBeans dan menerapkan konsep pemrograman berorientasi objek (OOP) serta materi perkuliahan sebelum dan setelah UTS.

📋 Deskripsi Project

Aplikasi menyediakan elemen-elemen berikut:

JFrame sebagai jendela utama.

JLabel untuk menampilkan judul "PERPUSTAKAAN".

JTable dan JScrollPane sebagai kerangka tampilan data buku.

Struktur GUI dibangun menggunakan GroupLayout yang dihasilkan otomatis oleh NetBeans.

🚀 Cara Menjalankan Program
Prasyarat

JDK 8 atau lebih baru

Disarankan menggunakan Apache NetBeans untuk kompatibilitas GUI Builder

Langkah Menjalankan

Clone atau download repository project.

Buka project di NetBeans.

Jalankan file GUIIJINNNN.java.

Jendela aplikasi akan muncul dengan tampilan tabel perpustakaan.

🧠 Implementasi Materi Pembelajaran

README ini disusun berdasarkan rubrik penilaian, mencakup 2 materi sebelum UTS, 1 materi setelah UTS non-GUI, serta GUI sebagai bagian wajib.

🎓 1. Materi Sebelum UTS
A. Pemrograman Berorientasi Objek (OOP)

Konsep OOP diterapkan melalui:

Class: public class GUIIJINNNN

Object: new GUIIJINNNN()

Method: initComponents(), main()

Constructor: digunakan untuk menginisialisasi komponen GUI

Reference Types: komponen Swing seperti JTable, JLabel

B. Array & Collection

Digunakan untuk mendefinisikan model data tabel:

new Object[][] { ... }
new String[] { "Judul", "Pengarang", "Tahun" }

C. Variabel & Tipe Data String

Penggunaan String untuk teks GUI, kolom tabel, dan parameter.

D. Exception Handling

Blok try–catch digunakan saat menetapkan Look and Feel (Nimbus).

🎓 2. Materi Setelah UTS (Non-GUI)
Multithreading – Event Dispatch Thread (EDT)

Pembuatan dan penampilan GUI ditempatkan di dalam:

java.awt.EventQueue.invokeLater(new Runnable() {
    public void run() {
        new GUIIJINNNN().setVisible(true);
    }
});


Ini memastikan GUI berjalan aman pada thread khusus Swing.

🖼️ 3. Implementasi GUI

Komponen yang digunakan:

JFrame – komponen utama aplikasi

JTable – penampil data

JScrollPane – memungkinkan tabel di-scroll

JLabel – judul aplikasi

GroupLayout – pengaturan layout hasil generator GUI Builder

GUI bersifat sederhana, bersih, dan dapat dikembangkan menjadi aplikasi perpustakaan penuh.

🖼️ 4. Screenshot Program

(Tambahkan screenshot setelah program dijalankan)

[ <img width="983" height="614" alt="image" src="https://github.com/user-attachments/assets/8abd8841-5322-42da-936d-1a7c7dbf5e20" />
]

🎬 5. Video Penjelasan

Video menjelaskan:

Struktur kode

Materi kuliah yang diterapkan

Demo tampilan GUI

🎥 Tonton video penjelasan:
**[▶️ Tonton Video Penjelasan di Sini](https://youtu.be/E72o7j9E1BQ)**

Penjelasan lengkap mengenai struktur kode, konsep PBO yang diterapkan, serta demo jalannya program dapat dilihat pada video di bawah ini.



---


👥 Pembagian Tugas Anggota
Nama	NIM	Tugas
Aqila Ramdan Fuady Latief	L0324005	Pengkodean GUI, dokumentasi README

Khudzaifah Hannan Burhanudin	L0324018	Analisis materi sebelum & setelah UTS, penyusunan penjelasan konsep, penyempurnaan laporan

Nadhifal Azharuddiya Atmaja	L0324027	Penyusunan video presentasi dan struktur program
