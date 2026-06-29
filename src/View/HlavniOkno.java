package View;

import Controller.HlavniController;
import Model.Objednavka;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class HlavniOkno extends JFrame {
    private JPanel panel1;
    private JTable table1;
    private JButton btnNacist;
    private JTextField txtFiltrJmeno;
    private JComboBox cmbFiltrStav;
    private JButton button1;
    private HlavniController controller;

    public HlavniOkno() {
        setContentPane(panel1);
        setTitle("Objednávky");
        setSize(1000,700);
        setLocationRelativeTo(null);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setDefaultLookAndFeelDecorated(true);
        setVisible(true);
        controller = new HlavniController(this);

        btnNacist.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Vyberte soubor");
            fileChooser.setFileFilter(new FileNameExtensionFilter("csv a txt soubory","csv","txt"));
            if (fileChooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION) {
                String cesta = fileChooser.getSelectedFile().getAbsolutePath();
                controller.nactiSoubor(cesta);
            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.aplikujFiltr();
            }
        });
    }
    public void zobrazObjednavky(List<Objednavka> listObjednavek){
        String[] sloupce = {"id","datum","zakaznik","produkt","kategorie","počet","cena/ks","hodnota","stav"};
        Object[][] data = new Object[listObjednavek.size()][9];
        for (int i=0;i<listObjednavek.size();i++){
            Objednavka o = listObjednavek.get(i);
            data[i][0] = o.getIdObjednavky();
            data[i][1] = o.getDatum();
            data[i][2] = o.getZakaznik();
            data[i][3] = o.getProdukt();
            data[i][4] = o.getKategorie();
            data[i][5] = o.getPocet();
            data[i][6] = o.getCenaZaKus();
            data[i][7] = o.getHodnotaObjednavky();
            data[i][8] = o.getStav();
        }
        table1.setModel(new DefaultTableModel(data,sloupce));
    }

    public String getTxtFiltrJmeno() {
        return txtFiltrJmeno.getText().trim().toLowerCase();
    }

    public Object getCmbFiltrStav() {
        return cmbFiltrStav.getSelectedItem();
    }
}
