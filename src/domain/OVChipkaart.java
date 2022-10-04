package domain;

import java.util.Date;

public class OVChipkaart {
    private int kaart_nummer;
    private Date geldig_tot;
    private int klasse;
    private String saldo;
    private int reiziger_id;

    public OVChipkaart(int kaart_nummer, int klasse, String saldo, Date geldig_tot) {
        this.kaart_nummer = kaart_nummer;
        this.klasse = klasse;
        this.saldo = saldo;
        this.geldig_tot = geldig_tot;
    }

    public int getKaart_nummer() {
        return kaart_nummer;
    }

    public void setKaart_nummer(int kaart_nummer) {
        this.kaart_nummer = kaart_nummer;
    }

    public int getKlasse() {
        return klasse;
    }

    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public Date getGeldig_tot() {
        return geldig_tot;
    }

    public void setGeldig_tot(Date geldig_tot) {
        this.geldig_tot = geldig_tot;
    }

    public int getReiziger_id() {
        return reiziger_id;
    }

    public void setReiziger_id(int reiziger_id) {
        this.reiziger_id = reiziger_id;
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
