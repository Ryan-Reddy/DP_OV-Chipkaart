package domain;

import DAO.AdresDAO;
import DAOPsql.AdresDAOPsql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The type Adres.
 */
public class Adres {
    /**
     * The Adres dao psql.
     */
    static AdresDAO adresDAOPsql;
    static {
        try {
            Connection mijnConn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ovchip", "postgres", "algra50");
            adresDAOPsql = new AdresDAOPsql(mijnConn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int adres_ID;
    private String postcode;
    private String huisnummer;
    private String straat;
    private String woonplaats;
    private int reiziger_id;

    /**
     * Instantiates a new Adres.
     *
     * @param postcode    the postcode
     * @param huisnummer  the huisnummer
     * @param straat      the straat
     * @param woonplaats  the woonplaats
     * @param reiziger_id the reiziger id
     * @throws SQLException the sql exception
     */
    public Adres(String postcode, String huisnummer, String straat, String woonplaats, int reiziger_id) throws SQLException {
        int sizeOfTable = adresDAOPsql.findAll().size();
        this.adres_ID = sizeOfTable +1; // correspondeert met de nummers in het systeem
        this.postcode = postcode;
        this.huisnummer = huisnummer;
        this.straat = straat;
        this.woonplaats = woonplaats;
        this.reiziger_id = reiziger_id;
//        adresDAOPsql.save(this);
    }

    /**
     * Instantiates a new Adres.
     *
     * @param postcode    the postcode
     * @param huisnummer  the huisnummer
     * @param straat      the straat
     * @param woonplaats  the woonplaats
     * @param reiziger_id the reiziger id
     * @param adres_id    the adres id
     * @throws SQLException the sql exception
     */
    public Adres(String postcode, String huisnummer, String straat, String woonplaats, int reiziger_id, int adres_id) throws SQLException {
        this.adres_ID = adres_id; // correspondeert met de nummers in het systeem
        this.postcode = postcode;
        this.huisnummer = huisnummer;
        this.straat = straat;
        this.woonplaats = woonplaats;
        this.reiziger_id = reiziger_id;
    }

    /**
     * Instantiates a new Adres.
     *
     * @throws SQLException the sql exception
     */
    public Adres() throws SQLException {
        this.adres_ID = adresDAOPsql.findAll().size()+1; // correspondeert met de nummers in het systeem
//        adresDAOPsql.save(this);
    }

    /**
     * Gets postcode.
     *
     * @return the postcode
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * Sets postcode.
     *
     * @param postcode the postcode
     */
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    /**
     * Gets adres id.
     *
     * @return the adres id
     */
    public int getAdres_ID() {
        return adres_ID;
    }

    /**
     * Sets adres id.
     *
     * @param adres_ID the adres id
     */
    public void setAdres_ID(int adres_ID) {
        this.adres_ID = adres_ID;
    }

    /**
     * Gets huisnummer.
     *
     * @return the huisnummer
     */
    public String getHuisnummer() {
        return huisnummer;
    }

    /**
     * Sets huisnummer.
     *
     * @param huisnummer the huisnummer
     */
    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
    }

    /**
     * Gets straat.
     *
     * @return the straat
     */
    public String getStraat() {
        return straat;
    }

    /**
     * Sets straat.
     *
     * @param straat the straat
     */
    public void setStraat(String straat) {
        this.straat = straat;
    }

    /**
     * Gets woonplaats.
     *
     * @return the woonplaats
     */
    public String getWoonplaats() {
        return woonplaats;
    }

    /**
     * Sets woonplaats.
     *
     * @param woonplaats the woonplaats
     */
    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    /**
     * Gets reiziger id.
     *
     * @return the reiziger id
     */
    public int getReiziger_id() {
        return reiziger_id;
    }

    /**
     * Sets reiziger id.
     *
     * @param reiziger_id the reiziger id
     */
    public void setReiziger_id(int reiziger_id) {
        this.reiziger_id = reiziger_id;
    }

    @Override
    public String toString() {
        Adres adres = adresDAOPsql.findByID(adres_ID);

        return "Adres{ # " + adres_ID +
                " " + postcode + '\'' +
                " " + huisnummer + '\'' +
                " " + straat + '\'' +
                '}';
    }
}
