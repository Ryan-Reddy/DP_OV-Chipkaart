package domain;

import java.util.Date;

public class Reiziger {

    private int id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;

    public Reiziger(int id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum) {
        this.id = id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
    }
    public String getId() {
        return String.valueOf(id);
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

    @Override
    public String toString() {
        return "\n~;~:~:~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~:~:~;~" +
                "\nvoorletters=   " + voorletters +
                "\ntussenvoegsel= " + tussenvoegsel +
                "\nachternaam=    " + achternaam +
                "\ngeboortedatum= " + geboortedatum +
                "\nmet id:        " + id +
                "\n~;~:~:~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~:~:~;~";
    }
}
