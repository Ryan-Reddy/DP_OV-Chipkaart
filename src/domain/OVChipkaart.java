package domain;

import DAO.AdresDAO;
import DAOPsql.AdresDAOPsql;

import java.sql.Date;
import java.util.List;

import static domain.Reiziger.reizigerDAOPsql;

/**
 * The type Ov chipkaart.
 */
public class OVChipkaart {
    private int kaart_nummer;
    private Date geldig_tot;
    private int klasse;
    private Double saldo;

    private int reiziger_id;
    private String status;

    private List<Product> productOpDezeKaart;

    /**
     * Instantiates a new Ov chipkaart.
     *
     * @param kaart_nummer the kaart nummer
     * @param geldig_tot   the geldig tot
     * @param klasse       the klasse
     * @param saldo        the saldo
     * @param reiziger_id  the reiziger id
     */
    public OVChipkaart(int kaart_nummer, java.util.Date geldig_tot, int klasse, Double saldo, int reiziger_id) {
        this.kaart_nummer = kaart_nummer;
        this.geldig_tot = (java.sql.Date) geldig_tot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger_id = reiziger_id;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public String getStatus() {
        // TODO connect met andere tabel via ov_chipkaart_productDAOSQL
        return status;
    }

    /**
     * Gets klasse.
     *
     * @return the klasse
     */
    public int getKlasse() {
        return klasse;
    }

    /**
     * Gets kaart nummer.
     *
     * @return the kaart nummer
     */
    public int getKaart_nummer() {
        return kaart_nummer;
    }

    /**
     * Gets saldo.
     *
     * @return the saldo
     */
    public Double getSaldo() {
        return saldo;
    }


    /**
     * Gets geldig tot.
     *
     * @return the geldig tot
     */
    public Date getGeldig_tot() {
        return geldig_tot;
    }


    /**
     * Gets reiziger id.
     *
     * @return the reiziger id
     */
    public int getReiziger_id() {
        return reiziger_id;
    }


    /**
     * Remove product van kaart boolean.
     *
     * @param product the product
     * @return the boolean
     */
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

    /**
     * Add product aan kaart boolean.
     *
     * @param product the product
     * @return the boolean
     */
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
