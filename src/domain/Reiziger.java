package domain;

import java.util.ArrayList;
import java.util.Date;

public class Reiziger {

    private int id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;

    private int adres_id;

    private ArrayList<OVChipkaart> alleKaartenVanReiziger;

    public Reiziger(int id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum) {
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

    public void setGeboortedatum(Date geboortedatum) {
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
