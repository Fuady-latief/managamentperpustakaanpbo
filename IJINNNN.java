/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package com.mycompany.ijinnnn;

/**
 *
 * @author NotLatippp
 */

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Class utama (entry point) aplikasi
 * Program dimulai dari method main pada class ini
 */
public class IJINNNN {

    // Method main sebagai titik awal eksekusi program Java
    public static void main(String[] args) {

        // Menjalankan GUI pada Event Dispatch Thread (EDT)
        // agar aplikasi Swing berjalan aman dan stabil
        SwingUtilities.invokeLater(() -> {
            try {
                // Mengatur tampilan GUI agar mengikuti tampilan sistem operasi
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}

            // Memuat data perpustakaan dari file (jika ada)
            Library library = Library.loadFromFile();

            // Membuat dan menampilkan frame utama aplikasi
            MainFrame frame = new MainFrame(library);
            frame.setVisible(true);
        });
    }
}

/**
 * Class Book merepresentasikan data buku
 * Mengimplementasikan Serializable agar dapat disimpan ke file
 */
class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    // Digunakan untuk auto-increment ID buku
    private static int NEXT_ID = 1;

    private int id;
    private String title;
    private String author;
    private int totalCopies;
    private int borrowedCopies;

    // Constructor untuk membuat objek buku baru
    public Book(String title, String author, int totalCopies) {
        this.id = NEXT_ID++;
        this.title = title;
        this.author = author;
        this.totalCopies = Math.max(1, totalCopies);
        this.borrowedCopies = 0;
    }

    // Menjaga konsistensi ID saat data dibaca dari file (serialisasi)
    private Object readResolve() {
        if (this.id >= NEXT_ID) NEXT_ID = this.id + 1;
        return this;
    }

    // Getter untuk atribut buku
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getTotalCopies() { return totalCopies; }
    public int getBorrowedCopies() { return borrowedCopies; }

    // Menghitung jumlah buku yang masih tersedia
    public int getAvailableCopies() {
        return totalCopies - borrowedCopies;
    }

    // Proses peminjaman buku
    public boolean borrow() {
        if (getAvailableCopies() <= 0) return false;
        borrowedCopies++;
        return true;
    }

    // Proses pengembalian buku
    public boolean returnBook() {
        if (borrowedCopies <= 0) return false;
        borrowedCopies--;
        return true;
    }

    // Menambah jumlah eksemplar buku
    public void addCopies(int n) {
        if (n > 0) totalCopies += n;
    }

    // Setter judul buku
    public void setTitle(String title) {
        this.title = title;
    }

    // Setter penulis buku
    public void setAuthor(String author) {
        this.author = author;
    }

    // Mengubah jumlah total buku dengan validasi
    public boolean setTotalCopies(int totalCopies) {
        if (totalCopies < borrowedCopies || totalCopies < 1) {
            return false;
        }
        this.totalCopies = totalCopies;
        return true;
    }
}

/**
 * Class Library berfungsi mengelola koleksi buku
 * serta penyimpanan dan pemuatan data ke file
 */
class Library implements Serializable {

    private static final long serialVersionUID = 1L;

    // Menyimpan daftar buku menggunakan Collection
    private List<Book> books = new ArrayList<>();

    // Nama file penyimpanan data
    private static final String DATA_FILE = "library.dat";

    // Menambahkan buku ke perpustakaan
    public synchronized void addBook(Book b) {
        books.add(b);
    }

    // Mengambil seluruh daftar buku
    public synchronized List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    // Mencari buku berdasarkan ID
    public synchronized Book findById(int id) {
        for (Book b : books)
            if (b.getId() == id) return b;
        return null;
    }

    // Menghapus buku dari perpustakaan
    public synchronized void removeBook(Book b) {
        books.remove(b);
    }

    // Proses peminjaman buku berdasarkan ID
    public synchronized boolean borrowBook(int id) {
        Book b = findById(id);
        if (b == null) return false;
        return b.borrow();
    }

    // Proses pengembalian buku berdasarkan ID
    public synchronized boolean returnBook(int id) {
        Book b = findById(id);
        if (b == null) return false;
        return b.returnBook();
    }

    // Menyimpan data perpustakaan ke file menggunakan serialisasi
    public synchronized void saveToFile() throws IOException {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(this);
        }
    }

    // Memuat data perpustakaan dari file
    public static Library loadFromFile() {
        File f = new File(DATA_FILE);
        if (!f.exists()) return new Library();
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(f))) {
            Object o = ois.readObject();
            if (o instanceof Library) return (Library) o;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Library();
    }
}

/**
 * MainFrame adalah frame utama aplikasi
 * Menampilkan tabel buku dan tombol-tombol aksi
 */
class MainFrame extends JFrame {

    private Library library;
    private JTable table;
    private DefaultTableModel tableModel;

