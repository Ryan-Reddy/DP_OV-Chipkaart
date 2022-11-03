package domain;

import DAOPsql.AdresDAOPsql;
import DAOPsql.ProductDAOPsql;
import DAOPsql.ReizigerDAOPsql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The type Product.
 */
public class Product {
    private int product_nummer;
    private String naam;
    private String beschrijving;
    private int prijs;
    static ProductDAOPsql productDAOPsql;
    static {
        try {
            Connection mijnConn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ovchip", "postgres", "algra50");
            productDAOPsql = new ProductDAOPsql(mijnConn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ArrayList<OVChipkaart> alleKaartenMetProduct;

    /**
     * Instantiates a new Product.
     *
     * product_nummer gets created
     * @param naam           the naam
     * @param beschrijving   the beschrijving
     * @param prijs          the prijs
     */
    public Product(String naam, String beschrijving, int prijs) throws SQLException {
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
        this.product_nummer = productDAOPsql.findAll().size() + 1;
    }
    /**
     * Instantiates a new Product.
     *
     * @param product_nummer the product nummer
     * @param naam           the naam
     * @param beschrijving   the beschrijving
     * @param prijs          the prijs
     */
    public Product(String naam, String beschrijving, int prijs, int product_nummer) throws SQLException {
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
        this.product_nummer = product_nummer;
    }


    /**
     * Voeg kaart toe aan lijst kaarten met product boolean.
     *
     * @param ovChipkaart the ov chipkaart
     * @return the boolean
     */
    public boolean voegKaartToeAanLijstKaartenMetProduct(OVChipkaart ovChipkaart) {
        try {
            return alleKaartenMetProduct.add(ovChipkaart);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Verwijder kaart uit lijst kaarten met product boolean.
     *
     * @param ovChipkaart the ov chipkaart
     * @return the boolean
     */
    public boolean verwijderKaartUitLijstKaartenMetProduct(OVChipkaart ovChipkaart) {
        try {
            return alleKaartenMetProduct.remove(ovChipkaart);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Gets product nummer.
     *
     * @return the product nummer
     */
    public int getProduct_nummer() {
        return product_nummer;
    }

    /**
     * Gets naam.
     *
     * @return the naam
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Gets beschrijving.
     *
     * @return the beschrijving
     */
    public String getBeschrijving() {
        return beschrijving;
    }

    /**
     * Gets prijs.
     *
     * @return the prijs
     */
    public int getPrijs() {
        return prijs;
    }

    /**
     * Remove kaart van product kaarten lijst boolean.
     *
     * @param ovChipkaart the ov chipkaart
     * @return the boolean
     */
    public boolean removeKaartVanProductKaartenLijst(OVChipkaart ovChipkaart) {
            return alleKaartenMetProduct.remove(ovChipkaart);
    }

    /**
     * Gets alle kaarten met product.
     *
     * @return the alle kaarten met product
     */
    public ArrayList<OVChipkaart> getAlleKaartenMetProduct() {
        return alleKaartenMetProduct;
    }

    @Override
    public String toString() {
        return "\n Product #" + product_nummer + " " + naam +
                "\t\t\t beschrijving='" + beschrijving +
                ", prijs=" + prijs +
                ", alleKaartenMetProduct=" + alleKaartenMetProduct;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return product_nummer;
    }
}
