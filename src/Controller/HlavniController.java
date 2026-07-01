package Controller;

import Model.CsvLoader;
import Model.Objednavka;
import Model.StavObjednavky;
import View.HlavniOkno;

import java.util.ArrayList;
import java.util.List;

public class HlavniController {
    private HlavniOkno okno;
    private CsvLoader loader;
    private final List<Objednavka> vsechnyObjednavky = new ArrayList<>();

    public HlavniController(HlavniOkno okno){
        this.okno = okno;
        this.loader = new CsvLoader();
    }

    public void nactiSoubor(String cesta){
        List<Objednavka> nacteneObjednavky = loader.nactiSoubor(cesta);
        vsechnyObjednavky.clear();
        vsechnyObjednavky.addAll(nacteneObjednavky);
        okno.zobrazObjednavky(nacteneObjednavky);
    }

    public void aplikujFiltr(){
        String hledanyText = okno.getTxtFiltrJmeno();
        Object hledanyStav = okno.getCmbFiltrStav();
        List<Objednavka> vyfiltrovane = new ArrayList<>();

        for (Objednavka o : vsechnyObjednavky){
            boolean odpovidaTextu = hledanyText.isEmpty()||o.getZakaznik().toLowerCase().contains(hledanyText)||o.getProdukt().toLowerCase().contains(hledanyText);
            boolean odpovidaStavu = hledanyStav.equals("Vsechny")||o.getStav().name().equals(hledanyStav);
            if (odpovidaTextu&&odpovidaStavu){
                vyfiltrovane.add(o);
            }
        }

        okno.zobrazObjednavky(vyfiltrovane);
    }

    public void zmenStavObjednavky(int indexRadku) {
        StavObjednavky novyStav = okno.vyberNovyStavDialog();

        if(novyStav == null){return;}
        vsechnyObjednavky.get(indexRadku).setStav(novyStav);
        okno.zobrazObjednavky(vsechnyObjednavky);
    }

    public void pridejNovouObjednavku(){
        Objednavka nova = okno.getNovaObjednavkaFormular();
        if(nova == null){return;}
        vsechnyObjednavky.add(nova);
        okno.zobrazObjednavky(vsechnyObjednavky);
        okno.smazFormularNovaObjednavka();
    }
}