    // Constructor frame utama
    public MainFrame(Library library) {
        super("Sistem Peminjaman Buku");
        this.library = library;

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);

        initUI();

        // Konfirmasi sebelum keluar aplikasi
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onExit();
            }
        });
    }

    // Inisialisasi seluruh komponen GUI dan event handler
    private void initUI() {

        JPanel top = new JPanel(new BorderLayout());

        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        JButton btnAdd = new JButton("Tambah Buku");
        JButton btnEdit = new JButton("Edit");
        JButton btnBorrow = new JButton("Pinjam");
        JButton btnReturn = new JButton("Kembalikan");
        JButton btnRemove = new JButton("Hapus");
        JButton btnSave = new JButton("Simpan");
        JButton btnLoad = new JButton("Muat");

        toolBar.add(btnAdd);
        toolBar.add(btnEdit);
        toolBar.add(btnBorrow);
        toolBar.add(btnReturn);
        toolBar.add(btnRemove);
        toolBar.addSeparator();
        toolBar.add(btnSave);
        toolBar.add(btnLoad);

        top.add(toolBar, BorderLayout.NORTH);

        // Kolom tabel buku
        String[] cols = {"ID", "Judul", "Penulis", "Total", "Dipinjam", "Tersedia"};

        // Model tabel (tidak bisa diedit langsung)
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(table);

        refreshTable();

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(top, BorderLayout.NORTH);
        getContentPane().add(scroll, BorderLayout.CENTER);

        // Status bar untuk feedback ke pengguna
        JLabel status = new JLabel("Siap");
        status.setBorder(BorderFactory.createEmptyBorder(4, 6, 4, 6));
        getContentPane().add(status, BorderLayout.SOUTH);

        // ================= EVENT HANDLER =================

        // Tambah buku
        btnAdd.addActionListener(e -> {
            AddBookDialog dlg = new AddBookDialog(this);
            dlg.setVisible(true);
            if (dlg.isOk()) {
                Book b = new Book(
                        dlg.getTitleText(),
                        dlg.getAuthorText(),
                        dlg.getCopies()
                );
                library.addBook(b);
                refreshTable();
                status.setText("Buku ditambahkan: " + b.getTitle());
            }
        });

        // Edit buku
        btnEdit.addActionListener(e -> {
            int id = getSelectedBookId();
            if (id == -1) {
                JOptionPane.showMessageDialog(this,
                        "Pilih buku yang akan diedit terlebih dahulu.");
                return;
            }

            Book b = library.findById(id);
            if (b == null) {
                JOptionPane.showMessageDialog(this, "Buku tidak ditemukan.");
                return;
            }

            AddBookDialog dlg = new AddBookDialog(this);
            dlg.setInitialData(b.getTitle(), b.getAuthor(), b.getTotalCopies());
            dlg.setVisible(true);

            if (dlg.isOk()) {
                int newCopies = dlg.getCopies();
                if (newCopies < b.getBorrowedCopies()) {
                    JOptionPane.showMessageDialog(this,
                            "Jumlah total tidak boleh kurang dari jumlah yang dipinjam.");
                    return;
                }
                b.setTitle(dlg.getTitleText());
                b.setAuthor(dlg.getAuthorText());
                b.setTotalCopies(newCopies);
                refreshTable();
                status.setText("Data buku ID " + id + " diperbarui");
            }
        });

        // Pinjam buku
        btnBorrow.addActionListener(e -> {
            int id = getSelectedBookId();
            if (id == -1) return;
            if (library.borrowBook(id)) {
                refreshTable();
                status.setText("Buku ID " + id + " dipinjam pada " + LocalDate.now());
            } else {
                JOptionPane.showMessageDialog(this,
                        "Buku tidak tersedia atau ID tidak ditemukan");
            }
        });

        // Kembalikan buku
        btnReturn.addActionListener(e -> {
            int id = getSelectedBookId();
            if (id == -1) return;
            if (library.returnBook(id)) {
                refreshTable();
                status.setText("Buku ID " + id + " dikembalikan pada " + LocalDate.now());
            } else {
                JOptionPane.showMessageDialog(this,
                        "Tidak ada buku yang dapat dikembalikan");
            }
        });

        // Hapus buku
        btnRemove.addActionListener(e -> {
            int id = getSelectedBookId();
            if (id == -1) return;
            Book b = library.findById(id);
            if (b == null) return;

            int sel = JOptionPane.showConfirmDialog(
                    this,
                    "Hapus buku '" + b.getTitle() + "' ?",
                    "Konfirmasi",
                    JOptionPane.YES_NO_OPTION
            );

            if (sel == JOptionPane.YES_OPTION) {
                library.removeBook(b);
                refreshTable();
                status.setText("Buku ID " + id + " dihapus");
            }
        });

        // Simpan data
        btnSave.addActionListener(e -> {
            try {
                library.saveToFile();
                status.setText("Data berhasil disimpan");
                JOptionPane.showMessageDialog(this, "Berhasil disimpan");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                        "Gagal menyimpan data: " + ex.getMessage());
            }
        });

        // Muat data
        btnLoad.addActionListener(e -> {
            library = Library.loadFromFile();
            refreshTable();
            status.setText("Data dimuat dari disk");
            JOptionPane.showMessageDialog(this, "Berhasil dimuat");
        });

        // Double-click tabel untuk melihat detail buku
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = table.getSelectedRow();
                    if (row >= 0) {
                        int id = (int) tableModel.getValueAt(row, 0);
                        Book b = library.findById(id);
                        if (b != null)
                            JOptionPane.showMessageDialog(
                                    MainFrame.this,
                                    bookDetails(b),
                                    "Detail Buku",
                                    JOptionPane.INFORMATION_MESSAGE
                            );
                    }
                }
            }
        });
    }

    // Menampilkan detail buku dalam bentuk teks
    private String bookDetails(Book b) {
        return String.format(
                "ID: %d\nJudul: %s\nPenulis: %s\nJumlah total: %d\nDipinjam: %d\nTersedia: %d",
                b.getId(),
                b.getTitle(),
                b.getAuthor(),
                b.getTotalCopies(),
                b.getBorrowedCopies(),
                b.getAvailableCopies()
        );
    }

    // Mengambil ID buku yang sedang dipilih di tabel
    private int getSelectedBookId() {
        int r = table.getSelectedRow();
        if (r == -1) return -1;
        Object v = tableModel.getValueAt(r, 0);
        try {
            return Integer.parseInt(v.toString());
        } catch (Exception e) {
            return -1;
        }
    }

    // Memperbarui isi tabel berdasarkan data terbaru
    private void refreshTable() {
        SwingUtilities.invokeLater(() -> {
            tableModel.setRowCount(0);
            for (Book b : library.getAllBooks()) {
                tableModel.addRow(new Object[]{
                        b.getId(),
                        b.getTitle(),
                        b.getAuthor(),
                        b.getTotalCopies(),
                        b.getBorrowedCopies(),
                        b.getAvailableCopies()
                });
            }
        });
    }

    // Konfirmasi keluar aplikasi dan simpan data jika diperlukan
    private void onExit() {
        int sel = JOptionPane.showConfirmDialog(
                this,
                "Simpan data perpustakaan sebelum keluar?",
                "Keluar",
                JOptionPane.YES_NO_CANCEL_OPTION
        );
        if (sel == JOptionPane.CANCEL_OPTION) return;
        if (sel == JOptionPane.YES_OPTION) {
            try {
                library.saveToFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        dispose();
        System.exit(0);
    }
}

/**
 * Dialog untuk menambah atau mengedit data buku
 */
class AddBookDialog extends JDialog {

    private JTextField tfTitle = new JTextField(30);
    private JTextField tfAuthor = new JTextField(30);
    private JSpinner spCopies =
            new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));

    private boolean ok = false;

    // Constructor dialog input buku
    public AddBookDialog(JFrame owner) {
        super(owner, "Tambah Buku", true);

        setLayout(new BorderLayout());

        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0; c.gridy = 0;
        p.add(new JLabel("Judul:"), c);
        c.gridx = 1;
        p.add(tfTitle, c);

        c.gridx = 0; c.gridy = 1;
        p.add(new JLabel("Penulis:"), c);
        c.gridx = 1;
        p.add(tfAuthor, c);

        c.gridx = 0; c.gridy = 2;
        p.add(new JLabel("Jumlah kopi:"), c);
        c.gridx = 1;
        p.add(spCopies, c);

        add(p, BorderLayout.CENTER);

        JPanel btns = new JPanel();
        JButton bOk = new JButton("OK");
        JButton bCancel = new JButton("Batal");
        btns.add(bOk);
        btns.add(bCancel);
        add(btns, BorderLayout.SOUTH);

        bOk.addActionListener(e -> {
            if (tfTitle.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Judul tidak boleh kosong");
                return;
            }
            ok = true;
            setVisible(false);
        });

        bCancel.addActionListener(e -> {
            ok = false;
            setVisible(false);
        });

        pack();
        setLocationRelativeTo(owner);
    }

    // Mengecek apakah dialog disetujui
    public boolean isOk() {
        return ok;
    }

    // Getter input dialog
    public String getTitleText() {
        return tfTitle.getText().trim();
    }

    public String getAuthorText() {
        return tfAuthor.getText().trim();
    }

    public int getCopies() {
        return (Integer) spCopies.getValue();
    }

    // Mengisi data awal saat edit buku
    public void setInitialData(String title, String author, int copies) {
        tfTitle.setText(title);
        tfAuthor.setText(author);
        spCopies.setValue(copies);
    }
}
