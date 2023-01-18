package domain;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * The type Reiziger.
 */
public class Reiziger {
    private int id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private LocalDate geboortedatum;
    private Adres adres;

    private ArrayList<OVChipkaart> ovChipkaarts;

    /**
     * Instantiates a new Reiziger.
     *
     * @param voorletters   the voorletters
     * @param tussenvoegsel the tussenvoegsel
     * @param achternaam    the achternaam
     * @param geboortedatum the geboortedatum
     * @throws SQLException the sql exception
     */
    public Reiziger(String voorletters, String tussenvoegsel, String achternaam, LocalDate geboortedatum) throws SQLException {
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
    }

    /**
     * Instantiates a new Reiziger.
     *
     * @param voorletters   the voorletters
     * @param tussenvoegsel the tussenvoegsel
     * @param achternaam    the achternaam
     * @param geboortedatum the geboortedatum
     * @param id            the id
     */
    public Reiziger(String voorletters, String tussenvoegsel, String achternaam, LocalDate geboortedatum, int id) {
        this.id = id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getVoorletters() {
        return voorletters;
    }
    public String getTussenvoegsel() {
        return tussenvoegsel;
    }
    public String getAchternaam() {
        return achternaam;
    }

    /**
     * Sets achternaam.
     *
     * @param achternaam the achternaam
     */
    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    /**
     * Gets geboortedatum.
     *
     * @return the geboortedatum
     */
    public Date getGeboortedatum() {
        return java.sql.Date.valueOf(geboortedatum);
    }
    public Adres getAdres() { return adres; }
    public void setAdres(Adres adres) { this.adres = adres; }
    public ArrayList<OVChipkaart> getOvChipkaarts() {
        return ovChipkaarts;
    }
    public void setOvChipkaarts(ArrayList<OVChipkaart> ovChipkaarts) {
        this.ovChipkaarts = ovChipkaarts;
    }

    @Override
    public String toString() {
        String s = "ID#" + id + ": " + voorletters + " ";
        if (tussenvoegsel != null) {s += tussenvoegsel + " ";}
        s += achternaam + " " + geboortedatum + " } {kaarten= " ;
        if(ovChipkaarts != null) s = String.valueOf(+ ovChipkaarts.size());
        s += "} ";

        if(adres != null) {s += "{Adres= " + adres.toString();};

        return s + " }";
    }

}
