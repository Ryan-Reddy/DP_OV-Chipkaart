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
     * AdresID gemaakt bij opslaan in db.
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
    }

    /**
     * Instantiates a new Adres.
     * Gebruikt bij ophalen van adres incl ID uit de db.
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
    public String getPostcode() {
        return postcode;
    }
    public int getAdres_ID() {
        return adres_ID;
    }
    public void setAdres_ID(int adres_ID) {
        this.adres_ID = adres_ID;
    }
    public String getHuisnummer() {
        return huisnummer;
    }
    public String getStraat() {
        return straat;
    }
    public String getWoonplaats() {
        return woonplaats;
    }
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
        String s = "ID# " + adres_ID +
                " " + postcode + '\'' +
                " " + huisnummer + '\'' +
                " " + straat + '\'' +
                '}';
        return s;
    }
}
