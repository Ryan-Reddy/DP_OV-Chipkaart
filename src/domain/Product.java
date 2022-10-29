package domain;

import java.util.ArrayList;

public class Product {
    private int product_nummer;
    private String naam;
    private String beschrijving;
    private int prijs;

    private ArrayList<OVChipkaart> alleKaartenMetProduct;

    public Product(int product_nummer, String naam, String beschrijving, int prijs) {
        this.product_nummer = product_nummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
    }


    public boolean voegKaartToeAanLijstKaartenMetProduct(OVChipkaart ovChipkaart) {
        try {
            return alleKaartenMetProduct.add(ovChipkaart);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean verwijderKaartUitLijstKaartenMetProduct(OVChipkaart ovChipkaart) {
        try {
            return alleKaartenMetProduct.remove(ovChipkaart);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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

    public boolean removeKaartVanProductKaartenLijst(OVChipkaart ovChipkaart) {
            return alleKaartenMetProduct.remove(ovChipkaart);
    }

    public ArrayList<OVChipkaart> getAlleKaartenMetProduct() {
        return alleKaartenMetProduct;
    }
}
