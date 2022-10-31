package domain;

import java.sql.Date;
import java.util.List;

public class OVChipkaart {
    private int kaart_nummer;
    private Date geldig_tot;
    private int klasse;
    private Double saldo;

    private int reiziger_id;
    private String status;

    private List<Product> productOpDezeKaart;

    public OVChipkaart(int kaart_nummer, java.util.Date geldig_tot, int klasse, Double saldo, int reiziger_id) {
        this.kaart_nummer = kaart_nummer;
        this.geldig_tot = (java.sql.Date) geldig_tot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger_id = reiziger_id;
    }

    public String getStatus() {
        // TODO connect met andere tabel via ov_chipkaart_productDAOSQL
        return status;
    }

    public int getKlasse() {
        return klasse;
    }

    public int getKaart_nummer() {
        return kaart_nummer;
    }

    public Double getSaldo() {
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
