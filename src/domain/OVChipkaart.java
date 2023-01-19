package domain;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Ov chipkaart.
 */
public class OVChipkaart {
    private int kaartNummer;
    private final Date geldigTot;
    private final int klasse;
    private final Double saldo;

    private Reiziger reiziger;
    private String status;


    private List<Product> productOpDezeKaart;

    /**
     * Instantiates a new Ov chipkaart.
     *
     * @param kaartNummer the kaart nummer
     * @param geldigTot   the geldig tot
     * @param klasse       the klasse
     * @param saldo        the saldo
     * @param reiziger  the reiziger
     */
    public OVChipkaart(LocalDate geldigTot, int klasse, Double saldo, Reiziger reiziger, int kaartNummer) {
        this.kaartNummer = kaartNummer;
        this.geldigTot = Date.valueOf(geldigTot);
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger = reiziger;
        productOpDezeKaart = new ArrayList<>();
    }
    public OVChipkaart(LocalDate vandaag, int klasse, Double saldo, Reiziger reiziger) {
        this.geldigTot = Date.valueOf(vandaag.plusYears(2)); // twee jaar geldig, alleen nieuwe.
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger = reiziger;
        productOpDezeKaart = new ArrayList<>();
    }

    public OVChipkaart(LocalDate vandaag, int klasse, double saldo) {
        this.geldigTot = Date.valueOf(vandaag);
        this.klasse = klasse;
        this.saldo = saldo;
        productOpDezeKaart = new ArrayList<>();
    }

    public Reiziger getReiziger() {
        return reiziger;
    }

    public String getStatus() {
        return status;
    }
    public int getKlasse() {
        return klasse;
    }
    public int getKaartNummer() {
        return kaartNummer;
    }
    public void setKaartNummer(int kaart_nummer) {
        this.kaartNummer = kaart_nummer;
    }
    public Double getSaldo() {
        return saldo;
    }
    public Date getGeldigTot() {
        return geldigTot;
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
     *
     * @param product the product
     */
    public void addProductAanKaart(Product product) {
        try {
            product.voegKaartToe(this);
            this.productOpDezeKaart.add(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Product> getProductOpDezeKaart() {
        return (ArrayList<Product>) productOpDezeKaart;
    }
    @Override
    public String toString() {
        return "OVChipkaart{" +
                "kaart_nummer=" + kaartNummer +
                ", klasse=" + klasse +
                ", status='" + saldo + '\'' +
                ", last_update=" + geldigTot +
                ", reizigerID= " + reiziger.getId() +
                "\n alleproductenopkaart: {"
                + getProductOpDezeKaart().toString()
                + "} }";
    }
}
