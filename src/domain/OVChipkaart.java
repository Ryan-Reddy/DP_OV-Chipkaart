package domain;

import java.util.Date;

public class OVChipkaart {
    private int kaart_nummer;
    private int product_nummer;
    private String status;
    private Date last_update;

    public OVChipkaart(int kaart_nummer, int product_nummer, String status, Date last_update) {
        this.kaart_nummer = kaart_nummer;
        this.product_nummer = product_nummer;
        this.status = status;
        this.last_update = last_update;
    }

    public int getKaart_nummer() {
        return kaart_nummer;
    }

    public void setKaart_nummer(int kaart_nummer) {
        this.kaart_nummer = kaart_nummer;
    }

    public int getProduct_nummer() {
        return product_nummer;
    }

    public void setProduct_nummer(int product_nummer) {
        this.product_nummer = product_nummer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getLast_update() {
        return last_update;
    }

    public void setLast_update(Date last_update) {
        this.last_update = last_update;
    }

    @Override
    public String toString() {
        return "OVChipkaart{" +
                "kaart_nummer=" + kaart_nummer +
                ", product_nummer=" + product_nummer +
                ", status='" + status + '\'' +
                ", last_update=" + last_update +
                '}';
    }
}
