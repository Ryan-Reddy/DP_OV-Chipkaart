package domain;

import java.util.Date;
import java.util.List;

public class OVChipkaart {
    private int kaart_nummer;
    private Date geldig_tot;
    private int klasse;
    private String saldo;
    private int reiziger_id;

    private List<Product> productOpDezeKaart;

    public OVChipkaart(int kaart_nummer, Date geldig_tot, int klasse, String saldo, int reiziger_id, List<Product> productOpDezeKaart) {
        this.kaart_nummer = kaart_nummer;
        this.geldig_tot = geldig_tot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger_id = reiziger_id;
        this.productOpDezeKaart = productOpDezeKaart;
    }

    public OVChipkaart(int kaart_nummer, Date geldig_tot, int klasse, String saldo, int reiziger_id) {
        this.kaart_nummer = kaart_nummer;
        this.geldig_tot = geldig_tot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger_id = reiziger_id;
    }

    public OVChipkaart(int kaart_nummer, int klasse, String saldo, Date geldig_tot) {
        this.kaart_nummer = kaart_nummer;
        this.klasse = klasse;
        this.saldo = saldo;
        this.geldig_tot = geldig_tot;
    }

    public int getKaart_nummer() {
        return kaart_nummer;
    }

    public int getKlasse() {
        return klasse;
    }

    public String getSaldo() {
        return saldo;
    }


    public Date getGeldig_tot() {
        return geldig_tot;
    }


    public int getReiziger_id() {
        return reiziger_id;
    }


    public boolean removeProductVanKaart(Product product) {
        try {
            product.removeKaartVanProductKaartenLijst(this);
            this.productOpDezeKaart = null;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addProductAanKaart(Product product) {
        try {
            product.voegKaartToeAanLijstKaartenMetProduct(this);
            this.productOpDezeKaart.add(product);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public String toString() {
        return "OVChipkaart{" +
                "kaart_nummer=" + kaart_nummer +
                ", product_nummer=" + klasse +
                ", status='" + saldo + '\'' +
                ", last_update=" + geldig_tot +
                '}';
    }
}
