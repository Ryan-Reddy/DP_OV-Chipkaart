package domain;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Ov chipkaart.
 */
public class OVChipkaart {
    private int kaart_nummer;
    private Date geldig_tot;
    private int klasse;
    private Double saldo;

    private Reiziger reiziger;
    private String status;

    public Reiziger getReiziger() {
        return reiziger;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    private List<Product> productOpDezeKaart;

    /**
     * Instantiates a new Ov chipkaart.
     *
     * @param kaart_nummer the kaart nummer
     * @param geldig_tot   the geldig tot
     * @param klasse       the klasse
     * @param saldo        the saldo
     * @param reiziger  the reiziger
     */
    public OVChipkaart(int kaart_nummer, LocalDate geldig_tot, int klasse, Double saldo, Reiziger reiziger) {
        this.kaart_nummer = kaart_nummer;
        geldig_tot.plusYears(2);
        this.geldig_tot = Date.valueOf(geldig_tot);
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger = reiziger;
        productOpDezeKaart = new ArrayList<>();
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
    /**
     * Remove product van kaart.
     * @param product the product
     * @return the boolean
     */
    public boolean removeProductVanKaart(Product product) {
        try {
            product.verwijderKaart(this);
            this.productOpDezeKaart = null;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Add product aan kaart.
     * @param product the product
     * @return the boolean
     */
    public boolean addProductAanKaart(Product product) {
        try {
            product.voegKaartToe(this);
            this.productOpDezeKaart.add(product);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public String toString() {
        String string = "OVChipkaart{" +
                "kaart_nummer=" + kaart_nummer +
                ", product_nummer=" + klasse +
                ", status='" + saldo + '\'' +
                ", last_update=" + geldig_tot +'}';
//        if (productOpDezeKaart != null) {
//            string += productOpDezeKaart.toString();
//        }
        return string;
    }

}
