package domain;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

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

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets voorletters.
     *
     * @return the voorletters
     */
    public String getVoorletters() {
        return voorletters;
    }

    /**
     * Sets voorletters.
     *
     * @param voorletters the voorletters
     */
    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    /**
     * Gets tussenvoegsel.
     *
     * @return the tussenvoegsel
     */
    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    /**
     * Sets tussenvoegsel.
     *
     * @param tussenvoegsel the tussenvoegsel
     */
    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    /**
     * Gets achternaam.
     *
     * @return the achternaam
     */
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

//        TODO: return geboortedatum;
        return java.sql.Date.valueOf("1990-12-23");
    }
    public void setAdres(Adres adres) {
    }

    public Adres getAdres() {
        return adres;
    }

    public void setGeboortedatum(LocalDate geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public ArrayList<OVChipkaart> getOvChipkaarts() {
        return ovChipkaarts;
    }

    public void setOvChipkaarts(ArrayList<OVChipkaart> ovChipkaarts) {
        this.ovChipkaarts = ovChipkaarts;
    }

    @Override
    public String toString() {
        String s = "#" + id + ": " + voorletters + " ";
        if (!tussenvoegsel.isBlank()) {s += tussenvoegsel + " ";}
        s += achternaam + " " + geboortedatum + " {adres= " + adres.toString() + " } {kaarten= " ;
        if(ovChipkaarts != null) return s + ovChipkaarts.size() + " }";
        return s + "null }";
    }

}
