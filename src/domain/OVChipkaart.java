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
    public OVChipkaart(LocalDate geldig_tot, int klasse, Double saldo, Reiziger reiziger, int kaart_nummer) {
        // TODO connect met andere tabel via ov_chipkaart_productDAOSQL

        this.kaart_nummer = kaart_nummer;
        this.geldig_tot = Date.valueOf(geldig_tot);
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger = reiziger;
        productOpDezeKaart = new ArrayList<>();
    }
    public OVChipkaart(LocalDate vandaag, int klasse, Double saldo, Reiziger reiziger) {
        this.geldig_tot = Date.valueOf(vandaag.plusYears(2)); // twee jaar geldig, alleen nieuwe.
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger = reiziger;
        productOpDezeKaart = new ArrayList<>();
    }

    public OVChipkaart(LocalDate vandaag, int klasse, double saldo) {
        this.geldig_tot = Date.valueOf(vandaag);
        this.klasse = klasse;
        this.saldo = saldo;
        productOpDezeKaart = new ArrayList<>();
    }

    public Reiziger getReiziger() {
        return reiziger;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }
    public String getStatus() {
        return status;
    }
    public int getKlasse() {
        return klasse;
    }
    public int getKaart_nummer() {
        return kaart_nummer;
    }
    public void setKaartNummer(int kaart_nummer) {
        this.kaart_nummer = kaart_nummer;
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
            System.out.println("voegt product ID#:" + product.getId() + " toe aan kaart: " + this.kaart_nummer);
            product.voegKaartToe(this);
            this.productOpDezeKaart.add(product);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public ArrayList<Product> getProductOpDezeKaart() {
        return (ArrayList<Product>) productOpDezeKaart;
    }
    @Override
    public String toString() {
        String string = "OVChipkaart{" +
                "kaart_nummer=" + kaart_nummer +
                ", klasse=" + klasse +
                ", status='" + saldo + '\'' +
                ", last_update=" + geldig_tot +
                ", reizigerID= " + reiziger.getId() +
                "\n alleproductenopkaart: {"
                + getProductOpDezeKaart().toString()
                + "} }";
        return string;
    }

}
