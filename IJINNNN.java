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

public class IJINNNN {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            Library library = Library.loadFromFile();
            MainFrame frame = new MainFrame(library);
            frame.setVisible(true);
        });
    }
}

class Book implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int NEXT_ID = 1;

    private int id;
    private String title;
    private String author;
    private int totalCopies;
    private int borrowedCopies;

    public Book(String title, String author, int totalCopies) {
        this.id = NEXT_ID++;
        this.title = title;
        this.author = author;
        this.totalCopies = Math.max(1, totalCopies);
        this.borrowedCopies = 0;
    }

    private Object readResolve() {
        if (this.id >= NEXT_ID) NEXT_ID = this.id + 1;
        return this;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getTotalCopies() { return totalCopies; }
    public int getBorrowedCopies() { return borrowedCopies; }
    public int getAvailableCopies() { return totalCopies - borrowedCopies; }

    public boolean borrow() {
        if (getAvailableCopies() <= 0) return false;
        borrowedCopies++;
        return true;
    }

    public boolean returnBook() {
        if (borrowedCopies <= 0) return false;
        borrowedCopies--;
        return true;
    }

    public void addCopies(int n) {
        if (n > 0) totalCopies += n;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean setTotalCopies(int totalCopies) {
        if (totalCopies < borrowedCopies || totalCopies < 1) {
            return false;
        }
        this.totalCopies = totalCopies;
        return true;
    }
}

class Library implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Book> books = new ArrayList<>();

    private static final String DATA_FILE = "library.dat";

    public synchronized void addBook(Book b) {
        books.add(b);
    }

    public synchronized List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    public synchronized Book findById(int id) {
        for (Book b : books) if (b.getId() == id) return b;
        return null;
    }

    public synchronized void removeBook(Book b) {
        books.remove(b);
    }

    public synchronized boolean borrowBook(int id) {
        Book b = findById(id);
        if (b == null) return false;
        return b.borrow();
    }

    public synchronized boolean returnBook(int id) {
        Book b = findById(id);
        if (b == null) return false;
        return b.returnBook();
    }

    public synchronized void saveToFile() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(this);
        }
    }

    public static Library loadFromFile() {
        File f = new File(DATA_FILE);
        if (!f.exists()) return new Library();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            Object o = ois.readObject();
            if (o instanceof Library) return (Library) o;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Library();
    }
}

class MainFrame extends JFrame {
    private Library library;
    private JTable table;
    private DefaultTableModel tableModel;

