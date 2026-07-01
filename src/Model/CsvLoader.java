package Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvLoader {
    public List<Objednavka> nactiSoubor(String cesta){
        List<Objednavka> objednavky = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(cesta))){
            String radek;
            boolean prvniRadek = true;

            while ((radek = br.readLine())!=null){
                if (prvniRadek){
                    prvniRadek = false;
                    continue;
                }
                String[] sloupce = radek.split(";");
                if (sloupce.length!=8) {
                    System.out.println("chybný řádek: " + radek);
                    continue;
                }
                try {
                    String id = sloupce[0].trim();
                    String datum = sloupce[1].trim();
                    String zakaznik = sloupce[2].trim();
                    String produkt = sloupce[3].trim();
                    String kategorie = sloupce[4].trim();
                    int pocet = Integer.parseInt(sloupce[5].trim());
                    double cena = Double.parseDouble(sloupce[6].trim());
                    StavObjednavky stav = StavObjednavky.valueOf(sloupce[7].trim().toUpperCase());

                    objednavky.add(new Objednavka(id,datum,zakaznik,produkt,kategorie,pocet,cena,stav));

                }
                catch (Exception e) {
                    System.out.println("chybný řádek: " + radek);
                }
            }
        }
        catch (IOException e){
            System.out.println("Soubor se nepodařilo načíst" + e.getMessage());
        }
        return objednavky;
    }
}
