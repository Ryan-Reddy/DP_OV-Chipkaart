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

public class Reiziger {
    static AdresDAO adresDAOPsql;
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

    private int adres_id;

    private ArrayList<OVChipkaart> alleKaartenVanReiziger;

    public Reiziger(String voorletters, String tussenvoegsel, String achternaam, LocalDate geboortedatum) throws SQLException {
        this.id = reizigerDAOPsql.findAll().size() + 1;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
        reizigerDAOPsql.save(this);
        Adres newAdres = new Adres();
        this.adres_id = newAdres.getAdres_ID();
    }

    public Reiziger(String voorletters, String tussenvoegsel, String achternaam, LocalDate geboortedatum, int id) {
        this.id = id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
        // haalt corresponderende adres_id op bij inladen uit db
        if (adresDAOPsql.getAdresByReiziger(this) != null) this.adres_id = adresDAOPsql.getAdresByReiziger(this).getAdres_ID();
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

    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public Date getGeboortedatum() {

//        TODO: return geboortedatum;
        return java.sql.Date.valueOf("1990-12-23");
    }

    public void setGeboortedatum(LocalDate geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public int getAdres_id() {
        return adres_id;
    }

    public void setAdres_id(int adres_id) {
        this.adres_id = adres_id;
    }

    @Override
    public String toString() {
        String s = "#" + id + ": " + voorletters + " " + tussenvoegsel + " " + achternaam + " " + geboortedatum + " {adres id= " + adres_id + "} kaartenvan: " ;
        if(alleKaartenVanReiziger !=null) return s + alleKaartenVanReiziger.size();
        return s + "geen kaarten gevonden";
    }
}
