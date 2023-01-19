package domain;

import java.util.ArrayList;

/**
 * The type Product.
 */
public class Product {
    private int product_nummer;
    private final String naam;
    private final String beschrijving;
    private int prijs;

    private final ArrayList<OVChipkaart> ovChipkaartenMetProduct;
    /**
     * Instantiates a new Product.
     *
     * product_nummer gets created
     * @param naam           the naam
     * @param beschrijving   the beschrijving
     * @param prijs          the prijs
     */
    public Product(String naam, String beschrijving, int prijs) {
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
        ovChipkaartenMetProduct = new ArrayList<>();
    }
    /**
     * Instantiates a new Product.
     *
     * @param product_nummer the product nummer
     * @param naam           the naam
     * @param beschrijving   the beschrijving
     * @param prijs          the prijs
     */
    public Product(String naam, String beschrijving, int prijs, int product_nummer) {
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
        this.product_nummer = product_nummer;
        ovChipkaartenMetProduct = new ArrayList<>();
    }
    public void voegKaartToe(OVChipkaart ovChipkaart) {
        try {
            ovChipkaartenMetProduct.add(ovChipkaart);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void verwijderKaart(OVChipkaart ovChipkaart) {
        try {
            ovChipkaartenMetProduct.remove(ovChipkaart);
        } catch (Exception e) {
            e.printStackTrace();
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
        return "Product #" + product_nummer + " " + naam +
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

    public ArrayList<OVChipkaart> getOvChipkaartenMetProduct() {
        return ovChipkaartenMetProduct;
    }

    public void addOvChipKaart(OVChipkaart ovchippers) {
        ovChipkaartenMetProduct.add(ovchippers);
    }

    public Product setPrijs(int i) {
        this.prijs = i;
        return this;
    }
}
