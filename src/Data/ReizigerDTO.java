package Data;

import domain.OVChipkaart;

import java.util.ArrayList;
import java.util.Date;

public class ReizigerDTO {
    private int id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;
    private int adres_id;
    private ArrayList<OVChipkaart> alleKaartenVanReiziger;
}