    public MainFrame(Library library) {
        super("Sistem Peminjaman Buku");
        this.library = library;
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        initUI();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onExit();
            }
        });
    }

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

        String[] cols = {"ID", "Judul", "Penulis", "Total", "Dipinjam", "Tersedia"};
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

        JLabel status = new JLabel("Siap");
        status.setBorder(BorderFactory.createEmptyBorder(4, 6, 4, 6));
        getContentPane().add(status, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> {
            AddBookDialog dlg = new AddBookDialog(this);
            dlg.setVisible(true);
            if (dlg.isOk()) {
                Book b = new Book(dlg.getTitleText(), dlg.getAuthorText(), dlg.getCopies());
                library.addBook(b);
                refreshTable();
                status.setText("Buku ditambahkan: " + b.getTitle());
            }
        });

        btnEdit.addActionListener(e -> {
            int id = getSelectedBookId();
            if (id == -1) {
                JOptionPane.showMessageDialog(this, "Pilih buku yang akan diedit terlebih dahulu.");
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
                String newTitle = dlg.getTitleText();
                String newAuthor = dlg.getAuthorText();
                int newCopies = dlg.getCopies();

                if (newCopies < b.getBorrowedCopies()) {
                    JOptionPane.showMessageDialog(this,
                            "Jumlah total tidak boleh kurang dari jumlah yang sudah dipinjam (" + b.getBorrowedCopies() + ").");
                    return;
                }

                b.setTitle(newTitle);
                b.setAuthor(newAuthor);
                if (!b.setTotalCopies(newCopies)) {
                    JOptionPane.showMessageDialog(this,
                            "Gagal mengubah jumlah total. Pastikan jumlah minimal 1 dan tidak kurang dari yang dipinjam.");
                    return;
                }

                refreshTable();
                status.setText("Data buku ID " + id + " berhasil diubah.");
            }
        });

        btnBorrow.addActionListener(e -> {
            int id = getSelectedBookId();
            if (id == -1) {
                String s = JOptionPane.showInputDialog(this, "Masukkan ID Buku yang ingin dipinjam:");
                if (s == null) return;
                try { id = Integer.parseInt(s.trim()); } catch (Exception ex) { JOptionPane.showMessageDialog(this, "ID tidak valid"); return; }
            }
            boolean ok = library.borrowBook(id);
            if (ok) {
                refreshTable();
                status.setText("Buku ID " + id + " dipinjam pada " + LocalDate.now());
            } else JOptionPane.showMessageDialog(this, "Tidak dapat meminjam buku (tidak tersedia atau ID tidak ditemukan)");
        });

        btnReturn.addActionListener(e -> {
            int id = getSelectedBookId();
            if (id == -1) {
                String s = JOptionPane.showInputDialog(this, "Masukkan ID Buku yang akan dikembalikan:");
                if (s == null) return;
                try { id = Integer.parseInt(s.trim()); } catch (Exception ex) { JOptionPane.showMessageDialog(this, "ID tidak valid"); return; }
            }
            boolean ok = library.returnBook(id);
            if (ok) {
                refreshTable();
                status.setText("Buku ID " + id + " dikembalikan pada " + LocalDate.now());
            } else JOptionPane.showMessageDialog(this, "Tidak dapat mengembalikan buku (tidak ada yang dipinjam atau ID tidak ditemukan)");
        });

        btnRemove.addActionListener(e -> {
            int id = getSelectedBookId();
            if (id == -1) { JOptionPane.showMessageDialog(this, "Pilih buku terlebih dahulu atau masukkan ID"); return; }
            Book b = library.findById(id);
            if (b == null) { JOptionPane.showMessageDialog(this, "Buku tidak ditemukan"); return; }
            int sel = JOptionPane.showConfirmDialog(this, "Hapus buku '" + b.getTitle() + "' ?","Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (sel == JOptionPane.YES_OPTION) {
                library.removeBook(b);
                refreshTable();
                status.setText("Buku ID " + id + " dihapus");
            }
        });

        btnSave.addActionListener(e -> {
            try {
                library.saveToFile();
                status.setText("Tersimpan ke disk");
                JOptionPane.showMessageDialog(this, "Berhasil disimpan");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Gagal menyimpan: " + ex.getMessage());
            }
        });

        btnLoad.addActionListener(e -> {
            Library l = Library.loadFromFile();
            this.library = l;
            refreshTable();
            status.setText("Dimuat dari disk");
            JOptionPane.showMessageDialog(this, "Berhasil dimuat");
        });

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = table.getSelectedRow();
                    if (row >= 0) {
                        int id = (int) tableModel.getValueAt(row, 0);
                        Book b = library.findById(id);
                        if (b != null) JOptionPane.showMessageDialog(MainFrame.this, bookDetails(b), "Detail Buku", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });
    }

    private String bookDetails(Book b) {
        return String.format("ID: %d\nJudul: %s\nPenulis: %s\nJumlah total: %d\nDipinjam: %d\nTersedia: %d",
                b.getId(), b.getTitle(), b.getAuthor(), b.getTotalCopies(), b.getBorrowedCopies(), b.getAvailableCopies());
    }

    private int getSelectedBookId() {
        int r = table.getSelectedRow();
        if (r == -1) return -1;
        Object v = tableModel.getValueAt(r, 0);
        if (v instanceof Integer) return (Integer) v;
        try { return Integer.parseInt(v.toString()); } catch (Exception e) { return -1; }
    }

    private void refreshTable() {
        SwingUtilities.invokeLater(() -> {
            tableModel.setRowCount(0);
            for (Book b : library.getAllBooks()) {
                tableModel.addRow(new Object[]{b.getId(), b.getTitle(), b.getAuthor(), b.getTotalCopies(), b.getBorrowedCopies(), b.getAvailableCopies()});
            }
        });
    }

    private void onExit() {
        int sel = JOptionPane.showConfirmDialog(this, "Simpan data perpustakaan sebelum keluar?","Keluar", JOptionPane.YES_NO_CANCEL_OPTION);
        if (sel == JOptionPane.CANCEL_OPTION) return;
        if (sel == JOptionPane.YES_OPTION) {
            try { library.saveToFile(); } catch (IOException e) { e.printStackTrace(); }
        }
        dispose();
        System.exit(0);
    }
}

class AddBookDialog extends JDialog {
    private JTextField tfTitle = new JTextField(30);
    private JTextField tfAuthor = new JTextField(30);
    private JSpinner spCopies = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
    private boolean ok = false;

    public AddBookDialog(JFrame owner) {
        super(owner, "Tambah Buku", true);
        setLayout(new BorderLayout());
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4,4,4,4);
        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0; c.gridy = 0; p.add(new JLabel("Judul:"), c);
        c.gridx = 1; p.add(tfTitle, c);

        c.gridx = 0; c.gridy = 1; p.add(new JLabel("Penulis:"), c);
        c.gridx = 1; p.add(tfAuthor, c);

        c.gridx = 0; c.gridy = 2; p.add(new JLabel("Jumlah kopi:"), c);
        c.gridx = 1; p.add(spCopies, c);

        add(p, BorderLayout.CENTER);

        JPanel btns = new JPanel();
        JButton bOk = new JButton("OK");
        JButton bCancel = new JButton("Batal");
        btns.add(bOk); btns.add(bCancel);
        add(btns, BorderLayout.SOUTH);

        bOk.addActionListener(e -> {
            if (tfTitle.getText().trim().isEmpty()) { JOptionPane.showMessageDialog(this, "Judul tidak boleh kosong"); return; }
            ok = true; setVisible(false);
        });
        bCancel.addActionListener(e -> { ok = false; setVisible(false); });

        pack();
        setLocationRelativeTo(owner);
    }

    public boolean isOk() { return ok; }
    public String getTitleText() { return tfTitle.getText().trim(); }
    public String getAuthorText() { return tfAuthor.getText().trim(); }
    public int getCopies() { return (Integer) spCopies.getValue(); }

    public void setInitialData(String title, String author, int copies) {
        tfTitle.setText(title);
        tfAuthor.setText(author);
        spCopies.setValue(copies);
    }
}
