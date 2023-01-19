package domain;

import dao.AdresDAO;
import daoPsql.AdresDAOPsql;

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
    static final AdresDAO adresDAOPsql;
    static {
        try {
            Connection mijnConn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ovchip", "postgres", "algra50");
            adresDAOPsql = new AdresDAOPsql(mijnConn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int adresId;
    private final String postcode;
    private final String huisnummer;
    private final String straat;
    private final String woonplaats;
    private int reizigerId;

    /**
     * Instantiates a new Adres.
     * AdresID gemaakt bij opslaan in db.
     * @param postcode    the postcode
     * @param huisnummer  the huisnummer
     * @param straat      the straat
     * @param woonplaats  the woonplaats
     * @param reizigerId the reiziger id
     */
    public Adres(String postcode, String huisnummer, String straat, String woonplaats, int reizigerId) {
        int sizeOfTable = adresDAOPsql.findAll().size();
        this.adresId = sizeOfTable +1; // correspondeert met de nummers in het systeem
        this.postcode = postcode;
        this.huisnummer = huisnummer;
        this.straat = straat;
        this.woonplaats = woonplaats;
        this.reizigerId = reizigerId;
    }

    /**
     * Instantiates a new Adres.
     * Gebruikt bij ophalen van adres inclusief ID uit de db.
     * @param postcode    the postcode
     * @param huisnummer  the huisnummer
     * @param straat      the straat
     * @param woonplaats  the woonplaats
     * @param reizigerId the reiziger id
     * @param adres_id    the adres id
     */
    public Adres(String postcode, String huisnummer, String straat, String woonplaats, int reizigerId, int adres_id) {
        this.adresId = adres_id; // correspondeert met de nummers in het systeem
        this.postcode = postcode;
        this.huisnummer = huisnummer;
        this.straat = straat;
        this.woonplaats = woonplaats;
        this.reizigerId = reizigerId;
    }
    public String getPostcode() {
        return postcode;
    }
    public int getAdresId() {
        return adresId;
    }
    public void setAdresId(int adresId) {
        this.adresId = adresId;
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
    public int getReizigerId() {
        return reizigerId;
    }

    @Override
    public String toString() {
        return "ID# " + adresId +
                " " + postcode + '\'' +
                " " + huisnummer + '\'' +
                " " + straat + '\'' +
                '}';
    }
}
