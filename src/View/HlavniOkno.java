package View;

import Controller.HlavniController;
import Model.Objednavka;
import Model.StavObjednavky;

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
    private JButton btnZmenStav;
    private JTextField txtID;
    private JTextField txtDatum;
    private JTextField txtZakaznik;
    private JTextField txtCena;
    private JTextField txtPocet;
    private JButton btnVytvorNovou;
    private JTextArea txtAnalytika;
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
        btnZmenStav.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int  vybranyRadek = table1.getSelectedRow();
                if(vybranyRadek==-1){
                    JOptionPane.showMessageDialog(HlavniOkno.this, "Vyberte objednávku z tabulky!", "Chyba", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                controller.zmenStavObjednavky(vybranyRadek);

            }
        });
        btnVytvorNovou.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.pridejNovouObjednavku();
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

    public StavObjednavky vyberNovyStavDialog(){
        return (StavObjednavky) JOptionPane.showInputDialog(this, "Vyberte nový stav", "Změna stavu", JOptionPane.QUESTION_MESSAGE, null, StavObjednavky.values(), StavObjednavky.values()[0]);
    }

    public String getTxtFiltrJmeno() {
        return txtFiltrJmeno.getText().trim().toLowerCase();
    }

    public Object getCmbFiltrStav() {
        return cmbFiltrStav.getSelectedItem();
    }

    public Objednavka getNovaObjednavkaFormular(){
        String id  = txtID.getText().trim();
        String datum = txtDatum.getText().trim();
        String zakaznik = txtZakaznik.getText().trim();


        if(id.isEmpty() || datum.isEmpty() || zakaznik.isEmpty() ) {
            JOptionPane.showMessageDialog(this, "Vyplňte všechna pole formuláře", "Chyba", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        int pocet;
        double cenaZaKus;

        try{
            pocet = Integer.parseInt(txtPocet.getText().trim());
            cenaZaKus= Double.parseDouble(txtCena.getText().trim());
        } catch (NumberFormatException e){
            JOptionPane.showMessageDialog(this, "Počet a cena musí být čísla!!", "Chyba", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        return new Objednavka(id, datum, zakaznik, "Produkt", "kategorie", pocet, cenaZaKus,  StavObjednavky.NOVA);
    }

    public void smazFormularNovaObjednavka(){
        txtID.setText("");
        txtDatum.setText("");
        txtZakaznik.setText("");
        txtPocet.setText("");
        txtCena.setText("");
    }

    public void zobrazAnalytiku(double celkovaTrzba, double prumerna, int pocetNova,int  pocetZaplacena, int pocetExpandovana,int pocetVyrizena ,int pocetStornovana){

        String vysledky = String.format(
                "=== ANALYTIKA ===\n" +
                        "Celková tržba:       %.2f Kč\n" +
                        "Průměrná hodnota:    %.2f Kč\n\n" +
                        "=== POČTY PODLE STAVU ===\n" +
                        "Nová:        %d\n" +
                        "Zaplacená:   %d\n" +
                        "Expedovaná:  %d\n" +
                        "Vyřízená:    %d\n" +
                        "Stornovaná:  %d",
                celkovaTrzba, prumerna,
                pocetNova, pocetZaplacena, pocetExpandovana, pocetVyrizena, pocetStornovana
        );

        txtAnalytika.setText(vysledky);
    }
}
