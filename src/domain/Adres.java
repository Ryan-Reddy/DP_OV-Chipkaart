package domain;

public class Adres {
    private int adres_ID;
    private String postcode;
    private String huisnummer;
    private String straat;
    private String woonplaats;
    private int reiziger_id;

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public Adres(int adres_ID, String postcode, String huisnummer, String straat, String woonplaats, int reiziger_id) {
        this.adres_ID = adres_ID;
        this.postcode = postcode;
        this.huisnummer = huisnummer;
        this.straat = straat;
        this.woonplaats = woonplaats;
        this.reiziger_id = reiziger_id;
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

    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    public int getReiziger_id() {
        return reiziger_id;
    }

    public void setReiziger_id(int reiziger_id) {
        this.reiziger_id = reiziger_id;
    }

    @Override
    public String toString() {
        return "\n~;~:~:~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~:~:~;~" +
                "\n     Id=   \t" + adres_ID +
                "\n     Postcode= \t" + postcode +
                "\n     Huisnummer=    \t" + huisnummer +
                "\n     Straat= \t" + straat +
                "\n     Woonplaats:        \t" + woonplaats +
                "\n     Reiziger_id:      \t" + reiziger_id +
                "\n     Naam reiziger:      \t" + reiziger_id +
                "\n~;~:~:~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~:~:~;~";
    }
}
