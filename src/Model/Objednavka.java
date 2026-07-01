package Model;

public class Objednavka {
    private String idObjednavky;
    private String datum;
    private String zakaznik;
    private String produkt;
    private String kategorie;
    private int pocet;
    private double cenaZaKus;
    private StavObjednavky stav;

    public Objednavka( String idObjednavky, String datum, String zakaznik, String produkt, String kategorie, int pocet, double cenaZaKus, StavObjednavky stav) {
        this.stav = stav;
        this.cenaZaKus = cenaZaKus;
        this.pocet = pocet;
        this.kategorie = kategorie;
        this.produkt = produkt;
        this.zakaznik = zakaznik;
        this.datum = datum;
        this.idObjednavky = idObjednavky;
    }

    public double getHodnotaObjednavky() {
        return pocet * cenaZaKus;
    }

    //Gettery a Settery

    public StavObjednavky getStav() {
        return stav;
    }

    public void setStav(StavObjednavky stav) {
        this.stav = stav;
    }

    public double getCenaZaKus() {
        return cenaZaKus;
    }

    public void setCenaZaKus(double cenaZaKus) {
        this.cenaZaKus = cenaZaKus;
    }

    public int getPocet() {
        return pocet;
    }

    public void setPocet(int pocet) {
        this.pocet = pocet;
    }

    public String getKategorie() {
        return kategorie;
    }

    public void setKategorie(String kategorie) {
        this.kategorie = kategorie;
    }

    public String getProdukt() {
        return produkt;
    }

    public void setProdukt(String produkt) {
        this.produkt = produkt;
    }

    public String getZakaznik() {
        return zakaznik;
    }

    public void setZakaznik(String zakaznik) {
        this.zakaznik = zakaznik;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getIdObjednavky() {
        return idObjednavky;
    }

    public void setIdObjednavky(String idObjednavky) {
        this.idObjednavky = idObjednavky;
    }

    @Override
    public String toString() {
        return idObjednavky + " | " + zakaznik + " | " + produkt + " | " + stav;
    }
}
