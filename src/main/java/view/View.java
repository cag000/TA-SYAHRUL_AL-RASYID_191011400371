package view;

import usecase.Usecase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class View extends JFrame {
    private Usecase usecase;
    private JPanel mainPanel;
    private JLabel aplikasiPenyewaanBarangLabel;
    private JTextField itemNameText;
    private JButton itemTambahButton;
    private DefaultTableModel dtmI;
    private DefaultTableModel dtmR;
    private JTable dtmItem;
    private JTable dtmRental;
    private JButton itemTampilkanButton;
    private JButton itemUbahButton;
    private JButton itemHapusButton;
    private JButton itemClearButton;
    private JLabel rentalIdLabel;
    private JTextField rentalItemIdText;
    private JTextField rentalTenantText;
    private JTextField rentalStartTimeText;
    private JTextField rentalEndTimeText;
    private JComboBox statusComboBox;
    private JLabel itemIdLabel;
    private JTextField itemCategoryText;
    private JButton exitButton;
    private JButton rentalTampilkanButton;
    private JButton rentalTambahButton;
    private JButton rentalUbahButton;
    private JButton rentalHapusButton;
    private JButton rentalClearButton;
    private JScrollPane itemTablejscrollPane;
    private JScrollPane rentalTableJscrollPane;
    private JTextField searchItemText;
    private JButton itemSearchButton;
    private JButton rentalSearchButton;
    private JTextField searchRentalText;

    public View(Usecase usecase) {
        super("Penyewaan | 191011400371 | SYAHRUL AL-RASYID");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.setSize(1501, 876);
        this.usecase = usecase;
        statusItem();
        clearItem();
        clearRental();
        tampilkanRental();

        itemTampilkanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tampilkanItem();
            }
        });
        itemTambahButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tambahItem();
                clearItem();
                tampilkanItem();
            }
        });
        itemUbahButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ubahItem();
                clearItem();
                tampilkanItem();
            }
        });
        itemHapusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hapusItem();
                tampilkanItem();
            }
        });
        itemClearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearItem();
            }
        });
        itemSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchItem();
            }
        });

        rentalTampilkanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tampilkanRental();
            }
        });
        rentalTambahButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    tambahRental();
                    clearRental();
                    tampilkanRental();
                } catch (ParseException parseException) {
                    JOptionPane.showMessageDialog(null, parseException.getMessage());
                }
            }
        });
        rentalUbahButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ubahRental();
                clearRental();
                tampilkanRental();
            }
        });
        rentalHapusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hapusRental();
                tampilkanRental();
            }
        });
        rentalClearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearRental();
            }
        });
        rentalSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchRental();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitAll();
            }
        });

        dtmItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int i = dtmItem.getSelectedRow();
                if (i == -1) {
                    return;
                }

                List<String> list = new ArrayList<>();
                for (int index = 0; index < dtmItem.getColumnCount(); index++) {
                    String data = String.valueOf(dtmItem.getValueAt(i, index));
                    list.add(data);
                }

                itemIdLabel.setText(list.get(0));
                itemNameText.setText(list.get(1));
                itemCategoryText.setText(list.get(2));
                statusComboBox.setSelectedItem(list.get(3));
            }
        });
        dtmRental.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int i = dtmRental.getSelectedRow();
                if (i == -1) {
                    return;
                }

                List<String> list = new ArrayList<>();
                for (int index = 0; index < dtmRental.getColumnCount(); index++) {
                    String data = String.valueOf(dtmRental.getValueAt(i, index));
                    list.add(data);
                }

                rentalIdLabel.setText(list.get(0));
                rentalItemIdText.setText(list.get(1));
                rentalTenantText.setText(list.get(2));
                rentalStartTimeText.setText(list.get(3));
                rentalEndTimeText.setText(list.get(4));

            }
        });
    }

    // Item
    void statusItem() {
        statusComboBox.addItem("Available");
        statusComboBox.addItem("Unavailable");
        statusComboBox.addItem("PreOrder");
        statusComboBox.setSelectedIndex(-1);
    }

    void tampilkanItem() {
        Object[] rows = {"ID", "Nama", "Kategori", "Status"};
        dtmI = new DefaultTableModel(null, rows);
        dtmItem.setModel(dtmI);
        dtmItem.setBorder(null);
        itemTablejscrollPane.setVisible(true);
        itemTablejscrollPane.setViewportView(dtmItem);

        try {
            List<models.Item> res = usecase.listDataItem();
            for (models.Item item : res) {
                Object[] show = {"" + item.getId(), item.getName(), item.getCategory(), item.getStatus()};
                dtmI.addRow(show);
            }
        } catch (SQLException throwables) {
            JOptionPane.showMessageDialog(null, throwables.getMessage());
        }
    }

    void tambahItem() {
        models.Item form = new models.Item();
        form.setName(itemNameText.getText());
        form.setCategory(itemCategoryText.getText());
        form.setStatus(String.valueOf(statusComboBox.getSelectedItem()));

        boolean res = usecase.addDataItem(form);
        if (!res) JOptionPane.showMessageDialog(null, "Tidak bisa menambahkan item baru");
    }

    void ubahItem() {
        int ok = JOptionPane.showConfirmDialog(null, "Apakah yakin untuk mengubah data ini?", "Komfirmasi", JOptionPane.YES_NO_OPTION);
        models.Item form = new models.Item();

        if (ok == 0) {
            long id = Long.parseLong(itemIdLabel.getText());
            form.setId(id);
            form.setName(itemNameText.getText());
            form.setCategory(itemCategoryText.getText());
            form.setStatus(String.valueOf(statusComboBox.getSelectedItem()));
            models.Item res = usecase.updateDataItem(form);
            if (res == new models.Item()) JOptionPane.showMessageDialog(null, "Tidak bisa mengubah item ID " + id);
        }

    }

    void hapusItem() {
        int ok = JOptionPane.showConfirmDialog(null, "Apakah yakin menghapus Data ini?",
                "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);
        if (ok == 0) {
            long id = Long.parseLong(itemIdLabel.getText());
            boolean res = usecase.deleteDataItem(id);
            if (!res) JOptionPane.showMessageDialog(null, "Tidak bisa menghapus item ID " + id);
        }
    }

    void clearItem() {
        itemIdLabel.setText("");
        itemNameText.setText("");
        itemCategoryText.setText("");
        statusComboBox.setSelectedIndex(-1);
        searchItemText.setText("Masukan Id Item");
        searchRentalText.setText("Masukan Id Rental");
        itemNameText.requestFocus();
    }

    void searchItem() {
        long id = Long.parseLong(searchItemText.getText());
        models.Item res = usecase.getDataItem(id);
        itemIdLabel.setText(String.valueOf(res.getId()));
        itemNameText.setText(res.getName());
        itemCategoryText.setText(res.getCategory());
        statusComboBox.setSelectedItem(res.getStatus());

        Object[] rows = {"ID", "Nama", "Kategori", "Status"};
        dtmI = new DefaultTableModel(null, rows);
        dtmItem.setModel(dtmI);
        dtmItem.setBorder(null);
        itemTablejscrollPane.setVisible(true);
        itemTablejscrollPane.setViewportView(dtmItem);
        Object[] show = {"" + res.getId(), res.getName(), res.getCategory(), res.getStatus()};
        dtmI.addRow(show);

    }

    // Rental
    void tampilkanRental() {
        Object[] rowsR = {"ID", "Item ID", "Penyewa", "Waktu Mulai", "Waktu Berakhir"};
        dtmR = new DefaultTableModel(null, rowsR);
        dtmRental.setModel(dtmR);
        dtmRental.setBorder(null);
        rentalTableJscrollPane.setVisible(true);
        rentalTableJscrollPane.setViewportView(dtmRental);

        Object[] rowsI = {"ID", "Nama", "Kategori", "Status"};
        dtmI = new DefaultTableModel(null, rowsI);
        dtmItem.setModel(dtmI);
        dtmItem.setBorder(null);
        itemTablejscrollPane.setVisible(true);
        itemTablejscrollPane.setViewportView(dtmItem);

        try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            List<models.Rental> res = usecase.listDataRental();
            for (models.Rental data : res) {
                Object[] showRental = {"" + data.getId(), data.getItem().getId(),
                        data.getTenant(), data.getStartTime().toLocalDateTime().format(format),
                        data.getEndTime().toLocalDateTime().format(format)};

                Object[] showItem = {"" + data.getItem().getId(), data.getItem().getName(),
                        data.getItem().getCategory(), data.getItem().getStatus()};

                dtmR.addRow(showRental);
                dtmI.addRow(showItem);
            }
        } catch (SQLException throwables) {
            JOptionPane.showMessageDialog(null, throwables.getMessage());
        }
    }

    void tambahRental() throws ParseException {
        models.Item formItem = new models.Item();
        formItem.setId(Long.parseLong(rentalItemIdText.getText()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startTime = LocalDateTime.parse(rentalStartTimeText.getText(), formatter);
        LocalDateTime endTime = LocalDateTime.parse(rentalEndTimeText.getText(), formatter);

        models.Rental formRental = new models.Rental();
        formRental.setItem(formItem);
        formRental.setTenant(rentalTenantText.getText());
        formRental.setStartTime(Timestamp.valueOf(startTime));
        formRental.setEndTime(Timestamp.valueOf(endTime));

        boolean res = usecase.addDataRental(formRental);
        if (!res) JOptionPane.showMessageDialog(null, "Tidak bisa menambahkan penyewaan baru");
    }

    void ubahRental() {
        int ok = JOptionPane.showConfirmDialog(null, "Apakah yakin untuk mengubah data ini?", "Komfirmasi", JOptionPane.YES_NO_OPTION);

        if (ok == 0) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime startTime = LocalDateTime.parse(rentalStartTimeText.getText(), formatter);
            LocalDateTime endTime = LocalDateTime.parse(rentalEndTimeText.getText(), formatter);

            models.Item formItem = new models.Item();
            formItem.setId(Long.parseLong(rentalItemIdText.getText()));

            long id = Long.parseLong(rentalIdLabel.getText());
            models.Rental formRental = new models.Rental();
            formRental.setId(id);
            formRental.setItem(formItem);
            formRental.setTenant(rentalTenantText.getText());
            formRental.setStartTime(Timestamp.valueOf(startTime));
            formRental.setEndTime(Timestamp.valueOf(endTime));

            models.Rental res = usecase.updateDataRental(formRental);
            if (res == new models.Rental()) JOptionPane.showMessageDialog(null, "Tidak bisa mengubah rental ID " + id);
        }

    }

    void hapusRental() {
        int ok = JOptionPane.showConfirmDialog(null, "Apakah yakin menghapus Data ini?",
                "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);
        if (ok == 0) {
            long id = Long.parseLong(rentalIdLabel.getText());
            boolean res = usecase.deleteDataRental(id);
            if (!res) JOptionPane.showMessageDialog(null, "Tidak bisa menghapus item ID " + id);
        }
    }

    void clearRental() {
        rentalIdLabel.setText("");
        rentalItemIdText.setText("");
        rentalTenantText.setText("");
        rentalStartTimeText.setText("e.g 2021-12-02 10:30");
        rentalEndTimeText.setText("e.g 2021-12-02 15:30");
        rentalItemIdText.requestFocus();
    }

    void searchRental() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        long id = Long.parseLong(searchRentalText.getText());
        models.Rental res = usecase.getDataRental(id);
        itemIdLabel.setText(String.valueOf(res.getItem().getId()));
        itemNameText.setText(res.getItem().getName());
        itemCategoryText.setText(res.getItem().getCategory());
        statusComboBox.setSelectedItem(res.getItem().getStatus());

        rentalIdLabel.setText(String.valueOf(res.getId()));
        rentalItemIdText.setText(String.valueOf(res.getItem().getId()));
        rentalTenantText.setText(res.getTenant());
        rentalStartTimeText.setText(res.getStartTime().toLocalDateTime().format(formatter));
        rentalEndTimeText.setText(res.getEndTime().toLocalDateTime().format(formatter));
    }

    void exitAll() {
        System.exit(0);
    }
}
