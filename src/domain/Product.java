package domain;

import DAOPsql.ProductDAOPsql;

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
        alleKaartenMetProduct = new ArrayList<>();
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
        alleKaartenMetProduct = new ArrayList<>();        // TODO connect met andere tabel via ov_chipkaart_productDAOSQL
    }
    public boolean voegKaartToe(OVChipkaart ovChipkaart) {
        try {
            return alleKaartenMetProduct.add(ovChipkaart);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean verwijderKaart(OVChipkaart ovChipkaart) {
        try {
            return alleKaartenMetProduct.remove(ovChipkaart);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public void setProduct_nummer(int product_nummer) {
        this.product_nummer = product_nummer;
    }
    public int getProduct_nummer() {
        return product_nummer;
    }
    public String getNaam() {
        return naam;
    }
    public String getBeschrijving() {
        return beschrijving;
    }
    public int getPrijs() {
        return prijs;
    }
    @Override
    public String toString() {
        return "\n Product #" + product_nummer + " " + naam +
                "\t\t\t beschrijving='" + beschrijving +
                ", prijs=" + prijs;
    }
    /**
     * Gets id.
     * @return the id
     */
    public int getId() {
        return product_nummer;
    }

}
