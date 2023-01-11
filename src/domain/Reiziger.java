package domain;

import DAO.AdresDAO;
import DAOPsql.AdresDAOPsql;
import DAOPsql.ReizigerDAOPsql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

/**
 * The type Reiziger.
 */
public class Reiziger {
    /**
     * The Adres dao psql.
     */
    static AdresDAO adresDAOPsql;
    /**
     * The Reiziger dao psql.
     */
    static ReizigerDAOPsql reizigerDAOPsql;

    static {
        try {
            Connection mijnConn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ovchip", "postgres", "algra50");
            adresDAOPsql = new AdresDAOPsql(mijnConn);
            reizigerDAOPsql = new ReizigerDAOPsql(mijnConn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private int id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private LocalDate geboortedatum;

    private int adres_id = 0;

    private ArrayList<OVChipkaart> alleKaartenVanReiziger;

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
        Adres newAdres = new Adres();
        this.adres_id = newAdres.getAdres_ID();
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

        // haalt corresponderende adres_id op bij inladen uit db
        if (adresDAOPsql.getAdresByReiziger(this) != null) this.adres_id = adresDAOPsql.getAdresByReiziger(this).getAdres_ID();
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

    /**
     * Sets geboortedatum.
     *
     * @param geboortedatum the geboortedatum
     */
    public void setGeboortedatum(LocalDate geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    /**
     * Gets adres id.
     *
     * @return the adres id
     */
    public int getAdres_id() {
        return adres_id;
    }

    /**
     * Sets adres id.
     *
     * @param adres_id the adres id
     */
    public void setAdres_id(int adres_id) {
        this.adres_id = adres_id;
    }

    @Override
    public String toString() {
        String adresString = null;
        if (adresDAOPsql.getAdresByID(adres_id) != null)
             adresString = adresDAOPsql.getAdresByID(adres_id).toString();

        String s = "#" + id + ": " + voorletters + " ";
        if (!tussenvoegsel.isBlank()) {s += tussenvoegsel + " ";}

        s += achternaam + " " + geboortedatum + " {adres= " + adresString + " } {kaarten= " ;

        if(alleKaartenVanReiziger != null) return s + alleKaartenVanReiziger.size() + " }";
        return s + "null }";
    }
}
