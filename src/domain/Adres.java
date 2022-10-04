package domain;

public class Adres {
    private int id;
    private String postcode;
    private String huisnummer;
    private String straat;
    private String woonplaats;
    private String reiziger_id;

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getReiziger_id() {
        return reiziger_id;
    }

    public void setReiziger_id(String reiziger_id) {
        this.reiziger_id = reiziger_id;
    }

    @Override
    public String toString() {
        return "\n~;~:~:~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~:~:~;~" +
                "\n     Id=   \t" + id +
                "\n     Postcode= \t" + postcode +
                "\n     Huisnummer=    \t" + huisnummer +
                "\n     Straat= \t" + straat +
                "\n     Woonplaats:        \t" + woonplaats +
                "\n     Reiziger_id:      \t" + reiziger_id +
                "\n     Naam reiziger:      \t" + reiziger_id +
                "\n~;~:~:~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~:~:~;~";
    }
}
