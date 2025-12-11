# 📚 Aplikasi Perpustakaan – Java Swing

Aplikasi ini merupakan implementasi sederhana dari sistem perpustakaan menggunakan **Java Swing**. Program menampilkan antarmuka grafis (GUI) berisi judul aplikasi dan tabel data. Proyek ini dibuat sebagai pemenuhan tugas Pemrograman Berorientasi Objek.

---

## 📝 Deskripsi Project
Aplikasi ini menampilkan:
- **JFrame** sebagai jendela utama  
- **JLabel** judul *“PERPUSTAKAAN”*  
- **JTable** sebagai kerangka data buku  
- **JScrollPane** untuk menampung tabel  
- Layout otomatis menggunakan **GroupLayout (NetBeans GUI Builder)**  

---

## 🚀 Cara Menjalankan Program

### Prasyarat
- JDK 8+
- Apache NetBeans (direkomendasikan)

### Langkah Menjalankan
1. Clone/download repository ini  
2. Buka di NetBeans  
3. Jalankan file **GUIIJINNNN.java**  
4. GUI akan muncul  

---

# 🧠 Implementasi Materi Pembelajaran

## 🎓 1. Materi Sebelum UTS

### **A. Pemrograman Berorientasi Objek (OOP)**
- **Class**: `GUIIJINNNN`
- **Object**: `new GUIIJINNNN()`
- **Method**: `initComponents()`, `main()`
- **Constructor**: inisialisasi GUI
- **Reference Types**: komponen Swing

### **B. Array & Collection**
```java
new Object[][] { ... }
new String[] { "Judul", "Pengarang", "Tahun" }
```

### **C. Variabel & Tipe Data**
Penggunaan String & variabel komponen.

### **D. Exception Handling**
Digunakan saat memuat Nimbus Look and Feel.

---

## 🎓 2. Materi Setelah UTS (Non-GUI)

### **Multithreading – Event Dispatch Thread (EDT)**  
```java
EventQueue.invokeLater(() -> {
    new GUIIJINNNN().setVisible(true);
});
```

---

# 🖥️ 3. Implementasi GUI
Komponen:
- **JFrame**
- **JLabel**
- **JTable**
- **JScrollPane**
- **GroupLayout**

---

## 🖼️ Screenshot Program


```
[ <img width="983" height="614" alt="Screenshot 2025-12-11 215854" src="https://github.com/user-attachments/assets/8d274a40-f10b-497f-b883-572cca3f37ac" />
 ]
```

---

## 🎬 Video Penjelasan
**[▶️ Tonton Video Penjelasan di Sini](https://youtu.be/E72o7j9E1BQ)**
---

## 👥 Pembagian Tugas Anggota

| Nama | NIM | Tugas |
|------|------|-------|
| **Aqila Ramdan Fuady Latief** | L0324005 | Pengkodean GUI, dokumentasi README |
| **Khudzaifah Hannan Burhanudin** | L0324018 | Analisis materi sebelum & setelah UTS, penyusunan penjelasan konsep, penyempurnaan laporani |
| **Nadhifal Azharuddiya Atmaja** | L0324027 | Penyusunan video presentasi dan struktur program |

---

