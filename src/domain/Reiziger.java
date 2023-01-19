package domain;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * The type Reiziger.
 */
public class Reiziger {
    private int id;
    private final String voorletters;
    private final String tussenvoegsel;
    private String achternaam;
    private final LocalDate geboortedatum;
    private Adres adres;

    private ArrayList<OVChipkaart> ovChipkaarten = new ArrayList<>();

    /**
     * Instantiates a new Reiziger.
     *
     * @param voorletters   the voorletters
     * @param tussenvoegsel the tussenvoegsel
     * @param achternaam    the achternaam
     * @param geboortedatum the geboortedatum
     */
    public Reiziger(String voorletters, String tussenvoegsel, String achternaam, LocalDate geboortedatum) {
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
    public ArrayList<OVChipkaart> getOvChipkaarten() {
        return ovChipkaarten;
    }
    public void setOvChipkaarten(ArrayList<OVChipkaart> ovChipkaarten) {
        this.ovChipkaarten = ovChipkaarten;
    }

    @Override
    public String toString() {
        String s = "ID#" + id + ": " + voorletters + " ";
        if (tussenvoegsel != null) {s += tussenvoegsel + " ";}
        s += achternaam + " " + geboortedatum + " } {kaarten= " ;
        if(ovChipkaarten != null) s += String.valueOf(ovChipkaarten.toString());
        s += "} ";

        if(adres != null) {s += "{Adres= " + adres;}

        return s + " }";
    }

    public void addOVChipkaart(OVChipkaart ovChipkaart) {
        this.ovChipkaarten.add(ovChipkaart);
    }
}
